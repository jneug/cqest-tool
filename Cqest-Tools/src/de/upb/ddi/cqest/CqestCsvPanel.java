package de.upb.ddi.cqest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.upb.ddi.cqest.model.CqestItem;
import de.upb.ddi.cqest.model.CqestModel;

public class CqestCsvPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2421845764781339673L;

	private JTextField jtfLabelsFile, jtfItanalFile, jtfShowFile,
			jtfOutputFile;

	private JLabel jlStatus;

	private JCheckBox jcbFilterData, jcbFormat;

	public CqestCsvPanel() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		JLabel jlLabelsFile = new JLabel("Labels file:");
		JLabel jlItanalFile = new JLabel("Itanal file:");
		JLabel jlShowFile = new JLabel("Show file:");
		JLabel jlOutputFile = new JLabel("Output to:");

		jtfLabelsFile = new JTextField(40);
		jtfItanalFile = new JTextField(40);
		jtfShowFile = new JTextField(40);
		jtfOutputFile = new JTextField(40);

		CqestBrowseButton jbBrowse1 = new CqestBrowseButton(jtfLabelsFile);
		CqestBrowseButton jbBrowse2 = new CqestBrowseButton(jtfItanalFile);
		CqestBrowseButton jbBrowse3 = new CqestBrowseButton(jtfShowFile);
		CqestBrowseButton jbBrowse4 = new CqestBrowseButton(jtfOutputFile);

		JButton jbParse = new JButton("Parse");
		jbParse.addActionListener(this);

		jlStatus = new JLabel(" ");

		jcbFilterData = new JCheckBox("Filter empty items", true);
		jcbFormat = new JCheckBox("Write in CSV2 format", true);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(jlLabelsFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jlItanalFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jlShowFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jlOutputFile,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGroup(layout.createParallelGroup()
								.addComponent(jtfLabelsFile)
								.addComponent(jtfItanalFile)
								.addComponent(jtfShowFile)
								.addComponent(jtfOutputFile)
								.addComponent(jcbFilterData)
								.addComponent(jcbFormat)
								.addComponent(jbParse)
								.addComponent(jlStatus))
						.addGroup(layout.createParallelGroup()
								.addComponent(jbBrowse1)
								.addComponent(jbBrowse2)
								.addComponent(jbBrowse3)
								.addComponent(jbBrowse4))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlLabelsFile)
								.addComponent(jtfLabelsFile)
								.addComponent(jbBrowse1))
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlItanalFile)
								.addComponent(jtfItanalFile)
								.addComponent(jbBrowse2))
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlShowFile)
								.addComponent(jtfShowFile)
								.addComponent(jbBrowse3))
						.addGroup(layout.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
								.addComponent(jlOutputFile)
								.addComponent(jtfOutputFile)
								.addComponent(jbBrowse4))
						.addComponent(jcbFilterData)
						.addComponent(jcbFormat)
						.addComponent(jbParse)
						.addComponent(jlStatus)

				);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		CqestModel model = new CqestModel();
		model.labelsFile = jtfLabelsFile.getText().trim();
		model.showFile = jtfShowFile.getText().trim();
		model.itanalFile = jtfItanalFile.getText().trim();

		String out = jtfOutputFile.getText().trim();

		try {
			new CqestLabelsParser().parse(model);
			new CqestShowParser().parse(model);
			new CqestItanalParser().parse(model);

			CqestCsvWriter writer = new CqestCsvWriter(out);
			if( jcbFormat.isSelected() ) {
				writer.setFormat(CqestCsvWriter.CSV2);
			}
			if( jcbFilterData.isSelected() ) {
				writer.setFilter(notEmptyFilter);
			}
			writer.write(model);

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

	private final CqestItemFilter notEmptyFilter = new CqestItemFilter() {
		@Override
		public boolean isAccepted( CqestItem item ) {
			return test(item.estimate, item.error, item.mnsq, item.ci0,
					item.ci1, item.t, item.wmnsq, item.wci0, item.wci1,
					item.wt, item.cases, item.discrimination);
		}

		private boolean test( double... values ) {
			for( double f: values ) {
				if( f != 0 )
					return true;
			}
			return false;
		}
	};

}
