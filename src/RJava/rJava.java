import java.io.*;
import java.math.BigDecimal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.RMainLoopCallbacks;
/**
 * the class that we need for the R-Java Interface
 * @author Guo Jiexin
 *
 */
class TextConsole implements RMainLoopCallbacks {
	public void rWriteConsole(Rengine re, String text, int oType) {
		System.out.print(text);
	}

	public void rBusy(Rengine re, int which) {
		System.out.println("rBusy(" + which + ")");
	}

	public String rReadConsole(Rengine re, String prompt, int addToHistory) {
		System.out.print(prompt);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String s = br.readLine();
			return (s == null || s.length() == 0) ? s : s + "\n";
		} catch (Exception e) {
			System.out.println("jriReadConsole exception: " + e.getMessage());
		}
		return null;
	}

	public void rShowMessage(Rengine re, String message) {
		System.out.println("rShowMessage \"" + message + "\"");
	}

	@SuppressWarnings("deprecation")
	public String rChooseFile(Rengine re, int newFile) {
		FileDialog fd = new FileDialog(new Frame(),
				(newFile == 0) ? "Select a file" : "Select a new file",
				(newFile == 0) ? FileDialog.LOAD : FileDialog.SAVE);
		fd.show();
		String res = null;
		if (fd.getDirectory() != null)
			res = fd.getDirectory();
		if (fd.getFile() != null)
			res = (res == null) ? fd.getFile() : (res + fd.getFile());
		return res;
	}

	public void rFlushConsole(Rengine re) {
	}

	public void rLoadHistory(Rengine re, String filename) {
	}

	public void rSaveHistory(Rengine re, String filename) {
	}
}
/**
 * 
* @ClassName: rJava
* @Description: TODO
* @author Guo Jiexin
* @date Aug 31, 2012 10:18:08 AM 
*
 */
