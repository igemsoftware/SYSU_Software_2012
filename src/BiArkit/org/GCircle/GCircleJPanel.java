/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * GCircleJPanel.java
 *
 * Created on 2012-8-21, 17:44:27
 */
package org.GCircle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import org.igem.browser.Genome;
import org.sysu.igem.utils.GetGenomeFromXml;
import org.sysu.igem.utils.HistoryFile;
import org.sysu.igem.utils.ReadFileRandom;

/**
 * A self draw JPanel that can draw genomes in a GCircle way
 * @author Guo Jiexin
 */
public class GCircleJPanel extends javax.swing.JPanel {

    /**
     * Circles that the GCirclepanel have drawed,use it to find point in circle easier
     */
    List<circleData> circles = new ArrayList<circleData>();
    /**
     * Genome list's list
     */
    List<List<Genome>> genomesList = new ArrayList<List<Genome>>();
    /**
     * The total length of a genome list 
     */
    List<Integer> MAX_LENGTHS = new ArrayList<Integer>();
    /**
     * Genome Signals' list's list
     */
    List<List<Double>> genomeSignalList = new ArrayList<List<Double>>();
    List<List<Double>> secondGenomeSignalList = new ArrayList<List<Double>>();
    /**
     * The max signal of a signals' list
     */
    List<Double> genomeSignalMax = new ArrayList<Double>();
    List<Double> secondGenomeSignalMax = new ArrayList<Double>();
    /**
     * The Signals' Color when drawing
     */
    final private Color GENOME_SIGNAL_COLOR = Color.black;
    /**
     * To know if the user choose to show tooltip or information Form
     */
    private boolean tooltipEnable = false;
    /**
     * The scale of the panel,use it to zoom in or out
     */
    private double scale = 1.0;
    /**
     * start to draw angle is 90 (from the top of a circle)
     * from y axis positive side to start drawing
     *          y axis
     *          ^-->from here
     *          |
     *          |
     *          |
     * --------------------> x axis
     *          |
     *          |
     *          |     
     */
    final private double START_DRAW_ANGLE = 90;
    private Font menuFont = new java.awt.Font("Arial Black", 0, 12);
    private Rectangle2D maxRectOfCircle = null;
    private Point prePoint = null;
    private Point nowPoint = null;
    /**
     * The GCircleJPanel must bu add on a jscrollpanne to scroll
     */
    private JScrollPane scrollpane = null;
    public ImagePanel thumbnailPane = null;
    private JMenuItem resetScaleMenuItem;
    private JMenu optionMenu;
    private JMenuItem setScaleMenuItem;

    public JPanel getThumbnailPane() {
        return thumbnailPane;
    }

    public void setThumbnailPane(ImagePanel thumbnailPane) {
        this.thumbnailPane = thumbnailPane;
    }
    private SoftFileRead softFile = null;
    /**
     * The JFileChooser that use to open files(use the same one to save the last directory user choose)
     */
    private JFileChooser jFileChooser = new JFileChooser();
    /**
     * For menu's exit button,so need a frame variable to close
     */
    private JFrame parent = null;
    private GetGenomeFromXml xmlFile = null;
//    /**
//     * The image that paint on GCircleJPanel
//     * to improve the performance of the program 
//     * (once the panel is paint then use this image to save and needn't to redraw 
//     * all the arcs on the panel except the image is clear)
//     */
//    private BufferedImage GCirclePanelImage = null;

    /**
     * @return the scrollpane
     */
    public JScrollPane getScrollpane() {
        return scrollpane;
    }

    /**
     * Reset the scale to 1.0
     */
    private void resetScale() {
        this.setToolTipText("");
        this.clearDrawed();
        this.setSize(this.scrollpane.getSize());
        this.setPreferredSize(null);
        this.maxRectOfCircle = this.scrollpane.getBounds();
        this.scale = 1.0;
        this.repaint();
    }

