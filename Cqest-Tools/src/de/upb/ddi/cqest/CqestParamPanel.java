package de.upb.ddi.cqest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CqestParamPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5949500060217271178L;
	
	private JTextField jtfParamFile, jtfOutputFile;

	public CqestParamPanel() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		JLabel jlParamFile = new JLabel("Parameter File:");
		JLabel jlOutputFile = new JLabel("Output to:");

		jtfParamFile = new JTextField(40);
		jtfOutputFile = new JTextField(40);
		
		JButton jbParse = new JButton("Parse");
		jbParse.addActionListener(this);

		CqestBrowseButton jbBrowse1 = new CqestBrowseButton(jtfParamFile);
		CqestBrowseButton jbBrowse2 = new CqestBrowseButton(jtfOutputFile);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(jlParamFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jlOutputFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGroup(layout.createParallelGroup()
								.addComponent(jtfParamFile)
								.addComponent(jtfOutputFile)
								.addComponent(jbParse))
						.addGroup(layout.createParallelGroup()
								.addComponent(jbBrowse1)
								.addComponent(jbBrowse2))
			);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(jlParamFile)
										.addComponent(jtfParamFile)
										.addComponent(jbBrowse1))
						.addGroup(layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(jlOutputFile)
										.addComponent(jtfOutputFile)
										.addComponent(jbBrowse2))
						.addComponent(jbParse)
						
			);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		CqestParamParser parser = new CqestParamParser(jtfParamFile.getText(), jtfOutputFile.getText());
		parser.parse();
	}

}
