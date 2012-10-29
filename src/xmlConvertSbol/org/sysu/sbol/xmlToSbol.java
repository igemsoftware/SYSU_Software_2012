package org.sysu.sbol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sbolstandard.core.DnaComponent;
import org.sbolstandard.core.DnaSequence;
import org.sbolstandard.core.SBOLDocument;
import org.sbolstandard.core.SBOLFactory;
import org.sbolstandard.core.SBOLRootObject;
import org.sbolstandard.core.SequenceAnnotation;
import org.sbolstandard.core.StrandType;
import org.sbolstandard.core.util.SequenceOntology;
import org.sysu.sbol.Subpart.subpart_type;
/**
 * 
* @ClassName: xmlToSbol 
* @Description: Core class to convert an xml to sbol
* @author Guo Jiexin
* @date Oct 25, 2012 7:00:19 PM 
*
 */
public class xmlToSbol {
	public static int partId=0;
	private SBOLRootObject createDnaComponent(String xml,SBOLDocument document) {
        SAXBuilder builder = new SAXBuilder();
        DnaComponent dnaComponent = SBOLFactory.createDnaComponent();
        try {
            Document doc = builder.build(new File(xml));
            Element rootEl = doc.getRootElement();
            
            Element list = rootEl.getChild("part_list").getChild("part");
            partId=Integer.parseInt(list.getChildText("part_id"));
            dnaComponent.setURI(URI.create(list.getChildText("part_url")));
            dnaComponent.setDisplayId(list.getChildText("part_name"));
            dnaComponent.setName(list.getChildText("part_short_name"));
            dnaComponent.setDescription(list.getChildText("part_short_desc"));
            Element seq=rootEl.getChild("part_list").getChild("part").getChild("sequences");
            
            dnaComponent.setDnaSequence(this.createDnaSequence(seq.getChildText("seq_data")));
            List<SequenceAnnotation> seqs=createAllDnaSubComponent(xml);
            System.out.println(seqs.size());
            for(int i=0;i<seqs.size();i++)
            	dnaComponent.addAnnotation(seqs.get(i));
            
        } catch (JDOMException e) {
//            e.printStackTrace();
            
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return dnaComponent;
    }
	public String strToSeq(String str)
	{
		String newStr="";
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)=='a'||str.charAt(i)=='c'
					||str.charAt(i)=='t'||str.charAt(i)=='g')
			{
				newStr+=str.charAt(i);
			}
		}
		return newStr;
	}
	public DnaSequence createDnaSequence(String seq) {
		DnaSequence dnaSequence = SBOLFactory.createDnaSequence();
		dnaSequence.setURI(URI.create("http://partsregistry.org/seq/partseq_"+this.partId));
		dnaSequence.setNucleotides(strToSeq(seq));
		return dnaSequence;		
	}
	private List<SequenceAnnotation> createAllDnaSubComponent(String xml) {
		List<SequenceAnnotation> subs=new ArrayList();
		Subparts mysubparts=new Subparts(xml);
		for(int i=0;i<mysubparts.getSubpart_list().size();i++)
		{
			subs.add(createAnnotation(mysubparts.getSubpart_list().get(i),i+1));
		}		
		for(int i=0;i<mysubparts.feature_list.size();i++)
		{
			subs.add(createAnnotation(mysubparts.feature_list.get(i),i+1));
		}
		return subs;		
	}
	private SequenceAnnotation createAnnotation(feature sub,int place) {
		SequenceAnnotation annotation2 = SBOLFactory.createSequenceAnnotation();
		annotation2.setBioStart(sub.startpos);
		annotation2.setBioEnd(sub.endpos);
		if(sub.direction.equals("forward"))
			annotation2.setStrand(StrandType.POSITIVE);
		else
			annotation2.setStrand(StrandType.NEGATIVE);
		annotation2.setURI(URI.create("http://sbols.org/anot/f_"+sub.id));
		DnaComponent dnaComponent2 = SBOLFactory.createDnaComponent();
		dnaComponent2.setURI(URI.create("http://partsregistry.org/feat/f_"+sub.id));
		dnaComponent2.setDisplayId("f_"+sub.id);
		dnaComponent2.setName(sub.type);
		dnaComponent2.addType(SequenceOntology.type(sub.type));
		annotation2.setSubComponent(dnaComponent2);
		return annotation2;	
	}
	/**
	 * Add subpart to a SequenceAnnotation
	 * @param sub Subpart to create SequenceAnnotation
	 * @param place
	 * @return
	 */
	private SequenceAnnotation createAnnotation(Subpart sub,int place) {
		SequenceAnnotation annotation2 = SBOLFactory.createSequenceAnnotation();
		annotation2.setURI(URI.create("http://sbols.org/anot/an_"+this.partId+"_"+sub.id+"_"+place));
		DnaComponent dnaComponent2 = SBOLFactory.createDnaComponent();
		dnaComponent2.setURI(URI.create("http://partsregistry.org/Part/"+sub.getPart_name()));
		dnaComponent2.setDescription(sub.description);
		dnaComponent2.setDisplayId(sub.getPart_name());
		dnaComponent2.setName(sub.nickName);
		dnaComponent2.addType(SequenceOntology.type(sub.getType().toString()));
		annotation2.setSubComponent(dnaComponent2);
		return annotation2;	
	}
	public static int size=0;
//	public static URI getTypeBySubpartType(subpart_type type)
//	{
//		switch(type)
//		{
//			case Terminator:return SequenceOntology.TERMINATOR;
//			case Primers:return SequenceOntology.PRIMER_BINDING_SITE;
//		}
//		return SequenceOntology.CDS;			
//	}
    public xmlToSbol(String xml, String sbol) throws IOException {
        SBOLDocument document = createDocument(xml);
        System.out.println("Created a new SBOL document with " + document.getContents().size() + " element(s)");
        FileOutputStream fileoutStream=new FileOutputStream(sbol);
        SBOLFactory.write(document, fileoutStream);
        fileoutStream.close();
    }

    /**
     * Creates a document with a single DnaComponent.
     */
    public SBOLDocument createDocument(String xml) {
        SBOLDocument document = SBOLFactory.createDocument();
        document.addContent(createDnaComponent(xml,document));
        return document;
    }
//    public static void main(String[] args) {
//        try {
//            new xmlToSbol("F:/360data/重要数据/桌面/TestBrowser/biobrick/Composite parts/BBa_I5610.xml","1.sbol");
//        } catch (IOException ex) {
////            Logger.getLogger(xmlToSbol.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}
