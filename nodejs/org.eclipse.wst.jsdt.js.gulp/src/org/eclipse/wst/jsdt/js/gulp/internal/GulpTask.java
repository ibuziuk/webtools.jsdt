package org.eclipse.wst.jsdt.js.gulp.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.jsdt.js.common.build.system.ITask;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GulpTask implements ITask {
	private String name;
	private boolean isDefault;
	private IFile buildFile;
	
	public GulpTask(String name, IFile buildFile, boolean isDefault) {
		this.name = name;
		this.buildFile = buildFile;
		this.isDefault = isDefault;
	}
	
	@Override
	public String getName() {
		return name;
	}	

	@Override
	public boolean isDefault() {
		return isDefault;
	}

	@Override
	public IFile getBuildFile() {
		return buildFile;
	}

}
