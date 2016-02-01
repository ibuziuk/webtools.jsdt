/*******************************************************************************
 * Copyright (c) 2007-2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.wst.jsdt.js.grunt.internal.launch;

import java.rmi.activation.Activator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;


public class GruntLaunchConfigurationAutoFill {
	
	public static void fillLaunchConfiguraion(ILaunchConfigurationWorkingCopy launchConfiguration, IProject project) {
		if (project != null) {
//			launchConfiguration.setAttribute(CordovaSimLaunchConstants.PROJECT, project.getName());
		}
	}
	
	public static ILaunchConfiguration chooseLaunchConfiguration(ILaunchConfiguration[] configurations, IProject project) {
//		try {
//			for (ILaunchConfiguration configuration : configurations) {
//				String projectName = configuration.getAttribute(CordovaSimLaunchConstants.PROJECT, (String) null);
//				String fh = configuration.getAttribute(CordovaSimLaunchConstants.FH, (String) null);
//				if (fh == null && projectName != null && projectName.equals(project.getName())) {
//					return configuration;
//				}
//			}
//		} catch (CoreException e) {
//			GruntPlugin.logError(e, e.getMessage());
//		}
		return null;
	}
	
	public static IProject getProjectToRun(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			Object firstElement = structuredSelection.getFirstElement();
			IResource resource = ResourceUtil.getResource(firstElement);
			if (resource != null) {
				return resource.getProject();
			}
		}
		return null;
	}
}
