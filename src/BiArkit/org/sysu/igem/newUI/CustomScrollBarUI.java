/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.newUI;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * 
 * @author Zheng Yuanhuan
 */
public class CustomScrollBarUI extends BasicScrollBarUI {
    //private Color frameColor = new Color(145, 105, 55);
    //public Color frameColor = Color.red;

    public Color frameColor = new Color(146, 148, 151);

    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(16, 16);
    }

    //Redraw the scrollbar slider
    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        super.paintThumb(g, c, thumbBounds);
        int tw = thumbBounds.width;
        int th = thumbBounds.height;
        // Reset the origin of the graphics context, this must be written, 
        //otherwise there will be the phenomenon of drag the slider when the slider is not moving
        g.translate(thumbBounds.x, thumbBounds.y);

        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = null;
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            //gp = new GradientPaint(0, 0, new Color(242, 222, 198), tw, 0, new Color(207, 190, 164));
            //gp = new GradientPaint(0, 0, new Color(242, 222, 198), 0, th, Color.red);
            gp = new GradientPaint(0, 0, new Color(70, 198, 249), 0, th, new Color(70, 198, 249));
        }
        if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            //gp = new GradientPaint(0, 0, new Color(242, 222, 198), 0, th, new Color(207, 190, 164));
            //gp = new GradientPaint(0, 0, new Color(242, 222, 198), 0, th, Color.red);
            gp = new GradientPaint(0, 0, new Color(70, 198, 249), 0, th, new Color(70, 198, 249));
        }
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, tw - 1, th - 1, 5, 5);
        g2.setColor(frameColor);
        g2.drawRoundRect(0, 0, tw - 1, th - 1, 5, 5);
    }

    // The redraw slider sliding regional background
    @Override
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = null;
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            //gp = new GradientPaint(0, 0, new Color(198, 178, 151), trackBounds.width, 0, new Color(234, 214, 190));
            // gp = new GradientPaint(0, 0, new Color(198, 178, 151), trackBounds.width, 0, Color.red);
            gp = new GradientPaint(0, 0, frameColor, trackBounds.width, 0, frameColor);
        }
        if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            //gp = new GradientPaint(0, 0, new Color(198, 178, 151), 0, trackBounds.height, new Color(234, 214, 190));
            //gp = new GradientPaint(0, 0, new Color(198, 178, 151), 0, trackBounds.height, Color.red);
            gp = new GradientPaint(0, 0, frameColor, 0, trackBounds.height, frameColor);
        }
        g2.setPaint(gp);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.setColor(new Color(175, 155, 95));
        g2.drawRect(trackBounds.x, trackBounds.y, trackBounds.width - 1, trackBounds.height - 1);
        if (trackHighlight == BasicScrollBarUI.DECREASE_HIGHLIGHT) {
            this.paintDecreaseHighlight(g);
        }
        if (trackHighlight == BasicScrollBarUI.INCREASE_HIGHLIGHT) {
            this.paintIncreaseHighlight(g);
        }
    }

    // Redraw when sliding up or left button mouse click area
    @Override
    protected void paintDecreaseHighlight(Graphics g) {
        g.setColor(Color.green);
        int x = this.getTrackBounds().x;
        int y = this.getTrackBounds().y;
        int w = 0, h = 0;
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            w = this.getThumbBounds().width;
            h = this.getThumbBounds().y - y;

        }
        if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            w = this.getThumbBounds().x - x;
            h = this.getThumbBounds().height;
        }
        g.fillRect(x, y, w, h);
    }

    // Redraw when the mouse to click on the slider to the right button down or the area between the
    @Override
    protected void paintIncreaseHighlight(Graphics g) {
        Insets insets = scrollbar.getInsets();
        g.setColor(Color.blue);
        int x = this.getThumbBounds().x;
        int y = this.getThumbBounds().y;
        int w = this.getTrackBounds().width;
        int h = this.getTrackBounds().height;
        g.fillRect(x, y, w, h);
    }

    //The down to the right arrow 
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new BasicArrowButton(orientation) {
            // Redraw button triangle mark

            @Override
            public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = null;
                Image arrowImg = null;
                switch (this.getDirection()) {
                    case BasicArrowButton.SOUTH:
                        //gp = new GradientPaint(0, 0, new Color(242, 222, 198), getWidth(), 0, new Color(207, 190, 164));
                        arrowImg = new ImageIcon("src\\org\\igem\\browser\\SliderSouthButton.png").getImage();
                        break;
                    case BasicArrowButton.EAST:
                        //gp = new GradientPaint(0, 0, new Color(242, 222, 198), 0, getHeight(), new Color(207, 190, 164));
                        //arrowImg = ImageLoader.get("east.gif");
                        arrowImg = new ImageIcon("src\\org\\igem\\browser\\SliderEastButton.png").getImage();
                        break;
                }

                //g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(frameColor);
                g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2.drawImage(arrowImg, (getWidth() - 2) / 2 - 5, (getHeight() - 2) / 2 - 5, 14, 14, null);
                //g2.drawImage(arrowImg, (getWidth() - 2) / 2, (getHeight() - 2) / 2, 13, 13, null);
                //g2.drawImage(arrowImg, (getWidth() - 2) / 2 - 5, (getHeight() - 2) / 2 - 5, 10, 10, null);
                //g2.drawImage(arrowImg, (getWidth() - 2) / 2 - 5, (getHeight() - 2) / 2 - 5, this);
            }
        };
    }

    //To the left and up arrow
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new BasicArrowButton(orientation) {

            @Override
            public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = null;
                Image arrowImg = null;
                switch (this.getDirection()) {
                    case BasicArrowButton.NORTH:
                        //gp = new GradientPaint(0, 0, new Color(242, 222, 198), getWidth(), 0, new Color(207, 190, 164));
                        //arrowImg = ImageLoader.get("south.gif");
                        arrowImg = new ImageIcon("src\\org\\igem\\browser\\SliderNorthButton.png").getImage();
                        break;
                    case BasicArrowButton.WEST:
                        //gp = new GradientPaint(0, 0, new Color(242, 222, 198), getWidth(), 0, new Color(207, 190, 164));
                        //arrowImg = ImageLoader.get("south.gif");
                        arrowImg = new ImageIcon("src\\org\\igem\\browser\\SliderWestButton.png").getImage();
                        break;
                }
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(frameColor);
                g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2.drawImage(arrowImg, (getWidth() - 2) / 2 - 5, (getHeight() - 2) / 2 - 5, 14, 14, null);
            }
        };
    }
}