package de.upb.ddi.cqest;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JTextField;

public class FilePathDropTarget extends DropTarget {
	
	private static final long serialVersionUID = -7329704168700204763L;
	
	private JTextField jtf;
	
	public FilePathDropTarget(JTextField jtf) {
		this.jtf = jtf;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void drop( DropTargetDropEvent evt ) {
		try {
			evt.acceptDrop(DnDConstants.ACTION_COPY);
			List<File> droppedFiles = (List<File>)
					evt.getTransferable().getTransferData(
							DataFlavor.javaFileListFlavor);
			if( !droppedFiles.isEmpty() )
				jtf.setText(droppedFiles.get(0)
						.getAbsolutePath());
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
