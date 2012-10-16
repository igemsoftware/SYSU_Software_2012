/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import java.util.ArrayList;
import java.util.List;
import org.igem.browser.Genome;
import org.jdom.Element;

/**
 * Get genome list from xml
 * @author Guo Jiexin
 */
public class GetGenomeFromXml {
    private String xmlFilePath=null;
    List<Genome> xmlData=new ArrayList<Genome>();
    public List<Genome> getXmlData() {
        return xmlData;
    }
    public void setXmlData(String filepath) {
        this.xmlData.clear();
        this.xmlFilePath=filepath;
        this.readGenomeXML();
    }
    public void setXmlData(List<Genome> xmlData) {
        this.xmlData.clear();
        this.xmlData = xmlData;
    }
    public GetGenomeFromXml(String path) {
        this.xmlFilePath=path;
        this.readGenomeXML();
    }    
    public void readGenomeXML()
    {
        if (xmlFilePath == null) {
            return;
        }        
        ReadXml read = new ReadXml();
        List<Element> l = new ArrayList<Element>();
        Genome genome = null;
        l = read.readXMLContent(xmlFilePath);
        for (Element el : l) {
            genome = new Genome();
            if(el.getChildText("head").isEmpty()||el.getChildText("tail").isEmpty())
                continue;
            genome.setFeature(el.getChildText("feature"));
            genome.setName(el.getChildText("name"));
            genome.setHead(Integer.parseInt(el.getChildText("head")));
            genome.setTail(Integer.parseInt(el.getChildText("tail")));
            genome.setTag(el.getChildText("tag"));
            genome.setProduct(el.getChildText("product"));
            genome.setGeneLink(el.getChildText("genelink"));
            xmlData.add(genome);
        }
    }
}