    private void zoomOutScale() {
        this.setToolTipText("");
        this.clearDrawed();
        this.setSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * 0.8), (int) (this.maxRectOfCircle.getHeight() * 0.8)));
        this.setPreferredSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * 0.8), (int) (this.maxRectOfCircle.getHeight() * 0.8)));
        this.scale *= 0.8;
    }

    public void setZoomScale(double scaleToSet) {
        this.resetScale();
        this.setSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * scaleToSet), (int) (this.maxRectOfCircle.getHeight() * scaleToSet)));
        this.setPreferredSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * scaleToSet), (int) (this.maxRectOfCircle.getHeight() * scaleToSet)));
        this.scale = scaleToSet;
    }

    private void zoomInScale() {
        this.setToolTipText("");
        this.clearDrawed();
        this.setSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * 1.2), (int) (this.maxRectOfCircle.getHeight() * 1.2)));
        this.setPreferredSize(new Dimension((int) (this.maxRectOfCircle.getWidth() * 1.2), (int) (this.maxRectOfCircle.getHeight() * 1.2)));
        this.scale *= 1.2;
    }
    /**
     * To realize double buffered
     */
    private Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (this.offScreenImage != null) {            
        } else {
            offScreenImage = createImage(this.getWidth(), this.getHeight());
            this.mypaint(offScreenImage.getGraphics());
        }
        g.drawImage(this.offScreenImage, 0, 0, null);
        g.dispose();
    }

    /**
     * A class that use to contain message for the progressBar
     */
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
     * Using SwingWorker to load a genome signal list in background
     */
    public class LoadAListGenomeSignalBackground extends SwingWorker<Void, Message> {

        private List<Genome> genes = null;
        MyProgress use = null;

        public LoadAListGenomeSignalBackground(List<Genome> genes) {
            super();
            this.genes = genes;
            this.use = new MyProgress();
        }

        @Override
        protected void done() {
            this.use.finish();
            GCircleJPanel.this.repaint();
        }

        /**
         * To change the progressBar's message and persent
         */
        @Override
        protected void process(List<Message> chunks) {
            use.printProgress(chunks.get(chunks.size() - 1).message,
                    chunks.get(chunks.size() - 1).percent);
        }

        @Override
        protected Void doInBackground() throws Exception {
            if (this.genes != null && !this.genes.isEmpty()) {
                Double max = new Double(0);
                List<Double> temp = this.getGenomeSignalListInBackground();
                GCircleJPanel.this.genomeSignalList.add(temp);
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j) > max) {
                        max = temp.get(j);
                    }
                }
                GCircleJPanel.this.genomeSignalMax.add(max);
                if (GCircleJPanel.this.secondGenomeSignalList.size() != 0) {
                    max = new Double(0);
                    temp = this.getGenomeSignalListInBackground();
                    GCircleJPanel.this.secondGenomeSignalList.add(temp);
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j) > max) {
                            max = temp.get(j);
                        }
                    }
                    GCircleJPanel.this.secondGenomeSignalMax.add(max);
                }
            }
            return null;
        }

        /**
         * Get signals by soft file
         * @param genes
         * @return 
         */
        private List<Double> getGenomeSignalListInBackground() {
            if (GCircleJPanel.this.softFile == null || GCircleJPanel.this.softFile.getSignalsRead() == null) {
                return null;
            }
            List<Double> signals = new ArrayList<Double>();
            double find = 0;
            for (int i = 0; i < genes.size(); i++) {
                if (genes.get(i).getName().isEmpty())//if the gene has no name :continue with the value 0
                {
                    signals.add(0.0);
                    continue;
                }
                find = GCircleJPanel.this.softFile.getSignalByGeneTitle(genes.get(i).getName());
                if (find == 0.0) {
                    find = GCircleJPanel.this.softFile.getSignalByGeneSymbol(genes.get(i).getName());
                }
                signals.add(find);
                this.publish(new Message(i + " of " + genes.size() + " " + genes.get(i).getName(), (int) ((double) i / genes.size() * 100)));
            }
            return signals;
        }
    }

    /**
     * Using SwingWorker to load all genome signal lists in background
     */
    public class LoadAllGenomeSignalsBackGround extends SwingWorker<Void, Message> {

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
            GCircleJPanel.this.clearDrawed();
        }

        /**
         * To change the progressBar's message and persent
         */
        @Override
        protected void process(List<Message> chunks) {
            use.printProgress(chunks.get(chunks.size() - 1).message,
                    chunks.get(chunks.size() - 1).percent);
        }
        /**
         * To create the ProgressFrame
         */
        MyProgress use = null;

        public LoadAllGenomeSignalsBackGround() {
            super();
            this.use = new MyProgress();
            use.printProgress("Starting....", 0);
        }

        /**
         * Get signals by soft file
         * @param genes
         * @return 
         */
        private List<Double> getGenomeSignalListInBackground(List<Genome> genes) {
            if (GCircleJPanel.this.softFile == null || GCircleJPanel.this.softFile.getSignalsRead() == null) {
                return null;
            }
            List<Double> signals = new ArrayList<Double>();
            double find = 0;
            for (int i = 0; i < genes.size(); i++) {
                //if the gene has no name :continue with the value 0
                if (genes.get(i).getName().isEmpty()) {
                    signals.add(0.0);
                    continue;
                }
                find = GCircleJPanel.this.softFile.getSignalByGeneTitle(genes.get(i).getName());
                if (find == 0.0) {
                    find = GCircleJPanel.this.softFile.getSignalByGeneSymbol(genes.get(i).getName());
                }
                signals.add(find);
                this.publish(new Message(i + " of " + genes.size() + " " + genes.get(i).getName(), (int) ((double) i / genes.size() * 100)));
            }
            return signals;
        }

        /**
         * create R commands to calculate and write to files
         */
        @Override
        protected Void doInBackground() throws Exception {

            if (GCircleJPanel.this.genomeSignalList.isEmpty()) {
                for (int i = 0; i < GCircleJPanel.this.genomesList.size(); i++) {
                    Double max = new Double(0);
                    List<Double> temp = this.getGenomeSignalListInBackground(GCircleJPanel.this.genomesList.get(i));

                    GCircleJPanel.this.genomeSignalList.add(temp);
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j) > max) {
                            max = temp.get(j);
                        }
                    }
                    GCircleJPanel.this.genomeSignalMax.add(max);
                }
            } else {
                for (int i = 0; i < GCircleJPanel.this.genomesList.size(); i++) {
                    Double max = new Double(0);
                    List<Double> temp = this.getGenomeSignalListInBackground(GCircleJPanel.this.genomesList.get(i));
                    GCircleJPanel.this.secondGenomeSignalList.add(temp);
                    for (int j = 0; j < temp.size(); j++) {
                        if (temp.get(j) > max) {
                            max = temp.get(j);
                        }
                    }
                    GCircleJPanel.this.secondGenomeSignalMax.add(max);
                }
            }
            return null;
        }
    }

    /**
     * Get signals by soft file
     * @param genes
     * @return 
     */
    private List<Double> getGenomeSignalListInBackground(List<Genome> genes) {
        if (GCircleJPanel.this.softFile == null || GCircleJPanel.this.softFile.getSignalsRead() == null) {
            return null;
        }
        List<Double> signals = new ArrayList<Double>();
        double find = 0;
        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i).getName().isEmpty())//if the gene has no name :continue with the value 0
            {
                signals.add(0.0);
                continue;
            }
            find = GCircleJPanel.this.softFile.getSignalByGeneTitle(genes.get(i).getName());
            if (find == 0.0) {
                find = GCircleJPanel.this.softFile.getSignalByGeneSymbol(genes.get(i).getName());
            }
            signals.add(find);
            //this.publish(new Message(i + " of " + genes.size() + " " + genes.get(i).getName(), (int) ((double) i / genes.size() * 100)));
        }
        return signals;
    }

    /**
     * Get all Genome Signals
     */
    public void getAllGenomeSignals() {
        if (this.softFile == null || this.softFile.getSignalsRead() == null) {
            return;
        }
        LoadAllGenomeSignalsBackGround back = new LoadAllGenomeSignalsBackGround();
        back.execute();
    }
    /**
     * A test function that make the signals 0-4 repeating
     * @param genes
     * @return 
     */
    private int testSignalMax = 10;

    public List<Double> test_getGenomeSignalList(List<Genome> genes) {
        if (this.softFile == null || this.softFile.getSignalsRead() == null) {
            return null;
        }
        List<Double> signals = new ArrayList<Double>();
        for (int i = 0; i < genes.size(); i++) {
            signals.add((double) (i % testSignalMax));
        }
        return signals;
    }

