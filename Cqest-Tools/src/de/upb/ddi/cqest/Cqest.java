package de.upb.ddi.cqest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Cqest extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	private JTabbedPane jTPmain;

	public Cqest() throws HeadlessException {
		super("Cqest - ConQuest Version 2 Tools");
		this.setBounds(100, 100, 450, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		this.setLayout(new BorderLayout());
		
		jTPmain = new JTabbedPane() {
			private static final long serialVersionUID = 1L;

			@Override
			public void setSelectedIndex( int index ) {
				super.setSelectedIndex(index);
				Cqest.this.pack();
			}

			@Override
			public void setSelectedComponent( Component c ) {
				super.setSelectedComponent(c);
				Cqest.this.pack();
			}
			
		};
		this.add(jTPmain, BorderLayout.CENTER);

		jTPmain.add("Csv", new CqestCsvPanel());
		jTPmain.add("PVs", new CqestPVsPanel());
		//jTPmain.add("Params", new CqestParamPanel());
	}


	public static void main( String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cqest window = new Cqest();
					window.initialize();
					window.setVisible(true);
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
		});
	}

}
