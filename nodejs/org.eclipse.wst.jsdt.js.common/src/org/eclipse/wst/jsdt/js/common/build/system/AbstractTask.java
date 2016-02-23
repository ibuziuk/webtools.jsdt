package org.eclipse.wst.jsdt.js.common.build.system;

import org.eclipse.core.resources.IFile;

public abstract class AbstractTask implements ITask {

	private String name;
	private boolean isDefault;
	private IFile buildFile;
	private Location location;

	public AbstractTask(String name, IFile buildFile, boolean isDefault, Location location) {
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
