package org.eclipse.wst.jsdt.js.grunt.internal.util;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.core.dom.JavaScriptUnit;
import org.eclipse.wst.jsdt.js.common.build.system.util.ASTUtil;
import org.eclipse.wst.jsdt.js.common.util.WorkbenchResourceUtil;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GruntUtil {
	
	public static List<String> getTasks(String pathToFile) throws JavaScriptModelException {
		File file = WorkbenchResourceUtil.getFile(pathToFile);
		if (file != null) {
			IFile ifile = WorkbenchResourceUtil.getFileForLocation(file.getAbsolutePath());
			if (ifile != null) {
				JavaScriptUnit unit = ASTUtil.getJavaScriptUnit(ifile);
				GruntVisitor visitor = new GruntVisitor();
				unit.accept(visitor);
				return visitor.getTasks();
			}
		}
		return Collections.emptyList();
	}
}
