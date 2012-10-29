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

import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Adam Liu
 */
public class XMLToEugeneHeader extends DefaultHandler {

    public XMLToEugeneHeader() {
        try {
            FileWriter fstream = new FileWriter("PartDeclaration.h", false);
            _out = new BufferedWriter(fstream);
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        _tempVal = "";
        if (qName.equalsIgnoreCase("part")) {
            _partDeclaration = "";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        _tempVal = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("part")) {
            try {
                _out.write(_partDeclaration + System.getProperty("line.separator"));
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        } else if (qName.equalsIgnoreCase("identifier")) {
            _partDeclaration += (_tempVal + "(.ID(\"" + _tempVal + "\"), ");
        } else if (qName.equalsIgnoreCase("type")) {
            _partDeclaration = (_tempVal + " " + _partDeclaration);
        } else if (qName.equalsIgnoreCase(/*"description"*/ "name")) {
            _partDeclaration += (".Description(\"" + _tempVal + "\"), ");
        } else if (qName.equalsIgnoreCase(/*"longdescription"*/ "description")) {
            _partDeclaration += (".LongDescription(\"" + _tempVal + "\"), ");
        } else if (qName.equalsIgnoreCase("sequence")) {
            _partDeclaration += (".Sequence(\"" + _tempVal + "\"), ");
        } else if (qName.equalsIgnoreCase("length")) {
            _partDeclaration += (".Length(" + _tempVal + "), ");
        } else if (qName.equalsIgnoreCase("orientation")) {
            //_partDeclaration += (".Orientation(\"" + _tempVal + "\"));");
            if (_tempVal.equals("+")) {
                _partDeclaration += (".Orientation(\"Forward\"));");
            } else if(_tempVal.equals("-")) {
                _partDeclaration += (".Orientation(\"Reverse\"));");
            } else {
                _partDeclaration += (".Orientation(\"Bidirectional\"));");
            }
        }
    }

    public void run() {
        parseDocument();
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private methods                   ////

    private void parseDocument() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse("C:\\Users\\q\\Desktop\\BBa_B1101.xml", this);
            _out.close();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////
    
    private String _tempVal;
    private String _partDeclaration;
    private BufferedWriter _out;


    // put XMLToEugeneHeaderExample.xml in the working directory
    public static void main(String[] args) {
        XMLToEugeneHeader xmlToHeader = new XMLToEugeneHeader();
        xmlToHeader.run();
    }

}