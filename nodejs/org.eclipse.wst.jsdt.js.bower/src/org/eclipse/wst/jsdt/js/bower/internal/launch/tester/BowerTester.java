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
package org.eclipse.wst.jsdt.js.bower.internal.launch.tester;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.jsdt.js.bower.BowerPlugin;
import org.eclipse.wst.jsdt.js.bower.util.BowerUtil;
import org.eclipse.core.expressions.PropertyTester;

/**
 * @author Ilya Buziuk (ibuziuk)
 */
public final class BowerTester extends PropertyTester {
	private static final String IS_BOWER_INIT = "isBowerInit"; //$NON-NLS-1$

	public BowerTester() {
	}

	@Override
	public boolean test(Object receiver, String property, Object[] arg2, Object expectedValue) {
		try {
			if (IS_BOWER_INIT.equals(property) && receiver instanceof IResource) {
				IResource resource = (IResource) receiver;
				if (resource instanceof IProject) {
					return BowerUtil.isBowerJsonExist((IProject) resource);
				} else if (resource instanceof IFolder) {
					return BowerUtil.hasBowerJson((IFolder) resource);
				} else if (resource instanceof IFile) {
					return BowerUtil.isBowerJson((IFile) resource);
				}
			}
		} catch (CoreException e) {
			BowerPlugin.logError(e);
		}
		return false;
	}
	
}