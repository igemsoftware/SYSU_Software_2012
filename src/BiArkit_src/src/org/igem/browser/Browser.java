/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Browser.java
 *
 * Created on Jul 21, 2012, 2:13:18 PM
 */
package org.igem.browser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdom.Element;
import org.sysu.igem.riboswitch.Riboswitch;
import org.sysu.igem.siRNA.siRNA;
import org.sysu.igem.utils.OpenFastaFile;
import org.sysu.igem.utils.ReadFileRandom;
import org.sysu.igem.utils.ReadXml;
import org.sysu.igem.riboswitch.jPanelUp;
import twaver.TWaverUtil;
import org.sysu.igem.utils.GetGenomeFromXml;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.GCircle.MyJFrame;
import org.KeggNetwork.KGMLReader;
import org.sysu.igem.utils.FileNode;
import org.sysu.igem.utils.HistoryFile;
import org.sysu.igem.utils.setJTree;

/**
 * The main frame
 *
 * @author q
 */
public class Browser extends javax.swing.JFrame {

    ReadFileRandom file;
    Object[] obj;
    String[] head;
    DefaultTableModel model;
    DefaultMutableTreeNode treeRoot;
    DefaultMutableTreeNode parent;
    int id;
    String FilePath = null;
    String xmlFilePath = null;
    /**
     * Colors of the button of genome
     */
    final Color[] colors = {new java.awt.Color(255, 127, 36), new java.awt.Color(255, 165, 0), new java.awt.Color(255, 127, 36), new java.awt.Color(255, 165, 0)};
    /**
     * @param counter
     * @description use to determine the current character, use jslider to
     * change
     */
    public int position;
    /**
     * @param size
     * @description to ensure the zoom size now
     */
    int size;
    /**
     * @param xmlData
     */
    List<Genome> xmlData;
    //Border  border=null;
    private KGMLReader kgmlReader;

    /**
     * Creates new form Browser
     */
    public Browser() throws IOException {
//        final StartFrame startFrame = new StartFrame();
//        startFrame.setVisible(true);        
//        //Timer timer = new Timer();
//        int  timeToDelay= 2000;
//        try {
//            Thread.sleep(timeToDelay);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //new StartFrame().setVisible(true);
        position = 0;
        size = 20;
        this.setResizable(false);
        File imagefile = new File("logo32.logo");
        if (imagefile.exists()) {
            Image IconImage = ImageIO.read(imagefile);
            this.setIconImage(IconImage);
        }
        //
        StartFrame startframe = new StartFrame(this);
        startframe.setVisible(true);
        //
        //        this.setVisible(true);
        initComponents();
        String currentPath = System.getProperty("user.dir");
        currentPath = "file:/" + currentPath + "\\license.dat";
        System.out.println(currentPath);
        TWaverUtil.validateLicense(currentPath);
        //TWaverUtil.validateLicense(currentPath);
        // this.setVisible(true);
        initSlider();
        initMyComponents();
        initJtree();
        initbiobrick("");
        initRiboswitch();
        initSiRNA();
        xmlData = new ArrayList();
        this.jTree1.setVisible(false);

        //
        jTabbedPane1.setUI(new ChangeColor());
        //

    }

    /**
     * The usefulness of this internal class that in the JTree node select occurred on the selected file format conversion
     */
    private class MyFile {

        private File file;

        public MyFile(File file) {
            this.file = file;
        }

        @Override
        public String toString() {
            String name = file.getName().trim();
            if (name.length() == 0) {
                name = file.getAbsolutePath().trim();
            }
            return name;
        }
    }

    /**
     * Initialize jtree
     */
    private void initJtree() {
        jTree1.setCellRenderer(new MyTreeCellRenderer());
        new setJTree(jTree1, new File("biobrick"));

        jTree1.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent arg0) {

                if (jTree1.getLastSelectedPathComponent() == null) {
                    return;
                }
                if (jTree1.getLastSelectedPathComponent().toString().trim().equals("Local")) {
                    return;
                }
                TreePath path = arg0.getPath();
                if (path.getLastPathComponent() instanceof FileNode) {
                    FileNode node = (FileNode) path.getLastPathComponent();
                    if (!node.isExplored()) {
                        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
                        node.explore();
                        model.nodeStructureChanged(node);
                    }
                }
                String select = "";//arg0.getPath().getPathComponent(arg0.getPath().getPathCount() - 1).toString();
                Object[] paths = arg0.getPath().getPath();
                for (int i = paths.length - 1; i >= 0; i--) {
                    if (i != paths.length - 1) {
                        select = paths[i] + "\\" + select;
                    } else {
                        select = paths[i] + select;
                    }
                }
                System.out.println(select);
                File f = new File(select);
                if (f.exists() && !f.isDirectory()) {
                    initbiobrick(select);
                }
            }
        });


    }
    private GetGenomeFromXml xmlFile = null;

    /**
     * Initialize Menu item
     */
    private void initMyComponents() {
        openFastaAndXmlItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("open fastaFile begin");
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JFileChooser jFileChooser = new JFileChooser();
                try {
                    jFileChooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return true;
                    }

                    @Override
                    public String getDescription() {
                        return "fasta file";
                    }
                });
                jFileChooser.setDialogTitle("Open Fasta File");
                jFileChooser.showOpenDialog(null);
                if (jFileChooser.getSelectedFile() == null) {
                    return;
                }
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FilePath = jFileChooser.getSelectedFile().getAbsolutePath();
                try {
                    HistoryFile.setPathToHistoryFile("history.txt", jFileChooser.getSelectedFile().getParent());
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
                file = new ReadFileRandom(FilePath);
                file.setReadSize(size);
                obj = file.readFileByRandomAccess(0);
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    jFileChooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        String nameString = f.getName();
                        return nameString.toLowerCase().endsWith(".xml") || f.isDirectory();
                    }

                    @Override
                    public String getDescription() {
                        return "Xml file";
                    }
                });
                jFileChooser.setDialogTitle("Open xml File");
                jFileChooser.showOpenDialog(null);
                try {
                    HistoryFile.setPathToHistoryFile("history.txt", jFileChooser.getSelectedFile().getParent());
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (jFileChooser.getSelectedFile() == null) {
                    return;
                }
                xmlFilePath = jFileChooser.getSelectedFile().getAbsolutePath();
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!xmlFilePath.equals("")) {
                    if (xmlFile == null) {
                        xmlFile = new GetGenomeFromXml(xmlFilePath);
                    } else {
                        xmlFile.setXmlData(xmlFilePath);
                    }
                    Browser.this.xmlData = xmlFile.getXmlData();
                    Browser.this.position = 51;
                    Browser.this.jslider.setValue(51);
                    if (Browser.this.buttonsPanel == null) {
                        Browser.this.buttonsPanel = new JPanel();
                        Browser.this.buttonsPanel.setLayout(null);
                        Browser.this.buttonsPanel.setSize(jScrollPane4.getWidth(), jScrollPane4.getHeight());
                    }
                    int left = Browser.this.createButtonsOnPanelByCurrentHeadAndTail(Browser.this.buttonsPanel, position);//position + size);
                    Browser.this.buttonsPanel.setPreferredSize(new Dimension(left, jScrollPane4.getHeight()));
                    jScrollPane4.getViewport().setView(Browser.this.buttonsPanel);
                    jScrollPane4.validate();
                }
            }
        });

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

        table = new JTable();
        table.setRowSelectionAllowed(false); //set you can select a single cell
        table.setColumnSelectionAllowed(true);   //Select the column directly
        table.setSelectionBackground(Color.BLUE);   //Set the background color of the selected row
        table.setSelectionForeground(Color.white);      //Modify the foreground color of the selected cell
        table.setShowGrid(false);
        renderer.setPreferredSize(new Dimension(0, 0));
        table.getTableHeader().setDefaultRenderer(renderer);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("row :" + table.getSelectedColumn());

                int no = 0;
                boolean on = false;
                for (Genome g : xmlData) {
                    no++;
                    if (g.isCds(position)) {
                        on = true;
                        showGenomeInformaiton(g);
                        break;
                    }
                }
            }
        });
        cdsTable = new JTable();
        cdsTable.setRowSelectionAllowed(false); //set you can select a single cell
        cdsTable.setColumnSelectionAllowed(true);   //Select the column directly
        cdsTable.setSelectionBackground(Color.BLUE);   //Set the background color of the selected row
        cdsTable.setSelectionForeground(Color.BLACK);      //Modify the foreground color of the selected cell
        cdsTable.setShowGrid(false);
        renderer.setPreferredSize(new Dimension(0, 0));
        cdsTable.getTableHeader().setDefaultRenderer(renderer);
