/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.igem.browser;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;
import org.sysu.igem.utils.ReadXml;

/**
 *The data class of genome
 * @author q
 */
public class Genome {
    int head;
    int tail;
    String feature;
    String name;
    String tag;
    String product;

    public String getGeneLink() {
        return geneLink;
    }

    public void setGeneLink(String geneLink) {
        this.geneLink = geneLink;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
    String geneLink;
    
    public String getTag(){
        return tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }
    
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTail() {
        return tail;
    }

    public void setTail(int tail) {
        this.tail = tail;
    }
    
    public Genome(int head, int tail, String feature, String name){
        this.head = head;
        this.tail = tail;
        this.feature = feature;
        this.name = name;
    }
    
    public Genome(){
        this.head = 0;
        this.tail = 0;
        this.feature = "";
        this.name = "";
    }
    
    public boolean isCds(int position){
        if(position >= head && position <= tail)
            return true;
        return false;
    }
    
    public List<Genome> setGenomeList(String fileName){
        List<Genome> list = new ArrayList();
        List<Element> element = new ReadXml().readXMLContent(fileName);
        Genome genome;
        for(Element e : element){
            genome = new Genome(
                    Integer.parseInt(e.getChildText("head")),
                    Integer.parseInt(e.getChildText("tail")),
                    e.getChildText("feature"),
                    e.getChildText("name")
                    );
            genome.setGeneLink(e.getChildText("genelink"));            
            list.add(genome);
        }
        return list;
    }
    
    public static void main(String[] args){
        List<Genome> list = new Genome().setGenomeList("genome.xml");
        for(Genome l : list){
            System.out.println(l.getHead());
        }
    }
}
