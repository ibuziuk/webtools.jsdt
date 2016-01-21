package org.eclipse.wst.jsdt.js.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.jsdt.js.cli.core.CLI;
import org.eclipse.wst.jsdt.js.cli.core.CLICommand;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.GruntConstants;
import org.eclipse.wst.jsdt.js.grunt.internal.Messages;
import org.eclipse.wst.jsdt.js.grunt.internal.launch.GruntLaunchConstants;

public class GruntLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {
	public static final String LAUNCH_CONFIGURATION_ID = "org.eclipse.wst.jsdt.js.gruntLaunchConfigurationType"; //$NON-NLS-1$

	@Override
	public void launch(ILaunchConfiguration conf, String arg1, ILaunch arg2, IProgressMonitor monitor) throws CoreException {
		String projectName = conf.getAttribute(GruntLaunchConstants.PROJECT, (String) null);
		String filePath = conf.getAttribute(GruntLaunchConstants.FILE, (String) null);
		String commandName = conf.getAttribute(GruntLaunchConstants.COMMAND, (String) null);
		
		IProject project = getProject(projectName);
		IFile gruntFile = getGruntFile(project, filePath);
	
		launch(gruntFile, generateCLICommand(commandName), monitor);
	}
	
	protected void launch(IFile resource, CLICommand command, IProgressMonitor monitor) {
		try {
			 new CLI(resource.getProject(), resource.getParent().getLocation()).execute(command, monitor);
		} catch (CoreException e) {
			GruntPlugin.logError(e);
			ErrorDialog.openError(Display.getDefault().getActiveShell(), Messages.GruntLaunchError_Title,
					Messages.GruntLaunchError_Message, e.getStatus());
		}
	}
	
	protected CLICommand generateCLICommand(String command) {
		return new CLICommand(GruntConstants.GRUNT, command, null, null);
	}
	
	public static IProject getProject(String name) {
		if (name != null) {
			try {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
				if (project != null && project.exists()) {
					return project;
				}
			} catch (IllegalArgumentException e) {
			}
		}
		
		return null;
	}
	
	
	public static IFile getGruntFile(IProject project, String filePath) {
		if (project != null && project.exists()) {
			try {
				IResource resource = project.findMember(new Path(filePath));
				if (project != null && project.exists()) {
					return (IFile) resource;
				}
			} catch (IllegalArgumentException e) {
			}
		}
		
		return null;
	}
	
}
