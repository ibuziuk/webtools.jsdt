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
package org.eclipse.wst.jsdt.js.common.build.system;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FileAdapterFactory implements IAdapterFactory {

	private static Class[] PROPERTIES = new Class[] { IResource.class };

	@Override
	public Object getAdapter(Object element, Class key) {
		ITask node = getJSBuildFileNode(element);
		if (node == null) {
			return null;
		}
		if (IResource.class.equals(key)) {
			return getResource(node);
		}
		return null;
	}

	private Object getResource(ITask node) {
		return node.getBuildFile();
	}

	private ITask getJSBuildFileNode(Object element) {
		if (element instanceof ITask) {
			return (ITask) element;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return PROPERTIES;
	}

}