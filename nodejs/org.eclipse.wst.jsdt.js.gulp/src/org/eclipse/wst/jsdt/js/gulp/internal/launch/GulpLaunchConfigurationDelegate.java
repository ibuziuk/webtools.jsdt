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
package org.eclipse.wst.jsdt.js.gulp.internal.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.wst.jsdt.js.cli.core.CLI;
import org.eclipse.wst.jsdt.js.cli.core.CLICommand;
import org.eclipse.wst.jsdt.js.common.util.WorkbenchResourceUtil;
import org.eclipse.wst.jsdt.js.gulp.GulpPlugin;
import org.eclipse.wst.jsdt.js.gulp.internal.GulpConstants;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GulpLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration conf, String arg1, ILaunch arg2, IProgressMonitor monitor) throws CoreException {
		String projectName = conf.getAttribute(GulpConstants.PROJECT, (String) null);
		String dirPath = conf.getAttribute(GulpConstants.DIR, (String) null);
		String commandName = conf.getAttribute(GulpConstants.COMMAND, (String) null);
		
		IProject project = WorkbenchResourceUtil.getProject(projectName);
		if (project != null && project.exists()) {
			IPath dir = (dirPath == null) ? project.getLocation() : new Path(dirPath);
			CLICommand command = generateCLICommand(commandName);
			launchGulp(project, dir, command, monitor);
		}	
	}
	

	private void launchGulp(IProject project, IPath dir, CLICommand command, IProgressMonitor monitor) {
		try {
			WorkbenchResourceUtil.showConsoleView();
			new CLI(project, dir, command).execute(monitor);
		} catch (CoreException e) {
			GulpPlugin.logError(e);
		}
	}
	
	protected CLICommand generateCLICommand(String commandName) {
		return new CLICommand(GulpConstants.GULP, commandName, null, null);
	}

}
