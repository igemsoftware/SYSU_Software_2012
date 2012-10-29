package org.sysu.sbol;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

/**
 * 
 * @ClassName: fileFrame
 * @Description: Show a frame for the user to convert an xml to sbol
 * @author Guo Jiexin
 * @date Oct 25, 2012 6:58:54 PM
 * 
 */
public class fileFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7452063081204792575L;
	private JPanel contentPane;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			// java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (InstantiationException ex) {
			// java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (IllegalAccessException ex) {
			// java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			// java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			// null, ex);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fileFrame frame = new fileFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String xml = "";
	public String sbol = "";
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public fileFrame() {
		setTitle("Sbol Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton xmlOpenButton = new JButton("open xml");
		xmlOpenButton
				.setToolTipText("To open the xml that you want to change to sbol format");
		xmlOpenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				try {
					chooser.setCurrentDirectory(new File(HistoryFile
							.getPathFromHistoryFile("history.txt")));
				} catch (IOException ex) {

				}
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".xml");
					}

					public String getDescription() {
						return "xml file(*.xml)";
					}
				});
				int returnVal = chooser.showOpenDialog(fileFrame.this);// .showOpenDialog(fileFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: "
							+ chooser.getSelectedFile().getName());

					fileFrame.this.xml = chooser.getSelectedFile()
							.getAbsolutePath();
					fileFrame.this.getLblNewLabel().setText(
							chooser.getSelectedFile().getName());
					fileFrame.this.getLblNewLabel().setToolTipText(
							chooser.getSelectedFile().getAbsolutePath());
					try {
						HistoryFile.setPathToHistoryFile(chooser
								.getSelectedFile().getPath(), "history.txt");
					} catch (IOException ex) {

					}
				}
			}
		});
		xmlOpenButton.setBounds(14, 32, 113, 27);
		panel.add(xmlOpenButton);

		JButton sbolSelectButton = new JButton("new sbol");
		sbolSelectButton
				.setToolTipText("To select the file that you want to create as a sbol format file");
		sbolSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				try {
					chooser.setCurrentDirectory(new File(HistoryFile
							.getPathFromHistoryFile("history.txt")));
				} catch (IOException ex) {

				}
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return true;
					}

					public String getDescription() {
						return "sbol file(*.sbol)";
					}
				});
				int returnVal = chooser.showSaveDialog(fileFrame.this);// .showOpenDialog(fileFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile().exists()) {
						int n = JOptionPane
								.showConfirmDialog(
										null,
										"The file you selected is existed,would you like to overwrite it?",
										"File already exists",
										JOptionPane.YES_NO_OPTION);
						switch (n) {
						case JOptionPane.YES_OPTION:
							break;
						case JOptionPane.NO_OPTION:
							return;
						}
					}
					if (!chooser.getSelectedFile().getAbsolutePath()
							.endsWith(".sbol")) {
						fileFrame.this.sbol = chooser.getSelectedFile()
								.getAbsolutePath() + ".sbol";
						fileFrame.this.getLblNewLabel_1().setText(
								chooser.getSelectedFile().getName() + ".sbol");
					} else {
						fileFrame.this.sbol = chooser.getSelectedFile()
								.getAbsolutePath();
						fileFrame.this.getLblNewLabel_1().setText(
								chooser.getSelectedFile().getName());

					}
					fileFrame.this.getLblNewLabel_1().setToolTipText(
							chooser.getSelectedFile().getAbsolutePath());
					try {
						HistoryFile.setPathToHistoryFile(chooser
								.getSelectedFile().getPath(), "history.txt");
					} catch (IOException ex) {

					}
				}
			}
		});
		sbolSelectButton.setBounds(14, 82, 113, 27);
		panel.add(sbolSelectButton);

		lblNewLabel = new JLabel("xml:");
		lblNewLabel.setBounds(141, 34, 93, 23);
		panel.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("sbol:");
		lblNewLabel_1.setBounds(141, 86, 93, 23);
		panel.add(lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("GO!");
		btnNewButton_2.setToolTipText("To start creating your sbol file.");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileFrame.this.xml.isEmpty()
						|| fileFrame.this.sbol.isEmpty()) {
					JOptionPane
							.showMessageDialog(
									fileFrame.this,
									"You hava to open an xml and select your new sbol file!",
									"Warning", JOptionPane.WARNING_MESSAGE);
				}
				try {
					new xmlToSbol(fileFrame.this.xml, fileFrame.this.sbol);
				} catch (IOException ex) {
					// Logger.getLogger(xmlToSbol.class.getName()).log(Level.SEVERE,
					// null, ex);
				}
			}
		});
		btnNewButton_2.setBounds(304, 142, 93, 27);
		panel.add(btnNewButton_2);

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(fileFrame.class
				.getResource("/org/sysu/sbol/SBOL.png")));
		lblNewLabel_2.setBounds(248, 44, 163, 65);
		panel.add(lblNewLabel_2);

		JButton btnNewButton = new JButton("Clear");
		btnNewButton
				.setToolTipText("Clear your selection of xml and sbol file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileFrame.this.clearAll();
			}
		});
		btnNewButton.setBounds(182, 142, 113, 27);
		panel.add(btnNewButton);
	}

	private void clearAll() {
		this.lblNewLabel.setText("xml:");
		this.lblNewLabel_1.setText("sbol:");
		this.xml = "";
		this.sbol = "";
	}

	public JLabel getLblNewLabel_1() {
		return lblNewLabel_1;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}
}
