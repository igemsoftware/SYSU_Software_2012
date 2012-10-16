/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.newUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * @author Zheng Yuanhuan
 */
public class MySliderUI extends BasicSliderUI {

    //The following code respectively represent pictures of the three states for indicating the icons
    public Image thumbImage = null;//The ordinary  picture of pressing

    public MySliderUI(JSlider b) {
        super(b);
    }

    /** */
    /**
     * Drawing Indicators
     */
    /* public void paintThumb(Graphics g) {
    super.paintThumb(g);
    }*/
    /** */
    /**
     * Drawing scale track
     */
    public void paintTrack(Graphics g) {
        int cy, cw;

        Rectangle trackBounds = trackRect;
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            Graphics2D g2 = (Graphics2D) g;
            cy = (trackBounds.height / 2) - 2;
            cw = trackBounds.width;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.translate(trackBounds.x, trackBounds.y + cy);

            //background color
            //Change the color of the partial drag slot drag strip
            g2.setPaint(new Color(146, 148, 151));
            g2.fillRect(0, -cy + 5, cw, cy);

            int trackLeft = 0;
            int trackRight = 0;
            trackRight = trackRect.width - 1;

            int middleOfThumb = 0;
            int fillLeft = 0;
            int fillRight = 0;

            // Coordinate Conversion
            //
            thumbRect.setSize(18, 18);
            //
            middleOfThumb = thumbRect.x + (thumbRect.width / 2);
            middleOfThumb -= trackRect.x;

            if (!drawInverted()) {
                fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
                fillRight = middleOfThumb;
            } else {
                fillLeft = middleOfThumb;
                fillRight = !slider.isEnabled() ? trackRight - 1
                        : trackRight - 2;
            }
            // Color settings set not gradient - glide area
            g2.setPaint(new GradientPaint(0, 0, new Color(230, 231, 232), cw, 0,
                    new Color(230, 231, 232), true));

            g2.fillRect(0, -cy + 5, fillRight - fillLeft, cy);

            g2.translate(-trackBounds.x, -(trackBounds.y + cy));
        } else {
            super.paintTrack(g);
        }
    }

    public void paintThumb(Graphics g) {
        //Change the look of the cursor
        Image img = new ImageIcon("src\\org\\igem\\browser\\JsliderButton.png").getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //The filled oval frame for the current thumb position
        //g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width,thumbRect.height);
        //Textures (converted image using mouse events to reflect the different state)
        g2d.drawImage(img, thumbRect.x, thumbRect.y + 3, thumbRect.width, thumbRect.height, null);
        //g2d.drawImage(img, MIN_SCROLL, MIN_SCROLL, MIN_SCROLL, MIN_SCROLL, );
    }
}
