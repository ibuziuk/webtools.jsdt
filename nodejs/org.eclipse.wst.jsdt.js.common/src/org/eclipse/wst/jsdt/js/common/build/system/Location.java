package org.eclipse.wst.jsdt.js.common.build.system;

public class Location {
	private int start;
	private int length;

	public Location(int start, int length) {
		this.start = start;
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}
}
