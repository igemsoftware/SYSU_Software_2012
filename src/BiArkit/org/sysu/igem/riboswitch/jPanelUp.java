package org.sysu.igem.riboswitch;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
/**
 * The class that can show up regulation or down regulation
 * @author Guo Jiexin
 */
public class jPanelUp extends JFrame {

    private List<JLabel> jLabelListUp = new ArrayList<JLabel>();
    private List<JLabel> jLabelListDown = new ArrayList<JLabel>();
    private String upString = "";
    private String downString = "";
    private static Rectangle rect = null;

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());//"com.sun.java.swing.plaf.Windows.WindowsLookAndFeel"); 
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                try {
//                    jPanelUp frame = new jPanelUp();
//                    frame.setVisible(true);
//                    frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//                    frame.setBak((JPanel) frame.getContentPane(), "upRegulationChanged.jpg");
//                    frame.SetAGCTRightTop(frame.upString.substring(12), (JPanel) frame.getContentPane(), rect);
//                    frame.SetAGCTLeftTop(frame.upString.substring(0, 6), (JPanel) frame.getContentPane(), rect);
//                    frame.SetAGCTLeftBottom(frame.upString.substring(6, 12), (JPanel) frame.getContentPane(), rect);
//                    frame.SetAGCTRowRightTop(frame.upString.substring(0, 6), (JPanel) frame.getContentPane(), rect);
//                    frame.SetAGCTRowRightBottom(frame.upString.substring(6, 12), (JPanel) frame.getContentPane(), rect);
//                    frame.SetAGCTRightRight(frame.upString.substring(12), (JPanel) frame.getContentPane(), rect);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//    }
    /**
     * Show a up regulation jpg frame
     * @param a 
     */
    public static void ShowUpJPG(Riboswitch a) {
        try {
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());//"com.sun.java.swing.plaf.Windows.WindowsLookAndFeel"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jPanelUp frame = new jPanelUp(a);
        frame.setLocation(25, 25);
        frame.setTitle("Up Regulation ");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        frame.setBak((JPanel) frame.getContentPane(), "upRegulationChanged.jpg");
        frame.setSize(frame.img.getIconWidth()+22,(int)(frame.img.getIconHeight()+frame.img.getIconHeight()*0.07));
        
        frame.SetAGCTRightTop(frame.upString.substring(12).toUpperCase(), (JPanel) frame.getContentPane(), rect);
        frame.SetAGCTLeftTop(frame.upString.substring(0, 6).toUpperCase(), (JPanel) frame.getContentPane(), rect);
        frame.SetAGCTLeftBottom(frame.upString.substring(6, 12).toUpperCase(), (JPanel) frame.getContentPane(), rect);
        frame.SetAGCTRowRightTop(frame.upString.substring(0, 6).toUpperCase(), (JPanel) frame.getContentPane(), rect);
        frame.SetAGCTRowRightBottom(frame.upString.substring(6, 12).toUpperCase(), (JPanel) frame.getContentPane(), rect);
        frame.SetAGCTRightRight(frame.upString.substring(12).toUpperCase(), (JPanel) frame.getContentPane(), rect);
    }

    public static void ShowDownJPG(Riboswitch b) {
        try {
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());//"com.sun.java.swing.plaf.Windows.WindowsLookAndFeel"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jPanelUp frame2 = new jPanelUp(b);
        frame2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame2.setTitle("Down Regulation");
        frame2.setVisible(true);
        frame2.setLocation(25, 25);
        frame2.setBak((JPanel) frame2.getContentPane(), "downRegulationChanged.jpg");
        frame2.setSize(frame2.img.getIconWidth()+22,(int)(frame2.img.getIconHeight()+frame2.img.getIconHeight()*0.07));
        
        //frame2.SetDownJPGRightTop(frame2.downString.substring(12).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
        frame2.SetDownJPGLeftTop(frame2.downString.substring(0, 6).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
        frame2.SetDownJPGLeftBottom(frame2.downString.substring(6, 12).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
        frame2.SetDownJPGRowTop(frame2.downString.substring(0, 6).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
        frame2.SetDownJPGRowBottom(frame2.downString.substring(6, 12).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
        //frame2.SetDownJPGRightRight(frame2.downString.substring(12).toUpperCase(), (JPanel) frame2.getContentPane(), rect);
    }
    /**
     * Print chars from right to left on down jpg's LeftTop
     * @param str
     * @param panel
     * @param rectangle 
     */
    public void SetDownJPGLeftTop(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {
            return;
        }
        String color = "ff7700";
        int x = 202;
        int y = 247;
        int width = 19;
        int height = 19;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 5.5);
            y = (int) Math.ceil(rectangle.height / 2.3);    
//            System.out.println(x+" "+y);
        }
        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            //y += height - 5;
            x -= 15;
            this.jLabelListDown.add(labels[i]);
        }
    }

    public void SetDownJPGRightRight(String str, JPanel panel, Rectangle rectangle) {
        String color = "005c2f";
        int x = 650;
        int y = 100;
        int width = 19;
        int height = 19;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 1.75);
            y = (int) Math.ceil(rectangle.height / 5.35);
            ///width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[str.length()];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            y += height - 8;
            x += 15;
            this.jLabelListDown.add(labels[i]);
        }
    }

    public void SetDownJPGRowTop(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {
            return;
        }
        String color = "ff7700";
        int x = 750;
        int y = 115;
        int width = 19;
        int height = 19;
        if (rectangle != null) {
             x = (int) Math.ceil(rectangle.width *0.678);
             y = (int) Math.ceil(rectangle.height /4.89);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
//            System.out.println(rectangle.width+" "+rectangle.height);
        }
        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            x -= 15;
            //y += 12;
            this.jLabelListDown.add(labels[i]);
        }
    }

    public void SetDownJPGRowBottom(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {
            return;
        }
        String color = "ff7700";
        int x = 670;
        int y = 160;
        int width = 17;
        int height = 17;
        if (rectangle != null) {
//            x = (int) Math.ceil(rectangle.width / 1.45);
//            y = (int) Math.ceil(rectangle.height / 2.1);
//            System.out.println(rectangle.width);
//            System.out.println(rectangle.height);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }

        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            x += 15;
            //y -= 17;
            this.jLabelListDown.add(labels[i]);
        }
    }

    public void SetDownJPGLeftBottom(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {
            return;
        }
        String color = "ff7700";
        int x = 170;
        int y = 330;
        int width = 19;
        int height = 19;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 6.5);
            y = (int) Math.ceil(rectangle.height / 1.72);
