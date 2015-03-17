import java.io.File;

import parser.XMLParser;


public class Main {

	public static void main(String[] args) 
	{
		PromptManager pm = new PromptManager();
		
		String dtdpath = pm.requestString("Enter Path to DTD File: ");
		String xmlpath = pm.requestString("Enter Path to XML File: ");
		
		File dtd = new File(dtdpath);
		File xml = new File(xmlpath);
		
		if (!(dtd.isFile() && xml.isFile())) 
		{
			System.out.println("One or both paths are not files.");
		}
		
		XMLParser parser = new XMLParser(dtdpath,xmlpath);
		parser.parse();
		
		System.out.println("Finished");
	}

}
