// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html

package org.eclipse.wst.jsdt.chromium.internal.wip;

import org.eclipse.wst.jsdt.chromium.DebugContext;
import org.eclipse.wst.jsdt.chromium.RelayOk;
import org.eclipse.wst.jsdt.chromium.Script;
import org.eclipse.wst.jsdt.chromium.SyncCallback;
import org.eclipse.wst.jsdt.chromium.UpdatableScript;
import org.eclipse.wst.jsdt.chromium.internal.ScriptBase;
import org.eclipse.wst.jsdt.chromium.internal.wip.WipContextBuilder.WipDebugContextImpl;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.input.debugger.SetScriptSourceData;
import org.eclipse.wst.jsdt.chromium.internal.wip.protocol.output.debugger.SetScriptSourceParams;
import org.eclipse.wst.jsdt.chromium.util.GenericCallback;
import org.eclipse.wst.jsdt.chromium.util.RelaySyncCallback;

/**
 * Wip implementation of {@link Script}.
 */
class WipScriptImpl extends ScriptBase<String> {
  private final WipScriptManager scriptManager;

  WipScriptImpl(WipScriptManager scriptManager, Descriptor<String> descriptor) {
    super(descriptor);
    this.scriptManager = scriptManager;
  }

  @Override
  public RelayOk setSourceOnRemote(String newSource, UpdateCallback callback,
      SyncCallback syncCallback) {
    return sendLiveEditRequest(newSource, false, callback, syncCallback);
  }

  @Override
  public RelayOk previewSetSource(String newSource, UpdateCallback callback,
      SyncCallback syncCallback) {
    return sendLiveEditRequest(newSource, true, callback, syncCallback);
  }

  private RelayOk sendLiveEditRequest(String newSource, final boolean preview,
      final UpdateCallback updateCallback,
      final SyncCallback syncCallback) {

    RelaySyncCallback relay = new RelaySyncCallback(syncCallback);
    final RelaySyncCallback.Guard guard = relay.newGuard();

    SetScriptSourceParams params = new SetScriptSourceParams(getId(), newSource, preview);

    GenericCallback<SetScriptSourceData> commandCallback =
        new GenericCallback<SetScriptSourceData>() {
      @Override
      public void success(SetScriptSourceData value) {
        RelayOk relayOk = guard.getRelay().finish();
        // possiblyUpdateCallFrames(preview, value, updateCallback, guard.getRelay());
        guard.discharge(relayOk);
        WipContextBuilder contextBuilder = scriptManager.getTabImpl().getContextBuilder();
        doStepIn(contextBuilder);
      }

      @Override
      public void failure(Exception exception) {
        // TODO: provide failure details when supported by protocol.
        UpdatableScript.Failure failure = UpdatableScript.Failure.UNSPECIFIED;
        updateCallback.failure(exception.getMessage(), failure);
      }
      
      private void doStepIn(WipContextBuilder contextBuilder) {
        if (contextBuilder != null) {
            WipDebugContextImpl currentContext = contextBuilder.getCurrentContext();
            if (currentContext != null) {
                currentContext.continueVm(DebugContext.StepAction.IN, 0, null);
            }
        }
      }
    };

    WipCommandProcessor commandProcessor = scriptManager.getTabImpl().getCommandProcessor();
    return commandProcessor.send(params, commandCallback, guard.asSyncCallback());
  }
  /*
  private RelayOk possiblyUpdateCallFrames(boolean preview, final SetScriptSourceData data,
      final UpdateCallback updateCallback, RelaySyncCallback relay) {

    // TODO: support 'step-in recommended'.

    List<CallFrameValue> callFrames = null;
    if (!preview) {
      callFrames = data.callFrames();
    }
    if (callFrames == null) {
      dispatchResult(data.result(), updateCallback);
      return relay.finish();
    } else {
      GenericCallback<Void> setFramesCallback =
          new GenericCallback<Void>() {
        @Override public void success(Void value) {
          dispatchResult(data.result(), updateCallback);
        }
        @Override public void failure(Exception exception) {
          throw new RuntimeException(exception);
        }
      };
      WipContextBuilder contextBuilder = scriptManager.getTabImpl().getContextBuilder();
      return contextBuilder.updateStackTrace(callFrames, setFramesCallback,
          relay.getUserSyncCallback());
    }
  }

  private void dispatchResult(SetScriptSourceData.Result result, UpdateCallback updateCallback) {
    if (updateCallback != null) {
      LiveEditResult liveEditResult;
      try {
        liveEditResult =
            LiveEditProtocolParserAccess.get().parseLiveEditResult(result.getUnderlyingObject());
      } catch (JsonProtocolParseException e) {
        throw new RuntimeException("Failed to parse LiveEdit response", e);
      }
      ChangeDescription wrappedChangeDescription =
          UpdateResultParser.wrapChangeDescription(liveEditResult);
      updateCallback.success(false, null, wrappedChangeDescription);
    }
  }
  */
}
