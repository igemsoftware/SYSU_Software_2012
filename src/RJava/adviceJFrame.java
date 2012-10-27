import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
* @ClassName: adviceJFrame 
* @Description: TODO
* @author Guo Jiexin
* @date Oct 21, 2012 2:02:08 PM 
*
 */
public class adviceJFrame extends JFrame {

	private JPanel contentPane;
	private JCheckBox checkBox_0;
	private JCheckBox checkBox_1;
	private JCheckBox chckbxFvaSolve;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adviceJFrame frame = new adviceJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public adviceJFrame() {
		setTitle("Advice from your selection");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 491, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("Select Your File");
		label.setBounds(14, 13, 259, 18);
		panel.add(label);
		
		JButton button = new JButton("OpenFile");
		button.setEnabled(false);
		button.setToolTipText("To select a SBML file for input.");
		button.setBounds(346, 9, 106, 27);
		panel.add(button);
		
		JButton btnClose = new JButton("Close!");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adviceJFrame.this.dispose();
			}
		});
		btnClose.setToolTipText("To Start Calculate");
		btnClose.setBounds(14, 275, 90, 27);
		panel.add(btnClose);
		
		JButton button_2 = new JButton("Edit Reactions");
		button_2.setEnabled(false);
		button_2.setBounds(14, 235, 145, 27);
		panel.add(button_2);
		
		checkBox_0 = new JCheckBox("Exhaustive single deletion");
		checkBox_0.setBounds(14, 40, 252, 27);
		panel.add(checkBox_0);
		
		checkBox_1 = new JCheckBox("Reaction deletion");
		checkBox_1.setBounds(14, 72, 190, 27);
		panel.add(checkBox_1);
		
		chckbxFvaSolve = new JCheckBox("FVA Solve");
		chckbxFvaSolve.setBounds(14, 104, 172, 27);
		panel.add(chckbxFvaSolve);
		
		checkBox_3 = new JCheckBox("Preturbation Analysis");
		checkBox_3.setBounds(14, 167, 206, 27);
		panel.add(checkBox_3);
		
		checkBox_4 = new JCheckBox("PhPP ANALYSIS ");
		checkBox_4.setBounds(14, 199, 190, 27);
		panel.add(checkBox_4);
		
		JLabel label_1 = new JLabel("Select:null");
		label_1.setBounds(229, 76, 218, 18);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Select:null");
		label_2.setBounds(230, 171, 226, 18);
		panel.add(label_2);
		
		checkBox_5 = new JCheckBox("Mutant Fva");
		checkBox_5.setBounds(14, 135, 133, 27);
		panel.add(checkBox_5);
		
		JLabel label_3 = new JLabel("Select:null");
		label_3.setBounds(229, 140, 223, 18);
		panel.add(label_3);
		this.setLocation(400, 300);
	}
	
	public JCheckBox getCheckBox() {
		return checkBox_0;
	}
	public JCheckBox getCheckBox_1() {
		return checkBox_1;
	}
	public JCheckBox getCheckBox_2() {
		return chckbxFvaSolve;
	}
	public JCheckBox getCheckBox_3() {
		return checkBox_3;
	}
	public JCheckBox getCheckBox_4() {
		return checkBox_4;
	}
	public JCheckBox getCheckBox_5() {
		return checkBox_5;
	}
}
