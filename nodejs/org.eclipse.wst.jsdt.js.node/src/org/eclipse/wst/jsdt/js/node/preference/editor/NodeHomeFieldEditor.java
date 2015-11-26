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
package org.eclipse.wst.jsdt.js.node.preference.editor;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.jsdt.js.node.Constants;
import org.eclipse.wst.jsdt.js.node.Messages;
import org.eclipse.wst.jsdt.js.node.util.NodeExternalUtil;
import org.eclipse.wst.jsdt.js.node.util.PlatformUtil;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class NodeHomeFieldEditor extends DirectoryFieldEditor {

	public NodeHomeFieldEditor(String name, String label, Composite composite) {
		super(name, label, composite);
		setEmptyStringAllowed(true);
	}

	@Override
	protected boolean doCheckState() {
		String filename = getTextControl().getText();
		filename = filename.trim();
		if (filename.isEmpty()) {
			this.getPage().setMessage(Messages.NodePreferencePage_NotSpecifiedNodeWarning, IStatus.WARNING);
			return true;
		} else {
			// clear the warning message
			this.getPage().setMessage(null);
		}

		if (!filename.endsWith(File.separator)) {
			filename = filename + File.separator;
		}

		File selectedFile = new File(filename);
		String nodeExecutableName = NodeExternalUtil.getNodeExecutableName();
		File nodeExecutable = new File(selectedFile, nodeExecutableName);
		
		if (!nodeExecutable.exists()) {
			
			if (PlatformUtil.isMacOS()) {
				setErrorMessage(Messages.NodePreferencePage_NotValidNodeError);
				return false;				
			} 
			
			// JBIDE-20351 Bower tooling doesn't detect node when the binary is called 'nodejs'
			// If "nodejs" is not detected try to detect "node"
			if (PlatformUtil.isLinux()) {
				nodeExecutableName = Constants.NODE;
			
			//JBIDE-20988 Preference validation fails on windows if node executable called node64.exe
			} else if (PlatformUtil.isWindows()) {
				nodeExecutableName = Constants.NODE_64_EXE;
			}
			
			nodeExecutable = new File(selectedFile, nodeExecutableName);
			if (!nodeExecutable.exists()) {
				setErrorMessage(Messages.NodePreferencePage_NotValidNodeError);
				return false;				
			}
		}

		return true;
	}

	@Override
	public void setValidateStrategy(int value) {
		super.setValidateStrategy(VALIDATE_ON_KEY_STROKE);
	}

}