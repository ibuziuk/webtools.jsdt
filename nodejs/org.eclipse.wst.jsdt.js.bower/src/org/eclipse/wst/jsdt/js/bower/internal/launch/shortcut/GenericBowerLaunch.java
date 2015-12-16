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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.wst.jsdt.js.bower.BowerPlugin;
import org.eclipse.wst.jsdt.js.bower.internal.BowerConstants;
import org.eclipse.wst.jsdt.js.bower.util.BowerUtil;
import org.eclipse.wst.jsdt.js.process.launcher.core.CLI;
import org.eclipse.wst.jsdt.js.process.launcher.core.CLICommand;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public abstract class GenericBowerLaunch implements ILaunchShortcut {
	
	protected abstract String getCommandName();

	protected abstract String getLaunchName();
	
	protected abstract CLICommand getCLICommand();
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			 Object element = ((IStructuredSelection)selection).getFirstElement();
			 if (element != null && element instanceof IResource) {
				try {
					IResource selectedResource = (IResource) element;
					launchBower(selectedResource);
				} catch (CoreException e) {
					BowerPlugin.logError(e);
				}
			 }
		}
	}
	
	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput editorInput = editor.getEditorInput();
		IFile file = ResourceUtil.getFile(editorInput);
		if (file != null && file.exists() && BowerConstants.BOWER_JSON.equals(file.getName())) {
			try {
				launchBower(file);
			} catch (CoreException e) {
				BowerPlugin.logError(e);
			}
		}
	}

	protected String getWorkingDirectory(final IResource resource) throws CoreException {
		String workingDir = null;
		if (resource != null && resource.exists()) {
			if (resource.getType() == IResource.FILE && BowerConstants.BOWER_JSON.equals(resource.getName())) {
				workingDir = resource.getParent().getRawLocation().makeAbsolute().toOSString();
			} else if (resource.getType() == IResource.FOLDER) {
				workingDir = resource.getRawLocation().makeAbsolute().toOSString();
			} else if (resource.getType() == IResource.PROJECT) {
				IProject project = (IProject) resource;
				IFile file = project.getFile(BowerConstants.BOWER_JSON);
				if (file.exists()) {
					workingDir = resource.getRawLocation().makeAbsolute().toOSString();
				} else {
					try {
						workingDir = getWorkingDirectory(project);
					} catch (IOException e) {
						BowerPlugin.logError(e);
					}
				}
			}
		}
		return workingDir;
	}
	
	private void launchBower(final IResource resource) throws CoreException {
		new CLI(resource.getProject(), getWorkingDirectory(resource)).execute(getCLICommand());
//		String nodeLocation = NodeExternalUtil.getNodeExecutableLocation();
//		String bowerLocation = BowerUtil.getBowerExecutableLocation();
//		if (nodeLocation == null || nodeLocation.isEmpty()) {
//			NodeExceptionNotifier.nodeLocationNotDefined();
//		} else if (bowerLocation == null || bowerLocation.isEmpty()) {
//			BowerExceptionNotifier.bowerLocationNotDefined();
//		} else {
//			this.setWorkingProject(resource.getProject());
//			launchNodeTool(getWorkingDirectory(resource), nodeLocation, bowerLocation);
//		}
	}

	/**
	 * Detects working directory for bower execution depending on .bowerrc file
	 * @throws CoreException 
	 * @throws UnsupportedEncodingException
	 * @see <a href="http://bower.io/docs/config/">Bower Configuration</a>
	 */
	private String getWorkingDirectory(final IProject project) throws CoreException, UnsupportedEncodingException {
		String workingDir = null;
		IFile bowerrc = BowerUtil.getBowerrc(project);
		if (bowerrc != null) {
			IContainer parent = bowerrc.getParent();
			if (parent.exists() && parent.findMember(BowerConstants.BOWER_JSON) != null) {
				workingDir = parent.getRawLocation().makeAbsolute().toOSString();
			} else {
				String directoryName = BowerUtil.getDirectoryName(bowerrc);
				directoryName = (directoryName != null) ? directoryName : BowerConstants.BOWER_COMPONENTS;
				workingDir = BowerUtil.getBowerWorkingDir(project, directoryName);
			}
		} else {
			// Trying to find bower.json file ignoring "bower_components"
			// (default components directory)
			workingDir = BowerUtil.getBowerWorkingDir(project, BowerConstants.BOWER_COMPONENTS);
		}
		return workingDir;
	}
	
}
