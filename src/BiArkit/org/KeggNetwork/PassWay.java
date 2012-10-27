package org.KeggNetwork;
import org.dom4j.Element;
/**
 * 
 * @author Guo Jiexin
 */
public class PassWay {
	public String name = "not init";
	public String org = "not init";
	public String number = "not init";
	public String title = "not init";
	public String image = "not init";
	public String link = "not init";
	public PassWay(final Element element) {
		name = element.attributeValue("name");
		this.org = element.attributeValue("org");
		this.number = element.attributeValue("number");
		this.title = element.attributeValue("title");
		this.image = element.attributeValue("image");
		this.link = element.attributeValue("link");
	}
	public void PrintAll() 
	{
		System.out.print(name + ' ');
		System.out.print(org + ' ');
		System.out.print(number + ' ');
		System.out.print(title + ' ');
		System.out.print(image + ' ');
		System.out.print(link + ' ');
		System.out.println();
	}
}
