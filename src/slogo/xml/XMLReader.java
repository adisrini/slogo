package slogo.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;

/**
 * This is the primary class to parse information from the XML File.
 * 
 * @author Aditya Srinivasan, Harry Guo, and Michael Kuryshev
 */

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import slogo.model.SlogoException;


public class XMLReader {

    private Document doc;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private File xmlFile;
    private ResourceBundle myXMLResources;

    /**
     * Takes in an xml file and initializes a factory and builder to parse the information (DATA
     * ONLY).
     * 
     * @param xml file
     */
    public XMLReader (File xml) {
        initializeDocument(xml);
    }
    
    private void initializeDocument(File xml){
        try {
            //System.out.println("XML file : "+xml);
            xmlFile = xml;
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            System.out.println("XML FILE: " + xml);

            doc = builder.parse(xml);
            doc.getDocumentElement().normalize();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Parsed Document");
        System.out.println(doc.getChildNodes());
        System.out.println("");
    }
    
    /**
     * Takes a tag name and attribute and parses the value of the attribute
     * 
     * @param tagName of the element
     * @param attribute
     * @return value of the attribute
     */
    public String readElementContent (String tagName,String tabIndex) {
        Node element = doc.getElementsByTagName(tagName).item(0);
        return element.getTextContent();
    }
    
    public String readAttributeContent(String elementName,String attributeName, String tabIndex){
        System.out.println(doc.getElementsByTagName(elementName).item(0));
        
        NodeList elements = doc.getElementsByTagName("Tab");
        Node element = null;
        
        for (int i=0; i<elements.getLength();i++){
            element = elements.item(i);
            if (element.getAttributes().getNamedItem("index").equals(tabIndex)){
                break;
            }
        }
        
        NodeList subElements = element.getChildNodes();
        Node attribute = null;
        for (int i =0 ;i <subElements.getLength();i++){
            if (subElements.item(i).getNodeName().equals(elementName)){
                attribute = subElements.item(i).getAttributes().getNamedItem(attributeName);
            }

        }

        return attribute.getTextContent();
    }
    
    public NodeList getElementsOfName(String tagname){
        return doc.getElementsByTagName(tagname);
    }
    
    
    
}
