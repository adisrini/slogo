package slogo.view;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import slogo.xml.XMLReader;


public class ToolPanel extends ToolBar {

    private static final String DEFAULT_LOCATION = "/resources/bundles/";
    private static final String EXTENSION = "Tool.properties";
    private Properties toolProperties;
    private Properties imageProperties;
    
    private HBox leftAlign;
    private HBox rightAlign;
    
    private LineStyleOptions styler;
    
    private Color chosenColor;
    private double chosenWidth;
    
    private Color DEFAULT_COLOR;
    private double DEFAULT_WIDTH;
    
    private XMLReader xmlReader;
    
    private Line chosenLine;
    
    private Color c;
    private InternalEditor internalEditor;
    
    private GraphicsWindow graphicsWindow;
    
    public ToolPanel (Map<String, EventHandler<ActionEvent>> eventMap, GraphicsWindow gw) {
    	xmlReader = new XMLReader(DefaultPreferenceTab.XML_DEFAULT);
    	
    	this.graphicsWindow = gw;
    	
    	chosenColor = DEFAULT_COLOR;
    	chosenWidth = DEFAULT_WIDTH;
    	chosenLine = new Line();
    	
    	leftAlign = new HBox();
    	rightAlign = new HBox();
    	
        internalEditor = InternalEditor.getInstance();
        
        toolProperties = new OrderedProperties();
        imageProperties = new OrderedProperties();
        try {
            toolProperties.load(this.getClass()
                    .getResourceAsStream(DEFAULT_LOCATION + "English" + EXTENSION));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            imageProperties.load(this.getClass()
                    .getResourceAsStream("/resources/toolbarimages/Images.properties"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        initializeButtons(eventMap, leftAlign);
        initializeColorPicker(leftAlign);
        initializePenCustomizer(rightAlign);
        
        HBox.setHgrow(leftAlign, Priority.ALWAYS);
        HBox.setHgrow(rightAlign, Priority.ALWAYS);
        
        leftAlign.setAlignment(Pos.CENTER_LEFT);
        rightAlign.setAlignment(Pos.CENTER_RIGHT);
        
        this.getItems().addAll(leftAlign, rightAlign);
    }

    private void initializeButtons (Map<String, EventHandler<ActionEvent>> eventMap, HBox alignment) {
        for (Enumeration<?> enumer = toolProperties.propertyNames(); enumer.hasMoreElements();) {
            String name = (String) enumer.nextElement();
            System.out.println(name);
            String nameValue = toolProperties.getProperty(name);

            EventHandler<ActionEvent> actionOnClick = eventMap.get(nameValue);
            
            if (nameValue.equals("Reset")){
                actionOnClick = e->internalEditor.setEditorText(new StringBuilder("Reset"));
            }
            
            alignment.getChildren().add(makeButton(name, actionOnClick));
        }
    }

    private Button makeButton (String name, EventHandler<ActionEvent> event) {
        Button button = new Button();

        if (imageProperties.keySet().contains(name)) {
            ImageView buttonImage = new ImageView(new Image("/resources/toolbarimages/" +
                                                            imageProperties.get(name)));
            button.setGraphic(buttonImage);

            Tooltip tooltip = new Tooltip(name);
            tooltip.install(button, tooltip);
        }
        else {
            button.setText(name);
        }

        button.setId(name);
        button.setOnAction(event);

        return button;
    }
    
    private void initializeColorPicker(HBox alignment){
    	ColorPicker cp = new ColorPicker();
    	c = Color.WHITE;
    	cp.setOnAction(e -> {
    		c = cp.getValue();
    		String rgb = Math.round(c.getRed()*255) + " " + Math.round(c.getGreen()*255) + " " + Math.round(c.getBlue()*255);
        	Tooltip tp = new Tooltip(rgb);
        	Tooltip.install(cp, tp);
    	});
    	
    	alignment.getChildren().add(cp);
        
    }
    
    private void initializePenCustomizer(HBox alignment) {
    	
    	InternalEditor internalEditor = InternalEditor.getInstance();
    	
    	styler = new LineStyleOptions(chosenColor, chosenWidth);
    	
    	styler.setOnAction(e -> {
    		
    		chosenLine = styler.getValue();
    		
    		int index = 1;
    		
    		for(Line line : styler.getItems()) {
    			if(chosenLine.equals(line)) {
    				break;
    			}
    			index++;
    		}
    		
    		internalEditor.setEditorText(new StringBuilder("SetPenStyle " + index));
    		
    	});
    	
    	Palette palette = new Palette();
		SingleSelectionModel<HColorAlias> paletteSelector = palette.getSelectionModel();
		
    	palette.setOnAction(e -> {
    		
    		HColorAlias selectedColor = paletteSelector.getSelectedItem();
    		chosenColor = selectedColor.getColor();
    		
    		styler.setChosenColor(chosenColor);
    		
    		internalEditor.setEditorText(new StringBuilder("SetPenColor " + selectedColor.toString()));
    		
    	});
    	
		for(HColorAlias color : palette.getItems()) {
			if(color.toString().equals(xmlReader.readAttributeContent("PenColorIndex", "value", "0"))) {
				palette.setValue(color);
			}
		}
    	
    	NumberTextField penWidthField = new NumberTextField();
    	penWidthField.textProperty().addListener(e -> {
    		
    		chosenWidth = Double.parseDouble(penWidthField.getText());
    		
    		internalEditor.setEditorText(new StringBuilder("SetPenSize " + Double.toString(chosenWidth)));
    		
    		styler.setChosenWidth(chosenWidth);
    		
    	});
    	
    	penWidthField.setText(xmlReader.readAttributeContent("PenWidth", "value", "0"));
    	
    	alignment.getChildren().addAll(palette, penWidthField, styler);
    	
    }

}
