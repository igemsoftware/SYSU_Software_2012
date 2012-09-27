/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.io.File;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Jonny
 */
public class setJTree{
    JTree tree;
    File file;// = new File("./"); 
    //final JTree tree = new JTree(createTreeModel());

    public setJTree(JTree tree, File file) {
        this.tree = tree;
        this.file = file;
        //System.out.println(file.listFiles());
        
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode diskNode = createTreeModel();
        model.setRoot(diskNode);
    }
    
    private DefaultMutableTreeNode createTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(file);
        File[] rootPath = file.listFiles();//File.listRoots();
        for (int i = 1; i < rootPath.length; i++) {
            FileNode Node = new FileNode(rootPath[i]);
            Node.explore();
            rootNode.add(Node);
        }
        return rootNode;
    }
}
