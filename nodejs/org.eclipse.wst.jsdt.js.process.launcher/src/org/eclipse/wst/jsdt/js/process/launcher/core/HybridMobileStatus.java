package org.eclipse.wst.jsdt.js.process.launcher.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.jsdt.js.process.launcher.CLIPlugin;

public class HybridMobileStatus extends Status {
	
	public static final int STATUS_CODE_MISSING_ENGINE = 100;
	public static final int STATUS_CODE_CONFIG_PARSE_ERROR = 423;
	private IProject project;


	public HybridMobileStatus(int severity, String pluginId, int code,
			String message, Throwable exception) {
		super(severity, pluginId, code, message, exception);
	}
	
	public static HybridMobileStatus newMissingEngineStatus(IProject project, String message){
		HybridMobileStatus status =  new HybridMobileStatus(IStatus.ERROR, CLIPlugin.PLUGIN_ID, STATUS_CODE_MISSING_ENGINE, message, null);
		if(project != null){
			status.setProject(project);
		}
		return status;
	}

	public IProject getProject() {
		return project;
	}
	
	public void setProject(IProject project) {
		this.project = project;
	}
}
