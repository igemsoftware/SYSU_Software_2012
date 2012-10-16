/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

/**
 *
 * @author Jonny
 */
import java.awt.BorderLayout;
import java.io.File;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;


public class JTreeRoot extends JFrame {
    private DefaultMutableTreeNode node = null;
    public JTreeRoot() {
        JScrollPane scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        File file = new File("C:\\Users\\Jonny\\Documents\\NetBeansProjects\\GenomeBrowser");
        node = new DefaultMutableTreeNode(file);
        JTree tree = new JTree(node);
        scrollPane.setViewportView(tree);
        loadingTree(node);
        setSize(650, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void loadingTree(DefaultMutableTreeNode root) {
        File[] roots = File.listRoots();
        DefaultMutableTreeNode node = null;
        for (int i = 0; i < roots.length; i++) {
            node = new DefaultMutableTreeNode(roots[i].getPath());
            root.add(node);
            loadingTree(roots[i], node);
        }
    }


    private void loadingTree(File root, DefaultMutableTreeNode node) {
        File[] files = root.listFiles();
        DefaultMutableTreeNode subNode = null;
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getPath());
            subNode = new DefaultMutableTreeNode(files[i].getName());
            node.add(subNode);
            if (files[i].isDirectory()) {
                loadingTree(files[i], subNode);
            }
        }


    }


    public static void main(String[] args) {
        new JTreeRoot();
    }


}