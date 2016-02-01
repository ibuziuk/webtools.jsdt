/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * 	Contributors:
 * 		 Red Hat Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.wst.jsdt.js.grunt.internal.launch.shortcut;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.jsdt.js.common.build.system.Task;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.GruntConstants;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GruntLaunch implements ILaunchShortcut {
	
	@Override
	public void launch(IEditorPart arg0, String arg1) {
	
	}
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			 Object element = ((IStructuredSelection)selection).getFirstElement();
			 element.toString();
			 if (element instanceof Task) {
				 Task task = (Task) element;
				 launch(task,  mode);
			 }
		}
	}
	

	protected void launch(Task task, String mode) {
		try {
			ILaunchConfigurationType gruntLaunchConfiguraionType = DebugPlugin.getDefault().getLaunchManager()
					.getLaunchConfigurationType(GruntConstants.LAUNCH_CONFIGURATION_ID); 
			
			// Check if configuration already exists
			ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
					.getLaunchManager().getLaunchConfigurations(gruntLaunchConfiguraionType);
			
//			ILaunchConfiguration existingConfiguraion = CordovaSimLaunchConfigurationAutofillUtil
//					.chooseLaunchConfiguration(configurations, project);
//			
//			if (existingConfiguraion != null) {
//				DebugUITools.launch(existingConfiguraion, mode);
//			} else {
				IProject project = task.getBuildFile().getProject();	
				IFile buildFile = task.getBuildFile();	
				ILaunchConfigurationWorkingCopy newConfiguration = createEmptyLaunchConfiguration(project.getName() + " [" + buildFile.getName() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				newConfiguration.setAttribute(GruntConstants.PROJECT, project.getName());
				newConfiguration.setAttribute(GruntConstants.DIR, buildFile.getParent().getLocation().toOSString());
				newConfiguration.setAttribute(GruntConstants.COMMAND, task.getName());
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
		ILaunchConfigurationType launchConfigurationType = launchManager.getLaunchConfigurationType(GruntConstants.LAUNCH_CONFIGURATION_ID);
		ILaunchConfigurationWorkingCopy launchConfiguration = launchConfigurationType.newInstance(
				null, launchManager.generateLaunchConfigurationName(namePrefix));
		return launchConfiguration;
	}
	
	


}
