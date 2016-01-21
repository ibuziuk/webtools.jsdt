package org.eclipse.wst.jsdt.js.grunt.internal.launch;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.jsdt.js.cli.core.CLI;
import org.eclipse.wst.jsdt.js.cli.core.CLICommand;
import org.eclipse.wst.jsdt.js.common.build.system.GenericBuildSytemLaunch;
import org.eclipse.wst.jsdt.js.common.build.system.Task;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.GruntConstants;
import org.eclipse.wst.jsdt.js.grunt.internal.Messages;
import org.eclipse.wst.jsdt.js.launch.GruntLaunchConfigurationDelegate;

public class GruntLaunch extends GenericBuildSytemLaunch {
	@Override
	public void launch(IEditorPart arg0, String arg1) {
		// TODO Auto-generated method stub
		super.launch(arg0, arg1);
	}
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			 Object element = ((IStructuredSelection)selection).getFirstElement();
			 element.toString();
			 if (element != null && element instanceof Task) {
				 Task task = (Task) element;
				 launch(task,  mode);
			 }
		}
	}
	
	
	protected void launch(Task task, String mode) {
		try {
			ILaunchConfigurationType gruntLaunchConfiguraionType = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurationType(GruntLaunchConfigurationDelegate.LAUNCH_CONFIGURATION_ID); 
			ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations(gruntLaunchConfiguraionType);
			
//			ILaunchConfiguration existingConfiguraion = CordovaSimLaunchConfigurationAutofillUtil
//					.chooseLaunchConfiguration(configurations, project);
//			
//			if (existingConfiguraion != null) {
//				DebugUITools.launch(existingConfiguraion, mode);
//			} else {
				ILaunchConfigurationWorkingCopy newConfiguration = createEmptyLaunchConfiguration(task.getName() + "[" + task + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				newConfiguration.setAttribute(GruntLaunchConstants.PROJECT, task.getBuildFile().getProject().getName());
				newConfiguration.setAttribute(GruntLaunchConstants.FILE, getRelativePath(task.getBuildFile().getProject(), task.getBuildFile()).toString());
				newConfiguration.setAttribute(GruntLaunchConstants.COMMAND, task.getName());
				newConfiguration.doSave();
				DebugUITools.launch(newConfiguration, mode);				
//			}
		} catch (CoreException e) {
			GruntPlugin.logError(e, e.getMessage());
		}
	}

	protected ILaunchConfigurationWorkingCopy createEmptyLaunchConfiguration(
			String namePrefix) throws CoreException {
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType launchConfigurationType = launchManager.getLaunchConfigurationType(GruntLaunchConfigurationDelegate.LAUNCH_CONFIGURATION_ID);
		ILaunchConfigurationWorkingCopy launchConfiguration = launchConfigurationType.newInstance(
				null, launchManager.generateLaunchConfigurationName(namePrefix));
		return launchConfiguration;
	}
	
	

	@Override
	protected void launch(IFile resource, CLICommand command) {
		try {
			 new CLI(resource.getProject(), resource.getParent().getLocation()).execute(command, null);
		} catch (CoreException e) {
			GruntPlugin.logError(e);
			ErrorDialog.openError(Display.getDefault().getActiveShell(), Messages.GruntLaunchError_Title,
					Messages.GruntLaunchError_Message, e.getStatus());
		}
	}
	
	@Override
	protected CLICommand generateCLICommand(String commandName) {
		return new CLICommand(GruntConstants.GRUNT, commandName, null, null);
	}
	
	public static IPath getRelativePath(IContainer container, IResource resource) {
		if (resource == null) {
			return null;
		}
		if (container == null) {
			return resource.getFullPath();
		}
	
		IPath containerPath = container.getFullPath();
		IPath resourcePath = resource.getFullPath();
	
		if (containerPath.isPrefixOf(resourcePath)) {
			int containerPathSegmentCount = containerPath.segmentCount();
			return resourcePath.removeFirstSegments(containerPathSegmentCount);			
		}
		return null;
	}

}
