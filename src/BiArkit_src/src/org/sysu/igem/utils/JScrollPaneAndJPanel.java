/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

public class JScrollPaneAndJPanel extends JFrame {

    public JScrollPaneAndJPanel() {
        super("TestJScrollPane");
        this.setLayout(null);
        this.setBounds(200, 200, 300, 300);
        JPanel panel = new JPanel();
        //This code, set the panel preferred size, while ensuring wide and tall 
        //in a JScrollPane the wide high, such JScrollPane scroll bar appear below
        panel.setPreferredSize(new Dimension(200, 100));
        
        JButton button1 = new JButton("1");
        panel.add(button1);
        JButton button2 = new JButton("2");
        panel.add(button2);
        JButton button3 = new JButton("3");
        panel.add(button3);
        JButton button4 = new JButton("4");
        panel.add(button4);
        JButton button5 = new JButton("5");
        panel.add(button5);
        JButton button6 = new JButton("6");
        panel.add(button6);
        JButton button7 = new JButton("7");
        panel.add(button7);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10, 10, 175, 70);
        this.getContentPane().add(scrollPane);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new JScrollPaneAndJPanel();
    }
}
