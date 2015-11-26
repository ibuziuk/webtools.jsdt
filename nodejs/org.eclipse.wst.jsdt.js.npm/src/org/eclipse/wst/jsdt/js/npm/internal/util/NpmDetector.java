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
package org.eclipse.wst.jsdt.js.npm.internal.util;

import static org.eclipse.wst.jsdt.js.npm.internal.NpmConstants.*;

import java.io.File;

import org.eclipse.wst.jsdt.js.node.util.PlatformUtil;

/**
 * @author Ilya Buziuk (ibuziuk)
 */
public final class NpmDetector {
	private static final String USR_LOCAL_BIN = "/usr/local/bin"; //$NON-NLS-1$
	private static final String USR_BIN = "/usr/bin"; //$NON-NLS-1$

	private NpmDetector() {
	}

	public static String detectNpm() {
		String npmLocation = null;
		if (PlatformUtil.isWindows()) {
			npmLocation = detectNpmFromPath();
			if (npmLocation != null) {
				String separator = File.separator;
				if (!npmLocation.endsWith(separator)) {
					npmLocation = npmLocation + separator;
				}

				File npmHome = new File(npmLocation, NODE_MODULES + separator + NPM + separator + BIN);
				if (isDetected(npmHome, NPM_CLI_JS)) {
					npmLocation = npmHome.getAbsolutePath();
				}
			}
		} else {
			// Try to detect in "usr/local/bin" and "usr/bin" for Mac & Linux
			File usrLocalBin = new File(USR_LOCAL_BIN);
			if (isDetected(usrLocalBin, NPM)) {
				npmLocation = usrLocalBin.getAbsolutePath();
			} else {
				File usrBin = new File(USR_BIN);
				if (isDetected(usrBin, NPM)) {
					npmLocation = usrBin.getAbsolutePath();
				}
			}
		}
		return npmLocation;
	}
	
	private static boolean isDetected(File parent, String name) {
		if (parent != null && parent.exists()) {
			File file = new File(parent, name);
			if (file != null && file.exists()) {
				return true;
			}
		}
		return false;
	}
	
	private static String detectNpmFromPath() {
		return detectFromPath(File.separator + NPM);
	}
		
	private static String detectFromPath(final String pattern) {
		String location = null;
		String path = getPath();
		String spliter = getPathSpliter();
		if (path != null) {
			String[] pathElements = path.split(spliter);
			for (String element : pathElements) {
				if (element.contains(pattern)) {
					location = element;
					break;
				}
			}
		}
		return location;
	}
	
	private static String getPath() {
		return System.getenv(PATH);
	}
	
	private static String getPathSpliter() {
		return (PlatformUtil.isWindows()) ? ";" : ":"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
