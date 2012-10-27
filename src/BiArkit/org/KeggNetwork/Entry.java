package org.KeggNetwork;
import org.dom4j.Element;
/**
 * The entry in a xml file of pathway
 * @author Guo Jiexin
 */
public class Entry {
	public String id = "not init";
	public String name = "not init";
	public String type = "not init";
	public String reaction = "not init";
	public String link = "not init";
	public Graphics mGraphics = null;
	private Element mSubElementGraphics=null;	
	public Entry(final Element element) {
		id = element.attributeValue("id");
		name = element.attributeValue("name");
		type = element.attributeValue("type");
		reaction = element.attributeValue("reaction");
		link = element.attributeValue("link");
		mSubElementGraphics = element.element("graphics");
		this.mGraphics = new Graphics(mSubElementGraphics);
	}
	public void PrintAll() {
		System.out.print(id + ' ');
		System.out.print(name + ' ');
		System.out.print(type + ' ');
		System.out.print(reaction + ' ');
		System.out.print(link + ' ');
		System.out.println();
		if(this.mGraphics!=null)
			this.mGraphics.PrintAll();
	}
}