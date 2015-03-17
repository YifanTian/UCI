package parser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import titlecontents.TitleContents;
import connection.QueryExecuter;
import elements.Article;
import elements.Book;
import elements.Editor;
import elements.InCollection;
import elements.InProceedings;
import elements.MastersThesis;
import elements.PhdThesis;
import elements.Proceedings;
import elements.WWW;

public class XMLParser extends DefaultHandler {
	
	// resources
	private String dtdFile;
	private String xmlFile;
	
	// elements
	private Book book;
	private InProceedings inproceedings;
	private Proceedings proceedings;
	private InCollection incollection;
	private PhdThesis phdthesis;
	private MastersThesis mastersthesis;
	private WWW www;
	private Article article;
	private Editor editor;
	
	// Attributes
	private TitleContents tc;
	
	// query executor
	private QueryExecuter qe;
	
	// ancillary variables
	private String currentElement = "none";
	private boolean run = true;
	private String tag = null;
	private int count = 0;
	private String result = "";
	
	public XMLParser (String dtdFile, String xmlFile) {
		// set our input files
		this.dtdFile = dtdFile;
		this.xmlFile = xmlFile;
		// setup our query executer
		qe = new QueryExecuter();
	}
	
	public String parse () {
		try 
		{
			// create a new parser
			SAXParser xmlparser = SAXParserFactory.newInstance().newSAXParser();
			// define the input source to be our file
			InputSource input = new InputSource(xmlFile);
			// set the appropriate encoding to process the data correctly
			input.setEncoding("ISO-8859-1");
			// pass in the input source and the default handler object to parse the document
			xmlparser.parse(input, this);
			result = qe.commitTransaction();
			count = qe.getCount();
			return result;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public int getCount () {
		return count;
	}
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if (qName.equals("article") && currentElement.equals("none"))
		{
			 article = new Article(attributes.getValue("mdate"),attributes.getValue("key"),attributes.getValue("reviewingid"),attributes.getValue("rating"));
			 currentElement = "article";
			 tc = new TitleContents();
			 article.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("inproceedings") && currentElement.equals("none")) 
		{
			 inproceedings = new InProceedings(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "inproceedings";
			 tc = new TitleContents();
			 inproceedings.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("proceedings") && currentElement.equals("none"))
		{
			 proceedings = new Proceedings(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "proceedings";
			 tc = new TitleContents();
			 proceedings.setTc(tc);
			 run = true;
		 } 
		else if(qName.equals("book") && currentElement.equals("none")) 
		{
			 book = new Book(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "book";
			 tc = new TitleContents();
			 book.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("incollection") && currentElement.equals("none")) 
		{
			 incollection = new InCollection(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "incollection";
			 tc = new TitleContents();
			 incollection.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("phdthesis") && currentElement.equals("none")) 
		{
			 phdthesis = new PhdThesis(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "phdthesis";
			 tc = new TitleContents();
			 phdthesis.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("mastersthesis") && currentElement.equals("none")) 
		{
			 mastersthesis = new MastersThesis(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "mastersthesis";
			 tc = new TitleContents();
			 mastersthesis.setTc(tc);
			 run = true;
		 } 
		else if (qName.equals("www") && currentElement.equals("none"))
		{
			 www = new WWW(attributes.getValue("mdate"),attributes.getValue("key"));
			 currentElement = "www";
			 tc = new TitleContents();
			 www.setTc(tc);
			 run = true;
		 }
		else if (qName.equals("editor") && currentElement.equals("none")) 
		{
			 editor = new Editor();
			 currentElement = "editor";
			 tc = new TitleContents();
			 editor.setTc(tc);
			 run = false;
		 } 
				
	}
	
	
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		 if(run == true) 
		 {
			 if(qName.equalsIgnoreCase("author"))
			 {
				 tc.addAuthor(tag);
			 } 
			 else if (qName.equals("editor")) 
			 {
				 tc.setEditor(tag);
			 } 
			 else if (qName.equals("title"))
			 {
				 tc.setTitle(tag);
			 }
			 else if (qName.equals("booktitle"))
			 {
				 tc.setBooktitle(tag);
			 } 
			 else if (qName.equals("pages")) 
			 {
				 tc.setPages(tag);
			 }
			 else if (qName.equals("year"))
			 {
				 tc.setYear(tag);
			 } 
			 else if (qName.equals("address")) 
			 {
				 tc.setAddress(tag);
			 } 
			 else if (qName.equals("journal"))
			 {
				 tc.setJournal(tag);
			 }
			 else if (qName.equals("volume")) 
			 {
				 tc.setVolume(tag);
			 } 
			 else if (qName.equals("number"))
			 {
				 tc.setNumber(tag);
			 } 
			 else if (qName.equals("month")) 
			 {
				 tc.setMonth(tag);
			 } 
			 else if (qName.equals("url")) 
			 {
				 tc.setUrl(tag);
			 } 
			 else if (qName.equals("ee")) 
			 {
				 tc.setEe(tag);
			 } 
			 else if (qName.equals("cdrom")) 
			 {
				 tc.setCdrom(tag);
			 } 
			 else if (qName.equals("cite")) 
			 {
				 tc.setCite(tag);
			 } 
			 else if (qName.equals("publisher")) 
			 {
				 tc.setPublisher(tag);
			 }
			 else if (qName.equals("note")) 
			 {
				 tc.setNote(tag);
			 }
			 else if (qName.equals("crossref")) 
			 {
				 tc.setCrossref(tag);
			 }
			 else if (qName.equals("isbn")) 
			 {
				 tc.setIsbn(tag);
			 }
			 else if (qName.equals("series"))
			 {
				 tc.setSeries(tag);
			 }
			 else if (qName.equals("school")) 
			 {
				 tc.setSchool(tag);
			 } 
			 else if (qName.equals("chapter"))
			 {
				 tc.setChapter(tag);
			 }
		 }
		 if(qName.equals("article")) 
		 {
			 qe.addArticle(article);
			 currentElement = "none";
		 } 
		 else if(qName.equals("inproceedings")) 
		 {
			 qe.addInProceedings(inproceedings);
			 currentElement = "none";
		 }
		 else if(qName.equals("proceedings"))
		 {
			 qe.addProceedings(proceedings);
			 currentElement = "none";
		 }
		 else if(qName.equals("book")) 
		 {
			 qe.addBook(book);
			 currentElement = "none";
		 }
		 else if(qName.equals("incollection")) 
		 {
			 qe.addInCollection(incollection);
			 currentElement = "none";
		 }
		 else if(qName.equals("phdthesis"))
		 {
			 qe.addPhdThesis(phdthesis);
			 currentElement = "none";
		 } 
		 else if(qName.equals("mastersthesis")) 
		 {
			 qe.addMastersThesis(mastersthesis);
			 currentElement = "none";
		 } 
		 else if(qName.equals("www")) {
			 qe.addWWW(www);
			 currentElement = "none";
		 }
		 else if(qName.equals("editor") && currentElement.equals("editor")) 
		 {
			 editor.setText(tag);
			 currentElement = "none";
		 }
	}
	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		StringBuilder s = new StringBuilder();
		 for(int i = start; i <= length - 1; i++) {
			 String tmp = String.valueOf(ch[i]);
			 if(tmp.isEmpty() || tmp.equals(System.lineSeparator())) {
				 continue;
			 }
			 s.append(ch[i]);
		 }
		 tag = s.toString();
	}
}
