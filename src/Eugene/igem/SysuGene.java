package igem;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import antlr.TokenStreamSelector;
import eugene.Eugene;
import eugene.dom.Variable;
import eugene.parser.EugeneLexer;
import eugene.parser.EugeneParser;
import eugene.tools.XMLToEugeneHeader;

public class SysuGene
{
	public static TokenStreamSelector selector = new TokenStreamSelector();
	public static EugeneParser parser;
	public static EugeneLexer lexer;

	public SysuGene(String sysuGeneFile)
	{
		try
		{			
			File eugFile = new File(sysuGeneFile);
//			File tempFile=
			ANTLRFileStream inputsteam=new ANTLRFileStream(eugFile.getAbsolutePath());
//			inputsteam.
			lexer = new EugeneLexer();
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new EugeneParser(tokens);
			parser.initSymbolTables();
			parser.prog();
//			XMLToEugeneHeader xmlToHeader = new XMLToEugeneHeader();
//			xmlToHeader.run();
		//	parser.cleanUpNoExit();
		}
		catch (IOException ioe) {
			Logger.getLogger(Eugene.class.getName()).log(Level.SEVERE, null, ioe);
		} catch (RecognitionException ex) {
			Logger.getLogger(Eugene.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

//	public static void main(String[] args)
//	{
//		/*
//		if (args.length != 1) {
//			System.err.println("Usage: java -jar eugene.jar <eugene-file>");
//			System.exit(1);
//		}
//		*/
//		new SysuGene("Demo.eug");//args[0]);
//	}
}