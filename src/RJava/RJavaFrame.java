import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.rosuda.JRI.Rengine;

import MyProgressFrame.MyProgressBarFrame;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @ClassName: RJavaFrame
 * @Description: a JFrame that can show R-Java calculating results
 * @author Guo Jiexin
 * @date Aug 31, 2012 10:22:40 AM
 * 
 */
public class RJavaFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4517578222264682431L;
	private JPanel contentPane;
	private String filePath = "";
	private boolean needMax = false;
	private Rengine re = null;
	private rJava test = null;
	private JCheckBox chckbxExhaustivesingledeletion;
	private JCheckBox chckbxReactionDeletion;
	private JCheckBox chckbxFvaSlove;
	private JCheckBox chckbxPreturbationAnalysis;
	private JCheckBox chckbxPhppAnalysis;
	private int reactionDeletionSelected = 0;
	private int preturbationSelected = 0;
	private JLabel lblSelectReaction;
	private JLabel lblSelectPreturbation;
	private String userInput = "";
	private String carbon_reaction = null;
	private JFrame selectCarbonReactionFrame = null;
	private int boundary_reaction1 = -1;
	private int boundary_reaction2 = -1;
	private JDesktopPane desktop=new JDesktopPane();

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("Running in "+System.getProperty("sun.arch.data.model")+"bit"); 
					RJavaFrame frame = new RJavaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * create a MainFrame MenuBar has a button "Tile All Windows"
	 * 
	 * @param desktop
	 * @return
	 */
	public JMenuBar createMainFrameMenuBar(final JDesktopPane desktop) {
		JMenuBar menuBar = new JMenuBar();
		JMenu windowMenu = new JMenu("Windows");
		menuBar.add(windowMenu);
		JMenuItem newMenuItem = new JMenuItem("Tile All Windows");
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RJavaFrame.tileWindows(desktop);
			}
		});
		windowMenu.add(newMenuItem);
		extraMenu = new JMenu("Extra");
		menuBar.add(extraMenu);
		JMenuItem newMenuItem2 = new JMenuItem("Close the rgl");
		newMenuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RJavaFrame.this.re.eval("rgl.close()");
				extraMenu.setEnabled(false);
			}
		});
		extraMenu.add(newMenuItem2);
		extraMenu.setEnabled(false);
		return menuBar;
	}

	/**
	 * Create the frame.
	 */
	public RJavaFrame() {
//		try {
//			RJavaFrame.setDefaultLookAndFeelDecorated(true);
//			UIManager
//					.setLookAndFeel(new com.seaglasslookandfeel.SeaGlassLookAndFeel());
//			SwingUtilities.updateComponentTreeUI(this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		this.setTitle("IGEM FBA Solve");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 501, 297);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);

		final JLabel lblSelectFile = new JLabel("Select Your File");
		lblSelectFile.setBounds(14, 13, 259, 18);
		mainPanel.add(lblSelectFile);

		JButton btnOpenFile = new JButton("OpenFile");
		btnOpenFile.setToolTipText("To select a SBML file for input.");
		btnOpenFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RJavaFrame.this.desktop=new JDesktopPane();
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"SBML XML Files", "xml");
				chooser.setFileFilter(filter);
				try {
					chooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
				} catch (IOException e) {
					e.printStackTrace();
				}
				int returnVal = chooser.showOpenDialog(RJavaFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					RJavaFrame.this.filePath = chooser.getSelectedFile()
							.getAbsolutePath();
					lblSelectFile.setText("Selected File:"
							+ RJavaFrame.this.filePath
									.substring(RJavaFrame.this.filePath
											.lastIndexOf('\\') + 1));
					RJavaFrame.this.readFile(RJavaFrame.this.filePath);
				}
				try {
					if(chooser.getSelectedFile()!=null)
						HistoryFile.setPathToHistoryFile("history.txt", chooser.getSelectedFile().getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnOpenFile.setBounds(346, 9, 106, 27);
		mainPanel.add(btnOpenFile);
		JButton btnGo = new JButton("Go!");
		btnGo.setToolTipText("To Start Calculate");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// if the user didn't select a file
				if (RJavaFrame.this.filePath.length() == 0)
					JOptionPane.showMessageDialog(RJavaFrame.this,
							"you didn't select file!", "Warning!",
							JOptionPane.INFORMATION_MESSAGE);
				else {
					RJavaFrame.this.calc();			
				}
			}
		});
		btnGo.setBounds(14, 245, 90, 27);
		mainPanel.add(btnGo);

		JButton btnEditReactions = new JButton("Edit Reactions");
		btnEditReactions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (RJavaFrame.this.filePath.length() == 0)
					JOptionPane.showMessageDialog(RJavaFrame.this,
							"You hava not selected file yet!", "Warning!",
							JOptionPane.INFORMATION_MESSAGE);
				else
					RJavaFrame.this.test
							.ShowReactionSelectForm(RJavaFrame.this);
			}
		});
		btnEditReactions.setBounds(14, 205, 145, 27);
		mainPanel.add(btnEditReactions);

		chckbxExhaustivesingledeletion = new JCheckBox(
				"Exhaustive single deletion");
		chckbxExhaustivesingledeletion.setBounds(14, 40, 252, 27);
		mainPanel.add(chckbxExhaustivesingledeletion);

		chckbxReactionDeletion = new JCheckBox("Reaction deletion");
		chckbxReactionDeletion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxReactionDeletion.isSelected()) {
					if (RJavaFrame.this.filePath.length() == 0) {
						JOptionPane.showMessageDialog(RJavaFrame.this,
								"You hava not selected file yet!", "Warning!",
								JOptionPane.INFORMATION_MESSAGE);
						chckbxReactionDeletion.setSelected(false);
					} else
						RJavaFrame.this.selectReactionFrom();
				} else {
					RJavaFrame.this.reactionDeletionSelected = -1;
					RJavaFrame.this.lblSelectReaction.setText("Select:null");
				}
			}
		});
		chckbxReactionDeletion.setBounds(14, 72, 190, 27);
		mainPanel.add(chckbxReactionDeletion);

		chckbxFvaSlove = new JCheckBox("Fva Solve");
		chckbxFvaSlove.setBounds(14, 104, 172, 27);
		mainPanel.add(chckbxFvaSlove);

		chckbxPreturbationAnalysis = new JCheckBox("Preturbation Analysis");
		chckbxPreturbationAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxPreturbationAnalysis.isSelected()) {
					if (RJavaFrame.this.filePath.length() == 0) {
						JOptionPane.showMessageDialog(RJavaFrame.this,
								"You hava not selected file yet!", "Warning!",
								JOptionPane.INFORMATION_MESSAGE);
						chckbxReactionDeletion.setSelected(false);
					} else
						RJavaFrame.this.selectPreturbationFrom();
				} else {
					RJavaFrame.this.preturbationSelected = 0;
					RJavaFrame.this.lblSelectPreturbation
							.setText("Select:null");
				}
			}
		});
		chckbxPreturbationAnalysis.setBounds(14, 136, 206, 27);
		mainPanel.add(chckbxPreturbationAnalysis);

		chckbxPhppAnalysis = new JCheckBox("PhPP ANALYSIS ");
		chckbxPhppAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxPhppAnalysis.isSelected())
					userInput = JOptionPane.showInputDialog(null,
							"please input your carbon source", "");
				else
					userInput = "";
			}
		});
		chckbxPhppAnalysis.setBounds(14, 168, 190, 27);
		mainPanel.add(chckbxPhppAnalysis);

		lblSelectReaction = new JLabel("Select:null");
		lblSelectReaction.setBounds(235, 76, 252, 18);
		mainPanel.add(lblSelectReaction);

		lblSelectPreturbation = new JLabel("Select:null");
		lblSelectPreturbation.setBounds(233, 140, 226, 18);
		mainPanel.add(lblSelectPreturbation);

		String[] args = null;
		re = new Rengine(args, false, new TextConsole());
	}
	public void deleteAllXls() {
		// System.out.println(System.getProperty("user.dir"));
		File[] files = this.getAllFileinDir(System.getProperty("user.dir"));
		for (int i = 0; i < files.length; i++) {
			if (files[i].getPath().toLowerCase().endsWith(".xls")) {
				this.deleteFile(files[i].getPath());
			}
		}
	}
	public void deleteAllPdf()
	{
		File[] files = this.getAllFileinDir(System.getProperty("user.dir"));
		for (int i = 0; i < files.length; i++) {
			if (files[i].getPath().toLowerCase().endsWith(".pdf")) {
				this.deleteFile(files[i].getPath());
			}
		}
	}
	public void showAllPng(JDesktopPane desktop) {
		// System.out.println(System.getProperty("user.dir"));
		File[] files = this.getAllFileinDir(System.getProperty("user.dir"));
		for (int i = 0; i < files.length; i++) {
			if (files[i].getPath().toLowerCase().endsWith(".png")) {
				new ShowBmp(files[i].getPath(), desktop).setVisible(true);
			}
		}
	}

	public File[] getAllFileinDir(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	public void readFile(String fileName) {
		this.test = new rJava();
		test.ReadSbml(fileName);
	}

	public void selectPreturbationFrom() {
		final JFrame frame = new JFrame("Select reaction");
		rJava.initDefaultFrame(frame);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel tempLabel = new JLabel("");
		tempLabel.setPreferredSize(new Dimension(380, 70));
		tempLabel.setVisible(true);
		panel.add(tempLabel);
		JLabel label1 = new JLabel("Select which reaction?");
		label1.setVisible(true);
		RJavaFrame.this.preturbationSelected = 0;
		RJavaFrame.this.lblSelectPreturbation.setText("Select:"
				+ RJavaFrame.this.test.Reaction_list.get(0).id);
		panel.add(label1);
		String[] temp = new String[this.test.Reaction_list.size()];
		for (int i = 0; i < this.test.Reaction_list.size(); i++)
			temp[i] = this.test.Reaction_list.get(i).id;
		final JComboBox selectReactionJComboBox = new JComboBox(temp);
		selectReactionJComboBox.setMaximumRowCount(15);
		selectReactionJComboBox.setVisible(true);
		selectReactionJComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RJavaFrame.this.preturbationSelected = selectReactionJComboBox
						.getSelectedIndex();
				RJavaFrame.this.lblSelectPreturbation.setText("Select:"
						+ RJavaFrame.this.test.Reaction_list
								.get(preturbationSelected).id);
			}
		});
		panel.add(selectReactionJComboBox);
		JButton finishButton = new JButton("finish");
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(finishButton);
	}

	/**
	 * Create a frame that the user can select reaction for the reactionDeletion
	 * Function
	 */
	public void selectReactionFrom() {
		final JFrame frame = new JFrame("Select Reaction");
		rJava.initDefaultFrame(frame);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel tempLabel = new JLabel("");
		tempLabel.setPreferredSize(new Dimension(380, 70));
		tempLabel.setVisible(true);
		panel.add(tempLabel);
		JLabel label1 = new JLabel("Select which reaction?");
		label1.setVisible(true);
		RJavaFrame.this.reactionDeletionSelected = 0;
		RJavaFrame.this.lblSelectReaction.setText("Select:"
				+ RJavaFrame.this.test.Reaction_list.get(0).id);
		panel.add(label1);
		String[] temp = new String[this.test.Reaction_list.size()];
		for (int i = 0; i < this.test.Reaction_list.size(); i++)
			temp[i] = this.test.Reaction_list.get(i).id;
		final JComboBox selectReactionJComboBox = new JComboBox(temp);
		selectReactionJComboBox.setMaximumRowCount(15);
		selectReactionJComboBox.setVisible(true);
		selectReactionJComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RJavaFrame.this.reactionDeletionSelected = selectReactionJComboBox
						.getSelectedIndex();
				RJavaFrame.this.lblSelectReaction.setText("Select:"
						+ RJavaFrame.this.test.Reaction_list
								.get(reactionDeletionSelected).id);
			}
		});
		panel.add(selectReactionJComboBox);
		JButton finishButton = new JButton("finish");
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(finishButton);
	}

	/**
	 * delete folder or file,no matter it exist or not
	 * 
	 * @param sPath
	 *            the folder or file need to delete
	 * @return success return true,else return false
	 */
	public static boolean DeleteFolder(String sPath) {
		File file = new File(sPath);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(sPath);
			} else {
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * Delete a single file
	 * 
	 * @param sPath
	 *            Delete file's name
	 * @return The single file deleted successfully returns true, false
	 *         otherwise
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// Path is file and is not empty then delete
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * Delete directories (folders) and files in that directory
	 * 
	 * @param sPath
	 *            the path that you want to delete
	 * 
	 * @return Directory deleted successfully returns true, false otherwise
	 */
	public static boolean deleteDirectory(String sPath) {
		// if SPath not the end of the file separator
		// then automatically add the file separator
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// If the sPath file does not exist or
		// is not a directory, then exit
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// Delete all the files in folder
		// (including subdirectories)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// Delete sub files
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // Delete the subdirectory
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// Delete the current directory
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	private class Message {
		public String message;
		public int percent;

		public Message(String message, int percent) {
			super();
			this.message = message;
			this.percent = percent;
		}
	}
	/**
	 * 
	* @ClassName: BackgroundWorker 
	* @Description: To do the r commands background 
	* 					and showing a progressbar for the progress of caculation
	* @author Guo Jiexin
	* @date Sep 14, 2012 12:50:00 PM 
	*
	 */
	public class BackgroundWorker extends SwingWorker<Void, Message> {
		/**
		 * To create the Mainframe and show the result
		 */
		@Override
		protected void done() {	
			use.printProgress("Finish !", 100);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			use.finish();
			this.setProgress(100);
			RJavaFrame.this.test.readFluxByLines("fluxes.txt");
			
			JFrame mainFrame = new JFrame("Resulting");
			rJava.initDefaultFrame(mainFrame);
			Dimension scrSize = Toolkit.getDefaultToolkit()
					.getScreenSize();
			
			// To make the frame max to the screen
			mainFrame
					.reshape(0, 0, scrSize.width, scrSize.height - 100);
			//final JDesktopPane desktop = new JDesktopPane();
			mainFrame.setJMenuBar(RJavaFrame.this
					.createMainFrameMenuBar(desktop));

			JInternalFrame fbaFrom = RJavaFrame.this.test
					.showFBA_solveForm(RJavaFrame.this.re);
			desktop.add(fbaFrom);
			if (RJavaFrame.this.chckbxExhaustivesingledeletion
					.isSelected()) {
				JInternalFrame frame = new JInternalFrame(
						"Exhaustive_single_deletion", true, true, true,
						true);
				rJava.initDefaultFrame(frame);
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(
						RJavaFrame.this.test.Reaction_list.size()
								+ RJavaFrame.this.test.Reaction_list
										.size(), 2, 50, 5));
				RJavaFrame.this.test.addBiomassToPanel(
						"biomass.txt", panel);
				RJavaFrame.this.test.addLethalDeletionsToPanel(
						"lethal.txt", panel);
				RJavaFrame.this.test.addSubOptimalDelsToPanel(
						"sub_optimal_dels.txt", panel);
				RJavaFrame.this.test.addSuperOptimalDelsToPanel(
						"super_optimal_dels.txt", panel);
				JScrollPane scrollPane = new JScrollPane(panel);
				scrollPane.setViewportView(panel);
				frame.getContentPane().add(scrollPane);
				new ShowBmp("singleKOresults.bmp", desktop)
						.setVisible(true);
				new ShowBmp("singleKOresults2.bmp", desktop)
						.setVisible(true);
				desktop.add(frame);
				frame.show();
			}
			if (RJavaFrame.this.chckbxReactionDeletion.isSelected()) {
				desktop.add(RJavaFrame.this.test
						.ShowReactionDelectionForm(
								"Wildtype_fluxes.txt",
								"Mutant_fluxes.txt"));
			}
			if (RJavaFrame.this.chckbxFvaSlove.isSelected()) {
				desktop.add(RJavaFrame.this.test.ShowFvaSolveForm());
			}
			if (RJavaFrame.this.chckbxPreturbationAnalysis.isSelected()) {
				RJavaFrame.this.showAllPng(desktop);
			}
			if(RJavaFrame.this.chckbxPhppAnalysis.isSelected())
			{
				re.eval("source(\"rtemp//choose.r\")");
				String carbonName = "glucose";
				if (!userInput.equals(""))
					carbonName = userInput;
				carbonName = "\"" + carbonName + "\"";
				System.out
						.println("carbonreactionList=choose_carbon_source(PCS="
								+ carbonName + ",fba_object=testda)");
				re.eval("carbonreactionList=choose_carbon_source(PCS="
						+ carbonName + ",fba_object=testda)");
				re.eval("write.table(carbonreactionList,file=\"carbon.txt\")");
				selectCarbonReactionFrame = this.frame
						.selectCarbonReactionForm(carbonName);
				//System.out.println("here");
				
				
				
			}
			mainFrame.getContentPane().add(desktop);
			mainFrame.show();
			RJavaFrame.tileWindows(desktop);
			if (RJavaFrame.this.selectCarbonReactionFrame != null
					&& RJavaFrame.this.selectCarbonReactionFrame
							.isShowing())
				RJavaFrame.this.selectCarbonReactionFrame
						.setExtendedState(JFrame.MAXIMIZED_BOTH);
			RJavaFrame.deleteFile("PHPP.bmp");
		}
		/**
		 * To change the progressBar's message and persent
		 */
		@Override
		protected void process(List<Message> chunks) {
			use.printProgress(chunks.get(chunks.size() - 1).message,
					chunks.get(chunks.size() - 1).percent);
		}

		RJavaFrame frame = null;
		/**
		 * To create the ProgressFrame
		 */
		MyProgressBarFrame use = null;
		public BackgroundWorker(RJavaFrame frame) {
			super();
			this.frame = frame;
			this.use = new MyProgressBarFrame();			
			use.printProgress("Starting....",0);
		}
		/**
		 * 
		 * @return
		 */
		public String GetRxnGeneMatBackground() {
			StringBuilder matrix =new StringBuilder("rxnGeneMat=matrix(c(");
			int col = this.frame.test.mAllGene_List.size();
			int row = this.frame.test.Reaction_list.size();
			int[][] numMatrix = new int[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (this.frame.test.Reaction_list.get(i).mGeneName
							.indexOf(this.frame.test.mAllGene_List.get(j)) == -1) {
						numMatrix[i][j] = 0;
					} else
						numMatrix[i][j] = 1;
				}
			}
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					matrix.append(numMatrix[i][j]);
					if (!(i == row - 1 && j == col - 1))
						matrix.append(",");
				}
				this.publish(new Message("Loading RxnGeneMat..",i/row*100-1));
			}
			this.publish(new Message("Sending RxnGeneMat to R",99));
			matrix.append("),nrow=").append(row).append(",ncol=").append(col).append(")");
			return matrix.toString();
		}
		public String GetMatrixBackground() {
			StringBuilder matrix = new StringBuilder("matr=matrix(c(");
			final int row = this.frame.test.Species_list.size();// -this.rowToIgnore;
			//System.out.println(row);
			final int col = this.frame.test.Reaction_list.size();
			//System.out.println(col);
			BigDecimal [][] numMatrix = new BigDecimal[row][col];		
			for (int i = 0; i < this.frame.test.Reaction_list.size(); i++) {
				for (int j = 0; j < this.frame.test.Reaction_list.get(i).mReactants.size(); j++) {
					int reactant = this.frame.test
							.findSpecieByName(this.frame.test.Reaction_list.get(i).mReactants
									.get(j).species);
					numMatrix[reactant][i] = this.frame.test.Reaction_list.get(i).mReactants.get(j).stoichiometry;
					
				}
				for (int j = 0; j < this.frame.test.Reaction_list.get(i).mProducts.size(); j++) {
					int product = this.frame.test
							.findSpecieByName(this.frame.test.Reaction_list.get(i).mProducts.get(j).species);
					numMatrix[product][i] = this.frame.test.Reaction_list.get(i).mProducts.get(j).stoichiometry.negate();
				}			
			}
			for (int j = 0; j < col; j++)
			{
				for (int i = 0; i < row - this.frame.test.rowToIgnore; i++) {
					if(numMatrix[i][j]==null)
						matrix.append('0');
					else
						matrix.append(numMatrix[i][j]);
					//System.out.println(numMatrix[i][j]);
					if (!(i == row - this.frame.test.rowToIgnore - 1 && j == col - 1))
						matrix.append(",") ;	
				}	
				this.publish(new Message("Loading matrix",(int)((double)j/col*100)-1));
			}
			this.publish(new Message("Sending matrix to R",99));
			matrix.append("),nrow=").append(row - this.frame.test.rowToIgnore).append(",ncol=").append(col).append(")");
			return matrix.toString();
		}
		
		/**
		 * create R commands to calculate and write to files
		 */
		@Override
		protected Void doInBackground() throws Exception {
			test.setNeedMax(needMax);
			if (!Rengine.versionCheck())
				System.exit(1);
			if (!re.waitForR())
				return null;
			re.eval("max=TRUE");
			System.out.println("max=TRUE");
			// After this are all R commands
			// GetMatrix() and GetRxnGeneMat() need most of time
			this.publish(new Message("Loading Matrix", 2));
			String temp = this.GetMatrixBackground();//test.GetMatrix();
			re.eval(temp);
			temp = null;
			this.publish(new Message("Loading Object", 12));
			temp = test.GetObj();
			re.eval(temp);
			temp = null;
			System.gc();
			System.runFinalization();
			this.publish(new Message("Loading LowerValue", 20));
			temp = test.GetLowerVal();
			re.eval(temp);
			temp = null;
			this.publish(new Message("Loading UpperValue",30));
			temp = test.GetUpperVal();
			re.eval(temp);
			System.out.println(temp);
			temp = null;
			this.publish(new Message("Loading Reaction_list",35));
			temp = test.GetReaction_list();
			re.eval(temp);
			temp = null;
			temp = test.GetMetabolite_Name();
			re.eval(temp);
			temp = null;
			temp = test.GetAllGenes();
			re.eval(temp);
			temp = test.GetGPR();
			re.eval(temp);
			temp = null;
			this.publish(new Message("Loading RxnGeneMatrix",60));
			temp = this.GetRxnGeneMatBackground();//test.GetRxnGeneMat();
			re.eval(temp);
			temp = null;
			temp = test.GetSubSystem();
			re.eval(temp);
			temp = test.Getboundaryreaction();
			re.eval(temp);
			temp = null;
			this.publish(new Message("Caculating Summa",62));
			re.eval("source(\"rtemp//summa.r\")");
			re.eval("testda=summa(reaction_list,metabolite_name,matr,obj,lower_val,upper_val,gpr,all_genes,rxnGeneMat,max,subsystem,boundaryreaction)");
			re.eval("library(abcdeFBA)");
			System.out.println(re.eval("testda"));
			re.eval("FBA_solve(fba_object=testda,precision=6£¬verbosity=T)");
			System.out.println();
			System.out.print(re.eval("FBA_solve(fba_object=testda)"));
			this.publish(new Message("Caculating FBA_solve",65));
			re.eval("a=FBA_solve(testda)");
			System.out.println(re.eval("testda"));
			re.eval("write.table(a$fluxes,file=\"fluxes.txt\")");
			if (RJavaFrame.this.chckbxExhaustivesingledeletion.isSelected()) {
//				re.eval("x=1");
//				re.eval("y=1");
//				re.eval("plot(x,y)");
				re.eval("source(\"rtemp//Exhaustive.r\")");
				this.publish(new Message("R is caculating Exhaustive_single_knockout",68));
				re.eval("A=Exhaustive_single_knockout(fba_object=testda,plot_to_file=TRUE)");
				System.out
						.println("A=Exhaustive_single_knockout(fba_object=testda,plot_to_file=TRUE)");
				this.publish(new Message("Reading files",75));
				re.eval("write.table(A$biomass_all,file=\"biomass.txt\")");
				re.eval("write.table(A$lethal_dels,file=\"lethal.txt\")");
				re.eval("write.table(A$sub_optimal_dels,file=\"sub_optimal_dels.txt\")");
				re.eval("write.table(A$super_optimal_dels,file=\"super_optimal_dels.txt\")");
				re.eval("dev.off()");
			}
			if (this.frame.chckbxReactionDeletion.isSelected()) {
				this.publish(new Message("Caculating single_delete_flux",75));
				re.eval("source(\"rtemp//single_delete_flux.r\")");
				System.out
						.println("result_reaction_deletion=single_delete_flux("
								+ (this.frame.reactionDeletionSelected + 1)
								+ ",testd=testda)");
				re.eval("result_reaction_deletion=single_delete_flux("
						+ (this.frame.reactionDeletionSelected + 1)
						+ ",testd=testda)");
				re.eval("write.table(result_reaction_deletion$Wildtype_fluxes,file=\"Wildtype_fluxes.txt\")");
				re.eval("write.table(result_reaction_deletion$Mutant_fluxes,file=\"Mutant_fluxes.txt\")");
			}

			if (this.frame.chckbxFvaSlove.isSelected()) {
				this.publish(new Message("Caculating FLUX_VAR_ANALYSIS",75));
				re.eval("c=FLUX_VAR_ANALYSIS(testda)");
				System.out.println("c=FLUX_VAR_ANALYSIS(testda)");
				re.eval("write.table(c[,1] ,file=\"1.txt\")");
				System.out.println("write.table(c[,1] ,file=\"1.txt\")");
				re.eval("write.table(c[,2] ,file=\"2.txt\")");
				re.eval("write.table(c[,3] ,file=\"3.txt\")");
				re.eval("write.table(c[,4] ,file=\"4.txt\")");
				re.eval("write.table(c[,5] ,file=\"5.txt\")");
			}
			if (this.frame.chckbxPreturbationAnalysis.isSelected()) {
				System.out.println("A=perturbation("
						+ this.frame.preturbationSelected + 1 + ",testda)");
				this.publish(new Message("Caculating perturbation",85));
				re.eval("source(\"rtemp//perturbation.r\")");
				re.eval("A=perturbation(" + this.frame.preturbationSelected + 1
						+ ",testda)");
				re.eval("dev.off()");
			}
			if (this.frame.chckbxPhppAnalysis.isSelected()) {
				
			}
			// Clean the R memory
			re.eval("rm()");
			re.eval("gc()");
			return null;
		}
	} 
	/**
	 * To input R commands and calculate
	 */
	public void calc() {
		final BackgroundWorker back = new BackgroundWorker(this);
		back.execute();		
	}

	/**
	 * After selected two reactions,do calculating
	 */
	public void toDoPHPPAnalysis() {
		re.eval("source(\"rtemp//plot_phpp.r\")");
		StringBuilder php = new StringBuilder("PHP(c(");
		php.append(this.boundary_reaction1 + 1).append(",")
				.append(this.boundary_reaction2 + 1);
		php.append("),fba_object=testda,").append(this.carbon_reaction)
				.append(")");
		System.out.println(php);
		re.eval(php.toString());
		this.extraMenu.setEnabled(true);
		new ShowBmp("PHPP.bmp", desktop)
		.setVisible(true);

		this.tileWindows(desktop);
	}

	private int selectBoundaryReactionCount = 0;
	private JMenu extraMenu;

	/**
	 * To create a jframe that user can select two boundary reactions
	 */
	public void selectBoundaryReactionForm() {
		selectBoundaryReactionCount = 0;
		this.boundary_reaction1 = -1;
		this.boundary_reaction2 = -1;
		final JFrame frame = new JFrame("To choose two boundary reactions");
		frame.setLocationRelativeTo(null);// To center the frame
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 700);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		final JList jlist = new JList();
		final DefaultListModel model = new DefaultListModel();
		// Add all boundary reactions to the jlist's model
		for (int i = 0; i < test.boundaryReaction_List.size(); i++)
			model.addElement(test.boundaryReaction_List.get(i).id);
		jlist.setModel(model);
		jlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int selectReactionNumber = 0;
				for (int i = 0; i < RJavaFrame.this.test.Reaction_list.size(); i++) {
					if (RJavaFrame.this.test.Reaction_list.get(i).id
							.equals((String) jlist.getSelectedValue())) {
						selectReactionNumber = i;
						break;
					}
				}
				// After selected then delete the selected item in jlist
				if (jlist.getSelectedIndex() >= 0) {
					model.removeElementAt(jlist.getSelectedIndex());
					RJavaFrame.this.selectBoundaryReactionCount++;
					jlist.setSelectedIndex(-1);
					jlist.setModel(model);
				}
				// Have selected one reaction then change the title
				// and the first boundary_reaction is selectReactionNumber
				if (RJavaFrame.this.selectBoundaryReactionCount == 1) {
					RJavaFrame.this.boundary_reaction1 = selectReactionNumber;
					frame.setTitle("To choose one boundary reaction");
				}
				// Have selected two reactions:dispose this frame and go to phpp
				// analysis
				else if (RJavaFrame.this.selectBoundaryReactionCount == 2) {
					RJavaFrame.this.boundary_reaction2 = selectReactionNumber;
					frame.dispose();
					RJavaFrame.this.toDoPHPPAnalysis();
				}
			}
		});
		JScrollPane scrollpane = new JScrollPane(jlist);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
	}

	/**
	 * a JFrame that let user choose
	 * 
	 * @return
	 */
	public JFrame selectCarbonReactionForm(final String carbonName) {
		Object[] temp = this.test.getSecondColString("carbon.txt")
				.toArray();
		deleteFile("carbon.txt");
		if (temp.length == 0 || temp[0].equals("0")) {
			JOptionPane.showMessageDialog(null, "This carbon source"
					+ carbonName + "does not exist!!!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		final JFrame frame = new JFrame("Choose the carbon reaction");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 700);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		final JList jlist = new JList(temp);
		jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				RJavaFrame.this.carbon_reaction = "carbon_reaction=\"Glucose "
						+ ((String) jlist.getSelectedValue()).replace("\"", "")
						+ "\"";
				System.out.println(RJavaFrame.this.carbon_reaction);
				frame.dispose();
				RJavaFrame.this.selectBoundaryReactionForm();
			}
		});
		JScrollPane scrollpane = new JScrollPane(jlist);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
		return frame;
	}

	/**
	 * To tile all the windows in desktop
	 * 
	 * @param desktop
	 *            the JDesktopPane that we need to tiled
	 */
	public static void tileWindows(JDesktopPane desktop) {
		// Find out the number of windows
		int frameCount = 0;
		for (JInternalFrame frame : desktop.getAllFrames()) {
			frameCount++;
		}
		// Calculate how much rows, how many columns
		// can tiled window all
		int rows = (int) Math.sqrt(frameCount);
		int cols = frameCount / rows;
		// Windows to add to the other columns
		int extra = frameCount % rows;
		// Calculated tiled internal window size
		int width = desktop.getWidth() / cols;
		// System.out.println("width" + desktop.getWidth());
		int height = desktop.getHeight() / rows;
		// Save tiled windows, each window in the
		// transverse and longitudinal index
		int x = 0;
		int y = 0;
		for (JInternalFrame frame : desktop.getAllFrames()) {
			try {
				// Cancel the internal window maximized, minimized
				frame.setMaximum(false);
				frame.setIcon(false);
				// Put the window at the specified location
				frame.reshape(x * width, y * height, width, height);
				y++;
				// Each drained a col of windows
				if (y == rows) {
					// Start emissions next column's windows
					y = 0;
					x++;
					// Extra window is equal to the number of columns
					// and the rest, all subsequent columns need to
					// arrange a window
					if (extra == cols - x) {
						rows++;
						height = desktop.getHeight() / rows;
					}
				}
			} catch (PropertyVetoException e) {
			}
		}
	}

}
