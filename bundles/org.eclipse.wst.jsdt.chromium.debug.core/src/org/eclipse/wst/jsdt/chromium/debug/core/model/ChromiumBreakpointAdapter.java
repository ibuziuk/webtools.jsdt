// Copyright (c) 2010, 2016 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Ilya Buziuk <ilyabuziuk@gmail.com> - https://bugs.eclipse.org/bugs/show_bug.cgi?id=486061

package org.eclipse.wst.jsdt.chromium.debug.core.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.debug.core.model.IBreakpoint;

/**
 * Implements breakpoint adapter for breakpoints provided by
 * org.eclipse.wst.jsdt.chromium.debug.* and org.eclipse.wst.jsdt.debug.*
 */
public class ChromiumBreakpointAdapter {
	// Bug 486061 - Translate breakpoints from .js files to Chromium / Node V8
	private static final String JSDT_BREAKPOINT_MODEL_ID = "org.eclipse.wst.jsdt.debug.model"; //$NON-NLS-1$

	// Storage for mapping JSDT -> Chromium breakpoints
	private static final Map<IBreakpoint, ChromiumLineBreakpoint> JSDT_TO_CHROMIUM_STORAGE = 
			new HashMap<IBreakpoint, ChromiumLineBreakpoint>();

	public static ChromiumLineBreakpoint tryCastBreakpoint(IBreakpoint breakpoint) {
		ChromiumLineBreakpoint chromiumBreakpoint = null;
		if (isChromiumLineBreakPoint(breakpoint)) {
			chromiumBreakpoint = (ChromiumLineBreakpoint) breakpoint;
		} else if (isJSDTBreakpoint(breakpoint)) {
			// Retrieving Chromium breakpoint from the storage
			chromiumBreakpoint = JSDT_TO_CHROMIUM_STORAGE.get(breakpoint);
		}

		return chromiumBreakpoint;
	}

	public static ChromiumLineBreakpoint tryCastBreakpointOnAddition(IBreakpoint breakpoint) {
		ChromiumLineBreakpoint chromiumBreakpoint = null;
		if (isChromiumLineBreakPoint(breakpoint)) {
			chromiumBreakpoint = (ChromiumLineBreakpoint) breakpoint;
		} else if (isJSDTBreakpoint(breakpoint)) {
			chromiumBreakpoint = new ChromiumLineBreakpoint(breakpoint);
			// Adding newly created Chromium breakpoint to the storage
			JSDT_TO_CHROMIUM_STORAGE.put(breakpoint, chromiumBreakpoint);
		}

		return chromiumBreakpoint;
	}

	public static ChromiumLineBreakpoint tryCastBreakpointOnRemoval(IBreakpoint breakpoint) {
		ChromiumLineBreakpoint chromiumBreakpoint = null;
		if (isChromiumLineBreakPoint(breakpoint)) {
			chromiumBreakpoint = (ChromiumLineBreakpoint) breakpoint;
		} else if (isJSDTBreakpoint(breakpoint)) {
			chromiumBreakpoint = JSDT_TO_CHROMIUM_STORAGE.get(breakpoint);
			// JSDT breakpoint was removed - removing Chromium breakpoint from the storage
			JSDT_TO_CHROMIUM_STORAGE.remove(breakpoint);
		}

		return chromiumBreakpoint;
	}

	private static boolean isChromiumLineBreakPoint(IBreakpoint breakpoint) {
		return (breakpoint instanceof ChromiumLineBreakpoint
				&& VProjectWorkspaceBridge.DEBUG_MODEL_ID.equals(breakpoint.getModelIdentifier()));
	}

	private static boolean isJSDTBreakpoint(IBreakpoint breakpoint) {
		return (breakpoint != null && JSDT_BREAKPOINT_MODEL_ID.equals(breakpoint.getModelIdentifier()));
	}

}
