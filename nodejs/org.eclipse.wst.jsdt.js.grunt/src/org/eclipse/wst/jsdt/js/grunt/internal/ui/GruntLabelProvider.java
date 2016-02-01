package org.eclipse.wst.jsdt.js.grunt.internal.ui;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.jsdt.js.common.build.system.Task;

public class GruntLabelProvider extends LabelProvider implements IStyledLabelProvider, IColorProvider{
	
	@Override
	public Image getImage(Object element) {
		//DESIGN-735 Need to create icon for JavaScript Build Systems
		if (element instanceof Task) {
			return ImageResource.getImage(ImageResource.IMG_GRUNTFILE);
		}
		return super.getImage(element);
	}

	@Override
	public Color getBackground(Object arg0) {
		return Display.getDefault().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	}

	@Override
	public Color getForeground(Object arg0) {
		return Display.getDefault().getSystemColor(SWT.COLOR_LIST_FOREGROUND);
	}

	@Override
	public StyledString getStyledText(Object object) {
		if (object instanceof Task) {
			return new StyledString(((Task) object).getName());
		}
		return null;
	}

}
