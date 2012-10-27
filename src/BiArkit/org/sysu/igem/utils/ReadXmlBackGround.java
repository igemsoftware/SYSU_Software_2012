/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.awt.Color;
import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import org.igem.browser.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *To read xml in background using SwingWorker
 * @author Guo Jiexin
 */
public class ReadXmlBackGround extends SwingWorker<List<Element>, Void> {

    public class buttonActlistener implements ActionListener {

        Browser brow;

        public buttonActlistener(long i, Browser brow) {
            this.head = i;
            this.brow = brow;
        }
        public long head = 0;

        public void actionPerformed(ActionEvent e) {
            brow.position = (int) head;
            brow.IsGeneButtonClick = true;
            brow.jslider.setValue(brow.position);
            brow.jslider.setValue(50);
            brow.IsGeneButtonClick = false;
            System.out.print(head);
        }
    }
    // final Color[] colors = {new java.awt.Color(255, 127, 36),new java.awt.Color(0,0, 0), new java.awt.Color(255,165, 0), new java.awt.Color(255, 127, 36)};
    final Color[] colors = {new java.awt.Color(255, 127, 36), new java.awt.Color(255, 165, 0)};
    public List<Element> list = new ArrayList<Element>();
    public JScrollPane jScrollPane4;
    public String xmlFilePath;
    public List<Genome> xmlData;
    JPanel panel = new JPanel();
    public int left = 0;//Began to draw from the left a few pixels
    public Browser brow = null;

    public ReadXmlBackGround(String xmlFilePath,
            JScrollPane jScrollPane4, List<Genome> xmlData, Browser bro) {
        this.xmlFilePath = xmlFilePath;
        this.jScrollPane4 = jScrollPane4;
        this.xmlData = xmlData;
        this.brow = bro;
    }

    public List<Element> readXMLContent(String fileName) {
        /* Fromat: xml/genome.xml */
        String file_name = fileName;
        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(new File(file_name));
            Element rootEl = doc.getRootElement();
            this.list = rootEl.getChildren();
            System.out.println(list.size());
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setUpXmlData() {
        if (xmlFilePath == null) {
            return;
        }
        Genome genome;
        panel.setLayout(null);
        int colorSize = colors.length;
        /* For Draw Buttons */
        int top = 100;//100 pixels from the start painting from the panel above 

        int tempright = 0;
        int separatWidth = 0;//The spacing between the left and right two buttons
        int i = -1;//In order to draw out the line just less than rectangle one
        List<Integer> heads = new ArrayList<Integer>();
        List<Integer> tails = new ArrayList<Integer>();
        JButton button1 = null;
        int buttonWidth = 0;
        // System.out.println("l  "+l.size());
        for (Element el : list) {
            genome = new Genome();
            genome.setFeature(el.getChildText("feature"));
            genome.setName(el.getChildText("name"));
            genome.setHead(Integer.parseInt(el.getChildText("head")));
            genome.setTail(Integer.parseInt(el.getChildText("tail")));
            genome.setTag(el.getChildText("tag"));
            genome.setProduct(el.getChildText("product"));
            genome.setGeneLink(el.getChildText("geneLink"));
            i++;
            final int head = Integer.parseInt(el.getChildText("head"));
            final int tail = Integer.parseInt(el.getChildText("tail"));
            buttonWidth = (tail - head) / 5;
            tails.add(tail);
            heads.add(head);
            if (i >= 1) {
                separatWidth = Math.abs((int) (heads.get(i) - tails.get(i))) / 5;
            }
            if (i == 0) {
                separatWidth = Math.abs((int) (heads.get(0) - 0)) / 5;
                this.AddLine(panel, tempright, separatWidth, top + 50 / 2);
                left = left + separatWidth;
            }
            String buttonText = "<html><font style=\"color:white;\">" + el.getChildText("name") + "|" + el.getChildText("product") + "</font></html>";
            button1 = new JButton(buttonText);
            button1.addActionListener(new buttonActlistener(head, brow));
            button1.setBackground(colors[(i + 1) % colorSize]);
            button1.setForeground(colors[i % colorSize]);
            button1.setBounds(left, top, buttonWidth, 50);
            button1.setVisible(true);
            button1.setToolTipText(genome.getName());
            panel.add(button1);
            tempright = left + buttonWidth;
            if (i < list.size() - 1) {
                this.AddLine(panel, tempright, separatWidth, top + 50 / 2);
            }
            left = left + buttonWidth + separatWidth;
            xmlData.add(genome);
        }
    }

    public void AddLine(JScrollPane panel, int left, int width, int top) {
        final int height = 10;//The height of the line to be added
        JButton button = new JButton();
        button.setBounds(left, top, width, 10);
        button.setBackground(Color.black);

        panel.setSize(876, height);
        panel.getViewport().setView(button);
    }

    public void AddLine(JPanel panel, int left, int width, int top) {
        final int height = 10;//The height of the line to be added
        JButton button = new JButton();
        button.setBounds(left, top, width, 10);
        button.setBackground(Color.black);
        panel.add(button);
    }

    @Override
    protected List<Element> doInBackground() throws Exception {
        list = this.readXMLContent(this.xmlFilePath);
        this.setUpXmlData();
        return list;
    }

    @Override
    protected void done() {
        try {
            //this.setUpXmlData();
            this.panel.setPreferredSize(new Dimension(left, 50));
            jScrollPane4.getViewport().setView(panel);
            jScrollPane4.validate();
        } catch (Exception ignore) {
        }
    }
}
