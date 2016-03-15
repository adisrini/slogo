package slogo.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


/**
 * ScrollPane displays messages (commands, command results, and errors) to user
 * 
 * Instantiated by: View.java --> executes printText when message needs to be displayed to user
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class Console extends ScrollPane implements IConsole {

    private VBox textPane;

    private static final String DEFAULT_COLOR = "WHITE";

    private Text textToDisplay;

    /**
     * Initializes the console with a given width and height
     * @param paneWidth
     * @param paneHeight
     */
    public Console (double paneWidth, double paneHeight) {

        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        this.setFitToWidth(true);
        this.setVvalue(1.0);

        textPane = new VBox();

        textToDisplay = new Text();
        textToDisplay.setFill(Paint.valueOf(DEFAULT_COLOR));
        textPane.getChildren().add(textToDisplay);

        this.setContent(textPane);
    }

    /**
     * Updates the textToDisplay to include the new text passed in
     * 
     * @param text: text passed to display on the console
     */
    @Override
    public void printText (Text text) {
        textToDisplay = text;
        textPane.getChildren().add(textToDisplay);
    }

}
