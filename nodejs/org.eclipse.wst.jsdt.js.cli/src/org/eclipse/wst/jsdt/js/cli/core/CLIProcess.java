package org.eclipse.wst.jsdt.js.cli.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.eclipse.wst.jsdt.js.cli.internal.util.ProcessKiller;

public class CLIProcess implements IProcess {
	private IProcess process;
	
	public CLIProcess(IProcess process) {
		this.process = process;
	}

	@Override
	public <T> T getAdapter(Class<T> arg0) {
		return process.getAdapter(arg0);
	}

	@Override
	public boolean canTerminate() {
		return process.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return process.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		try {
			process.getStreamsProxy().write("\u0003");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (process instanceof RuntimeProcess) {
			try {
				ProcessKiller.kill((RuntimeProcess) process);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchFieldException | IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public String getAttribute(String arg0) {
		return process.getAttribute(arg0);
	}

	@Override
	public int getExitValue() throws DebugException {
		return process.getExitValue();
	}

	@Override
	public String getLabel() {
		return process.getLabel();
	}

	@Override
	public ILaunch getLaunch() {
		return process.getLaunch();
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return process.getStreamsProxy();
	}

	@Override
	public void setAttribute(String arg0, String arg1) {
		process.setAttribute(arg0, arg1);
	}

}