public class rJava {
	/**
	 * 
	* @ClassName: FinishButtonListener 
	* @Description: the finishButton ActionListener
	* 				that for the RJavaFrame
	* @author Guo Jiexin
	* @date Aug 27, 2012 7:32:25 PM 
	*
	 */
	public class FinishButtonListener implements ActionListener {
		JFrame frame = null;
		rJava test = null;
		public FinishButtonListener(rJava test, JFrame frame) {
			this.frame = frame;
			this.test = test;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.test.isObjtiveSumEquOne())
				this.frame.dispose();
			else
				JOptionPane.showMessageDialog(this.frame, "所有反应目标值总和不等于1",
						"警告", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * @return the needMax
	 */
	public boolean isNeedMax() {
		return NeedMax;
	}

	/**
	 * @param needMax
	 *            the needMax to set
	 */
	public void setNeedMax(boolean needMax) {
		NeedMax = needMax;
	}
	/**
	 * The xml document that user opened
	 */
	private Document document = null;
	public List<Reaction> Reaction_list = new ArrayList<Reaction>();
	public List<Specie> Species_list = new ArrayList<Specie>();
	private boolean NeedMax = false;
	public List<Reaction> boundaryReaction_List = new ArrayList<Reaction>();
	public List<String> mAllGene_List = new ArrayList<String>();
	public List<String> mFluxes = new ArrayList<String>();
	public List<Integer> boundaryReactionNumber = new ArrayList<Integer>();
	public int rowToIgnore = 0;
	public int selectedReaction = 0;
	/**
	 * Use name to find the specie's place in species_list
	 * @param name
	 * @return positive or zero int if find,-1 if not found
	 */
	public int findSpecieByName(String name) {
		for (int i = 0; i < this.Species_list.size(); i++)
			if (this.Species_list.get(i).id.equals(name))
				return i;
		return -1;
	}
	/**
	 * Return is the specie id is in metabolite list
	 * @param specieName
	 * @return
	 */
	public boolean isSpecieIdInMetabolite_list(String specieName) {
		for (int i = 0; i < this.Species_list.size() - this.rowToIgnore; i++) {
			if (this.Species_list.get(i).id.equals(specieName))
				return true;
		}
		return false;
	}
	/**
	 * Return a file 's second col(The first line is ignore) 
	 * because the R's writefile format
	 * @param filename
	 * @return A list of String that is from the filename's file's second col
	 * 			(except the first line)
	 */
	public List<String> getSecondColString(String filename) {
		List<String> toReturn = new ArrayList<String>();
		File file = new File(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1) {
					String[] temp = tempString.split(" ");
					toReturn.add(temp[1]);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return toReturn;
	}

	public JInternalFrame ShowFvaSolveForm() {
		JInternalFrame frame = new JInternalFrame("FVA Solve", true, true,
				true, true); // create a titled JFrame Object
		rJava.initDefaultFrame(frame);
		frame.setSize(600, 600);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(this.Reaction_list.size() + 5, 5));
		List<String> list1 = this.getSecondColString("1.txt");// "Wildtype_fluxes.txt");
		List<String> list2 = this.getSecondColString("2.txt");// "Mutant_fluxes.txt");
		List<String> list3 = this.getSecondColString("3.txt");
		List<String> list4 = this.getSecondColString("4.txt");
		List<String> list5 = this.getSecondColString("5.txt");
		RJavaFrame.deleteFile("1.txt");
		RJavaFrame.deleteFile("2.txt");
		RJavaFrame.deleteFile("3.txt");
		RJavaFrame.deleteFile("4.txt");
		RJavaFrame.deleteFile("5.txt");
		JLabel label1 = new JLabel("reaction_list");
		label1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label2 = new JLabel("lower flux limit");
		label2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label3 = new JLabel("wild-type flux");
		label3.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label4 = new JLabel("upper flux limit");
		label4.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label5 = new JLabel("flux span");
		label5.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		System.out.println(list1.size());
		for (int i = 0; i < list1.size(); i++) {
			panel.add(new JLabel(list1.get(i)));
			panel.add(new JLabel(list2.get(i)));
			panel.add(new JLabel(list3.get(i)));
			panel.add(new JLabel(list4.get(i)));
			panel.add(new JLabel(list5.get(i)));
		}
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setViewportView(panel);
		frame.getContentPane().add(scrollPane);
		return frame;
	}

	public JInternalFrame ShowReactionDelectionForm(String wildtypeFileName,
			String mutantFileName) {
		JInternalFrame frame = new JInternalFrame("Reaction Deletion", true,
				true, true, true); // create a titled JFrame Object
		rJava.initDefaultFrame(frame);
		frame.setSize(700, 500);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(this.Reaction_list.size() + 5, 3, 50, 5));
		List<String> wildtype = this.getSecondColString(wildtypeFileName);// "Wildtype_fluxes.txt");
		List<String> mutant = this.getSecondColString(mutantFileName);// "Mutant_fluxes.txt");
		RJavaFrame.deleteFile(wildtypeFileName);
		RJavaFrame.deleteFile(mutantFileName);
		JLabel label1 = new JLabel("Reaction id");
		label1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label2 = new JLabel("wildtype fluxes");
		label2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		JLabel label3 = new JLabel("mutant fluxes");
		label3.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		for (int i = 0; i < wildtype.size(); i++) {
			JLabel l1 = new JLabel(this.Reaction_list.get(i).id);
			JLabel l2 = new JLabel(wildtype.get(i));
			JLabel l3 = new JLabel(mutant.get(i));
			if (!wildtype.get(i).equals(mutant.get(i))) {
				l1.setForeground(Color.red);
				l2.setForeground(Color.red);
				l3.setForeground(Color.red);
			}
			panel.add(l1);
			panel.add(l2);
			panel.add(l3);
		}
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setViewportView(panel);
		frame.getContentPane().add(scrollPane);
		return frame;
	}

