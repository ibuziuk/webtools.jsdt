package org.eclipse.wst.jsdt.js.gulp.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.wst.jsdt.js.common.build.system.ITask;
import org.eclipse.wst.jsdt.js.common.build.system.Location;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GulpTask implements ITask {
	private String name;
	private boolean isDefault;
	private IFile buildFile;
	private Location location;
	
	public GulpTask(String name, IFile buildFile, boolean isDefault, Location location) {
		this.name = name;
		this.buildFile = buildFile;
		this.isDefault = isDefault;
		this.location = location;
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

	@Override
	public Location getLocation() {
		return location;
	}

}
