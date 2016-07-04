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
package org.eclipse.wst.jsdt.js.node.internal.listeners;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.jsdt.js.node.NodePlugin;
import org.eclipse.wst.jsdt.js.node.internal.actions.PushChangesActionHolder;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class JSChangeListener implements IResourceChangeListener {

	private static final String JS = ".js"; //$NON-NLS-1$

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (IResourceChangeEvent.POST_CHANGE == event.getType()) {
			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						IResource resource = delta.getResource();
						if (resource.getName().endsWith(JS) && resource instanceof IFile) {
							final IFile file = (IFile) resource;
							Display.getDefault().asyncExec(new Runnable() {

								@Override
								public void run() {
									PushChangesActionHolder.get().createRunnable(file)
											.run(Display.getDefault().getActiveShell(), PlatformUI.getWorkbench()
													.getActiveWorkbenchWindow().getActivePage().getActivePart());
								}
							});
						}
						return true;
					}
				});
			} catch (CoreException e) {
				NodePlugin.logError(e);
			}
		}
	}

}
