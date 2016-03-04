// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html

package org.eclipse.wst.jsdt.chromium.debug.core.model;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.wst.jsdt.chromium.debug.core.ChromiumDebugPlugin;

/**
 * Implements breakpoint adapter for breakpoints provided by
 * org.eclipse.wst.jsdt.chromium.debug.*
 */
public class ChromiumBreakpointAdapter {
	// Bug 486061 - Translate breakpoints from .js files to Chromium / Node V8
	private static final String JSDT_BREAKPOINT_MODEL_ID = "org.eclipse.wst.jsdt.debug.model"; //$NON-NLS-1$

	public static ChromiumLineBreakpoint tryCastBreakpoint(IBreakpoint breakpoint) {
		if (!supportsBreakpoint(breakpoint)) {
			return null;
		}
		if (breakpoint instanceof ChromiumLineBreakpoint) {
			return (ChromiumLineBreakpoint) breakpoint;
		}

		try {
			return tryCastJSDTBreakpoint(breakpoint);
		} catch (CoreException e) {
			ChromiumDebugPlugin.logError(e.getMessage(), e);
		}

		return null;
	}

	private static boolean supportsBreakpoint(IBreakpoint breakpoint) {
		String modelId = breakpoint.getModelIdentifier();
		return (VProjectWorkspaceBridge.DEBUG_MODEL_ID.equals(modelId) || JSDT_BREAKPOINT_MODEL_ID.equals(modelId));
	}

	private static ChromiumLineBreakpoint tryCastJSDTBreakpoint(IBreakpoint breakpoint) throws CoreException {
		IMarker marker = breakpoint.getMarker();
		Object line = marker.getAttribute(IMarker.LINE_NUMBER);
		IResource resource = marker.getResource();
		ChromiumLineBreakpoint chromiumLineBreakpoint = new ChromiumLineBreakpoint(resource, (int) line, VProjectWorkspaceBridge.DEBUG_MODEL_ID);
	    DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(chromiumLineBreakpoint);
	    DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
	    return chromiumLineBreakpoint;
	}

}
