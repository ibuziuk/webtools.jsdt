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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.wst.jsdt.js.common.build.system.Task;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.GruntConstants;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GruntLaunchConfigurationAutoFill {
	
	public static ILaunchConfiguration chooseLaunchConfiguration(ILaunchConfiguration[] configurations, Task task) {
		try {
			for (ILaunchConfiguration conf : configurations) {
				String buildFileAttribute = conf.getAttribute(GruntConstants.BUILD_FILE, (String) null);
				String buildFilePath = task.getBuildFile().getLocation().toOSString();
				// Launch Configuration per build file (i.e. Gruntfile.js / gulpfile.js)
				if (buildFilePath.equals(buildFileAttribute)) {
					return conf;
				}
			}
		} catch (CoreException e) {
			GruntPlugin.logError(e, e.getMessage());
		}
		return null;
	}
}
