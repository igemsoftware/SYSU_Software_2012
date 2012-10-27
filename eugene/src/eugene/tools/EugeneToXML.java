/*
 Copyright (c) 2009 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS..
 */

/**
  *@author Lesia Bilitchenko, Adam Liu
  *@see <a href="http://www.clothocad.org">http://www.clothocad.org</a>
  */

package eugene.tools;

import eugene.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.w3c.dom.*;

/**
 *
 * @author Adam Liu
 */
public class EugeneToXML {
    
    // populate data model
    public EugeneToXML(HashMap<String, String> propertyDefs,
                       HashMap<String, ArrayList<ArrayList<String>>> partDefs,
                       HashMap<String, Part> partDecs,
                       HashMap<String, Device> deviceDecs,
                       HashMap<String, Primitive> primitiveDecs,
                       HashMap<String, Rule> ruleDecs,
                       HashMap<String, ArrayList<Primitive>> ruleAsserts,
                       HashMap<String, ArrayList<Primitive>> ruleNotes) {
        _propertyDefinitions = propertyDefs;
        _partDefinitions = partDefs;
        _partDeclarations = partDecs;
        _deviceDeclarations = deviceDecs;
        _primitiveDeclarations = primitiveDecs;
        _ruleDeclarations = ruleDecs;
        _ruleAssertions = ruleAsserts;
        _ruleNotes = ruleNotes;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    // iterate through data model and generate XML files
    public void exportXML(String fileName, boolean default_xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element root = document.createElement("design");
            document.appendChild(root);
            
            Element partsElem, partElem, propertyElem, devicesElem, deviceElem, componentsElem, componentElem;
            Text propertyText, componentText;
            Iterator<String> instanceIter, propertyIter;
            String partName, propertyName, propertyVal, deviceName, componentName;
            Part part;
            Device device;
            ArrayList<String> components;
            int componentsSize;

            // generate parts (maybe need to filter only parts used by devices)
            partsElem = document.createElement("parts");
            root.appendChild(partsElem);
            instanceIter = _partDeclarations.keySet().iterator();
            while (instanceIter.hasNext()) {
                // generate part tag
                partName = instanceIter.next();
                part = _partDeclarations.get(partName);
                partElem = document.createElement("part");
                partElem.setAttribute("type", part.type);
                partElem.setAttribute("name", partName);
                partsElem.appendChild(partElem);                
                if (default_xml) {
                    // generate tags for all properties
                    propertyIter = part.propertyValues.keySet().iterator();
                    while (propertyIter.hasNext()) {
                        propertyName = propertyIter.next();
                        propertyVal = part.propertyValues.get(propertyName).getValue().toString();
                        propertyElem = document.createElement(propertyName.toLowerCase());
                        partElem.appendChild(propertyElem);
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                } else {
                    // generate id tag
                    propertyElem = document.createElement("id");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("ID")) {
                        propertyVal = part.propertyValues.get("ID").txt;
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                    // generate name tag
                    propertyElem = document.createElement("description");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("Description")) {
                        propertyVal = part.propertyValues.get("Description").txt;
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                    // generate description tag
                    propertyElem = document.createElement("long_description");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("LongDescription")) {
                        propertyVal = part.propertyValues.get("LongDescription").txt;
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                    // generate sequence tag
                    propertyElem = document.createElement("sequence");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("Sequence")) {
                        propertyVal = part.propertyValues.get("Sequence").txt;
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                    // generate length tag
                    propertyElem = document.createElement("length");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("Length")) {
                        propertyVal = Double.toString(part.propertyValues.get("Length").num);
                        propertyVal = propertyVal.substring(0, propertyVal.indexOf("."));
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                    // generate orientation tag
                    propertyElem = document.createElement("orientation");
                    partElem.appendChild(propertyElem);
                    if (part.propertyValues.containsKey("Orientation")) {
                        propertyVal = part.propertyValues.get("Orientation").txt;
                        propertyText = document.createTextNode(propertyVal);
                        propertyElem.appendChild(propertyText);
                    }
                }
            }

            // generate devices
            devicesElem = document.createElement("devices");
            root.appendChild(devicesElem);
            instanceIter = _deviceDeclarations.keySet().iterator();
            while (instanceIter.hasNext()) {
                deviceName = instanceIter.next();
                device = _deviceDeclarations.get(deviceName);
                deviceElem = document.createElement("device");
                deviceElem.setAttribute("name", deviceName);
                devicesElem.appendChild(deviceElem);
                componentsElem = document.createElement("components");
                deviceElem.appendChild(componentsElem);
                components = device.components;
                componentsSize = components.size();
                for (int i = 0; i < componentsSize; i++) {
                    componentName = components.get(i);
                    componentElem = document.createElement("component");
                    if (_partDeclarations.containsKey(componentName)) {
                        componentElem.setAttribute("type", _partDeclarations.get(componentName).type);
                    } else {
                        componentElem.setAttribute("type", "Device");
                    }
                    componentsElem.appendChild(componentElem);
                    componentText = document.createTextNode(componentName);
                    componentElem.appendChild(componentText);
                }
            }

            // prepare a transformer
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fileName);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////


    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////

    private HashMap<String, String> _propertyDefinitions;
    private HashMap<String, ArrayList<ArrayList<String>>> _partDefinitions;
    private HashMap<String, Part> _partDeclarations;
    private HashMap<String, Device> _deviceDeclarations;
    private HashMap<String, Primitive> _primitiveDeclarations;
    private HashMap<String, Rule> _ruleDeclarations;
    private HashMap<String, ArrayList<Primitive>> _ruleAssertions;
    private HashMap<String, ArrayList<Primitive>> _ruleNotes;

    
    // put EugeneToXMLExample.eug in the working directory
    public static void main(String args[]) throws Exception {
        File eugFile = new File("C:\\Users\\q\\Desktop\\EugeneToXMLExample.eug");
        if (eugFile == null) {
            System.err.println("No file found.");
            return;
        }
        // run Eugene lexer and parser
        eugeneLexer lex = new eugeneLexer(new ANTLRFileStream(eugFile.getAbsolutePath()));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        eugeneParser parser = new eugeneParser(tokens);
        try {
            parser.prog();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eugene parser error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        HashMap<String, String> propertyDefs = parser.propertyDefinitions;
        HashMap<String, ArrayList<ArrayList<String>>> partDefs = parser.partDefinitions;
        HashMap<String, Part> partDecs = parser.partDeclarations;
        HashMap<String, Device> deviceDecs = parser.deviceDeclarations;
        HashMap<String, Primitive> primitiveDecs = parser.primitiveDeclarations;
        HashMap<String, Rule> ruleDecs = parser.ruleDeclarations;
        HashMap<String, ArrayList<Primitive>> ruleAsserts = parser.ruleAssertions;
        HashMap<String, ArrayList<Primitive>> ruleNotes = parser.ruleNotes;
        EugeneToXML eugToXML = new EugeneToXML(propertyDefs, partDefs, partDecs, deviceDecs, primitiveDecs, ruleDecs, ruleAsserts, ruleNotes);
        eugToXML.exportXML("", true);
        System.exit(0);
    }

}
