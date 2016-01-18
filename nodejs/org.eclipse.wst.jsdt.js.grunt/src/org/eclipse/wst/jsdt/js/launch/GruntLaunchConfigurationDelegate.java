package org.eclipse.wst.jsdt.js.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

public class GruntLaunchConfigurationDelegate implements ILaunchConfigurationDelegate {
	public static final String LAUNCH_CONFIGURATION_ID = "org.eclipse.wst.jsdt.js.gruntLaunchConfigurationType"; //$NON-NLS-1$

	@Override
	public void launch(ILaunchConfiguration launch, String arg1, ILaunch arg2, IProgressMonitor monitor) throws CoreException {
		System.out.println("Launch....");
	}

}
