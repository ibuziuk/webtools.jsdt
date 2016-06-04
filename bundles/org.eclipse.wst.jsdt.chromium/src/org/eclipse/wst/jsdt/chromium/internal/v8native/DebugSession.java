// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html

package org.eclipse.wst.jsdt.chromium.internal.v8native;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.wst.jsdt.chromium.Breakpoint;
import org.eclipse.wst.jsdt.chromium.DebugContext;
import org.eclipse.wst.jsdt.chromium.DebugEventListener;
import org.eclipse.wst.jsdt.chromium.InvalidContextException;
import org.eclipse.wst.jsdt.chromium.JavascriptVm;
import org.eclipse.wst.jsdt.chromium.JavascriptVm.ScriptsCallback;
import org.eclipse.wst.jsdt.chromium.JavascriptVm.SuspendCallback;
import org.eclipse.wst.jsdt.chromium.RelayOk;
import org.eclipse.wst.jsdt.chromium.SyncCallback;
import org.eclipse.wst.jsdt.chromium.Version;
import org.eclipse.wst.jsdt.chromium.internal.v8native.InternalContext.ContextDismissedCheckedException;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.V8ProtocolUtil;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.CommandResponse;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.FailedCommandResponse.ErrorDetails;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.input.SuccessCommandResponse;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.output.ContextlessDebuggerMessage;
import org.eclipse.wst.jsdt.chromium.internal.v8native.protocol.output.DebuggerMessageFactory;
import org.eclipse.wst.jsdt.chromium.util.AsyncFuture;
import org.eclipse.wst.jsdt.chromium.util.AsyncFuture.Callback;
import org.eclipse.wst.jsdt.chromium.util.AsyncFutureRef;
import org.eclipse.wst.jsdt.chromium.util.MethodIsBlockingException;
import org.eclipse.wst.jsdt.chromium.util.RelaySyncCallback;

/**
 * A class that holds and administers main parts of debug protocol implementation.
 */
public class DebugSession {
  private static final Logger LOGGER = Logger.getLogger(DebugSession.class.getName());

  /** The script manager for the associated tab. */
  private final ScriptManager scriptManager;

  private final V8CommandProcessor v8CommandProcessor;

  private final ContextBuilder contextBuilder;

  /** Our manager. */
  private DebugSessionManager sessionManager;

  /** Context owns breakpoint manager. */
  private final BreakpointManager breakpointManager;

  private final ScriptManagerProxy scriptManagerProxy = new ScriptManagerProxy(this);

  private final DefaultResponseHandler defaultResponseHandler;

  private final JavascriptVm javascriptVm;

  private volatile Version vmVersion = null;

  public DebugSession(DebugSessionManager sessionManager, V8ContextFilter contextFilter,
      V8CommandOutput v8CommandOutput, JavascriptVm javascriptVm) {
    this.scriptManager = new ScriptManager(contextFilter, this);
    this.sessionManager = sessionManager;
    this.javascriptVm = javascriptVm;
    this.breakpointManager = new BreakpointManager(this);

    this.defaultResponseHandler = new DefaultResponseHandler(this);
    this.v8CommandProcessor = new V8CommandProcessor(v8CommandOutput, defaultResponseHandler,
        this);
    this.contextBuilder = new ContextBuilder(this);
  }

  public ScriptManager getScriptManager() {
    return scriptManager;
  }

  public V8CommandProcessor getV8CommandProcessor() {
    return v8CommandProcessor;
  }

  public DebugSessionManager getSessionManager() {
    return sessionManager;
  }

  public void onDebuggerDetached() {
    getSessionManager().onDebuggerDetached();
    getScriptManager().reset();
    contextBuilder.forceCancelContext();
  }

  /**
   * Sends V8 command messages, but only those which doesn't depend on context.
   * Use {@code InternalContext} if you need to send context-specific commands.
   * @return
   */
  public RelayOk sendMessageAsync(ContextlessDebuggerMessage message, boolean isImmediate,
      V8CommandProcessor.V8HandlerCallback commandCallback, SyncCallback syncCallback) {
    return v8CommandProcessor.sendV8CommandAsync(message, isImmediate,
        commandCallback, syncCallback);
  }

  JavascriptVm getJavascriptVm() {
    return javascriptVm;
  }

  public Version getVmVersion() {
    return vmVersion;
  }

  /**
   * @return the DebugEventListener associated with this context
   */
  public DebugEventListener getDebugEventListener() {
    return getSessionManager().getDebugEventListener();
  }

  public BreakpointManager getBreakpointManager() {
    return breakpointManager;
  }

  public ScriptManagerProxy getScriptManagerProxy() {
    return scriptManagerProxy;
  }

  public ContextBuilder getContextBuilder() {
    return contextBuilder;
  }

  /**
   * Drops current context and creates a new one. This is useful if context is known to have changed
   * (e.g. experimental feature LiveEdit may change current stack while execution is suspended).
   * The method is asynchronous and returns immediately.
   * Does nothing if currently there is no active context. Otherwise dismisses current context,
   * invokes {@link DebugEventListener#resumed()} and initiates downloading stack frame descriptions
   * and building new context. When the context is built,
   * calls {@link DebugEventListener#suspended(DebugContext)}.
   * <p>
   * Must be called from Dispatch Thread.
   * @return true if context has been actually dropped.
   */
  public boolean recreateCurrentContext() {
    ContextBuilder.ExpectingBacktraceStep step = contextBuilder.startRebuildCurrentContext();
    if (step == null) {
      return false;
    }
    defaultResponseHandler.getBreakpointProcessor().processNextStep(step);
    return true;
  }

