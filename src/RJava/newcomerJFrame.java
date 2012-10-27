import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class newcomerJFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newcomerJFrame frame = new newcomerJFrame();
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
	public newcomerJFrame() {
		setTitle("Select what function do you need");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 462, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("META-Network without Perturbation");
		chckbxNewCheckBox.setBounds(0, 9, 303, 27);
		panel.add(chckbxNewCheckBox);
		
		final JCheckBox chckbxNewCheckBox_1 = new JCheckBox("2D or 3D Perturbation");
		chckbxNewCheckBox_1.setBounds(0, 50, 303, 27);
		panel.add(chckbxNewCheckBox_1);
		
		final JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Effect of genes and reactions(on network)\r\n ");
		chckbxNewCheckBox_2.setBounds(0, 94, 376, 27);
		panel.add(chckbxNewCheckBox_2);
		
		final JCheckBox chckbxNewCheckBox_3 = new JCheckBox("Effect of genes and reactions(on objective pathway)");
		chckbxNewCheckBox_3.setBounds(0, 139, 463, 27);
		panel.add(chckbxNewCheckBox_3);
		
		final JCheckBox chckbxRobustenessOfMetanework = new JCheckBox("Robusteness of META-Network");
		chckbxRobustenessOfMetanework.setBounds(0, 181, 252, 27);
		panel.add(chckbxRobustenessOfMetanework);
		
		JButton btnNewButton = new JButton("Get Advice!\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adviceJFrame frame=new adviceJFrame();
				if(chckbxNewCheckBox.isSelected())
				{
					frame.getCheckBox_2().setSelected(true);
				}
				if(chckbxNewCheckBox_1.isSelected())
				{
					frame.getCheckBox_3().setSelected(true);
					frame.getCheckBox_4().setSelected(true);
				}
				if(chckbxNewCheckBox_2.isSelected())
				{
					frame.getCheckBox_1().setSelected(true);
					frame.getCheckBox_5().setSelected(true);
				}
				if(chckbxNewCheckBox_3.isSelected())
				{
					frame.getCheckBox().setSelected(true);
					frame.getCheckBox_3().setSelected(true);
					frame.getCheckBox_4().setSelected(true);
				}
				if(chckbxRobustenessOfMetanework.isSelected())
				{
					frame.getCheckBox_2().setSelected(true);
					frame.getCheckBox_5().setSelected(true);
//					frame.getCheckBox_4().setSelected(true);
				}
				frame.setVisible(true);
				newcomerJFrame.this.dispose();				
			}
		});
		btnNewButton.setBounds(0, 228, 139, 27);
		panel.add(btnNewButton);
	}
}
