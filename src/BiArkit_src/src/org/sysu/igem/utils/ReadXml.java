/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sysu.igem.utils;

import org.jdom.*;
import org.jdom.input.*;
import java.io.*;
import java.util.*;
       
/**
 *
 * @author q
 */
public class ReadXml {
    List<Element> list;

    public ReadXml() {
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
            //List<Element> list = rootEl.getChildren("part");  //The same
        /* Useage
            for (Element el : list) {
                System.out.println(el.getChildText("head"));
                System.out.println(el.getChildText("product"));
            } 
        */
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static void main(String[] args){
        ReadXml read = new ReadXml();
        List<Element> l = read.readXMLContent("genome.xml");
        for (Element el : l) {
//            System.out.print(el.getChildText("head"));
            System.out.println(el.getChildText("head"));
            //System.out.print("->");
            //System.out.println(el.getChildText("tail"));
            //System.out.println(el.getChildText("product"));
        }
    }
}
