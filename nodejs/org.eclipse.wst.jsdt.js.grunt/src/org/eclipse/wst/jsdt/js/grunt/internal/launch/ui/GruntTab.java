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
package org.eclipse.wst.jsdt.js.grunt.internal.launch.ui;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.js.common.build.system.ui.JSBuildSystemFileSelectionDialog;
import org.eclipse.wst.jsdt.js.common.util.WorkbenchResourceUtil;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.GruntConstants;
import org.eclipse.wst.jsdt.js.grunt.internal.Messages;
import org.eclipse.wst.jsdt.js.grunt.internal.ui.ImageResource;
import org.eclipse.wst.jsdt.js.grunt.internal.util.GruntUtil;


@SuppressWarnings("restriction")
public class GruntTab extends AbstractLaunchConfigurationTab {
	private WidgetListener defaultListener = new WidgetListener();
	private Text buildFileText;
	private Combo tasksCommbo;
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH);
		((GridLayout) comp.getLayout()).verticalSpacing = 0;		
		createBuildFileEditor(comp);
		createTaskComboEditor(comp);
		setControl(comp);
	}
	
	private void createTaskComboEditor(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Tasks:", 1, 1, GridData.FILL_HORIZONTAL);
		tasksCommbo = SWTFactory.createCombo(group, SWT.BORDER|SWT.H_SCROLL, 0, null);
		tasksCommbo.addModifyListener(defaultListener);
	}

	private void createBuildFileEditor(Composite parent) {
		Group group = SWTFactory.createGroup(parent, "Build File:", 2, 1, GridData.FILL_HORIZONTAL);
		buildFileText = SWTFactory.createSingleText(group, 1);
		buildFileText.addModifyListener(defaultListener);
		Button fileButton = createPushButton(group, "Browse", null); 
		fileButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBuildFileButtonSelected();				
			}
		});
	}
	
	@Override
	public boolean isValid(ILaunchConfiguration launchConfig) {
		setErrorMessage(null);
		
		String buildFilePath = buildFileText.getText();
		File file = WorkbenchResourceUtil.getFile(buildFilePath);
		if (file == null) {
			setErrorMessage("Build File does not exist");
			return false;
		}
		
		IFile ifile = WorkbenchResourceUtil.getFileForLocation(file.getAbsolutePath());
		if (ifile == null) {
			return false;
		} else if (!ifile.getName().toLowerCase().equals("gruntfile.js")) {
			setErrorMessage("Not a gruntFile.js");
			return false;
		}
		
		return true;
	}

	@Override
	public String getName() {
		return Messages.GruntLaunchTab_Main;
	}
	
	@Override
	public Image getImage() {
		//DESIGN-735 Need to create icon for JavaScript Build Systems
		return ImageResource.getImage(ImageResource.IMG_GRUNTFILE);
	}
	
	
	private void disableTaskCombo() {
		if (tasksCommbo != null && !tasksCommbo.isDisposed()) {
			updateTasks(null);
			tasksCommbo.setEnabled(false);
		}
	}
	
	private void enableTaskCombo() {
		if (tasksCommbo != null && !tasksCommbo.isDisposed()) {
			tasksCommbo.setEnabled(true);
		}
	}
	
	private void updateTasks(String[] tasks) {
		if (tasksCommbo != null && !tasksCommbo.isDisposed()) {
			tasksCommbo.removeAll();
			if (tasks != null && tasks.length > 0) {
				tasksCommbo.setItems(tasks);
				tasksCommbo.setText(tasks[0]);
			}
		}
	}
	
	protected void handleBuildFileButtonSelected() {
		JSBuildSystemFileSelectionDialog dialog = new JSBuildSystemFileSelectionDialog("Grunt Build File",
				"Select Grunt Build File", new String[] { "js" });
		dialog.open();
		Object result = dialog.getFirstResult();	
		if (result instanceof IFile) {
			buildFileText.setText(((IFile) result).getLocation().toOSString());			
		}
	}
	
	IDialogSettings getDialogBoundsSettings(String id) {
		IDialogSettings settings = GruntPlugin.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(id);
		if (section == null) {
			section = settings.addNewSection(id);
		} 
		return section;
	}
	
	private class WidgetListener implements ModifyListener, SelectionListener {
		
		public void modifyText(ModifyEvent e) {
			updateLaunchConfigurationDialog();
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {/*do nothing*/}
		
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}
	}
	
	private IFile getBuildFile() {
		if (buildFileText != null && !buildFileText.isDisposed()) {
			String path = buildFileText.getText();
			File file = WorkbenchResourceUtil.getFile(path);
			if (file != null) {
				return WorkbenchResourceUtil.getFileForLocation(file.getAbsolutePath());
			}
		}
		return null;
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration lc) {
		String buildFileLocation = "";  //$NON-NLS-1$
		try {
			buildFileLocation = lc.getAttribute(GruntConstants.BUILD_FILE, (String) null);
			buildFileText.setText(buildFileLocation != null ? buildFileLocation : ""); //$NON-NLS-1$
			Set<String> tasks = GruntUtil.getTasks(buildFileLocation);
			if (!tasks.isEmpty()) {
				updateTasks(tasks.toArray(new String[tasks.size()]));
				String task = lc.getAttribute(GruntConstants.COMMAND, (String) null);
				if (task != null && tasks.contains(task)) {
					tasksCommbo.setText(task);
				} else {
					tasksCommbo.setText(tasks.iterator().next());
				}
			}	
		} catch (CoreException e) {
			GruntPlugin.logError(e, e.getMessage());
		}		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy wc) {
		IFile buildFile = getBuildFile();
		if (buildFile != null) {
			IProject project = buildFile.getProject();
			wc.setAttribute(GruntConstants.BUILD_FILE, buildFile.getLocation().toOSString());
			wc.setAttribute(GruntConstants.PROJECT, project.getName());
			wc.setAttribute(GruntConstants.DIR, buildFile.getParent().getLocation().toOSString());
			wc.setAttribute(GruntConstants.COMMAND, tasksCommbo.getText());
		}
	}
	
	private String[] getTasksFromFile(IFile file) throws JavaScriptModelException {
		Set<String> tasks = GruntUtil.getTasks(file.getLocation().toOSString());
		return tasks.toArray(new String[tasks.size()]);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {		
	}
	

}
