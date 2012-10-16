package org.KeggNetwork;
import java.awt.Graphics2D;
import twaver.ResizableNode;
import twaver.network.TNetwork;
import twaver.network.ui.ResizableNodeUI;
public class UmlNodeUI extends ResizableNodeUI {
    public UmlNodeUI(TNetwork network, ResizableNode node) {
        super(network, node);
    }
    //����ô������ô��
    protected void paintCustomDraw(Graphics2D g2d) {
        super.paintCustomDraw(g2d);
        //Rectangle bounds = this.getUIBounds();
        //g2d.drawLine(bounds.x+2, bounds.y+bounds.height-6, bounds.x+bounds.width-2, bounds.y+bounds.height-6);
        //g2d.drawLine(bounds.x+2, bounds.y+bounds.height-12, bounds.x+bounds.width-2, bounds.y+bounds.height-12);
    }
}