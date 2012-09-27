package org.sysu.igem.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class javaTree extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 6999685634761062308L;

    /**
     * The usefulness of this internal class that in the JTree node select occurred on the selected file format conversion
     */
    private class MyFile {

        private File file;

        public MyFile(File file) {
            this.file = file;
        }

        public String toString() {
            String name = file.getName().trim();
            if (name.length() == 0) {
                name = file.getAbsolutePath().trim();
            }
            return name;
        }
    }
    DefaultMutableTreeNode treeRoot;
    DefaultTableModel tableModel;
    JTable table;
    DefaultMutableTreeNode parent;
    Object[][] list = {{}};

    /**
     * Adding machine code && constructor interface
     * @throws UnknownHostException 
     */
    public void treeMake() throws UnknownHostException {
        InetAddress local = InetAddress.getLocalHost();
        treeRoot = new DefaultMutableTreeNode(local.getHostName());
        final JTree tree = new JTree(treeRoot);
        JScrollPane scrolltree = new JScrollPane(tree);
        scrolltree.setPreferredSize(new Dimension(200, 300));
        String[] row = {"File", "Type", "Last Modified Date"};
        tableModel = new DefaultTableModel(list, row);
        table = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(500, 500));
        JFrame jf = new JFrame();
        jf.add(BorderLayout.WEST, scrolltree);
        jf.add(BorderLayout.CENTER, scrollTable);
        jf.setSize(600, 600);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add computer local harddisk
        //for (char i = 'c'; i < 'z'; i++) {
        //String path = i + ":/";
        String path = "biobreak";
        if (((new File(path)).exists())) {
            DefaultMutableTreeNode diskNode = new DefaultMutableTreeNode(
                    new MyFile(new File(path)));
            treeRoot.add(diskNode);
            readfiles(new File(path), diskNode);
        }
        //}
        tree.addTreeSelectionListener(new TreeSelectionListener() {//添加listener

            public void valueChanged(TreeSelectionEvent arg0) {
                TreePath path = tree.getSelectionPath();
                if (path == null) {
                    return;
                }
                DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (!selectnode.isLeaf()) {
                    // Here plus the type of judgment
                    if (!(selectnode.getUserObject() instanceof MyFile)) {
                        return;
                    }
                    File file_select = ((MyFile) selectnode.getUserObject()).file;
                    // Read folder file to add the lower nodes
                    readfiles(file_select, selectnode);
                    DefaultMutableTreeNode firstchild = (DefaultMutableTreeNode) selectnode.getFirstChild();
                    selectnode.remove(firstchild);
                    tableModel.setRowCount(0);
                    File[] fileList = file_select.listFiles();
                    list = fu(fileList);
                    for (int i = 0; i < fileList.length; i++) {
                        tableModel.addRow(list[i]);
                    }
                } else {
                    tableModel.setRowCount(0);
                    try {
                        File file_select = ((MyFile) selectnode.getUserObject()).file;
                        String sizeFile = size(file_select);
                        Object re[][] = {{file_select.getName(), sizeFile, lastTime(file_select)}};
                        list = re;
                        tableModel.addRow(list[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Read selected node for child nodes
     * @param file
     * @param node 
     */
    public void readfiles(File file, DefaultMutableTreeNode node) {
        File list[] = file.listFiles();
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.length; i++) {
            File file_inlist = list[i];
            // String filename = file_inlist.getName();
            if (file_inlist.isDirectory()) {
                parent = new DefaultMutableTreeNode(new MyFile(file_inlist));
                // Add a blank file folder node child nodes appear as folders
                File stubadd = null;
                DefaultMutableTreeNode stub = new DefaultMutableTreeNode(
                        stubadd);
                parent.add(stub);
                node.add(parent);
            } else {
                DefaultMutableTreeNode son = new DefaultMutableTreeNode(
                        new MyFile(file_inlist));
                node.add(son);
            }
        }
    }

    public String size(File file) throws IOException {
        FileInputStream fileLength = new FileInputStream(file);
        String sizefile = fileLength.available() + "byte";
        return sizefile;
    }

    public Date lastTime(File file) {
        long lastModified = file.lastModified();
        Date date = new Date(lastModified);
        date.setTime(lastModified);
        return date;
    }

    public Object[][] fu(File[] file) {

        Object[][] m = new Object[file.length][3];
        for (int i = 0; i < file.length; i++) {
            m[i][0] = file[i].getName();
            try {
                if (file[i].isDirectory()) {
                    m[i][1] = "folder";
                } else {
                    m[i][1] = size(file[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            m[i][2] = lastTime(file[i]);
        }
        return m;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount(File[] file) {
        return file.length;
    }

    public Object getValueAt(int row, int col) {
        return list[row][col];
    }

    public static void main(String[] args) {
        javaTree disk = new javaTree();
        try {
            disk.treeMake();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}