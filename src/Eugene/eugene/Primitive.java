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

package eugene;

import java.util.*;

public class Primitive {

    public String name;
	public String type;
	public boolean bool;
    public String txt;
	public double num;
	public ArrayList<Double> numList;
	public ArrayList<String> txtList;
    public int index;

	//need to always set type and varname!!
	Primitive() {
		name = "";
        type = "";
		txt = "";
		num = 0.0;
		txtList = new ArrayList<String>();
		numList = new ArrayList<Double>();
        index = -1;
	}

	public Primitive(String n, String t) {
		name = n;
        type = t;
		txt = "";
		num = 0.0;
		txtList = new ArrayList<String>();
		numList = new ArrayList<Double>();
        index = -1;
	}

	public void printNumList() {
		for(int i=0 ; i < numList.size(); i++) {
			System.out.print(numList.get(i) + " ");
		}
		System.out.println();
	}

	public void printTextList() {
		for(int i=0 ; i < txtList.size(); i++) {
			System.out.print(txtList.get(i) + " ");
		}
		System.out.println();
	}
	public void copytxtList(ArrayList <String> txtL) {
		txtList.clear();
		for(int i = 0; i < txtL.size(); i++) {
			txtList.add(txtL.get(i));
		}
	}

	public void copynumList(ArrayList <Double> numL) {
		numList.clear();
		for(int i = 0; i < numL.size(); i++) {
			numList.add(numL.get(i));
		}
	}

	public Object getValue() {
		if (type.equals("txt")) {
			return txt;
		} else if (type.equals("num")) {
			return num;
		} else if (type.equals("txtList")) {
			return txtList;
		} else if (type.equals("numList")) {
			return numList;
		} else if (type.equals("bool")) {
			return bool;
		}
		return null;
	}

    public String type() {
        if (type.equals("numLst") || type.equals("txtLst")) {
            return type.substring(0, 3) + "[]";
        } else {
            return type;
        }
    }

    public String toString() {
        String rtn = "";
        int lstSize;
        if (type.equals("txt")) {
			rtn = "\"" + txt + "\"";
		} else if (type.equals("num")) {
			rtn = Double.toString(num);
		} else if (type.equals("txtList")) {
			rtn = "[";
            lstSize = txtList.size();
            for (int i = 0; i < lstSize; i++) {
                rtn += "\"" + txtList.get(i) + "\", ";
            }
            rtn = rtn.substring(0, rtn.length() - 2) + "]";
		} else if (type.equals("numList")) {
			rtn = "[";
            lstSize = txtList.size();
            for (int i = 0; i < lstSize; i++) {
                rtn += txtList.get(i) + ", ";
            }
            rtn = rtn.substring(0, rtn.length() - 2) + "]";
		} else if (type.equals("bool")) {
			if (bool) {
                rtn = "true";
            } else {
                rtn = "false";
            }
		}
        return rtn;
    }

}