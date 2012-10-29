/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;
import org.KeggNetwork.KGMLReader;
import org.igem.browser.Browser;

/**
 * To find any xml that has a specific string
 * @author Guo Jiexin
 */
public class FindXmlIncludeString {

//    public FindXmlIncludeString(KGMLReader kgmlReader) {
////        this.kgmlReader = kgmlReader;
////        this.jPanel13 = jPanel13;
//    }
    public FindXmlIncludeString(Browser browser) {   
        this.browser=browser;
    }
    public FindXmlIncludeString(JFrame frame) {   
        this.browser=browser;
    }
    Browser browser=null;
//    KGMLReader kgmlReader = null;
//    JPanel jPanel13 = null;

    private boolean isStringInFile(final String str, File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = br.readLine();
        while (temp != null) {
            if (temp.toLowerCase().indexOf(str.toLowerCase()) >= 0) {
                return true;
            }
            temp = br.readLine();
        }
        return false;
    }
    List<File> xmlHasString = new ArrayList<File>();

    private List<File> findAllXmlHasString(String str, String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                this.findAllXmlHasString(str, files[i].getPath());
            }
            if (files[i].isFile()) {
                if (this.isStringInFile(str, files[i])) {
                    xmlHasString.add(files[i]);
                }
            }
        }
        return xmlHasString;
    }
    /**
     * Create a file select form for biobrick
     * @param str
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void createBiobrickXmlSelectForm(String str) throws FileNotFoundException, IOException {
        final JFrame frame = new JFrame("Show Biobrick search result");
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        this.xmlHasString.clear();
        final List<File> xmls = this.findAllXmlHasString(str, "biobrick");
        final JList xmlJList = new JList();
        DefaultListModel model = new DefaultListModel();
        if (xmls.size() == 0) {
            model.addElement("no result!");
        } else {
            for (int i = 0; i < xmls.size(); i++) {
                model.addElement(xmls.get(i).getName());
            }
        }
        
        xmlJList.setModel(model);
        xmlJList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (!xmlJList.getSelectedValue().equals("no result!")) {
//                        System.out.println("1"+xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath());
//                        String[] temp=xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath().split("\\\\");
//                        int place=0;
//                        for(int i=0;i<temp.length;i++)
//                        {
//                            System.out.println(temp[i]);
//                            if(temp[i].equals("biobrick"))
//                               place=i;
//                        }
//                        Object[] temp2=new Object[temp.length-place];
//                        System.out.println(temp.length-place);
//                        int size=0;
//                        for(int i=place;i<temp.length;i++)
//                            temp2[size++]=temp[i];
//                        TreePath path=new TreePath(temp2);
//                    FindXmlIncludeString.this.browser.jTree1.setSelectionPath(path);
                    FindXmlIncludeString.this.browser.initbiobrick(xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath());
                }
                frame.dispose();
            }
        });
        JScrollPane scrollpane = new JScrollPane(xmlJList);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
    }
    
    public void createKeggXmlSelectForm(String str) throws FileNotFoundException, IOException {
        final JFrame frame = new JFrame("Show kegg search result");
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        this.xmlHasString.clear();
        final List<File> xmls = this.findAllXmlHasString(str, "xml\\kegg");
        final JList xmlJList = new JList();
        DefaultListModel model = new DefaultListModel();
        if (xmls.isEmpty()) {
            model.addElement("no result!");
        } else {
            for (int i = 0; i < xmls.size(); i++) {
                model.addElement(xmls.get(i).getName());
            }
        }        
        xmlJList.setModel(model);
        xmlJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!xmlJList.getSelectedValue().equals("no result!")) {
//                    FindXmlIncludeString.this.browser.kgmlReader.ClearAll();
//                    FindXmlIncludeString.this.browser.kgmlReader.ReadXML(xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath());
//                    FindXmlIncludeString.this.browser.kgmlReader=new org.KeggNetwork.KGMLReader(xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath(), FindXmlIncludeString.this.browser.jPanel13);
//                    FindXmlIncludeString.this.browser.kgmlReader = new org.KeggNetwork.KGMLReader(xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath(), FindXmlIncludeString.this.browser.jPanel13);
//                    FindXmlIncludeString.this.browser.kgmlReader.ClearAll();
                    FindXmlIncludeString.this.browser.showANewKgml(xmls.get(xmlJList.getSelectedIndex()).getAbsolutePath());
                }
                frame.dispose();
            }
        });
        JScrollPane scrollpane = new JScrollPane(xmlJList);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
    }

    public static void main(String args[]) {
    }
}
