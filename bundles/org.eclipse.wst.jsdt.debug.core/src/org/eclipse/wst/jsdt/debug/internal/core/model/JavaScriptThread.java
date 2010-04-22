/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.debug.internal.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.jsdt.debug.core.breakpoints.IJavaScriptBreakpoint;
import org.eclipse.wst.jsdt.debug.core.breakpoints.IJavaScriptBreakpointParticipant;
import org.eclipse.wst.jsdt.debug.core.breakpoints.IJavaScriptLineBreakpoint;
import org.eclipse.wst.jsdt.debug.core.breakpoints.IJavaScriptLoadBreakpoint;
import org.eclipse.wst.jsdt.debug.core.jsdi.ScriptReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.StackFrame;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.Event;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.StepEvent;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequestManager;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.StepRequest;
import org.eclipse.wst.jsdt.debug.core.model.IJavaScriptStackFrame;
import org.eclipse.wst.jsdt.debug.core.model.IJavaScriptThread;
import org.eclipse.wst.jsdt.debug.core.model.IJavaScriptValue;
import org.eclipse.wst.jsdt.debug.internal.core.Constants;
import org.eclipse.wst.jsdt.debug.internal.core.JavaScriptDebugPlugin;
import org.eclipse.wst.jsdt.debug.internal.core.breakpoints.JavaScriptBreakpoint;
import org.eclipse.wst.jsdt.debug.internal.core.breakpoints.JavaScriptLoadBreakpoint;

/**
 * A JavaScript thread.
 * 
 * JavaScript threads act as their own event listener for suspend and 
 * step events and are called out to from JavaScript breakpoints to handle 
 * suspending at a breakpoint.
 * 
 * @since 1.0
 */
public class JavaScriptThread extends JavaScriptDebugElement implements IJavaScriptEventListener, IJavaScriptThread {

	/**
	 * handler for stepping
	 */
	class StepHandler implements IJavaScriptEventListener {

		private StepRequest request = null;
		
		/**
		 * Sends step request
		 */
		public void step(int kind, int detail) {
			if (canResume()) {
				request = createStepRequest(this, kind);
				thread.resume();
				pendingstep = this;
				clearFrames();
				clearBreakpoints();
				fireResumeEvent(detail);
			}
		}
		
		/**
		 * Creates a new step request
		 * 
		 * @param listener the element that will respond to the event
		 * @param step step command to send
		 * @return the newly created {@link StepRequest}
		 */
		StepRequest createStepRequest(IJavaScriptEventListener listener, int step) {
			EventRequestManager requestManager = getVM().eventRequestManager();
			StepRequest stepRequest = requestManager.createStepRequest(thread, step);
			stepRequest.setEnabled(true);
			getJavaScriptDebugTarget().addJSDIEventListener(listener, stepRequest);
			return stepRequest;
		}
		
