/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * genomeBrowserJFrame.java
 *
 * Created on 2012-10-21, 7:57:34
 */
package org.igem.browser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.sysu.igem.newUI.IComboBoxRenderer;
import org.sysu.igem.newUI.IComboBoxUI;
import org.sysu.igem.newUI.MySliderUI;
import org.sysu.igem.utils.GetGenomeFromXml;
import org.sysu.igem.utils.HistoryFile;
import org.sysu.igem.utils.ReadFileRandom;
import org.sysu.igem.utils.StringUtils;

/**
 *
 * @author Administrator
 */
public class genomeBrowserJFrame extends javax.swing.JFrame {

    JTable table;
    JTable cdsTable;
    private String FilePath;
    private ReadFileRandom file;
    private int size;
    private Object[] obj;
    private String xmlFilePath;
    private GetGenomeFromXml xmlFile;
    private List<Genome> xmlData;
    private int position;
    public final JSlider jslider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    public boolean IsGeneButtonClick = false;
    public JPanel buttonsPanel = null;
    /**
     * Colors of the button of genome
     */
    final Color[] colors = {new java.awt.Color(255, 127, 36), new java.awt.Color(255, 165, 0),
        new java.awt.Color(255, 127, 36), new java.awt.Color(255, 165, 0)};

    /** Creates new form genomeBrowserJFrame */
    public genomeBrowserJFrame() {
        position = 0;
        size = 20;
        initComponents();
        initSlider();
        this.initMyComponents();
        xmlData = new ArrayList();
    }
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
                    if (buttonsPanel == null) {
                        buttonsPanel = new JPanel();
                        buttonsPanel.setLayout(null);
                        buttonsPanel.setSize(jScrollPane4.getWidth(), jScrollPane4.getHeight());
                    }
                    int left = createButtonsOnPanelByCurrentHeadAndTail(buttonsPanel, position);//position + size);
                    buttonsPanel.setPreferredSize(new Dimension(left, jScrollPane4.getHeight()));
                    jScrollPane4.getViewport().setView(buttonsPanel);
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
        sliderPane.validate();
    }
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
                        if (f.getName().toLowerCase().endsWith(".fasta") || f.isDirectory()) {
                            return true;
                        } else {
                            return false;
                        }
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
                    HistoryFile.setPathToHistoryFile(jFileChooser.getSelectedFile().getParent(), "history.txt");


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
                jFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

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
                    HistoryFile.setPathToHistoryFile(jFileChooser.getSelectedFile().getParent(), "history.txt");


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
                    xmlData = xmlFile.getXmlData();
                    position = 51;
                    jslider.setValue(51);
                    if (buttonsPanel == null) {
                        buttonsPanel = new JPanel();
                        buttonsPanel.setLayout(null);
                        buttonsPanel.setSize(jScrollPane4.getWidth(), jScrollPane4.getHeight());
                    }
                    int left = createButtonsOnPanelByCurrentHeadAndTail(buttonsPanel, position);//position + size);
                    buttonsPanel.setPreferredSize(new Dimension(left, jScrollPane4.getHeight()));
                    jScrollPane4.getViewport().setView(buttonsPanel);
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

        jLabel2.setText("");
        jLabel4.setText("");
        jLabel6.setText("");
        jLabel8.setText("");
        jLabel10.setText("");
        jLabel12.setText("");
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
        jLabel12.setText("<HTML><U>" + genome.getGeneLink() + "</U></HTML>");
        //return true;
    }

    /**
     * Draw Black line
     *
     * @param biobrickPanel
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
     * to paint buttons on biobrickPanel from head
     * @param biobrickPanel
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
    int id;

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
//        jScrollPane1.setOpaque(true);
        jScrollPane1.validate();
    }

    /**
     * the actionListener that use by the gene button
     */
    public class buttonActlistener implements ActionListener {

        genomeBrowserJFrame brow;
        public int head = 0;

//        public buttonActlistener(int i, Browser brow) {
//            this.head = i;
//            this.brow = brow;
//        }
        public buttonActlistener(int i, genomeBrowserJFrame frame) {
            this.head = i;
            this.brow = frame;
        }

        public void actionPerformed(ActionEvent e) {
            position = head;
            for (int i = 0; i < brow.xmlData.size(); i++) {
                if (brow.xmlData.get(i).getHead() == head) {
                    brow.showGenomeInformaiton(brow.xmlData.get(i));
                    break;
                }
            }
            brow.IsGeneButtonClick = true;
            jslider.setValue(position - 50);
            jslider.setValue(50);
            brow.IsGeneButtonClick = false;
            System.out.print(head);
        }
    }

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
                if (userInput.equals("Product")) {
                    geneNameList[i] = genes.get(i).getProduct();
                } else {
                    geneNameList[i] = genes.get(i).getName();
                }
            }
            final JList geneJList = new JList(geneNameList);
            geneJList.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    showGenomeInformaiton(genes.get(geneJList.getSelectedIndex()));
                    position = genes.get(geneJList.getSelectedIndex()).head;
                    jslider.setValue(genes.get(geneJList.getSelectedIndex()).getHead());
                    frame.dispose();
                }
            });
            JScrollPane scrollpane = new JScrollPane(geneJList);
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
            return true;
        }
    }

    public boolean isNumber(String str) {
        if (java.lang.Character.isDigit(str.charAt(0))) {
            return true;
        }
        return false;
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
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        positionInputArea = new javax.swing.JTextField();
        submitBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        zoomSlider = new javax.swing.JSlider();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openFastaAndXmlItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Genome Browser");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setName("jPanel6"); // NOI18N
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
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setBackground(java.awt.Color.white);
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("To show the seq of genome"); // NOI18N
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setOpaque(false);

        sliderPane.setBackground(new java.awt.Color(255, 255, 255));
        sliderPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        sliderPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sliderPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        sliderPane.setName("sliderPane"); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 1, 18));
        jLabel3.setForeground(new java.awt.Color(64, 64, 65));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("HEAD");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial Narrow", 1, 18));
        jLabel5.setForeground(new java.awt.Color(64, 64, 65));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("TAIL");
        jLabel5.setName("jLabel5"); // NOI18N

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane4.setAutoscrolls(true);
        jScrollPane4.setName("jScrollPane4"); // NOI18N
        jScrollPane4.setOpaque(false);

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jPanel4.setFont(new java.awt.Font("微软雅黑", 1, 18));
        jPanel4.setName("jPanel4"); // NOI18N

        nameLabel.setBackground(new java.awt.Color(255, 255, 255));
        nameLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        nameLabel.setForeground(new java.awt.Color(105, 105, 105));
        nameLabel.setText("name");
        nameLabel.setName("nameLabel"); // NOI18N

        productLabel.setBackground(new java.awt.Color(255, 255, 255));
        productLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        productLabel.setForeground(new java.awt.Color(105, 105, 105));
        productLabel.setText("product");
        productLabel.setName("productLabel"); // NOI18N

        linkLabel.setBackground(new java.awt.Color(255, 255, 255));
        linkLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        linkLabel.setForeground(new java.awt.Color(105, 105, 105));
        linkLabel.setText("link");
        linkLabel.setName("linkLabel"); // NOI18N

        headLabel.setBackground(new java.awt.Color(255, 255, 255));
        headLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        headLabel.setForeground(new java.awt.Color(105, 105, 105));
        headLabel.setText("head");
        headLabel.setName("headLabel"); // NOI18N

        tailLabel.setBackground(new java.awt.Color(255, 255, 255));
        tailLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        tailLabel.setForeground(new java.awt.Color(105, 105, 105));
        tailLabel.setText("tail");
        tailLabel.setName("tailLabel"); // NOI18N

        lengthLabel.setBackground(new java.awt.Color(255, 255, 255));
        lengthLabel.setFont(new java.awt.Font("Arial Black", 1, 18));
        lengthLabel.setForeground(new java.awt.Color(105, 105, 105));
        lengthLabel.setText("length");
        lengthLabel.setName("lengthLabel"); // NOI18N

        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane11.setName("jScrollPane11"); // NOI18N

        jPanel20.setBackground(java.awt.Color.white);
        jPanel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
        jPanel20.setName("jPanel20"); // NOI18N
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel2.setForeground(new java.awt.Color(105, 105, 105));
        jLabel2.setText("jLabel2"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel20.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 129, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel4.setForeground(new java.awt.Color(105, 105, 105));
        jLabel4.setText("jLabel4");
        jLabel4.setName("jLabel4"); // NOI18N
        jPanel20.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 173, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel6.setForeground(new java.awt.Color(105, 105, 105));
        jLabel6.setText("jLabel2");
        jLabel6.setName("jLabel6"); // NOI18N
        jPanel20.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 226, 127, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel8.setForeground(new java.awt.Color(105, 105, 105));
        jLabel8.setText("ok");
        jLabel8.setName("jLabel8"); // NOI18N
        jPanel20.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 41, -1, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel10.setForeground(new java.awt.Color(105, 105, 105));
        jLabel10.setText("jLabel2");
        jLabel10.setName("jLabel10"); // NOI18N
        jPanel20.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 2, 105, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial Black", 1, 18));
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("jLabel2");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel12.setName("jLabel12"); // NOI18N
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
        jPanel20.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 85, 520, -1));

        jScrollPane11.setViewportView(jPanel20);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(lengthLabel)
                    .addComponent(headLabel)
                    .addComponent(linkLabel)
                    .addComponent(productLabel)
                    .addComponent(tailLabel))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addGap(16, 16, 16)
                        .addComponent(productLabel)
                        .addGap(18, 18, 18)
                        .addComponent(linkLabel)
                        .addGap(18, 18, 18)
                        .addComponent(headLabel)
                        .addGap(18, 18, 18)
                        .addComponent(tailLabel)
                        .addGap(27, 27, 27)
                        .addComponent(lengthLabel)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 446, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(sliderPane, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(sliderPane, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 192, 16));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel1.setName("jPanel1"); // NOI18N

        jComboBox1.setUI(new IComboBoxUI());
        jComboBox1.setRenderer(new IComboBoxRenderer());
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Position", "Product", "Name" }));
        jComboBox1.setToolTipText("Position");
        jComboBox1.setName("jComboBox1"); // NOI18N

        positionInputArea.setFont(new java.awt.Font("宋体", 1, 15));
        positionInputArea.setName("positionInputArea"); // NOI18N

        submitBtn.setBackground(new java.awt.Color(255, 255, 255));
        submitBtn.setFont(new java.awt.Font("Gisha", 1, 15));
        submitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/igem/browser/submit.png"))); // NOI18N
        submitBtn.setName("submitBtn"); // NOI18N
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(positionInputArea, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(submitBtn, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(positionInputArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 192, 16));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 0));
        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 16));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/igem/browser/zoom.png"))); // NOI18N
        jLabel1.setText("Zoom");
        jLabel1.setName("jLabel1"); // NOI18N
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
        zoomSlider.setName("zoomSlider"); // NOI18N
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

        menuBar.setBackground(new java.awt.Color(255, 255, 255));
        menuBar.setFont(new java.awt.Font("Arial Black", 0, 14));
        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setBackground(java.awt.Color.white);
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        fileMenu.setFont(new java.awt.Font("Arial Black", 0, 14));
        fileMenu.setName("fileMenu"); // NOI18N

        openFastaAndXmlItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        openFastaAndXmlItem.setBackground(java.awt.Color.white);
        openFastaAndXmlItem.setFont(new java.awt.Font("Arial Black", 0, 15));
        openFastaAndXmlItem.setMnemonic('o');
        openFastaAndXmlItem.setText("Open Fasta and Xml");
        openFastaAndXmlItem.setToolTipText("<html><BODY STYLE=\\\"BACKGROUND-COLOR:#FFFFFF;\\\">Click to open the fasta and xml file of Genomes</html>"); // NOI18N
        openFastaAndXmlItem.setName("openFastaAndXmlItem"); // NOI18N
        fileMenu.add(openFastaAndXmlItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setBackground(java.awt.Color.white);
        exitMenuItem.setFont(new java.awt.Font("Arial Black", 0, 15));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit"); // NOI18N
        exitMenuItem.setToolTipText("<html><BODY STYLE=\\\"BACKGROUND-COLOR:#FFFFFF;\\\">Click to exit the program</html>"); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setBackground(java.awt.Color.white);
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");
        helpMenu.setFont(new java.awt.Font("Arial Black", 0, 14));
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setBackground(java.awt.Color.white);
        aboutMenuItem.setFont(new java.awt.Font("Arial Black", 0, 15));
        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("<html><BODY STYLE=\\\"BACKGROUND-COLOR:#FFFFFF;\\\">About us</html>"); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        if (this.jLabel12.getText().isEmpty()) {
            return;
        }
        //System.out.println(this.removeHtmlString(this.jLabel12.getText()));          
        try {
            Runtime.getRuntime().exec("cmd.exe /c start iexplore " + StringUtils.removeHtmlString(this.jLabel12.getText()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "cannot open url");
        }
		 }//GEN-LAST:event_jLabel12MouseClicked

        private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
            //this.setCursor(Cursor.HAND_CURSOR);         
            this.jLabel12.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}//GEN-LAST:event_jLabel12MouseEntered

        private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
            // TODO add your handling code here:         
            //this.setCursor(Cursor.DEFAULT_CURSOR);
            this.jLabel12.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel12MouseExited

    private void jPanel6ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel6ComponentHidden
		}//GEN-LAST:event_jPanel6ComponentHidden
        private void jPanel6ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel6ComponentShown
		}//GEN-LAST:event_jPanel6ComponentShown

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
		}//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:         
        new AboutJFrame().setVisible(true);
		}//GEN-LAST:event_aboutMenuItemActionPerformed

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
        int left = this.createButtonsOnPanelByCurrentHeadAndTail(this.buttonsPanel, position);//position + size);
        this.buttonsPanel.setPreferredSize(new Dimension(left, jScrollPane4.getHeight()));
        jScrollPane4.getViewport().setView(this.buttonsPanel);
        jScrollPane4.validate();
		}//GEN-LAST:event_submitBtnMouseClicked

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        // TODO add your handling code here:
	}//GEN-LAST:event_submitBtnActionPerformed
    private void zoomSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_zoomSliderMouseReleased
            if (file == null) {
                zoomSlider.setValue(11);
            }
	}//GEN-LAST:event_zoomSliderMouseReleased

    private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomSliderStateChanged
        System.out.println("Zoom Slider:" + zoomSlider.getValue());
        size = zoomSlider.getValue();
        System.out.println(table);
        if (file != null) {
            file.setReadSize(size);
            showATGC();
        }
		}//GEN-LAST:event_zoomSliderStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(genomeBrowserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(genomeBrowserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(genomeBrowserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(genomeBrowserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new genomeBrowserJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel headLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lengthLabel;
    private javax.swing.JLabel linkLabel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JMenuItem openFastaAndXmlItem;
    private javax.swing.JTextField positionInputArea;
    private javax.swing.JLabel productLabel;
    private javax.swing.JScrollPane sliderPane;
    private javax.swing.JButton submitBtn;
    private javax.swing.JLabel tailLabel;
    private javax.swing.JSlider zoomSlider;
    // End of variables declaration//GEN-END:variables
}
