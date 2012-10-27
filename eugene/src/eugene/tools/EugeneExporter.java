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
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;

/**
 *
 * @author Adam Liu
 */
public class EugeneExporter {

    // populate data model
    public EugeneExporter(HashMap<String, String> propertyDefs,
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

    // iterate through data model and write code to file
    public void exportEugene(File file) throws FileNotFoundException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        Writer output = new BufferedWriter(new FileWriter(file));
        try {
            String line, propertyName, propertyType, partName, instanceName, imagePath, assertion, note, componentName, components;
            ArrayList<String> propertyList, imagePathList, componentList;
            Part part;
            Rule rule;
            Device device;
            ArrayList<String> devicesToProcess;
            HashSet<String> devicesWritten;
            int lstSize;
            Iterator<String> iter;
            // parse property definitions
            iter = _propertyDefinitions.keySet().iterator();
            while (iter.hasNext()) {
                propertyName = iter.next();
                propertyType = _propertyDefinitions.get(propertyName);
                if (propertyType.equals("numList") || propertyType.equals("txtList")) {
                    propertyType = propertyType.substring(0, 3) + "[]";
                }
                line = parsePropertyDefinition(propertyName, propertyType);
                output.write(line);
            }
            // parse part definitions
            iter = _partDefinitions.keySet().iterator();
            while (iter.hasNext()) {
                partName = iter.next();
                propertyList = _partDefinitions.get(partName).get(0);
                line = parsePartDefinition(partName, propertyList);
                output.write(line);
                imagePathList = _partDefinitions.get(partName).get(1);
                if (!imagePathList.isEmpty()) {
                    line = parseImageBinding(partName, imagePathList.get(0));
                    output.write(line);
                }
            }
            // parse part declarations and instance image bindings
            iter = _partDeclarations.keySet().iterator();
            while (iter.hasNext()) {
                instanceName = iter.next();
                part = _partDeclarations.get(instanceName);
                line = parsePartDeclaration(part);
                output.write(line);
                imagePathList = _partDefinitions.get(part.type).get(1);
                if (!imagePathList.isEmpty()) {
                    imagePath = imagePathList.get(0);
                    if (!part.imagePath.equals(imagePath)) {
                        line = parseImageBinding(part.name, imagePath);
                        output.write(line);
                    }
                }
            }
            // parse rule declarations
            iter = _ruleDeclarations.keySet().iterator();
            while (iter.hasNext()) {
                instanceName = iter.next();
                rule = _ruleDeclarations.get(instanceName);
                line = parseRuleDeclaration(rule);
                output.write(line);
            }
            // parse rule assertions
            iter = _ruleAssertions.keySet().iterator();
            while (iter.hasNext()) {
                assertion = iter.next();
                line = parseRuleAssertion(assertion);
                output.write(line);
            }
            // parse rule notes
            iter = _ruleNotes.keySet().iterator();
            while (iter.hasNext()) {
                note = iter.next();
                line = parseRuleNote(note);
                output.write(line);
            }
            // parse device declarations and instance image bindings
            devicesToProcess = new ArrayList<String>(_deviceDeclarations.keySet());
            devicesWritten = new HashSet<String>();
            components = "";
            while (!devicesToProcess.isEmpty()) {
                instanceName = devicesToProcess.remove(0);
                device = _deviceDeclarations.get(instanceName);
                componentList = device.components;
                lstSize = componentList.size();
                for (int i = 0; i < lstSize; i++) {
                    componentName = componentList.get(i);
                    components += (componentName + ", ");
                    if (!(_partDeclarations.containsKey(componentName) || devicesWritten.contains(componentName))) {
                        components = "";
                        break;
                    }
                }
                if (components.isEmpty() && lstSize != 0) {
                    devicesToProcess.add(instanceName);
                } else {
                    if (lstSize != 0) {
                        line = parseDeviceDeclaration(device.name, components.substring(0, components.length() - 2));
                    } else { // empty device
                        line = parseDeviceDeclaration(device.name, "");
                    }
                    output.write(line);
                    if (!device.imagePath.isEmpty()) {
                        line = parseImageBinding(device.name, device.imagePath);
                        output.write(line);
                    }
                    devicesWritten.add(instanceName);
                }
                components = "";
            }
        }
        finally {
            output.close();
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    // parse property definitions
    private String parsePropertyDefinition(String name, String type) {
        return "Property " + name + "(" + type + ");" + System.getProperty("line.separator");
    }

    // parse part definitions
    private String parsePartDefinition(String name, ArrayList<String> propertyList) {
        String rtn = "Part " + name + "(";
        int lstSize = propertyList.size();
        for (int i = 0; i < lstSize; i++) {
            rtn += (propertyList.get(i) + ", ");
        }
        rtn = rtn.substring(0, rtn.length() - 2) + ");" + System.getProperty("line.separator");
        return rtn;
    }

    // parse image bindings
    private String parseImageBinding(String name, String imagePath) {
        return "Image (" + name + ", \"" + imagePath + "\");" + System.getProperty("line.separator");
    }

    // parse part declarations
    private String parsePartDeclaration(Part p) {
        String rtn = p.type + " " + p.name + "(";
        Iterator<String> iter = p.propertyValues.keySet().iterator();
        String propertyName, propertyValue;
        while (iter.hasNext()) {
            propertyName = iter.next();
            propertyValue = p.propertyValues.get(propertyName).toString();
            rtn += ("." + propertyName + "(" + propertyValue + "), ");
        }
        rtn = rtn.substring(0, rtn.length() - 2) + ");" + System.getProperty("line.separator");
        return rtn;
    }

    // parse rule declarations
    private String parseRuleDeclaration(Rule r) {
        return "Rule " + r.name + "(" + r.operand1 + " " + r.operator + " " + r.operand2 + ");" + System.getProperty("line.separator");
    }

    // parse rule assertions
    private String parseRuleAssertion(String assertion) {
        return "Assert(" + assertion + ");" + System.getProperty("line.separator");
    }

    // parse rule notes
    private String parseRuleNote(String note) {
        return "Note(" + note + ");" + System.getProperty("line.separator");
    }

    // parse device declarations
    private String parseDeviceDeclaration(String name, String components) {
        return "Device " + name + "(" + components +  ");" + System.getProperty("line.separator");
    }

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

    
    public static void main(String args[]) throws Exception {
        File eugFile = new File("C:\\Users\\q\\Desktop\\EugeneToXMLExample.eug");
        if (eugFile == null) {
            System.err.println("No file found.");
            return;
        }
        // Run Eugene lexer and parser
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
        EugeneExporter eugEx = new EugeneExporter(propertyDefs, partDefs, partDecs, deviceDecs, primitiveDecs, ruleDecs, ruleAsserts, ruleNotes);
        eugEx.exportEugene(new File("C:\\Users\\q\\Desktop\\EugeneToXMLExample.eug.eug"));
        System.exit(0);
    }

}
