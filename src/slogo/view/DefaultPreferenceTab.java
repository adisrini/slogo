package slogo.view;

import java.io.File;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import slogo.xml.XMLReader;
import slogo.xml.XMLWriter;

/**
 * A tab that holds the default preferences as specified
 * in the DefaultPreferences.xml file.
 * 
 * @author Arjun Desai, Aditya Srinivasan
 *
 */
public class DefaultPreferenceTab extends CustomTab {
    private static final String DEFAULT_XML_PATH = "./src/resources/preferences/PreferencesXML.xml";
    public static final File XML_DEFAULT = new File(DEFAULT_XML_PATH);
    
    private static final String VALUE = "value";

    private XMLReader xmlReader;
    private XMLWriter xmlWriter;

    private LanguageComboBox language;
    private Palette penColor;
    private NumberTextField penWidth;
    private Palette backgroundColor;
    private TurtlePicker shapePicker;
    private ImageView shapeField;
    private NumberTextField numTurtles;
    private ComboBox<String> files;

    /**
     * Initializes the tab with a name and initializes attributes from the
     * XML file. Also adds content to the tab to display what the default
     * preferences are.
     * @param name
     */
    public DefaultPreferenceTab (String name) {
        this.setText(name);
        
        File xml = new File(DEFAULT_XML_PATH);
        
        xmlReader = new XMLReader(xml);
        xmlWriter = new XMLWriter(xml);
        
        files = new ComboBox<String>();
        
        initializeAttributes();

        this.addContent(generateTitle("Language"), language,
                        generateTitle("File To Load"), files,
                        generateTitle("Pen Color"), penColor,
                        generateTitle("Pen Width"), penWidth,
                        generateTitle("Background Color"), backgroundColor,
                        generateTitle("Turtle Image"), shapeField,
                        generateTitle("Number of Turtles"), numTurtles);
    }
    
    /**
     * Initializes all attributes.
     */
    private void initializeAttributes() {
    	initializeLanguage();
        initializePenColor();
        initializePenWidth();
        initializeBackgroundColor();
        initializeShape();
        initializeNumTurtles();
    }
    
    /**
     * Initializes the language as read from the XML.
     */
    private void initializeLanguage(){
        language = new LanguageComboBox();
        language.setValue(xmlReader.readAttributeContent("Language", VALUE,"0"));
    }
    
    /**
     * Initializes the pen color as read from the XML.
     */
    private void initializePenColor () {
        penColor = new Palette();
        String index = xmlReader.readAttributeContent("PenColorIndex", VALUE,"0");
        penColor.loadColor(index);
    }

    /**
     * Initializes the background color as read from the XML.
     */
    private void initializeBackgroundColor () {
        backgroundColor = new Palette();
        String index = xmlReader.readAttributeContent("BackgroundColorIndex", VALUE,"0");
        backgroundColor.loadColor(index);
    }

    /**
     * Initializes the pen width as read from the XML.
     */
    private void initializePenWidth () {
        penWidth = new NumberTextField();
        String defaultWidth = xmlReader.readAttributeContent("PenWidth", VALUE,"0");
        penWidth.setText(defaultWidth);
    }

    /**
     * Initializes the initial number of turtles as read from the XML.
     */
    private void initializeNumTurtles () {
        numTurtles = new NumberTextField();
        String defaultNumTurtles = xmlReader.readAttributeContent("NumTurtles", VALUE,"0");
        numTurtles.setText(defaultNumTurtles);
    }

    /**
     * Initializes the shape as read from the XML.
     */
    private void initializeShape () {
        String initialIndex = xmlReader.readAttributeContent("Shape", VALUE,"0");
        shapePicker = new TurtlePicker(initialIndex);
        shapeField = new ImageView();

        shapeField = shapePicker.loadImage(initialIndex);

        shapeField.setFitWidth(200);
        shapeField.setFitHeight(200);

        shapeField.setOnMouseClicked(e -> {
            shapePicker.showAndWait();
            shapeField.setImage(shapePicker.getImage().getImage());;
        });
    }
    
    /**
     * Writes updated values to the DefaultPreferences XML file.
     */
    void writeToXML(){
        xmlWriter.modifyAttribute("Language", VALUE, "0", language.getValue());
        xmlWriter.modifyAttribute("BackgroundColorIndex", VALUE, "0", backgroundColor.getValue());
        xmlWriter.modifyAttribute("PenColorIndex", VALUE, "0", penColor.getValue());
        xmlWriter.modifyAttribute("PenWidth", VALUE, "0", penWidth.getText());
        xmlWriter.modifyAttribute("Shape", VALUE, "0", shapePicker.getValue());
        xmlWriter.modifyAttribute("NumTurtles", VALUE, "0", numTurtles.getText());
    }
}
