/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.newUI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Zheng Yuanhuan
 */
public class IComboBoxRenderer implements ListCellRenderer {
 
 private DefaultListCellRenderer defaultCellRenderer = new DefaultListCellRenderer();

 public IComboBoxRenderer() {
  super();
 }

 public Component getListCellRendererComponent(JList list, Object value,
   int index, boolean isSelected, boolean cellHasFocus) {

  JLabel renderer = (JLabel)defaultCellRenderer.getListCellRendererComponent(
    list, value, index, isSelected, cellHasFocus);
  if(isSelected){
   renderer.setBackground(new Color(146,148,151));
   renderer.setForeground(Color.black);
  }else{
   renderer.setBackground(new Color(220,220,220));
  }
  list.setSelectionBackground(new Color(220,220,220));
  list.setBorder(null);
  //renderer.setFont(Color.WHITE);
  renderer.setHorizontalAlignment(JLabel.CENTER);
  return renderer;
 }
}

