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
package org.eclipse.wst.jsdt.js.grunt.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.jsdt.js.common.build.system.ITask;

/**
 * @author "Ilya Buziuk (ibuziuk)"
 */
public class GruntLabelProvider extends LabelProvider implements IStyledLabelProvider, IColorProvider{
	
	@Override
	public Image getImage(Object element) {
		//DESIGN-735 Need to create icon for JavaScript Build Systems
		if (element instanceof ITask) {
			return ImageResource.getImage(ImageResource.IMG_GRUNTFILE);
		} else if (element instanceof IFile) {
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
		if (object instanceof ITask) {
			return new StyledString(((ITask) object).getName());
		}
		return null;
	}

}
