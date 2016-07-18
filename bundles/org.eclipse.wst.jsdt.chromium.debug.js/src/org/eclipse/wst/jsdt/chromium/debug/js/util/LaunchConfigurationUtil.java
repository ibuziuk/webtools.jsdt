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
package org.eclipse.wst.jsdt.chromium.debug.js.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IProcess;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public final class LaunchConfigurationUtil {
	
	private static final String ID_CHROME_PROCESS_TYPE = "Chrome / Chromium"; // TODO better place for constants

	private LaunchConfigurationUtil(){
	}
	
	public static Map<String, String> getDefaultAttributes() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(IProcess.ATTR_PROCESS_TYPE, ID_CHROME_PROCESS_TYPE);
		return map;
	}

}
