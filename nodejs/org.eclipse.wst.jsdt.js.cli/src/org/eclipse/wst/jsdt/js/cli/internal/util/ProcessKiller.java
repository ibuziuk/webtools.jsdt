package org.eclipse.wst.jsdt.js.cli.internal.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.debug.core.model.RuntimeProcess;

public final class ProcessKiller {
	
	private ProcessKiller(){
	}
	
	public static void kill(RuntimeProcess process) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {
		long pid = getPID(process);
		killByPID(pid);
	}
	
	private static long getPID(RuntimeProcess process) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, IOException {		
		Field f = process.getClass().getDeclaredField("fProcess"); //$NON-NLS-1$
		f.setAccessible(true);
		Process systemProcess = (Process) f.get(process);
		systemProcess.destroyForcibly();
		Field field = systemProcess.getClass().getDeclaredField("handle"); //$NON-NLS-1$
		field.setAccessible(true);
		return field.getLong(systemProcess);
		
	}
	
	private static void killByPID(long pid) throws IOException {
		Runtime runtime = Runtime.getRuntime();
//		if (PlatformUtil.isWindows()) {
			runtime.exec("taskkill /F /pid " + pid); //$NON-NLS-1$
//		} else {
//			runtime.exec("kill -9 " + pid); //$NON-NLS-1$
//		}
	}

}
