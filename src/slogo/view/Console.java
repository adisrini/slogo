package slogo.view;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class Console extends ScrollPane implements IConsole{

	private VBox textPane;
	
	private static final String ERROR_COLOR = "RED";
	private static final String DEFAULT_COLOR = "WHITE";
	
	private static final String ERROR_PREFIX = "ERROR: ";
	
    private Text textToDisplay;
    
    public Console(double paneWidth, double paneHeight){
        
        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        // set policy for scrolling
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        // Does not allow for horizontal scrolling
        this.setFitToWidth(true);
        this.setVvalue(1.0);
        
        textPane = new VBox();
        
        // Initialize textToDisplay
        textToDisplay= new Text();
        textToDisplay.setFill(Paint.valueOf(DEFAULT_COLOR));
        textPane.getChildren().add(textToDisplay);

        this.setContent(textPane);
    }
    
    /**
     * Updates the textToDisplay to include the new text passed in
     * @param text: text passed to display on the console
     */
    @Override
    public void printText(Text text) {
    	textToDisplay = text;
    	textPane.getChildren().add(textToDisplay);
    }

}
