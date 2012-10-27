package org.KeggNetwork;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

/**
 * A data class to store the relation 's data
 * @author Guo Jiexin
 */
public class Relation {
	public class SubType
	{
		public String name= "not init";
		public String value= "not init";
		public SubType(final Element element) {
			name=element.attributeValue("name");
			value=element.attributeValue("value");
		}		
		public void PrintAll()
		{
			System.out.print(name+' ');
			System.out.print(this.value+' ');
			System.out.println();
		}
	}
	public String entry1_id= "not init";
	public String entry2_id= "not init";
	public String type= "not init";
	public List<SubType> mSubType=new ArrayList<SubType>();
	public Relation(final Element element) {
		entry1_id=element.attributeValue("entry1");
		entry2_id=element.attributeValue("entry2");
		type=element.attributeValue("type");	
		List<Element> subtypes = element.elements("subtype"); 
		if(subtypes!=null)
	        for (Iterator<Element> it = subtypes.iterator(); it.hasNext();) 
	        { 
	            Element elm = (Element) it.next(); 
	            SubType ent=new SubType(elm); 
	            this.mSubType.add(ent);
	        } 
	}	
	public void PrintAll()
	{
		System.out.print(this.entry1_id+' ');
		System.out.print(this.entry2_id+' ');
		System.out.print(this.type+' ');
		System.out.println();
		for(Iterator<SubType> it=this.mSubType.iterator();it.hasNext();)
		{
			SubType elm = (SubType) it.next();
			elm.PrintAll();
		}
	}
}
