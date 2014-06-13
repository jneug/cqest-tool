package de.upb.ddi.cqest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.upb.ddi.cqest.model.CqestModel;
import de.upb.ddi.cqest.model.CqestPerson;

public class CqestPVsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -2390608177366625636L;

	private JTextField jtfPVsFile, jtfOutputFile;

	private JLabel jlStatus;

	public CqestPVsPanel() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		JLabel jlPVsFile = new JLabel("PVs file:");
		JLabel jlOutputFile = new JLabel("Output to:");

		jtfPVsFile = new JTextField(40);
		jtfOutputFile = new JTextField(40);

		CqestBrowseButton jbBrowse1 = new CqestBrowseButton(jtfPVsFile);
		CqestBrowseButton jbBrowse4 = new CqestBrowseButton(jtfOutputFile);

		JButton jbParse = new JButton("Parse");
		jbParse.addActionListener(this);

		jlStatus = new JLabel(" ");

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(jlPVsFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jlOutputFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGroup(layout.createParallelGroup()
								.addComponent(jtfPVsFile)
								.addComponent(jtfOutputFile)
								.addComponent(jbParse)
								.addComponent(jlStatus))
						.addGroup(layout.createParallelGroup()
								.addComponent(jbBrowse1)
								.addComponent(jbBrowse4))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlPVsFile)
								.addComponent(jtfPVsFile)
								.addComponent(jbBrowse1))
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlOutputFile)
								.addComponent(jtfOutputFile)
								.addComponent(jbBrowse4))
						.addComponent(jbParse)
						.addComponent(jlStatus)

				);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		CqestModel model = new CqestModel();
		model.pvsFile = jtfPVsFile.getText().trim();

		String out = jtfOutputFile.getText().trim();

		try {
			new CqestPVsParser().parse(model);

			System.out.println("Case;pv1;pv2;pv3;pv4;pv5;eap;sd");
			for( CqestPerson per: model.persons.values() ) {
				System.out.println(per.id+";"+per.pvs[0]+";"+per.pvs[1]+";"+per.pvs[2]+";"+per.pvs[3]+";"+per.pvs[4]+";"+per.eap+";"+per.sd);
			}
				
			jlStatus.setForeground(Color.GREEN);
			jlStatus.setText("Done!");
			System.out.println("Done!");
		} catch( Exception ex ) {
			jlStatus.setForeground(Color.RED);
			jlStatus.setText("Failed: " + ex.getMessage());
			System.err.println("Failed!");
			ex.printStackTrace();
		}
	}

}
