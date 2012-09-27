/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.igem.browser;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 * The UI setting of jtree
 * @author Guo Jiexin
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        //String     stringValue   =   tree.convertValueToText(value,sel, expanded,   leaf,   row,   hasFocus); 
        if (!leaf) {
            this.setIconByValue(value);
        }
        return this;
    }
    public void setIconByValue(Object value)
    {
        ImageIcon ii;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        TreeNode[] path = node.getPath();
        if(path.length>=2)
        {
            String secondDir = path[1].toString();
            if (secondDir.toLowerCase().equals("DNA".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/DNA.png");
            }
            else if (secondDir.toLowerCase().equals("Composite parts".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/composite-part.png");
            }
            else if (secondDir.toLowerCase().equals("Plasmid backbones".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/primer-backbone.png");
            }
            else if (secondDir.toLowerCase().equals("Plasmids".toLowerCase())) {
                //
                ii = new ImageIcon("src/resource/biobrick button/primer-backbone.png");
                //
            }
            else if (secondDir.toLowerCase().equals("Primers".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/primers.png");
            }
            else if (secondDir.toLowerCase().equals("Promoters".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/promoter.png");
            }
            else if (secondDir.toLowerCase().equals("Protein coding sequences".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/protein-coding-sequence.png");
            }
            else if (secondDir.toLowerCase().equals("Protein domains".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/protein-domain.png");
            }
            else if (secondDir.toLowerCase().equals("Ribosome Binding Sites".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/ribosome-binding-site.png");
            }else if (secondDir.toLowerCase().equals("Terminators".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/terminator.png");
            }else if (secondDir.toLowerCase().equals("Translational units".toLowerCase())) {
                ii = new ImageIcon("src/resource/biobrick button/translational-units.png");
            }else{
                ii = new ImageIcon("src/resource/biobrick button/DNA.png");
            }
            this.setIcon(ii);            
        }
        //this.setIcon(ii);
    }
}