//    /**
//     * Get signals by soft file
//     * @param genes
//     * @return 
//     */
//    private List<Double> getGenomeSignalList(List<Genome> genes) {
//        if (this.softFile == null || this.softFile.getSignalsRead() == null) {
//            return null;
//        }
//        List<Double> signals = new ArrayList<Double>();
//        double find = 0;
//        for (int i = 0; i < genes.size(); i++) {
//            if (genes.get(i).getName().isEmpty())//if the gene has no name :continue with the value 0
//            {
//                signals.add(0.0);
//                continue;
//            }
//            find = this.softFile.getSignalByGeneTitle(genes.get(i).getName());
//            if (find == 0.0) {
//                find = this.softFile.getSignalByGeneSymbol(genes.get(i).getName());
//            }
//            signals.add(find);//.getSignalById(genes.get(i).getName()));
//            //System.out.println(genes.get(i).getName() + " " + signals.get(i));
//        }
//        return signals;
//    }
    /**
     * Create a file menu:
     * File->Open Signal File;
     *      Open Soft File;
     *      Exit
     * @return 
     */
    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setBackground(Color.white);
        fileMenu.setFont(new java.awt.Font("Arial Black", 0, 14));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        final JMenuItem openXmlFastaMenuItem = new JMenuItem("Open Xml File and Fasta File");
        openXmlFastaMenuItem.setFont(menuFont);
        openXmlFastaMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    jFileChooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setDialogTitle("Read Xml File");
                jFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return (f.isDirectory() || f.getName().endsWith(".xml"));
                    }

                    @Override
                    public String getDescription() {
                        return "Xml file";
                    }
                });
                jFileChooser.showOpenDialog(GCircleJPanel.this);
                try {
                    HistoryFile.setPathToHistoryFile(jFileChooser.getSelectedFile().getPath(), "history.txt");
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (jFileChooser.getSelectedFile() == null) {
                    return;
                }
                String xmlPath = jFileChooser.getSelectedFile().getAbsolutePath();
                if (xmlFile == null) {
                    xmlFile = new GetGenomeFromXml(jFileChooser.getSelectedFile().getAbsolutePath());
                } else {
                    xmlFile.setXmlData(jFileChooser.getSelectedFile().getAbsolutePath());
                }
                try {
                    jFileChooser.setCurrentDirectory(new File(HistoryFile.getPathFromHistoryFile("history.txt")));
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setDialogTitle("Read Fasta File");
                jFileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return true;
                    }

                    @Override
                    public String getDescription() {
                        return "Fasta file";
                    }
                });
                jFileChooser.showOpenDialog(GCircleJPanel.this);
                if (jFileChooser.getSelectedFile() == null) {
                    return;
                }
                ReadFileRandom file = new ReadFileRandom(jFileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(jFileChooser.getSelectedFile().getAbsolutePath());
                try {
                    HistoryFile.setPathToHistoryFile(jFileChooser.getSelectedFile().getPath(), "history.txt");
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                GCircleJPanel.this.readGenomesFromXml(xmlPath, file.getFileSize() - file.getFirstLength());
                System.out.println("Gene length total: " + (file.getFileSize() - file.getFirstLength()));
                if (GCircleJPanel.this.genomesList.size() > GCircleJPanel.this.genomeSignalList.size() && GCircleJPanel.this.softFile != null) {
                    for (int i = GCircleJPanel.this.genomeSignalList.size(); i < GCircleJPanel.this.genomesList.size(); i++) {
                        new GCircleJPanel.LoadAListGenomeSignalBackground(GCircleJPanel.this.genomesList.get(i)).execute();
                    }
                }
                optionMenu.setEnabled(true);
                GCircleJPanel.this.setScaleMenuItem.setEnabled(true);
                // GCircleJPanel.this.repaint();               
            }
        });
        JMenuItem openSignalAndSoftFileMenuItem = new JMenuItem("Open Signal and Soft File");
        openSignalAndSoftFileMenuItem.setFont(this.menuFont);
        openSignalAndSoftFileMenuItem.setMnemonic('t');
        openSignalAndSoftFileMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser.setSelectedFile(null);
                if (new File(System.getProperty("user.dir") + "\\GCircle").isDirectory()) {
                    jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\GCircle"));
                } else//set directory to current directory
                {
                    jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                }
                try {
                    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jFileChooser.setFileFilter(new FileFilter() {

                        @Override
                        public boolean accept(File f) {
                            return true;
                        }

                        @Override
                        public String getDescription() {
                            return "Soft file";
                        }
                    });
                    jFileChooser.setDialogTitle("Open Soft File");
                    jFileChooser.showOpenDialog(null);
                    if (jFileChooser.getSelectedFile() == null) {
                        return;
                    }
                    GCircleJPanel.this.softFile = new SoftFileRead(jFileChooser.getSelectedFile().getPath());
                    System.out.println(GCircleJPanel.this.softFile.getFilePath());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                jFileChooser.setSelectedFile(null);
                try {
                    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jFileChooser.setDialogTitle("Read Signal File");
                    jFileChooser.setFileFilter(new FileFilter() {

                        @Override
                        public boolean accept(File f) {
                            return true;
                        }

                        @Override
                        public String getDescription() {
                            return "Signal file";
                        }
                    });
                    jFileChooser.showOpenDialog(null);
                    if (jFileChooser.getSelectedFile() == null) {
                        return;
                    }
                    if (GCircleJPanel.this.softFile != null) {
                        GCircleJPanel.this.softFile.readSignalsFile(jFileChooser.getSelectedFile().getPath());
                        System.out.println(GCircleJPanel.this.softFile.getSignalsRead().getFilePath());
                        GCircleJPanel.this.getAllGenomeSignals();
                        GCircleJPanel.this.clearDrawed();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JMenuItem clearMenuItem = new JMenuItem("Clear");
        clearMenuItem.setFont(menuFont);
        clearMenuItem.setMnemonic('c');
        clearMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GCircleJPanel.this.clearAll();

            }
        });
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setFont(menuFont);
        exitMenuItem.setMnemonic('x');
        exitMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GCircleJPanel.this.parent.dispose();
            }
        });
        fileMenu.add(openXmlFastaMenuItem);
        fileMenu.add(openSignalAndSoftFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(clearMenuItem);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    /**
     * Create "option" menu:
     * Option->enable Tooltip/disable Tooltip;
     *          Zoom In;
     *          Zoom Out
     * @return 
     */
    private JMenu createOptionMenu() {        
        optionMenu = new JMenu("Option");
        optionMenu.setBackground(Color.white);
        optionMenu.setEnabled(false);
        optionMenu.setMnemonic(KeyEvent.VK_O);
        optionMenu.setFont(menuFont);
        final JMenuItem tooltipEnableMenuItem = new JMenuItem("enable Tooltip");
        tooltipEnableMenuItem.setMnemonic('e');
        tooltipEnableMenuItem.setFont(menuFont);
        if (this.tooltipEnable) {
            tooltipEnableMenuItem.setText("disable Tooltip");
            tooltipEnableMenuItem.setMnemonic('d');
        }
        tooltipEnableMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GCircleJPanel.this.tooltipEnable = !GCircleJPanel.this.tooltipEnable;
                if (GCircleJPanel.this.tooltipEnable) {
                    tooltipEnableMenuItem.setText("disable Tooltip");
                    tooltipEnableMenuItem.setMnemonic('d');
                } else {
                    GCircleJPanel.this.setToolTipText("");//clear tootip
                    tooltipEnableMenuItem.setText("enable Tooltip");
                    tooltipEnableMenuItem.setMnemonic('e');
                }
            }
        });
        final JMenuItem saveToJPGMenuItem = new JMenuItem("Save To JPEG");
        saveToJPGMenuItem.setMnemonic('s');
        saveToJPGMenuItem.setFont(menuFont);
        saveToJPGMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    GCircleJPanel.this.saveComponentToFile(GCircleJPanel.this);
                } catch (IOException ex) {
                    Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        setScaleMenuItem = new JMenuItem("Set scale");
        setScaleMenuItem.setFont(menuFont);
        setScaleMenuItem.setMnemonic('s');
        setScaleMenuItem.setEnabled(false);
        setScaleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new SetScaleJFrame(GCircleJPanel.this).setVisible(true);
            }
        });
        resetScaleMenuItem = new JMenuItem("Reset scale");
        resetScaleMenuItem.setFont(menuFont);
        resetScaleMenuItem.setMnemonic('r');
        resetScaleMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GCircleJPanel.this.resetScale();
                //GCircleJPanel.this.scale=1.0;
                //GCircleJPanel.this.repaint();
            }
        });
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
        zoomInMenuItem.setFont(menuFont);
        zoomInMenuItem.setMnemonic('i');
        zoomInMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //GCircleJPanel.this.scale += 0.1;//Zoom in  
                //To redraw all the panel
                //GCircleJPanel.this.afterResetScale();
                GCircleJPanel.this.zoomInScale();
            }
        });
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");
        zoomOutMenuItem.setFont(menuFont);
        zoomOutMenuItem.setMnemonic('o');
        zoomOutMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GCircleJPanel.this.zoomOutScale();
            }
        });
        optionMenu.add(tooltipEnableMenuItem);
        optionMenu.add(saveToJPGMenuItem);
        optionMenu.addSeparator();
        optionMenu.add(setScaleMenuItem);
        optionMenu.add(resetScaleMenuItem);
        optionMenu.add(zoomInMenuItem);
        optionMenu.add(zoomOutMenuItem);
        return optionMenu;
    }

    /**
     * Create a menubar that has 
     * File;Option
     * @return 
     */
    public JMenuBar createFrameMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.white);
        menuBar.add(this.createFileMenu());
        menuBar.add(this.createOptionMenu());
        return menuBar;
    }

    /**
     * @param scrollpane the scrollpane to set
     */
    public void setScrollpane(JScrollPane scrollpane) {
        this.scrollpane = scrollpane;
    }

    /**
     * Read Genomes by a List of genome
     * @param genes
     * @param maxLength 
     */
    public void readGenomesByList(List<Genome> genes, int maxLength) {
        this.MAX_LENGTHS.add(maxLength);
        this.genomesList.add(genes);
    }

    /**
     * Read Genomes by xmlFile and fastaFile
     * @param xmlFile
     * @param fastaFile 
     */
    public void readGenomsByFile(GetGenomeFromXml xmlFile, ReadFileRandom fastaFile) {
        this.genomesList.add(xmlFile.getXmlData());
        this.MAX_LENGTHS.add(fastaFile.getFileSize() - fastaFile.getFirstLength());
        System.out.println(fastaFile.getFileSize() - fastaFile.getFirstLength());
    }

    /**
     * read a xml to get its data of genomes
     * @param xmlfile the path of xmlfile     
     */
    public void readGenomesFromXml(String xmlfile, int maxLength) {
        this.MAX_LENGTHS.add(maxLength);
        GetGenomeFromXml xmlFile = new GetGenomeFromXml(xmlfile);
        this.genomesList.add(xmlFile.getXmlData());
    }

    /**
     * To make the tooltip place at the outter border of the circle when tooltipEnabled
     * @param event
     * @return 
     */
    @Override
    public Point getToolTipLocation(MouseEvent event) {
        if (this.tooltipEnable) {
            for (int i = 0; i < this.circles.size(); i++) {
                //to judge which circle is the mouse pointing in
                if (this.isPointInCircle(this.circles.get(i), event.getX(), event.getY())) {
                    return this.getOuterPointOfCircleByLineXYandCenter(
                            this.circles.get(i), event.getX(), event.getY());
                }
            }
        }
        return super.getToolTipLocation(event);
    }

    /**
     * 
     * to return a point in the line(circle.centerX,circle.centerY)to(x,y)
     * and it is on the outer border of the circle
     *
     * -------*((int) pointx, (int) pointy) the point needed to return
     * |     /
     * |    / ->  the bevel edge's length equals the circle's radius 
     * |   *(x,y)->the point that your mouse is placing
     * |  /
     * | /-->this is the angle I calc out
     * *->(circle.centerX,circle.centerY)
     * 
     * @param circle the circle that we need its outer pint
     * @param x
     * @param y
     * @return a point that is on circle's outer border
     */
    private Point getOuterPointOfCircleByLineXYandCenter(circleData circle, double x, double y) {
        double angle = this.getAngleFromLineToYAxis(circle, x, y);
        double pointx = Math.sin(Math.toRadians(angle)) * circle.radius + circle.x;
        double pointy = circle.y - Math.cos(Math.toRadians(angle)) * circle.radius;
        return new Point((int) (pointx), (int) (pointy));
    }

    public GCircleJPanel(JFrame frame) {
        this.initComponents();
        this.parent = frame;
        //if current directory has sub folder"GCircle" then go into it
        if (new File(System.getProperty("user.dir") + "\\GCircle").isDirectory()) {
            this.jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\GCircle"));
        } else {//set directory to current directory
            this.jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        }

        this.jFileChooser.setLocation(new Point(500, 400));
        this.updateUI();
    }

    /**
     * Clear all vars
     */
    public void clearAll() {
        this.offScreenImage = null;
        this.resetScale();
        this.circles.clear();
        this.genomesList.clear();
        this.genomeSignalList.clear();
        this.MAX_LENGTHS.clear();
        if (this.softFile != null) {
            this.softFile = null;
        }
        if (this.secondGenomeSignalMax != null) {
            this.secondGenomeSignalMax.clear();
            this.secondGenomeSignalList.clear();
        }
        this.optionMenu.setEnabled(false);
        this.repaint();
    }

    /**
     * Clear all lists about drawing
     */
    public void clearDrawed() {
        this.offScreenImage = null;
        this.circles.clear();
        this.thumbnailPane.clearDraw();
        this.repaint();
    }

    private void mypaint(Graphics g) {
        this.setAnti_Aliasing(g);//set Graphics g's Anti aliasing
        //the largest rectOfCircle(as the panel border)  
        maxRectOfCircle = this.getCenterRectangle2D(
                this.getWidth() / 2, this.getWidth() / 2,
                this.getWidth(), this.getWidth());
        if (this.genomesList.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.genomesList.size(); i++) {
            Rectangle2D rectOfCircle = this.getCenterRectangle2D(maxRectOfCircle,
                    maxRectOfCircle.getWidth() / this.genomesList.size()
                    * (this.genomesList.size() - i), maxRectOfCircle.getWidth()
                    / this.genomesList.size() * (this.genomesList.size() - i));
            circleData circle = this.drawACircleGenome((Graphics2D) g, this.genomesList.get(i), rectOfCircle, this.MAX_LENGTHS.get(i));
            if (!this.genomesList.isEmpty() && !this.genomeSignalList.isEmpty() && i < this.genomeSignalList.size()) {
                System.out.println(i + " " + this.genomeSignalList.size());
                this.drawACircleSignal((Graphics2D) g, this.genomesList.get(i),
                        this.genomeSignalList.get(i), rectOfCircle,
                        this.genomeSignalMax.get(i), this.MAX_LENGTHS.get(i));
            }
            if (this.secondGenomeSignalList != null && !this.secondGenomeSignalList.isEmpty() && i < this.secondGenomeSignalList.size()) {
                this.drawACircleSignal((Graphics2D) g, this.genomesList.get(i),
                        this.genomeSignalList.get(i), this.getCenterRectangle2D(rectOfCircle, rectOfCircle.getWidth() / this.whiteCircleParameter,
                        rectOfCircle.getHeight() / this.whiteCircleParameter),
                        this.genomeSignalMax.get(i), this.MAX_LENGTHS.get(i));
            }
            if (!this.circles.contains(circle) && circle != null) {
                this.circles.add(circle);
            }
        }
        this.thumbnailPane.clearDraw();
    }

    @Override
    public void paint(Graphics g) {
        this.update(g);
        //super.paint(g);
//        if (offScreenImage == null) {
//            offScreenImage = this.createImage(this.getWidth(), this.getHeight());
//        }
//        Graphics goffScreen = offScreenImage.getGraphics();
//        goffScreen.drawImage(offScreenImage, 0, 0, null);
//        //g.clearRect(0, 0, imgwidth + 5, imgheight + 5);//缩小的时候移除上一幅图片
//        g.drawImage(offScreenImage, 0, 0, offScreenImage.getWidth(parent), offScreenImage.getHeight(parent), this);
    }

    private void moveScrollPaneTo(double xRate, double yRate) {
        this.scrollpane.getHorizontalScrollBar().setValue((int) (xRate));
        this.scrollpane.getVerticalScrollBar().setValue((int) (yRate));
    }

    /**
     * A filefilter that contains jpeg files
     */
    class JPGFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            String nameString = f.getName();
            return nameString.toLowerCase().endsWith(".jpg");
        }

        @Override
        public String getDescription() {
            return "jpg(*.jpg)";
        }
    }

    /**
     * To save a component's image to BufferedImage
     * @param component
     * @return
     * @throws IOException 
     */
    public BufferedImage saveComponentToBufferedImage(Component component) throws IOException {
        BufferedImage image = null;
        try {
            image = new BufferedImage(component.getWidth(),
                    component.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            component.print(g);
            g = null;
        } catch (Exception e) {
        }
        return image;
    }

    /**
     * The panel that shows the thumbnail
     */
    class ImagePanel extends JPanel {

        private BufferedImage image = null;
        GCircleJPanel GCirclePanel = null;
        private Color rectBorderColor = Color.RED;

        public void clearDraw() {
            this.image = null;
            this.repaint();
        }

        public BufferedImage getImage() {
            return image;
        }

        public void setImage(BufferedImage image) {
            this.image = image;
        }
        private Point prePoint = null;
        private Point nowPoint = null;

        public ImagePanel(GCircleJPanel GCirclePanel) {
            this.GCirclePanel = GCirclePanel;
            this.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                    ImagePanel.this.prePoint = e.getPoint();
                }

                public void mouseReleased(MouseEvent e) {
                    ImagePanel.this.nowPoint = e.getPoint();
                    double moveTo = (double) (ImagePanel.this.nowPoint.x - ImagePanel.this.prePoint.x) / ImagePanel.this.getWidth() * scrollpane.getHorizontalScrollBar().getMaximum();
                    scrollpane.getHorizontalScrollBar().setValue(scrollpane.getHorizontalScrollBar().getValue() + (int) moveTo);
                    moveTo = (double) (ImagePanel.this.nowPoint.y - ImagePanel.this.prePoint.y) / ImagePanel.this.getHeight() * scrollpane.getVerticalScrollBar().getMaximum();
                    scrollpane.getVerticalScrollBar().setValue(scrollpane.getVerticalScrollBar().getValue() + (int) moveTo);
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });
        }

        /**
         * To paint a rectangle that is the GCircle panel showing
         * @param g 
         */
        private void paintRectangle(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(this.rectBorderColor);
            double width = (double) scrollpane.getWidth() / GCirclePanel.getWidth() * this.getWidth();
            double height = (double) scrollpane.getHeight() / GCirclePanel.getHeight() * this.getHeight();
            double x = (double) scrollpane.getHorizontalScrollBar().getValue() / scrollpane.getHorizontalScrollBar().getMaximum() * this.getWidth();
            double y = (double) scrollpane.getVerticalScrollBar().getValue() / scrollpane.getVerticalScrollBar().getMaximum() * this.getHeight();
            Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
            g2d.draw(rect);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!GCirclePanel.genomesList.isEmpty()) {
                if (image == null) {
                    try {
                        image = GCirclePanel.saveComponentToBufferedImage(GCirclePanel);
                    } catch (IOException ex) {
                        Logger.getLogger(GCircleJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        image = zoomOutImage(image, this.getWidth(), this.getHeight());
                        g.drawImage(image, 0, 0, null);                    
                } else {
                    g.drawImage(image, 0, 0, null);
                }
            }
            
            this.paintRectangle(g);
        }
    }

    /**    
     * Zoom out the picture   
     * @param originalImage Original picture    
     * @param times Narrow times   
     * @return Image after zoom
     */
    public static BufferedImage zoomOutImage(BufferedImage originalImage, int width, int height) {
        if (originalImage == null) {
            return null;
        }
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * Save the component's image to a jpeg file
     * @param component
     * @throws IOException 
     */
    public void saveComponentToFile(Component component) throws IOException {
        BufferedImage image = null;
        try {
            image = new BufferedImage(component.getWidth(),
                    component.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();
            component.print(g);
            g = null;
        } catch (Exception e) {
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(this.jFileChooser.getCurrentDirectory());
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);//Set dialog to save_dialog
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        JPGFileFilter fileFilter = new JPGFileFilter();
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setSelectedFile(new File("GCircle"));
        int index = chooser.showDialog(null, "Save to JPEG");
        if (index == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String fileName = chooser.getName(f);
            if (!fileName.endsWith(".jpg")) {
                fileName += ".jpg";
            }
            String writePath = chooser.getCurrentDirectory().getAbsolutePath() + File.separator + fileName;
            System.out.println(writePath);
            File pathFile = new File(writePath);
            if (pathFile.exists()) {
                int num = JOptionPane.showConfirmDialog(null, "File already exists,would you like to overwrite it?", "File Exists", JOptionPane.YES_NO_OPTION); //JOptionPane.OK_CANCEL_OPTION);
                if (num == JOptionPane.YES_OPTION) {
                } else {
                    return;
                }
            }
            try {
                ImageIO.write(image, "jpg", pathFile);
            } catch (IOException e1) {
            }
        }
    }
    /**
     * The parameter that decided how large the white circle inside the GCircle is
     */
    private double whiteCircleParameter = 1.1; //if 1.1->1.5 the white circle is larger

    /**
     * to draw a circle from a genelist
     * @param g the Graphics to draw on
     * @param geneList the list of genes
     * @return circleData create by this function
     */
    private circleData drawACircleGenome(Graphics2D g2d, final List<Genome> geneList, final Rectangle2D rectOfCircle, final double max_length) {
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < geneList.size(); i++) {
            this.drawAGenome(g2d, geneList.get(i), rectOfCircle, max_length);
        }
        this.drawAWhiteCircle(g2d, this.getCenterRectangle2D(rectOfCircle,
                rectOfCircle.getWidth() / this.whiteCircleParameter, rectOfCircle.getWidth() / this.whiteCircleParameter));

        return new circleData(geneList, rectOfCircle.getCenterX(),
                rectOfCircle.getCenterY(), rectOfCircle.getWidth() / this.whiteCircleParameter / 2, rectOfCircle.getWidth() / 2);
    }

    /**
     * To draw a circle from a signalList
     * @param g2d
     * @param geneList
     * @param signalList
     * @param rectOfCircle
     * @param max_Signal
     * @param max_length 
     */
    private void drawACircleSignal(Graphics2D g2d, final List<Genome> geneList,
            List<Double> signalList, final Rectangle2D rectOfCircle,
            final double max_Signal, final double max_length) {
        Color temp = g2d.getColor();
        g2d.setColor(this.GENOME_SIGNAL_COLOR);
        for (int i = 0; i < signalList.size(); i++) {
            if (signalList.get(i) != null) {
                this.drawASignal(g2d, geneList.get(i), signalList.get(i),
                        this.getCenterRectangle2D(rectOfCircle, rectOfCircle.getWidth() / this.whiteCircleParameter, rectOfCircle.getWidth() / this.whiteCircleParameter), max_Signal, max_length);
            }
        }
        this.drawAWhiteCircle(g2d, this.getCenterRectangle2D(rectOfCircle,
                rectOfCircle.getWidth() / this.whiteCircleParameter / this.whiteCircleParameter, rectOfCircle.getWidth() / this.whiteCircleParameter / this.whiteCircleParameter));
        g2d.setColor(temp);
    }

    /**
     * To draw a signal value arc
     * @param g2d
     * @param gene
     * @param signal
     * @param rectOfCircle
     * @param max_Signal
     * @param MAX_LENGTH the genelist's max length
     */
    private void drawASignal(Graphics2D g2d, Genome gene, final double signal,
            final Rectangle2D rectOfCircle, final double max_Signal,
            final double MAX_LENGTH) {
        if (signal == 0.0) {
            return;
        }
        double startAngle = (double) (gene.getHead()) / MAX_LENGTH * 360;
        double passAngle = 0;
        passAngle = (double) ((gene.getTail() - gene.getHead())) / MAX_LENGTH * 360;
        double i = rectOfCircle.getWidth() - rectOfCircle.getWidth() / whiteCircleParameter;
//        Arc2D arc = new Arc2D.Double(this.getCenterRectangle2D(rectOfCircle,
//                rectOfCircle.getWidth() - i * ((max_Signal - signal) / max_Signal),
//                rectOfCircle.getWidth() - i * ((max_Signal - signal) / max_Signal)), this.START_DRAW_ANGLE - startAngle, -passAngle, Arc2D.PIE);
        Arc2D arc = new Arc2D.Double(this.getCenterRectangle2D(rectOfCircle,
                rectOfCircle.getWidth() / whiteCircleParameter + i * (signal / max_Signal),
                rectOfCircle.getWidth() / whiteCircleParameter + i * (signal / max_Signal)), this.START_DRAW_ANGLE - startAngle, -passAngle, Arc2D.PIE);
        g2d.fill(arc);
    }

    /**
     * get a Rectangle,whose center is the same as the parameter with width 
     * and height from parameter    
     * @param maxRectOfCircle
     * @param width
     * @param height
     * @return a new Rectangle2D the same center as param:maxRectOfCircle but 
     *          its width and height equal param:width and height 
     */
    private Rectangle2D getCenterRectangle2D(final Rectangle2D rectOfCircle,
            double width, double height) {
        double x = rectOfCircle.getCenterX() - width / 2;
        double y = rectOfCircle.getCenterY() - height / 2;
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * get a rectangle whose center is(centerX,centerY)
     * @param centerX
     * @param centerY
     * @param width
     * @param height
     * @return 
     */
    private Rectangle2D getCenterRectangle2D(double centerX, double centerY, double width, double height) {
        double x = centerX - width / 2;
        double y = centerY - height / 2;
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * to draw a circle by Rectangle2D maxRectOfCircle ,and its background color is
     * the same as the panel's
     * @param g
     * @param maxRectOfCircle 
     */
    private void drawAWhiteCircle(Graphics2D g2d, final Rectangle2D rectOfCircle) {
        Color temp = g2d.getColor();
        g2d.setColor(this.getBackground());
        Arc2D arc = new Arc2D.Double(rectOfCircle, 0, 360, Arc2D.PIE);
        g2d.fill(arc);
        g2d.setColor(temp);
    }

    /**
     * 
     * @param g the Graphics to draw on geneList
     * @param geneList the list of genes
     * @param placeOfGene the place of gene in geneList
     * @param rectOfCircle the geneList's rect to draw
     * @param MAX_LENGTH 
     */
    private Arc2D drawAGenome(Graphics2D g2d,
            Genome gene, final Rectangle2D rectOfCircle, final double MAX_LENGTH) {
        if (!gene.getProduct().isEmpty() && gene.getProduct().equals("hypothetical protein")) {
            return null;
        }
        if (g2d.getColor().equals(new Color(0, 176, 240))) {
            g2d.setColor(new Color(220, 50, 10));
        } else {
            g2d.setColor(new Color(0, 176, 240));
        }
        double startAngle = (double) (gene.getHead()) / MAX_LENGTH * 360;
        double passAngle = 0;
        passAngle = (double) ((gene.getTail() - gene.getHead())) / MAX_LENGTH * 360;
        Arc2D arc = new Arc2D.Double(rectOfCircle, this.START_DRAW_ANGLE - startAngle, -passAngle, Arc2D.PIE);
        g2d.fill(arc);
        return arc;
    }

    /**
     * set Graphics g's Anti aliasing
     * @param g 
     */
    private void setAnti_Aliasing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        HashMap<Object, Object> renderingHintsMap = new HashMap<Object, Object>();
        renderingHintsMap.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(renderingHintsMap);
    }

    /**
     * return the distance between (x1,y1) and (x2,y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return the distance between (x1,y1) and (x2,y2)
     */
    private double lengthBetweenTwoPoint(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * return if the point(x,y)is in the circle's innerRadius and radius
     * @param circle
     * @param x
     * @param y
     * @return a boolean means is the point is between innerRadius and radius
     */
    private boolean isPointInCircle(circleData circle, double x, double y) {
        double lengthBetweenTwoPoint = this.lengthBetweenTwoPoint(circle.getX(), circle.getY(), x, y);
        //if innerRadius<=lengthBetweenTwoPoint<=radius means the point is
        //in the circle
        if (lengthBetweenTwoPoint >= circle.getInnerRadius() && lengthBetweenTwoPoint <= circle.getRadius()) {
            return true;
        }
        return false;
    }

    /**
     * To return a gene that contains angle(from Y Axis Positive)
     * @param genes
     * @param angle 
     * @param MAX_LENGTH the genelist's ma length
     * @return 
     */
    private Genome getGenomeByAngleFromYAxis(List<Genome> genes, double angle, final double MAX_LENGTH) {
        return this.binarySearchGeneByPosition(genes,
                angle / 360 * MAX_LENGTH/*the positon that the angle represant*/);
    }

    /**
     * To get the gene including position
     * (only 50+ times when the genome list has 4339 genome)
     * @param genes gene list
     * @param position
     * @return 
     */
    private Genome binarySearchGeneByPosition(final List<Genome> genes, final double position) {
        int time = 0;
        int mid = genes.size() / 2;
        if (this.isPositionInGenome(genes.get(mid), position, genes.get(mid + 1))) {
            return genes.get(mid);
        }
        int start = 0;
        int end = genes.size() - 1;
        while (start < end) {
            time++;
            if (time >= 4) {
                break;
            }
            mid = (end - start) / 2 + start;
            if (position < genes.get(mid).getHead()) {
                end = mid - 1;
            } else if (position > genes.get(mid).getTail()) {
                start = mid + 1;
            } else {
                if (position < genes.get(mid + 1).getHead()) {
                    return genes.get(mid);
                } else {
                    mid++;
                }
            }
        }
        for (int i = start; i <= end; i++) {
            time++;
            if (genes.get(i).getHead() <= position && position <= genes.get(i).getTail()) {
                if (!genes.get(i).getProduct().isEmpty() && genes.get(i).getProduct().equals("hypothetical protein")) {
                    //return null;
                    return genes.get(i);
                }
                if (i + 1 <= end && position <= genes.get(i + 1).getHead()) {
                    return genes.get(i);
                }
                if (i + 1 == end) {
                    return genes.get(i);
                }
            }
        }

        return null;
    }

    private boolean isPositionInGenome(Genome gene, double position, Genome nextGene) {

        if (gene.getHead() <= position && position <= gene.getTail()) {
            if (nextGene != null && position < nextGene.getHead()) {
                return true;
            }
        }
        return false;
    }

    /**
     * vector from (x1,y1) to (x2,y2) To Y Axis's cos value
     * @param x1
     * @param y1 
     * @param x2
     * @param y2 
     * @return cosValue between vector(x2-x1,y2-y1)and positive Y axis
     */
    private static double cosValueBetweenALineAndPositiveX(double x1, double y1, double x2, double y2) {
        double cosValue = 0;
        double realx = x2 - x1;
        double realy = y2 - y1;
        cosValue = (realx * 0 + realy * (-1)) / (1 * Math.sqrt(realx * realx + realy * realy));
        return cosValue;
    }

    /**
     * Get angle from Line:(circle's center to(x,y)) To Y Axis's positive side
     * @param circle
     * @param x 
     * @param y
     * @return an angle from 0 to 360
     */
    private double getAngleFromLineToYAxis(circleData circle, double x, double y) {
        double angle = 0;
        angle = cosValueBetweenALineAndPositiveX(circle.getX(), circle.getY(), x, y);
        angle = Math.toDegrees(Math.acos(angle));
        if (circle.getX() > x) {
            return 360 - angle;
        }
        return angle;
    }

    /**
     * Get a genome's tooltip to show on the screen
     * @param gene the gene which needs tooltip
     * @param message extra message that need to add
     * @return a string in <html> mode
     */
    private String getGenomeTooltip(Genome gene, String message) {
        StringBuilder str = new StringBuilder("<html>");
        str.append(message);
        str.append("<br>");
        str.append("name: ");
        str.append(gene.getName());
        str.append("<br>");
        str.append("product: ");
        str.append(gene.getProduct());
        str.append("<br>");
        str.append("head: ");
        str.append(gene.getHead());
        str.append("<br>");
        str.append("tail: ");
        str.append(gene.getTail());
        str.append("</html>");
        return str.toString();
    }

    /**
     * Show a genome information JOptionPane 
     * @param evt to get where the mouse is now
     */
    private void showGenomeInformationForm(java.awt.event.MouseEvent evt) {
        JOptionPane.showMessageDialog(null,
                this.getGenomeTooltip(this.genomeMouseIsOn,
                evt.getX() + " " + evt.getY()), "Genome Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * A class storing the data of circle
     * which can not be change once been created
     */
    private class circleData {

        private List<Genome> genes = null;
        private double x = 0;
        private double y = 0;
        private double radius = 0;
        private double innerRadius = 0;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final circleData other = (circleData) obj;
            if (this.genes != other.genes && (this.genes == null || !this.genes.equals(other.genes))) {
                return false;
            }
            if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
                return false;
            }
            if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
                return false;
            }
            if (Double.doubleToLongBits(this.radius) != Double.doubleToLongBits(other.radius)) {
                return false;
            }
            if (Double.doubleToLongBits(this.innerRadius) != Double.doubleToLongBits(other.innerRadius)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + (this.genes != null ? this.genes.hashCode() : 0);
            hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
            hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
            hash = 97 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
            hash = 97 * hash + (int) (Double.doubleToLongBits(this.innerRadius) ^ (Double.doubleToLongBits(this.innerRadius) >>> 32));
            return hash;
        }

        public double getInnerRadius() {
            return innerRadius;
        }

        public void setInnerRadius(double innerRadius) {
            this.innerRadius = innerRadius;
        }

        public circleData(List<Genome> ge, double x, double y, double innerradius, double radius) {
            this.genes = ge;
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.innerRadius = innerradius;
        }

        public List<Genome> getGenes() {
            return genes;
        }

        public double getRadius() {
            return radius;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public class BufferedImageBuilder {

        private static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

        public BufferedImage bufferImage(Image image) {
            return bufferImage(image, DEFAULT_IMAGE_TYPE);
        }

        public BufferedImage bufferImage(Image image, int type) {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, null, null);
            waitForImage(bufferedImage);
            return bufferedImage;
        }

        private void waitForImage(BufferedImage bufferedImage) {
            final ImageLoadStatus imageLoadStatus = new ImageLoadStatus();
            bufferedImage.getHeight(new ImageObserver() {

                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    if (infoflags == ALLBITS) {
                        imageLoadStatus.heightDone = true;
                        return true;
                    }
                    return false;
                }
            });
            bufferedImage.getWidth(new ImageObserver() {

                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    if (infoflags == ALLBITS) {
                        imageLoadStatus.widthDone = true;
                        return true;
                    }
                    return false;
                }
            });
//            while (!imageLoadStatus.widthDone && !imageLoadStatus.heightDone) {
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                }
//            }
        }

        class ImageLoadStatus {

            public boolean widthDone = false;
            public boolean heightDone = false;
        }
    }
    private boolean isMouseOnGene = false;
    private Genome genomeMouseIsOn = null;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(java.awt.Color.white);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setDoubleBuffered(false);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                formAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
    }//GEN-LAST:event_formMouseEntered
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (this.circles.size() > this.genomesList.size()
                || (this.circles.isEmpty() && !this.genomesList.isEmpty())) {
            this.clearDrawed();

        }
        for (int i = 0; i < this.circles.size(); i++) {
            //judge whether the mouse point is in circle
            if (isPointInCircle(this.circles.get(i), evt.getX(), evt.getY())) {
                Genome gene = this.getGenomeByAngleFromYAxis(this.genomesList.get(i),
                        this.getAngleFromLineToYAxis(this.circles.get(i),
                        evt.getX(), evt.getY()),
                        this.MAX_LENGTHS.get(i));
                //Have found the genome
                if (gene != null) {
                    this.isMouseOnGene = true;
                    this.genomeMouseIsOn = gene;
                    //found and need genome tooltip 
                    if (this.tooltipEnable) {
                        //if signal is not empty
                        if (!this.genomeSignalList.isEmpty()) {
                            //if have get the signal of gene
                            if (this.genomeSignalList.get(i).get(this.genomesList.get(i).indexOf(gene)) != null) {
                                this.setToolTipText(
                                        this.getGenomeTooltip(gene, "Signal:" + this.genomeSignalList.get(i).get(this.genomesList.get(i).indexOf(gene)) + " "
                                        + "<br>" + evt.getX() + " " + evt.getY()));
                            } else {//not get the signal of gene
                                this.setToolTipText(
                                        this.getGenomeTooltip(gene, evt.getX() + " " + evt.getY()));
                            }
                        } else {
                            this.setToolTipText(
                                    this.getGenomeTooltip(gene, evt.getX() + " " + evt.getY()));
                        }
                    } else {
                        //found the gene but no gene tooltip
                        this.setToolTipText("Left Click To Show Genome Information");
                        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    return;
                } else//not found
                {
                    this.isMouseOnGene = false;
                    this.setToolTipText("");
                    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            } else {
                this.isMouseOnGene = false;
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_formMouseMoved
    /**
     * Zoom in and out
     * @param evt 
     */
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved

        int xRate = (int) (this.scrollpane.getHorizontalScrollBar().getValue());
        int yRate = (int) (this.scrollpane.getVerticalScrollBar().getValue());
        if (evt.getWheelRotation() == -1) {
            this.zoomInScale();
        } else {
            this.zoomOutScale();
        }
        moveScrollPaneTo(xRate, yRate);
    }//GEN-LAST:event_formMouseWheelMoved
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
    }//GEN-LAST:event_formMouseDragged
    /**
     * To remember the prePoint for the dragging function
     * @param evt 
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (evt.getButton() == MouseEvent.BUTTON1) {
            prePoint = evt.getPoint();
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
    }//GEN-LAST:event_formMousePressed
    /**
     * To realize dragging if the mouse moveTo to right,the jpanel moveTo to left
     * the same as up and down
     * @param evt 
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON1) {
            nowPoint = evt.getPoint();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            this.scrollpane.getVerticalScrollBar().setValue((int) (this.scrollpane.getVerticalScrollBar().getValue() - (nowPoint.getY() - prePoint.getY())));
            this.scrollpane.getHorizontalScrollBar().setValue((int) (this.scrollpane.getHorizontalScrollBar().getValue() - (nowPoint.getX() - prePoint.getX())));
        }
    }//GEN-LAST:event_formMouseReleased
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    }//GEN-LAST:event_formComponentShown
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (!this.tooltipEnable && this.isMouseOnGene && evt.getButton() == MouseEvent.BUTTON1) {
            this.showGenomeInformationForm(evt);
        }
    }//GEN-LAST:event_formMouseClicked

    private void formAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorMoved
    }//GEN-LAST:event_formAncestorMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
