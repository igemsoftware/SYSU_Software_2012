package MyProgressFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * 
* @ClassName: MyProgressBarFrame 
* @Description: Create a ProgressBarFrame 
* @author Guo Jiexin
* @date Sep 15, 2012 5:06:49 PM 
*
 */
public class MyProgressBarFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208908427295010255L;
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblNewLabel;
	private JButton btnHideButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyProgressBarFrame frame = new MyProgressBarFrame();
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
	public MyProgressBarFrame() {
		setTitle("Please wait...");
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		//setUndecorated(true);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(14, 13, 472, 44);
		contentPane.add(progressBar);
		
		lblNewLabel = new JLabel("Loading....");
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 17));
		lblNewLabel.setBounds(58, 70, 407, 23);
		contentPane.add(lblNewLabel);
		
		btnHideButton = new JButton("Hide");
		btnHideButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MyProgressBarFrame.this.hide();
			}
		});
		btnHideButton.setBounds(185, 106, 113, 27);
		contentPane.add(btnHideButton);
	}
	public void printProgress(String text,int percent)
	{
		this.progressBar.setValue(percent);
		this.lblNewLabel.setText(text+"  "+percent+"%");
		this.setTitle("Please wait... "+percent+"% finished");
	}
	public void printProgress(int percent)
	{
		this.progressBar.setValue(percent);
		this.setTitle("Please wait... "+percent+"% finished");
	}
	public void finish()
	{
		this.dispose();
	}
}
