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
package org.eclipse.wst.jsdt.chromium.debug.js.runtime;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.jsdt.core.runtime.AbstractJSRuntimeType;
import org.eclipse.wst.jsdt.core.runtime.IJSRuntimeInstall;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class ChromiumRuntimeType extends AbstractJSRuntimeType {

	@Override
	public String getName() {
		return "Chrome / Chromium Runtime"; // TODO move to messages
	}

	@Override
	protected IJSRuntimeInstall doCreateRuntimeInstall(String id) {
		return new ChromiumRuntimeInstall(this,  id);
	}

	@Override
	protected IStatus doValidateInstallLocation(File tempFile) {
		// FIXME: Add real Chrome / Chromium path validation
		return Status.OK_STATUS;
	}

}
