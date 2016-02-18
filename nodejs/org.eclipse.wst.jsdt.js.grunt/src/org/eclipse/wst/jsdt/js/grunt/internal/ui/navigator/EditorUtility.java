package org.eclipse.wst.jsdt.js.grunt.internal.ui.navigator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.jsdt.js.common.build.system.ITask;
import org.eclipse.wst.jsdt.js.common.build.system.Location;
import org.eclipse.wst.jsdt.js.grunt.GruntPlugin;

public class EditorUtility {

	/**
	 * Tests if a is currently shown in an editor
	 * 
	 * @return the IEditorPart if shown, null if element is not open in an
	 *         editor
	 */
	public static IEditorPart isOpenInEditor(ITask task) {
		IEditorInput input = new FileEditorInput(task.getBuildFile());
		IWorkbenchPage p = GruntPlugin.getActivePage();
		if (p != null) {
			return p.findEditor(input);
		}
		return null;
	}


	public static void revealInEditor(IEditorPart editorPart,
			ITask task) {
		revealInEditor(editorPart, task, null);
	}

	public static void revealInEditor(IEditorPart editorPart, ITask task, IWorkbenchPage page) {
		if (task == null)
			return;

		ITextEditor textEditor = null;
		if (editorPart instanceof ITextEditor)
			textEditor = (ITextEditor) editorPart;
		else if (editorPart instanceof IAdaptable)
			textEditor = (ITextEditor) editorPart.getAdapter(ITextEditor.class);
		if (textEditor != null) {
			IDocument document = textEditor.getDocumentProvider().getDocument(editorPart.getEditorInput());
			Location location = task.getLocation();
			if (location != null) {
				int start = location.getStart();
				if (start > 0) {
					int length = location.getLength();
					textEditor.selectAndReveal(start, length);
					if (page != null) {
						page.activate(editorPart);
					}
				}
			}
		} /*else {
			Location location = element.getLocation(null);
			if (location != null) {
				IFile fileResource = element.getBuildFile().getBuildFileResource();
				int start = location.getStart();
				try {
					IMarker marker = fileResource.createMarker("org.eclipse.core.resources.textmarker");
					marker.setAttribute("lineNumber", start);
					editorPart = IDE.openEditor(page, marker, true);
					marker.delete();
				} catch (CoreException e) {
					GruntPlugin.logError(e);
				}
			}
		}*/

	}
}
