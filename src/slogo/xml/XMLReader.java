package slogo.xml;

/**
 * This is the primary class to parse information from the XML File.
 * @author Aditya Srinivasan, Harry Guo, and Michael Kuryshev
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import slogo.model.SlogoException;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class XMLReader {

	private Document doc;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private File xmlFile;
	public static final String XML_RESOURCE_PACKAGE = "resources/preferences";
	private ResourceBundle myXMLResources;

	/**
	 * Takes in an xml file and initializes a factory and builder to parse the information (DATA ONLY).
	 * @param xml file
	 */
	public XMLReader(File xml) {
		myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE);
		try {
			xmlFile = xml;
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			throw new SlogoException();
		}
	}
	
	/**
	 * Takes a tag name and attribute and parses the value of the attribute
	 * @param tagName of the element
	 * @param attribute
	 * @return value of the attribute
	 */
	public String getXMLTagInformation(String tagName, String attribute){
		NodeList list = doc.getElementsByTagName(tagName);
		Node nNode = list.item(0);
		Element eElement = (Element) nNode;
		String value = eElement.getAttribute(attribute);
		return value;
	}

}