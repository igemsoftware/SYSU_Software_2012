package org.KeggNetwork;

import org.dom4j.Element;

public class Graphics {

    public int getX() {
        return Integer.parseInt(x);
    }

    public void setX(String x) {
        this.x = x;
    }

    public int getY() {
        return Integer.parseInt(y);
    }

    public void setY(String y) {
        this.y = y;
    }
    public String name = "not init";
    public String fgcolor = "not init";
    public String bgcolor = "not init";
    public String type = "not init";
    private String x = "not init";
    private String y = "not init";
    public String width = "not init";
    public String height = "not init";

    public Graphics(final Element element) {
        name = element.attributeValue("name");
        fgcolor = element.attributeValue("fgcolor");
        bgcolor = element.attributeValue("bgcolor");
        type = element.attributeValue("type");
        x = element.attributeValue("x");
        y = element.attributeValue("y");
        width = element.attributeValue("width");
        height = element.attributeValue("height");
        if (name == null)//group type of the entry of graphics with no name attribute
        {
            this.width = "0";
            this.height = "0";
            this.name = "null";
        }
    }

    public void PrintAll() {
        System.out.print(this.name + ' ');
        System.out.print(this.fgcolor + ' ');
        System.out.print(this.bgcolor + ' ');
        System.out.print(this.type + ' ');
        System.out.print(this.x + ' ');
        System.out.print(this.y + ' ');
        System.out.print(this.width + ' ');
        System.out.print(this.height + ' ');
        System.out.println();
    }
}