	public void ShowReactionSelectForm(RJavaFrame rjFrame) {
		JFrame frame = new JFrame("Choose the reaction that you want to edit"); // create a titled JFrame Object
		rJava.initDefaultFrame(frame);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel tempLabel = new JLabel("");
		tempLabel.setPreferredSize(new Dimension(400, 70));
		tempLabel.setVisible(true);
		panel.add(tempLabel);
		JLabel label1 = new JLabel("which reaction to edit?");
		label1.setVisible(true);
		panel.add(label1);
		String[] temp = new String[this.Reaction_list.size()];
		for (int i = 0; i < this.Reaction_list.size(); i++)
			temp[i] = this.Reaction_list.get(i).id;
		final JComboBox selectReactionJComboBox = new JComboBox(temp);
		selectReactionJComboBox.setMaximumRowCount(15);
		selectReactionJComboBox.setVisible(true);
		selectReactionJComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rJava.this.selectedReaction = selectReactionJComboBox
						.getSelectedIndex();
			}
		});
		panel.add(selectReactionJComboBox);
		JButton editButton = new JButton("edit");
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rJava.this.editReactionForm(rJava.this.selectedReaction);
			}
		});
		JButton finishButton = new JButton("finish");
		finishButton.addActionListener(new FinishButtonListener(this, frame));
		panel.add(editButton);
		panel.add(finishButton);
	}

	/**
	 * initlaze jframe
	 * @param frame         
	 */
	public static void initDefaultFrame(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Action is to exit the window when close buttonclick
		frame.setSize(400, 300);
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // The size of the display 
		Dimension frameSize = frame.getSize(); // Get window size 
		if (frameSize.width > displaySize.width)// Width of the window can not be larger than the width of the display
			frameSize.width = displaySize.width; 
		if (frameSize.height > displaySize.height)//The height of the window can not be greater than the height of the display
			frameSize.height = displaySize.height; 
		frame.setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2); // Set window centered 
		frame.setVisible(true);// Settings window is visible
	}
	
	public static void initDefaultFrame(JInternalFrame frame) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Action is to exit the window when close buttonclick
		frame.setSize(400, 300);
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // The size of the display
		Dimension frameSize = frame.getSize(); // Get window size 
		if (frameSize.width > displaySize.width)// Width of the window can not be larger than the width of the display
			frameSize.width = displaySize.width;
		if (frameSize.height > displaySize.height)//The height of the window can not be greater than the height of the display
			frameSize.height = displaySize.height; 
		frame.setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2); // Set window centered 
		frame.setVisible(true);// Settings window is visible
	}
	/**
	 * Because the doubles can not compare equal accurately,use this function to make it right
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean doubleEqual(double p1, double p2) {
		if (Math.abs(p1 - p2) < 0.00001)
			return true;
		else
			return false;
	}

	public boolean isObjtiveSumEquOne() {
		// double compare = (Math.abs(p1-p2)<0.00001)?p2:p1;
		double sum = 0;
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			sum += this.Reaction_list.get(i).OBJECTIVE_COEFFICIENT;
		}
		if (doubleEqual(sum, 1.0))
			return true;
		else
			return false;
	}

	/**
	 * Create the edit reaction frame(the reactionNumber's) 
	 * @param reactionNumber which reaction that user want to edit
	 */
	public void editReactionForm(final int reactionNumber) {
		final JFrame frame = new JFrame("Edit"
				+ this.Reaction_list.get(reactionNumber).id);
		initDefaultFrame(frame);
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setLayout(new GridLayout(4, 2, 5, 50)); // new
														// FlowLayout(FlowLayout.RIGHT));
		JLabel label1 = new JLabel("Objective ");
		panel.add(label1);
		final JTextField objTextField = new JTextField(
				String.valueOf(this.Reaction_list.get(reactionNumber).OBJECTIVE_COEFFICIENT));
		panel.add(objTextField);
		JLabel label2 = new JLabel("Upper_Val ");
		panel.add(label2);
		final JTextField upperValTextField = new JTextField(
				String.valueOf(this.Reaction_list.get(reactionNumber).upper_val));
		panel.add(upperValTextField);
		JLabel label3 = new JLabel("Lower_Val ");
		panel.add(label3);
		final JTextField lowerValTextField = new JTextField(
				String.valueOf(this.Reaction_list.get(reactionNumber).lower_val));
		panel.add(lowerValTextField);
		JButton finishButton = new JButton("finish");
		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double temDouble = Double.parseDouble(objTextField.getText());
				if (!rJava.this.doubleEqual(
						rJava.this.Reaction_list.get(reactionNumber).OBJECTIVE_COEFFICIENT,
						temDouble))
					rJava.this.Reaction_list.get(reactionNumber).OBJECTIVE_COEFFICIENT = temDouble;
				temDouble = Double.parseDouble(upperValTextField.getText());
				if (!rJava.this.doubleEqual(
						rJava.this.Reaction_list.get(reactionNumber).upper_val,
						temDouble))
					rJava.this.Reaction_list.get(reactionNumber).upper_val = temDouble;
				temDouble = Double.parseDouble(lowerValTextField.getText());
				if (!rJava.this.doubleEqual(
						rJava.this.Reaction_list.get(reactionNumber).lower_val,
						temDouble))
					rJava.this.Reaction_list.get(reactionNumber).lower_val = temDouble;
				frame.dispose();
			}
		});
		JLabel temp = new JLabel();
		temp.setVisible(false);
		panel.add(temp);
		panel.add(finishButton);
	}

	public JInternalFrame showFBA_solveForm(final Rengine re) {
		JInternalFrame frame = new JInternalFrame("Fba Solve", true, true,
				true, true); 
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		frame.setLocation(0, 0);
		frame.setSize(700, 700);
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.width > displaySize.width)
			frameSize.width = displaySize.width;
		if (frameSize.height > displaySize.height)
			frameSize.height = displaySize.height;
		frame.setLocation((displaySize.width - frameSize.width) / 2,
				(displaySize.height - frameSize.height) / 2);
		frame.setVisible(true);
		JPanel panel = new JPanel();// (JPanel) frame.getContentPane();
		panel.setLayout(new GridLayout(this.Reaction_list.size() + 1 + 1, 2,
				50, 5));
		JScrollPane scrollPane = new JScrollPane(panel);

		JLabel label1 = new JLabel("reaction name");
		label1.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		label1.setVisible(true);
		panel.add(label1);
		JLabel label2 = new JLabel("fluxes");
		label2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(label2);
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			JLabel reaction = new JLabel(this.Reaction_list.get(i).id);
			//BigDecimal bg=new BigDecimal(this.mFluxes.get(i).toString()); 
			JLabel fluxes = new JLabel(this.mFluxes.get(i));
			panel.add(reaction);
			panel.add(fluxes);
		}
		scrollPane.setViewportView(panel);
		frame.getContentPane().add(scrollPane);

		return frame;
	}

	public void addBiomassToPanel(String fileName, JPanel panel) {
		JLabel title = new JLabel("Biomass Reaction");
		title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(title);
		JLabel templ = new JLabel("              ");
		templ.setVisible(false);
		panel.add(templ);
		if (fileName.equals(""))
			fileName = "biomass.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1 && line <= this.Reaction_list.size()) {
					String[] temp = tempString.split(" ");
					//System.out.println(line + " " + this.Reaction_list.size());
					JLabel reaction = new JLabel(
							this.Reaction_list.get(line - 2).id);
					panel.add(reaction);
					JLabel biomass = new JLabel(temp[1]);
					panel.add(biomass);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			RJavaFrame.deleteFile(fileName);
		}		
	}

	public void addLethalDeletionsToPanel(String fileName, JPanel panel) {
		JLabel title = new JLabel("Lethal Deletions");
		title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(title);
		JLabel templ = new JLabel("              ");
		templ.setVisible(false);
		panel.add(templ);
		if (fileName.equals(""))
			fileName = "lethal.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1) {
					String[] temp = tempString.split(" ");
					int reactionNum = Integer.parseInt(temp[1]) - 1;
					JLabel reaction = new JLabel(
							this.Reaction_list.get(reactionNum).id);
					JLabel nullTemp = new JLabel("           ");
					nullTemp.setVisible(false);
					panel.add(reaction);
					panel.add(nullTemp);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			RJavaFrame.deleteFile(fileName);
		}
	}

	public void addSubOptimalDelsToPanel(String fileName, JPanel panel) {
		JLabel title = new JLabel("Sub Optimal Dels");
		title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(title);
		JLabel templ = new JLabel("              ");
		templ.setVisible(false);
		panel.add(templ);
		if (fileName.equals(""))
			fileName = "sub_optimal_dels.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1) {
					String[] temp = tempString.split(" ");
					int reactionNum = Integer.parseInt(temp[1]) - 1;
					JLabel reaction = new JLabel(
							this.Reaction_list.get(reactionNum).id);
					JLabel nullTemp = new JLabel("           ");
					nullTemp.setVisible(false);
					panel.add(reaction);
					panel.add(nullTemp);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			RJavaFrame.deleteFile(fileName);			
		}
	}

	public void addSuperOptimalDelsToPanel(String fileName, JPanel panel) {
		JLabel title = new JLabel("Super Optimal Dels");
		title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
		panel.add(title);
		JLabel templ = new JLabel("              ");
		templ.setVisible(false);
		panel.add(templ);
		if (fileName.equals(""))
			fileName = "super_optimal_dels.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1) {
					String[] temp = tempString.split(" ");
					int reactionNum = Integer.parseInt(temp[1]) - 1;
					JLabel reaction = new JLabel(
							this.Reaction_list.get(reactionNum).id);
					JLabel nullTemp = new JLabel("           ");
					nullTemp.setVisible(false);
					panel.add(reaction);
					panel.add(nullTemp);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			RJavaFrame.deleteFile(fileName);
		}
	}

	public void readFluxByLines(String fileName) {
		if (fileName.equals(""))
			fileName = "fluxes.txt";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				if (line != 1) {
					String[] temp = tempString.split(" ");
					//this.mFluxes.add(Double.parseDouble(temp[1]));
					this.mFluxes.add(temp[1]);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			RJavaFrame.deleteFile(fileName);
		}
		
	}

	public String GetMatrix() {
		StringBuilder matrix = new StringBuilder("matr=matrix(c(");
		final int row = this.Species_list.size();// -this.rowToIgnore;
		//System.out.println(row);
		final int col = this.Reaction_list.size();
		//System.out.println(col);
		BigDecimal[][] numMatrix = new BigDecimal[row][col];		
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			for (int j = 0; j < Reaction_list.get(i).mReactants.size(); j++) {
				int reactant = this
						.findSpecieByName(Reaction_list.get(i).mReactants
								.get(j).species);
				numMatrix[reactant][i] = Reaction_list.get(i).mReactants.get(j).stoichiometry;
			}
			for (int j = 0; j < Reaction_list.get(i).mProducts.size(); j++) {
				int product = this
						.findSpecieByName(Reaction_list.get(i).mProducts.get(j).species);
				numMatrix[product][i] = Reaction_list.get(i).mProducts.get(j).stoichiometry.negate();
			}			
		}
		//System.out.println(col);
		for (int j = 0; j < col; j++)
		{
			for (int i = 0; i < row - this.rowToIgnore; i++) {
				if(numMatrix[i][j]==null)
					matrix.append('0');
				else
					matrix.append(numMatrix[i][j]);
				if (!(i == row - this.rowToIgnore - 1 && j == col - 1))
					matrix.append(",") ;
				//System.out.println("Matrix "+j+" "+col+" "+i+" "+(row - this.rowToIgnore));
			}			
		}
		matrix.append("),nrow=").append(row - this.rowToIgnore).append(",ncol=").append(col).append(")");
		return matrix.toString();
	}

	public void clearAll() {
		if (this.document != null)
			this.document.clearContent();
		this.NeedMax = false;
		this.Reaction_list.clear();
		this.Species_list.clear();
		this.rowToIgnore = 0;
		this.mAllGene_List.clear();
		this.mFluxes.clear();
		this.boundaryReaction_List.clear();
		this.boundaryReactionNumber.clear();
	}

	public void ReadSbml(String fileName) {
		this.clearAll();
		if (fileName.equals(""))
			return;
			//fileName = "Ec_core_flux1.xml";
		SAXReader reader = new SAXReader();
		System.out.println(fileName);
		reader.setValidation(false);
		try {
			document = reader.read(new File(fileName));
		} catch (DocumentException e) {
			System.out.println("Document open error in ReadSbml(rJava.java)");
			JOptionPane.showMessageDialog(null, "the xml format error，program will be closed！", "ERROR",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		this.getReaction_list();
		this.getSpecies_list();
		this.getBoundryReaction_list();
	}

	public String GetGPR() {
		StringBuilder temp =new StringBuilder("gpr=c(");// \"g1\",\"g2\",\"g1\")"
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			temp.append("\"");
			temp.append(this.Reaction_list.get(i).mGeneName);
			temp.append("\"");
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
		}
		temp.append(")");
		return temp.toString();
	}

	public String GetReaction_list() {
		StringBuilder temp =new StringBuilder("reaction_list=c(");
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			temp.append("\"");
			temp.append(Reaction_list.get(i).name);
			temp.append("\"");
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
		}
		temp.append(")");
		return temp.toString();
	}

	public String GetSubSystem() {
		StringBuilder temp =new StringBuilder("subsystem=c(");
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			temp.append("\"");
			temp.append(Reaction_list.get(i).SUBSYSTEM);
			temp.append("\"");
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
			//System.out.println(i+" "+this.Reaction_list.size());
		}
		temp.append(")");
		return temp.toString();
	}

	public String Getboundaryreaction() {
		StringBuilder temp =new StringBuilder("boundaryreaction=c(");
		for (int i = 0; i < this.boundaryReaction_List.size(); i++) {
			temp.append("\"");
			temp.append(this.boundaryReaction_List.get(i).id);
			temp.append("\"");
			if (i < this.boundaryReaction_List.size() - 1)
				temp.append(",");
			//System.out.println("boundaryReaction "+i+" "+this.boundaryReaction_List.size());
		}
		// for(int i=0;i<this.boundaryReactionNumber.size();i++)
		temp.append(")");
		return temp.toString();
	}

	public String GetMetabolite_Name() {
		StringBuilder temp =new StringBuilder("metabolite_name=c(");
		for (int i = 0; i < this.Species_list.size() - this.rowToIgnore; i++) {
			temp.append("\"");
			temp.append(Species_list.get(i).name);
			temp.append("\"");
			if (i < this.Species_list.size() - this.rowToIgnore - 1)
				temp.append(",");
		}
		temp.append(")");
		return temp.toString();
	}

	public String GetMax(boolean flag) {
		return "max=TRUE";
	}

	private void getSpecies_list() {
		List<Element> temp = document.getRootElement().elements("model");
		temp = temp.get(0).elements("listOfSpecies");
		List<Element> species = temp.get(0).elements("species");
		for (Element ele : species) {
			Specie spe = new Specie(ele);
			if (spe.boundaryCondition == true)
				this.rowToIgnore++;
			this.Species_list.add(spe);
		}
		temp = null;
	}

	private void getReaction_list() {
		List<Element> temp = document.getRootElement().elements("model");
		temp = temp.get(0).elements("listOfReactions");
		List<Element> reactions = temp.get(0).elements("reaction");
		for (Element ele : reactions)
			this.Reaction_list.add(new Reaction(ele));
		temp = null;
	}

	private void getBoundryReaction_list() {
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			Reaction reaction = this.Reaction_list.get(i);
			for (int j = 0; j < reaction.mProducts.size(); j++) {
				if (!this
						.isSpecieIdInMetabolite_list(reaction.mProducts.get(j).species)) {
					this.boundaryReaction_List.add(reaction);
					this.boundaryReactionNumber.add(i);
					break;
				}
			}
		}
	}

	public String GetAllGenes() {		
		StringBuilder temp = new StringBuilder("all_genes=c(");
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < this.Reaction_list.size(); i++)
			for (int j = 0; j < this.Reaction_list.get(i).mGeneNames.size(); j++)
				set.add(this.Reaction_list.get(i).mGeneNames.get(j));
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			this.mAllGene_List.add(it.next().toString());
		}
		for (int i = 0; i < this.mAllGene_List.size(); i++) {
			temp.append("\"");
			temp.append(this.mAllGene_List.get(i));
			temp.append("\"");
			if (i < this.mAllGene_List.size() - 1)
				temp.append(",");
		}
		temp.append(")");
		return temp.toString();
	}
	/**
	 * Get the RxnGeneMatrix
	 * @return A string of R's matrix format
	 */
	public String GetRxnGeneMat() {
		StringBuilder matrix =new StringBuilder("rxnGeneMat=matrix(c(");
		int col = this.mAllGene_List.size();
		int row = this.Reaction_list.size();
		int[][] numMatrix = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (Reaction_list.get(i).mGeneName
						.indexOf(mAllGene_List.get(j)) == -1) {
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
				//System.out.println("RxnMatrix "+i+" "+row+" "+j+" "+col);
			}
		}
		matrix.append("),nrow=").append(row).append(",ncol=").append(col).append(")");
		return matrix.toString();
	}

	public String GetObj(/* number from 0 */) {
		StringBuilder temp = new StringBuilder("obj=c(");
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			if (this.Reaction_list.get(i).OBJECTIVE_COEFFICIENT != 0)
				temp.append('1');
			else
				temp.append('0');
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
			//System.out.println(i+" "+this.Reaction_list.size());
		}
		temp.append(")");
		return temp.toString();
	}

	public String GetLowerVal() {
		StringBuilder temp = new StringBuilder("lower_val=c(");
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			temp.append(this.Reaction_list.get(i).lower_val) ;
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
			//System.out.println(i+" "+this.Reaction_list.size());
		}
		temp.append(")");
		return temp.toString();
	}

	public String GetUpperVal() {
		StringBuilder temp = new StringBuilder("upper_val=c(");
		for (int i = 0; i < this.Reaction_list.size(); i++) {
			temp.append(this.Reaction_list.get(i).upper_val);
			if (i < this.Reaction_list.size() - 1)
				temp.append(",");
		}
		temp.append(")");
		return temp.toString();
	}
}
