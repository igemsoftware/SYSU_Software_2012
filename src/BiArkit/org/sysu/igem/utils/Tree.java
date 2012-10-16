/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Jonny
 */
public class Tree {

    public static void main(String[] args) {
        TreeFrame frame = new TreeFrame("Tree");
        frame.setSize(1024, 768);
        frame.setVisible(true);
    }
}

class TreeFrame extends JFrame {

    public TreeFrame(String title) {
        super(title);

        JScrollPane jsp = new JScrollPane();
        TreePanel panel = new TreePanel();
        jsp.getViewport().add(panel);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jsp, BorderLayout.WEST);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                setVisible(false);
                dispose();
                System.exit(0);
            }
        });
    }
}

class TreePanel extends JPanel {

    private MyTreeNode root = null;
    private JTree jtree = null;

    private void addAllFile(File file, MyTreeNode node) {
        if (file == null && node == null) {
            file = new File("C:");     //Display the C: directory tree
            node = new MyTreeNode(file);
            root = node;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            } else {
                for (int i = 0; i < files.length; ++i) {
                    MyTreeNode newnode = new MyTreeNode(files[i]);
                    node.add(newnode);
                    //addAllFile(files[i],newnode);  

                }
            }
        } else {
            return;
        }
    }

    public TreePanel() {
        addAllFile(null, null);
        jtree = new JTree(root);
        jtree.addTreeWillExpandListener(new TreeWillExpandListener() {

            public void treeWillExpand(TreeExpansionEvent event) {
                MyTreeNode node = (MyTreeNode) event.getPath().getLastPathComponent();
                File nodefile = (File) node.getUserObject();
                File[] files = nodefile.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; ++i) {
                        MyTreeNode addnode = new MyTreeNode(files[i]);
                        node.add(addnode);
                    }
                }
                jtree.repaint();
            }

            public void treeWillCollapse(TreeExpansionEvent event) {
                jtree.repaint();
            }
        });
        jtree.addTreeExpansionListener(new TreeExpansionListener() {

            public void treeExpanded(TreeExpansionEvent event) {
                jtree.repaint();
            }

            public void treeCollapsed(TreeExpansionEvent event) {
                jtree.repaint();
            }
        });
        add(jtree);
    }
}

class MyTreeNode extends DefaultMutableTreeNode {

    public MyTreeNode() {
        super();
    }

    public MyTreeNode(Object userObject) {
        super(userObject);
    }

    public MyTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public String toString() {
        return ((File) this.userObject).getName();
    }

    public boolean isLeaf() {
        return !(((File) this.userObject).isDirectory());
    }
}