//        cdsTable.addMouseListener(new MouseAdapter() {
//
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("row :" + table.getSelectedColumn());
//            }
//        });

        jLabel2.setText("");
        jLabel4.setText("");
        jLabel6.setText("");
        jLabel8.setText("");
        jLabel10.setText("");
        jLabel12.setText("");
        jLabel24.setText("");
        jLabel25.setText("");
        jLabel26.setText("");
        jLabel27.setText("");
        jLabel28.setText("");
        jLabel29.setText("");
        jLabel30.setText("");
        jLabel31.setText("");
        jLabel32.setText("");
        jLabel33.setText("");
        jLabel34.setText("");
        this.jScrollPane6.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jLabel35.setText("");
        jLabel36.setText("");

//        jLabel3.setText("");
//        jLabel5.setText("");
    }

    /**
     *to init hte biobrick part
     * @param path
     */
    public void initbiobrick(String path) {
//        if (path.equals("")) {
//            path = "biobrick/BBa_B0015.xml";
//        }
        if (path.isEmpty()) {
            return;
        }
        ReadXml read = new ReadXml();
        System.out.println(path);
        List<Element> l = read.readXMLContent(path).get(0).getChildren();
        int colorSize = colors.length;

        /* For Draw Buttons */
        JPanel panel = new JPanel();
        int top = 1;//1 pixels from the start painting from the panel above the number 
        int left = 0;//Began to draw from the left a few pixels
        int tempright = 0;
        //final int height = 50;
        final int separatWidth = 40;//The interval between the two left and right buttons
        int i = -1;//In order to draw out the line just one less than the number of rectangular
        for (Element e : l) {
            //List<JButton> buttons = new ArrayList<JButton>();
            i++;
            String name = e.getChildText("part_name") + "|" + e.getChildText("part_short_desc");
            int buttonWidth = name.length() * 5;
            String buttonText = "<html><font style=\"color:white;\">" + e.getChildText("part_name") + "|" + e.getChildText("part_short_desc") + "</font></html>";
            JButton button1 = new JButton(buttonText);
            button1.setFocusable(false);
            button1.setBackground(colors[i % colorSize]);
            button1.setBounds(left, top, buttonWidth, 50);
            button1.setVisible(true);
            panel.add(button1);
            tempright = left + buttonWidth;
            if (i < l.size() - 1) {
                this.AddLine(panel, tempright, separatWidth, top + 50 / 2);
            }
            left = left + buttonWidth + separatWidth;

            jLabel24.setText(e.getChildText("part_id"));
            jLabel25.setText(e.getChildText("part_name"));
            jLabel26.setText(e.getChildText("part_short_name"));
            jLabel27.setText(e.getChildText("part_short_desc"));
            jLabel28.setText(e.getChildText("part_type"));
            jLabel29.setText(e.getChildText("part_status"));
            jLabel30.setText(e.getChildText("part_results"));
            jLabel31.setText(e.getChildText("part_nickname"));
            jLabel32.setText(e.getChildText("part_rating"));
            jLabel33.setText(e.getChildText("part_entered"));
            jLabel34.setText(e.getChildText("part_author"));
            this.jScrollPane6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
            jLabel35.setText(e.getChildText("best_quality"));

            String twins = "";
            List<Element> list = e.getChildren("twins");
            //System.out.println(list.size());
            if (!list.isEmpty()) {
                List<Element> list2 = list.get(0).getChildren("twin");
                if (!list2.isEmpty()) {
                    for (Element ee : list2) {
                        twins += ee.getValue();
                        twins += " ";
                    }
                    jLabel36.setText(twins);
                }
            }
        }
        panel.setPreferredSize(new Dimension(left, 50));
        jScrollPane5.getViewport().setView(panel);
        jScrollPane5.validate();
    }
    public final JSlider jslider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    public boolean IsGeneButtonClick = false;
    public JPanel buttonsPanel = null;

    /**
     * With automatic recovery jslider
     */
    private void initSlider() {
        //jslider.setBorder(new Border());

        //
        jslider.setUI(new MySliderUI(jslider));
        jslider.setPreferredSize(new Dimension(400, 40));
        jslider.setBackground(new Color(255, 255, 255));
        zoomSlider.setUI(new MySliderUI(zoomSlider));
        //
        jslider.setMajorTickSpacing(10);
        jslider.setMinorTickSpacing(1);
        jslider.setPaintTicks(false);
        jslider.setPaintLabels(false);
        jslider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jslider.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                jslider.setValue(50);
            }
        });
        jslider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (!IsGeneButtonClick) {
                    int currentPosition = jslider.getValue();
                    if (currentPosition != 50 && position + (currentPosition - 50) > 0) {
                        position += (currentPosition - 50);
                        position = position - position % 3;
                    }
                    System.out.println("Slider Position:" + position);
                    showATGC();
                    //int maxLength = file.getFileSize() - file.getFirstLength();
                    //double temp = Browser.this.zoomSlider.getValue() / 20.0; 
                    //double percent = (position / (double) (maxLength) / parameter);
                    //System.out.println(percent);
                    //int scrollLength = Browser.this.jScrollPane4.getHorizontalScrollBar().getMaximum();
                    //Browser.this.jScrollPane4.getHorizontalScrollBar().setValue((int) (scrollLength * percent));
                    if (Browser.this.buttonsPanel == null) {
                        Browser.this.buttonsPanel = new JPanel();
                        Browser.this.buttonsPanel.setLayout(null);
                        Browser.this.buttonsPanel.setSize(jScrollPane4.getWidth(), jScrollPane4.getHeight());
                    }
                    int left = Browser.this.createButtonsOnPanelByCurrentHeadAndTail(Browser.this.buttonsPanel, position);//position + size);
                    Browser.this.buttonsPanel.setPreferredSize(new Dimension(left, jScrollPane4.getHeight()));
                    jScrollPane4.getViewport().setView(Browser.this.buttonsPanel);
                    jScrollPane4.validate();
                } else {
                    int currentPosition = jslider.getValue();
                    if (currentPosition != 50 && position + (currentPosition - 50) > 0) {
                        position += (currentPosition - 50);
                        position = position - position % 3;
                    }
                    System.out.println("Slider Position:" + position);
                    showATGC();
                    System.out.println("IsGeneButtonClick");
                }
            }
        });

        sliderPane.setViewportView(jslider);
    }

    /**
     * Write a table for display ATGC
     */
    private void showATGC() {
        if (FilePath == null) {
            return;
        }
        jLabel3.setText(Integer.toString(position + 1));
        jLabel5.setText(Integer.toString(position + size));
        String[] headers = new String[size];
        for (int i = 0; i < size; i++) {
            headers[i] = Integer.toString(i + 1);
        }
        DefaultTableModel model = new DefaultTableModel(file.readFileByRandomAccess(position), headers) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);
        jScrollPane1.setViewportView(table);
        // jScrollPane1.setBackground(Color.white);
        getFromXml();
    }

    /**
     * get int from string
     *
     * @param string
     * @return numbers
     */
    public static int StringtoInt(String string) {
        int result = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                result *= 10;
                result += string.charAt(i) - '0';
            }
        }
        return result;
    }

    /**
     * the actionListener that use by the gene button
     */
    public class buttonActlistener implements ActionListener {

        Browser brow;
        public int head = 0;

        public buttonActlistener(int i, Browser brow) {
            this.head = i;
            this.brow = brow;
        }

        public void actionPerformed(ActionEvent e) {
            Browser.this.position = head;
            for (int i = 0; i < brow.xmlData.size(); i++) {
                if (brow.xmlData.get(i).getHead() == head) {
                    brow.showGenomeInformaiton(brow.xmlData.get(i));
                    break;
                }
            }
            brow.IsGeneButtonClick = true;
            Browser.this.jslider.setValue(Browser.this.position - 50);
            Browser.this.jslider.setValue(50);
            brow.IsGeneButtonClick = false;
            System.out.print(head);
        }
    }

    /**
     * to paint buttons on panel from head
     * @param panel
     * @param posHead
     * @param posTail
     * @return
     */
    public int createButtonsOnPanelByCurrentHeadAndTail(JPanel panel, final int posHead/*, final int posTail*/) {
        if (this.xmlData.isEmpty()) {
            return 0;
        }
        int left = 0;
        panel.removeAll();
        //panel.setLayout(null);
        List<Genome> genes = this.getGenesAfter_posHeadandBy_geneNumber(posHead, 5);
        left = this.AddLine(panel, left, (genes.get(0).getHead() - posHead) / 5, panel.getHeight() / 2 - 5);
        for (int i = 0; i < genes.size(); i++) {
            Color buttonBGColor = colors[(this.xmlData.indexOf(genes.get(i)) + 1) % colors.length];
            left = this.paintAGeneButtonOnJPanel(panel, genes.get(i), left, buttonBGColor);
            if (i + 1 < genes.size()) {
                left = this.AddLine(panel, left, (genes.get(i + 1).getHead() - genes.get(i).getTail()) / 5, panel.getHeight() / 2 - 5);
            }
        }
        return left;
    }

    public int paintAGeneButtonOnJPanel(JPanel panel, Genome gene, int left, Color buttonBGColor) {
        StringBuilder buttonText = new StringBuilder("<html><font style=\"color:white;\">").append(
                gene.name).append("</font></html>");
        JButton button1 = new JButton(buttonText.toString());
        button1.addActionListener(new buttonActlistener(gene.getHead(), this));
        button1.setBackground(buttonBGColor);
        button1.setForeground(Color.black);
        button1.setBorderPainted(false);
        button1.setBounds(left, 0, (gene.getTail() - gene.getHead()) / 5, panel.getHeight());
        button1.setVisible(true);
        button1.setToolTipText("Product:    " + gene.getProduct());
        panel.add(button1);
        buttonText = null;
        return left + (gene.getTail() - gene.getHead()) / 5;
    }

    /**
     * get genes that is after or contain the posHead and genes' number equals
     * geneNumber
     *
     * @param posHead
     * @param geneNumber
     * @return
     */
    public List<Genome> getGenesAfter_posHeadandBy_geneNumber(final int posHead, final int geneNumber) {
        List<Genome> genes = new ArrayList<Genome>();
        for (int i = 0; i < this.xmlData.size(); i++) {
            if (this.xmlData.get(i).getHead() >= posHead || (posHead > this.xmlData.get(i).getHead() && posHead <= this.xmlData.get(i).getTail())) {
                for (int j = 0; j < geneNumber; j++) {
                    if (i + j < this.xmlData.size()) {
                        genes.add(this.xmlData.get(i + j));
                    }
                }
                break;
            }
        }
        return genes;
    }

    /**
     * Draw Black line
     *
     * @param panel
     * @param left
     * @param width
     * @param top
     * @return
     */
    public int AddLine(JPanel panel, int left, int width, int top) {
        final int height = 10;//the height of the line
        JButton button = new JButton();
        button.setBounds(left, top, width, height);
        button.setBackground(Color.black);
        panel.add(button);
        return left + width;
    }

    /**
     * read data from xml
     */
    public void getFromXml() {
        if (xmlFilePath == null) {
            return;
        }
        int colorSize = colors.length;
        int[] flag = new int[size];
        int counter = 0;
        for (int i = 1; i <= size; i++) {
            counter = 0;
            for (Genome genome : xmlData) {
                counter++;
                if (genome.getFeature().equals("CDS") && genome.isCds(position + i)) {
                    flag[i - 1] = counter;
                    break;
                }
            }
        }
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        DefaultTableCellRenderer[] render2 = {new DefaultTableCellRenderer(), new DefaultTableCellRenderer(), new DefaultTableCellRenderer(), new DefaultTableCellRenderer()};
        for (int i = 0; i < colorSize; i++) {
            render2[i].setHorizontalAlignment(SwingConstants.CENTER);
            render2[i].setBackground(colors[i]);
        }
        render.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < size; i++) {
            System.out.print(flag[i]);
        }
        System.out.println();
        for (int i = 1; i <= size; i++) {
            //System.out.print(i + ":" + flag[i - 1] + " ");
            table.getColumn(Integer.toString(id + i)).setCellRenderer(render);
            if (flag[i - 1] != 0) {
                table.getColumn(Integer.toString(id + i)).setCellRenderer(render2[flag[i - 1] % colorSize]);
            }
        }
    }

    /**
     *to show genome information on jlabels
     * @param index
     * @param genome
     */
    public void showGenomeInformaiton(Genome genome) {
        jLabel2.setText("" + genome.getHead());
        jLabel4.setText("" + genome.getTail());
        jLabel6.setText(Integer.toString(genome.getTail() - genome.getHead()));
        jLabel8.setText(genome.getProduct());
        jLabel10.setText(genome.getName());
        jLabel12.setText(genome.getGeneLink());
        //return true;
    }
    public List<String> AptName = new ArrayList<String>();
    public List<String> Aptseq = new ArrayList<String>();
    public List<String> Target = new ArrayList<String>();
    public List<String> targetType = new ArrayList<String>();
    public Riboswitch riboswitch = null;
    public int aptSelect = 0;

    private Object makeObj(final String item) {
        return new Object() {

            @Override
            public String toString() {
                return item;
            }
        };
    }

    public void refreshJList1() {
        List<String> temp = Browser.this.getRiboswitchTargetByInput();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < temp.size(); i++) {
            model.addElement(temp.get(i));
        }
        Browser.this.jList1.setModel(model);
    }
    DefaultListModel riboswitchRecordModel = new DefaultListModel();
    List<Integer> riboswitchRecordType = new ArrayList<Integer>();
    List<String> riboswitchRecordUserInput = new ArrayList<String>();
    List<Integer> riboswitchRecordTargetSelect = new ArrayList<Integer>();
    List<Boolean> riboswitchRecordIsUpRegulation = new ArrayList<Boolean>();
    List<Integer> riboswitchRecordAptSelect = new ArrayList<Integer>();

