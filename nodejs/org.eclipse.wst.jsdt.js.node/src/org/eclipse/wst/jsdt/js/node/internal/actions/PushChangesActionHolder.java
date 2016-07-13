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
package org.eclipse.wst.jsdt.js.node.internal.actions;

import org.eclipse.wst.jsdt.chromium.debug.ui.actions.PushChangesAction;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class PushChangesActionHolder {

	public static PushChangesAction get() {
		return INSTANCE;
	}

	private static final PushChangesAction INSTANCE = new PushChangesAction();
}
