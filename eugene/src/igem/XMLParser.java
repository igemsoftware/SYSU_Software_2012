package igem;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
			System.out.println(this);
		}
		@Override
		public String toString() {
			StringBuilder str=new StringBuilder("Subpart ");
			str.append(this.name).append("(");
			str.append(".ID(\"").append(this.name).append("\"), ");
			str.append(".DISPLAYID(\"").append(this.id).append("\"), ");
			str.append(".Description(\"").append(this.description).append("\"), ");
			str.append(".TYPE(\"").append(this.type).append("\")");
			str.append(");");
			return str.toString();
		}		
	}
	private static String stringToSeq(String str)
	{
		StringBuilder toReturn=new StringBuilder("");
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)=='a'||str.charAt(i)=='t'||str.charAt(i)=='c'
					||str.charAt(i)=='g')
				toReturn.append(str.charAt(i));
		}
		return toReturn.toString();
	}
	public void createXmlEugeneHeaderFile(String file)
	{
		 try {
			FileWriter out = new FileWriter(file);
			out.write(this.selfBiobrick+"\r\n");
			out.write("Property sequence(txt);\r\n"+"Property name(txt);\r\n");
			out.write("Property ID(txt);\r\n"+"DISPLAYID(num);\r\n");
			out.write("Property Description(txt);\r\n"+"Property SEQUENCES(txt);\r\n");
			out.write("Property TYPE(txt);\r\n");
			out.write("Part Biobrick(ID, DISPLAYID, Description,SEQUENCES,TYPE);\r\n");
			out.write("Part Subpart(ID, DISPLAYID, Description,TYPE);\r\n");
			
			for(int i=0;i<this.subparts.size();i++)
				out.write(this.subparts.get(i)+"\r\n");
			out.write("print("+this.selfBiobrick.id+".name)");
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
    public XMLParser(String fileName)
    {
		this.readXMLContent(fileName);
    }    
    public biobrick selfBiobrick=null;
    public void readXMLContent(String fileName) {
        /* Fromat: xml/genome.xml */
        String file_name = fileName;
        SAXBuilder builder = new SAXBuilder();   
        try {
            Document doc = builder.build(new File(file_name));
            Element rootEl = doc.getRootElement();
            rootEl=rootEl.getChild("part_list");
            this.selfBiobrick=new biobrick(rootEl.getChild("part"));
            List<Element> list = rootEl.getChildren();
            this.getDeep_subparts(rootEl);
            this.getSpecified_subparts(rootEl);
        } catch (JDOMException e) {
        } catch (IOException e) {
        }
    }
    List<subpart> subparts=new ArrayList();
    public class biobrick
    {
    	String id;
    	String name;
    	String description;
    	String seq;
    	String type;
    	public biobrick(Element ele)
    	{
    		this.id=ele.getChildText("part_id");
    		this.name=ele.getChildText("part_name");
    		this.type=ele.getChildText("part_type");    
    		this.seq=stringToSeq(ele.getChild("sequences").getChildText("seq_data"));
    		System.out.println(this);
    	}
    	@Override
		public String toString() {
			StringBuilder str=new StringBuilder("Biobrick ");
			str.append(this.name).append("(");
			str.append(".ID(\"").append(this.name).append("\"),");
			str.append(".DISPLAYID(\"").append(this.id).append("\"), ");
			str.append(".Description(\"").append(this.description).append("\"), ");
			str.append(".SEQUENCES(\"").append(this.seq).append("\"), ");
			str.append(".TYPE(\"").append(this.type).append("\")");
			str.append(");");
			return str.toString();
		}
    }
	private void getSpecified_subparts(Element root_part_list)
	{
		List<Element> subparts_xml=root_part_list.getChild("part").getChild("specified_subparts").getChildren("subpart");
		if(subparts_xml.isEmpty())
			return;
		for(int i=0;i<subparts_xml.size();i++)
		{
			subparts.add(new subpart(subparts_xml.get(i)));
		}
	}
	private void getDeep_subparts(Element root_part_list)
	{
		List<Element> subparts_xml=root_part_list.getChild("part").getChild("deep_subparts").getChildren("subpart");
		if(subparts_xml.isEmpty())
			return;
		for(int i=0;i<subparts_xml.size();i++)
		{			
			subparts.add(new subpart(subparts_xml.get(i)));
		}		
	}
//	public void createMainFrame()
//	{
//		JFrame frame=new JFrame("biobrick to eugene header");
//		frame.setLocale(null);
//		frame.setSize(400, 400);
//		JPanel panel=new JPanel();
//		panel.setLayout(new FlowLayout());
//		frame.getContentPane().add(panel);
//		
//	}
	public static void main(String[] args){
		new XMLParser("C:/SYSU_Software_2012/eugene/bin/BBa_B1102.xml").createXmlEugeneHeaderFile("1.h");
	}
}
