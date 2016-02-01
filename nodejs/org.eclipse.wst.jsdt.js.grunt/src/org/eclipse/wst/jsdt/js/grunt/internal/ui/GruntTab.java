package org.eclipse.wst.jsdt.js.grunt.internal.ui;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;
import org.eclipse.wst.jsdt.js.common.build.system.ui.JSBuildSystemFileSelectionDialog;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;
import org.eclipse.wst.jsdt.js.grunt.internal.util.GruntUtil;


@SuppressWarnings("restriction")
public class GruntTab extends AbstractLaunchConfigurationTab {
	private WidgetListener defaultListener = new WidgetListener();

	
	private Text buildFileText;
	
	
	@Override
	public void createControl(Composite parent) {
		Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH);
		((GridLayout) comp.getLayout()).verticalSpacing = 0;		
		createBuildFileEditor(comp);
		setControl(comp);
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
	public String getName() {
		return "Main"; //$NON-NLS-1$
	}
	
	@Override
	public Image getImage() {
		return ImageResource.getImage(ImageResource.IMG_GRUNTFILE);
	}
	
	protected void handleBuildFileButtonSelected() {
		
		JSBuildSystemFileSelectionDialog dialog = new JSBuildSystemFileSelectionDialog("Grunt Build File", "Select Grunt Build File", new String[]{"js"});
	
		dialog.open();
		Object result = dialog.getFirstResult();	
		if (result instanceof IFile) {
			buildFileText.setText(((IFile) result).getLocation().toOSString());
			try {
				List<String> tasks = GruntUtil.getTasks(((IFile) result).getLocation().toOSString());
				for (String task : tasks) {
					System.out.println(task);
				}
			} catch (JavaScriptModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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


	@Override
	public void initializeFrom(ILaunchConfiguration arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy wc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy wc) {
		// TODO Auto-generated method stub
		
	}
	

}
