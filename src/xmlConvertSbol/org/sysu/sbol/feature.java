package org.sysu.sbol;

import org.jdom.Element;

public class feature {
	String id;
	String title;
	String type;
	String direction;
	int startpos;
	int endpos;
	public feature(Element ele) {
		this.id=ele.getChildText("id");
		this.title=ele.getChildText("title");
		this.type=ele.getChildText("type");
		this.direction=ele.getChildText("direction");
		this.startpos=Integer.parseInt(ele.getChildText("startpos"));
		this.endpos=Integer.parseInt(ele.getChildText("endpos"));
	}	
}
