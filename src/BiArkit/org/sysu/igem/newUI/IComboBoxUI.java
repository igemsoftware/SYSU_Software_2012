/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.newUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 *
 * @author Zheng Yuanhuan
 */
public class IComboBoxUI extends BasicComboBoxUI {

    private JButton arrow;
    private boolean boundsLight = false;
//    private static final int ARCWIDTH = 15;
//    private static final int ARCHEIGHT = 15;
    public ImageIcon imgArrow;
    public static int count = 1;

    public IComboBoxUI() {
        super();
        //count = 1;
    }

    protected JButton createArrowButton() {
        arrow = new JButton();
        if (count == 1) {
            imgArrow = new ImageIcon("src\\org\\igem\\browser\\comboboxButton1.png");
        } else if (count == 2) {
            imgArrow = new ImageIcon("src\\org\\igem\\browser\\comboboxButton2.png");
        }
        arrow.setIcon(imgArrow);
        arrow.setBackground(Color.white);
        arrow.setRolloverEnabled(true);
        arrow.setBorder(null);
        arrow.setBackground(Color.WHITE);
        arrow.setOpaque(false);
        arrow.setContentAreaFilled(false);
        count++;
        return arrow;
    }

    public void paint(Graphics g, JComponent c) {
        hasFocus = comboBox.hasFocus();
        Graphics2D g2 = (Graphics2D) g;
        if (!comboBox.isEditable()) {
            Rectangle r = rectangleForCurrentValue();

            paintCurrentValueBackground(g2, r, hasFocus);
            paintCurrentValue(g2, r, hasFocus);
        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int width = (int) this.getPreferredSize(c).getWidth()
                + arrow.getWidth() - 2;
        int height = 0;
        int heightOffset = 0;
        if (comboBox.isPopupVisible()) {
            heightOffset = 5;
            height = (int) this.getPreferredSize(c).getHeight();
            arrow.setIcon(imgArrow);
        } else {
            heightOffset = 0;
            height = (int) this.getPreferredSize(c).getHeight() - 1;
            arrow.setIcon(imgArrow);
        }
        if (comboBox.isFocusable()) {
            //g2.setColor(new Color(150, 207, 254));
            //Border Color
            g2.setColor(Color.black);
        }
        //g2.drawRoundRect(0, 0, width, height + heightOffset,ARCWIDTH,ARCHEIGHT);
        //g2.drawRect(0, 0,(int)this.getPreferredSize(c).getWidth(),(int)this.getPreferredSize(c).getHeight());
        // g2.drawRect(0, 0, 340, 23);
    }

    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        Font oldFont = comboBox.getFont();
        //comboBox.setFont(XUtil.defaultComboBoxFont);

        super.paintCurrentValue(g, bounds, hasFocus);
        comboBox.setFont(oldFont);
    }

    /* public Dimension getPreferredSize(JComponent c) {
    return super.getPreferredSize(c);
    }*/
    public boolean isBoundsLight() {
        return boundsLight;
    }

    public void setBoundsLight(boolean boundsLight) {
        this.boundsLight = boundsLight;
    }

    @Override
    protected ComboPopup createPopup() {
        ComboPopup popup = new BasicComboPopup(comboBox) {

            @Override
            protected JScrollPane createScroller() {
                JScrollPane sp = new JScrollPane(list,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                sp.getVerticalScrollBar().setUI(new CustomScrollBarUI());
                sp.setHorizontalScrollBar(null);
                return sp;
            }
            //Overloaded the paintBorder method that get a border we want ..
            /*public void paintBorder(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(150, 207, 254));
            g2.drawRoundRect(0,-arrow.getHeight(),getWidth()-1,getHeight()+arrow.getHeight()-1,0,0);
            }*/
        };
        return popup;
    }
}
