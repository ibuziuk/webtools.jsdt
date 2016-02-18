package org.eclipse.wst.jsdt.js.grunt.internal.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.wst.jsdt.js.common.build.system.ITask;

public class TaskLinkHelper implements ILinkHelper {

	@Override
	public void activateEditor(IWorkbenchPage page, IStructuredSelection selection) {
		if (selection == null || selection.isEmpty())
			return;
		Object element = selection.getFirstElement();
		if (element instanceof ITask) {
			ITask node = (ITask) element;
			IEditorPart part = EditorUtility.isOpenInEditor(node);
			if (part != null) {
				page.bringToTop(part);
				if (element instanceof ITask) {
					EditorUtility.revealInEditor(part, node);
				}
			}
		}
	}
		
	@Override
	public IStructuredSelection findSelection(IEditorInput arg0) {
		return StructuredSelection.EMPTY;
	}


}
