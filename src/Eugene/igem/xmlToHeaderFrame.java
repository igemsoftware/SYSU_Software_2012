package igem;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class xmlToHeaderFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					xmlToHeaderFrame frame = new xmlToHeaderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	String xml;
	String header;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	/**
	 * Create the frame.
	 */
	public xmlToHeaderFrame() {		
		setTitle("xml to eugene");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 392, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnNewButton = new JButton("open xml");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						" XML Files", "xml");
				chooser.setFileFilter(filter);
				try {
					chooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
				} catch (IOException e1) {
//					e1.printStackTrace();
				}
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					xml = chooser.getSelectedFile()
							.getAbsolutePath();
					btnNewButton.setToolTipText(xml);
//					xmlToHeaderFrame.this.getLblNewLabel().setText("xml:"+xml);
				}
				try {
					if(chooser.getSelectedFile()!=null)
						HistoryFile.setPathToHistoryFile("history.txt", chooser.getSelectedFile().getPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 12, 104, 23);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("select .eug");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(
//						" header Files", "*.*");
//				chooser.setFileFilter(filter);
				try {
					chooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
				} catch (IOException e1) {
//					e1.printStackTrace();
				}
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					header = chooser.getSelectedFile()
							.getAbsolutePath();
					if(!header.endsWith(".eug"))
						header+=".eug";
					btnNewButton_1.setToolTipText(header);
//					xmlToHeaderFrame.this.getLblNewLabel_1().setText("header:"+header);
				}
				try {
					if(chooser.getSelectedFile()!=null)
						HistoryFile.setPathToHistoryFile("history.txt", chooser.getSelectedFile().getPath());
				} catch (IOException e1) {
//					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(10, 67, 104, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Go!");
		btnNewButton_2.setToolTipText("Click this button and it will change the data from the biobrick xml to an eugene script that can be use directly by eugene.");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!xml.isEmpty()&&!header.isEmpty())
				{
					new XMLParser(xml).createXmlEugeneHeaderFile(header);
					System.out.println("here");
				}
				else
					JOptionPane.showMessageDialog(null,
							"you didn't select file!", "Warning!",
							JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_2.setBounds(10, 176, 104, 23);
		panel.add(btnNewButton_2);
		
		JButton clearButton_3 = new JButton("clear");
		clearButton_3.setToolTipText("clear the selected file");
		clearButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xmlToHeaderFrame.this.xml="";
				xmlToHeaderFrame.this.header="";
				xmlToHeaderFrame.this.getBtnNewButton().setToolTipText("");
				xmlToHeaderFrame.this.getBtnNewButton_1().setToolTipText("");
			}
		});
		clearButton_3.setBounds(10, 116, 104, 23);
		panel.add(clearButton_3);
		
		final JLabel lblNewLabel = new JLabel("Eugene");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Runtime.getRuntime().exec("cmd /c start http://eugene.sourceforge.net/");
				} catch (IOException e) {
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Cursor hander = new Cursor(Cursor.HAND_CURSOR);
				lblNewLabel.setCursor(hander);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Cursor hander = new Cursor(Cursor.DEFAULT_CURSOR);
				lblNewLabel.setCursor(hander);
			}
		});
		lblNewLabel.setToolTipText("Click me to see the eugene's website!");
		lblNewLabel.setIcon(new ImageIcon(xmlToHeaderFrame.class.getResource("/igem/60px-Eugene.png")));
		lblNewLabel.setBounds(169, 12, 116, 200);
		panel.add(lblNewLabel);
	}
	public JButton getBtnNewButton() {
		return btnNewButton;
	}
	public JButton getBtnNewButton_1() {
		return btnNewButton_1;
	}
}