		/**
		 * Aborts the pending step
		 */
		void abort() {
			try {
				deleteRequest(this, request);
				thread.resume();
				fireResumeEvent(DebugEvent.CLIENT_REQUEST);
			}
			finally {
				pendingstep = null;
			}
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.wst.jsdt.debug.internal.core.model.IJavaScriptEventListener#handleEvent(org.eclipse.wst.jsdt.debug.core.jsdi.event.Event, org.eclipse.wst.jsdt.debug.internal.core.model.JavaScriptDebugTarget, boolean, org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet)
		 */
		public boolean handleEvent(Event event, JavaScriptDebugTarget target, boolean suspendVote, EventSet eventSet) {
			StepEvent stepEvent = (StepEvent) event;
			return handleStepEvent(this, stepEvent);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.wst.jsdt.debug.internal.core.model.IJavaScriptEventListener#eventSetComplete(org.eclipse.wst.jsdt.debug.core.jsdi.event.Event, org.eclipse.wst.jsdt.debug.internal.core.model.JavaScriptDebugTarget, boolean, org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet)
		 */
		public void eventSetComplete(Event event, JavaScriptDebugTarget target, boolean suspend, EventSet eventSet) {}
	}
	
	/**
	 * Constant for no stack frames
	 * 
	 * @see #getStackFrames()
	 */
	static final IStackFrame[] NO_STACK_FRAMES = new IStackFrame[0];

	/**
	 * Constant for no breakpoints
	 * 
	 * @see #getBreakpoints()
	 */
	static final IBreakpoint[] NO_BREAKPOINTS = new IBreakpoint[0];

	// states
	private static final int UNKNOWN = 0;
	private static final int SUSPENDED = 1;
	private static final int RUNNING = 2;
	private static final int TERMINATED = 4;

	/**
	 * Stack frames, or <code>null</code> if none.
	 */
	private List frames = null;

	/**
	 * Breakpoints or empty if none.
	 */
	private ArrayList breakpoints = new ArrayList(4);

	/**
	 * Current state
	 */
	private int state = UNKNOWN;

	/**
	 * The underlying {@link ThreadReference} for this thread
	 */
	private final ThreadReference thread;

	/**
	 * Flag to track if the thread is in the process of suspending
	 */
	private boolean suspending = false;
	
	/**
	 * {@link StepHandler} handle to know if a step has been initiated
	 */
	private StepHandler pendingstep = null;
	
	/**
	 * Constructor
	 * 
	 * @param target the target the thread belongs to
	 * @param thread the underlying {@link ThreadReference}
	 */
	public JavaScriptThread(JavaScriptDebugTarget target, ThreadReference thread) {
		super(target);
		Assert.isNotNull(thread);
		this.thread = thread;
		this.state = thread.isSuspended() ? SUSPENDED : RUNNING;
	}

	/**
	 * @return the status text for the thread
	 */
	private String statusText() {
		switch(state) {
			case SUSPENDED: {
				if(this.breakpoints.size() > 0) {
					try {
						JavaScriptBreakpoint breakpoint = (JavaScriptBreakpoint) breakpoints.get(0);
						if (breakpoint instanceof JavaScriptLoadBreakpoint) {
							String name = breakpoint.getScriptPath();
							if(Constants.EMPTY_STRING.equals(name)) {
								name = getSourceName();
							}
							return NLS.bind(ModelMessages.JSDIThread_suspended_loading_script, name);
						}
						//TODO support function breakpoints here
						if(breakpoint instanceof IJavaScriptLineBreakpoint) {
							IJavaScriptLineBreakpoint bp = (IJavaScriptLineBreakpoint) breakpoint;
							return NLS.bind(ModelMessages.breakpoint_at_line_location, new String[] {Integer.toString(bp.getLineNumber()), getSourceName()});
						}
						//TODO also need to report stopped at debugger; statement
					} catch (CoreException ce) {
						JavaScriptDebugPlugin.log(ce);
					}
				}
				return ModelMessages.thread_suspended;
			}
			case RUNNING: {
				if(pendingstep != null) {
					return ModelMessages.thread_stepping;
				}
				return ModelMessages.thread_running;
			}
			case TERMINATED: {
				return ModelMessages.thread_terminated;
			}
			case ThreadReference.THREAD_STATUS_ZOMBIE: {
				return ModelMessages.thread_zombie;
			}
			default: {
				return ModelMessages.thread_state_unknown;
			}
		}
	}

	/**
	 * Returns the name of the source from the top stackframe or a 
	 * default name <code>&lt;evaluated_source&gt;</code>
	 * 
	 * @return the name for the source
	 * @throws DebugException
	 */
	String getSourceName() throws DebugException {
		IJavaScriptStackFrame frame = (IJavaScriptStackFrame) getTopStackFrame();
		if(frame != null) {
			return frame.getSourceName();
		}
		//all else failed say "evaluated_script"
		return ModelMessages.JavaScriptThread_evaluated_script;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getBreakpoints()
	 */
	public IBreakpoint[] getBreakpoints() {
		if (this.breakpoints.isEmpty()) {
			return NO_BREAKPOINTS;
		}
		return (IBreakpoint[]) this.breakpoints.toArray(new IBreakpoint[this.breakpoints.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getName()
	 */
	public String getName() throws DebugException {
		return NLS.bind(ModelMessages.JSDIThread_thread_title, new String[] { thread.name(), statusText() });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getPriority()
	 */
	public int getPriority() throws DebugException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getStackFrames()
	 */
	public IStackFrame[] getStackFrames() throws DebugException {
		if (!isSuspended()) {
			return NO_STACK_FRAMES;
		}
		if (this.frames == null) {
			this.frames = new ArrayList();
			List threadFrames = this.thread.frames();
			for (Iterator iterator = threadFrames.iterator(); iterator.hasNext();) {
				StackFrame stackFrame = (StackFrame) iterator.next();
				JavaScriptStackFrame jsdiStackFrame = createJSDIStackFrame(stackFrame);
				this.frames.add(jsdiStackFrame);
			}
		}
		return (IStackFrame[]) this.frames.toArray(new IStackFrame[this.frames.size()]);
	}

	/**
	 * Delegate method to create a {@link JavaScriptStackFrame}
	 * 
	 * @param stackFrame
	 * @return a new {@link JavaScriptStackFrame}
	 */
	JavaScriptStackFrame createJSDIStackFrame(StackFrame stackFrame) {
		return new JavaScriptStackFrame(this, stackFrame);
	}

	/**
	 * Clears out old stack frames after resuming.
	 */
	private synchronized void clearFrames() {
		if (this.frames != null) {
			this.frames.clear();
			this.frames = null;
		}
	}

	/**
	 * Clears out the cached collection of breakpoints
	 */
	private synchronized void clearBreakpoints() {
		this.breakpoints.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getTopStackFrame()
	 */
	public IStackFrame getTopStackFrame() throws DebugException {
		IStackFrame[] stackFrames = getStackFrames();
		if (stackFrames != null && stackFrames.length > 0) {
			return stackFrames[0];
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IThread#hasStackFrames()
	 */
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public synchronized boolean canResume() {
		return state == SUSPENDED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public synchronized boolean canSuspend() {
		return state == RUNNING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public synchronized boolean isSuspended() {
		return this.state == SUSPENDED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public synchronized void resume() throws DebugException {
		if (getDebugTarget().isSuspended()) {
			getDebugTarget().resume();
		} else {
			resume(true);
		}
	}

	/**
	 * Callback for the owning target to tell the thread to suspend
	 */
	public synchronized void targetResume() {
		resume(false);
	}

	/**
	 * Performs the actual resume of the thread
	 * 
	 * @param fireevent
	 */
	void resume(boolean fireevent) {
		if (canResume()) {
			abortPendingStep();
			this.thread.resume();
			markResumed();
			if (fireevent) {
				fireResumeEvent(DebugEvent.CLIENT_REQUEST);
			}
		}
	}

	/**
	 * Aborts a pending step and sets the pending step to <code>null</code>
	 */
	void abortPendingStep() {
		if(pendingstep != null) {
			pendingstep.abort();
			pendingstep = null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public synchronized void suspend() throws DebugException {
		if (canSuspend()) {
			suspendUnderlyingThread();
		}
	}

	/**
	 * Delegate method to suspend the underlying thread
	 */
	void suspendUnderlyingThread() {
		if(suspending) {
			return;
		}
		if (isSuspended()) {
			fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
			return;
		}
		suspending = true;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					JavaScriptThread.this.thread.suspend();
					long stop = System.currentTimeMillis() + VirtualMachine.DEFAULT_TIMEOUT;
					boolean suspended = JavaScriptThread.this.thread.isSuspended();
					while (System.currentTimeMillis() < stop && !suspended) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
						}
						suspended = JavaScriptThread.this.thread.isSuspended();
						if (suspended) {
							break;
						}
					}
					if (!suspended) {
						IStatus status= new Status(
								IStatus.ERROR, 
								JavaScriptDebugPlugin.PLUGIN_ID, 
								100, 
								NLS.bind(ModelMessages.thread_timed_out_trying_to_suspend, new String[] {new Integer(VirtualMachine.DEFAULT_TIMEOUT).toString()}), 
								null); 
						JavaScriptDebugPlugin.log(status);
					}
					markSuspended();
					fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
				} catch (RuntimeException exception) {
				} finally {
					suspending = false;
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * Call-back from a breakpoint that has been hit
	 * 
	 * @param breakpoint
	 * @param vote
	 * @return if the thread should stay suspended
	 */
	public boolean suspendForBreakpoint(IJavaScriptBreakpoint breakpoint, boolean vote) {
		IJavaScriptBreakpointParticipant[] participants = JavaScriptDebugPlugin.getParticipantManager().getParticipants(breakpoint);
		int suspend = 0;
		for (int i = 0; i < participants.length; i++) {
			suspend |= participants[i].breakpointHit(this, breakpoint);
		}
		if((suspend & IJavaScriptBreakpointParticipant.SUSPEND) > 0 ||
				suspend == IJavaScriptBreakpointParticipant.DONT_CARE) {
			addBreakpoint(breakpoint);
			return true;
		}
		return false;
	}

	/**
	 * Call-back from {@link JavaScriptBreakpoint#eventSetComplete(Event, JavaScriptDebugTarget, boolean, EventSet)} to handle suspending / cleanup
	 * 
	 * @param breakpoint
	 * @param suspend
	 *            if the thread should suspend
	 * @param eventSet
	 */
	public void suspendForBreakpointComplete(IJavaScriptBreakpoint breakpoint, boolean suspend, EventSet eventSet) {
		if (suspend) {
			try {
				if (breakpoint.getSuspendPolicy() == IJavaScriptBreakpoint.SUSPEND_THREAD) {
					markSuspended();
				} else {
					getDebugTarget().suspend();
				}
				fireSuspendEvent(DebugEvent.BREAKPOINT);
			} catch (CoreException ce) {
				JavaScriptDebugPlugin.log(ce);
			}
		}
	}
	
	/**
	 * Call-back for a script that has been loaded
	 * @param breakpoint
	 * @param script
	 * @param vote
	 * @return if the thread should stay suspended
	 */
	public int suspendForScriptLoad(IJavaScriptBreakpoint breakpoint, ScriptReference script, boolean vote) {
		IJavaScriptBreakpointParticipant[] participants = JavaScriptDebugPlugin.getParticipantManager().getParticipants(breakpoint);
		int suspend = 0;
		for (int i = 0; i < participants.length; i++) {
			suspend |= participants[i].scriptLoaded(this, script, breakpoint);
		}
		return suspend;
	}
	
	/**
	 * Call-back from {@link JavaScriptLoadBreakpoint#eventSetComplete(Event, JavaScriptDebugTarget, boolean, EventSet)} to handle suspending / cleanup
	 * 
	 * @param breakpoint
	 * @param script
	 * @param suspend if the thread should suspend
	 * @param eventSet
	 */
	public void suspendForScriptLoadComplete(IJavaScriptBreakpoint breakpoint, ScriptReference script, boolean suspend, EventSet eventSet) {
		if (suspend) {
			try {
				if (breakpoint.getSuspendPolicy() == IJavaScriptBreakpoint.SUSPEND_THREAD) {
					markSuspended();
				} else {
					getDebugTarget().suspend();
				}
				suspendUnderlyingThread();
				fireSuspendEvent(DebugEvent.BREAKPOINT);
			} catch (CoreException ce) {
				JavaScriptDebugPlugin.log(ce);
			}
		}
	}
	
	/**
	 * Adds the given breakpoint to the collection for this thread
	 * 
	 * @param breakpoint
	 * @return if the breakpoint added removed an existing entry
	 */
	public boolean addBreakpoint(IJavaScriptBreakpoint breakpoint) {
		synchronized (this.breakpoints) {
			return this.breakpoints.add(breakpoint);
		}
	}

	/**
	 * Removes the breakpoint from the cached collection of breakpoints
	 * 
	 * @param breakpoint
	 * @return if the breakpoint was removed
	 */
	public boolean removeBreakpoint(IJavaScriptBreakpoint breakpoint) {
		synchronized (this.breakpoints) {
			return this.breakpoints.remove(breakpoint);
		}
	}

	/**
	 * Sets the state of the thread to {@link #SUSPENDED}
	 */
	synchronized void markSuspended() {
		this.state = SUSPENDED;
	}

	/**
	 * Sets the state of the thread to {@link #RUNNING} and clears any cached stack frames
	 */
	synchronized void markResumed() {
		this.state = RUNNING;
		clearFrames();
		clearBreakpoints();
	}

	/**
	 * Sets the state of the thread to {@link #TERMINATED}
	 */
	synchronized void markTerminated() {
		this.state = TERMINATED;
	}
	
	/**
	 * Delete the given event request
	 * 
	 * @param listener the element that will be removed as a listener
	 * @param request
	 */
	public void deleteRequest(IJavaScriptEventListener listener, EventRequest request) {
		getJavaScriptDebugTarget().removeJSDIEventListener(listener, request);
		EventRequestManager requestManager = getVM().eventRequestManager();
		requestManager.deleteEventRequest(request);
	}
	
	/**
	 * Handles a {@link StepEvent}
	 * 
	 * @param listener the listener to remove
	 * @param event
	 * @return <code>true</code> if the event was not handled (we should resume), <code>false</code> if
	 * the event was handled (we should suspend)
	 */
	boolean handleStepEvent(IJavaScriptEventListener listener, StepEvent event) {
		ThreadReference threadReference = event.thread();
		if (threadReference == thread) {
			pendingstep = null;
			if(event.location() == null) {
				resume(true);
			}
			else {
				markSuspended();
				fireSuspendEvent(DebugEvent.STEP_END);
			}
			deleteRequest(listener, event.request());
			return false;
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		return canStep() || atScriptLoadBreakpoint();
	}

	/**
	 * Returns if the top breakpoint the thread is suspended on is an {@link IJavaScriptLoadBreakpoint}
	 * 
	 * @return <code>true</code> if the thread is suspended at a script load breakpoint, <code>false</code>
	 * otherwise
	 */
	boolean atScriptLoadBreakpoint() {
		if(this.breakpoints != null && this.breakpoints.size() > 1) {
			return this.breakpoints.get(0) instanceof IJavaScriptLoadBreakpoint;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return canStep();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		return canStep();
	}

	/**
	 * @return <code>true</code> if a step is allowed
	 */
	boolean canStep() {
		try {
			return isSuspended() && !isStepping() && getTopStackFrame() != null;
		}
		catch(DebugException de) {
			JavaScriptDebugPlugin.log(de);
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public synchronized boolean isStepping() {
		return pendingstep != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		StepHandler handler = new StepHandler();
		handler.step(StepRequest.STEP_INTO, DebugEvent.STEP_INTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		StepHandler handler = new StepHandler();
		handler.step(StepRequest.STEP_OVER, DebugEvent.STEP_OVER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		StepHandler handler = new StepHandler();
		handler.step(StepRequest.STEP_OUT, DebugEvent.STEP_RETURN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public synchronized boolean isTerminated() {
		return this.state == TERMINATED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public synchronized void terminate() throws DebugException {
		this.state = TERMINATED;
		getJavaScriptDebugTarget().terminate();
	}

	/**
	 * Returns if the underlying {@link ThreadReference} of this thread matches the given {@link ThreadReference} using pointer equality
	 * 
	 * @param thread
	 * @return true if the {@link ThreadReference}s are the same
	 */
	public boolean matches(ThreadReference thread) {
		return this.thread == thread;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.DebugElement#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if(IThread.class == adapter) {
			return this;
		}
		if(IStackFrame.class == adapter) {
			try {
				return getTopStackFrame();
			} catch (DebugException e) {
				JavaScriptDebugPlugin.log(e);
			}
		}
		if(IJavaScriptThread.class == adapter) {
			return this;
		}
		if(IJavaScriptStackFrame.class == adapter) {
			try {
				return getTopStackFrame();
			} catch (DebugException e) {
				JavaScriptDebugPlugin.log(e);
			}
		}
		return super.getAdapter(adapter);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.core.model.IJavaScriptThread#evaluate(java.lang.String)
	 */
	public IJavaScriptValue evaluate(String expression) {
		try {
			IStackFrame frame = getTopStackFrame();
			if(frame instanceof JavaScriptStackFrame) {
				return new JavaScriptValue(getJavaScriptDebugTarget(), ((JavaScriptStackFrame) frame).getUnderlyingStackFrame().evaluate(expression));
			}
		}
		catch(DebugException de) {
			//do nothing, return
		}
		return new JavaScriptValue(getJavaScriptDebugTarget(), getVM().mirrorOfNull());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.core.model.IJavaScriptThread#getFrameCount()
	 */
	public int getFrameCount() {
		if(isSuspended()) {
			return this.thread.frameCount();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.internal.core.model.IJavaScriptEventListener#handleEvent(org.eclipse.wst.jsdt.debug.core.jsdi.event.Event, org.eclipse.wst.jsdt.debug.internal.core.model.JavaScriptDebugTarget, boolean, org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet)
	 */
	public boolean handleEvent(Event event, JavaScriptDebugTarget target, boolean suspendVote, EventSet eventSet) {
		if(event instanceof StepEvent) {
			return handleStepEvent(this, (StepEvent) event);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.internal.core.model.IJavaScriptEventListener#eventSetComplete(org.eclipse.wst.jsdt.debug.core.jsdi.event.Event, org.eclipse.wst.jsdt.debug.internal.core.model.JavaScriptDebugTarget, boolean, org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet)
	 */
	public void eventSetComplete(Event event, JavaScriptDebugTarget target,	boolean suspend, EventSet eventSet) {
	}
}
