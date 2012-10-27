// $ANTLR 3.1.2 C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g 2010-03-23 23:31:41

	package eugene;
	import java.util.HashMap;
	import java.util.Stack;
	import java.util.Set;
	import java.util.Iterator;
	import java.util.Random;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class eugeneParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "PLUS", "MINUS", "MULT", "DIV", "LEFTP", "RIGHTP", "LEFTSBR", "RIGHTSBR", "LEFTCUR", "RIGHTCUR", "EQUALS", "UNDERS", "SEMIC", "COMMA", "DOT", "NUM", "INT", "BOOLEAN", "IMAGE", "PROPERTY", "PART", "DEVICE", "RULE", "TXT", "ADDPROPS", "PRINT", "BEFORE", "AFTER", "WITH", "NOTWITH", "NEXTTO", "NOTCONTAINS", "CONTAINS", "NOTMORETHAN", "NEQUAL", "LTHAN", "GTHAN", "LEQUAL", "GEQUAL", "AND", "OR", "NOT", "ASSERT", "NOTE", "IF", "ELSE", "ON", "TRUE", "FALSE", "PERMUTE", "STRICT", "FLEXIBLE", "LETTER", "STRING", "NUMBER", "REAL", "DIGIT", "WS", "NEWLINE", "LINE_COMMENT", "ML_COMMENT"
    };
    public static final int NEXTTO=34;
    public static final int NOTMORETHAN=37;
    public static final int ADDPROPS=28;
    public static final int LETTER=56;
    public static final int PART=24;
    public static final int CONTAINS=36;
    public static final int LTHAN=39;
    public static final int EQUALS=14;
    public static final int NOT=45;
    public static final int AND=43;
    public static final int LEFTP=8;
    public static final int EOF=-1;
    public static final int IF=48;
    public static final int ML_COMMENT=64;
    public static final int LEQUAL=41;
    public static final int BOOLEAN=21;
    public static final int FLEXIBLE=55;
    public static final int COMMA=17;
    public static final int NOTE=47;
    public static final int IMAGE=22;
    public static final int GEQUAL=42;
    public static final int TXT=27;
    public static final int DEVICE=25;
    public static final int UNDERS=15;
    public static final int PLUS=4;
    public static final int DIGIT=60;
    public static final int DOT=18;
    public static final int LEFTSBR=10;
    public static final int NOTCONTAINS=35;
    public static final int WITH=32;
    public static final int RIGHTSBR=11;
    public static final int LEFTCUR=12;
    public static final int NEQUAL=38;
    public static final int STRICT=54;
    public static final int RULE=26;
    public static final int RIGHTP=9;
    public static final int LINE_COMMENT=63;
    public static final int ELSE=49;
    public static final int NUMBER=58;
    public static final int PERMUTE=53;
    public static final int ON=50;
    public static final int BEFORE=30;
    public static final int INT=20;
    public static final int NOTWITH=33;
    public static final int MINUS=5;
    public static final int MULT=6;
    public static final int AFTER=31;
    public static final int TRUE=51;
    public static final int ASSERT=46;
    public static final int PRINT=29;
    public static final int NUM=19;
    public static final int REAL=59;
    public static final int WS=61;
    public static final int NEWLINE=62;
    public static final int PROPERTY=23;
    public static final int GTHAN=40;
    public static final int OR=44;
    public static final int RIGHTCUR=13;
    public static final int DIV=7;
    public static final int SEMIC=16;
    public static final int FALSE=52;
    public static final int STRING=57;

    // delegates
    // delegators


        public eugeneParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public eugeneParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return eugeneParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g"; }


    	@Override
    	public void reportError(RecognitionException RecogEx) {
    		int line = input.LT(-1).getLine();
    		throw new IllegalArgumentException("@Error Line " + line + ": " + "Syntax error.");
    	}

    	//memory for definitions
    	public HashMap<String, String> propertyDefinitions = new HashMap<String, String>();
    	public HashMap<String, ArrayList<ArrayList<String>>> partDefinitions = new HashMap<String, ArrayList<ArrayList<String>>>();

    	//memory for values and instances
    	public HashMap<String, Part> partDeclarations = new HashMap<String, Part>();
    	public HashMap<String, Device> deviceDeclarations = new HashMap<String, Device>();
    	public HashMap<String, Rule> ruleDeclarations = new HashMap<String, Rule>();
    	public HashMap<String, Primitive> primitiveDeclarations = new HashMap<String, Primitive>();

    	boolean debug = false;
    	boolean checktemp = true;
    	boolean compListInside = false;
    	Stack<String> evaluate = new Stack<String>();
    	String typeList = "";
    	String partType = "";

    	//working variables for intermediate steps to hold contents
    	ArrayList<String> propertyListHolder = new ArrayList<String>();
    	ArrayList<Primitive> propertyValuesHolder = new ArrayList<Primitive>();
    	ArrayList<String> componentList = new ArrayList<String>();

    	//working lists to hold temporary elements for asserting and noting statements
    	ArrayList<Primitive> assertionList = new ArrayList<Primitive>();

    	ArrayList<Primitive> ifEvaluationList = new ArrayList<Primitive>();

    	//memory for asserting and noting rules
    	public HashMap<String, ArrayList<Primitive>> ruleAssertions = new HashMap<String, ArrayList<Primitive>>();
    	public HashMap<String, ArrayList<Primitive>> ruleNotes = new HashMap<String, ArrayList<Primitive>>();
    	public HashMap<String, ArrayList<String>> ruleAssertionViolations = new HashMap<String, ArrayList<String>>();
    	public HashMap<String, ArrayList<String>> ruleNoteViolations = new HashMap<String, ArrayList<String>>();

    	//holds the assert statement expression as key for ruleAssertions and ruleNotes
    	String assertStatement = "";

    	//stores the logic values evaluated by if statement and accroding to the values a statement will be
    	//executed or not in rule statement
    	Stack<String> ifValueStack = new Stack<String>();


    	//defines a property and its type, if not already defined
    	public void defineProperty(String type, String varname) {
    		if (propertyDefinitions.containsKey(varname)) {
    			printError("Property " + varname + " already exists.");
    		} else {
    			propertyDefinitions.put(varname, type);
    		}
    	}

    	//defines a part class if not already declared
    	public void definePart(String varname) {
    		if (!checkIfAlreadyDeclared(varname, true)) {
    			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    			ArrayList<String> propertyList = new ArrayList<String>();
    			ArrayList<String> imageBinding = new ArrayList<String>();
    			ArrayList<String> instanceList = new ArrayList<String>();
    			list.add(propertyList);
    			list.add(imageBinding);
    			list.add(instanceList);
    			partDefinitions.put(varname, list);
    		}
    	}

    	//declares primitive without instantiating it to any value
    	public void declarePrimitiveNoValue(String name, String type) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			Primitive p = new Primitive(name , type);
    			primitiveDeclarations.put(name, p);
    		}
    	}

    	//declares and instantiates a num primitive with a value
    	public void declarePrimitiveWithValueNum(String name, Primitive prim, int index) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			if (prim.type.equals("num")) {
    				Primitive p = new Primitive(name, "num");
    				p.num = prim.num;
    				primitiveDeclarations.put(name, p);
    			} else if (prim.type.equals("numList") && prim.index != -1) {
    				Primitive p = new Primitive(name, "num");
    				p.num = prim.numList.get(prim.index);
    			} else {
    				printError("Type mismatch. Type is " + prim.type + " but must be num.");
    			}
    		}
    	}

    	//declares and instantiates a txt primitive with a value
    	public void declarePrimitiveWithValueTxt(String name, Primitive prim, int index) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			if (prim.type.equals("txt")) {
    				Primitive p = new Primitive(name, "txt");
    				p.txt = prim.txt;
    				primitiveDeclarations.put(name, p);
    			} else if (prim.type.equals("txtList") && index != -1) {
    				Primitive p = new Primitive(name, "txt");
    				p.txt = prim.txtList.get(index);
    			} else {
    				printError("Type mismatch. Type is " + prim.type + " but must be txt.");
    			}
    		}
    	}

    	//declares and instantiates a txtList primitive with a txt value list
    	public void declarePrimitiveWithValueTxtList(String name, Primitive prim) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			if (prim.type.equals("txtList")) {
    				Primitive p = new Primitive(name, "txtList");
    				p.txtList.addAll(prim.txtList);
    				primitiveDeclarations.put(name, p);
    			} else {
    				printError("Type mismatch. Type is " + prim.type + " but must be txtList.");
    			}
    		}
    	}

    	//declares and instantiates a numList primitive with a num value list
    	public void declarePrimitiveWithValueNumList(String name, Primitive prim) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			if (prim.type.equals("numList")) {
    				Primitive p = new Primitive(name, "numList");
    				p.numList.addAll(prim.numList);
    				primitiveDeclarations.put(name, p);
    			} else {
    				printError("Type mismatch. Type is " + prim.type + " but must be numList.");
    			}
    		}
    	}

    	//declares and instantiates a boolean primitive with a boolean value
    	public void declarePrimitiveWithValueBool(String name, Primitive prim) {
    		if (!checkIfAlreadyDeclared(name, true)) {
    			if (prim.type.equals("bool")) {
    				Primitive p = new Primitive(name, "bool");
    				p.bool = prim.bool;
    				primitiveDeclarations.put(name, p);
    			} else {
    				printError("Type mismatch. Type is " + prim.type + " but must be numList.");
    			}
    		}
    	}

    	//adds property to the list which will be instantiated for each part through instPropertyList(typename)
    	public void addToPropertyListHolder(String prop) {
    		if (!propertyDefinitions.containsKey(prop)) {
    			printError("Property " + prop + " does not exist.");
    		} else {
    			if(propertyDefinitions.get(prop).equals("txtList")) {
    				typeList = "txt";
    			} else if(propertyDefinitions.get(prop).equals("numList")) {
    				typeList = "num";
    			}
    			propertyListHolder.add(prop);
    		}
    	}

    	//adds values to the corresponding property
    	public void addToPropertyValuesHolder(String prop, Primitive p, int index) {
    		if (propertyDefinitions.get(prop).equals(p.type)) {
    			propertyValuesHolder.add(p);
    		} else if(p.type.equals("txtList") && index != -1 && p.type.equals("txt")) {
    			p.index = index;
    			propertyValuesHolder.add(p);
    		} else if(p.type.equals("numList") && index != -1 && p.type.equals("num")) {
    			p.index = index;
    			propertyValuesHolder.add(p);
    		} else {
    			printError("Expected a value of type " + propertyDefinitions.get(prop) + " for property " + prop + ".");
    		}
    	}

    	//instantiates a list of properties that a pomponent can have
    	public void instPropertyList(String typename) {
    		String property;
    		for (int i = 0; i < propertyListHolder.size(); i++) {
    			property = propertyListHolder.get(i);
    			if (!propertyDefinitions.containsKey(property)) {
    				printError("Property " + property + " does not exist.");
    			} else {
    				partDefinitions.get(typename).get(0).add(property);
    			}
    		}
    		propertyListHolder.clear();
    	}

    	//binds a specific image path to a class or instance, not really useful by itself, needs to be used with Spectacles
    	public void bindImage(String name, String path) {
    		if(partDefinitions.containsKey(name)) {
    			partDefinitions.get(name).get(1).add(path.substring(1, path.length()-1));
    		} else if (partDeclarations.containsKey(name)) {
    			partDeclarations.get(name).imagePath = path.substring(1, path.length()-1);
    		} else if (deviceDeclarations.containsKey(name)) {
    			deviceDeclarations.get(name).imagePath = path.substring(1, path.length()-1);
    		} else {
    			printError("Part " + name + " does not exist.");
    		}
    	}

    	//declares an instance of a part
    	public void declareInstances(String classname, String varname) {
    		if (!(partDefinitions.containsKey(classname))) {
    			printError("Part " + classname + " does not exist.");
    		} else if (!checkIfAlreadyDeclared(varname, false) && !(propertyDefinitions.containsKey(varname))) {
    			Part part = new Part();
    			part.name = varname;
    			part.type = classname;
    			if (!partDefinitions.get(classname).get(1).isEmpty()) {
    				part.imagePath = partDefinitions.get(classname).get(1).get(0);
    			}
    			ArrayList<String> props = partDefinitions.get(classname).get(0);
    			for (int i = 0; i < props.size(); i++) {
    				String name = props.get(i);
    				Primitive prim = new Primitive(name, propertyDefinitions.get(name));
    				part.propertyValues.put(name, prim);
    			}
    			partDeclarations.put(varname, part);
    			partDefinitions.get(classname).get(2).add(varname);
    		}
    	}

    	//checks if the name has been defined in program
    	public boolean checkIfAlreadyDeclared(String name, boolean all) {
    		if (ruleDeclarations.containsKey(name)) {
    			printError(name + " has already been defined as a rule.");
    			return true;
    		} else if (partDeclarations.containsKey(name)) {
    			printError(name + " has already been defined of type " + partDeclarations.get(name).type + ".");
    			return true;
    		} else if (deviceDeclarations.containsKey(name)) {
    			printError(name + " has already been defined of type device.");
    			return true;
    		} else if (primitiveDeclarations.containsKey(name)) {
    			printError(name + " has already been defined of type " + primitiveDeclarations.get(name).type + ".");
    			return true;
    		} else if (propertyDefinitions.containsKey(name) && all) {
    			printError(name + " has aready been defined as a property.");
    		} else if (partDefinitions.containsKey(name) && all) {
    			printError(name + " has already been defined as a part class.");
    		}
    		return false;
    	}

    	//declares a rule by passing in the appropritate data types of the rule operands to declareRule method
    	public void checkToDeclareRule(Primitive leftPrim, String leftOp, Primitive rightPrim, String rightOp, String name, String leftInst, String rightInst, String operator) {
    		if (leftPrim != null && rightPrim != null) {
    			declareRule(name, leftInst, leftPrim, rightInst, rightPrim, operator);
    		} else {
    			if (leftPrim != null) {
    				declareRule(name, leftInst, leftPrim, rightOp, null, operator);
    			} else if (rightPrim != null) {
    				declareRule(name, leftOp, null, rightInst, rightPrim, operator);
    			} else {
    				declareRule(name, leftOp, null, rightOp, null, operator);
    			}
    		}
    	}

    	//declares a rule instance
    	public void declareRule(String rulename, String leftOp, Primitive leftOpP, String rightOp, Primitive rightOpP, String operator) {
    		if (!checkIfAlreadyDeclared(rulename, true)) {
    			Rule r = new Rule();
    			r.operator = operator;
                		r.name = rulename;
    			if ((partDeclarations.containsKey(leftOp) /*|| deviceDeclarations.containsKey(leftOp)*/) && (partDeclarations.containsKey(rightOp) /*|| deviceDeclarations.containsKey(rightOp)*/)) {
    				if (leftOpP != null && rightOpP != null) {
    					Part leftComp = partDeclarations.get(leftOp);
    					Part rightComp = partDeclarations.get(rightOp);
    					if (leftComp.propertyValues.containsKey(leftOpP.name) && rightComp.propertyValues.containsKey(rightOpP.name)) {
    						String typeleft = leftComp.propertyValues.get(leftOpP.name).type;
    						String typeright = rightComp.propertyValues.get(rightOpP.name).type;
    						if (typeleft.equals(typeright)) {
    							r.operand1 = leftOpP.getValue().toString();
    							r.operand2 = rightOpP.getValue().toString();
    							ruleDeclarations.put(rulename, r);
    						} else {
    							printError("Left and right property are not of the same type.\nLeft property type: " + typeleft + " and right property type: " + typeright);
    						}
    					} else {
    						printError("Either property " + leftOpP.name + " or property " + rightOpP.name + " are not " + "properties of part " + leftOp + " or part" + rightOp);
    					}
    				} else if (leftOpP == null && rightOpP == null) {
    					r.operand1 = leftOp;
    					r.operand2 = rightOp;
    					ruleDeclarations.put(rulename, r);
    				}
    			} else if (partDeclarations.containsKey(leftOp) && leftOpP != null && primitiveDeclarations.containsKey(rightOp)) {
    				r.operand1 = leftOpP.getValue().toString();
    				r.operand2 = primitiveDeclarations.get(rightOp).getValue().toString();
    				ruleDeclarations.put(rulename, r);
    			} else if (partDeclarations.containsKey(rightOp) && rightOpP != null && primitiveDeclarations.containsKey(leftOp)) {
    				r.operand1 = primitiveDeclarations.get(leftOp).getValue().toString();
    				r.operand2 = rightOpP.getValue().toString();
    				ruleDeclarations.put(rulename, r);
    			//for NOTCONTAINS
    			} else if (leftOp == null && partDeclarations.containsKey(rightOp)) {
    				r.operand1 = "";
    				r.operand2 = rightOp;
    				ruleDeclarations.put(rulename, r);
    			//for NOTMORETHAN
    			} else if (partDeclarations.containsKey(leftOp) && primitiveDeclarations.containsKey(rightOp)) {
    				r.operand1 = leftOp;
    				r.operand2 = primitiveDeclarations.get(rightOp).getValue().toString();
    				ruleDeclarations.put(rulename, r);
    			} else {
    				//printError("Either " + leftOp + " or " + rightOp + " or both are not either a part or device or primitive.");
    				printError(leftOp + " or " + rightOp + " might not have been declared.");
    			}
    		}
    	}

    	//sets the values of all properties in a part
    	public void setProperties(String classname, String name) {
    		ArrayList<String> listproperties = partDefinitions.get(classname).get(0);
    		Primitive p;
    		if (propertyValuesHolder.size() != listproperties.size()) {
    			printError("Expected " + listproperties.size() + " values for part " + classname + ".");
    		}
    		for (int i = 0; i < listproperties.size(); i++) {
    			p = partDeclarations.get(name).propertyValues.get(listproperties.get(i));
    			if (p.type == propertyValuesHolder.get(i).type) {
    				if (p.type.equals("num")) {
    					p.num = propertyValuesHolder.get(i).num;
    				} else if (p.type.equals("txt")) {
    					p.txt = propertyValuesHolder.get(i).txt;
    				} else if (p.type.equals("txtList")) {
    					p.copytxtList(propertyValuesHolder.get(i).txtList);
    				} else if (p.type.equals("numList")) {
    					p.copynumList(propertyValuesHolder.get(i).numList);
    				} else if (p.type.equals("bool")) {
    					p.bool = propertyValuesHolder.get(i).bool;
    				}
    			} else if (propertyValuesHolder.get(i).type.equals("numList") && p.type.equals("num") && propertyValuesHolder.get(i).index != -1) {
    				p.num = propertyValuesHolder.get(i).numList.get(propertyValuesHolder.get(i).index);
    			} else if (propertyValuesHolder.get(i).type.equals("txtList") && p.type.equals("txt") && propertyValuesHolder.get(i).index != -1) {
    				p.txt = propertyValuesHolder.get(i).txtList.get(propertyValuesHolder.get(i).index);
    			} else {
    				printError("Expected a value of type " + p.type + " for property " + p.name + ".");
    			}
    		}
    		propertyValuesHolder.clear();
    	}

    	//sets the values of specified properties in a part
    	public void setPropertiesDot(String classname, String name) {
    		ArrayList<String> listproperties = partDefinitions.get(classname).get(0);
    		String propertyName;
    		Primitive p;
    		for (int i = 0; i < propertyListHolder.size(); i++) {
    			propertyName = propertyListHolder.get(i);
    			if (propertyDefinitions.containsKey(propertyName)) {
    				p = partDeclarations.get(name).propertyValues.get(propertyName);
    				if (p.type.equals("num")) {
    					p.num = propertyValuesHolder.get(i).num;
    				} else if (p.type.equals("txt")) {
    					p.txt = propertyValuesHolder.get(i).txt;
    				} else if (p.type.equals("txtList")) {
    					p.copytxtList(propertyValuesHolder.get(i).txtList);
    				} else if (p.type.equals("numList")) {
    					p.copynumList(propertyValuesHolder.get(i).numList);
    				} else if (p.type.equals("bool")) {
    					p.bool = propertyValuesHolder.get(i).bool;
    				}
    			} else {
    				printError("Property " + propertyName + " does not exist for part " + classname + ".");
    			}
    		}
    		propertyListHolder.clear();
    		propertyValuesHolder.clear();
    	}

    	//declares an instance of a device
    	public void declareDevice(String deviceInst) {
    		if (!checkIfAlreadyDeclared(deviceInst, true)) {
    			Device d = new Device();
    			d.type = "Device";
    			d.name = deviceInst;
    			deviceDeclarations.put(deviceInst, d);
    		}
    	}

    	//instantiates all fields of the device
    	public void instDevice(String deviceInst){
    		if (!checkIfAlreadyDeclared(deviceInst, true)) {
    			Device d = new Device();
    			d.type = "Device";
    			d.name = deviceInst;
    			d.components.addAll(componentList);
    			componentList.clear();
    			deviceDeclarations.put(deviceInst, d);
    		}
    	}

    	//checks if a part instance has been defined
    	public void isPartDefined(String instance) {
    		if (!partDefinitions.containsKey(instance)) {
    			printError("Part " + instance + " does not exist.");
    		}
    	}

    	//adds part or device to component list of a device
    	public void addToComponentList(String compInst) {
    		if (!partDeclarations.containsKey(compInst) && !deviceDeclarations.containsKey(compInst)) {
    			printError(compInst + " does not exist.");
    		} else {
    			componentList.add(compInst);
    		}
    	}

    	//gets the property values of a component list of a device
    	public Primitive getDevicePropertyValue(String inst, String prop, int index) {
    		if (!deviceDeclarations.containsKey(inst)) {
    			printError("Device " + inst + " does not exist.");
    		} else if (!propertyDefinitions.containsKey(prop)) {
    			printError("Property " + prop + " does not exist.");
    		} else {
    			Device d = deviceDeclarations.get(inst);
    			ArrayList<Primitive> listOfPrimitives = getPartList(d, prop);
    			Primitive p = new Primitive();
    			String property = propertyDefinitions.get(prop);
    			if (property.equals("txtList") || property.equals("numList")) {
    				p.type = propertyDefinitions.get(prop);
    			} else {
    				p.type = propertyDefinitions.get(prop) + "List";
    			}
    			for (int i = 0; i < listOfPrimitives.size(); i++) {
    				if (p.type.equals("txtList")) {
    					if (property.equals("txtList")) {
    						p.txtList.addAll(listOfPrimitives.get(i).txtList);
    					} else {
    						p.txtList.add(listOfPrimitives.get(i).txt);
    					}
    				} else if (p.type.equals("numList")) {
    					if (property.equals("numList")) {
    						p.numList.addAll(listOfPrimitives.get(i).numList);
    					} else {
    						p.numList.add(listOfPrimitives.get(i).num);
    					}
    				}
    			}
    			return p;
    		}
    		return null;
    	}

    	//expands the component list of a device to the basic component parts and returns a list of the properties from all those expanded components
    	public ArrayList<Primitive> getPartList(Device d, String prop) {
    		ArrayList<Primitive> compLst = new ArrayList<Primitive>();
    		ArrayList<String> comps = d.components;
    		String comp;
    		for (int i = 0; i < comps.size(); i++) {
    			comp = comps.get(i);
    			if (partDeclarations.containsKey(comp)) {
    				compLst.add(partDeclarations.get(comp).propertyValues.get(prop));
    			} else {
    				compLst.addAll(getPartList(deviceDeclarations.get(comp), prop));
    			}
    		}
    		return compLst;
    	}

    	//returns a list of names of the expanded components of a device
    	public ArrayList<String> getPartList(Device d) {
    		ArrayList<String> compLst = new ArrayList<String>();
    		ArrayList<String> comps = d.components;
    		String comp;
    		for (int i = 0; i < comps.size(); i++) {
    			comp = comps.get(i);
    			if (partDeclarations.containsKey(comp)) {
    				compLst.add(comp);
    			} else {
    				compLst.addAll(getPartList(deviceDeclarations.get(comp)));
    			}
    		}
    		return compLst;
    	}

    	//right now only property is associated with parts, need to check later in devices
    	public Primitive getPropertyValue(String inst, String prop, Primitive p, int index) {
    		if (!partDeclarations.containsKey(inst)) {
    			printError("Part " + inst + " does not exist.");
    		} else if (!propertyDefinitions.containsKey(prop)){
    			printError("Property " + prop + " does not exist.");
    		} else {
    			Part part = partDeclarations.get(inst);
    			if (part == null) {
    				printError("Part " + inst + " does not exist.");
    			} else {
    				Primitive prim;
    				if (index != -1) {
    					if (part.propertyValues.get(prop).type.equals("txtList")) {
    						int size = part.propertyValues.get(prop).txtList.size();
    						if (size <= index) {
    							printError("Index out of bound exception: index " + index + " size: " + size);
    						} else {
    							prim = part.propertyValues.get(prop);
    							if (prim == null) {
    								printError("Property " + prop + " does not exist for part " + part.name + ".");
    							} else {
    								p.type = "txt";
    								p.txt = prim.txtList.get(index);
    								return prim;
    							}
    						}
    					} else if (part.propertyValues.get(prop).type.equals("numList")) {
    						int size = part.propertyValues.get(prop).numList.size();
    						if (size <= index) {
    							printError("Index out of bound exception: index " + index + " size: " + size);
    						} else {
    							prim = part.propertyValues.get(prop);
    							if (prim == null) {
    								printError("Property " + prop + " does not exist for part " + part.name + ".");
    							} else {
    								p.type = "num";
    								p.num = prim.numList.get(index);
    								return prim;
    							}
    						}
    					}
    				} else {
    					prim = part.propertyValues.get(prop);
    					if (prim == null) {
    						printError("Property " + prop + " does not exist for part " + part.name + ".");
    					} else {
    						p.type = prim.type;
    						if (prim.type.equals("txt")) {
    							p.txt = prim.txt;
    						} else if (prim.type.equals("num")) {
    							p.num = prim.num;
    						} else if (prim.type.equals("bool")) {
    							p.bool = prim.bool;
    						} else if (prim.type.equals("txtList")) {
    							p.txtList.addAll(prim.txtList);
    						} else if (prim.type.equals("numList")) {
    							p.numList.addAll(prim.numList);
    						}
    						return prim;
    					}
    					
    				}
    			}
    		}
    		return null;
    	}

    	//concatenates to assertStatement either a bracket or operator
    	public void addToAssertStatement(String s) {
    		assertStatement += s;
    	}

    	//concatenates assertStatement with the ruleName which will be used as key for either ruleAsserts hashmap or ruleNotes hashmap
    	public String getAssertStatementOperand(String ruleName) {
    		if (ruleDeclarations.containsKey(ruleName)) {
    			assertStatement += " " + ruleName;
    			return ruleName;
    		} else {
    			printError(ruleName + " is not a rule instance.");
    		}
    		return ruleName;
    	}

    	//adds operand to Assertion List
    	public void addToAssertionList(String opname, String type) {
    		Primitive p;
    		if (opname != null) {
    			p = new Primitive(opname, type);
    			assertionList.add(p);
    		}
    	}

    	//clears the current assertionList after it has been stored in either the hashmap ruleNotes or ruleAssertions
    	public void clearAssertionList() {
    		if (assertionList.size() != 0) {
    			assertionList.clear();
    			assertStatement = "";
    		}
    	}

    	//stores the current assertionList in either ruleNotes or ruleAssertions, depending what grammar method is called
    	public void storeAssertList(boolean assertCheck) {
    		if (!assertionList.isEmpty()) {
    			ArrayList<Primitive> list = new ArrayList<Primitive>();
    			list.addAll(assertionList);
    			if (assertCheck) {
    				ruleAssertions.put(assertStatement, list);
    			} else {
    				ruleNotes.put(assertStatement, list);
    			}
    		}
    	}

    	//asserts or notes a rule or combination of rules when a device is declared
    	public boolean AssertRule(Device d, ArrayList<Primitive> List, String key, boolean note, boolean ifcheck) {
    		if (!assertionList.isEmpty()) {
    			Stack<String> evaluationStack = new Stack<String>();
    			ArrayList<String> partList = getPartList(d);//list where all members are of class part
    			ArrayList<String> unopenedList = d.components; //list of devices or parts, not expanded like above
    			ArrayList<String> list;
    			for (int i = 0; i < List.size(); i++) {
    				Primitive subject = List.get(i);
    				if (subject.type.equals("operand")) {
    					evaluationStack.push(List.get(i).name);
    				} else {
    					String ruleName = evaluationStack.pop(), result = "", result2 = "", ruleName2 = "";
    					Rule rule2;
    					if (ruleName.equals("0") || ruleName.equals("1")) {
    						result = ruleName;
    					} else {
    						Rule rule = ruleDeclarations.get(ruleName);
    						//if both are devices, pass in just list consisting of devices possibly
    						if (deviceDeclarations.containsKey(rule.operand1) && deviceDeclarations.containsKey(rule.operand2)) {
    							result = rule.evaluate(null, unopenedList);
    						} else {
    							//if one of the operands is a device pass in both lists else if none is a device
    							//it means they can only be parts and pass just the component list
    							if (deviceDeclarations.containsKey(rule.operand1) || deviceDeclarations.containsKey(rule.operand2)) {
    							} else {
    								result = rule.evaluate(partList, null);
    							}
    						}
    					}
    					if (subject.name.equals("NOT")) {
    						if (result.equals("0")) {
    							result = "1";
    						} else {
    							result = "0";
    						}
    						evaluationStack.push(result);
    					} else {
    						ruleName2 = evaluationStack.pop();
    						if (ruleName2.equals("0") || ruleName2.equals("1")) {
    							result2 = ruleName2;
    						} else {
    							rule2 = ruleDeclarations.get(ruleName2);
    							if (deviceDeclarations.containsKey(rule2.operand1) && deviceDeclarations.containsKey(rule2.operand2)) {
    								result2 = rule2.evaluate(null, unopenedList);
    							} else {
    								//currently cannot compare a part to a device
    								if (deviceDeclarations.containsKey(rule2.operand1) || deviceDeclarations.containsKey(rule2.operand2)) {
    								//to be implemented maybe
    								} else {
    									result2 = rule2.evaluate(partList, null);
    								}
    							}
    						}
    						evaluationStack.push(evaluateValue(result, result2, subject.name));
    					}
    				}
    				if (!evaluationStack.peek().equals("0") && !evaluationStack.peek().equals("1") && ruleDeclarations.containsKey(evaluationStack.peek())) {
    					Rule rule = ruleDeclarations.get(evaluationStack.pop());
    					String result = "";
    					if (deviceDeclarations.containsKey(rule.operand1) && deviceDeclarations.containsKey(rule.operand2)) {
    						result = rule.evaluate(null, unopenedList);
    						evaluationStack.push(result);
    					} else {
    						//currently cannot compare a part to a device
    						if (deviceDeclarations.containsKey(rule.operand1) || deviceDeclarations.containsKey(rule.operand2)) {
    						//maybe to be implemented
    						} else {
    							result = rule.evaluate(partList, null);
    							evaluationStack.push(result);
    						}
    					}
    				}
    			}
    			if (evaluationStack.peek().equals("0") && note && !ifcheck) {
    				String s = evaluationStack.pop();
    				System.out.println("Warning, the Note statement: ("+ key + ") has been violated on device instance " + d.name);
    				return false;
    			} else if (evaluationStack.peek().equals("0") && !note && !ifcheck) {
    				String s = evaluationStack.pop();
    				return false;
    			} else if (evaluationStack.peek().equals("1")) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    		return false;
    	}

    	//iterates through all existing rules and checks if the created device meets the criteria
    	//if a rule in assert list is not met by the device, an excpetion is thrown and program terminates
    	//if a rule in note list is not met by the device, a warning statement is printed out
    	public void applyRulesToDevice(String deviceInst) {
    		Set<String> setAssert = ruleAssertions.keySet();
    		Set<String> setNote = ruleNotes.keySet();
    		Iterator<String> iAssert = setAssert.iterator();
    		Iterator<String> iNote = setNote.iterator();
    		String key = "";
    		while (iNote.hasNext()) {
    			key = iNote.next();
    			boolean b = AssertRule(deviceDeclarations.get(deviceInst), ruleNotes.get(key), key, true, false);
    			if (!b) {
    				if (!ruleNoteViolations.containsKey(deviceInst)) {
    					ruleNoteViolations.put(deviceInst, new ArrayList<String>());					
    				}
    				ruleNoteViolations.get(deviceInst).add(key);
    			}
    		}
    		while (iAssert.hasNext()) {
    			key = iAssert.next();
    			boolean b = AssertRule(deviceDeclarations.get(deviceInst), ruleAssertions.get(key), key, false, false);
    			if (!b) {
    				if (!ruleAssertionViolations.containsKey(deviceInst)) {
    					ruleAssertionViolations.put(deviceInst, new ArrayList<String>());					
    				}
    				ruleAssertionViolations.get(deviceInst).add(key);
    				printError("Error, the Assert statement: ("+ key + ") has been violated on device instance " + deviceInst);
    			}
    		}
    	}

    	//evaluates the logical value of two truth statements. Used by AssertRule method
    	public String evaluateValue(String result, String result2, String operator) {
    		if (operator.equals("AND")) {
    			if (result.equals("0") || result2.equals("0")) {
    				return "0";
    			} else {
    				return "1";
    			}
    		}
    		if (operator.equals("OR")) {
    			if (result.equals("0") && result2.equals("0")) {
    				return "0";
    			} else {
    				return "1";
    			}
    		}
    		return "null";
    	}

    	//assigns value to an address and used in rule statement
    	public void valueAssignment(Primitive primVariable, int index, Primitive pRight) {
    		if (primVariable.type.equals(pRight.type)) {
    			if (primVariable.type.equals("num")) {
    				primVariable.num = pRight.num;
    			} else if (primVariable.type.equals("txt")) {
    				primVariable.txt = pRight.txt;
    			} else if (primVariable.type.equals("bool")) {
    				primVariable.bool = pRight.bool;
    			} else if (primVariable.type.equals("numList")) {
    				primVariable.numList.clear();
    				primVariable.numList.addAll(pRight.numList);
    			} else if (primVariable.type.equals("txtList")) {
    				primVariable.txtList.clear();
    				primVariable.txtList.addAll(pRight.txtList);
    			}
    		} else {
    			if (index != -1) {
    				if (primVariable.type.equals("numList") && pRight.type.equals("num")) {
    					primVariable.numList.set(index, pRight.num);
    				} else if (primVariable.type.equals("txtList") && pRight.type.equals("txt")) {
    					primVariable.txtList.set(index, pRight.txt);
    				} else {
    					printError("Incompatible types. Left variable type is " + primVariable.type + " and  instantiated type of value is " + pRight.type);
    				}
    			}
    		}
    	}

    	//sets first expression in print statement
    	public String setStatementPrint(Primitive p) {
    		if (p != null) {
    			if (p.index != -1 && p.type.equals("numList")) {
    				return p.numList.get(p.index).toString();
    			} else if (p.index != -1 && p.type.equals("txtList")) {
    				return p.txtList.get(p.index);
    			} else {
    				return p.getValue().toString();
    			}
    		}
    		return "";
    	}

    	//prints the concatenated string statment
    	public void print(String str) {
    		if (str != null) {
    			System.out.println(str);
    		} else {
    			System.out.println("null");
    		}
    	}

    	//copies the values of a primitive to a newly created Primitive
    	public Primitive copyPrimitive(Primitive source) {
    		Primitive destination = new Primitive();
    		destination.index = source.index;
    		if (source.type.equals("num")) {
    			destination.type = "num";
    			destination.num = source.num;
    		} else if (source.type.equals("txt")) {
    			destination.type = "txt";
    			destination.txt = source.txt;
    		} else if (source.type.equals("numList")) {
    			destination.type = "numList";
    			destination.numList.clear();
    			destination.numList.addAll(source.numList);
    		} else if (source.type.equals("txtList")) {
    			destination.type = "txtList";
    			destination.txtList.clear();
    			destination.txtList.addAll(source.txtList);
    		} else if (source.type.equals("bool")) {
    			destination.type = "bool";
    			destination.bool = source.bool;
    		}
    		return destination;
    	}

    	//does multiplication or division on a primitive, used by grammar rule multExpr
    	public void doMultDivOp(Primitive source, Primitive destination, String op) {
    		if (source.type.equals("num")) {
    			if (op.equals("*")) {
    				destination.num *= source.num;
    			} else {
    				if (source.num != 0) {
    					destination.num /= source.num;
    				} else {
    					printError("Division by zero.");
    				}
    			}
    		}
    	}

    	//does addition or subtraction on a primitive, used by grammar rule expr
    	public void doMinPlusOp(Primitive source, Primitive destination, String op) {
    		if (op.equals("+")) {
    			if (source.type.equals("num")) {
    				destination.num += source.num;
    			} else if (source.type.equals("numList")) {
    				destination.numList.addAll(source.numList);
    			} else if (source.type.equals("txtList")) {
    				destination.txtList.addAll(source.txtList);
    			} else if (source.type.equals("txt")) {
    				destination.txt += source.txt;
    			}
    		} else if (op.equals("-")) {
    			if (source.type.equals("num")) {
    				destination.num -= source.num;
    			}
    		}
    	}

    	//method used to collect individual members in declaration, used by grammar rule list
    	public void addToListPrim(Primitive p, Primitive listPrim) {
    		if (p.type.equals(typeList)) {
    			if (typeList.equals("txt")) {
    				listPrim.txtList.add(p.txt);
    			} else if(typeList.equals("num")) {
    				listPrim.numList.add(p.num);
    			}
    		} else {
    			printError("Type mismatch. List type is " + typeList + " and instantiated primitive type is " + p.type);
    		}
    	}

    	//validates if expression associated with rules on devices, used by the grammar rule ifStatement
    	public boolean validateIfStatement(ArrayList<Device> dList) {
    		boolean c;
    		for (int i = 0; i < dList.size(); i++) {
    			c = AssertRule(dList.get(i), assertionList, assertStatement, false, true);
    			if (!c) {
    				ifValueStack.push("0");
    				return false;
    			}
    		}
    		ifValueStack.push("1");
    		return true;
    	}

    	public boolean evaluateRelationalExpression(ArrayList<Primitive> list) {
    		if (list.get(0).type.equals("num")) {
    			return processNums(list);
    		} else if (list.get(0).type.equals("txt")) {
    			return processTxt(list);
    		} else if (list.get(0).type.equals("bool")) {
    			return processBool(list);
    		} else if (list.get(0).type.equals("txtList") || list.get(0).type.equals("numList")) {
    			return processList(list);
    		}
    		return false;
    	}

    	public boolean processNums(ArrayList<Primitive> list) {
    		if (list.get(list.size()-1).name.equals("<")) {
    			return compareLessThan(list.get(0).num, list.get(1).num);
    		} else if (list.get(list.size()-1).name.equals(">")) {
    			return compareGreaterThan(list.get(0).num, list.get(1).num);
    		} else if (list.get(list.size()-1).name.equals("<=")) {
    			return (!compareGreaterThan(list.get(0).num, list.get(1).num));
    		} else if (list.get(list.size()-1).name.equals(">=")) {
    			return (!compareLessThan(list.get(0).num, list.get(1).num));
    		} else if (list.get(list.size()-1).name.equals("==")) {
    			return compareEquals(list.get(0).num, list.get(1).num);
    		} else if (list.get(list.size()-1).name.equals("!=")) {
    			return (!compareEquals(list.get(0).num, list.get(1).num));
    		}
    		return false;
    	}

    	public boolean processTxt(ArrayList<Primitive> list) {
    		if (list.get(list.size()-1).name.equals("==")) {
    			return list.get(0).txt.equals(list.get(1).txt);
    		} else if (list.get(list.size()-1).name.equals("!=")) {
    			return !(list.get(0).txt.equals(list.get(1).txt));
    		} else if (list.get(list.size()-1).name.equals("<")) {
    			if (list.get(0).txt.compareTo(list.get(1).txt) < 0) {
    				return true;
    			} else {
    				return false;
    			}
    		} else if (list.get(list.size()-1).name.equals(">")) {
    			if (list.get(0).txt.compareTo(list.get(1).txt) > 0) {
    				return true;
    			} else {
    				return false;
    			}
    		} else if (list.get(list.size()-1).name.equals(">=")) {
    			if (list.get(0).txt.compareTo(list.get(1).txt) >= 0) {
    				return true;
    			} else {
    				return false;
    			}
    		} else if (list.get(list.size()-1).name.equals("<=")) {
    			if (list.get(0).txt.compareTo(list.get(1).txt) <= 0) {
    				return true;
    			} else {
    				return false;
    			}
    		}
    		return false;
    	}

    	public boolean processBool(ArrayList<Primitive> list) {
    		if (list.get(list.size()-1).name.equals("==")) {
    			if (list.get(0).bool == list.get(1).bool) {
    				return true;
    			} else {
    				return false;
    			}
    		} else if (list.get(list.size()-1).name.equals("!=")) {
    			if (list.get(0).bool != list.get(1).bool) {
    				return true;
    			} else {
    				return false;
    			}
    		} else if (list.size() == 1) {
    			if (list.get(0).bool) {
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			printError("Cannot evaluate for boolean types this operator " + list.get(list.size()-1).name);
    		}
    		return false;
    	}

    	public boolean processList(ArrayList<Primitive> list) {
    		if (list.get(list.size()-1).name.equals("==")) {
    			return compareEqualsLists(list);
    		} else if (list.get(list.size()-1).name.equals("!=")) {
    			return !compareEqualsLists(list);
    		}
    		return false;
    	}

    	public boolean compareLessThan(double num1, double num2) {
    		if (num1 < num2) {
    			return true;
    		}
    		return false;
    	}

    	public boolean compareGreaterThan(double num1, double num2) {
    		if (num1 > num2) {
    			return true;
    		}
    		return false;
    	}

    	public boolean compareEquals(double num1, double num2) {
    		if (num1 == num2) {
    			return true;
    		}
    		return false;
    	}

    	public boolean compareEqualsLists(ArrayList<Primitive> list) {
    		if (list.get(0).type.equals("txtList")) {
    			if (list.get(0).txtList.size() != list.get(1).txtList.size()) {
    				return false;
    			}
    			for (int i = 0; i < list.get(0).txtList.size(); i++) {
    				if (!list.get(0).txtList.get(i).equals(list.get(1).txtList.get(i))) {
    					return false;
    				}
    			}
    			return true;
    		} else if (list.get(0).type.equals("numList")) {
    			if (list.get(0).numList.size() != list.get(1).numList.size()) {
    				return false;
    			}
    			for (int i = 0; i < list.get(0).numList.size(); i++) {
    				if (!list.get(0).numList.get(i).equals(list.get(1).numList.get(i))) {

    					return false;
    				}
    			}
    			return true;
    		}
    		return false;
    	}

    	public boolean evaluateExpression(boolean result1, boolean result2, String logicalOperator) {
    		if (logicalOperator.equals("AND")) {
    			return (result1 && result2);
    		} else if (logicalOperator.equals("OR")) {
    			return (result1 || result2);
    		}
    		return false;
    	}

    	public boolean validateIfStatementExp() {
    		Stack<String> stack = new Stack<String>();
    		for (int i = 0; i < ifEvaluationList.size(); i++) {
    			if (ifEvaluationList.get(i).name.equals("operand")) {
    				stack.push(ifEvaluationList.get(i).txt);
    			} else {
    				String operator = ifEvaluationList.get(i).txt;
    				String op1 = stack.pop();
    				if (operator.equals("NOT")) {
    					if (op1.equals("1")) {
    						stack.push("0");
    					} else {
    						stack.push("1");
    					}
    				} else {
    					String op2 = stack.pop();
    					if (operator.equals("AND")) {
    						if (op1.equals("1") && op2.equals("1")) {
    							stack.push("1");
    						} else {
    							stack.push("0");
    						}
    					} else if (operator.equals("OR")) {
    						if (op1.equals("0") && op2.equals("0")) {
    							stack.push("0");
    						} else {
    							stack.push("1");
    						}
    					}
    				}
    			}
    		}
    		String check = stack.pop();
    		if (check.equals("1")) {
    			return true;
    		}
    		return false;
    	}

    	public boolean applyRulesToDevicesPermute(Device deviceInst, String degree) {
    		HashMap<String, ArrayList<Primitive>> ruleMap = new HashMap<String, ArrayList<Primitive>>();
    		boolean ruleMet = true, note = true;
    		
    		if(degree.equals("strict")) {
    			ruleMap = ruleAssertions;
    			note = false;
    		} else if(degree.equals("flexible")) {
    			ruleMap = ruleNotes;
    		} else
    			return false;
    		
    		Set<String> setKeys = ruleMap.keySet();
    		
    		Iterator<String> it = setKeys.iterator();
    		String key = "";
    		
    		while(it.hasNext()) {
    			key = it.next();
    			ruleMet = AssertRule(deviceInst, ruleMap.get(key), key, note, true);
    			if(!ruleMet)
    				return false;
    		}
    		return true;
    	}
    	public void permute(String device, int cap, String degree) {
    		ArrayList<String> compList = deviceDeclarations.get(device).components;
    		ArrayList<ArrayList<String>> compPool = new ArrayList<ArrayList<String>>();
    		ArrayList<ArrayList<String>> variations = new ArrayList<ArrayList<String>>();
    		int compListSize = compList.size();
    		int compPoolSize;
    		int variationsSize = 1;
    		ArrayList<Integer> restSizes = new ArrayList<Integer>();
    		int prevSize;
    		
    		for (int i = 0; i < compListSize; i++) {
    			compPool.add(new ArrayList<String>());
    			if (partDeclarations.containsKey(compList.get(i))) {
    				compPool.get(i).addAll(partDefinitions.get(partDeclarations.get(compList.get(i)).type).get(2));
    				variationsSize *= compPool.get(i).size();
    			} else {
    				compPool.get(i).add(compList.get(i));
    			}
    		}
    		
    		prevSize = variationsSize;
    		
    		for (int i = 0; i < compListSize; i++) {
    			restSizes.add(new Integer(prevSize/compPool.get(i).size()));
    			prevSize = restSizes.get(i);
    		}
    		for (int i = 0; i < variationsSize; i++) {
    			variations.add(new ArrayList<String>());
    		}
    		compPoolSize = compPool.size();
    		for (int i = 0; i < variationsSize; i++) {
    			for (int j = 0; j < compPoolSize; j++) {
    				variations.get(i).add(j, compPool.get(j).get((i/restSizes.get(j))%compPool.get(j).size()));
    			}
    		}
    		if(cap == -1) {//want all devices that are either are either not "stricly" violating, "flexible violating" or neither violating rules
    			if(degree.equals("") || degree.equals("strict") || degree.equals("flexible")) {
    				findDegreePermutations(device, degree, variationsSize, variations);
    			} else {
    				printError(degree + " is not a valid input of measuring degree for function permute.");
    			}
    		} else if(cap >= 0) {//want cap number of devices that are either not "stricly" violating, "flexible violating" or neither violating rules
    			if(degree.equals("") || degree.equals("strict") || degree.equals("flexible")) {
    				findDegreeCapPermutations(device, degree, cap, variationsSize, variations);
    			} else {
    				printError(degree + " is not a valid input of measuring degree for function permute.");
    			}
    		}
    	}

    	public void findDegreePermutations(String device, String degree, int variationsSize, ArrayList<ArrayList<String>> variations) {
            Device d;
    		int count = 1;

    		for (int i = 1; i <= variationsSize; i++) {
    			d = new Device();
    			d.type = "Device";
    			d.components.addAll(variations.get(i-1));
    			if (deviceDeclarations.containsKey(d.name)) {
    				printError(d.name + " already exists.");
    			}
    			if((!degree.equals("") && applyRulesToDevicesPermute(d, degree)) || degree.equals("")) {
    					d.name = device + "_" + Integer.toString(count);
    					printDebug(d.name + " " + d.components);
    					deviceDeclarations.put(d.name, d);
    					count++;
    			} 
    		}
    	}
    	
    	public void findDegreeCapPermutations(String device, String degree, int cap, int variationsSize, ArrayList<ArrayList<String>> variations) {
    		Device d;
    		Random randomGenerator = new Random();
    		int randomNum, i=1, loopcheck = 0;
    		
    		ArrayList<String> checkList = new ArrayList<String>();
    		for(int j=0; j < variationsSize; j++) {
    			checkList.add("0");
    		}
    		
    		while(i <= cap) {
    			d = new Device();
    			d.name = device + "_" + Integer.toString(i);
    			d.type = "Device";
    			randomNum = randomGenerator.nextInt(variationsSize);
    			d.components.addAll(variations.get(randomNum));
    					
    			if(checkList.get(randomNum).equals("1") || (!degree.equals("") && !applyRulesToDevicesPermute(d, degree))) {
    				checkList.set(randomNum, "1");
    				loopcheck++;
    				if(loopcheck >= cap) {
    					if(checkList.indexOf("0") == -1) {//means have looked at all variations
    						break;
    					}
    				}
    				continue;
    			} else {
    				checkList.set(randomNum, "1");
    				if (deviceDeclarations.containsKey(d.name)) {
    					printError(d.name + " already exists.");
    				}
    				deviceDeclarations.put(d.name, d);
    				i++;
    				printDebug(d.name + " " + d.components);
    					
    			}
    						
    		}
    	}
    	
    	public String printDevices(Device d, int amount) {
    		int count = 1;
    		boolean all = false;
    		String str = "";
    		
    		if(amount == -1)
    			all = true;
    			
    		while(count <= amount || all) {
    			String deviceName = d.name + "_" + Integer.toString(count);
    			if(deviceDeclarations.containsKey(deviceName)){
    				Device dPermuted = deviceDeclarations.get(deviceName);
    				str += dPermuted.name + " " + dPermuted.components + "\n";
    				count++;
    			} else
    				break;
    		}
    		
    		return str;
    	
    	}
    	
    	
    	public void printDebug(Object message) {
    		if (debug) {
    			int line = input.LT(-1).getLine();
    			System.out.println("@Debug Line " + line + ": " + message);
    		}
    	}

    	public void printError(Object message) {
    		int line = input.LT(-1).getLine();
    		throw new IllegalArgumentException("@Error Line " + line + ": " + message);
    	}


    public static class prog_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prog"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1337:1: prog : ( statement )+ EOF ;
    public final eugeneParser.prog_return prog() throws RecognitionException {
        eugeneParser.prog_return retval = new eugeneParser.prog_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF2=null;
        eugeneParser.statement_return statement1 = null;


        Object EOF2_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1338:2: ( ( statement )+ EOF )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1338:4: ( statement )+ EOF
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1338:4: ( statement )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==LEFTP||LA1_0==LEFTSBR||LA1_0==NUM||(LA1_0>=BOOLEAN && LA1_0<=TXT)||LA1_0==PRINT||(LA1_0>=ASSERT && LA1_0<=IF)||(LA1_0>=TRUE && LA1_0<=PERMUTE)||(LA1_0>=LETTER && LA1_0<=REAL)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1338:5: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_prog507);
            	    statement1=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_prog511); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "prog"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1342:1: statement : ( decl | declObjects | NUM numdecl SEMIC | TXT txtdecl SEMIC | TXT LEFTSBR RIGHTSBR txtlistdecl SEMIC | NUM LEFTSBR RIGHTSBR numlistdecl SEMIC | BOOLEAN booldecl SEMIC | print | permute | leftval= atom EQUALS rightval= expr SEMIC | ifStatement );
    public final eugeneParser.statement_return statement() throws RecognitionException {
        eugeneParser.statement_return retval = new eugeneParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NUM5=null;
        Token SEMIC7=null;
        Token TXT8=null;
        Token SEMIC10=null;
        Token TXT11=null;
        Token LEFTSBR12=null;
        Token RIGHTSBR13=null;
        Token SEMIC15=null;
        Token NUM16=null;
        Token LEFTSBR17=null;
        Token RIGHTSBR18=null;
        Token SEMIC20=null;
        Token BOOLEAN21=null;
        Token SEMIC23=null;
        Token EQUALS26=null;
        Token SEMIC27=null;
        eugeneParser.atom_return leftval = null;

        eugeneParser.expr_return rightval = null;

        eugeneParser.decl_return decl3 = null;

        eugeneParser.declObjects_return declObjects4 = null;

        eugeneParser.numdecl_return numdecl6 = null;

        eugeneParser.txtdecl_return txtdecl9 = null;

        eugeneParser.txtlistdecl_return txtlistdecl14 = null;

        eugeneParser.numlistdecl_return numlistdecl19 = null;

        eugeneParser.booldecl_return booldecl22 = null;

        eugeneParser.print_return print24 = null;

        eugeneParser.permute_return permute25 = null;

        eugeneParser.ifStatement_return ifStatement28 = null;


        Object NUM5_tree=null;
        Object SEMIC7_tree=null;
        Object TXT8_tree=null;
        Object SEMIC10_tree=null;
        Object TXT11_tree=null;
        Object LEFTSBR12_tree=null;
        Object RIGHTSBR13_tree=null;
        Object SEMIC15_tree=null;
        Object NUM16_tree=null;
        Object LEFTSBR17_tree=null;
        Object RIGHTSBR18_tree=null;
        Object SEMIC20_tree=null;
        Object BOOLEAN21_tree=null;
        Object SEMIC23_tree=null;
        Object EQUALS26_tree=null;
        Object SEMIC27_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1343:2: ( decl | declObjects | NUM numdecl SEMIC | TXT txtdecl SEMIC | TXT LEFTSBR RIGHTSBR txtlistdecl SEMIC | NUM LEFTSBR RIGHTSBR numlistdecl SEMIC | BOOLEAN booldecl SEMIC | print | permute | leftval= atom EQUALS rightval= expr SEMIC | ifStatement )
            int alt2=11;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1343:4: decl
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_decl_in_statement525);
                    decl3=decl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, decl3.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1344:4: declObjects
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_declObjects_in_statement532);
                    declObjects4=declObjects();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, declObjects4.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1345:4: NUM numdecl SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM5=(Token)match(input,NUM,FOLLOW_NUM_in_statement539); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM5_tree = (Object)adaptor.create(NUM5);
                    adaptor.addChild(root_0, NUM5_tree);
                    }
                    pushFollow(FOLLOW_numdecl_in_statement541);
                    numdecl6=numdecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, numdecl6.getTree());
                    SEMIC7=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement543); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC7_tree = (Object)adaptor.create(SEMIC7);
                    adaptor.addChild(root_0, SEMIC7_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1346:4: TXT txtdecl SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    TXT8=(Token)match(input,TXT,FOLLOW_TXT_in_statement550); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TXT8_tree = (Object)adaptor.create(TXT8);
                    adaptor.addChild(root_0, TXT8_tree);
                    }
                    pushFollow(FOLLOW_txtdecl_in_statement552);
                    txtdecl9=txtdecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtdecl9.getTree());
                    SEMIC10=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement554); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC10_tree = (Object)adaptor.create(SEMIC10);
                    adaptor.addChild(root_0, SEMIC10_tree);
                    }

                    }
                    break;
                case 5 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1347:4: TXT LEFTSBR RIGHTSBR txtlistdecl SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    TXT11=(Token)match(input,TXT,FOLLOW_TXT_in_statement561); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TXT11_tree = (Object)adaptor.create(TXT11);
                    adaptor.addChild(root_0, TXT11_tree);
                    }
                    LEFTSBR12=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_statement563); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR12_tree = (Object)adaptor.create(LEFTSBR12);
                    adaptor.addChild(root_0, LEFTSBR12_tree);
                    }
                    RIGHTSBR13=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_statement565); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR13_tree = (Object)adaptor.create(RIGHTSBR13);
                    adaptor.addChild(root_0, RIGHTSBR13_tree);
                    }
                    pushFollow(FOLLOW_txtlistdecl_in_statement567);
                    txtlistdecl14=txtlistdecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtlistdecl14.getTree());
                    SEMIC15=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement569); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC15_tree = (Object)adaptor.create(SEMIC15);
                    adaptor.addChild(root_0, SEMIC15_tree);
                    }

                    }
                    break;
                case 6 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1348:4: NUM LEFTSBR RIGHTSBR numlistdecl SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    NUM16=(Token)match(input,NUM,FOLLOW_NUM_in_statement576); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM16_tree = (Object)adaptor.create(NUM16);
                    adaptor.addChild(root_0, NUM16_tree);
                    }
                    LEFTSBR17=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_statement578); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR17_tree = (Object)adaptor.create(LEFTSBR17);
                    adaptor.addChild(root_0, LEFTSBR17_tree);
                    }
                    RIGHTSBR18=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_statement580); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR18_tree = (Object)adaptor.create(RIGHTSBR18);
                    adaptor.addChild(root_0, RIGHTSBR18_tree);
                    }
                    pushFollow(FOLLOW_numlistdecl_in_statement582);
                    numlistdecl19=numlistdecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, numlistdecl19.getTree());
                    SEMIC20=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement584); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC20_tree = (Object)adaptor.create(SEMIC20);
                    adaptor.addChild(root_0, SEMIC20_tree);
                    }

                    }
                    break;
                case 7 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1349:4: BOOLEAN booldecl SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    BOOLEAN21=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_statement591); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOLEAN21_tree = (Object)adaptor.create(BOOLEAN21);
                    adaptor.addChild(root_0, BOOLEAN21_tree);
                    }
                    pushFollow(FOLLOW_booldecl_in_statement593);
                    booldecl22=booldecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booldecl22.getTree());
                    SEMIC23=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement595); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC23_tree = (Object)adaptor.create(SEMIC23);
                    adaptor.addChild(root_0, SEMIC23_tree);
                    }

                    }
                    break;
                case 8 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1350:4: print
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_print_in_statement602);
                    print24=print();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, print24.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()){
                      				if (ifValueStack.peek() == "1") {
                      					print((print24!=null?print24.concat:null));
                      				}
                      			} else {
                      				print((print24!=null?print24.concat:null));
                      			}
                      		
                    }

                    }
                    break;
                case 9 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1360:4: permute
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_permute_in_statement613);
                    permute25=permute();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, permute25.getTree());

                    }
                    break;
                case 10 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1361:4: leftval= atom EQUALS rightval= expr SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_atom_in_statement620);
                    leftval=atom();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, leftval.getTree());
                    EQUALS26=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_statement622); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS26_tree = (Object)adaptor.create(EQUALS26);
                    adaptor.addChild(root_0, EQUALS26_tree);
                    }
                    pushFollow(FOLLOW_expr_in_statement626);
                    rightval=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, rightval.getTree());
                    SEMIC27=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_statement628); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC27_tree = (Object)adaptor.create(SEMIC27);
                    adaptor.addChild(root_0, SEMIC27_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					valueAssignment((leftval!=null?leftval.primVariable:null), (leftval!=null?leftval.index:0), (rightval!=null?rightval.p:null));
                      				}
                      			} else {
                      				valueAssignment((leftval!=null?leftval.primVariable:null), (leftval!=null?leftval.index:0), (rightval!=null?rightval.p:null));
                      			}
                      		
                    }

                    }
                    break;
                case 11 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1371:4: ifStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ifStatement_in_statement639);
                    ifStatement28=ifStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ifStatement28.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class decl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "decl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1375:1: decl : ( PROPERTY LETTER LEFTP TXT RIGHTP SEMIC | PROPERTY LETTER LEFTP TXT LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY tn= LETTER LEFTP NUM RIGHTP SEMIC | PROPERTY LETTER LEFTP NUM LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY LETTER LEFTP BOOLEAN RIGHTP SEMIC | PART LETTER LEFTP propertyList RIGHTP SEMIC | DEVICE LETTER LEFTP componentList RIGHTP SEMIC | IMAGE LEFTP name= LETTER COMMA path= STRING RIGHTP SEMIC | RULE name= LETTER LEFTP (leftOp= LETTER | leftOpP= exprP | ) operator (rightOp= LETTER | rightOpP= exprP ) RIGHTP SEMIC | NOTE LEFTP statementsOfAssertion RIGHTP SEMIC | ASSERT LEFTP statementsOfAssertion RIGHTP SEMIC | LETTER pfunctions );
    public final eugeneParser.decl_return decl() throws RecognitionException {
        eugeneParser.decl_return retval = new eugeneParser.decl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token tn=null;
        Token name=null;
        Token path=null;
        Token leftOp=null;
        Token rightOp=null;
        Token PROPERTY29=null;
        Token LETTER30=null;
        Token LEFTP31=null;
        Token TXT32=null;
        Token RIGHTP33=null;
        Token SEMIC34=null;
        Token PROPERTY35=null;
        Token LETTER36=null;
        Token LEFTP37=null;
        Token TXT38=null;
        Token LEFTSBR39=null;
        Token RIGHTSBR40=null;
        Token RIGHTP41=null;
        Token SEMIC42=null;
        Token PROPERTY43=null;
        Token LEFTP44=null;
        Token NUM45=null;
        Token RIGHTP46=null;
        Token SEMIC47=null;
        Token PROPERTY48=null;
        Token LETTER49=null;
        Token LEFTP50=null;
        Token NUM51=null;
        Token LEFTSBR52=null;
        Token RIGHTSBR53=null;
        Token RIGHTP54=null;
        Token SEMIC55=null;
        Token PROPERTY56=null;
        Token LETTER57=null;
        Token LEFTP58=null;
        Token BOOLEAN59=null;
        Token RIGHTP60=null;
        Token SEMIC61=null;
        Token PART62=null;
        Token LETTER63=null;
        Token LEFTP64=null;
        Token RIGHTP66=null;
        Token SEMIC67=null;
        Token DEVICE68=null;
        Token LETTER69=null;
        Token LEFTP70=null;
        Token RIGHTP72=null;
        Token SEMIC73=null;
        Token IMAGE74=null;
        Token LEFTP75=null;
        Token COMMA76=null;
        Token RIGHTP77=null;
        Token SEMIC78=null;
        Token RULE79=null;
        Token LEFTP80=null;
        Token RIGHTP82=null;
        Token SEMIC83=null;
        Token NOTE84=null;
        Token LEFTP85=null;
        Token RIGHTP87=null;
        Token SEMIC88=null;
        Token ASSERT89=null;
        Token LEFTP90=null;
        Token RIGHTP92=null;
        Token SEMIC93=null;
        Token LETTER94=null;
        eugeneParser.exprP_return leftOpP = null;

        eugeneParser.exprP_return rightOpP = null;

        eugeneParser.propertyList_return propertyList65 = null;

        eugeneParser.componentList_return componentList71 = null;

        eugeneParser.operator_return operator81 = null;

        eugeneParser.statementsOfAssertion_return statementsOfAssertion86 = null;

        eugeneParser.statementsOfAssertion_return statementsOfAssertion91 = null;

        eugeneParser.pfunctions_return pfunctions95 = null;


        Object tn_tree=null;
        Object name_tree=null;
        Object path_tree=null;
        Object leftOp_tree=null;
        Object rightOp_tree=null;
        Object PROPERTY29_tree=null;
        Object LETTER30_tree=null;
        Object LEFTP31_tree=null;
        Object TXT32_tree=null;
        Object RIGHTP33_tree=null;
        Object SEMIC34_tree=null;
        Object PROPERTY35_tree=null;
        Object LETTER36_tree=null;
        Object LEFTP37_tree=null;
        Object TXT38_tree=null;
        Object LEFTSBR39_tree=null;
        Object RIGHTSBR40_tree=null;
        Object RIGHTP41_tree=null;
        Object SEMIC42_tree=null;
        Object PROPERTY43_tree=null;
        Object LEFTP44_tree=null;
        Object NUM45_tree=null;
        Object RIGHTP46_tree=null;
        Object SEMIC47_tree=null;
        Object PROPERTY48_tree=null;
        Object LETTER49_tree=null;
        Object LEFTP50_tree=null;
        Object NUM51_tree=null;
        Object LEFTSBR52_tree=null;
        Object RIGHTSBR53_tree=null;
        Object RIGHTP54_tree=null;
        Object SEMIC55_tree=null;
        Object PROPERTY56_tree=null;
        Object LETTER57_tree=null;
        Object LEFTP58_tree=null;
        Object BOOLEAN59_tree=null;
        Object RIGHTP60_tree=null;
        Object SEMIC61_tree=null;
        Object PART62_tree=null;
        Object LETTER63_tree=null;
        Object LEFTP64_tree=null;
        Object RIGHTP66_tree=null;
        Object SEMIC67_tree=null;
        Object DEVICE68_tree=null;
        Object LETTER69_tree=null;
        Object LEFTP70_tree=null;
        Object RIGHTP72_tree=null;
        Object SEMIC73_tree=null;
        Object IMAGE74_tree=null;
        Object LEFTP75_tree=null;
        Object COMMA76_tree=null;
        Object RIGHTP77_tree=null;
        Object SEMIC78_tree=null;
        Object RULE79_tree=null;
        Object LEFTP80_tree=null;
        Object RIGHTP82_tree=null;
        Object SEMIC83_tree=null;
        Object NOTE84_tree=null;
        Object LEFTP85_tree=null;
        Object RIGHTP87_tree=null;
        Object SEMIC88_tree=null;
        Object ASSERT89_tree=null;
        Object LEFTP90_tree=null;
        Object RIGHTP92_tree=null;
        Object SEMIC93_tree=null;
        Object LETTER94_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1376:2: ( PROPERTY LETTER LEFTP TXT RIGHTP SEMIC | PROPERTY LETTER LEFTP TXT LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY tn= LETTER LEFTP NUM RIGHTP SEMIC | PROPERTY LETTER LEFTP NUM LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY LETTER LEFTP BOOLEAN RIGHTP SEMIC | PART LETTER LEFTP propertyList RIGHTP SEMIC | DEVICE LETTER LEFTP componentList RIGHTP SEMIC | IMAGE LEFTP name= LETTER COMMA path= STRING RIGHTP SEMIC | RULE name= LETTER LEFTP (leftOp= LETTER | leftOpP= exprP | ) operator (rightOp= LETTER | rightOpP= exprP ) RIGHTP SEMIC | NOTE LEFTP statementsOfAssertion RIGHTP SEMIC | ASSERT LEFTP statementsOfAssertion RIGHTP SEMIC | LETTER pfunctions )
            int alt5=12;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1376:4: PROPERTY LETTER LEFTP TXT RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PROPERTY29=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_decl654); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PROPERTY29_tree = (Object)adaptor.create(PROPERTY29);
                    adaptor.addChild(root_0, PROPERTY29_tree);
                    }
                    LETTER30=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl656); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER30_tree = (Object)adaptor.create(LETTER30);
                    adaptor.addChild(root_0, LETTER30_tree);
                    }
                    LEFTP31=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl658); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP31_tree = (Object)adaptor.create(LEFTP31);
                    adaptor.addChild(root_0, LEFTP31_tree);
                    }
                    TXT32=(Token)match(input,TXT,FOLLOW_TXT_in_decl660); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TXT32_tree = (Object)adaptor.create(TXT32);
                    adaptor.addChild(root_0, TXT32_tree);
                    }
                    RIGHTP33=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl662); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP33_tree = (Object)adaptor.create(RIGHTP33);
                    adaptor.addChild(root_0, RIGHTP33_tree);
                    }
                    SEMIC34=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl664); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC34_tree = (Object)adaptor.create(SEMIC34);
                    adaptor.addChild(root_0, SEMIC34_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					defineProperty("txt", (LETTER30!=null?LETTER30.getText():null));
                      				}
                      			} else {
                      				defineProperty("txt", (LETTER30!=null?LETTER30.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1386:4: PROPERTY LETTER LEFTP TXT LEFTSBR RIGHTSBR RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PROPERTY35=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_decl673); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PROPERTY35_tree = (Object)adaptor.create(PROPERTY35);
                    adaptor.addChild(root_0, PROPERTY35_tree);
                    }
                    LETTER36=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl675); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER36_tree = (Object)adaptor.create(LETTER36);
                    adaptor.addChild(root_0, LETTER36_tree);
                    }
                    LEFTP37=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl677); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP37_tree = (Object)adaptor.create(LEFTP37);
                    adaptor.addChild(root_0, LEFTP37_tree);
                    }
                    TXT38=(Token)match(input,TXT,FOLLOW_TXT_in_decl679); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TXT38_tree = (Object)adaptor.create(TXT38);
                    adaptor.addChild(root_0, TXT38_tree);
                    }
                    LEFTSBR39=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_decl681); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR39_tree = (Object)adaptor.create(LEFTSBR39);
                    adaptor.addChild(root_0, LEFTSBR39_tree);
                    }
                    RIGHTSBR40=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_decl683); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR40_tree = (Object)adaptor.create(RIGHTSBR40);
                    adaptor.addChild(root_0, RIGHTSBR40_tree);
                    }
                    RIGHTP41=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl685); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP41_tree = (Object)adaptor.create(RIGHTP41);
                    adaptor.addChild(root_0, RIGHTP41_tree);
                    }
                    SEMIC42=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl687); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC42_tree = (Object)adaptor.create(SEMIC42);
                    adaptor.addChild(root_0, SEMIC42_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					defineProperty("txtList", (LETTER36!=null?LETTER36.getText():null));
                      				}
                      			} else {
                      				defineProperty("txtList", (LETTER36!=null?LETTER36.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1396:4: PROPERTY tn= LETTER LEFTP NUM RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PROPERTY43=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_decl696); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PROPERTY43_tree = (Object)adaptor.create(PROPERTY43);
                    adaptor.addChild(root_0, PROPERTY43_tree);
                    }
                    tn=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl700); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    tn_tree = (Object)adaptor.create(tn);
                    adaptor.addChild(root_0, tn_tree);
                    }
                    LEFTP44=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl702); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP44_tree = (Object)adaptor.create(LEFTP44);
                    adaptor.addChild(root_0, LEFTP44_tree);
                    }
                    NUM45=(Token)match(input,NUM,FOLLOW_NUM_in_decl704); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM45_tree = (Object)adaptor.create(NUM45);
                    adaptor.addChild(root_0, NUM45_tree);
                    }
                    RIGHTP46=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl706); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP46_tree = (Object)adaptor.create(RIGHTP46);
                    adaptor.addChild(root_0, RIGHTP46_tree);
                    }
                    SEMIC47=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl708); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC47_tree = (Object)adaptor.create(SEMIC47);
                    adaptor.addChild(root_0, SEMIC47_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					defineProperty("num", (tn!=null?tn.getText():null));
                      				}
                      			} else {
                      				defineProperty("num", (tn!=null?tn.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1406:4: PROPERTY LETTER LEFTP NUM LEFTSBR RIGHTSBR RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PROPERTY48=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_decl717); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PROPERTY48_tree = (Object)adaptor.create(PROPERTY48);
                    adaptor.addChild(root_0, PROPERTY48_tree);
                    }
                    LETTER49=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl719); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER49_tree = (Object)adaptor.create(LETTER49);
                    adaptor.addChild(root_0, LETTER49_tree);
                    }
                    LEFTP50=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl721); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP50_tree = (Object)adaptor.create(LEFTP50);
                    adaptor.addChild(root_0, LEFTP50_tree);
                    }
                    NUM51=(Token)match(input,NUM,FOLLOW_NUM_in_decl723); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUM51_tree = (Object)adaptor.create(NUM51);
                    adaptor.addChild(root_0, NUM51_tree);
                    }
                    LEFTSBR52=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_decl725); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR52_tree = (Object)adaptor.create(LEFTSBR52);
                    adaptor.addChild(root_0, LEFTSBR52_tree);
                    }
                    RIGHTSBR53=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_decl727); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR53_tree = (Object)adaptor.create(RIGHTSBR53);
                    adaptor.addChild(root_0, RIGHTSBR53_tree);
                    }
                    RIGHTP54=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl729); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP54_tree = (Object)adaptor.create(RIGHTP54);
                    adaptor.addChild(root_0, RIGHTP54_tree);
                    }
                    SEMIC55=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl731); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC55_tree = (Object)adaptor.create(SEMIC55);
                    adaptor.addChild(root_0, SEMIC55_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					defineProperty("numList", (LETTER49!=null?LETTER49.getText():null));
                      				}
                      			} else {
                      				defineProperty("numList", (LETTER49!=null?LETTER49.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 5 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1416:4: PROPERTY LETTER LEFTP BOOLEAN RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PROPERTY56=(Token)match(input,PROPERTY,FOLLOW_PROPERTY_in_decl740); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PROPERTY56_tree = (Object)adaptor.create(PROPERTY56);
                    adaptor.addChild(root_0, PROPERTY56_tree);
                    }
                    LETTER57=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl742); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER57_tree = (Object)adaptor.create(LETTER57);
                    adaptor.addChild(root_0, LETTER57_tree);
                    }
                    LEFTP58=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl744); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP58_tree = (Object)adaptor.create(LEFTP58);
                    adaptor.addChild(root_0, LEFTP58_tree);
                    }
                    BOOLEAN59=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_decl746); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    BOOLEAN59_tree = (Object)adaptor.create(BOOLEAN59);
                    adaptor.addChild(root_0, BOOLEAN59_tree);
                    }
                    RIGHTP60=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl748); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP60_tree = (Object)adaptor.create(RIGHTP60);
                    adaptor.addChild(root_0, RIGHTP60_tree);
                    }
                    SEMIC61=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl750); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC61_tree = (Object)adaptor.create(SEMIC61);
                    adaptor.addChild(root_0, SEMIC61_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					defineProperty("bool", (LETTER57!=null?LETTER57.getText():null));
                      				}
                      			} else {
                      				defineProperty("bool", (LETTER57!=null?LETTER57.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 6 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1426:4: PART LETTER LEFTP propertyList RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PART62=(Token)match(input,PART,FOLLOW_PART_in_decl759); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PART62_tree = (Object)adaptor.create(PART62);
                    adaptor.addChild(root_0, PART62_tree);
                    }
                    LETTER63=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl761); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER63_tree = (Object)adaptor.create(LETTER63);
                    adaptor.addChild(root_0, LETTER63_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					definePart((LETTER63!=null?LETTER63.getText():null));
                      				}
                      			} else {
                      				definePart((LETTER63!=null?LETTER63.getText():null));
                      			}
                      		
                    }
                    LEFTP64=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl767); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP64_tree = (Object)adaptor.create(LEFTP64);
                    adaptor.addChild(root_0, LEFTP64_tree);
                    }
                    pushFollow(FOLLOW_propertyList_in_decl769);
                    propertyList65=propertyList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyList65.getTree());
                    RIGHTP66=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl771); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP66_tree = (Object)adaptor.create(RIGHTP66);
                    adaptor.addChild(root_0, RIGHTP66_tree);
                    }
                    SEMIC67=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl773); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC67_tree = (Object)adaptor.create(SEMIC67);
                    adaptor.addChild(root_0, SEMIC67_tree);
                    }
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						instPropertyList((LETTER63!=null?LETTER63.getText():null));
                      					}
                      				} else {
                      					instPropertyList((LETTER63!=null?LETTER63.getText():null));
                      				}
                      			
                    }

                    }
                    break;
                case 7 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1445:4: DEVICE LETTER LEFTP componentList RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    DEVICE68=(Token)match(input,DEVICE,FOLLOW_DEVICE_in_decl783); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DEVICE68_tree = (Object)adaptor.create(DEVICE68);
                    adaptor.addChild(root_0, DEVICE68_tree);
                    }
                    LETTER69=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl785); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER69_tree = (Object)adaptor.create(LETTER69);
                    adaptor.addChild(root_0, LETTER69_tree);
                    }
                    LEFTP70=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl787); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP70_tree = (Object)adaptor.create(LEFTP70);
                    adaptor.addChild(root_0, LEFTP70_tree);
                    }
                    pushFollow(FOLLOW_componentList_in_decl789);
                    componentList71=componentList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, componentList71.getTree());
                    RIGHTP72=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl791); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP72_tree = (Object)adaptor.create(RIGHTP72);
                    adaptor.addChild(root_0, RIGHTP72_tree);
                    }
                    SEMIC73=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl793); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC73_tree = (Object)adaptor.create(SEMIC73);
                    adaptor.addChild(root_0, SEMIC73_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					instDevice((LETTER69!=null?LETTER69.getText():null));
                      					applyRulesToDevice((LETTER69!=null?LETTER69.getText():null));
                      				}
                      			} else {
                      				instDevice((LETTER69!=null?LETTER69.getText():null));
                      				applyRulesToDevice((LETTER69!=null?LETTER69.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 8 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1457:4: IMAGE LEFTP name= LETTER COMMA path= STRING RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    IMAGE74=(Token)match(input,IMAGE,FOLLOW_IMAGE_in_decl802); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IMAGE74_tree = (Object)adaptor.create(IMAGE74);
                    adaptor.addChild(root_0, IMAGE74_tree);
                    }
                    LEFTP75=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl804); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP75_tree = (Object)adaptor.create(LEFTP75);
                    adaptor.addChild(root_0, LEFTP75_tree);
                    }
                    name=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl808); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    name_tree = (Object)adaptor.create(name);
                    adaptor.addChild(root_0, name_tree);
                    }
                    COMMA76=(Token)match(input,COMMA,FOLLOW_COMMA_in_decl810); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA76_tree = (Object)adaptor.create(COMMA76);
                    adaptor.addChild(root_0, COMMA76_tree);
                    }
                    path=(Token)match(input,STRING,FOLLOW_STRING_in_decl814); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    path_tree = (Object)adaptor.create(path);
                    adaptor.addChild(root_0, path_tree);
                    }
                    RIGHTP77=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl816); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP77_tree = (Object)adaptor.create(RIGHTP77);
                    adaptor.addChild(root_0, RIGHTP77_tree);
                    }
                    SEMIC78=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl818); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC78_tree = (Object)adaptor.create(SEMIC78);
                    adaptor.addChild(root_0, SEMIC78_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					bindImage((name!=null?name.getText():null), (path!=null?path.getText():null));
                      				}
                      			} else {
                      				bindImage((name!=null?name.getText():null), (path!=null?path.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 9 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:4: RULE name= LETTER LEFTP (leftOp= LETTER | leftOpP= exprP | ) operator (rightOp= LETTER | rightOpP= exprP ) RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    RULE79=(Token)match(input,RULE,FOLLOW_RULE_in_decl827); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RULE79_tree = (Object)adaptor.create(RULE79);
                    adaptor.addChild(root_0, RULE79_tree);
                    }
                    name=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl831); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    name_tree = (Object)adaptor.create(name);
                    adaptor.addChild(root_0, name_tree);
                    }
                    LEFTP80=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl833); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP80_tree = (Object)adaptor.create(LEFTP80);
                    adaptor.addChild(root_0, LEFTP80_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:27: (leftOp= LETTER | leftOpP= exprP | )
                    int alt3=3;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==LETTER) ) {
                        int LA3_1 = input.LA(2);

                        if ( (LA3_1==EQUALS||(LA3_1>=BEFORE && LA3_1<=NOT)) ) {
                            alt3=1;
                        }
                        else if ( (LA3_1==LEFTSBR||LA3_1==DOT) ) {
                            alt3=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA3_0==EQUALS||(LA3_0>=BEFORE && LA3_0<=NOT)) ) {
                        alt3=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 0, input);

                        throw nvae;
                    }
                    switch (alt3) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:28: leftOp= LETTER
                            {
                            leftOp=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl838); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            leftOp_tree = (Object)adaptor.create(leftOp);
                            adaptor.addChild(root_0, leftOp_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:44: leftOpP= exprP
                            {
                            pushFollow(FOLLOW_exprP_in_decl844);
                            leftOpP=exprP();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, leftOpP.getTree());

                            }
                            break;
                        case 3 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:59: 
                            {
                            }
                            break;

                    }

                    pushFollow(FOLLOW_operator_in_decl849);
                    operator81=operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operator81.getTree());
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:70: (rightOp= LETTER | rightOpP= exprP )
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==LETTER) ) {
                        int LA4_1 = input.LA(2);

                        if ( (LA4_1==RIGHTP) ) {
                            alt4=1;
                        }
                        else if ( (LA4_1==LEFTSBR||LA4_1==DOT) ) {
                            alt4=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 0, input);

                        throw nvae;
                    }
                    switch (alt4) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:71: rightOp= LETTER
                            {
                            rightOp=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl854); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            rightOp_tree = (Object)adaptor.create(rightOp);
                            adaptor.addChild(root_0, rightOp_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1467:88: rightOpP= exprP
                            {
                            pushFollow(FOLLOW_exprP_in_decl860);
                            rightOpP=exprP();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, rightOpP.getTree());

                            }
                            break;

                    }

                    RIGHTP82=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl863); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP82_tree = (Object)adaptor.create(RIGHTP82);
                    adaptor.addChild(root_0, RIGHTP82_tree);
                    }
                    SEMIC83=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC83_tree = (Object)adaptor.create(SEMIC83);
                    adaptor.addChild(root_0, SEMIC83_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					checkToDeclareRule((leftOpP!=null?leftOpP.primVariable:null), (leftOp!=null?leftOp.getText():null), (rightOpP!=null?rightOpP.primVariable:null), (rightOp!=null?rightOp.getText():null), (name!=null?name.getText():null), (leftOpP!=null?leftOpP.inst:null), (rightOpP!=null?rightOpP.inst:null), (operator81!=null?input.toString(operator81.start,operator81.stop):null));
                      				}
                      			} else {
                      				checkToDeclareRule((leftOpP!=null?leftOpP.primVariable:null), (leftOp!=null?leftOp.getText():null), (rightOpP!=null?rightOpP.primVariable:null), (rightOp!=null?rightOp.getText():null), (name!=null?name.getText():null), (leftOpP!=null?leftOpP.inst:null), (rightOpP!=null?rightOpP.inst:null), (operator81!=null?input.toString(operator81.start,operator81.stop):null));
                      			}
                      		
                    }

                    }
                    break;
                case 10 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1477:4: NOTE LEFTP statementsOfAssertion RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    NOTE84=(Token)match(input,NOTE,FOLLOW_NOTE_in_decl874); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOTE84_tree = (Object)adaptor.create(NOTE84);
                    adaptor.addChild(root_0, NOTE84_tree);
                    }
                    LEFTP85=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl876); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP85_tree = (Object)adaptor.create(LEFTP85);
                    adaptor.addChild(root_0, LEFTP85_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					clearAssertionList();
                      				}
                      			} else {
                      				clearAssertionList();
                      			}
                      		
                    }
                    pushFollow(FOLLOW_statementsOfAssertion_in_decl882);
                    statementsOfAssertion86=statementsOfAssertion();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementsOfAssertion86.getTree());
                    RIGHTP87=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl884); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP87_tree = (Object)adaptor.create(RIGHTP87);
                    adaptor.addChild(root_0, RIGHTP87_tree);
                    }
                    SEMIC88=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl886); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC88_tree = (Object)adaptor.create(SEMIC88);
                    adaptor.addChild(root_0, SEMIC88_tree);
                    }
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						storeAssertList(false);
                      					}
                      				} else {
                      					storeAssertList(false);
                      				}
                      			
                    }

                    }
                    break;
                case 11 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1496:4: ASSERT LEFTP statementsOfAssertion RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    ASSERT89=(Token)match(input,ASSERT,FOLLOW_ASSERT_in_decl896); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSERT89_tree = (Object)adaptor.create(ASSERT89);
                    adaptor.addChild(root_0, ASSERT89_tree);
                    }
                    LEFTP90=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_decl898); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP90_tree = (Object)adaptor.create(LEFTP90);
                    adaptor.addChild(root_0, LEFTP90_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					clearAssertionList();
                      				}
                      			} else {
                      				clearAssertionList();
                      			}
                      		
                    }
                    pushFollow(FOLLOW_statementsOfAssertion_in_decl904);
                    statementsOfAssertion91=statementsOfAssertion();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementsOfAssertion91.getTree());
                    RIGHTP92=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_decl906); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP92_tree = (Object)adaptor.create(RIGHTP92);
                    adaptor.addChild(root_0, RIGHTP92_tree);
                    }
                    SEMIC93=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_decl908); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC93_tree = (Object)adaptor.create(SEMIC93);
                    adaptor.addChild(root_0, SEMIC93_tree);
                    }
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						storeAssertList(true);
                      					}
                      				} else {
                      					storeAssertList(true);
                      				}
                      			
                    }

                    }
                    break;
                case 12 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1515:4: LETTER pfunctions
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER94=(Token)match(input,LETTER,FOLLOW_LETTER_in_decl918); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER94_tree = (Object)adaptor.create(LETTER94);
                    adaptor.addChild(root_0, LETTER94_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					isPartDefined((LETTER94!=null?LETTER94.getText():null));
                      				}
                      			} else {
                      				isPartDefined((LETTER94!=null?LETTER94.getText():null));
                      			}
                      		
                    }
                    pushFollow(FOLLOW_pfunctions_in_decl924);
                    pfunctions95=pfunctions();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pfunctions95.getTree());
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						instPropertyList((LETTER94!=null?LETTER94.getText():null));
                      					}
                      				} else {
                      					instPropertyList((LETTER94!=null?LETTER94.getText():null));
                      				}
                      			
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "decl"

    public static class operator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "operator"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1536:1: operator : ( ruleOperators | relationalOperators | logicalOperators );
    public final eugeneParser.operator_return operator() throws RecognitionException {
        eugeneParser.operator_return retval = new eugeneParser.operator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        eugeneParser.ruleOperators_return ruleOperators96 = null;

        eugeneParser.relationalOperators_return relationalOperators97 = null;

        eugeneParser.logicalOperators_return logicalOperators98 = null;



        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1537:2: ( ruleOperators | relationalOperators | logicalOperators )
            int alt6=3;
            switch ( input.LA(1) ) {
            case BEFORE:
            case AFTER:
            case WITH:
            case NOTWITH:
            case NEXTTO:
            case NOTCONTAINS:
            case CONTAINS:
            case NOTMORETHAN:
                {
                alt6=1;
                }
                break;
            case EQUALS:
            case NEQUAL:
            case LTHAN:
            case GTHAN:
            case LEQUAL:
            case GEQUAL:
                {
                alt6=2;
                }
                break;
            case AND:
            case OR:
            case NOT:
                {
                alt6=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1537:4: ruleOperators
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ruleOperators_in_operator940);
                    ruleOperators96=ruleOperators();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleOperators96.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1538:4: relationalOperators
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_relationalOperators_in_operator945);
                    relationalOperators97=relationalOperators();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalOperators97.getTree());

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1539:4: logicalOperators
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_logicalOperators_in_operator950);
                    logicalOperators98=logicalOperators();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalOperators98.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "operator"

    public static class ruleOperators_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleOperators"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1542:1: ruleOperators : ( BEFORE | AFTER | WITH | NOTWITH | NEXTTO | NOTCONTAINS | CONTAINS | NOTMORETHAN );
    public final eugeneParser.ruleOperators_return ruleOperators() throws RecognitionException {
        eugeneParser.ruleOperators_return retval = new eugeneParser.ruleOperators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set99=null;

        Object set99_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1543:2: ( BEFORE | AFTER | WITH | NOTWITH | NEXTTO | NOTCONTAINS | CONTAINS | NOTMORETHAN )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:
            {
            root_0 = (Object)adaptor.nil();

            set99=(Token)input.LT(1);
            if ( (input.LA(1)>=BEFORE && input.LA(1)<=NOTMORETHAN) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set99));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleOperators"

    public static class relationalOperators_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalOperators"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1553:1: relationalOperators : ( EQUALS EQUALS | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL );
    public final eugeneParser.relationalOperators_return relationalOperators() throws RecognitionException {
        eugeneParser.relationalOperators_return retval = new eugeneParser.relationalOperators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EQUALS100=null;
        Token EQUALS101=null;
        Token NEQUAL102=null;
        Token LTHAN103=null;
        Token GTHAN104=null;
        Token LEQUAL105=null;
        Token GEQUAL106=null;

        Object EQUALS100_tree=null;
        Object EQUALS101_tree=null;
        Object NEQUAL102_tree=null;
        Object LTHAN103_tree=null;
        Object GTHAN104_tree=null;
        Object LEQUAL105_tree=null;
        Object GEQUAL106_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1554:2: ( EQUALS EQUALS | NEQUAL | LTHAN | GTHAN | LEQUAL | GEQUAL )
            int alt7=6;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt7=1;
                }
                break;
            case NEQUAL:
                {
                alt7=2;
                }
                break;
            case LTHAN:
                {
                alt7=3;
                }
                break;
            case GTHAN:
                {
                alt7=4;
                }
                break;
            case LEQUAL:
                {
                alt7=5;
                }
                break;
            case GEQUAL:
                {
                alt7=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1554:4: EQUALS EQUALS
                    {
                    root_0 = (Object)adaptor.nil();

                    EQUALS100=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_relationalOperators1007); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS100_tree = (Object)adaptor.create(EQUALS100);
                    adaptor.addChild(root_0, EQUALS100_tree);
                    }
                    EQUALS101=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_relationalOperators1009); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS101_tree = (Object)adaptor.create(EQUALS101);
                    adaptor.addChild(root_0, EQUALS101_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1555:4: NEQUAL
                    {
                    root_0 = (Object)adaptor.nil();

                    NEQUAL102=(Token)match(input,NEQUAL,FOLLOW_NEQUAL_in_relationalOperators1014); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEQUAL102_tree = (Object)adaptor.create(NEQUAL102);
                    adaptor.addChild(root_0, NEQUAL102_tree);
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1556:4: LTHAN
                    {
                    root_0 = (Object)adaptor.nil();

                    LTHAN103=(Token)match(input,LTHAN,FOLLOW_LTHAN_in_relationalOperators1019); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LTHAN103_tree = (Object)adaptor.create(LTHAN103);
                    adaptor.addChild(root_0, LTHAN103_tree);
                    }

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1557:4: GTHAN
                    {
                    root_0 = (Object)adaptor.nil();

                    GTHAN104=(Token)match(input,GTHAN,FOLLOW_GTHAN_in_relationalOperators1024); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GTHAN104_tree = (Object)adaptor.create(GTHAN104);
                    adaptor.addChild(root_0, GTHAN104_tree);
                    }

                    }
                    break;
                case 5 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1558:4: LEQUAL
                    {
                    root_0 = (Object)adaptor.nil();

                    LEQUAL105=(Token)match(input,LEQUAL,FOLLOW_LEQUAL_in_relationalOperators1029); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEQUAL105_tree = (Object)adaptor.create(LEQUAL105);
                    adaptor.addChild(root_0, LEQUAL105_tree);
                    }

                    }
                    break;
                case 6 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1559:4: GEQUAL
                    {
                    root_0 = (Object)adaptor.nil();

                    GEQUAL106=(Token)match(input,GEQUAL,FOLLOW_GEQUAL_in_relationalOperators1034); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GEQUAL106_tree = (Object)adaptor.create(GEQUAL106);
                    adaptor.addChild(root_0, GEQUAL106_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalOperators"

    public static class logicalOperators_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "logicalOperators"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1562:1: logicalOperators : ( AND | NOT | OR );
    public final eugeneParser.logicalOperators_return logicalOperators() throws RecognitionException {
        eugeneParser.logicalOperators_return retval = new eugeneParser.logicalOperators_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set107=null;

        Object set107_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1563:2: ( AND | NOT | OR )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:
            {
            root_0 = (Object)adaptor.nil();

            set107=(Token)input.LT(1);
            if ( (input.LA(1)>=AND && input.LA(1)<=NOT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set107));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "logicalOperators"

    public static class statementsOfAssertion_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementsOfAssertion"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1568:1: statementsOfAssertion : (s1= statmnts | ) ( operator s2= statmnts )* ;
    public final eugeneParser.statementsOfAssertion_return statementsOfAssertion() throws RecognitionException {
        eugeneParser.statementsOfAssertion_return retval = new eugeneParser.statementsOfAssertion_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        eugeneParser.statmnts_return s1 = null;

        eugeneParser.statmnts_return s2 = null;

        eugeneParser.operator_return operator108 = null;



        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1569:2: ( (s1= statmnts | ) ( operator s2= statmnts )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1569:4: (s1= statmnts | ) ( operator s2= statmnts )*
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1569:4: (s1= statmnts | )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==LEFTP||LA8_0==LETTER) ) {
                alt8=1;
            }
            else if ( (LA8_0==EOF||LA8_0==RIGHTP||LA8_0==EQUALS||(LA8_0>=BEFORE && LA8_0<=NOT)) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1569:5: s1= statmnts
                    {
                    pushFollow(FOLLOW_statmnts_in_statementsOfAssertion1069);
                    s1=statmnts();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s1.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()){
                      				if (ifValueStack.peek() == "1") {
                      					addToAssertionList((s1!=null?s1.op:null), "operand");
                      				}
                      			} else {
                      				addToAssertionList((s1!=null?s1.op:null), "operand");
                      			}
                      		
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1578:7: 
                    {
                    }
                    break;

            }

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1578:9: ( operator s2= statmnts )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==EQUALS||(LA9_0>=BEFORE && LA9_0<=NOT)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1578:10: operator s2= statmnts
            	    {
            	    pushFollow(FOLLOW_operator_in_statementsOfAssertion1080);
            	    operator108=operator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, operator108.getTree());
            	    if ( state.backtracking==0 ) {

            	      				if (!ifValueStack.empty()) {
            	      					if(ifValueStack.peek() == "1") {
            	      						addToAssertStatement(" " + (operator108!=null?input.toString(operator108.start,operator108.stop):null) + " ");
            	      					}
            	      				} else {
            	      					addToAssertStatement(" " + (operator108!=null?input.toString(operator108.start,operator108.stop):null) + " ");
            	      				}
            	      			
            	    }
            	    pushFollow(FOLLOW_statmnts_in_statementsOfAssertion1089);
            	    s2=statmnts();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, s2.getTree());
            	    if ( state.backtracking==0 ) {

            	      					if (!ifValueStack.empty()) {
            	      						if (ifValueStack.peek() == "1") {
            	      							addToAssertionList((s2!=null?s2.op:null), "operand");
            	      							addToAssertionList((operator108!=null?input.toString(operator108.start,operator108.stop):null), "operator");
            	      						}
            	      					} else {
            	      						addToAssertionList((s2!=null?s2.op:null), "operand");
            	      						addToAssertionList((operator108!=null?input.toString(operator108.start,operator108.stop):null), "operator");
            	      					}
            	      				
            	    }

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementsOfAssertion"

    public static class statmnts_return extends ParserRuleReturnScope {
        public String op;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statmnts"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1601:1: statmnts returns [String op] : ( LEFTP statementsOfAssertion RIGHTP | LETTER );
    public final eugeneParser.statmnts_return statmnts() throws RecognitionException {
        eugeneParser.statmnts_return retval = new eugeneParser.statmnts_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LEFTP109=null;
        Token RIGHTP111=null;
        Token LETTER112=null;
        eugeneParser.statementsOfAssertion_return statementsOfAssertion110 = null;


        Object LEFTP109_tree=null;
        Object RIGHTP111_tree=null;
        Object LETTER112_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1602:2: ( LEFTP statementsOfAssertion RIGHTP | LETTER )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==LEFTP) ) {
                alt10=1;
            }
            else if ( (LA10_0==LETTER) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1602:4: LEFTP statementsOfAssertion RIGHTP
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFTP109=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_statmnts1113); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP109_tree = (Object)adaptor.create(LEFTP109);
                    adaptor.addChild(root_0, LEFTP109_tree);
                    }
                    if ( state.backtracking==0 ) {
                       addToAssertStatement("("); 
                    }
                    pushFollow(FOLLOW_statementsOfAssertion_in_statmnts1117);
                    statementsOfAssertion110=statementsOfAssertion();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementsOfAssertion110.getTree());
                    RIGHTP111=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_statmnts1119); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP111_tree = (Object)adaptor.create(RIGHTP111);
                    adaptor.addChild(root_0, RIGHTP111_tree);
                    }
                    if ( state.backtracking==0 ) {
                       addToAssertStatement(")"); 
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1603:4: LETTER
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER112=(Token)match(input,LETTER,FOLLOW_LETTER_in_statmnts1126); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER112_tree = (Object)adaptor.create(LETTER112);
                    adaptor.addChild(root_0, LETTER112_tree);
                    }
                    if ( state.backtracking==0 ) {
                       retval.op = getAssertStatementOperand((LETTER112!=null?LETTER112.getText():null)); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "statmnts"

    public static class ifStatement_return extends ParserRuleReturnScope {
        public boolean check = true;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ifStatement"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1606:1: ifStatement returns [boolean check = true] : IF LEFTP (stmts= statementsOfAssertionExpr | exp= expression ) RIGHTP LEFTCUR ( statement )* ( RIGHTCUR ( ELSE LEFTCUR )=> ELSE LEFTCUR ( statement )* | ) RIGHTCUR ;
    public final eugeneParser.ifStatement_return ifStatement() throws RecognitionException {
        eugeneParser.ifStatement_return retval = new eugeneParser.ifStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token IF113=null;
        Token LEFTP114=null;
        Token RIGHTP115=null;
        Token LEFTCUR116=null;
        Token RIGHTCUR118=null;
        Token ELSE119=null;
        Token LEFTCUR120=null;
        Token RIGHTCUR122=null;
        eugeneParser.statementsOfAssertionExpr_return stmts = null;

        eugeneParser.expression_return exp = null;

        eugeneParser.statement_return statement117 = null;

        eugeneParser.statement_return statement121 = null;


        Object IF113_tree=null;
        Object LEFTP114_tree=null;
        Object RIGHTP115_tree=null;
        Object LEFTCUR116_tree=null;
        Object RIGHTCUR118_tree=null;
        Object ELSE119_tree=null;
        Object LEFTCUR120_tree=null;
        Object RIGHTCUR122_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1607:2: ( IF LEFTP (stmts= statementsOfAssertionExpr | exp= expression ) RIGHTP LEFTCUR ( statement )* ( RIGHTCUR ( ELSE LEFTCUR )=> ELSE LEFTCUR ( statement )* | ) RIGHTCUR )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1607:4: IF LEFTP (stmts= statementsOfAssertionExpr | exp= expression ) RIGHTP LEFTCUR ( statement )* ( RIGHTCUR ( ELSE LEFTCUR )=> ELSE LEFTCUR ( statement )* | ) RIGHTCUR
            {
            root_0 = (Object)adaptor.nil();

            IF113=(Token)match(input,IF,FOLLOW_IF_in_ifStatement1143); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF113_tree = (Object)adaptor.create(IF113);
            adaptor.addChild(root_0, IF113_tree);
            }
            LEFTP114=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_ifStatement1145); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFTP114_tree = (Object)adaptor.create(LEFTP114);
            adaptor.addChild(root_0, LEFTP114_tree);
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1607:13: (stmts= statementsOfAssertionExpr | exp= expression )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ON) ) {
                alt11=1;
            }
            else if ( (LA11_0==EOF||(LA11_0>=LEFTP && LA11_0<=LEFTSBR)||LA11_0==EQUALS||(LA11_0>=NEQUAL && LA11_0<=NOT)||(LA11_0>=TRUE && LA11_0<=FALSE)||(LA11_0>=LETTER && LA11_0<=REAL)) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1607:14: stmts= statementsOfAssertionExpr
                    {
                    pushFollow(FOLLOW_statementsOfAssertionExpr_in_ifStatement1150);
                    stmts=statementsOfAssertionExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, stmts.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1607:48: exp= expression
                    {
                    pushFollow(FOLLOW_expression_in_ifStatement1156);
                    exp=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exp.getTree());

                    }
                    break;

            }

            RIGHTP115=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_ifStatement1159); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHTP115_tree = (Object)adaptor.create(RIGHTP115);
            adaptor.addChild(root_0, RIGHTP115_tree);
            }
            LEFTCUR116=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_ifStatement1161); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFTCUR116_tree = (Object)adaptor.create(LEFTCUR116);
            adaptor.addChild(root_0, LEFTCUR116_tree);
            }
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					if ((stmts!=null?input.toString(stmts.start,stmts.stop):null) != null) {
              						retval.check = validateIfStatement((stmts!=null?stmts.dList:null));
              					} else {
              						retval.check = validateIfStatementExp();
              						if (retval.check) {
              							ifValueStack.push("1");
              						} else {
              							ifValueStack.push("0");
              						}
              					}
              					evaluate.push("1");
              				} else {
              					evaluate.push("0");
              				}
              			} else if (ifValueStack.empty()) {
              				if ((stmts!=null?input.toString(stmts.start,stmts.stop):null) != null) {
              					retval.check = validateIfStatement((stmts!=null?stmts.dList:null));
              				} else {
              					retval.check = validateIfStatementExp();
              					if (retval.check) {
              						ifValueStack.push("1");
              					} else {
              						ifValueStack.push("0");
              					}
              				}
              				if (retval.check) {
              					evaluate.push("1");
              				} else {
              					evaluate.push("0");
              				}
              			}
              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1642:5: ( statement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==LEFTP||LA12_0==LEFTSBR||LA12_0==NUM||(LA12_0>=BOOLEAN && LA12_0<=TXT)||LA12_0==PRINT||(LA12_0>=ASSERT && LA12_0<=IF)||(LA12_0>=TRUE && LA12_0<=PERMUTE)||(LA12_0>=LETTER && LA12_0<=REAL)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1642:6: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_ifStatement1168);
            	    statement117=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement117.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1642:18: ( RIGHTCUR ( ELSE LEFTCUR )=> ELSE LEFTCUR ( statement )* | )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RIGHTCUR) ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==EOF||LA14_1==LEFTP||LA14_1==LEFTSBR||LA14_1==RIGHTCUR||LA14_1==NUM||(LA14_1>=BOOLEAN && LA14_1<=TXT)||LA14_1==PRINT||(LA14_1>=ASSERT && LA14_1<=IF)||(LA14_1>=TRUE && LA14_1<=PERMUTE)||(LA14_1>=LETTER && LA14_1<=REAL)) ) {
                    alt14=2;
                }
                else if ( (LA14_1==ELSE) ) {
                    alt14=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1642:19: RIGHTCUR ( ELSE LEFTCUR )=> ELSE LEFTCUR ( statement )*
                    {
                    RIGHTCUR118=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_ifStatement1173); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTCUR118_tree = (Object)adaptor.create(RIGHTCUR118);
                    adaptor.addChild(root_0, RIGHTCUR118_tree);
                    }
                    if ( state.backtracking==0 ) {

                      				String str = evaluate.pop();
                      				if (str.equals("1") || evaluate.size() == 0) {
                      					ifValueStack.pop();
                      				}
                      			
                    }
                    ELSE119=(Token)match(input,ELSE,FOLLOW_ELSE_in_ifStatement1188); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELSE119_tree = (Object)adaptor.create(ELSE119);
                    adaptor.addChild(root_0, ELSE119_tree);
                    }
                    LEFTCUR120=(Token)match(input,LEFTCUR,FOLLOW_LEFTCUR_in_ifStatement1190); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTCUR120_tree = (Object)adaptor.create(LEFTCUR120);
                    adaptor.addChild(root_0, LEFTCUR120_tree);
                    }
                    if ( state.backtracking==0 ) {

                      					if (!ifValueStack.empty()) {
                      						if (ifValueStack.peek() == "1") {
                      							if (retval.check) {
                      								ifValueStack.add("0");
                      							} else {
                      								ifValueStack.add("1");
                      							}
                      							evaluate.push("1");
                      						} else {
                      							evaluate.push("0");
                      						}
                      					} else {
                      						if (retval.check){
                      							ifValueStack.add("0");
                      							evaluate.push("1");
                      						} else {
                      							ifValueStack.add("1");
                      							evaluate.push("0");
                      						}
                      					}
                                      
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1670:19: ( statement )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==LEFTP||LA13_0==LEFTSBR||LA13_0==NUM||(LA13_0>=BOOLEAN && LA13_0<=TXT)||LA13_0==PRINT||(LA13_0>=ASSERT && LA13_0<=IF)||(LA13_0>=TRUE && LA13_0<=PERMUTE)||(LA13_0>=LETTER && LA13_0<=REAL)) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1670:20: statement
                    	    {
                    	    pushFollow(FOLLOW_statement_in_ifStatement1199);
                    	    statement121=statement();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement121.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1671:7: 
                    {
                    }
                    break;

            }

            RIGHTCUR122=(Token)match(input,RIGHTCUR,FOLLOW_RIGHTCUR_in_ifStatement1211); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHTCUR122_tree = (Object)adaptor.create(RIGHTCUR122);
            adaptor.addChild(root_0, RIGHTCUR122_tree);
            }
            if ( state.backtracking==0 ) {

              						String str = evaluate.pop();
              						if (str.equals("1") || evaluate.size() == 0) {
              							ifValueStack.pop();
              						}
              					
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "ifStatement"

    public static class statementsOfAssertionExpr_return extends ParserRuleReturnScope {
        public ArrayList<Device> dList;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementsOfAssertionExpr"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1680:1: statementsOfAssertionExpr returns [ArrayList<Device> dList] : ON LEFTP deviceList RIGHTP statementsOfAssertion ;
    public final eugeneParser.statementsOfAssertionExpr_return statementsOfAssertionExpr() throws RecognitionException {
        eugeneParser.statementsOfAssertionExpr_return retval = new eugeneParser.statementsOfAssertionExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ON123=null;
        Token LEFTP124=null;
        Token RIGHTP126=null;
        eugeneParser.deviceList_return deviceList125 = null;

        eugeneParser.statementsOfAssertion_return statementsOfAssertion127 = null;


        Object ON123_tree=null;
        Object LEFTP124_tree=null;
        Object RIGHTP126_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1681:2: ( ON LEFTP deviceList RIGHTP statementsOfAssertion )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1681:4: ON LEFTP deviceList RIGHTP statementsOfAssertion
            {
            root_0 = (Object)adaptor.nil();

            ON123=(Token)match(input,ON,FOLLOW_ON_in_statementsOfAssertionExpr1233); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ON123_tree = (Object)adaptor.create(ON123);
            adaptor.addChild(root_0, ON123_tree);
            }
            LEFTP124=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_statementsOfAssertionExpr1235); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFTP124_tree = (Object)adaptor.create(LEFTP124);
            adaptor.addChild(root_0, LEFTP124_tree);
            }
            pushFollow(FOLLOW_deviceList_in_statementsOfAssertionExpr1237);
            deviceList125=deviceList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, deviceList125.getTree());
            RIGHTP126=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_statementsOfAssertionExpr1239); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHTP126_tree = (Object)adaptor.create(RIGHTP126);
            adaptor.addChild(root_0, RIGHTP126_tree);
            }
            if ( state.backtracking==0 ) {
              retval.dList = (deviceList125!=null?deviceList125.dList:null);
            }
            pushFollow(FOLLOW_statementsOfAssertion_in_statementsOfAssertionExpr1243);
            statementsOfAssertion127=statementsOfAssertion();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementsOfAssertion127.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementsOfAssertionExpr"

    public static class expression_return extends ParserRuleReturnScope {
        public boolean result1;
        public boolean result2;
        public boolean finalResult;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1684:1: expression returns [boolean result1, boolean result2, boolean finalResult] : (re1= relationalExpression | ) ( ( logicalOperators ) re2= relationalExpression )* ;
    public final eugeneParser.expression_return expression() throws RecognitionException {
        eugeneParser.expression_return retval = new eugeneParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        eugeneParser.relationalExpression_return re1 = null;

        eugeneParser.relationalExpression_return re2 = null;

        eugeneParser.logicalOperators_return logicalOperators128 = null;



        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:2: ( (re1= relationalExpression | ) ( ( logicalOperators ) re2= relationalExpression )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:4: (re1= relationalExpression | ) ( ( logicalOperators ) re2= relationalExpression )*
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:4: (re1= relationalExpression | )
            int alt15=2;
            switch ( input.LA(1) ) {
            case LEFTP:
            case LEFTSBR:
            case EQUALS:
            case NEQUAL:
            case LTHAN:
            case GTHAN:
            case LEQUAL:
            case GEQUAL:
            case TRUE:
            case FALSE:
            case LETTER:
            case STRING:
            case NUMBER:
            case REAL:
                {
                alt15=1;
                }
                break;
            case AND:
            case OR:
            case NOT:
                {
                int LA15_2 = input.LA(2);

                if ( (synpred50_eugene()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 2, input);

                    throw nvae;
                }
                }
                break;
            case RIGHTP:
                {
                int LA15_3 = input.LA(2);

                if ( (synpred50_eugene()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:5: re1= relationalExpression
                    {
                    pushFollow(FOLLOW_relationalExpression_in_expression1260);
                    re1=relationalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, re1.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:32: 
                    {
                    }
                    break;

            }

            if ( state.backtracking==0 ) {

              			if ((re1!=null?input.toString(re1.start,re1.stop):null) != null && (re1!=null?re1.list:null).size() != 0) {
              				retval.result1 = evaluateRelationalExpression((re1!=null?re1.list:null));
              				Primitive prim = new Primitive("operand", "txt");
              				if (retval.result1) {
              					prim.txt = "1";
              				} else {
              					prim.txt = "0";
              				}
              				ifEvaluationList.add(prim);
              			}
              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1697:5: ( ( logicalOperators ) re2= relationalExpression )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=AND && LA16_0<=NOT)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1697:6: ( logicalOperators ) re2= relationalExpression
            	    {
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1697:6: ( logicalOperators )
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1697:7: logicalOperators
            	    {
            	    pushFollow(FOLLOW_logicalOperators_in_expression1272);
            	    logicalOperators128=logicalOperators();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalOperators128.getTree());

            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_expression1277);
            	    re2=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, re2.getTree());
            	    if ( state.backtracking==0 ) {

            	      				if ((re2!=null?re2.list:null).size() != 0) {
            	      					retval.result2 = evaluateRelationalExpression((re2!=null?re2.list:null));
            	      					Primitive prim = new Primitive("operand", "txt");
            	      					if (retval.result2) {
            	      						prim.txt = "1";
            	      					} else {
            	      						prim.txt = "0";
            	      					}
            	      					ifEvaluationList.add(prim);
            	      					Primitive prim2 = new Primitive("operator", "txt");
            	      					prim2.txt = (logicalOperators128!=null?input.toString(logicalOperators128.start,logicalOperators128.stop):null);
            	      					ifEvaluationList.add(prim2);
            	      				}
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class relationalExpression_return extends ParserRuleReturnScope {
        public ArrayList<Primitive> list = new ArrayList<Primitive>();
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpression"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1715:1: relationalExpression returns [ArrayList<Primitive> list = new ArrayList<Primitive>()] : ( (e= expr | ) ( ( relationalOperators ) f= expr )* | LEFTP expression RIGHTP );
    public final eugeneParser.relationalExpression_return relationalExpression() throws RecognitionException {
        eugeneParser.relationalExpression_return retval = new eugeneParser.relationalExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LEFTP130=null;
        Token RIGHTP132=null;
        eugeneParser.expr_return e = null;

        eugeneParser.expr_return f = null;

        eugeneParser.relationalOperators_return relationalOperators129 = null;

        eugeneParser.expression_return expression131 = null;


        Object LEFTP130_tree=null;
        Object RIGHTP132_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:3: ( (e= expr | ) ( ( relationalOperators ) f= expr )* | LEFTP expression RIGHTP )
            int alt19=2;
            alt19 = dfa19.predict(input);
            switch (alt19) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:5: (e= expr | ) ( ( relationalOperators ) f= expr )*
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:5: (e= expr | )
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==LEFTP||LA17_0==LEFTSBR||(LA17_0>=TRUE && LA17_0<=FALSE)||(LA17_0>=LETTER && LA17_0<=REAL)) ) {
                        alt17=1;
                    }
                    else if ( (LA17_0==EOF||LA17_0==RIGHTP||LA17_0==EQUALS||(LA17_0>=NEQUAL && LA17_0<=NOT)) ) {
                        alt17=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 17, 0, input);

                        throw nvae;
                    }
                    switch (alt17) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:6: e= expr
                            {
                            pushFollow(FOLLOW_expr_in_relationalExpression1303);
                            e=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:15: 
                            {
                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if ((e!=null?e.p:null) != null) {
                      				retval.list.add((e!=null?e.p:null));
                      			}
                       		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:6: ( ( relationalOperators ) f= expr )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==EQUALS||(LA18_0>=NEQUAL && LA18_0<=GEQUAL)) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:7: ( relationalOperators ) f= expr
                    	    {
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:7: ( relationalOperators )
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:8: relationalOperators
                    	    {
                    	    pushFollow(FOLLOW_relationalOperators_in_relationalExpression1316);
                    	    relationalOperators129=relationalOperators();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalOperators129.getTree());

                    	    }

                    	    pushFollow(FOLLOW_expr_in_relationalExpression1321);
                    	    f=expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    	    if ( state.backtracking==0 ) {

                    	      				if ((f!=null?f.p:null) != null) {
                    	      					if ((f!=null?f.p:null).type.equals(retval.list.get(0).type)) {
                    	      						retval.list.add((f!=null?f.p:null));
                    	      					} else {
                    	      						printError("Type mismatch in relational expression in if statement.\n");
                    	      					}
                    	      				}
                    	      				Primitive pOp = new Primitive((relationalOperators129!=null?input.toString(relationalOperators129.start,relationalOperators129.stop):null), "operator");
                    	      				retval.list.add(pOp);
                    	      			
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1733:4: LEFTP expression RIGHTP
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFTP130=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_relationalExpression1334); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP130_tree = (Object)adaptor.create(LEFTP130);
                    adaptor.addChild(root_0, LEFTP130_tree);
                    }
                    pushFollow(FOLLOW_expression_in_relationalExpression1336);
                    expression131=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression131.getTree());
                    RIGHTP132=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_relationalExpression1338); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP132_tree = (Object)adaptor.create(RIGHTP132);
                    adaptor.addChild(root_0, RIGHTP132_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalExpression"

    public static class deviceList_return extends ParserRuleReturnScope {
        public ArrayList<Device> dList = new ArrayList<Device>();
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "deviceList"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1736:1: deviceList returns [ArrayList<Device> dList = new ArrayList<Device>()] : name= LETTER ( COMMA name= LETTER )* ;
    public final eugeneParser.deviceList_return deviceList() throws RecognitionException {
        eugeneParser.deviceList_return retval = new eugeneParser.deviceList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token name=null;
        Token COMMA133=null;

        Object name_tree=null;
        Object COMMA133_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1737:2: (name= LETTER ( COMMA name= LETTER )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1737:4: name= LETTER ( COMMA name= LETTER )*
            {
            root_0 = (Object)adaptor.nil();

            name=(Token)match(input,LETTER,FOLLOW_LETTER_in_deviceList1355); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            name_tree = (Object)adaptor.create(name);
            adaptor.addChild(root_0, name_tree);
            }
            if ( state.backtracking==0 ) {

              			if (deviceDeclarations.containsKey((name!=null?name.getText():null))) {
              				retval.dList.add(deviceDeclarations.get((name!=null?name.getText():null)));
              			}

              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1743:5: ( COMMA name= LETTER )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==COMMA) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1743:6: COMMA name= LETTER
            	    {
            	    COMMA133=(Token)match(input,COMMA,FOLLOW_COMMA_in_deviceList1362); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA133_tree = (Object)adaptor.create(COMMA133);
            	    adaptor.addChild(root_0, COMMA133_tree);
            	    }
            	    name=(Token)match(input,LETTER,FOLLOW_LETTER_in_deviceList1366); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    name_tree = (Object)adaptor.create(name);
            	    adaptor.addChild(root_0, name_tree);
            	    }
            	    if ( state.backtracking==0 ) {

            	      				if (deviceDeclarations.containsKey((name!=null?name.getText():null))) {
            	      					retval.dList.add(deviceDeclarations.get((name!=null?name.getText():null)));
            	      				}
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "deviceList"

    public static class numdecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numdecl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1751:1: numdecl : ( LETTER ( COMMA numdecl )* | LETTER EQUALS (ex= expr ) ( COMMA numdecl )* );
    public final eugeneParser.numdecl_return numdecl() throws RecognitionException {
        eugeneParser.numdecl_return retval = new eugeneParser.numdecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LETTER134=null;
        Token COMMA135=null;
        Token LETTER137=null;
        Token EQUALS138=null;
        Token COMMA139=null;
        eugeneParser.expr_return ex = null;

        eugeneParser.numdecl_return numdecl136 = null;

        eugeneParser.numdecl_return numdecl140 = null;


        Object LETTER134_tree=null;
        Object COMMA135_tree=null;
        Object LETTER137_tree=null;
        Object EQUALS138_tree=null;
        Object COMMA139_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1752:2: ( LETTER ( COMMA numdecl )* | LETTER EQUALS (ex= expr ) ( COMMA numdecl )* )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==LETTER) ) {
                int LA23_1 = input.LA(2);

                if ( (LA23_1==EQUALS) ) {
                    alt23=2;
                }
                else if ( (LA23_1==EOF||(LA23_1>=SEMIC && LA23_1<=COMMA)) ) {
                    alt23=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1752:4: LETTER ( COMMA numdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER134=(Token)match(input,LETTER,FOLLOW_LETTER_in_numdecl1385); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER134_tree = (Object)adaptor.create(LETTER134);
                    adaptor.addChild(root_0, LETTER134_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveNoValue((LETTER134!=null?LETTER134.getText():null), "num");
                      				}
                      			} else {
                      				declarePrimitiveNoValue((LETTER134!=null?LETTER134.getText():null), "num");
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1761:5: ( COMMA numdecl )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==COMMA) ) {
                            int LA21_2 = input.LA(2);

                            if ( (synpred56_eugene()) ) {
                                alt21=1;
                            }


                        }


                        switch (alt21) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1761:6: COMMA numdecl
                    	    {
                    	    COMMA135=(Token)match(input,COMMA,FOLLOW_COMMA_in_numdecl1392); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA135_tree = (Object)adaptor.create(COMMA135);
                    	    adaptor.addChild(root_0, COMMA135_tree);
                    	    }
                    	    pushFollow(FOLLOW_numdecl_in_numdecl1394);
                    	    numdecl136=numdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, numdecl136.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1762:4: LETTER EQUALS (ex= expr ) ( COMMA numdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER137=(Token)match(input,LETTER,FOLLOW_LETTER_in_numdecl1401); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER137_tree = (Object)adaptor.create(LETTER137);
                    adaptor.addChild(root_0, LETTER137_tree);
                    }
                    EQUALS138=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_numdecl1403); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS138_tree = (Object)adaptor.create(EQUALS138);
                    adaptor.addChild(root_0, EQUALS138_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1762:18: (ex= expr )
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1762:19: ex= expr
                    {
                    pushFollow(FOLLOW_expr_in_numdecl1408);
                    ex=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ex.getTree());

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveWithValueNum((LETTER137!=null?LETTER137.getText():null), (ex!=null?ex.p:null), (ex!=null?ex.index:0));
                      				}
                      			} else {
                      				declarePrimitiveWithValueNum((LETTER137!=null?LETTER137.getText():null), (ex!=null?ex.p:null), (ex!=null?ex.index:0));
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1771:6: ( COMMA numdecl )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==COMMA) ) {
                            int LA22_2 = input.LA(2);

                            if ( (synpred58_eugene()) ) {
                                alt22=1;
                            }


                        }


                        switch (alt22) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1771:7: COMMA numdecl
                    	    {
                    	    COMMA139=(Token)match(input,COMMA,FOLLOW_COMMA_in_numdecl1417); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA139_tree = (Object)adaptor.create(COMMA139);
                    	    adaptor.addChild(root_0, COMMA139_tree);
                    	    }
                    	    pushFollow(FOLLOW_numdecl_in_numdecl1419);
                    	    numdecl140=numdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, numdecl140.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "numdecl"

    public static class txtdecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "txtdecl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1774:1: txtdecl : ( LETTER ( COMMA txtdecl )* | var= LETTER EQUALS let= expr ( COMMA txtdecl )* );
    public final eugeneParser.txtdecl_return txtdecl() throws RecognitionException {
        eugeneParser.txtdecl_return retval = new eugeneParser.txtdecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token LETTER141=null;
        Token COMMA142=null;
        Token EQUALS144=null;
        Token COMMA145=null;
        eugeneParser.expr_return let = null;

        eugeneParser.txtdecl_return txtdecl143 = null;

        eugeneParser.txtdecl_return txtdecl146 = null;


        Object var_tree=null;
        Object LETTER141_tree=null;
        Object COMMA142_tree=null;
        Object EQUALS144_tree=null;
        Object COMMA145_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1775:2: ( LETTER ( COMMA txtdecl )* | var= LETTER EQUALS let= expr ( COMMA txtdecl )* )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LETTER) ) {
                int LA26_1 = input.LA(2);

                if ( (LA26_1==EQUALS) ) {
                    alt26=2;
                }
                else if ( (LA26_1==EOF||(LA26_1>=SEMIC && LA26_1<=COMMA)) ) {
                    alt26=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1775:4: LETTER ( COMMA txtdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER141=(Token)match(input,LETTER,FOLLOW_LETTER_in_txtdecl1432); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER141_tree = (Object)adaptor.create(LETTER141);
                    adaptor.addChild(root_0, LETTER141_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveNoValue((LETTER141!=null?LETTER141.getText():null), "txt");
                      				}
                      			} else {
                      				declarePrimitiveNoValue((LETTER141!=null?LETTER141.getText():null), "txt");
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1784:5: ( COMMA txtdecl )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==COMMA) ) {
                            int LA24_2 = input.LA(2);

                            if ( (synpred59_eugene()) ) {
                                alt24=1;
                            }


                        }


                        switch (alt24) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1784:6: COMMA txtdecl
                    	    {
                    	    COMMA142=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtdecl1439); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA142_tree = (Object)adaptor.create(COMMA142);
                    	    adaptor.addChild(root_0, COMMA142_tree);
                    	    }
                    	    pushFollow(FOLLOW_txtdecl_in_txtdecl1441);
                    	    txtdecl143=txtdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtdecl143.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop24;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1786:4: var= LETTER EQUALS let= expr ( COMMA txtdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    var=(Token)match(input,LETTER,FOLLOW_LETTER_in_txtdecl1451); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    var_tree = (Object)adaptor.create(var);
                    adaptor.addChild(root_0, var_tree);
                    }
                    EQUALS144=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_txtdecl1453); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS144_tree = (Object)adaptor.create(EQUALS144);
                    adaptor.addChild(root_0, EQUALS144_tree);
                    }
                    pushFollow(FOLLOW_expr_in_txtdecl1457);
                    let=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, let.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveWithValueTxt((var!=null?var.getText():null), (let!=null?let.p:null), (let!=null?let.index:0));
                      				}
                      			} else {
                      				declarePrimitiveWithValueTxt((var!=null?var.getText():null), (let!=null?let.p:null), (let!=null?let.index:0));
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1795:5: ( COMMA txtdecl )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==COMMA) ) {
                            int LA25_2 = input.LA(2);

                            if ( (synpred61_eugene()) ) {
                                alt25=1;
                            }


                        }


                        switch (alt25) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1795:6: COMMA txtdecl
                    	    {
                    	    COMMA145=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtdecl1464); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA145_tree = (Object)adaptor.create(COMMA145);
                    	    adaptor.addChild(root_0, COMMA145_tree);
                    	    }
                    	    pushFollow(FOLLOW_txtdecl_in_txtdecl1466);
                    	    txtdecl146=txtdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtdecl146.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "txtdecl"

    public static class txtlistdecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "txtlistdecl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1798:1: txtlistdecl : ( LETTER ( COMMA txtlistdecl )* | var= LETTER EQUALS let= expr ( COMMA txtlistdecl )* );
    public final eugeneParser.txtlistdecl_return txtlistdecl() throws RecognitionException {
        eugeneParser.txtlistdecl_return retval = new eugeneParser.txtlistdecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token LETTER147=null;
        Token COMMA148=null;
        Token EQUALS150=null;
        Token COMMA151=null;
        eugeneParser.expr_return let = null;

        eugeneParser.txtlistdecl_return txtlistdecl149 = null;

        eugeneParser.txtlistdecl_return txtlistdecl152 = null;


        Object var_tree=null;
        Object LETTER147_tree=null;
        Object COMMA148_tree=null;
        Object EQUALS150_tree=null;
        Object COMMA151_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1799:2: ( LETTER ( COMMA txtlistdecl )* | var= LETTER EQUALS let= expr ( COMMA txtlistdecl )* )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==LETTER) ) {
                int LA29_1 = input.LA(2);

                if ( (LA29_1==EQUALS) ) {
                    alt29=2;
                }
                else if ( (LA29_1==EOF||(LA29_1>=SEMIC && LA29_1<=COMMA)) ) {
                    alt29=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1799:4: LETTER ( COMMA txtlistdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER147=(Token)match(input,LETTER,FOLLOW_LETTER_in_txtlistdecl1479); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER147_tree = (Object)adaptor.create(LETTER147);
                    adaptor.addChild(root_0, LETTER147_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveNoValue((LETTER147!=null?LETTER147.getText():null), "txtList");
                      				}
                      			} else {
                      				declarePrimitiveNoValue((LETTER147!=null?LETTER147.getText():null), "txtList");
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1808:5: ( COMMA txtlistdecl )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==COMMA) ) {
                            int LA27_2 = input.LA(2);

                            if ( (synpred62_eugene()) ) {
                                alt27=1;
                            }


                        }


                        switch (alt27) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1808:6: COMMA txtlistdecl
                    	    {
                    	    COMMA148=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtlistdecl1486); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA148_tree = (Object)adaptor.create(COMMA148);
                    	    adaptor.addChild(root_0, COMMA148_tree);
                    	    }
                    	    pushFollow(FOLLOW_txtlistdecl_in_txtlistdecl1488);
                    	    txtlistdecl149=txtlistdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtlistdecl149.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1809:4: var= LETTER EQUALS let= expr ( COMMA txtlistdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    var=(Token)match(input,LETTER,FOLLOW_LETTER_in_txtlistdecl1497); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    var_tree = (Object)adaptor.create(var);
                    adaptor.addChild(root_0, var_tree);
                    }
                    EQUALS150=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_txtlistdecl1499); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS150_tree = (Object)adaptor.create(EQUALS150);
                    adaptor.addChild(root_0, EQUALS150_tree);
                    }
                    if ( state.backtracking==0 ) {
                      typeList = "txt";
                    }
                    pushFollow(FOLLOW_expr_in_txtlistdecl1505);
                    let=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, let.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveWithValueTxtList((var!=null?var.getText():null), (let!=null?let.p:null));
                      				}
                      			} else {
                      				declarePrimitiveWithValueTxtList((var!=null?var.getText():null), (let!=null?let.p:null));
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1818:5: ( COMMA txtlistdecl )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==COMMA) ) {
                            int LA28_2 = input.LA(2);

                            if ( (synpred64_eugene()) ) {
                                alt28=1;
                            }


                        }


                        switch (alt28) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1818:6: COMMA txtlistdecl
                    	    {
                    	    COMMA151=(Token)match(input,COMMA,FOLLOW_COMMA_in_txtlistdecl1512); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA151_tree = (Object)adaptor.create(COMMA151);
                    	    adaptor.addChild(root_0, COMMA151_tree);
                    	    }
                    	    pushFollow(FOLLOW_txtlistdecl_in_txtlistdecl1514);
                    	    txtlistdecl152=txtlistdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, txtlistdecl152.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "txtlistdecl"

    public static class numlistdecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "numlistdecl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1821:1: numlistdecl : ( LETTER ( COMMA numlistdecl )* | var= LETTER EQUALS let= expr ( COMMA numlistdecl )* );
    public final eugeneParser.numlistdecl_return numlistdecl() throws RecognitionException {
        eugeneParser.numlistdecl_return retval = new eugeneParser.numlistdecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token LETTER153=null;
        Token COMMA154=null;
        Token EQUALS156=null;
        Token COMMA157=null;
        eugeneParser.expr_return let = null;

        eugeneParser.numlistdecl_return numlistdecl155 = null;

        eugeneParser.numlistdecl_return numlistdecl158 = null;


        Object var_tree=null;
        Object LETTER153_tree=null;
        Object COMMA154_tree=null;
        Object EQUALS156_tree=null;
        Object COMMA157_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1822:2: ( LETTER ( COMMA numlistdecl )* | var= LETTER EQUALS let= expr ( COMMA numlistdecl )* )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==LETTER) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==EQUALS) ) {
                    alt32=2;
                }
                else if ( (LA32_1==EOF||(LA32_1>=SEMIC && LA32_1<=COMMA)) ) {
                    alt32=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1822:4: LETTER ( COMMA numlistdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER153=(Token)match(input,LETTER,FOLLOW_LETTER_in_numlistdecl1527); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER153_tree = (Object)adaptor.create(LETTER153);
                    adaptor.addChild(root_0, LETTER153_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveNoValue((LETTER153!=null?LETTER153.getText():null), "txtList");
                      				}
                      			} else {
                      				declarePrimitiveNoValue((LETTER153!=null?LETTER153.getText():null), "txtList");
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1831:5: ( COMMA numlistdecl )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==COMMA) ) {
                            int LA30_2 = input.LA(2);

                            if ( (synpred65_eugene()) ) {
                                alt30=1;
                            }


                        }


                        switch (alt30) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1831:6: COMMA numlistdecl
                    	    {
                    	    COMMA154=(Token)match(input,COMMA,FOLLOW_COMMA_in_numlistdecl1534); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA154_tree = (Object)adaptor.create(COMMA154);
                    	    adaptor.addChild(root_0, COMMA154_tree);
                    	    }
                    	    pushFollow(FOLLOW_numlistdecl_in_numlistdecl1536);
                    	    numlistdecl155=numlistdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, numlistdecl155.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop30;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1832:4: var= LETTER EQUALS let= expr ( COMMA numlistdecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    var=(Token)match(input,LETTER,FOLLOW_LETTER_in_numlistdecl1545); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    var_tree = (Object)adaptor.create(var);
                    adaptor.addChild(root_0, var_tree);
                    }
                    EQUALS156=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_numlistdecl1547); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS156_tree = (Object)adaptor.create(EQUALS156);
                    adaptor.addChild(root_0, EQUALS156_tree);
                    }
                    if ( state.backtracking==0 ) {
                       typeList = "num";
                    }
                    pushFollow(FOLLOW_expr_in_numlistdecl1552);
                    let=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, let.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveWithValueNumList((var!=null?var.getText():null), (let!=null?let.p:null));
                      				}
                      			} else {
                      				declarePrimitiveWithValueNumList((var!=null?var.getText():null), (let!=null?let.p:null));
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1841:5: ( COMMA numlistdecl )*
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( (LA31_0==COMMA) ) {
                            int LA31_2 = input.LA(2);

                            if ( (synpred67_eugene()) ) {
                                alt31=1;
                            }


                        }


                        switch (alt31) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1841:6: COMMA numlistdecl
                    	    {
                    	    COMMA157=(Token)match(input,COMMA,FOLLOW_COMMA_in_numlistdecl1559); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA157_tree = (Object)adaptor.create(COMMA157);
                    	    adaptor.addChild(root_0, COMMA157_tree);
                    	    }
                    	    pushFollow(FOLLOW_numlistdecl_in_numlistdecl1561);
                    	    numlistdecl158=numlistdecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, numlistdecl158.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop31;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "numlistdecl"

    public static class booldecl_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booldecl"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1844:1: booldecl : ( LETTER ( COMMA booldecl )* | var= LETTER EQUALS let= expr );
    public final eugeneParser.booldecl_return booldecl() throws RecognitionException {
        eugeneParser.booldecl_return retval = new eugeneParser.booldecl_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token var=null;
        Token LETTER159=null;
        Token COMMA160=null;
        Token EQUALS162=null;
        eugeneParser.expr_return let = null;

        eugeneParser.booldecl_return booldecl161 = null;


        Object var_tree=null;
        Object LETTER159_tree=null;
        Object COMMA160_tree=null;
        Object EQUALS162_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1845:2: ( LETTER ( COMMA booldecl )* | var= LETTER EQUALS let= expr )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==LETTER) ) {
                int LA34_1 = input.LA(2);

                if ( (LA34_1==EQUALS) ) {
                    alt34=2;
                }
                else if ( (LA34_1==EOF||(LA34_1>=SEMIC && LA34_1<=COMMA)) ) {
                    alt34=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1845:4: LETTER ( COMMA booldecl )*
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER159=(Token)match(input,LETTER,FOLLOW_LETTER_in_booldecl1574); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER159_tree = (Object)adaptor.create(LETTER159);
                    adaptor.addChild(root_0, LETTER159_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveNoValue((LETTER159!=null?LETTER159.getText():null), "bool");
                      				}
                      			} else {
                      				declarePrimitiveNoValue((LETTER159!=null?LETTER159.getText():null), "bool");
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1854:5: ( COMMA booldecl )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==COMMA) ) {
                            int LA33_2 = input.LA(2);

                            if ( (synpred68_eugene()) ) {
                                alt33=1;
                            }


                        }


                        switch (alt33) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1854:6: COMMA booldecl
                    	    {
                    	    COMMA160=(Token)match(input,COMMA,FOLLOW_COMMA_in_booldecl1581); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA160_tree = (Object)adaptor.create(COMMA160);
                    	    adaptor.addChild(root_0, COMMA160_tree);
                    	    }
                    	    pushFollow(FOLLOW_booldecl_in_booldecl1583);
                    	    booldecl161=booldecl();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, booldecl161.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop33;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1855:4: var= LETTER EQUALS let= expr
                    {
                    root_0 = (Object)adaptor.nil();

                    var=(Token)match(input,LETTER,FOLLOW_LETTER_in_booldecl1592); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    var_tree = (Object)adaptor.create(var);
                    adaptor.addChild(root_0, var_tree);
                    }
                    EQUALS162=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_booldecl1594); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUALS162_tree = (Object)adaptor.create(EQUALS162);
                    adaptor.addChild(root_0, EQUALS162_tree);
                    }
                    pushFollow(FOLLOW_expr_in_booldecl1598);
                    let=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, let.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declarePrimitiveWithValueBool((var!=null?var.getText():null), (let!=null?let.p:null));
                      				}
                      			} else {
                      				declarePrimitiveWithValueBool((var!=null?var.getText():null), (let!=null?let.p:null));
                      			}
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "booldecl"

    public static class propertyList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyList"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1867:1: propertyList : ( LETTER COMMA propertyList | LETTER | );
    public final eugeneParser.propertyList_return propertyList() throws RecognitionException {
        eugeneParser.propertyList_return retval = new eugeneParser.propertyList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LETTER163=null;
        Token COMMA164=null;
        Token LETTER166=null;
        eugeneParser.propertyList_return propertyList165 = null;


        Object LETTER163_tree=null;
        Object COMMA164_tree=null;
        Object LETTER166_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1868:2: ( LETTER COMMA propertyList | LETTER | )
            int alt35=3;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LETTER) ) {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==COMMA) ) {
                    alt35=1;
                }
                else if ( (LA35_1==EOF||LA35_1==RIGHTP) ) {
                    alt35=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA35_0==EOF||LA35_0==RIGHTP) ) {
                alt35=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1868:4: LETTER COMMA propertyList
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER163=(Token)match(input,LETTER,FOLLOW_LETTER_in_propertyList1613); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER163_tree = (Object)adaptor.create(LETTER163);
                    adaptor.addChild(root_0, LETTER163_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					propertyListHolder.add((LETTER163!=null?LETTER163.getText():null));
                      				}
                      			} else {
                      				propertyListHolder.add((LETTER163!=null?LETTER163.getText():null));
                      			}
                      		
                    }
                    COMMA164=(Token)match(input,COMMA,FOLLOW_COMMA_in_propertyList1619); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA164_tree = (Object)adaptor.create(COMMA164);
                    adaptor.addChild(root_0, COMMA164_tree);
                    }
                    pushFollow(FOLLOW_propertyList_in_propertyList1621);
                    propertyList165=propertyList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyList165.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1878:4: LETTER
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER166=(Token)match(input,LETTER,FOLLOW_LETTER_in_propertyList1626); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER166_tree = (Object)adaptor.create(LETTER166);
                    adaptor.addChild(root_0, LETTER166_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					propertyListHolder.add((LETTER166!=null?LETTER166.getText():null));
                      				}
                      			} else {
                      				propertyListHolder.add((LETTER166!=null?LETTER166.getText():null));
                      			}
                      		
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1889:2: 
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "propertyList"

    public static class componentList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "componentList"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1891:1: componentList : ( (instJust1= LETTER | class1= LETTER inst1= LETTER | device= DEVICE instDev= LETTER ) COMMA componentList | (instJust2= LETTER | class2= LETTER inst2= LETTER | device2= DEVICE instDev2= LETTER ) | ( declObjects )* );
    public final eugeneParser.componentList_return componentList() throws RecognitionException {
        eugeneParser.componentList_return retval = new eugeneParser.componentList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token instJust1=null;
        Token class1=null;
        Token inst1=null;
        Token device=null;
        Token instDev=null;
        Token instJust2=null;
        Token class2=null;
        Token inst2=null;
        Token device2=null;
        Token instDev2=null;
        Token COMMA167=null;
        eugeneParser.componentList_return componentList168 = null;

        eugeneParser.declObjects_return declObjects169 = null;


        Object instJust1_tree=null;
        Object class1_tree=null;
        Object inst1_tree=null;
        Object device_tree=null;
        Object instDev_tree=null;
        Object instJust2_tree=null;
        Object class2_tree=null;
        Object inst2_tree=null;
        Object device2_tree=null;
        Object instDev2_tree=null;
        Object COMMA167_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:2: ( (instJust1= LETTER | class1= LETTER inst1= LETTER | device= DEVICE instDev= LETTER ) COMMA componentList | (instJust2= LETTER | class2= LETTER inst2= LETTER | device2= DEVICE instDev2= LETTER ) | ( declObjects )* )
            int alt39=3;
            switch ( input.LA(1) ) {
            case LETTER:
                {
                switch ( input.LA(2) ) {
                case LETTER:
                    {
                    switch ( input.LA(3) ) {
                    case LEFTP:
                        {
                        alt39=3;
                        }
                        break;
                    case COMMA:
                        {
                        alt39=1;
                        }
                        break;
                    case EOF:
                    case RIGHTP:
                        {
                        alt39=2;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 39, 4, input);

                        throw nvae;
                    }

                    }
                    break;
                case COMMA:
                    {
                    alt39=1;
                    }
                    break;
                case EOF:
                case RIGHTP:
                    {
                    alt39=2;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 39, 1, input);

                    throw nvae;
                }

                }
                break;
            case DEVICE:
                {
                int LA39_2 = input.LA(2);

                if ( (LA39_2==LETTER) ) {
                    int LA39_7 = input.LA(3);

                    if ( (LA39_7==EOF||LA39_7==RIGHTP) ) {
                        alt39=2;
                    }
                    else if ( (LA39_7==COMMA) ) {
                        alt39=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 39, 7, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 39, 2, input);

                    throw nvae;
                }
                }
                break;
            case EOF:
            case RIGHTP:
                {
                alt39=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:4: (instJust1= LETTER | class1= LETTER inst1= LETTER | device= DEVICE instDev= LETTER ) COMMA componentList
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:4: (instJust1= LETTER | class1= LETTER inst1= LETTER | device= DEVICE instDev= LETTER )
                    int alt36=3;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==LETTER) ) {
                        int LA36_1 = input.LA(2);

                        if ( (LA36_1==LETTER) ) {
                            alt36=2;
                        }
                        else if ( (LA36_1==COMMA) ) {
                            alt36=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 36, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA36_0==DEVICE) ) {
                        alt36=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 0, input);

                        throw nvae;
                    }
                    switch (alt36) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:5: instJust1= LETTER
                            {
                            instJust1=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1647); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            instJust1_tree = (Object)adaptor.create(instJust1);
                            adaptor.addChild(root_0, instJust1_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:24: class1= LETTER inst1= LETTER
                            {
                            class1=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1653); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            class1_tree = (Object)adaptor.create(class1);
                            adaptor.addChild(root_0, class1_tree);
                            }
                            inst1=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1657); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            inst1_tree = (Object)adaptor.create(inst1);
                            adaptor.addChild(root_0, inst1_tree);
                            }

                            }
                            break;
                        case 3 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1892:53: device= DEVICE instDev= LETTER
                            {
                            device=(Token)match(input,DEVICE,FOLLOW_DEVICE_in_componentList1663); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            device_tree = (Object)adaptor.create(device);
                            adaptor.addChild(root_0, device_tree);
                            }
                            instDev=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1667); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            instDev_tree = (Object)adaptor.create(instDev);
                            adaptor.addChild(root_0, instDev_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (instJust1 != null) {
                      						addToComponentList((instJust1!=null?instJust1.getText():null));
                      					} else if (inst1 != null) {
                      						if (partDeclarations.get((inst1!=null?inst1.getText():null)).type.equals((class1!=null?class1.getText():null))) {
                      							addToComponentList((inst1!=null?inst1.getText():null));
                      						} else {
                      							printError("Instance " + (inst1!=null?inst1.getText():null) + " is not a " + (class1!=null?class1.getText():null) + " but a " + partDeclarations.get((inst1!=null?inst1.getText():null)).type);
                      						}
                      					} else if (instDev != null) {
                      						if(deviceDeclarations.containsKey((instDev!=null?instDev.getText():null))) {
                      							addToComponentList((instDev!=null?instDev.getText():null));
                      						}
                      					}
                      				}
                      			} else {
                      				if (instJust1 != null) {
                      					addToComponentList((instJust1!=null?instJust1.getText():null));
                      				} else if (inst1 != null) {
                      					if (partDeclarations.get((inst1!=null?inst1.getText():null)).type.equals((class1!=null?class1.getText():null))) {
                      						addToComponentList((inst1!=null?inst1.getText():null));
                      					} else {
                      						printError("Instance " + (inst1!=null?inst1.getText():null) + " is not a " + (class1!=null?class1.getText():null) + " but a " + partDeclarations.get((inst1!=null?inst1.getText():null)).type);
                      					}
                      				} else if (instDev != null) {
                      						if(deviceDeclarations.containsKey((instDev!=null?instDev.getText():null))) {
                      							addToComponentList((instDev!=null?instDev.getText():null));
                      						}
                      				}
                      			}
                      		
                    }
                    COMMA167=(Token)match(input,COMMA,FOLLOW_COMMA_in_componentList1674); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA167_tree = (Object)adaptor.create(COMMA167);
                    adaptor.addChild(root_0, COMMA167_tree);
                    }
                    pushFollow(FOLLOW_componentList_in_componentList1676);
                    componentList168=componentList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, componentList168.getTree());

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1926:4: (instJust2= LETTER | class2= LETTER inst2= LETTER | device2= DEVICE instDev2= LETTER )
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1926:4: (instJust2= LETTER | class2= LETTER inst2= LETTER | device2= DEVICE instDev2= LETTER )
                    int alt37=3;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==LETTER) ) {
                        int LA37_1 = input.LA(2);

                        if ( (LA37_1==LETTER) ) {
                            alt37=2;
                        }
                        else if ( (LA37_1==EOF||LA37_1==RIGHTP) ) {
                            alt37=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 37, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA37_0==DEVICE) ) {
                        alt37=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);

                        throw nvae;
                    }
                    switch (alt37) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1926:5: instJust2= LETTER
                            {
                            instJust2=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1684); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            instJust2_tree = (Object)adaptor.create(instJust2);
                            adaptor.addChild(root_0, instJust2_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1926:24: class2= LETTER inst2= LETTER
                            {
                            class2=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1690); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            class2_tree = (Object)adaptor.create(class2);
                            adaptor.addChild(root_0, class2_tree);
                            }
                            inst2=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1694); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            inst2_tree = (Object)adaptor.create(inst2);
                            adaptor.addChild(root_0, inst2_tree);
                            }

                            }
                            break;
                        case 3 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1926:53: device2= DEVICE instDev2= LETTER
                            {
                            device2=(Token)match(input,DEVICE,FOLLOW_DEVICE_in_componentList1700); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            device2_tree = (Object)adaptor.create(device2);
                            adaptor.addChild(root_0, device2_tree);
                            }
                            instDev2=(Token)match(input,LETTER,FOLLOW_LETTER_in_componentList1704); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            instDev2_tree = (Object)adaptor.create(instDev2);
                            adaptor.addChild(root_0, instDev2_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (instJust2 != null) {
                      						addToComponentList((instJust2!=null?instJust2.getText():null));
                      					} else if (inst2 != null) {
                      						if (partDeclarations.get((inst2!=null?inst2.getText():null)).type.equals((class2!=null?class2.getText():null))) {
                      							addToComponentList((inst2!=null?inst2.getText():null));
                      						} else {
                      							printError("Instance " + (inst2!=null?inst2.getText():null) + " is not a " + (class2!=null?class2.getText():null) + " but a " + partDeclarations.get((inst2!=null?inst2.getText():null)).type);
                      						}
                      					} else if (instDev2 != null) {
                      						if(deviceDeclarations.containsKey((instDev2!=null?instDev2.getText():null))) {
                      							addToComponentList((instDev2!=null?instDev2.getText():null));
                      						}
                      					}
                      				}
                      			} else {
                      				if (instJust2 != null) {
                      					addToComponentList((instJust2!=null?instJust2.getText():null));
                      				} else if (inst2 != null) {
                      					if (partDeclarations.get((inst2!=null?inst2.getText():null)).type.equals((class2!=null?class2.getText():null))) {
                      						addToComponentList((inst2!=null?inst2.getText():null));
                      					} else {
                      						printError("Instance " + (inst2!=null?inst2.getText():null) + " is not a " + (class2!=null?class2.getText():null) + " but a " + partDeclarations.get((inst2!=null?inst2.getText():null)).type);
                      					}
                      				} else if (instDev2 != null) {
                      						if(deviceDeclarations.containsKey((instDev2!=null?instDev2.getText():null))) {
                      							addToComponentList((instDev2!=null?instDev2.getText():null));
                      						}
                      				}
                      			}
                      		
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1961:3: ( declObjects )*
                    {
                    root_0 = (Object)adaptor.nil();

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()){
                      				if (ifValueStack.peek() == "1") {
                      					compListInside = true;
                      				}
                      			} else {
                      				compListInside = true;
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1969:5: ( declObjects )*
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( (LA38_0==LETTER) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1969:6: declObjects
                    	    {
                    	    pushFollow(FOLLOW_declObjects_in_componentList1719);
                    	    declObjects169=declObjects();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, declObjects169.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "componentList"

    public static class pfunctions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pfunctions"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1972:1: pfunctions : DOT ADDPROPS LEFTP propertyList RIGHTP SEMIC ;
    public final eugeneParser.pfunctions_return pfunctions() throws RecognitionException {
        eugeneParser.pfunctions_return retval = new eugeneParser.pfunctions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT170=null;
        Token ADDPROPS171=null;
        Token LEFTP172=null;
        Token RIGHTP174=null;
        Token SEMIC175=null;
        eugeneParser.propertyList_return propertyList173 = null;


        Object DOT170_tree=null;
        Object ADDPROPS171_tree=null;
        Object LEFTP172_tree=null;
        Object RIGHTP174_tree=null;
        Object SEMIC175_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1973:2: ( DOT ADDPROPS LEFTP propertyList RIGHTP SEMIC )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1973:4: DOT ADDPROPS LEFTP propertyList RIGHTP SEMIC
            {
            root_0 = (Object)adaptor.nil();

            DOT170=(Token)match(input,DOT,FOLLOW_DOT_in_pfunctions1732); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DOT170_tree = (Object)adaptor.create(DOT170);
            adaptor.addChild(root_0, DOT170_tree);
            }
            ADDPROPS171=(Token)match(input,ADDPROPS,FOLLOW_ADDPROPS_in_pfunctions1734); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ADDPROPS171_tree = (Object)adaptor.create(ADDPROPS171);
            adaptor.addChild(root_0, ADDPROPS171_tree);
            }
            LEFTP172=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_pfunctions1736); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFTP172_tree = (Object)adaptor.create(LEFTP172);
            adaptor.addChild(root_0, LEFTP172_tree);
            }
            pushFollow(FOLLOW_propertyList_in_pfunctions1738);
            propertyList173=propertyList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyList173.getTree());
            RIGHTP174=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_pfunctions1740); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHTP174_tree = (Object)adaptor.create(RIGHTP174);
            adaptor.addChild(root_0, RIGHTP174_tree);
            }
            SEMIC175=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_pfunctions1742); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMIC175_tree = (Object)adaptor.create(SEMIC175);
            adaptor.addChild(root_0, SEMIC175_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "pfunctions"

    public static class declObjects_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declObjects"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1976:1: declObjects : (classname= LETTER varname= LETTER LEFTP propertyListInst RIGHTP SEMIC | classname= LETTER varname= LETTER LEFTP propertyListInstDot RIGHTP SEMIC );
    public final eugeneParser.declObjects_return declObjects() throws RecognitionException {
        eugeneParser.declObjects_return retval = new eugeneParser.declObjects_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token classname=null;
        Token varname=null;
        Token LEFTP176=null;
        Token RIGHTP178=null;
        Token SEMIC179=null;
        Token LEFTP180=null;
        Token RIGHTP182=null;
        Token SEMIC183=null;
        eugeneParser.propertyListInst_return propertyListInst177 = null;

        eugeneParser.propertyListInstDot_return propertyListInstDot181 = null;


        Object classname_tree=null;
        Object varname_tree=null;
        Object LEFTP176_tree=null;
        Object RIGHTP178_tree=null;
        Object SEMIC179_tree=null;
        Object LEFTP180_tree=null;
        Object RIGHTP182_tree=null;
        Object SEMIC183_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1977:2: (classname= LETTER varname= LETTER LEFTP propertyListInst RIGHTP SEMIC | classname= LETTER varname= LETTER LEFTP propertyListInstDot RIGHTP SEMIC )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==LETTER) ) {
                int LA40_1 = input.LA(2);

                if ( (LA40_1==LETTER) ) {
                    int LA40_2 = input.LA(3);

                    if ( (LA40_2==LEFTP) ) {
                        int LA40_3 = input.LA(4);

                        if ( (LA40_3==DOT) ) {
                            alt40=2;
                        }
                        else if ( ((LA40_3>=LEFTP && LA40_3<=LEFTSBR)||(LA40_3>=TRUE && LA40_3<=FALSE)||(LA40_3>=LETTER && LA40_3<=REAL)) ) {
                            alt40=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 40, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 40, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1977:4: classname= LETTER varname= LETTER LEFTP propertyListInst RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    classname=(Token)match(input,LETTER,FOLLOW_LETTER_in_declObjects1755); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    classname_tree = (Object)adaptor.create(classname);
                    adaptor.addChild(root_0, classname_tree);
                    }
                    varname=(Token)match(input,LETTER,FOLLOW_LETTER_in_declObjects1759); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    varname_tree = (Object)adaptor.create(varname);
                    adaptor.addChild(root_0, varname_tree);
                    }
                    LEFTP176=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_declObjects1761); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP176_tree = (Object)adaptor.create(LEFTP176);
                    adaptor.addChild(root_0, LEFTP176_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			partType = (classname!=null?classname.getText():null);
                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						declareInstances((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      						if (compListInside) {
                      							addToComponentList((varname!=null?varname.getText():null));
                      						}
                      					}
                      				} else {
                      					declareInstances((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      					if (compListInside) {
                      						addToComponentList((varname!=null?varname.getText():null));
                      					}
                      				}
                      		
                    }
                    pushFollow(FOLLOW_propertyListInst_in_declObjects1767);
                    propertyListInst177=propertyListInst();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyListInst177.getTree());
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						if (propertyValuesHolder.size() != 0) {
                      							setProperties((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      						}
                      					}
                      				} else {
                      					if (propertyValuesHolder.size() != 0) {
                      						setProperties((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      					}
                      				}
                      			
                    }
                    RIGHTP178=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_declObjects1774); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP178_tree = (Object)adaptor.create(RIGHTP178);
                    adaptor.addChild(root_0, RIGHTP178_tree);
                    }
                    SEMIC179=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_declObjects1776); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC179_tree = (Object)adaptor.create(SEMIC179);
                    adaptor.addChild(root_0, SEMIC179_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2007:4: classname= LETTER varname= LETTER LEFTP propertyListInstDot RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    classname=(Token)match(input,LETTER,FOLLOW_LETTER_in_declObjects1783); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    classname_tree = (Object)adaptor.create(classname);
                    adaptor.addChild(root_0, classname_tree);
                    }
                    varname=(Token)match(input,LETTER,FOLLOW_LETTER_in_declObjects1787); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    varname_tree = (Object)adaptor.create(varname);
                    adaptor.addChild(root_0, varname_tree);
                    }
                    LEFTP180=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_declObjects1789); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP180_tree = (Object)adaptor.create(LEFTP180);
                    adaptor.addChild(root_0, LEFTP180_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					declareInstances((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      					if (compListInside) {
                      						addToComponentList((varname!=null?varname.getText():null));
                      					}
                      				}
                      			} else {
                      				declareInstances((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      				if (compListInside) {
                      					addToComponentList((varname!=null?varname.getText():null));
                      				}
                      			}
                      		
                    }
                    pushFollow(FOLLOW_propertyListInstDot_in_declObjects1795);
                    propertyListInstDot181=propertyListInstDot();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyListInstDot181.getTree());
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						setPropertiesDot((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      					}
                      				} else {
                      					setPropertiesDot((classname!=null?classname.getText():null), (varname!=null?varname.getText():null));
                      				}
                      			
                    }
                    RIGHTP182=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_declObjects1802); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP182_tree = (Object)adaptor.create(RIGHTP182);
                    adaptor.addChild(root_0, RIGHTP182_tree);
                    }
                    SEMIC183=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_declObjects1804); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC183_tree = (Object)adaptor.create(SEMIC183);
                    adaptor.addChild(root_0, SEMIC183_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "declObjects"

    public static class propertyListInstDot_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyListInstDot"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2034:1: propertyListInstDot : DOT prop= LETTER LEFTP v1= expr RIGHTP ( COMMA DOT p= LETTER LEFTP v2= expr RIGHTP )* ;
    public final eugeneParser.propertyListInstDot_return propertyListInstDot() throws RecognitionException {
        eugeneParser.propertyListInstDot_return retval = new eugeneParser.propertyListInstDot_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token prop=null;
        Token p=null;
        Token DOT184=null;
        Token LEFTP185=null;
        Token RIGHTP186=null;
        Token COMMA187=null;
        Token DOT188=null;
        Token LEFTP189=null;
        Token RIGHTP190=null;
        eugeneParser.expr_return v1 = null;

        eugeneParser.expr_return v2 = null;


        Object prop_tree=null;
        Object p_tree=null;
        Object DOT184_tree=null;
        Object LEFTP185_tree=null;
        Object RIGHTP186_tree=null;
        Object COMMA187_tree=null;
        Object DOT188_tree=null;
        Object LEFTP189_tree=null;
        Object RIGHTP190_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2035:2: ( DOT prop= LETTER LEFTP v1= expr RIGHTP ( COMMA DOT p= LETTER LEFTP v2= expr RIGHTP )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2035:4: DOT prop= LETTER LEFTP v1= expr RIGHTP ( COMMA DOT p= LETTER LEFTP v2= expr RIGHTP )*
            {
            root_0 = (Object)adaptor.nil();

            DOT184=(Token)match(input,DOT,FOLLOW_DOT_in_propertyListInstDot1815); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DOT184_tree = (Object)adaptor.create(DOT184);
            adaptor.addChild(root_0, DOT184_tree);
            }
            prop=(Token)match(input,LETTER,FOLLOW_LETTER_in_propertyListInstDot1819); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            prop_tree = (Object)adaptor.create(prop);
            adaptor.addChild(root_0, prop_tree);
            }
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					addToPropertyListHolder((prop!=null?prop.getText():null));
              				}
              			} else {
              				addToPropertyListHolder((prop!=null?prop.getText():null));
              			}
              		
            }
            LEFTP185=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_propertyListInstDot1825); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LEFTP185_tree = (Object)adaptor.create(LEFTP185);
            adaptor.addChild(root_0, LEFTP185_tree);
            }
            pushFollow(FOLLOW_expr_in_propertyListInstDot1829);
            v1=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, v1.getTree());
            if ( state.backtracking==0 ) {

              				if (!ifValueStack.empty()) {
              					if (ifValueStack.peek() == "1") {
              						addToPropertyValuesHolder((prop!=null?prop.getText():null), (v1!=null?v1.p:null), (v1!=null?v1.index:0));
              					}
              				} else {
              					addToPropertyValuesHolder((prop!=null?prop.getText():null), (v1!=null?v1.p:null), (v1!=null?v1.index:0));
              				}
              			
            }
            RIGHTP186=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_propertyListInstDot1836); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RIGHTP186_tree = (Object)adaptor.create(RIGHTP186);
            adaptor.addChild(root_0, RIGHTP186_tree);
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2053:13: ( COMMA DOT p= LETTER LEFTP v2= expr RIGHTP )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==COMMA) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2053:14: COMMA DOT p= LETTER LEFTP v2= expr RIGHTP
            	    {
            	    COMMA187=(Token)match(input,COMMA,FOLLOW_COMMA_in_propertyListInstDot1839); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA187_tree = (Object)adaptor.create(COMMA187);
            	    adaptor.addChild(root_0, COMMA187_tree);
            	    }
            	    DOT188=(Token)match(input,DOT,FOLLOW_DOT_in_propertyListInstDot1841); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOT188_tree = (Object)adaptor.create(DOT188);
            	    adaptor.addChild(root_0, DOT188_tree);
            	    }
            	    p=(Token)match(input,LETTER,FOLLOW_LETTER_in_propertyListInstDot1845); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    p_tree = (Object)adaptor.create(p);
            	    adaptor.addChild(root_0, p_tree);
            	    }
            	    if ( state.backtracking==0 ) {

            	      					if (!ifValueStack.empty()) {
            	      						if (ifValueStack.peek() == "1") {
            	      							addToPropertyListHolder((p!=null?p.getText():null));
            	      						}
            	      					} else {
            	      						addToPropertyListHolder((p!=null?p.getText():null));
            	      					}
            	      				
            	    }
            	    LEFTP189=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_propertyListInstDot1853); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    LEFTP189_tree = (Object)adaptor.create(LEFTP189);
            	    adaptor.addChild(root_0, LEFTP189_tree);
            	    }
            	    pushFollow(FOLLOW_expr_in_propertyListInstDot1857);
            	    v2=expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, v2.getTree());
            	    if ( state.backtracking==0 ) {

            	      						if (!ifValueStack.empty()) {
            	      							if (ifValueStack.peek() == "1") {
            	      								addToPropertyValuesHolder((p!=null?p.getText():null), (v2!=null?v2.p:null), (v2!=null?v2.index:0));
            	      							}
            	      						} else {
            	      							addToPropertyValuesHolder((p!=null?p.getText():null), (v2!=null?v2.p:null), (v2!=null?v2.index:0));
            	      						}
            	      					
            	    }
            	    RIGHTP190=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_propertyListInstDot1866); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    RIGHTP190_tree = (Object)adaptor.create(RIGHTP190);
            	    adaptor.addChild(root_0, RIGHTP190_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "propertyListInstDot"

    public static class propertyListInst_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyListInst"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2074:1: propertyListInst : (val1= expr ( COMMA val2= expr )* | );
    public final eugeneParser.propertyListInst_return propertyListInst() throws RecognitionException {
        eugeneParser.propertyListInst_return retval = new eugeneParser.propertyListInst_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA191=null;
        eugeneParser.expr_return val1 = null;

        eugeneParser.expr_return val2 = null;


        Object COMMA191_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2075:2: (val1= expr ( COMMA val2= expr )* | )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==LEFTP||LA43_0==LEFTSBR||(LA43_0>=TRUE && LA43_0<=FALSE)||(LA43_0>=LETTER && LA43_0<=REAL)) ) {
                alt43=1;
            }
            else if ( (LA43_0==RIGHTP) ) {
                alt43=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2076:3: val1= expr ( COMMA val2= expr )*
                    {
                    root_0 = (Object)adaptor.nil();

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					ArrayList<String> propertyList = partDefinitions.get(partType).get(0);
                      					if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("txtList")) {
                      						typeList = "txt";
                      					} else if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("numList")) {
                      						typeList = "num";
                      					}
                      				}
                      			} else {
                      				ArrayList<String> propertyList = partDefinitions.get(partType).get(0);
                      				if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("txtList")) {
                      					typeList = "txt";
                      				} else if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("numList")) {
                      					typeList = "num";
                      				}
                      			}
                      		
                    }
                    pushFollow(FOLLOW_expr_in_propertyListInst1885);
                    val1=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, val1.getTree());
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						propertyValuesHolder.add((val1!=null?val1.p:null));
                      					}
                      				} else {
                      					propertyValuesHolder.add((val1!=null?val1.p:null));
                      				}
                      			
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2103:6: ( COMMA val2= expr )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==COMMA) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2103:7: COMMA val2= expr
                    	    {
                    	    COMMA191=(Token)match(input,COMMA,FOLLOW_COMMA_in_propertyListInst1893); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA191_tree = (Object)adaptor.create(COMMA191);
                    	    adaptor.addChild(root_0, COMMA191_tree);
                    	    }
                    	    if ( state.backtracking==0 ) {

                    	      					if (!ifValueStack.empty()) {
                    	      						if (ifValueStack.peek() == "1") {
                    	      							ArrayList<String> propertyList = partDefinitions.get(partType).get(0);
                    	      							if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("txtList")) {
                    	      								typeList = "txt";
                    	      							} else if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("numList")) {
                    	      								typeList = "num";
                    	      							}
                    	      						}
                    	      					} else {
                    	      						ArrayList<String> propertyList = partDefinitions.get(partType).get(0);
                    	      						if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("txtList")) {
                    	      							typeList = "txt";
                    	      						} else if (propertyDefinitions.get(propertyList.get(propertyValuesHolder.size())).equals("numList")) {
                    	      							typeList = "num";
                    	      						}
                    	      					}
                    	                     
                    	    }
                    	    pushFollow(FOLLOW_expr_in_propertyListInst1903);
                    	    val2=expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, val2.getTree());
                    	    if ( state.backtracking==0 ) {

                    	      						if (!ifValueStack.empty()) {
                    	      							if (ifValueStack.peek() == "1") {
                    	      								propertyValuesHolder.add((val2!=null?val2.p:null));
                    	      							}
                    	      						} else {
                    	      							propertyValuesHolder.add((val2!=null?val2.p:null));
                    	      						}
                    	      					
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2133:2: 
                    {
                    root_0 = (Object)adaptor.nil();

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "propertyListInst"

    public static class print_return extends ParserRuleReturnScope {
        public String concat;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "print"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2135:1: print returns [String concat] : ( PRINT LEFTP (val= expr | ) ( COMMA val2= expr )* RIGHTP SEMIC | device= LETTER '.' PRINT LEFTP (numVar= LETTER | num= NUMBER | ) RIGHTP SEMIC );
    public final eugeneParser.print_return print() throws RecognitionException {
        eugeneParser.print_return retval = new eugeneParser.print_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token device=null;
        Token numVar=null;
        Token num=null;
        Token PRINT192=null;
        Token LEFTP193=null;
        Token COMMA194=null;
        Token RIGHTP195=null;
        Token SEMIC196=null;
        Token char_literal197=null;
        Token PRINT198=null;
        Token LEFTP199=null;
        Token RIGHTP200=null;
        Token SEMIC201=null;
        eugeneParser.expr_return val = null;

        eugeneParser.expr_return val2 = null;


        Object device_tree=null;
        Object numVar_tree=null;
        Object num_tree=null;
        Object PRINT192_tree=null;
        Object LEFTP193_tree=null;
        Object COMMA194_tree=null;
        Object RIGHTP195_tree=null;
        Object SEMIC196_tree=null;
        Object char_literal197_tree=null;
        Object PRINT198_tree=null;
        Object LEFTP199_tree=null;
        Object RIGHTP200_tree=null;
        Object SEMIC201_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2136:2: ( PRINT LEFTP (val= expr | ) ( COMMA val2= expr )* RIGHTP SEMIC | device= LETTER '.' PRINT LEFTP (numVar= LETTER | num= NUMBER | ) RIGHTP SEMIC )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==PRINT) ) {
                alt47=1;
            }
            else if ( (LA47_0==LETTER) ) {
                alt47=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2136:4: PRINT LEFTP (val= expr | ) ( COMMA val2= expr )* RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PRINT192=(Token)match(input,PRINT,FOLLOW_PRINT_in_print1931); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRINT192_tree = (Object)adaptor.create(PRINT192);
                    adaptor.addChild(root_0, PRINT192_tree);
                    }
                    LEFTP193=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_print1933); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP193_tree = (Object)adaptor.create(LEFTP193);
                    adaptor.addChild(root_0, LEFTP193_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2136:16: (val= expr | )
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==LEFTP||LA44_0==LEFTSBR||(LA44_0>=TRUE && LA44_0<=FALSE)||(LA44_0>=LETTER && LA44_0<=REAL)) ) {
                        alt44=1;
                    }
                    else if ( (LA44_0==RIGHTP||LA44_0==COMMA) ) {
                        alt44=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 44, 0, input);

                        throw nvae;
                    }
                    switch (alt44) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2136:17: val= expr
                            {
                            pushFollow(FOLLOW_expr_in_print1938);
                            val=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, val.getTree());

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2136:28: 
                            {
                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.concat = setStatementPrint((val!=null?val.p:null));
                      				}
                      			} else {
                      				retval.concat = setStatementPrint((val!=null?val.p:null));
                      			}
                      		
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2145:5: ( COMMA val2= expr )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==COMMA) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2145:6: COMMA val2= expr
                    	    {
                    	    COMMA194=(Token)match(input,COMMA,FOLLOW_COMMA_in_print1949); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA194_tree = (Object)adaptor.create(COMMA194);
                    	    adaptor.addChild(root_0, COMMA194_tree);
                    	    }
                    	    pushFollow(FOLLOW_expr_in_print1953);
                    	    val2=expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, val2.getTree());
                    	    if ( state.backtracking==0 ) {

                    	      				if (!ifValueStack.empty()) {
                    	      					if (ifValueStack.peek() == "1") {
                    	      						if ((val2!=null?val2.p:null) != null) {
                    	      							if ((val2!=null?val2.p:null).index != -1 && (val2!=null?val2.p:null).type.equals("txtList") && (val2!=null?val2.p:null).txtList.size() != 0) {
                    	      								retval.concat += (val2!=null?val2.p:null).txtList.get((val2!=null?val2.p:null).index);
                    	      							} else if ((val2!=null?val2.p:null).index != -1 && (val2!=null?val2.p:null).type.equals("numList") && (val2!=null?val2.p:null).numList.size() != 0) {
                    	      								retval.concat += (val2!=null?val2.p:null).numList.get((val2!=null?val2.p:null).index).toString();
                    	      							} else {
                    	      								retval.concat += (val2!=null?val2.p:null).getValue().toString();
                    	      							}
                    	      						}
                    	      					}
                    	      				} else {
                    	      					if ((val2!=null?val2.p:null) != null) {
                    	      						if ((val2!=null?val2.p:null).index != -1 && (val2!=null?val2.p:null).type.equals("txtList") && (val2!=null?val2.p:null).txtList.size() != 0) {
                    	      							retval.concat += (val2!=null?val2.p:null).txtList.get((val2!=null?val2.p:null).index);
                    	      						} else if((val2!=null?val2.p:null).index != -1 && (val2!=null?val2.p:null).type.equals("numList") && (val2!=null?val2.p:null).numList.size() != 0) {
                    	      							retval.concat += (val2!=null?val2.p:null).numList.get((val2!=null?val2.p:null).index).toString();
                    	      						} else {
                    	      							retval.concat += (val2!=null?val2.p:null).getValue().toString();
                    	      						}
                    	      					}
                    	      				}

                    	      			
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop45;
                        }
                    } while (true);

                    RIGHTP195=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_print1963); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP195_tree = (Object)adaptor.create(RIGHTP195);
                    adaptor.addChild(root_0, RIGHTP195_tree);
                    }
                    SEMIC196=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_print1965); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC196_tree = (Object)adaptor.create(SEMIC196);
                    adaptor.addChild(root_0, SEMIC196_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2172:6: device= LETTER '.' PRINT LEFTP (numVar= LETTER | num= NUMBER | ) RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    device=(Token)match(input,LETTER,FOLLOW_LETTER_in_print1974); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    device_tree = (Object)adaptor.create(device);
                    adaptor.addChild(root_0, device_tree);
                    }
                    char_literal197=(Token)match(input,DOT,FOLLOW_DOT_in_print1976); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal197_tree = (Object)adaptor.create(char_literal197);
                    adaptor.addChild(root_0, char_literal197_tree);
                    }
                    PRINT198=(Token)match(input,PRINT,FOLLOW_PRINT_in_print1978); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRINT198_tree = (Object)adaptor.create(PRINT198);
                    adaptor.addChild(root_0, PRINT198_tree);
                    }
                    LEFTP199=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_print1980); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP199_tree = (Object)adaptor.create(LEFTP199);
                    adaptor.addChild(root_0, LEFTP199_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2172:36: (numVar= LETTER | num= NUMBER | )
                    int alt46=3;
                    switch ( input.LA(1) ) {
                    case LETTER:
                        {
                        alt46=1;
                        }
                        break;
                    case NUMBER:
                        {
                        alt46=2;
                        }
                        break;
                    case RIGHTP:
                        {
                        alt46=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 46, 0, input);

                        throw nvae;
                    }

                    switch (alt46) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2172:37: numVar= LETTER
                            {
                            numVar=(Token)match(input,LETTER,FOLLOW_LETTER_in_print1985); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            numVar_tree = (Object)adaptor.create(numVar);
                            adaptor.addChild(root_0, numVar_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2172:53: num= NUMBER
                            {
                            num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_print1991); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            num_tree = (Object)adaptor.create(num);
                            adaptor.addChild(root_0, num_tree);
                            }

                            }
                            break;
                        case 3 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2172:66: 
                            {
                            }
                            break;

                    }

                    RIGHTP200=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_print1997); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP200_tree = (Object)adaptor.create(RIGHTP200);
                    adaptor.addChild(root_0, RIGHTP200_tree);
                    }
                    SEMIC201=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_print1999); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC201_tree = (Object)adaptor.create(SEMIC201);
                    adaptor.addChild(root_0, SEMIC201_tree);
                    }
                    if ( state.backtracking==0 ) {

                      				if (!ifValueStack.empty()) {
                      					if (ifValueStack.peek() == "1") {
                      						if(deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      							if((numVar!=null?numVar.getText():null) != null) {
                      								if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      									int amount = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      									retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), amount);
                      								} else
                      									printError((numVar!=null?numVar.getText():null) + " has not been declared as a primitive.");
                      							} else if((num!=null?num.getText():null) != null) {
                      								retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), Integer.parseInt((num!=null?num.getText():null)));
                      							} else
                      								retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), -1);
                      						} else 
                      							printError((device!=null?device.getText():null) + " has not been declared as a Device.");
                      					}
                      				} else {
                      					if(deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						if((numVar!=null?numVar.getText():null) != null) {
                      							if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      								int amount = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      								retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), amount);
                      							} else
                      								printError((numVar!=null?numVar.getText():null) + " has not been declared as a primitive.");
                      						} else if((num!=null?num.getText():null) != null) {
                      							retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), Integer.parseInt((num!=null?num.getText():null)));
                      						} else
                      							retval.concat = printDevices(deviceDeclarations.get((device!=null?device.getText():null)), -1);
                      					} else 
                      						printError((device!=null?device.getText():null) + " has not been declared as a Device.");
                      					
                      				}
                      			
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "print"

    public static class permute_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "permute"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2209:1: permute : ( PERMUTE LEFTP device= LETTER RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC );
    public final eugeneParser.permute_return permute() throws RecognitionException {
        eugeneParser.permute_return retval = new eugeneParser.permute_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token device=null;
        Token num=null;
        Token numVar=null;
        Token degree=null;
        Token PERMUTE202=null;
        Token LEFTP203=null;
        Token RIGHTP204=null;
        Token SEMIC205=null;
        Token PERMUTE206=null;
        Token LEFTP207=null;
        Token COMMA208=null;
        Token RIGHTP209=null;
        Token SEMIC210=null;
        Token PERMUTE211=null;
        Token LEFTP212=null;
        Token COMMA213=null;
        Token COMMA214=null;
        Token RIGHTP215=null;
        Token SEMIC216=null;
        Token PERMUTE217=null;
        Token LEFTP218=null;
        Token COMMA219=null;
        Token RIGHTP220=null;
        Token SEMIC221=null;

        Object device_tree=null;
        Object num_tree=null;
        Object numVar_tree=null;
        Object degree_tree=null;
        Object PERMUTE202_tree=null;
        Object LEFTP203_tree=null;
        Object RIGHTP204_tree=null;
        Object SEMIC205_tree=null;
        Object PERMUTE206_tree=null;
        Object LEFTP207_tree=null;
        Object COMMA208_tree=null;
        Object RIGHTP209_tree=null;
        Object SEMIC210_tree=null;
        Object PERMUTE211_tree=null;
        Object LEFTP212_tree=null;
        Object COMMA213_tree=null;
        Object COMMA214_tree=null;
        Object RIGHTP215_tree=null;
        Object SEMIC216_tree=null;
        Object PERMUTE217_tree=null;
        Object LEFTP218_tree=null;
        Object COMMA219_tree=null;
        Object RIGHTP220_tree=null;
        Object SEMIC221_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2210:2: ( PERMUTE LEFTP device= LETTER RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC )
            int alt50=4;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2210:4: PERMUTE LEFTP device= LETTER RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PERMUTE202=(Token)match(input,PERMUTE,FOLLOW_PERMUTE_in_permute2015); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERMUTE202_tree = (Object)adaptor.create(PERMUTE202);
                    adaptor.addChild(root_0, PERMUTE202_tree);
                    }
                    LEFTP203=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_permute2017); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP203_tree = (Object)adaptor.create(LEFTP203);
                    adaptor.addChild(root_0, LEFTP203_tree);
                    }
                    device=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2021); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    device_tree = (Object)adaptor.create(device);
                    adaptor.addChild(root_0, device_tree);
                    }
                    RIGHTP204=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_permute2023); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP204_tree = (Object)adaptor.create(RIGHTP204);
                    adaptor.addChild(root_0, RIGHTP204_tree);
                    }
                    SEMIC205=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_permute2025); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC205_tree = (Object)adaptor.create(SEMIC205);
                    adaptor.addChild(root_0, SEMIC205_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						permute((device!=null?device.getText():null), -1, "");
                      					}
                      				}
                      			} else {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						permute((device!=null?device.getText():null), -1, "");
                      					}
                      			}
                      		
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2228:5: PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PERMUTE206=(Token)match(input,PERMUTE,FOLLOW_PERMUTE_in_permute2035); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERMUTE206_tree = (Object)adaptor.create(PERMUTE206);
                    adaptor.addChild(root_0, PERMUTE206_tree);
                    }
                    LEFTP207=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_permute2037); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP207_tree = (Object)adaptor.create(LEFTP207);
                    adaptor.addChild(root_0, LEFTP207_tree);
                    }
                    device=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2041); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    device_tree = (Object)adaptor.create(device);
                    adaptor.addChild(root_0, device_tree);
                    }
                    COMMA208=(Token)match(input,COMMA,FOLLOW_COMMA_in_permute2043); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA208_tree = (Object)adaptor.create(COMMA208);
                    adaptor.addChild(root_0, COMMA208_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2228:39: (num= NUMBER | numVar= LETTER )
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==NUMBER) ) {
                        alt48=1;
                    }
                    else if ( (LA48_0==LETTER) ) {
                        alt48=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 48, 0, input);

                        throw nvae;
                    }
                    switch (alt48) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2228:40: num= NUMBER
                            {
                            num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_permute2048); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            num_tree = (Object)adaptor.create(num);
                            adaptor.addChild(root_0, num_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2228:53: numVar= LETTER
                            {
                            numVar=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2054); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            numVar_tree = (Object)adaptor.create(numVar);
                            adaptor.addChild(root_0, numVar_tree);
                            }

                            }
                            break;

                    }

                    RIGHTP209=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_permute2058); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP209_tree = (Object)adaptor.create(RIGHTP209);
                    adaptor.addChild(root_0, RIGHTP209_tree);
                    }
                    SEMIC210=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_permute2060); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC210_tree = (Object)adaptor.create(SEMIC210);
                    adaptor.addChild(root_0, SEMIC210_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						if((num!=null?num.getText():null) != null)
                      							permute((device!=null?device.getText():null), Integer.parseInt((num!=null?num.getText():null)), "");
                      						else if((numVar!=null?numVar.getText():null) != null) { 
                      							if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      								int cap = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      								permute((device!=null?device.getText():null), cap, "");
                      							} else
                      								printError("Variable " + (numVar!=null?numVar.getText():null) + " has not been declared as a primitive type.");
                      						}
                      					}
                      				}
                      			} else {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						if((num!=null?num.getText():null) != null)
                      							permute((device!=null?device.getText():null), Integer.parseInt((num!=null?num.getText():null)), "");
                      						else if((numVar!=null?numVar.getText():null) != null) {
                      							if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      								int cap = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      								permute((device!=null?device.getText():null), cap, "");
                      							} else
                      								printError("Variable " + (numVar!=null?numVar.getText():null) + " has not been declared as a primitive type.");
                      							
                      						}
                      					}
                      			}
                      		
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2263:5: PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PERMUTE211=(Token)match(input,PERMUTE,FOLLOW_PERMUTE_in_permute2070); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERMUTE211_tree = (Object)adaptor.create(PERMUTE211);
                    adaptor.addChild(root_0, PERMUTE211_tree);
                    }
                    LEFTP212=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_permute2072); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP212_tree = (Object)adaptor.create(LEFTP212);
                    adaptor.addChild(root_0, LEFTP212_tree);
                    }
                    device=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2076); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    device_tree = (Object)adaptor.create(device);
                    adaptor.addChild(root_0, device_tree);
                    }
                    COMMA213=(Token)match(input,COMMA,FOLLOW_COMMA_in_permute2078); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA213_tree = (Object)adaptor.create(COMMA213);
                    adaptor.addChild(root_0, COMMA213_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2263:39: (num= NUMBER | numVar= LETTER )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==NUMBER) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==LETTER) ) {
                        alt49=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 49, 0, input);

                        throw nvae;
                    }
                    switch (alt49) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2263:40: num= NUMBER
                            {
                            num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_permute2083); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            num_tree = (Object)adaptor.create(num);
                            adaptor.addChild(root_0, num_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2263:53: numVar= LETTER
                            {
                            numVar=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2089); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            numVar_tree = (Object)adaptor.create(numVar);
                            adaptor.addChild(root_0, numVar_tree);
                            }

                            }
                            break;

                    }

                    COMMA214=(Token)match(input,COMMA,FOLLOW_COMMA_in_permute2092); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA214_tree = (Object)adaptor.create(COMMA214);
                    adaptor.addChild(root_0, COMMA214_tree);
                    }
                    degree=(Token)input.LT(1);
                    if ( (input.LA(1)>=STRICT && input.LA(1)<=FLEXIBLE) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(degree));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    RIGHTP215=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_permute2104); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP215_tree = (Object)adaptor.create(RIGHTP215);
                    adaptor.addChild(root_0, RIGHTP215_tree);
                    }
                    SEMIC216=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_permute2106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC216_tree = (Object)adaptor.create(SEMIC216);
                    adaptor.addChild(root_0, SEMIC216_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						if((num!=null?num.getText():null) != null)
                      							permute((device!=null?device.getText():null), Integer.parseInt((num!=null?num.getText():null)), (degree!=null?degree.getText():null));
                      						else if((numVar!=null?numVar.getText():null) != null) {
                      							if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      								int cap = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      								permute((device!=null?device.getText():null), cap, (degree!=null?degree.getText():null));
                      							} else
                      								printError("Variable " + (numVar!=null?numVar.getText():null) + " has not been declared as a primitive type.");
                      						}
                      					}
                      				}
                      			} else {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						if((num!=null?num.getText():null) != null)
                      							permute((device!=null?device.getText():null), Integer.parseInt((num!=null?num.getText():null)), (degree!=null?degree.getText():null));
                      						else if((numVar!=null?numVar.getText():null) != null) {
                      							if(primitiveDeclarations.containsKey((numVar!=null?numVar.getText():null))) {
                      								int cap = (int)primitiveDeclarations.get((numVar!=null?numVar.getText():null)).num;
                      								permute((device!=null?device.getText():null), cap, (degree!=null?degree.getText():null));
                      							} else
                      								printError("Variable " + (numVar!=null?numVar.getText():null) + " has not been declared as a primitive type.");
                      						}
                      					}
                      			}
                      		
                    }

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2297:5: PERMUTE LEFTP device= LETTER COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC
                    {
                    root_0 = (Object)adaptor.nil();

                    PERMUTE217=(Token)match(input,PERMUTE,FOLLOW_PERMUTE_in_permute2116); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERMUTE217_tree = (Object)adaptor.create(PERMUTE217);
                    adaptor.addChild(root_0, PERMUTE217_tree);
                    }
                    LEFTP218=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_permute2118); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTP218_tree = (Object)adaptor.create(LEFTP218);
                    adaptor.addChild(root_0, LEFTP218_tree);
                    }
                    device=(Token)match(input,LETTER,FOLLOW_LETTER_in_permute2122); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    device_tree = (Object)adaptor.create(device);
                    adaptor.addChild(root_0, device_tree);
                    }
                    COMMA219=(Token)match(input,COMMA,FOLLOW_COMMA_in_permute2124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA219_tree = (Object)adaptor.create(COMMA219);
                    adaptor.addChild(root_0, COMMA219_tree);
                    }
                    degree=(Token)input.LT(1);
                    if ( (input.LA(1)>=STRICT && input.LA(1)<=FLEXIBLE) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(degree));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    RIGHTP220=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_permute2136); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTP220_tree = (Object)adaptor.create(RIGHTP220);
                    adaptor.addChild(root_0, RIGHTP220_tree);
                    }
                    SEMIC221=(Token)match(input,SEMIC,FOLLOW_SEMIC_in_permute2138); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMIC221_tree = (Object)adaptor.create(SEMIC221);
                    adaptor.addChild(root_0, SEMIC221_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      						printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      					} else {
                      						
                      						permute((device!=null?device.getText():null), -1 , (degree!=null?degree.getText():null));
                      					}
                      				}
                      			} else {
                      				if (!deviceDeclarations.containsKey((device!=null?device.getText():null))) {
                      					printError("Device " + (device!=null?device.getText():null) + " does not exist.");
                      				} else {
                      					permute((device!=null?device.getText():null), -1 , (degree!=null?degree.getText():null));
                      				}
                      			}
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "permute"

    public static class exprP_return extends ParserRuleReturnScope {
        public Primitive p;
        public String inst;
        public int index;
        public String listAddress;
        public Primitive primVariable;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exprP"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2318:1: exprP returns [Primitive p, String inst, int index, String listAddress, Primitive primVariable] : (instance= LETTER | multinst= exprD ) DOT property ;
    public final eugeneParser.exprP_return exprP() throws RecognitionException {
        eugeneParser.exprP_return retval = new eugeneParser.exprP_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token instance=null;
        Token DOT222=null;
        eugeneParser.exprD_return multinst = null;

        eugeneParser.property_return property223 = null;


        Object instance_tree=null;
        Object DOT222_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2319:2: ( (instance= LETTER | multinst= exprD ) DOT property )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2319:4: (instance= LETTER | multinst= exprD ) DOT property
            {
            root_0 = (Object)adaptor.nil();

            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2319:4: (instance= LETTER | multinst= exprD )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==LETTER) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==LEFTSBR) ) {
                    alt51=2;
                }
                else if ( (LA51_1==DOT) ) {
                    alt51=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2319:5: instance= LETTER
                    {
                    instance=(Token)match(input,LETTER,FOLLOW_LETTER_in_exprP2160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    instance_tree = (Object)adaptor.create(instance);
                    adaptor.addChild(root_0, instance_tree);
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2319:21: multinst= exprD
                    {
                    pushFollow(FOLLOW_exprD_in_exprP2164);
                    multinst=exprD();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, multinst.getTree());

                    }
                    break;

            }

            DOT222=(Token)match(input,DOT,FOLLOW_DOT_in_exprP2167); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DOT222_tree = (Object)adaptor.create(DOT222);
            adaptor.addChild(root_0, DOT222_tree);
            }
            pushFollow(FOLLOW_property_in_exprP2169);
            property223=property();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, property223.getTree());
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					retval.index = (property223!=null?property223.index:0);
              					if ((property223!=null?property223.listAddress:null) != null) {
              						retval.listAddress = (property223!=null?property223.listAddress:null);
              					}
              					if ((multinst!=null?multinst.inst:null) != null) {
              						if (partDeclarations.containsKey((multinst!=null?multinst.inst:null))) {
              							retval.p = new Primitive();
              							retval.primVariable = getPropertyValue((multinst!=null?multinst.inst:null), (property223!=null?property223.prop:null), retval.p, (property223!=null?property223.index:0));
              						} else if (deviceDeclarations.containsKey((multinst!=null?multinst.inst:null))) {
              							retval.p = getDevicePropertyValue((multinst!=null?multinst.inst:null), (property223!=null?property223.prop:null), (property223!=null?property223.index:0));
              						}
              						retval.inst = (multinst!=null?multinst.inst:null);
              					} else {
              						if (partDeclarations.containsKey((instance!=null?instance.getText():null))) {
              							retval.p = new Primitive();
              							retval.primVariable = getPropertyValue((instance!=null?instance.getText():null), (property223!=null?property223.prop:null), retval.p, (property223!=null?property223.index:0));
              						} else if (deviceDeclarations.containsKey((instance!=null?instance.getText():null))) {
              							retval.p = getDevicePropertyValue((instance!=null?instance.getText():null), (property223!=null?property223.prop:null), (property223!=null?property223.index:0));
              						}
              						retval.inst = (instance!=null?instance.getText():null);
              					}
              				}
              			} else {
              				retval.index = (property223!=null?property223.index:0);
              				if ((property223!=null?property223.listAddress:null) != null) {
              					retval.listAddress = (property223!=null?property223.listAddress:null);
              				}
              				if ((multinst!=null?multinst.inst:null) != null) {
              					if (partDeclarations.containsKey((multinst!=null?multinst.inst:null))) {
              						retval.p = new Primitive();
              						retval.primVariable = getPropertyValue((multinst!=null?multinst.inst:null), (property223!=null?property223.prop:null), retval.p, (property223!=null?property223.index:0));
              					} else if(deviceDeclarations.containsKey((multinst!=null?multinst.inst:null))) {
              						retval.p = getDevicePropertyValue((multinst!=null?multinst.inst:null), (property223!=null?property223.prop:null), (property223!=null?property223.index:0));
              					}
              					retval.inst = (multinst!=null?multinst.inst:null);
              				} else {
              					if (partDeclarations.containsKey((instance!=null?instance.getText():null))) {
              						retval.p = new Primitive();
              						retval.primVariable = getPropertyValue((instance!=null?instance.getText():null), (property223!=null?property223.prop:null), retval.p, (property223!=null?property223.index:0));
              					} else if (deviceDeclarations.containsKey((instance!=null?instance.getText():null))) {
              						retval.p = getDevicePropertyValue((instance!=null?instance.getText():null), (property223!=null?property223.prop:null), (property223!=null?property223.index:0));
              					}
              					retval.inst = (instance!=null?instance.getText():null);
              				}
              			}
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "exprP"

    public static class property_return extends ParserRuleReturnScope {
        public String prop;
        public int index = -1;
        public String listAddress;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "property"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2371:1: property returns [String prop, int index = -1, String listAddress] : ( LETTER | LETTER LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR );
    public final eugeneParser.property_return property() throws RecognitionException {
        eugeneParser.property_return retval = new eugeneParser.property_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token num=null;
        Token r=null;
        Token LETTER224=null;
        Token LETTER225=null;
        Token LEFTSBR226=null;
        Token RIGHTSBR227=null;

        Object num_tree=null;
        Object r_tree=null;
        Object LETTER224_tree=null;
        Object LETTER225_tree=null;
        Object LEFTSBR226_tree=null;
        Object RIGHTSBR227_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2372:2: ( LETTER | LETTER LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==LETTER) ) {
                int LA53_1 = input.LA(2);

                if ( (LA53_1==LEFTSBR) ) {
                    alt53=2;
                }
                else if ( (LA53_1==EOF||(LA53_1>=PLUS && LA53_1<=DIV)||LA53_1==RIGHTP||LA53_1==RIGHTSBR||LA53_1==EQUALS||(LA53_1>=SEMIC && LA53_1<=COMMA)||(LA53_1>=BEFORE && LA53_1<=NOT)) ) {
                    alt53=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 53, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2372:4: LETTER
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER224=(Token)match(input,LETTER,FOLLOW_LETTER_in_property2188); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER224_tree = (Object)adaptor.create(LETTER224);
                    adaptor.addChild(root_0, LETTER224_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.prop = (LETTER224!=null?LETTER224.getText():null);
                      					retval.index = -1;
                      				}
                      			} else {
                      				retval.prop = (LETTER224!=null?LETTER224.getText():null);
                      				retval.index = -1;
                      			}
                      		
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2384:4: LETTER LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER225=(Token)match(input,LETTER,FOLLOW_LETTER_in_property2197); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER225_tree = (Object)adaptor.create(LETTER225);
                    adaptor.addChild(root_0, LETTER225_tree);
                    }
                    LEFTSBR226=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_property2199); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR226_tree = (Object)adaptor.create(LEFTSBR226);
                    adaptor.addChild(root_0, LEFTSBR226_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2384:19: (num= NUMBER | r= REAL )
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==NUMBER) ) {
                        alt52=1;
                    }
                    else if ( (LA52_0==REAL) ) {
                        alt52=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 0, input);

                        throw nvae;
                    }
                    switch (alt52) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2384:20: num= NUMBER
                            {
                            num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_property2204); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            num_tree = (Object)adaptor.create(num);
                            adaptor.addChild(root_0, num_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2384:31: r= REAL
                            {
                            r=(Token)match(input,REAL,FOLLOW_REAL_in_property2208); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            r_tree = (Object)adaptor.create(r);
                            adaptor.addChild(root_0, r_tree);
                            }

                            }
                            break;

                    }

                    RIGHTSBR227=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_property2211); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR227_tree = (Object)adaptor.create(RIGHTSBR227);
                    adaptor.addChild(root_0, RIGHTSBR227_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.prop = (LETTER225!=null?LETTER225.getText():null);
                      					retval.listAddress = (LETTER225!=null?LETTER225.getText():null);
                      					if (r != null) {
                      						printError("Invalid index, must be an integer.");
                      					} else {
                      						retval.index = Integer.parseInt((num!=null?num.getText():null));
                      					}
                      				}
                      			} else {
                      				retval.prop = (LETTER225!=null?LETTER225.getText():null);
                      				retval.listAddress = (LETTER225!=null?LETTER225.getText():null);
                      				if (r != null) {
                      					printError("Invalid index, must be an integer.");
                      				} else {
                      					retval.index = Integer.parseInt((num!=null?num.getText():null));
                      				}
                      			}
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "property"

    public static class exprD_return extends ParserRuleReturnScope {
        public String inst;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exprD"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2409:1: exprD returns [String inst] : instance= LETTER ( LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR )+ ;
    public final eugeneParser.exprD_return exprD() throws RecognitionException {
        eugeneParser.exprD_return retval = new eugeneParser.exprD_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token instance=null;
        Token num=null;
        Token r=null;
        Token LEFTSBR228=null;
        Token RIGHTSBR229=null;

        Object instance_tree=null;
        Object num_tree=null;
        Object r_tree=null;
        Object LEFTSBR228_tree=null;
        Object RIGHTSBR229_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:2: (instance= LETTER ( LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR )+ )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:4: instance= LETTER ( LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR )+
            {
            root_0 = (Object)adaptor.nil();

            instance=(Token)match(input,LETTER,FOLLOW_LETTER_in_exprD2233); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            instance_tree = (Object)adaptor.create(instance);
            adaptor.addChild(root_0, instance_tree);
            }
            if ( state.backtracking==0 ) {
              retval.inst = (instance!=null?instance.getText():null);
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:46: ( LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR )+
            int cnt55=0;
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==LEFTSBR) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:48: LEFTSBR (num= NUMBER | r= REAL ) RIGHTSBR
            	    {
            	    LEFTSBR228=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_exprD2239); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    LEFTSBR228_tree = (Object)adaptor.create(LEFTSBR228);
            	    adaptor.addChild(root_0, LEFTSBR228_tree);
            	    }
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:56: (num= NUMBER | r= REAL )
            	    int alt54=2;
            	    int LA54_0 = input.LA(1);

            	    if ( (LA54_0==NUMBER) ) {
            	        alt54=1;
            	    }
            	    else if ( (LA54_0==REAL) ) {
            	        alt54=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 54, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt54) {
            	        case 1 :
            	            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:57: num= NUMBER
            	            {
            	            num=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_exprD2244); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            num_tree = (Object)adaptor.create(num);
            	            adaptor.addChild(root_0, num_tree);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2410:68: r= REAL
            	            {
            	            r=(Token)match(input,REAL,FOLLOW_REAL_in_exprD2248); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            r_tree = (Object)adaptor.create(r);
            	            adaptor.addChild(root_0, r_tree);
            	            }

            	            }
            	            break;

            	    }

            	    RIGHTSBR229=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_exprD2251); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    RIGHTSBR229_tree = (Object)adaptor.create(RIGHTSBR229);
            	    adaptor.addChild(root_0, RIGHTSBR229_tree);
            	    }
            	    if ( state.backtracking==0 ) {

            	      			if (!ifValueStack.empty()) {
            	      				if (ifValueStack.peek() == "1") {
            	      					if (deviceDeclarations.containsKey(retval.inst)) {
            	      						ArrayList<String> comp = deviceDeclarations.get(retval.inst).components;
            	      						if (r !=  null) {
            	      							printError("Invalid index.");
            	      						} else {
            	      							int n = Integer.parseInt((num!=null?num.getText():null));
            	      							if (n > comp.size()) {
            	      								printError("Index out of bounds, index + " + n + " and list size " + comp.size() + ".");
            	      							} else {
            	      								retval.inst = comp.get(n);
            	      							}
            	      						}
            	      					} else if (partDeclarations.containsKey(retval.inst)) {
            	      						if (r !=  null)
            	      							printError("Invalid index.");
            	      						else {
            	      							retval.inst = partDeclarations.get(retval.inst).name;
            	      						}
            	      					} else {
            	      						printError("Device " + instance + " does not exist.");
            	      					}
            	      				}
            	      			} else {
            	      				if (deviceDeclarations.containsKey(retval.inst)) {
            	      					ArrayList<String> comp = deviceDeclarations.get(retval.inst).components;
            	      					if (r !=  null) {
            	      						printError("Invalid index.");
            	      					} else {
            	      						int n = Integer.parseInt((num!=null?num.getText():null));
            	      						if (n > comp.size()) {
            	      							printError("Index out of bounds, index + " + n + " and list size " + comp.size() + ".");
            	      						} else {
            	      							retval.inst = comp.get(n);
            	      						}
            	      					}
            	      				} else if (partDeclarations.containsKey(retval.inst)) {
            	      					if (r !=  null) {
            	      						printError("Invalid index.");
            	      					} else {
            	      						retval.inst = partDeclarations.get(retval.inst).name;
            	      					}
            	      				} else {
            	      					printError("Device " + instance + " does not exist.");
            	      				}
            	      			}
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt55 >= 1 ) break loop55;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(55, input);
                        throw eee;
                }
                cnt55++;
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "exprD"

    public static class expr_return extends ParserRuleReturnScope {
        public Primitive p;
        public String instance;
        public int index;
        public String listAddress;
        public Primitive primVariable;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2462:1: expr returns [Primitive p, String instance, int index, String listAddress, Primitive primVariable] : e= multExpr ( '+' e= multExpr | '-' e= multExpr )* ;
    public final eugeneParser.expr_return expr() throws RecognitionException {
        eugeneParser.expr_return retval = new eugeneParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal230=null;
        Token char_literal231=null;
        eugeneParser.multExpr_return e = null;


        Object char_literal230_tree=null;
        Object char_literal231_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2463:2: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2463:4: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multExpr_in_expr2275);
            e=multExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					retval.p = copyPrimitive((e!=null?e.p:null));
              					retval.instance = (e!=null?e.instance:null);
              					retval.index = (e!=null?e.index:0); //should be changed later
              					if ((e!=null?e.listAddress:null) != null) {
              						retval.listAddress = (e!=null?e.listAddress:null);
              					}
              					retval.primVariable = (e!=null?e.primVariable:null);
              				}
              			} else {
              				retval.p = copyPrimitive((e!=null?e.p:null));
              				retval.instance = (e!=null?e.instance:null);
              				retval.index = (e!=null?e.index:0);//should be changed later
              				if ((e!=null?e.listAddress:null) != null) {
              					retval.listAddress = (e!=null?e.listAddress:null);
              				}
              				retval.primVariable = (e!=null?e.primVariable:null);
              			}
              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2484:5: ( '+' e= multExpr | '-' e= multExpr )*
            loop56:
            do {
                int alt56=3;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==PLUS) ) {
                    alt56=1;
                }
                else if ( (LA56_0==MINUS) ) {
                    alt56=2;
                }


                switch (alt56) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2484:6: '+' e= multExpr
            	    {
            	    char_literal230=(Token)match(input,PLUS,FOLLOW_PLUS_in_expr2282); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal230_tree = (Object)adaptor.create(char_literal230);
            	    adaptor.addChild(root_0, char_literal230_tree);
            	    }
            	    pushFollow(FOLLOW_multExpr_in_expr2286);
            	    e=multExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            	    if ( state.backtracking==0 ) {

            	      				if (!ifValueStack.empty()) {
            	      					if (ifValueStack.peek() == "1") {
            	      						doMinPlusOp((e!=null?e.p:null), retval.p, "+");
            	      					}
            	      				} else {
            	      					doMinPlusOp((e!=null?e.p:null), retval.p, "+");
            	      				}
            	      			
            	    }

            	    }
            	    break;
            	case 2 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2493:8: '-' e= multExpr
            	    {
            	    char_literal231=(Token)match(input,MINUS,FOLLOW_MINUS_in_expr2295); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal231_tree = (Object)adaptor.create(char_literal231);
            	    adaptor.addChild(root_0, char_literal231_tree);
            	    }
            	    pushFollow(FOLLOW_multExpr_in_expr2299);
            	    e=multExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            	    if ( state.backtracking==0 ) {

            	      					if (!ifValueStack.empty()) {
            	      						if (ifValueStack.peek() == "1") {
            	      							doMinPlusOp((e!=null?e.p:null), retval.p, "-");
            	      						}
            	      					} else {
            	      						doMinPlusOp((e!=null?e.p:null), retval.p, "-");
            	      					}
            	      				
            	    }

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class multExpr_return extends ParserRuleReturnScope {
        public Primitive p;
        public String instance;
        public int index;
        public String listAddress;
        public Primitive primVariable;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multExpr"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2505:1: multExpr returns [Primitive p, String instance, int index, String listAddress, Primitive primVariable] : e= atom ( (mul= '*' | div= '/' ) e= atom )* ;
    public final eugeneParser.multExpr_return multExpr() throws RecognitionException {
        eugeneParser.multExpr_return retval = new eugeneParser.multExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token mul=null;
        Token div=null;
        eugeneParser.atom_return e = null;


        Object mul_tree=null;
        Object div_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2506:2: (e= atom ( (mul= '*' | div= '/' ) e= atom )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2506:4: e= atom ( (mul= '*' | div= '/' ) e= atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr2325);
            e=atom();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					retval.p = (e!=null?e.p:null);
              					if ((e!=null?e.instance:null) != null) {
              						retval.instance = (e!=null?e.instance:null);
              					}
              					retval.index = (e!=null?e.index:0);
              					if ((e!=null?e.listAddress:null) != null) {
              						retval.listAddress = (e!=null?e.listAddress:null);
              					}
              					retval.primVariable = (e!=null?e.primVariable:null);
              				}
              			} else {
              				retval.p = (e!=null?e.p:null);
              				if ((e!=null?e.instance:null) != null) {
              					retval.instance = (e!=null?e.instance:null);
              				}
              				retval.index = (e!=null?e.index:0);
              				if ((e!=null?e.listAddress:null) != null) {
              					retval.listAddress = (e!=null?e.listAddress:null);
              				}
              				retval.primVariable = (e!=null?e.primVariable:null);
              			}
              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2531:5: ( (mul= '*' | div= '/' ) e= atom )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( ((LA58_0>=MULT && LA58_0<=DIV)) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2531:7: (mul= '*' | div= '/' ) e= atom
            	    {
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2531:7: (mul= '*' | div= '/' )
            	    int alt57=2;
            	    int LA57_0 = input.LA(1);

            	    if ( (LA57_0==MULT) ) {
            	        alt57=1;
            	    }
            	    else if ( (LA57_0==DIV) ) {
            	        alt57=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 57, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt57) {
            	        case 1 :
            	            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2531:8: mul= '*'
            	            {
            	            mul=(Token)match(input,MULT,FOLLOW_MULT_in_multExpr2336); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            mul_tree = (Object)adaptor.create(mul);
            	            adaptor.addChild(root_0, mul_tree);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2531:16: div= '/'
            	            {
            	            div=(Token)match(input,DIV,FOLLOW_DIV_in_multExpr2340); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            div_tree = (Object)adaptor.create(div);
            	            adaptor.addChild(root_0, div_tree);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_atom_in_multExpr2345);
            	    e=atom();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            	    if ( state.backtracking==0 ) {

            	      				if (!ifValueStack.empty()) {
            	      					if (ifValueStack.peek() == "1") {
            	      						if ((mul!=null?mul.getText():null) != null) {
            	      							doMultDivOp((e!=null?e.p:null), retval.p, (mul!=null?mul.getText():null));
            	      						} else {
            	      							doMultDivOp((e!=null?e.p:null), retval.p, (div!=null?div.getText():null));
            	      						}
            	      					}
            	      				} else {
            	      					if ((mul!=null?mul.getText():null) != null) {
            	      						doMultDivOp((e!=null?e.p:null), retval.p, (mul!=null?mul.getText():null));
            	      					} else {
            	      						doMultDivOp((e!=null?e.p:null), retval.p, (div!=null?div.getText():null));
            	      					}
            	      				}
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "multExpr"

    public static class atom_return extends ParserRuleReturnScope {
        public Primitive p = new Primitive();
        public String instance;
        public int index = -1;
        public String listAddress;
        public Primitive primVariable;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2551:1: atom returns [Primitive p = new Primitive(), String instance, int index = -1, String listAddress, Primitive primVariable] : ( exprP | (n= NUMBER | n= REAL ) | (t= TRUE | f= FALSE ) | LETTER | STRING | LETTER LEFTSBR (n= NUMBER | r= REAL ) RIGHTSBR | LEFTSBR list RIGHTSBR | '(' expr ')' );
    public final eugeneParser.atom_return atom() throws RecognitionException {
        eugeneParser.atom_return retval = new eugeneParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token n=null;
        Token t=null;
        Token f=null;
        Token r=null;
        Token LETTER233=null;
        Token STRING234=null;
        Token LETTER235=null;
        Token LEFTSBR236=null;
        Token RIGHTSBR237=null;
        Token LEFTSBR238=null;
        Token RIGHTSBR240=null;
        Token char_literal241=null;
        Token char_literal243=null;
        eugeneParser.exprP_return exprP232 = null;

        eugeneParser.list_return list239 = null;

        eugeneParser.expr_return expr242 = null;


        Object n_tree=null;
        Object t_tree=null;
        Object f_tree=null;
        Object r_tree=null;
        Object LETTER233_tree=null;
        Object STRING234_tree=null;
        Object LETTER235_tree=null;
        Object LEFTSBR236_tree=null;
        Object RIGHTSBR237_tree=null;
        Object LEFTSBR238_tree=null;
        Object RIGHTSBR240_tree=null;
        Object char_literal241_tree=null;
        Object char_literal243_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2552:2: ( exprP | (n= NUMBER | n= REAL ) | (t= TRUE | f= FALSE ) | LETTER | STRING | LETTER LEFTSBR (n= NUMBER | r= REAL ) RIGHTSBR | LEFTSBR list RIGHTSBR | '(' expr ')' )
            int alt62=8;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2552:4: exprP
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_exprP_in_atom2368);
                    exprP232=exprP();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exprP232.getTree());
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p = (exprP232!=null?exprP232.p:null);
                      					retval.instance = (exprP232!=null?exprP232.inst:null);
                      					retval.index = (exprP232!=null?exprP232.index:0);
                      					retval.p.index = retval.index;
                      					if ((exprP232!=null?exprP232.listAddress:null) != null) {
                      						retval.listAddress = (exprP232!=null?exprP232.listAddress:null);
                      					}
                      					retval.primVariable = (exprP232!=null?exprP232.primVariable:null);
                      				}
                      			} else {
                      				retval.p = (exprP232!=null?exprP232.p:null);
                      				retval.instance = (exprP232!=null?exprP232.inst:null);
                      				retval.index = (exprP232!=null?exprP232.index:0);
                      				retval.p.index = retval.index;
                      				if ((exprP232!=null?exprP232.listAddress:null) != null) {
                      					retval.listAddress = (exprP232!=null?exprP232.listAddress:null);
                      				}
                      				retval.primVariable = (exprP232!=null?exprP232.primVariable:null);
                      			}
                      		
                    }

                    }
                    break;
                case 2 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2576:4: (n= NUMBER | n= REAL )
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2576:4: (n= NUMBER | n= REAL )
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==NUMBER) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==REAL) ) {
                        alt59=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 59, 0, input);

                        throw nvae;
                    }
                    switch (alt59) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2576:5: n= NUMBER
                            {
                            n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_atom2380); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            n_tree = (Object)adaptor.create(n);
                            adaptor.addChild(root_0, n_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2576:16: n= REAL
                            {
                            n=(Token)match(input,REAL,FOLLOW_REAL_in_atom2386); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            n_tree = (Object)adaptor.create(n);
                            adaptor.addChild(root_0, n_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p.num = Double.parseDouble((n!=null?n.getText():null));
                      					retval.p.type = "num";
                      				}
                      			} else {
                      				retval.p.num = Double.parseDouble((n!=null?n.getText():null));
                      				retval.p.type = "num";
                      			}
                      		
                    }

                    }
                    break;
                case 3 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2588:4: (t= TRUE | f= FALSE )
                    {
                    root_0 = (Object)adaptor.nil();

                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2588:4: (t= TRUE | f= FALSE )
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==TRUE) ) {
                        alt60=1;
                    }
                    else if ( (LA60_0==FALSE) ) {
                        alt60=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 60, 0, input);

                        throw nvae;
                    }
                    switch (alt60) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2588:5: t= TRUE
                            {
                            t=(Token)match(input,TRUE,FOLLOW_TRUE_in_atom2399); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            t_tree = (Object)adaptor.create(t);
                            adaptor.addChild(root_0, t_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2588:14: f= FALSE
                            {
                            f=(Token)match(input,FALSE,FOLLOW_FALSE_in_atom2405); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            f_tree = (Object)adaptor.create(f);
                            adaptor.addChild(root_0, f_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p.type = "bool";
                      					if (t != null) {
                      						retval.p.bool = true;
                      					} else {
                      						retval.p.bool = false;
                      					}
                      				}
                      			} else {
                      				retval.p.type = "bool";
                      				if (t != null) {
                      					retval.p.bool = true;
                      				} else {
                      					retval.p.bool = false;
                      				}
                      			}
                      		
                    }

                    }
                    break;
                case 4 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2608:4: LETTER
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER233=(Token)match(input,LETTER,FOLLOW_LETTER_in_atom2415); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER233_tree = (Object)adaptor.create(LETTER233);
                    adaptor.addChild(root_0, LETTER233_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (primitiveDeclarations.containsKey((LETTER233!=null?LETTER233.getText():null))) {
                      						retval.p = copyPrimitive(primitiveDeclarations.get((LETTER233!=null?LETTER233.getText():null)));
                      						retval.primVariable = primitiveDeclarations.get((LETTER233!=null?LETTER233.getText():null));
                      					} else {
                      						printError((LETTER233!=null?LETTER233.getText():null) + " is not a valid primitive.");
                      					}
                      				}
                      			} else {
                      				if (primitiveDeclarations.containsKey((LETTER233!=null?LETTER233.getText():null))) {
                      					retval.p = copyPrimitive(primitiveDeclarations.get((LETTER233!=null?LETTER233.getText():null)));
                      					retval.primVariable = primitiveDeclarations.get((LETTER233!=null?LETTER233.getText():null));
                      				} else {
                      					printError((LETTER233!=null?LETTER233.getText():null) + " is not a valid primitive.");
                      				}
                      			}
                      		
                    }

                    }
                    break;
                case 5 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2628:4: STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    STRING234=(Token)match(input,STRING,FOLLOW_STRING_in_atom2424); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STRING234_tree = (Object)adaptor.create(STRING234);
                    adaptor.addChild(root_0, STRING234_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p.type = "txt";
                      					retval.p.txt = (STRING234!=null?STRING234.getText():null).substring(1, (STRING234!=null?STRING234.getText():null).length()-1);
                      				}
                      			} else {
                      				retval.p.type = "txt";
                      				retval.p.txt = (STRING234!=null?STRING234.getText():null).substring(1, (STRING234!=null?STRING234.getText():null).length()-1);
                      			}
                      		
                    }

                    }
                    break;
                case 6 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2640:4: LETTER LEFTSBR (n= NUMBER | r= REAL ) RIGHTSBR
                    {
                    root_0 = (Object)adaptor.nil();

                    LETTER235=(Token)match(input,LETTER,FOLLOW_LETTER_in_atom2433); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LETTER235_tree = (Object)adaptor.create(LETTER235);
                    adaptor.addChild(root_0, LETTER235_tree);
                    }
                    LEFTSBR236=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_atom2435); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR236_tree = (Object)adaptor.create(LEFTSBR236);
                    adaptor.addChild(root_0, LEFTSBR236_tree);
                    }
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2640:19: (n= NUMBER | r= REAL )
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==NUMBER) ) {
                        alt61=1;
                    }
                    else if ( (LA61_0==REAL) ) {
                        alt61=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 61, 0, input);

                        throw nvae;
                    }
                    switch (alt61) {
                        case 1 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2640:20: n= NUMBER
                            {
                            n=(Token)match(input,NUMBER,FOLLOW_NUMBER_in_atom2440); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            n_tree = (Object)adaptor.create(n);
                            adaptor.addChild(root_0, n_tree);
                            }

                            }
                            break;
                        case 2 :
                            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2640:29: r= REAL
                            {
                            r=(Token)match(input,REAL,FOLLOW_REAL_in_atom2444); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            r_tree = (Object)adaptor.create(r);
                            adaptor.addChild(root_0, r_tree);
                            }

                            }
                            break;

                    }

                    RIGHTSBR237=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_atom2447); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR237_tree = (Object)adaptor.create(RIGHTSBR237);
                    adaptor.addChild(root_0, RIGHTSBR237_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					if (r != null) {
                      						printError("Invalid index.");
                      					} else {
                      						if (primitiveDeclarations.containsKey((LETTER235!=null?LETTER235.getText():null))) {
                      							retval.listAddress = (LETTER235!=null?LETTER235.getText():null);
                      							Primitive prim = primitiveDeclarations.get((LETTER235!=null?LETTER235.getText():null));
                      							if (prim.type.equals("numList")) {
                      								int s = Integer.parseInt((n!=null?n.getText():null));
                      								if (s > prim.numList.size()) {
                      									printError("Index out of bounds, index: " + (n!=null?n.getText():null) + " and list size: " + s + ".");
                      								} else {
                      									retval.p.num = prim.numList.get(s);
                      									retval.p.type = "num";
                      									retval.index = s;
                      									retval.primVariable = prim;
                      								}
                      							} else if (prim.type.equals("txtList")) {
                      								int s = Integer.parseInt((n!=null?n.getText():null));
                      								if (s > prim.txtList.size()) {
                      									printError("Index out of bounds, index: " + (n!=null?n.getText():null) + " and list size: " + s + ".");
                      								} else {
                      									retval.p.txt = prim.txtList.get(s);
                      									retval.p.type = "txt";
                      									retval.index = s;
                      									retval.primVariable = prim;
                      								}
                      							}
                      						} else {
                      							printError("List " + (LETTER235!=null?LETTER235.getText():null) + " does not exist or is not a primitive list.");
                      						}
                      					}
                      				}
                      			} else {
                      				if (r != null) {
                      					printError("Invalid index.");
                      				} else {
                      					if (primitiveDeclarations.containsKey((LETTER235!=null?LETTER235.getText():null))) {
                      						retval.listAddress = (LETTER235!=null?LETTER235.getText():null);
                      						Primitive prim = primitiveDeclarations.get((LETTER235!=null?LETTER235.getText():null));
                      						if (prim.type.equals("numList")) {
                      							int s = Integer.parseInt((n!=null?n.getText():null));
                      							if (s > prim.numList.size()) {
                      								printError("Index out of bounds, index: " + (n!=null?n.getText():null) + " and list size: " + s + ".");
                      							} else {
                      								retval.p.num = prim.numList.get(s);
                      								retval.p.type = "num";
                      								retval.index = s;
                      								retval.primVariable = prim;
                      							}
                      						} else if (prim.type.equals("txtList")) {
                      							int s = Integer.parseInt((n!=null?n.getText():null));
                      							if (s > prim.txtList.size()) {
                      								printError("Index out of bounds, index: " + (n!=null?n.getText():null) + " and list size: " + s + ".");
                      							} else {
                      								retval.p.txt = prim.txtList.get(s);
                      								retval.p.type = "txt";
                      								retval.index = s;
                      								retval.primVariable = prim;
                      							}
                      						}
                      					} else {
                      						printError("List " + (LETTER235!=null?LETTER235.getText():null) + " does not exist or is not a primitive list.");
                      					}
                      				}
                      			} //end else
                      		
                    }

                    }
                    break;
                case 7 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2710:4: LEFTSBR list RIGHTSBR
                    {
                    root_0 = (Object)adaptor.nil();

                    LEFTSBR238=(Token)match(input,LEFTSBR,FOLLOW_LEFTSBR_in_atom2456); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSBR238_tree = (Object)adaptor.create(LEFTSBR238);
                    adaptor.addChild(root_0, LEFTSBR238_tree);
                    }
                    pushFollow(FOLLOW_list_in_atom2458);
                    list239=list();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list239.getTree());
                    RIGHTSBR240=(Token)match(input,RIGHTSBR,FOLLOW_RIGHTSBR_in_atom2460); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSBR240_tree = (Object)adaptor.create(RIGHTSBR240);
                    adaptor.addChild(root_0, RIGHTSBR240_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p = (list239!=null?list239.listPrim:null);
                      					retval.primVariable = (list239!=null?list239.listPrim:null);
                      				}
                      			} else {
                      				retval.p = (list239!=null?list239.listPrim:null);
                      				retval.primVariable = (list239!=null?list239.listPrim:null);
                      			}
                      		
                    }

                    }
                    break;
                case 8 :
                    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2722:4: '(' expr ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal241=(Token)match(input,LEFTP,FOLLOW_LEFTP_in_atom2469); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal241_tree = (Object)adaptor.create(char_literal241);
                    adaptor.addChild(root_0, char_literal241_tree);
                    }
                    pushFollow(FOLLOW_expr_in_atom2471);
                    expr242=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr242.getTree());
                    char_literal243=(Token)match(input,RIGHTP,FOLLOW_RIGHTP_in_atom2473); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal243_tree = (Object)adaptor.create(char_literal243);
                    adaptor.addChild(root_0, char_literal243_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			if (!ifValueStack.empty()) {
                      				if (ifValueStack.peek() == "1") {
                      					retval.p = (expr242!=null?expr242.p:null);
                      					retval.primVariable = (expr242!=null?expr242.primVariable:null);
                      				}
                      			} else {
                      				retval.p = (expr242!=null?expr242.p:null);
                      				retval.primVariable = (expr242!=null?expr242.primVariable:null);
                      			}
                      		
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    public static class list_return extends ParserRuleReturnScope {
        public Primitive listPrim;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "list"
    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2736:1: list returns [Primitive listPrim] : str1= expr ( COMMA str2= expr )* ;
    public final eugeneParser.list_return list() throws RecognitionException {
        eugeneParser.list_return retval = new eugeneParser.list_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COMMA244=null;
        eugeneParser.expr_return str1 = null;

        eugeneParser.expr_return str2 = null;


        Object COMMA244_tree=null;

        try {
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2737:2: (str1= expr ( COMMA str2= expr )* )
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2737:4: str1= expr ( COMMA str2= expr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expr_in_list2494);
            str1=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, str1.getTree());
            if ( state.backtracking==0 ) {

              			if (!ifValueStack.empty()) {
              				if (ifValueStack.peek() == "1") {
              					retval.listPrim = new Primitive();
              					retval.listPrim.type = typeList + "List";
              					addToListPrim((str1!=null?str1.p:null), retval.listPrim);
              				}
              			} else {
              				retval.listPrim = new Primitive();
              				retval.listPrim.type = typeList + "List";
              				addToListPrim((str1!=null?str1.p:null), retval.listPrim);
              			}
              		
            }
            // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2750:5: ( COMMA str2= expr )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==COMMA) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:2750:6: COMMA str2= expr
            	    {
            	    COMMA244=(Token)match(input,COMMA,FOLLOW_COMMA_in_list2501); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA244_tree = (Object)adaptor.create(COMMA244);
            	    adaptor.addChild(root_0, COMMA244_tree);
            	    }
            	    pushFollow(FOLLOW_expr_in_list2505);
            	    str2=expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, str2.getTree());
            	    if ( state.backtracking==0 ) {

            	      				if (!ifValueStack.empty()) {
            	      					if (ifValueStack.peek() == "1") {
            	      						addToListPrim((str2!=null?str2.p:null), retval.listPrim);
            	      					}
            	      				} else {
            	      					addToListPrim((str2!=null?str2.p:null), retval.listPrim);
            	      				}
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        	catch (RecognitionException RecogEx) {
        		int line = input.LT(-1).getLine();
        		throw new IllegalArgumentException("@Error Line " + RecogEx.line + ": " + "Syntax error.");
        	}
        finally {
        }
        return retval;
    }
    // $ANTLR end "list"

    // $ANTLR start synpred50_eugene
    public final void synpred50_eugene_fragment() throws RecognitionException {   
        eugeneParser.relationalExpression_return re1 = null;


        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:5: (re1= relationalExpression )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1685:5: re1= relationalExpression
        {
        pushFollow(FOLLOW_relationalExpression_in_synpred50_eugene1260);
        re1=relationalExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred50_eugene

    // $ANTLR start synpred54_eugene
    public final void synpred54_eugene_fragment() throws RecognitionException {   
        eugeneParser.expr_return e = null;

        eugeneParser.expr_return f = null;


        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:5: ( (e= expr | ) ( ( relationalOperators ) f= expr )* )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:5: (e= expr | ) ( ( relationalOperators ) f= expr )*
        {
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:5: (e= expr | )
        int alt67=2;
        int LA67_0 = input.LA(1);

        if ( (LA67_0==LEFTP||LA67_0==LEFTSBR||(LA67_0>=TRUE && LA67_0<=FALSE)||(LA67_0>=LETTER && LA67_0<=REAL)) ) {
            alt67=1;
        }
        else if ( (LA67_0==EOF||LA67_0==EQUALS||(LA67_0>=NEQUAL && LA67_0<=GEQUAL)) ) {
            alt67=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 67, 0, input);

            throw nvae;
        }
        switch (alt67) {
            case 1 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:6: e= expr
                {
                pushFollow(FOLLOW_expr_in_synpred54_eugene1303);
                e=expr();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1716:15: 
                {
                }
                break;

        }

        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:6: ( ( relationalOperators ) f= expr )*
        loop68:
        do {
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==EQUALS||(LA68_0>=NEQUAL && LA68_0<=GEQUAL)) ) {
                alt68=1;
            }


            switch (alt68) {
        	case 1 :
        	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:7: ( relationalOperators ) f= expr
        	    {
        	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:7: ( relationalOperators )
        	    // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1721:8: relationalOperators
        	    {
        	    pushFollow(FOLLOW_relationalOperators_in_synpred54_eugene1316);
        	    relationalOperators();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }

        	    pushFollow(FOLLOW_expr_in_synpred54_eugene1321);
        	    f=expr();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop68;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred54_eugene

    // $ANTLR start synpred56_eugene
    public final void synpred56_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1761:6: ( COMMA numdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1761:6: COMMA numdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred56_eugene1392); if (state.failed) return ;
        pushFollow(FOLLOW_numdecl_in_synpred56_eugene1394);
        numdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred56_eugene

    // $ANTLR start synpred58_eugene
    public final void synpred58_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1771:7: ( COMMA numdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1771:7: COMMA numdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred58_eugene1417); if (state.failed) return ;
        pushFollow(FOLLOW_numdecl_in_synpred58_eugene1419);
        numdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred58_eugene

    // $ANTLR start synpred59_eugene
    public final void synpred59_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1784:6: ( COMMA txtdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1784:6: COMMA txtdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred59_eugene1439); if (state.failed) return ;
        pushFollow(FOLLOW_txtdecl_in_synpred59_eugene1441);
        txtdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred59_eugene

    // $ANTLR start synpred61_eugene
    public final void synpred61_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1795:6: ( COMMA txtdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1795:6: COMMA txtdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred61_eugene1464); if (state.failed) return ;
        pushFollow(FOLLOW_txtdecl_in_synpred61_eugene1466);
        txtdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred61_eugene

    // $ANTLR start synpred62_eugene
    public final void synpred62_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1808:6: ( COMMA txtlistdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1808:6: COMMA txtlistdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred62_eugene1486); if (state.failed) return ;
        pushFollow(FOLLOW_txtlistdecl_in_synpred62_eugene1488);
        txtlistdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred62_eugene

    // $ANTLR start synpred64_eugene
    public final void synpred64_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1818:6: ( COMMA txtlistdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1818:6: COMMA txtlistdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred64_eugene1512); if (state.failed) return ;
        pushFollow(FOLLOW_txtlistdecl_in_synpred64_eugene1514);
        txtlistdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred64_eugene

    // $ANTLR start synpred65_eugene
    public final void synpred65_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1831:6: ( COMMA numlistdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1831:6: COMMA numlistdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred65_eugene1534); if (state.failed) return ;
        pushFollow(FOLLOW_numlistdecl_in_synpred65_eugene1536);
        numlistdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred65_eugene

    // $ANTLR start synpred67_eugene
    public final void synpred67_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1841:6: ( COMMA numlistdecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1841:6: COMMA numlistdecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred67_eugene1559); if (state.failed) return ;
        pushFollow(FOLLOW_numlistdecl_in_synpred67_eugene1561);
        numlistdecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred67_eugene

    // $ANTLR start synpred68_eugene
    public final void synpred68_eugene_fragment() throws RecognitionException {   
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1854:6: ( COMMA booldecl )
        // C:\\Documents and Settings\\Administrator\\Desktop\\eugene.g:1854:6: COMMA booldecl
        {
        match(input,COMMA,FOLLOW_COMMA_in_synpred68_eugene1581); if (state.failed) return ;
        pushFollow(FOLLOW_booldecl_in_synpred68_eugene1583);
        booldecl();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred68_eugene

    // Delegated rules

    public final boolean synpred65_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred65_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred68_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred68_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred64_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred64_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred67_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred67_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred61_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred61_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred59_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred59_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred50_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred50_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred54_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred54_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred62_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred62_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred56_eugene() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred56_eugene_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA19 dfa19 = new DFA19(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA62 dfa62 = new DFA62(this);
    static final String DFA2_eotS =
        "\20\uffff";
    static final String DFA2_eofS =
        "\20\uffff";
    static final String DFA2_minS =
        "\1\10\1\uffff\3\12\6\uffff\1\34\4\uffff";
    static final String DFA2_maxS =
        "\1\73\1\uffff\3\70\6\uffff\1\70\4\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\3\uffff\1\7\1\10\1\11\1\12\1\13\1\2\1\uffff\1\6\1"+
        "\3\1\5\1\4";
    static final String DFA2_specialS =
        "\20\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\10\1\uffff\1\10\10\uffff\1\3\1\uffff\1\5\5\1\1\4\1\uffff"+
            "\1\6\20\uffff\2\1\1\11\2\uffff\2\10\1\7\2\uffff\1\2\3\10",
            "",
            "\1\10\3\uffff\1\10\3\uffff\1\13\45\uffff\1\12",
            "\1\14\55\uffff\1\15",
            "\1\16\55\uffff\1\17",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\1\1\6\32\uffff\1\10",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "1342:1: statement : ( decl | declObjects | NUM numdecl SEMIC | TXT txtdecl SEMIC | TXT LEFTSBR RIGHTSBR txtlistdecl SEMIC | NUM LEFTSBR RIGHTSBR numlistdecl SEMIC | BOOLEAN booldecl SEMIC | print | permute | leftval= atom EQUALS rightval= expr SEMIC | ifStatement );";
        }
    }
    static final String DFA5_eotS =
        "\22\uffff";
    static final String DFA5_eofS =
        "\22\uffff";
    static final String DFA5_minS =
        "\1\26\1\70\7\uffff\1\10\1\23\2\11\5\uffff";
    static final String DFA5_maxS =
        "\2\70\7\uffff\1\10\1\33\2\12\5\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\6\1\7\1\10\1\11\1\12\1\13\1\14\4\uffff\1\5\1\1\1\2\1"+
        "\3\1\4";
    static final String DFA5_specialS =
        "\22\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\4\1\1\1\2\1\3\1\5\23\uffff\1\7\1\6\10\uffff\1\10",
            "\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\12",
            "\1\14\1\uffff\1\15\5\uffff\1\13",
            "\1\16\1\17",
            "\1\20\1\21",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "1375:1: decl : ( PROPERTY LETTER LEFTP TXT RIGHTP SEMIC | PROPERTY LETTER LEFTP TXT LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY tn= LETTER LEFTP NUM RIGHTP SEMIC | PROPERTY LETTER LEFTP NUM LEFTSBR RIGHTSBR RIGHTP SEMIC | PROPERTY LETTER LEFTP BOOLEAN RIGHTP SEMIC | PART LETTER LEFTP propertyList RIGHTP SEMIC | DEVICE LETTER LEFTP componentList RIGHTP SEMIC | IMAGE LEFTP name= LETTER COMMA path= STRING RIGHTP SEMIC | RULE name= LETTER LEFTP (leftOp= LETTER | leftOpP= exprP | ) operator (rightOp= LETTER | rightOpP= exprP ) RIGHTP SEMIC | NOTE LEFTP statementsOfAssertion RIGHTP SEMIC | ASSERT LEFTP statementsOfAssertion RIGHTP SEMIC | LETTER pfunctions );";
        }
    }
    static final String DFA19_eotS =
        "\23\uffff";
    static final String DFA19_eofS =
        "\1\1\22\uffff";
    static final String DFA19_minS =
        "\1\10\7\uffff\1\0\12\uffff";
    static final String DFA19_maxS =
        "\1\73\7\uffff\1\0\12\uffff";
    static final String DFA19_acceptS =
        "\1\uffff\1\1\20\uffff\1\2";
    static final String DFA19_specialS =
        "\10\uffff\1\0\12\uffff}>";
    static final String[] DFA19_transitionS = {
            "\1\10\2\1\3\uffff\1\1\27\uffff\10\1\5\uffff\2\1\3\uffff\4\1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1715:1: relationalExpression returns [ArrayList<Primitive> list = new ArrayList<Primitive>()] : ( (e= expr | ) ( ( relationalOperators ) f= expr )* | LEFTP expression RIGHTP );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_8 = input.LA(1);

                         
                        int index19_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred54_eugene()) ) {s = 1;}

                        else if ( (true) ) {s = 18;}

                         
                        input.seek(index19_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA50_eotS =
        "\13\uffff";
    static final String DFA50_eofS =
        "\13\uffff";
    static final String DFA50_minS =
        "\1\65\1\10\1\70\1\11\1\uffff\1\66\1\uffff\2\11\2\uffff";
    static final String DFA50_maxS =
        "\1\65\1\10\1\70\1\21\1\uffff\1\72\1\uffff\2\21\2\uffff";
    static final String DFA50_acceptS =
        "\4\uffff\1\1\1\uffff\1\4\2\uffff\1\3\1\2";
    static final String DFA50_specialS =
        "\13\uffff}>";
    static final String[] DFA50_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3",
            "\1\4\7\uffff\1\5",
            "",
            "\2\6\1\10\1\uffff\1\7",
            "",
            "\1\12\7\uffff\1\11",
            "\1\12\7\uffff\1\11",
            "",
            ""
    };

    static final short[] DFA50_eot = DFA.unpackEncodedString(DFA50_eotS);
    static final short[] DFA50_eof = DFA.unpackEncodedString(DFA50_eofS);
    static final char[] DFA50_min = DFA.unpackEncodedStringToUnsignedChars(DFA50_minS);
    static final char[] DFA50_max = DFA.unpackEncodedStringToUnsignedChars(DFA50_maxS);
    static final short[] DFA50_accept = DFA.unpackEncodedString(DFA50_acceptS);
    static final short[] DFA50_special = DFA.unpackEncodedString(DFA50_specialS);
    static final short[][] DFA50_transition;

    static {
        int numStates = DFA50_transitionS.length;
        DFA50_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA50_transition[i] = DFA.unpackEncodedString(DFA50_transitionS[i]);
        }
    }

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = DFA50_eot;
            this.eof = DFA50_eof;
            this.min = DFA50_min;
            this.max = DFA50_max;
            this.accept = DFA50_accept;
            this.special = DFA50_special;
            this.transition = DFA50_transition;
        }
        public String getDescription() {
            return "2209:1: permute : ( PERMUTE LEFTP device= LETTER RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA (num= NUMBER | numVar= LETTER ) COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC | PERMUTE LEFTP device= LETTER COMMA degree= ( STRICT | FLEXIBLE ) RIGHTP SEMIC );";
        }
    }
    static final String DFA62_eotS =
        "\16\uffff";
    static final String DFA62_eofS =
        "\1\uffff\1\11\12\uffff\1\15\1\uffff";
    static final String DFA62_minS =
        "\1\10\1\4\5\uffff\1\72\2\uffff\2\13\1\4\1\uffff";
    static final String DFA62_maxS =
        "\1\73\1\55\5\uffff\1\73\2\uffff\2\13\1\55\1\uffff";
    static final String DFA62_acceptS =
        "\2\uffff\1\2\1\3\1\5\1\7\1\10\1\uffff\1\1\1\4\3\uffff\1\6";
    static final String DFA62_specialS =
        "\16\uffff}>";
    static final String[] DFA62_transitionS = {
            "\1\6\1\uffff\1\5\50\uffff\2\3\3\uffff\1\1\1\4\2\2",
            "\4\11\1\uffff\1\11\1\7\1\11\2\uffff\1\11\1\uffff\2\11\1\10"+
            "\23\uffff\10\11",
            "",
            "",
            "",
            "",
            "",
            "\1\12\1\13",
            "",
            "",
            "\1\14",
            "\1\14",
            "\4\15\1\uffff\1\15\1\10\1\15\2\uffff\1\15\1\uffff\2\15\1\10"+
            "\23\uffff\10\15",
            ""
    };

    static final short[] DFA62_eot = DFA.unpackEncodedString(DFA62_eotS);
    static final short[] DFA62_eof = DFA.unpackEncodedString(DFA62_eofS);
    static final char[] DFA62_min = DFA.unpackEncodedStringToUnsignedChars(DFA62_minS);
    static final char[] DFA62_max = DFA.unpackEncodedStringToUnsignedChars(DFA62_maxS);
    static final short[] DFA62_accept = DFA.unpackEncodedString(DFA62_acceptS);
    static final short[] DFA62_special = DFA.unpackEncodedString(DFA62_specialS);
    static final short[][] DFA62_transition;

    static {
        int numStates = DFA62_transitionS.length;
        DFA62_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA62_transition[i] = DFA.unpackEncodedString(DFA62_transitionS[i]);
        }
    }

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = DFA62_eot;
            this.eof = DFA62_eof;
            this.min = DFA62_min;
            this.max = DFA62_max;
            this.accept = DFA62_accept;
            this.special = DFA62_special;
            this.transition = DFA62_transition;
        }
        public String getDescription() {
            return "2551:1: atom returns [Primitive p = new Primitive(), String instance, int index = -1, String listAddress, Primitive primVariable] : ( exprP | (n= NUMBER | n= REAL ) | (t= TRUE | f= FALSE ) | LETTER | STRING | LETTER LEFTSBR (n= NUMBER | r= REAL ) RIGHTSBR | LEFTSBR list RIGHTSBR | '(' expr ')' );";
        }
    }
 

    public static final BitSet FOLLOW_statement_in_prog507 = new BitSet(new long[]{0x0F39C0002FE80500L});
    public static final BitSet FOLLOW_EOF_in_prog511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decl_in_statement525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declObjects_in_statement532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_statement539 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numdecl_in_statement541 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TXT_in_statement550 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtdecl_in_statement552 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TXT_in_statement561 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_statement563 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_statement565 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtlistdecl_in_statement567 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_in_statement576 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_statement578 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_statement580 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numlistdecl_in_statement582 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOLEAN_in_statement591 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_booldecl_in_statement593 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_print_in_statement602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_permute_in_statement613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_statement620 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_statement622 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_statement626 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_statement628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_statement639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_decl654 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl656 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl658 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_TXT_in_decl660 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl662 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_decl673 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl675 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl677 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_TXT_in_decl679 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_decl681 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_decl683 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl685 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_decl696 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl700 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl702 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NUM_in_decl704 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl706 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_decl717 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl719 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl721 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_NUM_in_decl723 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_decl725 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_decl727 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl729 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_in_decl740 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl742 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl744 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_BOOLEAN_in_decl746 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl748 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PART_in_decl759 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl761 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl767 = new BitSet(new long[]{0x0100000000000200L});
    public static final BitSet FOLLOW_propertyList_in_decl769 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl771 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEVICE_in_decl783 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl785 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl787 = new BitSet(new long[]{0x0100000002000200L});
    public static final BitSet FOLLOW_componentList_in_decl789 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl791 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMAGE_in_decl802 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl804 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl808 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_decl810 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_STRING_in_decl814 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl816 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_in_decl827 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl831 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl833 = new BitSet(new long[]{0x01003FFFC0004000L});
    public static final BitSet FOLLOW_LETTER_in_decl838 = new BitSet(new long[]{0x01003FFFC0004000L});
    public static final BitSet FOLLOW_exprP_in_decl844 = new BitSet(new long[]{0x01003FFFC0004000L});
    public static final BitSet FOLLOW_operator_in_decl849 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_decl854 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_exprP_in_decl860 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl863 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOTE_in_decl874 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl876 = new BitSet(new long[]{0x01003FFFC0004300L});
    public static final BitSet FOLLOW_statementsOfAssertion_in_decl882 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl884 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSERT_in_decl896 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_decl898 = new BitSet(new long[]{0x01003FFFC0004300L});
    public static final BitSet FOLLOW_statementsOfAssertion_in_decl904 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_decl906 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_decl908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_decl918 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_pfunctions_in_decl924 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOperators_in_operator940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalOperators_in_operator945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalOperators_in_operator950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_ruleOperators0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUALS_in_relationalOperators1007 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_relationalOperators1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEQUAL_in_relationalOperators1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LTHAN_in_relationalOperators1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GTHAN_in_relationalOperators1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEQUAL_in_relationalOperators1029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GEQUAL_in_relationalOperators1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_logicalOperators0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statmnts_in_statementsOfAssertion1069 = new BitSet(new long[]{0x01003FFFC0004002L});
    public static final BitSet FOLLOW_operator_in_statementsOfAssertion1080 = new BitSet(new long[]{0x0100000000000100L});
    public static final BitSet FOLLOW_statmnts_in_statementsOfAssertion1089 = new BitSet(new long[]{0x01003FFFC0004002L});
    public static final BitSet FOLLOW_LEFTP_in_statmnts1113 = new BitSet(new long[]{0x01003FFFC0004300L});
    public static final BitSet FOLLOW_statementsOfAssertion_in_statmnts1117 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_statmnts1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_statmnts1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_ifStatement1143 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_ifStatement1145 = new BitSet(new long[]{0x0F1C3FFFC0004500L});
    public static final BitSet FOLLOW_statementsOfAssertionExpr_in_ifStatement1150 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_expression_in_ifStatement1156 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_ifStatement1159 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFTCUR_in_ifStatement1161 = new BitSet(new long[]{0x0F39C0002FE82500L});
    public static final BitSet FOLLOW_statement_in_ifStatement1168 = new BitSet(new long[]{0x0F39C0002FE82500L});
    public static final BitSet FOLLOW_RIGHTCUR_in_ifStatement1173 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_ELSE_in_ifStatement1188 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LEFTCUR_in_ifStatement1190 = new BitSet(new long[]{0x0F39C0002FE82500L});
    public static final BitSet FOLLOW_statement_in_ifStatement1199 = new BitSet(new long[]{0x0F39C0002FE82500L});
    public static final BitSet FOLLOW_RIGHTCUR_in_ifStatement1211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_statementsOfAssertionExpr1233 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_statementsOfAssertionExpr1235 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_deviceList_in_statementsOfAssertionExpr1237 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_statementsOfAssertionExpr1239 = new BitSet(new long[]{0x01003FFFC0004100L});
    public static final BitSet FOLLOW_statementsOfAssertion_in_statementsOfAssertionExpr1243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_expression1260 = new BitSet(new long[]{0x01003FFFC0004002L});
    public static final BitSet FOLLOW_logicalOperators_in_expression1272 = new BitSet(new long[]{0x0F183FFFC0004500L});
    public static final BitSet FOLLOW_relationalExpression_in_expression1277 = new BitSet(new long[]{0x01003FFFC0004002L});
    public static final BitSet FOLLOW_expr_in_relationalExpression1303 = new BitSet(new long[]{0x000007C000004002L});
    public static final BitSet FOLLOW_relationalOperators_in_relationalExpression1316 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_relationalExpression1321 = new BitSet(new long[]{0x000007C000004002L});
    public static final BitSet FOLLOW_LEFTP_in_relationalExpression1334 = new BitSet(new long[]{0x0F1C3FFFC0004500L});
    public static final BitSet FOLLOW_expression_in_relationalExpression1336 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_relationalExpression1338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_deviceList1355 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_deviceList1362 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_deviceList1366 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_numdecl1385 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_numdecl1392 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numdecl_in_numdecl1394 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_numdecl1401 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_numdecl1403 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_numdecl1408 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_numdecl1417 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numdecl_in_numdecl1419 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_txtdecl1432 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_txtdecl1439 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtdecl_in_txtdecl1441 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_txtdecl1451 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_txtdecl1453 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_txtdecl1457 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_txtdecl1464 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtdecl_in_txtdecl1466 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_txtlistdecl1479 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_txtlistdecl1486 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtlistdecl_in_txtlistdecl1488 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_txtlistdecl1497 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_txtlistdecl1499 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_txtlistdecl1505 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_txtlistdecl1512 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtlistdecl_in_txtlistdecl1514 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_numlistdecl1527 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_numlistdecl1534 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numlistdecl_in_numlistdecl1536 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_numlistdecl1545 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_numlistdecl1547 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_numlistdecl1552 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_numlistdecl1559 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numlistdecl_in_numlistdecl1561 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_booldecl1574 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_booldecl1581 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_booldecl_in_booldecl1583 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_LETTER_in_booldecl1592 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_EQUALS_in_booldecl1594 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_booldecl1598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_propertyList1613 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_propertyList1619 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_propertyList_in_propertyList1621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_propertyList1626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_componentList1647 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_LETTER_in_componentList1653 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_componentList1657 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_DEVICE_in_componentList1663 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_componentList1667 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_componentList1674 = new BitSet(new long[]{0x0100000002000000L});
    public static final BitSet FOLLOW_componentList_in_componentList1676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_componentList1684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_componentList1690 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_componentList1694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DEVICE_in_componentList1700 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_componentList1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declObjects_in_componentList1719 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_DOT_in_pfunctions1732 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_ADDPROPS_in_pfunctions1734 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_pfunctions1736 = new BitSet(new long[]{0x0100000000000200L});
    public static final BitSet FOLLOW_propertyList_in_pfunctions1738 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_pfunctions1740 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_pfunctions1742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_declObjects1755 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_declObjects1759 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_declObjects1761 = new BitSet(new long[]{0x0F18000000000700L});
    public static final BitSet FOLLOW_propertyListInst_in_declObjects1767 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_declObjects1774 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_declObjects1776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_declObjects1783 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_declObjects1787 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_declObjects1789 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_propertyListInstDot_in_declObjects1795 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_declObjects1802 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_declObjects1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_propertyListInstDot1815 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_propertyListInstDot1819 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_propertyListInstDot1825 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_propertyListInstDot1829 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_propertyListInstDot1836 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_propertyListInstDot1839 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_propertyListInstDot1841 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_propertyListInstDot1845 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_propertyListInstDot1853 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_propertyListInstDot1857 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_propertyListInstDot1866 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_expr_in_propertyListInst1885 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_propertyListInst1893 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_propertyListInst1903 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_PRINT_in_print1931 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_print1933 = new BitSet(new long[]{0x0F18000000020700L});
    public static final BitSet FOLLOW_expr_in_print1938 = new BitSet(new long[]{0x0000000000020200L});
    public static final BitSet FOLLOW_COMMA_in_print1949 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_print1953 = new BitSet(new long[]{0x0000000000020200L});
    public static final BitSet FOLLOW_RIGHTP_in_print1963 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_print1965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_print1974 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_print1976 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_PRINT_in_print1978 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_print1980 = new BitSet(new long[]{0x0500000000000200L});
    public static final BitSet FOLLOW_LETTER_in_print1985 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NUMBER_in_print1991 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_print1997 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_print1999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERMUTE_in_permute2015 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_permute2017 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_permute2021 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_permute2023 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_permute2025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERMUTE_in_permute2035 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_permute2037 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_permute2041 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_permute2043 = new BitSet(new long[]{0x0500000000000000L});
    public static final BitSet FOLLOW_NUMBER_in_permute2048 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_LETTER_in_permute2054 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_permute2058 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_permute2060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERMUTE_in_permute2070 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_permute2072 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_permute2076 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_permute2078 = new BitSet(new long[]{0x0500000000000000L});
    public static final BitSet FOLLOW_NUMBER_in_permute2083 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_LETTER_in_permute2089 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_permute2092 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_set_in_permute2096 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_permute2104 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_permute2106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERMUTE_in_permute2116 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LEFTP_in_permute2118 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_LETTER_in_permute2122 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_permute2124 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_set_in_permute2128 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_permute2136 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_SEMIC_in_permute2138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_exprP2160 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_exprD_in_exprP2164 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DOT_in_exprP2167 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_property_in_exprP2169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_property2188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_property2197 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_property2199 = new BitSet(new long[]{0x0C00000000000000L});
    public static final BitSet FOLLOW_NUMBER_in_property2204 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_REAL_in_property2208 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_property2211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_exprD2233 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_exprD2239 = new BitSet(new long[]{0x0C00000000000000L});
    public static final BitSet FOLLOW_NUMBER_in_exprD2244 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_REAL_in_exprD2248 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_exprD2251 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_multExpr_in_expr2275 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_PLUS_in_expr2282 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_multExpr_in_expr2286 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_MINUS_in_expr2295 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_multExpr_in_expr2299 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_atom_in_multExpr2325 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_MULT_in_multExpr2336 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_DIV_in_multExpr2340 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_atom_in_multExpr2345 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_exprP_in_atom2368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMBER_in_atom2380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_atom2386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_atom2399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_atom2405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_atom2415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atom2424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LETTER_in_atom2433 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_LEFTSBR_in_atom2435 = new BitSet(new long[]{0x0C00000000000000L});
    public static final BitSet FOLLOW_NUMBER_in_atom2440 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_REAL_in_atom2444 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_atom2447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTSBR_in_atom2456 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_list_in_atom2458 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHTSBR_in_atom2460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTP_in_atom2469 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_atom2471 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHTP_in_atom2473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_list2494 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_list2501 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_list2505 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_relationalExpression_in_synpred50_eugene1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_synpred54_eugene1303 = new BitSet(new long[]{0x000007C000004002L});
    public static final BitSet FOLLOW_relationalOperators_in_synpred54_eugene1316 = new BitSet(new long[]{0x0F18000000000500L});
    public static final BitSet FOLLOW_expr_in_synpred54_eugene1321 = new BitSet(new long[]{0x000007C000004002L});
    public static final BitSet FOLLOW_COMMA_in_synpred56_eugene1392 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numdecl_in_synpred56_eugene1394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred58_eugene1417 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numdecl_in_synpred58_eugene1419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred59_eugene1439 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtdecl_in_synpred59_eugene1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred61_eugene1464 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtdecl_in_synpred61_eugene1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred62_eugene1486 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtlistdecl_in_synpred62_eugene1488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred64_eugene1512 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_txtlistdecl_in_synpred64_eugene1514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred65_eugene1534 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numlistdecl_in_synpred65_eugene1536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred67_eugene1559 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_numlistdecl_in_synpred67_eugene1561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMMA_in_synpred68_eugene1581 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_booldecl_in_synpred68_eugene1583 = new BitSet(new long[]{0x0000000000000002L});

}