  public void suspend(final SuspendCallback suspendCallback) {
    V8CommandProcessor.V8HandlerCallback v8Callback = new V8CommandCallbackBase() {
      @Override
      public void failure(String message, ErrorDetails errorDetails) {
        if (suspendCallback != null) {
          suspendCallback.failure(new Exception(message));
        }
      }
      @Override
      public void success(SuccessCommandResponse successResponse) {
        if (suspendCallback != null) {
          suspendCallback.success();
        }

        ContextBuilder.ExpectingBreakEventStep step1 = contextBuilder.buildNewContextWhenIdle();
        if (step1 == null) {
          return;
        }
        ContextBuilder.ExpectingBacktraceStep step2 =
            step1.setContextState(Collections.<Breakpoint>emptyList(), null);
        defaultResponseHandler.getBreakpointProcessor().processNextStep(step2);
      }
    };
    sendMessageAsync(DebuggerMessageFactory.suspend(), true, v8Callback, null);
  }

  /**
   * A proxy to script manager that makes sure that all scripts have been pre-loaded from remote.
   * This is done only once per debug session.
   * TODO: consider loading all scripts synchronously on session start.
   */
  public static class ScriptManagerProxy {
    private final DebugSession debugSession;
    private final AsyncFutureRef<Void> scriptsLoadedFuture = new AsyncFutureRef<Void>();

    ScriptManagerProxy(DebugSession debugSession) {
      this.debugSession = debugSession;
    }

    public RelayOk getAllScripts(final ScriptsCallback callback, SyncCallback syncCallback) {
      if (!scriptsLoadedFuture.isInitialized()) {
        scriptsLoadedFuture.initializeRunning(new ScriptsRequester());
      }

      // Operation is multi-step, so make sure that syncCallback won't be left uncalled.
      RelaySyncCallback relay = new RelaySyncCallback(syncCallback);
      final RelaySyncCallback.Guard guard = relay.newGuard();

      Callback<Void> futureCallback = new Callback<Void>() {
        @Override public void done(Void res) {
          if (callback != null) {
            RelayOk relayOk = getAllScriptsAsync(callback, guard.getRelay());
            guard.discharge(relayOk);
          }
        }
      };

      return scriptsLoadedFuture.getAsync(futureCallback, guard.asSyncCallback());
    }

    private RelayOk getAllScriptsAsync(final ScriptsCallback callback, RelaySyncCallback relay) {
      // We should call the callback from Dispatch thread (so that the whole collection
      // kept fresh during the call-back).
      return debugSession.getV8CommandProcessor().runInDispatchThread(
          new Runnable() {
            @Override
            public void run() {
              callback.success(debugSession.getScriptManager().allScripts());
            }
          },
          relay.getUserSyncCallback());
    }

    private class ScriptsRequester implements AsyncFuture.Operation<Void> {
      @Override
      public RelayOk start(final Callback<Void> requestCallback,
          SyncCallback syncCallback) {
        V8Helper.ScriptLoadCallback scriptLoadCallback = new V8Helper.ScriptLoadCallback() {
          @Override
          public void success() {
            requestCallback.done(null);
          }

          @Override
          public void failure(final String message) {
            LOGGER.log(Level.SEVERE, null,
                new Exception("Failed to load scripts from remote: " + message));
            requestCallback.done(null);
          }
        };
        return V8Helper.reloadAllScriptsAsync(debugSession, scriptLoadCallback, syncCallback);
      }
    }
  }

  /**
   * Checks version of V8 and check if it in running state.
   */
  public void startCommunication() throws MethodIsBlockingException {
    V8BlockingCallback<Void> callback = new V8BlockingCallback<Void>() {
      @Override
      public Void messageReceived(CommandResponse response) {
        SuccessCommandResponse successResponse = response.asSuccess();
        if (successResponse == null) {
          return null;
        }
        Version vmVersion = V8ProtocolUtil.parseVersionResponse(successResponse);
        DebugSession.this.vmVersion = vmVersion;

        if (V8VersionFeatures.isRunningAccurate(vmVersion)) {
          Boolean running = successResponse.running();
          if (running == Boolean.FALSE) {
            ContextBuilder.ExpectingBreakEventStep step1 = contextBuilder.buildNewContextWhenIdle();
            // If step is not null -- we are already in process of building a context.
            if (step1 != null) {
              ContextBuilder.ExpectingBacktraceStep step2 =
                  step1.setContextState(Collections.<Breakpoint>emptyList(), null);

              defaultResponseHandler.getBreakpointProcessor().processNextStep(step2);
            }
          }
        }
        return null;
      }

      @Override
      protected Void handleSuccessfulResponse(SuccessCommandResponse response) {
        throw new UnsupportedOperationException();
      }
    };

    V8Helper.callV8Sync(this.v8CommandProcessor, DebuggerMessageFactory.version(), callback);
  }

  public RelayOk sendLoopbackMessage(Runnable callback, SyncCallback syncCallback) {
    return this.v8CommandProcessor.runInDispatchThread(callback, syncCallback);
  }

  public void maybeRethrowContextException(ContextDismissedCheckedException e) {
    // TODO(peter.rybin): make some kind of option out of this
    // Disabling strict policy ^
    final boolean strictPolicy = false;
    if (strictPolicy) {
      throw new InvalidContextException(e);
    }
  }

  public RelayOk maybeRethrowContextException(ContextDismissedCheckedException e,
      SyncCallback syncCallback) {
    // TODO(peter.rybin): make some kind of option out of this
    final boolean strictPolicy = true;
    if (strictPolicy) {
      throw new InvalidContextException(e);
    } else {
      if (syncCallback != null) {
        syncCallback.callbackDone(new InvalidContextException(e));
      }
      return new RelayOk() {
        // It's ok, we called SyncCallback alright.
      };
    }
  }
}
