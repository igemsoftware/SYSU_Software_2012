package org.sysu.igem.utils;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class testSlider {

    static JFrame frame = new JFrame("JSlider ");

    public static void main(String[] args) {
        // show();
        ShowTable();
    }

    public static void ShowTable() {
        Frame f = new Frame("ceshi");

        JTable table;
        String[] headers = {"1","2","3","4","5"};
        Object[][] cellData = {{1, 2, 3, 4, 5}, {2}, {3}};

        DefaultTableModel model = new DefaultTableModel(cellData, headers) ;

        table = new JTable(model);
        
        TableColumnModel columns = table.getColumnModel();
        
        TableColumn column = columns.getColumn(0);
        column.setPreferredWidth(200);
        
        
        column = columns.getColumn(2);
        column.setPreferredWidth(5);
        
        f.add(table);
        f.setBounds(30, 30, 250, 120);
        f.setVisible(true);
    }

    public static void show() {

        final JLabel label = new JLabel("heool");
        final JSlider jslider = new JSlider(JSlider.HORIZONTAL, 0, 100,
                50);


        jslider.setMajorTickSpacing(10);
        jslider.setMinorTickSpacing(1);
        jslider.setPaintTicks(false);
        jslider.setPaintLabels(false);
        jslider.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

//        framesPerSecond.setUI(new MetalSliderUI() {
//            @Override
//            public void paintThumb(Graphics g) {
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                        RenderingHints.VALUE_ANTIALIAS_ON);
//                g2d.setColor(Color.red);
//                g2d.fillRect(thumbRect.x, thumbRect.y, thumbRect.width,
//                        thumbRect.height);
//            }
//        });

        jslider.addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                //System.out.println(e.getSource());
                jslider.setValue(50);
            }
        });

        jslider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                System.out.println(jslider.getValue());
            }
        });

        frame.getContentPane().setLayout(new BorderLayout());

        frame.setSize(400, 400);

        frame.getContentPane().add(jslider, BorderLayout.NORTH);
        frame.getContentPane().add(label, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}