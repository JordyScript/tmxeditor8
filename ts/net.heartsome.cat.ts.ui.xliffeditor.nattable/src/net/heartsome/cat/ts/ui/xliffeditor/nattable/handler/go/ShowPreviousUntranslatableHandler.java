package net.heartsome.cat.ts.ui.xliffeditor.nattable.handler.go;

import java.util.Arrays;

import net.heartsome.cat.ts.core.file.XLFHandler;
import net.heartsome.cat.ts.ui.xliffeditor.nattable.editor.XLIFFEditorImplWithNatTable;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 转到上一不可翻译文本段(此类未使用，因此未做国际化)
 * @author weachy
 * @version
 * @since JDK1.5
 */
public class ShowPreviousUntranslatableHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (!(editor instanceof XLIFFEditorImplWithNatTable)) {
			return null;
		}
		XLIFFEditorImplWithNatTable xliffEditor = (XLIFFEditorImplWithNatTable) editor;
		int[] selectedRows = xliffEditor.getSelectedRows();
		if (selectedRows.length < 1) {
			return null;
		}
		Arrays.sort(selectedRows);
		int firstSelectedRow = selectedRows[0];
		XLFHandler handler = xliffEditor.getXLFHandler();

		int row = handler.getPreviousUntranslatableSegmentIndex(firstSelectedRow);
		if (row != -1) {
			xliffEditor.jumpToRow(row);
		} else {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "", "不存在上一不可翻译文本段。");
		}

		return null;
	}

}
