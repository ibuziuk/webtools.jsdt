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
package org.eclipse.wst.jsdt.js.npm;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wst.jsdt.js.npm.internal.preference.NpmPreferenceHolder;
import org.osgi.framework.BundleContext;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class NpmPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.jboss.tools.jst.js.npm"; //$NON-NLS-1$

	// The shared instance
	private static NpmPlugin plugin;

	public NpmPlugin() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		IPreferenceStore store = NpmPlugin.getDefault().getPreferenceStore();
		NpmPreferenceHolder.setStore(store);
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static NpmPlugin getDefault() {
		return plugin;
	}
	
	public static void logError(Throwable e) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}
	
	public static void logError(Throwable e, String message) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, e));
	}
	
	public static void logInfo(String info) {
		getDefault().getLog().log(new Status(IStatus.INFO, PLUGIN_ID, info));
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}