//            System.out.println(rectangle.width);
//            System.out.println(rectangle.height);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }

        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            //y += height - 5;
            x += 15;
            this.jLabelListDown.add(labels[i]);
        }
    }

    public void SetDownJPGRightTop(String str, JPanel panel, Rectangle rectangle) {
        StringBuilder sb = new StringBuilder(str);
        str = sb.reverse().toString();
        String color = "005c2f";
        int x = 141;
        int y = 119;
        int width = 16;
        int height = 17;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 8);
            y = (int) Math.ceil(rectangle.height / 4.8);
            //System.out.println(x);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            x += 13;
            y += height - 5;
            this.jLabelListDown.add(labels[i]);
        }
    }

    /**
     * 
     * @param str
     * @param panel
     * @param rectangle 
     */
    public void SetAGCTRightRight(String str, JPanel panel, Rectangle rectangle) {
        String color = "005c2f";
        int x = 519;
        int y = 285;
        int width = 20;
        int height = 20;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 1.55);
            y = (int) Math.ceil(rectangle.height / 2.8);
            System.out.println(x);
            System.out.println(y);
            width = (int) Math.ceil(rectangle.width / 8);
            height = (int) Math.ceil(rectangle.height / 45);
        }

        JLabel[] labels = new JLabel[str.length()];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            y += height - 1;
            this.jLabelListUp.add(labels[i]);
        }
    }

    public void SetAGCTRightTop(String str, JPanel panel, Rectangle rectangle) {
        StringBuilder sb = new StringBuilder(str);
        str = sb.reverse().toString();
        String color = "005c2f";
        int x = 97;
        int y = 284;
        int width = 20;
        int height = 17;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 8);
            y = (int) Math.ceil(rectangle.height / 7);
            //System.out.println(y);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[str.length()];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            y += height - 1;
            this.jLabelListUp.add(labels[i]);
        }
    }

    public void SetAGCTLeftTop(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {
            return;
        }
        String color = "ff7700";
        int x = 70;
        int y = 111;
        int width = 20;
        int height = 20;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 11.1);
            y = (int) Math.ceil(rectangle.height / 7);
            //width=(int) Math.ceil(rectangle.width/8);
            ///height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            y += height - 1;
            this.jLabelListUp.add(labels[i]);
        }
    }

    public void SetAGCTLeftBottom(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {           
            return;
        }
        String color = "ff7700";
        int x = 53;
        int y = 253;
        int width = 20;
        int height = 20;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 14);
            y = (int) Math.ceil(rectangle.height / 3);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[6];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            if (i < 3) {
                x -= 5;
            } else {
                x += 5;
            }
            y += height - 1;
            this.jLabelListUp.add(labels[i]);
        }
    }

    /**
     * 
     * @param str
     * @param panel
     * @param rectangle 
     */
    public void SetAGCTRowRightTop(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {            
            return;
        }
        String color = "ff7700";
        int x = 427;
        int y = 450;
        int width = 20;
        int height = 20;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 1.95);
            y = (int) Math.ceil(rectangle.height / 1.7);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[str.length()];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            if (i <= 1) {
                y -= 8;
            } else if (i >= 4) {
                y += 8;
            }
            x -= width - 2;
            this.jLabelListUp.add(labels[i]);
        }
    }

    /**
     * 
     * @param str
     * @param panel
     * @param rectangle 
     */
    public void SetAGCTRowRightBottom(String str, JPanel panel, Rectangle rectangle) {
        if (str.length() != 6) {            
            return;
        }
        String color = "ff7700";
        int x = 327;
        int y = 510;
        int width = 20;
        int height = 20;
        if (rectangle != null) {
            x = (int) Math.ceil(rectangle.width / 2.5);
            y = (int) Math.ceil(rectangle.height / 1.5);
            //width=(int) Math.ceil(rectangle.width/8);
            //height=(int) Math.ceil(rectangle.height/45);
        }
        JLabel[] labels = new JLabel[str.length()];//new JLabel("<html><font color='"+color+"'>"+str.charAt(0)+"</font></html>");
        for (int i = 0; i < str.length(); i++) {
            labels[i] = new JLabel("<html><font color='" + color + "'>" + str.charAt(i) + "</font></html>");
            labels[i].setBounds(x, y, width, height);
            labels[i].setFont(new Font("Dialog", 0, 16));
            labels[i].setDoubleBuffered(true);
            panel.add(labels[i]);
            if (i <= 1) {
                y += 7;
            } else if (i >= 4) {
                y -= 7;
            }
            x += width - 2;
            this.jLabelListUp.add(labels[i]);
        }
    }
    //public JLabel background=null;
    public ImageIcon img=null;
    /**
     * Set LayeredPane's background jpeg
     * @param panel
     * @param JPGFileName 
     */
    public void setBak(JPanel panel, String JPGFileName) {
        panel.setOpaque(false);
        panel.setLayout(null);
        img = new ImageIcon(JPGFileName); 
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        this.rect = background.getBounds();
    }

    /**
     * Create the frame.
     */
    public jPanelUp() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(300, 100, 450, 300);
        Riboswitch a = new Riboswitch();
        a.makeON();
        String MS1 = a.getMS();
        String aptamer1 = a.getAptamer();
        this.upString = aptamer1.substring(0, 6);
        this.upString += aptamer1.substring(aptamer1.length() - 6);
        this.upString += MS1.substring(0, MS1.length() - 4);
        System.out.println(MS1.substring(0, MS1.length() - 4));
        a.makeOFF();
        String MS2 = a.getMS();
        String aptamer2 = a.getAptamer();
        this.downString = aptamer2.substring(0, 6);
        this.downString += aptamer2.substring(aptamer1.length() - 6);
        this.downString += MS2.substring(0, MS1.length() - 4);
        System.out.println(MS2.substring(0, MS1.length() - 4));
        this.setVisible(true);
    }

    public jPanelUp(Riboswitch a) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(300, 100, 450, 300);
        a.makeON();
        String MS1 = a.getMS();
        String aptamer1 = a.getAptamer();
        this.upString = aptamer1.substring(0, 6);
        this.upString += aptamer1.substring(aptamer1.length() - 6);
        this.upString += MS1.substring(0, MS1.length() - 4);
        System.out.println(MS1.substring(0, MS1.length() - 4));
        a.makeOFF();
        String MS2 = a.getMS();
        String aptamer2 = a.getAptamer();
        this.downString = aptamer2.substring(0, 6);
        this.downString += aptamer2.substring(aptamer2.length() - 6);
        //this.downString += MS2.substring(0, MS2.length() - 4);
        //System.out.println(MS2.substring(0, MS2.length() - 4));
        this.setVisible(true);
    }
}
