/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * 	Contributors:
 * 		 Red Hat Inc. - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.wst.jsdt.js.bower.internal.launch.shortcut;

import org.eclipse.wst.jsdt.js.bower.BowerCommands;
import org.eclipse.wst.jsdt.js.bower.internal.BowerConstants;
import org.eclipse.wst.jsdt.js.process.launcher.core.CLICommand;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class BowerUpdate extends GenericBowerLaunch {
	private static final String LAUNCH_NAME = "Bower Update"; //$NON-NLS-1$
	private static final CLICommand COMMAND = new CLICommand(BowerConstants.BOWER, BowerCommands.UPDATE.getValue(),
			null, null);
	
	@Override
	protected String getLaunchName() {
		return LAUNCH_NAME;
	}
	
	@Override
	protected String getCommandName() {
		return BowerCommands.UPDATE.getValue();
	}

	@Override
	protected CLICommand getCLICommand() {
		return COMMAND;
	}

}
