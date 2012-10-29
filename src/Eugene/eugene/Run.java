package eugene;

import java.util.*;
import java.io.*;
import org.antlr.runtime.*;

public class Run {

    public static String tmpPath = System.getProperty("java.io.tmpdir");
    public static String includePath = "include/";
    public static int lineOffSet;
    private static boolean _option_XML_Default = false;
    private static boolean _option_XML_SBDTP = false;
    private static boolean _option_Print_Devices = false;

    public static void main(String args[]) throws Exception {
        File eugFile = null;
        if (args.length != 0) {
            // get file and options from command line argument
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-xml-default")) {
                    _option_XML_Default = true;
                } else if (args[i].equals("-xml-sbdtp")) {
                    _option_XML_SBDTP = true;
                } else if (args[i].equals("-print-devices")) {
                    _option_Print_Devices = true;
                } else {
                    eugFile = new File(args[i]);
                    break;
                }
            }
        } else {
            //get specific include folder
            fileDialogFrame fdFolder = new fileDialogFrame();
            fdFolder.displayInclude();
            //if specific include folder is choosen, otherwise default include is
            //used
            if(fdFolder.getFolder() != null)
                includePath = fdFolder.getFolder().getAbsolutePath();
            
            // get file from file chooser
            fileDialogFrame fdFrame = new fileDialogFrame();
            fdFrame.display();
            eugFile = fdFrame.getFile();
        }
        if (eugFile != null) {
            String eugFileName = eugFile.getAbsolutePath();
            String outputFile = eugFileName + ".out";
            LogFile.start(outputFile);
            File newFile = includeFiles(eugFile);
            double timeStart = System.currentTimeMillis(), timeEnd;
            eugeneLexer lex = new eugeneLexer(new ANTLRFileStream(newFile.getAbsolutePath()));
            CommonTokenStream tokens = new CommonTokenStream(lex);
            eugeneParser parser = new eugeneParser(tokens);
            try {
                parser.prog();
            } catch (IllegalArgumentException e) {
                System.err.println(parseErrorMessage(e.getMessage()));
                //System.err.println(e.getMessage());
            }
            timeEnd = System.currentTimeMillis();
            double total = timeEnd - timeStart;
            System.out.println("\nCompile time: " + total + " ms");

            HashMap<String, String> propertyDefs = parser.propertyDefinitions;
            HashMap<String, ArrayList<ArrayList<String>>> partDefs = parser.partDefinitions;
            HashMap<String, Part> partDecs = parser.partDeclarations;
            HashMap<String, Device> deviceDecs = parser.deviceDeclarations;
            HashMap<String, Primitive> primitiveDecs = parser.primitiveDeclarations;
            HashMap<String, Rule> ruleDecs = parser.ruleDeclarations;
            HashMap<String, ArrayList<Primitive>> ruleAsserts = parser.ruleAssertions;
            HashMap<String, ArrayList<Primitive>> ruleNotes = parser.ruleNotes;

            // process options
            if (_option_XML_Default) {
                System.out.println("Generating default XML...");
                String xmlName = eugFileName.substring(0, eugFileName.length()-4) + "_default.xml";
                eugene.tools.EugeneToXML eugToXML = new eugene.tools.EugeneToXML(propertyDefs, partDefs, partDecs, deviceDecs, primitiveDecs, ruleDecs, ruleAsserts, ruleNotes);
                eugToXML.exportXML(xmlName, true);
            }
            if (_option_XML_SBDTP) {
                System.out.println("Generating SBDTP XML...");
                String xmlName = eugFileName.substring(0, eugFileName.length()-4) + "_sbdtp.xml";
                eugene.tools.EugeneToXML eugToXML = new eugene.tools.EugeneToXML(propertyDefs, partDefs, partDecs, deviceDecs, primitiveDecs, ruleDecs, ruleAsserts, ruleNotes);
                eugToXML.exportXML(xmlName, false);
            }
            if (_option_Print_Devices) {
                System.out.println("Printing devices...");
                ArrayList<String> keys = new ArrayList(deviceDecs.keySet());
                Collections.sort(keys);
                int keysLen = keys.size();
                String deviceName;
                for (int i = 0; i < keysLen; i++) {
                    deviceName = keys.get(i);
                    System.out.println(deviceName + ": "+ deviceDecs.get(deviceName).components);
                }
            }

            newFile.delete();
            LogFile.stop();
        }
        System.exit(0);
    }

    public static File includeFiles(File eugfile) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(eugfile.getAbsolutePath()));
				String line, newFileName = tmpPath + "tmp.eug";
                FileWriter fstream = new FileWriter(newFileName);
				BufferedWriter out = new BufferedWriter(fstream);
				String[] lineArgs;
				boolean p = true, c = true;
				lineOffSet = 0;
				while ((line = in.readLine()) != null) {
					if(line.startsWith("include")) {
						line = line.replace("include", "");
                        line = line.replace(";", "");
						lineArgs = line.split(",");
						for(int i=0; i < lineArgs.length; i++) {
							lineArgs[i] = lineArgs[i].trim();
                            if(lineArgs[i].contains("PropertyDefinition.h") && p) {
								appendFile("PropertyDefinition.h", out, true);
								p = false;
							} else if(lineArgs[i].contains("PartDefinition.h") && c) {
								appendFile("PartDefinition.h", out, true);
								c = false;
							} else if(!c && !p) {
								appendFile(lineArgs[i], out, true);
							}
						}
					} else {
						break;
					}
				}
				appendFile(eugfile.getAbsolutePath(), out, false);
                out.close();
				return (new File(newFileName));
				
			} catch (IOException e) {
                System.err.println("Cannot write to file 1.");
			}
            return null;
	}

	private static void appendFile(String fileName, BufferedWriter out, boolean increment) {
        try {
            if(increment)
                fileName = includePath + "\\" + fileName;
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = in.readLine()) != null) {
                if(increment) {
                    lineOffSet++;
                }
                if(!line.startsWith("include")) {
                    out.write(line + System.getProperty("line.separator"));
                }
			}
			in.close();
		} catch (IOException e) {
            System.err.println("Cannot write to file 2. " + fileName + " inc " + includePath);
		}
	}

    private static String parseErrorMessage(String msg) {
        try {
            int line = Integer.parseInt(msg.substring(12, msg.indexOf(":")));
            return "@Error Line " + (line - lineOffSet) + ": " + msg.substring(msg.indexOf(":") + 2);
        } catch (NumberFormatException nfe) {
            return msg;
        }
    }

}