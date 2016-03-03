package slogo.view;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ToolPanel extends ToolBar {

    private static final String DEFAULT_LOCATION = "/resources/bundles/";
    private static final String EXTENSION = "Tool.properties";
    private Properties toolProperties;
    private Properties imageProperties;

    public ToolPanel (Map<String, EventHandler<ActionEvent>> eventMap) {
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
                    .getResourceAsStream("/resources/images/Images.properties"));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        initializeButtons(eventMap);
        initializeColorPicker();
    }

    private void initializeButtons (Map<String, EventHandler<ActionEvent>> eventMap) {
        for (Enumeration<?> enumer = toolProperties.propertyNames(); enumer.hasMoreElements();) {
            String name = (String) enumer.nextElement();
            System.out.println(name);
            String nameValue = toolProperties.getProperty(name);

            EventHandler<ActionEvent> actionOnClick = eventMap.get(nameValue);

            this.getItems().add(makeButton(name, actionOnClick));
        }
    }

    private Button makeButton (String name, EventHandler<ActionEvent> event) {
        Button button = new Button();

        if (imageProperties.keySet().contains(name)) {
            ImageView buttonImage = new ImageView(new Image("/resources/images/" +
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
    
    private void initializeColorPicker(){
        this.getItems().add(new ColorPicker());
    }
    
    private void initializePenCustomizer() {
    	
    }

}
