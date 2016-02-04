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
package org.eclipse.wst.jsdt.js.gulp.internal.util;

import java.io.File;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;
import org.eclipse.wst.jsdt.js.common.build.system.util.ASTUtil;
import org.eclipse.wst.jsdt.js.common.util.WorkbenchResourceUtil;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GulpUtil {
	
	public static Set<String> getTasks(String pathToFile) throws JavaScriptModelException {
		File file = WorkbenchResourceUtil.getFile(pathToFile);
		if (file != null) {
			IFile ifile = WorkbenchResourceUtil.getFileForLocation(file.getAbsolutePath());
			if (ifile != null) {
				JavaScriptUnit unit = ASTUtil.getJavaScriptUnit(ifile);
				GulpVisitor visitor = new GulpVisitor();
				unit.accept(visitor);
				return visitor.getTasks();
			}
		}
		return Collections.emptySet();
	}
}
