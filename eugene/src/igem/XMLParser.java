package igem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class XMLParser {
	public class subpart
	{
		public String id;
		public String name;
		public String description;
		public String type;
		public subpart(Element ele)
		{
			this.id=ele.getChildText("part_id");
			this.name=ele.getChildText("part_name");
			this.description=ele.getChildText("part_short_desc");
			this.type=ele.getChildText("part_type");
		}
	}
	List<Element> list;
    public XMLParser(String fileName)
    {
		this.readXMLContent(fileName);
    }    
    public List<Element> readXMLContent(String fileName) {
        /* Fromat: xml/genome.xml */
        String file_name = fileName;
        SAXBuilder builder = new SAXBuilder();   
        try {
            Document doc = builder.build(new File(file_name));
            Element rootEl = doc.getRootElement();
            //Get all child elements
            list = rootEl.getChildren();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
	private void getSpecified_subparts(Element root_part_list)
	{
		List<Element> subparts_xml=root_part_list.getChild("part").getChild("specified_subparts").getChildren("subpart");
		List<subpart> subparts=new ArrayList();
		for(int i=0;i<subparts_xml.size();i++)
		{
			subparts.add(new subpart(subparts_xml.get(i)));
		}
	}
	private void getDeep_subparts(Element root_part_list)
	{
		List<Element> subparts_xml=root_part_list.getChild("part").getChild("deep_subparts").getChildren("subpart");
		List<subpart> subparts=new ArrayList();
		for(int i=0;i<subparts_xml.size();i++)
		{
			subparts.add(new subpart(subparts_xml.get(i)));
		}

	}
	public static void main(String[] args){
		//System.out.println("hello");
		
	}
}
