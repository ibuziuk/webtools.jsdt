package org.eclipse.wst.jsdt.js.cli.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.jsdt.js.cli.CLIPlugin;

/**
 * Generic execution result for Cordova CLI.
 * 
 * @author Gorkem Ercan
 *
 */
public class CLIResult {
	private final String errorMessage;
	private final String message;
	
	public CLIResult(String error, String message){
		this.errorMessage = error;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	String getErrorMessage() {
		return errorMessage;
	}


	public boolean hasError() {
		return errorMessage != null && !errorMessage.isEmpty();
	}
	
	IStatus asStatus(){
		if(hasError()){
			return new CLIStatus(IStatus.ERROR, CLIPlugin.PLUGIN_ID, 500, getErrorMessage(), null);
		}
		return Status.OK_STATUS;
	}
	
	CoreException asCoreException(){
		if(hasError()){
			return new CoreException(asStatus());
		}
		return null;
	}

}
