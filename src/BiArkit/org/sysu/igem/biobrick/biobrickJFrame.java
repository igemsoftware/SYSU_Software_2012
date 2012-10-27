/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * biobrickJFrame.java
 *
 * Created on 2012-10-20, 23:04:25
 */
package org.sysu.igem.biobrick;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.jdom.Element;
import org.sysu.igem.newUI.MyTreeCellRenderer;
import org.sysu.igem.utils.FileNode;
import org.sysu.igem.utils.FindXmlIncludeString;
import org.sysu.igem.utils.ReadXml;
import org.sysu.igem.utils.setJTree;

/**
 *
 * @author Administrator
 */
public class biobrickJFrame extends javax.swing.JFrame {

    /** Creates new form biobrickJFrame */
    public biobrickJFrame() {
        initComponents();
        this.initJtree();
    }
    JPanel biobrickPanel = null;
    /**
     * Initialize jtree
     */
    private void initJtree() {
        this.biobrickPanel = new JPanel();
        this.biobrickPanel.setLayout(null);
        this.biobrickPanel.setSize(this.jScrollPane5.getWidth(), this.jScrollPane5.getHeight());
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
                File f = new File(select);
                if (f.exists() && !f.isDirectory()) {
                    biobrickJFrame.this.biobrickPanel.removeAll();
                    initbiobrick(select);
                }
            }
        });
    }
    /**
     * return true means this biobrick has subparts,false means no
     * @param path
     * @return 
     */
    private boolean drawAllSubpartsOnScrollpane(JPanel panel, String path) {
        boolean flag = false;
        List<Subpart> subparts = new Subparts(path).getSubpart_list();
        if (!subparts.isEmpty()) {
            flag = true;
            int left = 0;
            for (int i = 0; i < subparts.size(); i++) {
                JButton l = this.drawASubpart(panel, left, subparts.get(i));
                left += l.getWidth();
            }
            panel.setPreferredSize(new Dimension(left, jScrollPane5.getHeight() - 1));
            JButton button=new JButton();
            button.setBackground(Color.BLACK);
            button.setForeground(Color.BLACK);

            button.setLocation(0,jScrollPane5.getHeight()/2+3);
            button.setSize(left, 7);
            button.setVisible(true);
            panel.add(button);
            //panel.getGraphics().drawLine(0, panel.getHeight()/2,panel.getWidth() , panel.getHeight()/2);
            jScrollPane5.getViewport().setView(panel);
            jScrollPane5.validate();
        }
        return flag;
    }

    private JButton drawABiobrick(JPanel panel, String name) {
        panel.setSize(jScrollPane5.getWidth(), jScrollPane5.getHeight());
        panel.setPreferredSize(new Dimension(jScrollPane5.getWidth()-5, jScrollPane5.getHeight()));
        JButton button = new JButton(name);
        button.setFont(new java.awt.Font("Arial Black", 0, 18));
        button.setLocation(panel.getWidth() / 2 - button.getWidth() / 2 - 45, 0);
        button.setBorder(null);
        button.setBackground(Color.white);
        button.setSize(name.length() * 15, panel.getHeight() - 3);
        button.setVisible(true);
        panel.add(button);
        jScrollPane5.getViewport().setView(panel);
        jScrollPane5.validate();
        return button;
    }

    private JButton drawASubpart(JPanel panel, int left, Subpart part) {
        JButton wbn1 = new JButton();
        wbn1.setVerticalTextPosition(SwingConstants.TOP);
        wbn1.setBorder(null);
        wbn1.setContentAreaFilled(false);
        wbn1.setHorizontalTextPosition(SwingConstants.CENTER);
        wbn1.setLocation(left, 0);
        wbn1.setIcon(new ImageIcon("src/resource/biobrick/"+part.getType()+".png"));
        System.out.println("src/resource/biobrick/"+part.getType()+".png");
        wbn1.setText(part.getPart_name());
        wbn1.setSize(part.getPart_name().length() * 15, panel.getHeight());
        wbn1.setVisible(true);
        panel.add(wbn1);
        return wbn1;
    }
    /**
     *to init the biobrick part
     * @param path
     */
    public void initbiobrick(String path) {
        biobrickPanel.setBackground(Color.white);
        if (path.isEmpty()) {
            return;
        }
        ReadXml read = new ReadXml();
        //System.out.println(path);
        List<Element> l = read.readXMLContent(path).get(0).getChildren();
        if (!this.drawAllSubpartsOnScrollpane(biobrickPanel, path)) {
            System.out.println(l.get(0).getChildText("part_name"));
            this.drawABiobrick(biobrickPanel, l.get(0).getChildText("part_name"));
        }
        for (Element e : l) {
            jLabel24.setText(e.getChildText("part_id"));            
            jLabel25.setText(e.getChildText("part_name"));
            jLabel26.setText(e.getChildText("part_short_name"));
            jLabel27.setText(e.getChildText("part_short_desc"));
            jLabel27.setBackground(new java.awt.Color(176, 196, 255));
            jLabel28.setText(e.getChildText("part_type"));
            jLabel29.setText(e.getChildText("part_status"));
            jLabel29.setBackground(new java.awt.Color(176, 196, 255));
            jLabel30.setText(e.getChildText("part_results"));
            jLabel31.setText(e.getChildText("part_nickname"));
            jLabel32.setText(e.getChildText("part_rating"));
            jLabel33.setText(e.getChildText("part_entered"));
            jLabel34.setText(e.getChildText("part_author"));
            jLabel34.setBackground(new java.awt.Color(176, 196, 255));
            this.jScrollPane6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(105, 105, 105), 2, true));
            jLabel35.setText(e.getChildText("best_quality"));
            jLabel35.setBackground(new java.awt.Color(176, 196, 255));
            String twins = "";
            List<Element> list = e.getChildren("twins");
            if (!list.isEmpty()) {
                List<Element> list2 = list.get(0).getChildren("twin");
                if (!list2.isEmpty()) {
                    for (Element ee : list2) {
                        twins += ee.getValue();
                        twins += " ";
                    }
                    jLabel36.setText(twins);
                    jLabel36.setBackground(new java.awt.Color(176, 196, 255));
                }
            }
        }
        jScrollPane5.validate();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
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
        jScrollPane12 = new javax.swing.JScrollPane();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel24 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        findBiobrickTextField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel7ComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel7ComponentShown(evt);
            }
        });

        jPanel9.setBackground(java.awt.Color.white);
        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(java.awt.Color.white);
        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel11.setName("jPanel11"); // NOI18N

        jLabel7.setBackground(java.awt.Color.white);
        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel7.setText("part_id");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setOpaque(true);

        jLabel9.setBackground(java.awt.Color.white);
        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel9.setText("part_name");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setOpaque(true);

        jLabel11.setBackground(java.awt.Color.white);
        jLabel11.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel11.setText("part_short_name");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setOpaque(true);

        jLabel14.setBackground(java.awt.Color.white);
        jLabel14.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel14.setText("part_short_desc");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setOpaque(true);

        jLabel15.setBackground(java.awt.Color.white);
        jLabel15.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel15.setText("part_type");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setOpaque(true);

        jLabel16.setBackground(java.awt.Color.white);
        jLabel16.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel16.setText("part_status");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setOpaque(true);

        jLabel17.setBackground(java.awt.Color.white);
        jLabel17.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel17.setText("part_results");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setOpaque(true);

        jLabel18.setBackground(java.awt.Color.white);
        jLabel18.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel18.setText("part_nickname");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setOpaque(true);

        jLabel19.setBackground(java.awt.Color.white);
        jLabel19.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel19.setText("part_rating");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setOpaque(true);

        jLabel20.setBackground(java.awt.Color.white);
        jLabel20.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel20.setText("part_entered");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setOpaque(true);

        jLabel21.setBackground(java.awt.Color.white);
        jLabel21.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel21.setText("part_author");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setOpaque(true);

        jLabel22.setBackground(java.awt.Color.white);
        jLabel22.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel22.setText("best_quality");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setOpaque(true);

        jLabel23.setBackground(java.awt.Color.white);
        jLabel23.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel23.setText("twins");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setOpaque(true);

        jLabel24.setBackground(new java.awt.Color(176, 196, 255));
        jLabel24.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel24.setText("jLabel24");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(176, 196, 255));
        jLabel25.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel25.setText("jLabel24");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setOpaque(true);

        jLabel26.setBackground(new java.awt.Color(176, 196, 255));
        jLabel26.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel26.setText("jLabel24");
        jLabel26.setName("jLabel26"); // NOI18N
        jLabel26.setOpaque(true);

        jLabel28.setBackground(new java.awt.Color(176, 196, 255));
        jLabel28.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel28.setText("jLabel24");
        jLabel28.setName("jLabel28"); // NOI18N
        jLabel28.setOpaque(true);

        jLabel30.setBackground(new java.awt.Color(176, 196, 255));
        jLabel30.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel30.setText("jLabel24");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setOpaque(true);

        jLabel31.setBackground(new java.awt.Color(176, 196, 255));
        jLabel31.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel31.setText("jLabel24");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setOpaque(true);

        jLabel32.setBackground(new java.awt.Color(176, 196, 255));
        jLabel32.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel32.setText("jLabel24");
        jLabel32.setName("jLabel32"); // NOI18N
        jLabel32.setOpaque(true);

        jLabel33.setBackground(new java.awt.Color(176, 196, 255));
        jLabel33.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel33.setText("jLabel24");
        jLabel33.setName("jLabel33"); // NOI18N
        jLabel33.setOpaque(true);

        jLabel35.setBackground(java.awt.Color.white);
        jLabel35.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel35.setText("jLabel24");
        jLabel35.setName("jLabel35"); // NOI18N
        jLabel35.setOpaque(true);

        jLabel36.setBackground(java.awt.Color.white);
        jLabel36.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel36.setText("jLabel24");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setOpaque(true);

        jLabel29.setBackground(java.awt.Color.white);
        jLabel29.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel29.setText("jLabel29");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setOpaque(true);

        jScrollPane6.setBackground(java.awt.Color.white);
        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jLabel34.setBackground(java.awt.Color.white);
        jLabel34.setText("jLabel24");
        jLabel34.setDoubleBuffered(true);
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setOpaque(true);
        jScrollPane6.setViewportView(jLabel34);

        jScrollPane12.setBorder(null);
        jScrollPane12.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane12.setName("jScrollPane12"); // NOI18N

        jLabel27.setBackground(java.awt.Color.white);
        jLabel27.setFont(new java.awt.Font("Arial Black", 0, 16));
        jLabel27.setText("jLabel24");
        jLabel27.setName("jLabel27"); // NOI18N
        jLabel27.setOpaque(true);
        jScrollPane12.setViewportView(jLabel27);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel22)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel23))
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel26))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel33))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap())
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 660, -1));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 215, 0), 2, true));
        jScrollPane5.setForeground(java.awt.Color.white);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setName("jScrollPane5"); // NOI18N
        jScrollPane5.getViewport().setBackground(new java.awt.Color(255, 255, 0));
        jPanel9.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 90));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE))
        );

        jPanel23.setName("jPanel23"); // NOI18N
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        jTree1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jTree1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18));
        jTree1.setName("jTree1"); // NOI18N
        jScrollPane10.setViewportView(jTree1);

        jPanel23.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 96, 270, 580));

        jPanel24.setBackground(new java.awt.Color(255, 192, 16));
        jPanel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel24.setName("jPanel24"); // NOI18N

        jLabel48.setFont(new java.awt.Font("Arial Black", 0, 15));
        jLabel48.setText("FIND BIOBRICK");
        jLabel48.setName("jLabel48"); // NOI18N

        findBiobrickTextField.setName("findBiobrickTextField"); // NOI18N

        jButton3.setText("GO!");
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(findBiobrickTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addComponent(jLabel48))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findBiobrickTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel23.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel7ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel7ComponentHidden
        this.jTree1.setVisible(false);
        this.jScrollPane10.setVisible(false);
        this.jPanel23.setVisible(false);
	}//GEN-LAST:event_jPanel7ComponentHidden

    private void jPanel7ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel7ComponentShown
        this.jPanel23.setVisible(true);
        this.jScrollPane10.setVisible(true);
        this.jTree1.setVisible(true);
        this.validate();
	}//GEN-LAST:event_jPanel7ComponentShown

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            new FindXmlIncludeString(this).createBiobrickXmlSelectForm(this.findBiobrickTextField.getText());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(biobrickJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(biobrickJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
	}//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(biobrickJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new biobrickJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField findBiobrickTextField;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
