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
package org.eclipse.wst.jsdt.js.common.build.system.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.jsdt.core.IJavaScriptUnit;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.dom.AST;
import org.eclipse.wst.jsdt.core.dom.ASTParser;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class ASTUtil {
	public static JavaScriptUnit getJavaScriptUnit(IFile file) throws JavaScriptModelException {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(getCompilationUnit(file));
		parser.setResolveBindings(true);
		parser.setStatementsRecovery(true);
		parser.setBindingsRecovery(true);
		return (JavaScriptUnit) parser.createAST(null);
	}
	
	private static IJavaScriptUnit getCompilationUnit(IFile file) {
		return (IJavaScriptUnit) JavaScriptCore.create(file);
	}
	
}
