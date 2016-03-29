// Copyright (c) 2009 The Chromium Authors. All rights reserved.
// This program and the accompanying materials are made available
// under the terms of the Eclipse Public License v1.0 which accompanies
// this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html

package org.eclipse.wst.jsdt.chromium.debug.ui.launcher;

import org.eclipse.wst.jsdt.chromium.debug.core.model.BreakpointSynchronizer;
import org.eclipse.wst.jsdt.chromium.debug.core.model.JavascriptVmEmbedder;
import org.eclipse.wst.jsdt.chromium.debug.core.model.JavascriptVmEmbedderFactory;
import org.eclipse.wst.jsdt.chromium.debug.core.model.NamedConnectionLoggerFactory;
import org.eclipse.wst.jsdt.chromium.ConnectionLogger;
import org.eclipse.debug.core.ILaunch;

public class StandaloneV8LaunchType extends LaunchTypeBase {
  @Override
  protected JavascriptVmEmbedder.ConnectionToRemote createConnectionToRemote(String host, int port,
      final ILaunch launch, boolean addConsoleLogger, boolean hideVirtualFilesystem) {
    NamedConnectionLoggerFactory consoleFactory;
    if (addConsoleLogger) {
      consoleFactory = new NamedConnectionLoggerFactory() {
        public ConnectionLogger createLogger(String title) {
          return LaunchTypeBase.createConsoleAndLogger(launch, false, title);
        }
      };
    } else {
      consoleFactory = NO_CONNECTION_LOGGER_FACTORY;
    }
    return JavascriptVmEmbedderFactory.connectToStandalone(host, port, consoleFactory, hideVirtualFilesystem);
  }

  @Override
  protected BreakpointSynchronizer.Direction getPresetSyncDirection() {
    return null;
  }
}