//    public void initRiboswitchRecord() {
//    }
    public void initRiboswitch() {
        //initRiboswitchRecord();
        riboswitchTarNameTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                Browser.this.refreshJList1();
            }

            public void removeUpdate(DocumentEvent e) {
                Browser.this.refreshJList1();
            }

            public void changedUpdate(DocumentEvent e) {
                Browser.this.refreshJList1();
            }
        });
        this.readAptamer();
    }

    /**
     *
     */
    public void readAptamer() {
        ReadXml read = new ReadXml();
        List<Element> l = new ArrayList<Element>();
        l = read.readXMLContent("aptamer.xml");
        for (int i = 0; i < l.size(); i++) {
            Element temp = l.get(i).getChild("target");
            if (temp != null) {
                Target.add(temp.getText());
                temp = l.get(i).getChild("targettype");
                targetType.add(temp.getText());
                temp = l.get(i).getChild("aptamers");
                temp = temp.getChild("aptamer");
                AptName.add(temp.getChild("name").getText());
                Aptseq.add(temp.getChild("seq").getText());
            }
        }
    }
    JLabel s1 = new JLabel("Tom Tuschl's Method:");
    JLabel s2 = new JLabel("Rational Design:");
    Box vertical = Box.createVerticalBox();

    /**
     *
     */
    public void initSiRNA() {

        siRNA s = new siRNA("p53.fasta");
        List list = s.siRNA();
        vertical.setBackground(new java.awt.Color(255, 215, 0));
        vertical.setSize(this.scrollPane1.getWidth(), this.scrollPane1.getHeight());

        s1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //
        //Modify the text color
        s1.setForeground(Color.black);
        s2.setForeground(Color.black);
        s1.setFont(new java.awt.Font("Arial Black", 0, 18));
        s2.setFont(new java.awt.Font("Arial Black", 0, 18));
        //Modify the background color
        //s1.setBackground(new Color(255, 192, 16));    
        //s2.setBackground(new Color(255, 192, 16));
        //

        t1 = new JTextArea();
        t2 = new JTextArea();
//        t1.setLineWrap(true);
//        t2.setLineWrap(true);
        t1.setText(list.get(0).toString());
        t2.setText(list.get(1).toString());

        vertical.add(s1);
        vertical.add(t1);
        vertical.add(s2);
        vertical.add(t2);

        scrollPane1.add(vertical);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sliderPane = new javax.swing.JScrollPane();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        productLabel = new javax.swing.JLabel();
        linkLabel = new javax.swing.JLabel();
        headLabel = new javax.swing.JLabel();
        tailLabel = new javax.swing.JLabel();
        lengthLabel = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jPanel20 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        riboswitchTarNameTextField = new javax.swing.JTextField();
        riboswitchTypeComboBox = new javax.swing.JComboBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        riboGoButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        riboswitchList = new javax.swing.JList();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jLabel46 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        zoomSlider = new javax.swing.JSlider();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        positionInputArea = new javax.swing.JTextField();
        submitBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        goBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        riboswitchRecordList = new javax.swing.JList();
        jPanel19 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        keggFindElementTextField = new javax.swing.JTextField();
        keggFindElementsButton = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openFastaAndXmlItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        FbaMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sysu Igem");
        setBackground(new java.awt.Color(0, 0, 0));
        setBounds(new java.awt.Rectangle(200, 3, 0, 0));
        setFont(new java.awt.Font("Meiryo", 1, 14)); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 0, 0));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jTabbedPane1.setForeground(new java.awt.Color(146, 148, 151));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Arial Black", 0, 14));
        jTabbedPane1.setName(""); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPane1ComponentShown(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel6ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel6ComponentShown(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jScrollPane1.setBackground(java.awt.Color.white);
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        sliderPane.setBackground(new java.awt.Color(255, 255, 255));
        sliderPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        sliderPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sliderPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 0, 18));
        jLabel3.setForeground(new java.awt.Color(64, 64, 65));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("HEAD");

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 0, 18));
        jLabel5.setForeground(new java.awt.Color(64, 64, 65));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("TAIL");

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setAutoscrolls(true);
        jScrollPane4.setOpaque(false);

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jPanel4.setFont(new java.awt.Font("微软雅黑", 1, 18));

        nameLabel.setBackground(new java.awt.Color(255, 255, 255));
        nameLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        nameLabel.setForeground(new java.awt.Color(105, 105, 105));
        nameLabel.setText("name");

        productLabel.setBackground(new java.awt.Color(255, 255, 255));
        productLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        productLabel.setForeground(new java.awt.Color(105, 105, 105));
        productLabel.setText("product");

        linkLabel.setBackground(new java.awt.Color(255, 255, 255));
        linkLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        linkLabel.setForeground(new java.awt.Color(105, 105, 105));
        linkLabel.setText("link");

        headLabel.setBackground(new java.awt.Color(255, 255, 255));
        headLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        headLabel.setForeground(new java.awt.Color(105, 105, 105));
        headLabel.setText("head");

        tailLabel.setBackground(new java.awt.Color(255, 255, 255));
        tailLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        tailLabel.setForeground(new java.awt.Color(105, 105, 105));
        tailLabel.setText("tail");

        lengthLabel.setBackground(new java.awt.Color(255, 255, 255));
        lengthLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        lengthLabel.setForeground(new java.awt.Color(105, 105, 105));
        lengthLabel.setText("length");

        jScrollPane11.setBorder(null);

        jPanel20.setBackground(java.awt.Color.white);
        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel2.setForeground(new java.awt.Color(105, 105, 105));
        jLabel2.setText("jLabel2"); // NOI18N
        jPanel20.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 129, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel4.setForeground(new java.awt.Color(105, 105, 105));
        jLabel4.setText("jLabel4");
        jPanel20.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 173, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel6.setForeground(new java.awt.Color(105, 105, 105));
        jLabel6.setText("jLabel2");
        jPanel20.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 226, 127, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel8.setForeground(new java.awt.Color(105, 105, 105));
        jLabel8.setText("ok");
        jPanel20.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 41, -1, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel10.setForeground(new java.awt.Color(105, 105, 105));
        jLabel10.setText("jLabel2");
        jPanel20.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 2, 105, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("jLabel2");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });
        jPanel20.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 85, -1, -1));

        jScrollPane11.setViewportView(jPanel20);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nameLabel)
                    .add(lengthLabel)
                    .add(headLabel)
                    .add(linkLabel)
                    .add(productLabel)
                    .add(tailLabel))
                .add(19, 19, 19)
                .add(jScrollPane11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(nameLabel)
                        .add(16, 16, 16)
                        .add(productLabel)
                        .add(18, 18, 18)
                        .add(linkLabel)
                        .add(18, 18, 18)
                        .add(headLabel)
                        .add(18, 18, 18)
                        .add(tailLabel)
                        .add(27, 27, 27)
                        .add(lengthLabel)))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 432, Short.MAX_VALUE)
                        .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(sliderPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jLabel5))
                .add(14, 14, 14)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(25, 25, 25)
                .add(sliderPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("GenomeBrowser", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel7.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel7ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel7ComponentShown(evt);
            }
        });

        jPanel9.setBackground(java.awt.Color.white);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 17, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 68, Short.MAX_VALUE)
        );

        jPanel11.setBackground(java.awt.Color.white);
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jLabel7.setBackground(java.awt.Color.white);
        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel7.setText("part_id");
        jLabel7.setOpaque(true);

        jLabel9.setBackground(java.awt.Color.white);
        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel9.setText("part_name");
        jLabel9.setOpaque(true);

        jLabel11.setBackground(java.awt.Color.white);
        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel11.setText("part_short_name");
        jLabel11.setOpaque(true);

        jLabel14.setBackground(java.awt.Color.white);
        jLabel14.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel14.setText("part_short_desc");
        jLabel14.setOpaque(true);

        jLabel15.setBackground(java.awt.Color.white);
        jLabel15.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel15.setText("part_type");
        jLabel15.setOpaque(true);

        jLabel16.setBackground(java.awt.Color.white);
        jLabel16.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel16.setText("part_status");
        jLabel16.setOpaque(true);

        jLabel17.setBackground(java.awt.Color.white);
        jLabel17.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel17.setText("part_results");
        jLabel17.setOpaque(true);

        jLabel18.setBackground(java.awt.Color.white);
        jLabel18.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel18.setText("part_nickname");
        jLabel18.setOpaque(true);

        jLabel19.setBackground(java.awt.Color.white);
        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel19.setText("part_rating");
        jLabel19.setOpaque(true);

        jLabel20.setBackground(java.awt.Color.white);
        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel20.setText("part_entered");
        jLabel20.setOpaque(true);

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel21.setText("part_author");
        jLabel21.setOpaque(true);

        jLabel22.setBackground(java.awt.Color.white);
        jLabel22.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel22.setText("best_quality");
        jLabel22.setOpaque(true);

        jLabel23.setBackground(java.awt.Color.white);
        jLabel23.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel23.setText("twins");
        jLabel23.setOpaque(true);

        jLabel24.setBackground(java.awt.Color.white);
        jLabel24.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel24.setText("jLabel24");
        jLabel24.setOpaque(true);

        jLabel25.setBackground(java.awt.Color.white);
        jLabel25.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel25.setText("jLabel24");
        jLabel25.setOpaque(true);

        jLabel26.setBackground(java.awt.Color.white);
        jLabel26.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel26.setText("jLabel24");
        jLabel26.setOpaque(true);

        jLabel27.setBackground(java.awt.Color.white);
        jLabel27.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel27.setText("jLabel24");
        jLabel27.setOpaque(true);

        jLabel28.setBackground(java.awt.Color.white);
        jLabel28.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel28.setText("jLabel24");
        jLabel28.setOpaque(true);

        jLabel30.setBackground(java.awt.Color.white);
        jLabel30.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel30.setText("jLabel24");
        jLabel30.setOpaque(true);

        jLabel31.setBackground(java.awt.Color.white);
        jLabel31.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel31.setText("jLabel24");
        jLabel31.setOpaque(true);

        jLabel32.setBackground(java.awt.Color.white);
        jLabel32.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel32.setText("jLabel24");
        jLabel32.setOpaque(false);

        jLabel33.setBackground(java.awt.Color.white);
        jLabel33.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel33.setText("jLabel24");
        jLabel33.setOpaque(true);

        jLabel35.setBackground(java.awt.Color.white);
        jLabel35.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel35.setText("jLabel24");
        jLabel35.setOpaque(true);

        jLabel36.setBackground(java.awt.Color.white);
        jLabel36.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel36.setText("jLabel24");
        jLabel36.setOpaque(true);

        jLabel29.setBackground(java.awt.Color.white);
        jLabel29.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel29.setText("jLabel29");
        jLabel29.setOpaque(true);

        jScrollPane6.setBackground(java.awt.Color.white);
        jScrollPane6.setBorder(null);

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(jLabel21.getFont());
        jLabel34.setForeground(jLabel21.getForeground());
        jLabel34.setText("jLabel24");
        jLabel34.setDoubleBuffered(true);
        jLabel34.setOpaque(true);
        jScrollPane6.setViewportView(jLabel34);

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel11)
                    .add(jLabel14)
                    .add(jLabel15)
                    .add(jLabel16)
                    .add(jLabel17)
                    .add(jLabel18)
                    .add(jLabel19)
                    .add(jLabel22)
                    .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel21, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel20))
                    .add(jLabel23)
                    .add(jLabel7)
                    .add(jLabel9))
                .add(32, 32, 32)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel25)
                    .add(jLabel35)
                    .add(jLabel33)
                    .add(jLabel32)
                    .add(jLabel31)
                    .add(jLabel30)
                    .add(jLabel28)
                    .add(jLabel27)
                    .add(jLabel26)
                    .add(jLabel24)
                    .add(jLabel36)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .add(jLabel29))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel24)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel9)
                    .add(jLabel25))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(jLabel26))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel14)
                    .add(jLabel27))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel15)
                    .add(jLabel28))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel16)
                    .add(jLabel29))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel17)
                    .add(jLabel30))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel18)
                    .add(jLabel31))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel19)
                    .add(jLabel32))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel20)
                    .add(jLabel33))
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jLabel21))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel22)
                    .add(jLabel35))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel36)
                    .add(jLabel23))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        jScrollPane5.getViewport().setBackground(new java.awt.Color(255, 255, 0));

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(13, 13, 13)
                        .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Biobrick", jPanel7);

        jPanel5.setBackground(java.awt.Color.white);
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel5.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel5ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel5ComponentShown(evt);
            }
        });

        riboswitchTarNameTextField.setFont(new java.awt.Font("宋体", 0, 18));
        riboswitchTarNameTextField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        riboswitchTarNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riboswitchTarNameTextFieldActionPerformed(evt);
            }
        });

        riboswitchTypeComboBox.setUI(new IComboBoxUI());
        riboswitchTypeComboBox.setRenderer(new IComboBoxRenderer());
        riboswitchTypeComboBox.setFont(new java.awt.Font("Arial Black", 0, 14));
        riboswitchTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Protein", "Virus", "Enzyme", "Drug", "Small molecule", "Lipid", "Cell", "Dye", "Nucleic acid", "Animo acid", "Antibody", "Chemical compound", "Peptide", "Hormone", "Carbohydrate" }));
        riboswitchTypeComboBox.setOpaque(false);
        riboswitchTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riboswitchTypeComboBoxActionPerformed(evt);
            }
        });

        jRadioButton1.setFont(new java.awt.Font("Arial Black", 0, 14));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Up Regulation");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Arial Black", 0, 14));
        jRadioButton2.setText("Down Regulation");
        jRadioButton2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton2StateChanged(evt);
            }
        });
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        riboGoButton1.setFont(new java.awt.Font("Arial Black", 0, 14));
        riboGoButton1.setText("Go!");
        riboGoButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riboGoButton1MouseClicked(evt);
            }
        });
        riboGoButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riboGoButton1ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        jTextArea1.setColumns(10);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 165, 0), 2, true));
        jScrollPane2.setViewportView(jTextArea1);

        jLabel40.setFont(new java.awt.Font("Arial Black", 0, 14));
        jLabel40.setText("Sequence:");

        jScrollPane7.setBorder(null);

        riboswitchList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        riboswitchList.setFont(new java.awt.Font("宋体", 0, 18));
        riboswitchList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        riboswitchList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                riboswitchListValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(riboswitchList);

        jScrollPane8.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane8.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        //jScrollPane8.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane8.setBorder(null);

        jList1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        jList1.setFont(new java.awt.Font("宋体", 0, 18));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(jList1);
        //jScrollPane8.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        //jScrollPane8.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        jScrollPane8.updateUI();

        jLabel42.setFont(new java.awt.Font("Arial Black", 0, 14));
        jLabel42.setText("Target Name");

        jLabel43.setFont(new java.awt.Font("Arial Black", 0, 14));
        jLabel43.setText("Aptamer");

        jPanel16.setBackground(new java.awt.Color(255, 215, 0));

        jLabel39.setBackground(new java.awt.Color(255, 165, 0));
        jLabel39.setFont(new java.awt.Font("Arial Black", 0, 14));
        jLabel39.setText("Target Type:");

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel39, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel39, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(255, 215, 0));

        jLabel38.setBackground(new java.awt.Color(255, 215, 0));
        jLabel38.setFont(new java.awt.Font("Arial Black", 0, 14));
        jLabel38.setText("Input your Target:");

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jLabel38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(118, 118, 118)
                .add(jLabel42)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 335, Short.MAX_VALUE)
                .add(jLabel43)
                .add(74, 74, 74))
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jRadioButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jRadioButton2))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 320, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                        .add(51, 51, 51)
                        .add(riboGoButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(77, 77, 77))))
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel40)
                .addContainerGap(597, Short.MAX_VALUE))
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                .add(60, 60, 60)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(riboswitchTarNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .add(riboswitchTypeComboBox, 0, 452, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(riboswitchTypeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(riboswitchTarNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel42)
                    .add(jLabel43))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane7)
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(riboGoButton1)
                    .add(jRadioButton2)
                    .add(jRadioButton1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel40)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        //jScrollPane8.updateUI();

        jTabbedPane1.addTab("RiboSwitch", jPanel5);

        jPanel12.setBackground(java.awt.Color.white);
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel12.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel12ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel12ComponentShown(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollPane1.setBackground(new java.awt.Color(255, 255, 0));
        jPanel12.add(scrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 390, 610));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/igem/browser/rnai.png"))); // NOI18N
        jPanel12.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 390, -1));

        jLabel45.setBackground(new java.awt.Color(255, 255, 255));
        jLabel45.setOpaque(true);
        jPanel12.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 300, 110));

        jLabel47.setBackground(new java.awt.Color(255, 255, 255));
        jLabel47.setOpaque(true);
        jPanel12.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, 300, 140));

        jTabbedPane1.addTab("SiRNA", jPanel12);

        jPanel13.setBackground(java.awt.Color.white);
        jPanel13.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel13ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel13ComponentShown(evt);
            }
        });
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel37.setBackground(java.awt.Color.white);
        jLabel37.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jLabel37.setDoubleBuffered(true);
        jLabel37.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jLabel37ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jLabel37ComponentShown(evt);
            }
        });
        jPanel13.add(jLabel37, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("MetaNetwork", jPanel13);

        jPanel14.setBackground(new java.awt.Color(204, 204, 204));
        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel14.setForeground(new java.awt.Color(146, 148, 151));
        jPanel14.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 192, 16));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 16));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/igem/browser/zoom.png"))); // NOI18N
        jLabel1.setText("Zoom");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 13, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        zoomSlider.setBackground(new java.awt.Color(255, 192, 16));
        zoomSlider.setForeground(new java.awt.Color(245, 0, 0));
        zoomSlider.setMaximum(65);
        zoomSlider.setMinimum(20);
        zoomSlider.setMinorTickSpacing(5);
        zoomSlider.setValue(20);
        zoomSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                zoomSliderMouseReleased(evt);
            }
        });
        zoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zoomSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 104;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 13, 20);
        jPanel3.add(zoomSlider, gridBagConstraints);

        jPanel14.add(jPanel3);
        jPanel3.setBounds(10, 120, 270, 55);

        jPanel1.setBackground(new java.awt.Color(255, 192, 16));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jComboBox1.setUI(new IComboBoxUI());
        jComboBox1.setRenderer(new IComboBoxRenderer());
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Position", "Product", "Name" }));
        jComboBox1.setToolTipText("Position");

        positionInputArea.setFont(new java.awt.Font("宋体", 1, 15));

        submitBtn.setBackground(new java.awt.Color(255, 255, 255));
        submitBtn.setFont(new java.awt.Font("Gisha", 1, 15));
        submitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/igem/browser/submit.png"))); // NOI18N
        submitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitBtnMouseClicked(evt);
            }
        });
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(positionInputArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(submitBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(positionInputArea, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .add(submitBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(22, 22, 22))
        );

        jPanel14.add(jPanel1);
        jPanel1.setBounds(10, 40, 270, 72);

        jPanel8.setBackground(new java.awt.Color(241, 241, 242));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel8.setForeground(new java.awt.Color(241, 241, 242));

        jButton1.setBackground(java.awt.Color.white);
        jButton1.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButton1.setText("Open Fasta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setBackground(java.awt.Color.white);

        inputArea.setColumns(20);
        inputArea.setLineWrap(true);
        inputArea.setRows(5);
        inputArea.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jScrollPane3.setViewportView(inputArea);

        goBtn.setBackground(java.awt.Color.white);
        goBtn.setFont(new java.awt.Font("Arial Black", 0, 14));
        goBtn.setText("Go");
        goBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        goBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goBtnMouseClicked(evt);
            }
        });
        goBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBtnActionPerformed(evt);
            }
        });

        jButton2.setBackground(java.awt.Color.white);
        jButton2.setFont(new java.awt.Font("Arial Black", 0, 14));
        jButton2.setText("Clear");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel41.setBackground(java.awt.Color.white);
        jLabel41.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel41.setForeground(new java.awt.Color(246, 146, 30));
        jLabel41.setText("Direct Input");

        org.jdesktop.layout.GroupLayout jPanel17Layout = new org.jdesktop.layout.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel41, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel41, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(jPanel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 25, Short.MAX_VALUE)
                        .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel8Layout.createSequentialGroup()
                        .add(jButton1)
                        .add(18, 18, 18)
                        .add(goBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(24, 24, 24)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 361, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(goBtn)
                    .add(jButton1))
                .addContainerGap())
        );

        jPanel14.add(jPanel8);
        jPanel8.setBounds(10, 460, 270, 490);

        jPanel18.setBackground(java.awt.Color.white);

        jLabel44.setFont(new java.awt.Font("Arial Black", 0, 15));
        jLabel44.setText("Riboswitch Record");

        riboswitchRecordList.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(225, 215, 0), 2, true));
        riboswitchRecordList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        riboswitchRecordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                riboswitchRecordListMouseClicked(evt);
            }
        });
        riboswitchRecordList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                riboswitchRecordListValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(riboswitchRecordList);

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .add(jLabel44))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel44)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.add(jPanel18);
        jPanel18.setBounds(10, 290, 270, 483);

        jPanel19.setBackground(java.awt.Color.white);
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jPanel19.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel19ComponentShown(evt);
            }
        });
        jPanel19.setLayout(null);

        jPanel21.setBackground(new java.awt.Color(255, 196, 12));
        jPanel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));

        jLabel13.setFont(new java.awt.Font("Arial Black", 0, 15));
        jLabel13.setText("Find Elements");

        keggFindElementsButton.setFont(new java.awt.Font("Arial Black", 0, 15));
        keggFindElementsButton.setText("Go!");
        keggFindElementsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keggFindElementsButtonMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel21Layout.createSequentialGroup()
                        .add(keggFindElementTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(keggFindElementsButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel13))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel13)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(keggFindElementTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(keggFindElementsButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel21);
        jPanel21.setBounds(16, 15, 250, 90);

        jPanel22.setBackground(java.awt.Color.white);
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 250, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 104, Short.MAX_VALUE)
        );

        jPanel19.add(jPanel22);
        jPanel22.setBounds(16, 112, 254, 108);

        jPanel14.add(jPanel19);
        jPanel19.setBounds(10, 180, 270, 220);

        jTree1.setBackground(jPanel4.getBackground());
        jTree1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jTree1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18));
        jScrollPane10.setViewportView(jTree1);

        jPanel14.add(jScrollPane10);
        jScrollPane10.setBounds(0, 0, 290, 322);

        menuBar.setBackground(java.awt.Color.black);
        menuBar.setForeground(new java.awt.Color(255, 255, 255));
        menuBar.setFont(new java.awt.Font("Arial Black", 0, 14));

        fileMenu.setForeground(new java.awt.Color(230, 231, 232));
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setFont(new java.awt.Font("Arial Black", 0, 14));

        openFastaAndXmlItem.setMnemonic('o');
        openFastaAndXmlItem.setText("Open Fasta and Xml");
        fileMenu.add(openFastaAndXmlItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        FbaMenu.setForeground(new java.awt.Color(230, 231, 232));
        FbaMenu.setMnemonic('t');
        FbaMenu.setText("Tools");
        FbaMenu.setFont(new java.awt.Font("Arial Black", 0, 14));

        jMenuItem1.setText("Simulator");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        FbaMenu.add(jMenuItem1);

        jMenuItem3.setText("G-Circle");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        FbaMenu.add(jMenuItem3);

        menuBar.add(FbaMenu);

        helpMenu.setForeground(new java.awt.Color(230, 231, 232));
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Arial Black", 0, 14));

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 292, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTabbedPane1)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomSliderStateChanged
        System.out.println("Zoom Slider:" + zoomSlider.getValue());
        size = zoomSlider.getValue();
        System.out.println(table);
        if (file != null) {
            file.setReadSize(size);
            showATGC();
        }
}//GEN-LAST:event_zoomSliderStateChanged

    private void zoomSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoomSliderMouseReleased
        if (file == null) {
            zoomSlider.setValue(11);
        }
}//GEN-LAST:event_zoomSliderMouseReleased
    /**
     *
     * @param product
     * @return
     */
    private List<Genome> findGenesByProduct(String product) {
        List<Genome> genes = new ArrayList<Genome>();
        for (Genome genome : xmlData) {
            if (genome.getProduct().toLowerCase().indexOf(product.toLowerCase()) >= 0 && genome.getName() != null) {
                genes.add(genome);
            }
        }
        return genes;
    }

    private List<Genome> findGenesByName(String name) {
        List<Genome> genes = new ArrayList<Genome>();
        for (Genome genome : xmlData) {
            if (genome.getName().toLowerCase().indexOf(name.toLowerCase()) >= 0) {
                genes.add(genome);
            }
        }
        return genes;
    }

    /**
     * To show a form contain a jlist to show the result of searching gene
     *
     * @return true if there is result,false otherwise
     */
    public boolean showSearchGenesResultForm(String userInput, int choice) {
        final List<Genome> genes;

        if (choice == 1) {
            genes = this.findGenesByProduct(userInput);
        } else {
            genes = this.findGenesByName(userInput);
        }

        if (genes == null || genes.isEmpty()) {
            return false;
        } else {
            final JFrame frame = new JFrame("Show genome search result");
            frame.setSize(400, 400);
            frame.setLocation(200, 200);
            frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            String[] geneNameList = new String[genes.size()];
            for (int i = 0; i < genes.size(); i++) {
                if (userInput == "Product") {
                    geneNameList[i] = genes.get(i).getProduct();
                } else {
                    geneNameList[i] = genes.get(i).getName();
                }
            }
            final JList geneJList = new JList(geneNameList);
            geneJList.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    Browser.this.showGenomeInformaiton(genes.get(geneJList.getSelectedIndex()));
                    Browser.this.position = genes.get(geneJList.getSelectedIndex()).head;
                    Browser.this.jslider.setValue(genes.get(geneJList.getSelectedIndex()).getHead());
                    frame.dispose();
                }
            });
            JScrollPane scrollpane = new JScrollPane(geneJList);
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
            return true;
        }
    }
    private void submitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitBtnMouseClicked
        int choice;
        if (file == null) {
            JOptionPane.showMessageDialog(jPanel1, "Please Open a fasta file first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (jComboBox1.getSelectedIndex()) {
            case 0:
                String number = positionInputArea.getText();
                if (!number.isEmpty() && isNumber(number)) {
                    System.out.println("Submit Number:" + number);
                    if (StringtoInt(number) - 1 > file.getFileSize() - 1) {
                        JOptionPane.showMessageDialog(jPanel1, "Input Number should not larger than " + file.getFileSize(), "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        position = StringtoInt(number) - 1;
                        showATGC();
                    }
                } else {
                    JOptionPane.showMessageDialog(jPanel1, "Input Should be numbers and not empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 1:
                String userInput = positionInputArea.getText();
                choice = 1;
                if (xmlFilePath == null) {
                    JOptionPane.showMessageDialog(jPanel1, "Please Open a xml file first!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!this.showSearchGenesResultForm(userInput, choice)) {
                    JOptionPane.showMessageDialog(jPanel1, "Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 2:
                String name = positionInputArea.getText();
                choice = 2;
                if (xmlFilePath == null) {
                    JOptionPane.showMessageDialog(jPanel1, "Please Open a xml file first!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!this.showSearchGenesResultForm(name, choice)) {
                    JOptionPane.showMessageDialog(jPanel1, "Not Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
        }
}//GEN-LAST:event_submitBtnMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
        int index = sourceTabbedPane.getSelectedIndex();
        System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
    }//GEN-LAST:event_jTabbedPane1StateChanged
    private void jPanel13ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel13ComponentShown
        this.jPanel19.setVisible(true);
        this.jPanel19.setLocation(5, 5);
        this.jPanel19.setSize(this.jPanel14.getWidth() - 10, this.jPanel14.getHeight() - 10);
        this.jPanel14.validate();
        if (kgmlReader != null) {
            return;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFileChooser chooser = new JFileChooser();
        try {
            chooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
        } catch (IOException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "KEGG XML Files", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String xmlPath = chooser.getSelectedFile().getAbsolutePath();
            System.out.println(xmlPath);
            if (xmlPath == null) {
                return;
            }
            if (xmlPath != null || !xmlPath.equals("null") || !xmlPath.equals("")) {
                kgmlReader = new org.KeggNetwork.KGMLReader(xmlPath, this.jPanel13);
                this.jLabel37.setVisible(false);
                this.jPanel13.validate();
            }
        }
        if (chooser.getSelectedFile() == null) {
            return;
        }
        try {
            HistoryFile.setPathToHistoryFile("history.txt", chooser.getSelectedFile().getPath());
        } catch (IOException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel13ComponentShown

    private void jTabbedPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPane1ComponentShown
        // TODO add your handling code here:      
    }//GEN-LAST:event_jTabbedPane1ComponentShown
    private void jPanel7ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel7ComponentShown
        this.jScrollPane10.setVisible(true);
        this.jScrollPane10.setSize(this.jPanel14.getWidth() - 25, this.jPanel14.getHeight() - 25);
        this.jScrollPane10.setLocation(10, 15);
        //this.jTree1.setVisible(true);
        this.jTree1.setLocation(0, 0);
        this.jTree1.setSize(this.jPanel14.getWidth() - 25, this.jPanel14.getHeight() - 25);
        this.jTree1.setVisible(true);
        this.validate();
    }//GEN-LAST:event_jPanel7ComponentShown
    private void jPanel6ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel6ComponentShown
        this.jPanel1.setVisible(true);
        this.jPanel3.setVisible(true);
    }//GEN-LAST:event_jPanel6ComponentShown
    private void jPanel5ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel5ComponentShown
        this.jPanel18.setVisible(true);
        this.jPanel18.setLocation(10, 10);
        this.jPanel18.setSize(this.jPanel14.getWidth() - 25, this.jPanel14.getHeight() - 25);
        this.validate();
    }//GEN-LAST:event_jPanel5ComponentShown
    private void jPanel12ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel12ComponentShown
        this.scrollPane1.setSize(this.jPanel12.getWidth(), this.jPanel12.getHeight());
        this.jPanel8.setVisible(true);
        this.jPanel8.setLocation(10, 10);
        this.jPanel8.setSize(this.jPanel14.getWidth() - 25, this.jPanel14.getHeight() - 25);
        this.jPanel14.validate();
    }//GEN-LAST:event_jPanel12ComponentShown
    private void jLabel37ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel37ComponentShown
        this.jPanel1.setVisible(false);
        this.jPanel3.setVisible(false);
        this.validate();
    }//GEN-LAST:event_jLabel37ComponentShown

    private void jPanel6ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel6ComponentHidden
        // TODO add your handling code here:
        this.jPanel1.setVisible(false);
        this.jPanel3.setVisible(false);
    }//GEN-LAST:event_jPanel6ComponentHidden

    private void jPanel7ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel7ComponentHidden
        this.jTree1.setVisible(false);
        this.jScrollPane10.setVisible(false);
    }//GEN-LAST:event_jPanel7ComponentHidden

    private void jRadioButton2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton2StateChanged
    }//GEN-LAST:event_jRadioButton2StateChanged

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        this.jRadioButton1.setSelected(!this.jRadioButton1.isSelected());
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        this.jRadioButton2.setSelected(!this.jRadioButton2.isSelected());
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jPanel12ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel12ComponentHidden
        this.jPanel8.setVisible(false);
        this.validate();
    }//GEN-LAST:event_jPanel12ComponentHidden

    private void goBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goBtnMouseClicked
        String input = inputArea.getText().toUpperCase();
        // Boolean flag = true;

        siRNA s = new siRNA(input);

        if (s.isValid()) {
            //Box vertical = Box.createVerticalBox();
            List list = s.siRNA();
            //JPanel panel = new JPanel();
//            JLabel s1 = new JLabel("Tom Tuschl's Method:");
//
//            JLabel s2 = new JLabel("Rational Design:");
            //s1.setForeground(Color.black);
            //s2.setForeground(Color.black);
//            JTextField t1 = new JTextField();
//            JTextField t2 = new JTextField();
            t1.setText(list.get(0).toString());
            t2.setText(list.get(1).toString());
//            s1.setForeground(Color.black);
//            s2.setForeground(Color.black);
//            s1.setFont(new java.awt.Font("Arial Black", 0, 18));
//            s2.setFont(new java.awt.Font("Arial Black", 0, 18));
            //System.out.println(list.toString());

            //vertical.add(s1);
            //vertical.add(t1);
            //vertical.add(s2);
            //vertical.add(t2);

            //scrollPane1.add(vertical);
        }
    }//GEN-LAST:event_goBtnMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        OpenFastaFile f = new OpenFastaFile("", "");
        //f.showSaveDataDialog();
        FilePath = f.showOpenDataDialog();
        Box vertical = Box.createVerticalBox();
        vertical.setSize(this.scrollPane1.getWidth(), this.scrollPane1.getHeight());
        siRNA s = new siRNA(new File(FilePath));

        inputArea.setText(s.getGene());
        if (s.isValid()) {
            List list = s.siRNA();
            //JPanel panel = new JPanel();
//            JLabel s1 = new JLabel("Tom Tuschl's Method:");
//            JLabel s2 = new JLabel("Rational Design:");
//            JTextField t1 = new JTextField();
//            JTextField t2 = new JTextField();

            t1.setText(list.get(0).toString());
            t2.setText(list.get(1).toString());
            s1.setForeground(Color.black);
            s2.setForeground(Color.black);
//            vertical.add(s1);
//            vertical.add(t1);
//            vertical.add(s2);
//            vertical.add(t2);
//
//            scrollPane1.add(vertical);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        inputArea.setText("");
        t1.setText("");
        t2.setText("");
    }//GEN-LAST:event_jButton2MouseClicked
    /**
     * Add all the riboswitch things by record list
     */
    public void autoAddRecordToList() {
        this.riboswitchRecordAptSelect.add(this.riboswitchList.getSelectedIndex());
        this.riboswitchRecordIsUpRegulation.add(this.jRadioButton1.isSelected());
        this.riboswitchRecordUserInput.add(this.riboswitchTarNameTextField.getText());
        this.riboswitchRecordTargetSelect.add(this.jList1.getSelectedIndex());
        this.riboswitchRecordType.add(this.riboswitchTypeComboBox.getSelectedIndex());
        this.riboswitchRecordModel.addElement(this.riboswitchList.getSelectedValue());
        this.riboswitchRecordList.setModel(riboswitchRecordModel);
        this.riboswitchRecordList.validate();
    }

    private void riboGoButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riboGoButton1MouseClicked
        autoAddRecordToList();
        for (int i = 0; i < AptName.size(); i++) {
            if (AptName.get(i).equals((String) this.riboswitchList.getSelectedValue())) {
                this.aptSelect = i;
                break;
            }
        }
        this.riboswitch = new Riboswitch(this.Aptseq.get(aptSelect));
        this.jTextArea1.setText("");
        if (this.jRadioButton1.isSelected()) {
            jPanelUp.ShowUpJPG(riboswitch);
            this.jTextArea1.setText(riboswitch.getON().toUpperCase());
        }
        if (this.jRadioButton2.isSelected()) {
            jPanelUp.ShowDownJPG(riboswitch);
            this.jTextArea1.setText(riboswitch.getOFF().toUpperCase());
        }
    }//GEN-LAST:event_riboGoButton1MouseClicked
    /**
     * getAptnameByTarget
     *
     * @param target
     * @return
     */
    public List<String> getAptnameByTarget(String target) {
        List<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < Target.size(); i++) {
            if (Target.get(i).equals(target)) {
                toReturn.add(this.AptName.get(i));
            }
        }
        return toReturn;
    }

    public List<String> getRiboswitchTargetByInput() {
        List<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < Target.size(); i++) {
            //when targettype=="" means it is all type
            //or else targetType equals riboswitchTypeComboBox.getSelectedItem()
            if (targetType.get(i).length() == 0 || targetType.get(i).indexOf((String) this.riboswitchTypeComboBox.getSelectedItem()) >= 0) {
                String userInput = this.riboswitchTarNameTextField.getText();
                //target userInput include userinput
                //and both to lowercase compare
                if (Target.get(i).toLowerCase().indexOf(userInput.toLowerCase()) >= 0) {
                    if (!toReturn.contains(this.Target.get(i))) {
                        toReturn.add(this.Target.get(i));
                    }
                }
            }
        }
        return toReturn;
    }
    private void riboswitchTarNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riboswitchTarNameTextFieldActionPerformed
    }//GEN-LAST:event_riboswitchTarNameTextFieldActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        List<String> temp = Browser.this.getAptnameByTarget((String) this.jList1.getSelectedValue());
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < temp.size(); i++) {
            model.addElement(temp.get(i));
        }
        this.riboswitchList.setModel(model);
    }//GEN-LAST:event_jList1ValueChanged

    private void riboswitchListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_riboswitchListValueChanged
    }//GEN-LAST:event_riboswitchListValueChanged

    private void riboswitchTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riboswitchTypeComboBoxActionPerformed
        // String temp=this.riboswitchTarNameTextField.getText();
        this.refreshJList1();
    }//GEN-LAST:event_riboswitchTypeComboBoxActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /c start runfba.bat");
        } catch (IOException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jPanel5ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel5ComponentHidden
        this.jPanel18.setVisible(false);
        this.validate();
    }//GEN-LAST:event_jPanel5ComponentHidden

    private void goBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBtnActionPerformed
    }//GEN-LAST:event_goBtnActionPerformed
    /**
     *
     * @param select the record that user selected
     */
    public void autoReturnRiboswitchRecord(int select) {
        this.riboswitchTypeComboBox.setSelectedIndex(this.riboswitchRecordType.get(select));
        this.riboswitchTarNameTextField.setText(this.riboswitchRecordUserInput.get(select));
        this.jList1.setSelectedIndex(this.riboswitchRecordTargetSelect.get(select));
        this.riboswitchList.setSelectedIndex(this.riboswitchRecordAptSelect.get(select));
        this.jRadioButton1.setSelected(this.riboswitchRecordIsUpRegulation.get(select));
        this.jRadioButton2.setSelected(!this.riboswitchRecordIsUpRegulation.get(select));
        this.jTextArea1.setText("");
    }
    private void riboswitchRecordListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_riboswitchRecordListValueChanged
    }//GEN-LAST:event_riboswitchRecordListValueChanged

    private void riboswitchRecordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_riboswitchRecordListMouseClicked
        this.autoReturnRiboswitchRecord(this.riboswitchRecordList.getSelectedIndex());
    }//GEN-LAST:event_riboswitchRecordListMouseClicked

    private void jLabel37ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel37ComponentHidden
    }//GEN-LAST:event_jLabel37ComponentHidden

    private void jPanel13ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel13ComponentHidden
        this.jPanel19.setVisible(false);
    }//GEN-LAST:event_jPanel13ComponentHidden

    private void jPanel19ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel19ComponentShown
        // Browser.this.jList2.setModel(keggListModel);
    }//GEN-LAST:event_jPanel19ComponentShown

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_submitBtnActionPerformed

    private void keggFindElementsButtonMouseClicked(java.awt.event.MouseEvent evt) {
        try {
            //        String toFind = this.keggFindElementTextField.getText();
            //        List<UmlNode> nodeList = this.kgmlReader.FindNodeByName(toFind);
            //        if (nodeList.isEmpty()) {
            //            JOptionPane.showMessageDialog(this, "Can't find element include "+toFind, "Can't find element", JOptionPane.ERROR_MESSAGE);
            //        } else {
            //            this.kgmlReader.AllPreNodeReset();
            //            for (UmlNode node : nodeList) {
            //                this.kgmlReader.preFindedNodes.add(node);
            //                node.putCustomDrawFillColor(Color.PINK);
            //            }
            //        }
            new FindKeggXmlIncludeString(this.kgmlReader, this.jPanel13).createKeggXmlSelectForm(this.keggFindElementTextField.getText());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void riboGoButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riboGoButton1ActionPerformed
    }//GEN-LAST:event_riboGoButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new MyJFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec("cmd.exe /c start iexplore " + this.jLabel12.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "cannot open url");
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        // TODO add your handling code here:
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        // TODO add your handling code here:
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_jLabel12MouseExited

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        new AboutJFrame().setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    public boolean isNumber(String str) {
        if (java.lang.Character.isDigit(str.charAt(0))) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                Browser browser;
                try {
                    browser = new Browser(); //.setVisible(true);
                    browser.getContentPane().setBackground(Color.black);
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Set the main frame not visible
                //browser.setVisible(false);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu FbaMenu;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton goBtn;
    private javax.swing.JLabel headLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTextField keggFindElementTextField;
    private javax.swing.JButton keggFindElementsButton;
    private javax.swing.JLabel lengthLabel;
    private javax.swing.JLabel linkLabel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JMenuItem openFastaAndXmlItem;
    private javax.swing.JTextField positionInputArea;
    private javax.swing.JLabel productLabel;
    private javax.swing.JButton riboGoButton1;
    private javax.swing.JList riboswitchList;
    private javax.swing.JList riboswitchRecordList;
    private javax.swing.JTextField riboswitchTarNameTextField;
    private javax.swing.JComboBox riboswitchTypeComboBox;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JScrollPane sliderPane;
    private javax.swing.JButton submitBtn;
    private javax.swing.JLabel tailLabel;
    private javax.swing.JSlider zoomSlider;
    // End of variables declaration//GEN-END:variables
    JTable table;
    JTable cdsTable;
    JTextArea t1;// = new JTextField();
    JTextArea t2;// = new JTextField();
}
