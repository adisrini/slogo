package slogo.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;

/**
 * Creates an XML file from the current configuration of the simulation into the textXML.dataFiles
 * folder"
 * 
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLWriter {

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document doc;
    private File xml;
    
    public static final String XML_RESOURCE_PACKAGE = "resources/preferences";

    public XMLWriter (File xml) {
        this.xml=xml;
        initializeDocument(xml);
    }
    
    private void initializeDocument(File xml){
        try {
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

    public void modifyAttribute (String elementName,String attributeName, String tabIndex, Object content) {
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
                System.out.println("Element: "+subElements.item(i));
                attribute = subElements.item(i).getAttributes().getNamedItem(attributeName);
            }

        }
        
        System.out.println("Attribute found: "+attribute);
        System.out.println("New Attribute: "+content.toString());
        attribute.setTextContent(content.toString());
        transformXML();
    }
    
    public void modifyElementContent(String elementName,String tabIndex, Object content){
        Node element = doc.getElementsByTagName(elementName).item(0);
        element.setTextContent(content.toString());
    }
    
    /**
     * Creates the root element of the XML file.
     * 
     * @param name
     * @return an Element for future reference
     */
    public Element createRoot (String name) {
        Element eElement = doc.createElement(name);
        doc.appendChild(eElement);
        return eElement;
    }

    /**
     * Adds an element to a parent element.
     * 
     * @param name
     * @return an Element for future reference
     */
    public Element addElementWithAttributes (String name, Element parent,Map<String,String> attributes) {
        Element eElement = doc.createElement(name);
        for (String key : attributes.keySet()){
            eElement.setAttribute(key, attributes.get(key));
        }
        parent.appendChild(eElement);
        transformXML();
        return eElement;
    }
    
    private Element addElementWithValueAttribute(String name, Element parent, String attribute){
        Element eElement = doc.createElement(name);
        eElement.setAttribute("value", attribute);
        parent.appendChild(eElement);
        transformXML();
        return eElement;
    }
    /**
     * Adds an attribute with given name and value to a parent element.
     * 
     * @param name
     * @param value
     * @param parent
     */
    public void addAttribute (String name, String value, Element parent) {
        Attr node = doc.createAttribute(name);
        node.setValue(value);
        parent.setAttributeNode(node);
    }
    
    public void addElementWithChildren(String index,String name,Map<String,String> children){
        Element parent = (Element) doc.getElementsByTagName("slogo").item(0);
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put("index", index);
        attributes.put("name", name);
        Element tab = addElementWithAttributes("Tab",parent,attributes);
        
        for (String key : children.keySet()){
            addElementWithValueAttribute(key,tab,children.get(key));
        }
        
        transformXML();
    }
    
    private void transformXML(){
       
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xml);
            transformer.transform(source, result);
        }
        catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
