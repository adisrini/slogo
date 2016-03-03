package slogo.xml;

/**
 * Creates an XML file from the current configuration of the simulation into the textXML.dataFiles folder"
 * @author Aditya Srinivasan, Harry Guo, and Michael Kuryshev
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cellsociety_team25.MainController;
import cellsociety_team25.SimulationException;
import cellsociety_team25.SimulationModel;
import slogo.model.SlogoException;
import slogo.view.View;

import java.io.File;
import java.util.ResourceBundle;

public class XMLWriter {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;

	public static final String XML_RESOURCE_PACKAGE = "resources/preferences";
	private static final String FILE_DESTINATION = "src/testXMLs/dataFiles/";
	
	private ResourceBundle myXMLResources;

	private View myView;

	/**
	 * Constructor to initialize factory and builder to create an XML file.
	 * @param model to build xml off of
	 */
	
	public XMLWriter(View view){
		myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE);
		myView = view;

		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
		} catch (Exception e) {
			throw new SlogoException();
		}
	}
	
	/**
	 * Create the XML file given a title and the current state of the simulation.
	 * @param XMLtitle
	 */
	public void create(String XMLtitle){
		try {
			// TODO: change to be specific for Slogo XML
			Element simulation = createRoot(myXMLResources.getString("Simulation"));

			Element gameType = addElement(myXMLResources.getString("GameType"), simulation);
			addAttribute(myXMLResources.getString("Name"), myModel.getGameType(), gameType);

			Element title = addElement(myXMLResources.getString("Title"), simulation);
			addAttribute(myXMLResources.getString("Name"), myModel.getTitle(), title);

			Element author = addElement(myXMLResources.getString("Author"), simulation);
			addAttribute(myXMLResources.getString("Name"), myModel.getAuthor(), author);

			Element parameters = addElement(myXMLResources.getString("Parameters"), simulation);
			for (String parameterName: myModel.getParameterMap().keySet()){
				Element parameter = addElement(myXMLResources.getString("Parameter"), parameters);
				addAttribute(myXMLResources.getString("Name"), parameterName, parameter);
				addAttribute(myXMLResources.getString("Value"), myModel.getParameterMap().get(parameterName).toString(), parameter);
			}

			Element colorMap = addElement(myXMLResources.getString("ColorMap"), simulation);
			for (Integer state: myModel.getColorMap().keySet()){
				Element color = addElement(myXMLResources.getString("Color"), colorMap);
				addAttribute(myXMLResources.getString("State"), state.toString(), color);
				addAttribute(myXMLResources.getString("Color"), myModel.getColorMap().get(state) , color);
			}

			Element grid = addElement(myXMLResources.getString("Grid"), simulation);
			for (int i = 0; i < myModel.getGridStates()[0].length; i++) {
				Element row = addElement(myXMLResources.getString("Row"), grid);
				String temp = "";
				for (int j = 0; j < myModel.getGridStates()[0].length; j++) {
					temp+=myModel.getGridStates()[i][j] + ",";
				}
				temp = temp.substring(0, temp.length() - 1);
				addAttribute(myXMLResources.getString("States"), temp, row);
			}

			// write the content into xml file
			writeToFolder(XMLtitle);

		} catch (Exception e) {
			throw new SlogoException();
		}

	}

	/**
	 * Writes XML file to a folder given a title for file.
	 * @param XMLtitle
	 */
	private void writeToFolder(String XMLtitle){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(FILE_DESTINATION + XMLtitle + ".xml"));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new SimulationException(myModel.getErrorResources().getString("ErrorSave"));
		}
	}

	/**
	 * Creates the root element of the XML file.
	 * @param name
	 * @return an Element for future reference
	 */
	public Element createRoot(String name){
		Element eElement = doc.createElement(name);
		doc.appendChild(eElement);
		return eElement;
	}

	/**
	 * Adds an element to a parent element.
	 * @param name
	 * @return an Element for future reference
	 */
	public Element addElement(String name, Element parent){
		Element eElement = doc.createElement(name);
		parent.appendChild(eElement);
		return eElement;
	}
	
	/**
	 * Adds an attribute with given name and value to a parent element.
	 * @param name
	 * @param value
	 * @param parent
	 */
	public void addAttribute(String name, String value, Element parent){
		Attr node = doc.createAttribute(name);
		node.setValue(value);
		parent.setAttributeNode(node);
	}
}