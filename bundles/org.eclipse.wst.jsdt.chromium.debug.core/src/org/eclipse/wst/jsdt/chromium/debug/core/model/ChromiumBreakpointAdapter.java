// Copyright (c) 2010 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html

package org.eclipse.wst.jsdt.chromium.debug.core.model;


import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;

/**
 * Implements breakpoint adapter for breakpoints provided by org.eclipse.wst.jsdt.chromium.debug.*
 */
public class ChromiumBreakpointAdapter {
  public static ChromiumLineBreakpoint tryCastBreakpoint(IBreakpoint breakpoint) {
    if (!supportsBreakpoint(breakpoint)) {
      return null;
    }
    if (breakpoint instanceof ChromiumLineBreakpoint == false) {
			try {
				IMarker marker = breakpoint.getMarker();
				IResource resource = marker.getResource();
				Object line = marker.getAttribute(IMarker.LINE_NUMBER);
				Object id = marker.getAttribute(IBreakpoint.ID);
				return new ChromiumLineBreakpoint(resource, (int) line, id.toString());
			} catch (CoreException e) {
				return null;
			}
//    	return null;
    }
    return (ChromiumLineBreakpoint) breakpoint;
  }

  private static boolean supportsBreakpoint(IBreakpoint breakpoint) {
    return VProjectWorkspaceBridge.DEBUG_MODEL_ID.equals(breakpoint.getModelIdentifier());
  }
}
