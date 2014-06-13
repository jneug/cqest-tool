package de.upb.ddi.cqest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class CqestBrowseButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 6580481105928636038L;

	
	private JTextField jtfPath;
	
	public CqestBrowseButton( JTextField jtfPath ) {
		super("Browse");
		this.addActionListener(this);
		
		this.jtfPath = jtfPath;
		this.jtfPath.setDropTarget(new FilePathDropTarget(this.jtfPath));
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		JFileChooser jfcBrowser;
		
		File dir = new File(this.jtfPath.getText().trim());
		if( dir.isDirectory() ) {
			jfcBrowser = new JFileChooser(dir);
		} else if( dir.getParentFile() != null && dir.getParentFile().exists() )
			jfcBrowser = new JFileChooser(dir.getParentFile());
		else
			jfcBrowser = new JFileChooser();
		
		jfcBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int returnVal = jfcBrowser
				.showOpenDialog(this.getParent());
		if( returnVal == JFileChooser.APPROVE_OPTION ) {
			String path = jfcBrowser.getSelectedFile()
					.getAbsolutePath();
			this.jtfPath.setText(path);
		}
	}
	
}
