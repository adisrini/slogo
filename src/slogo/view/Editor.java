package slogo.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * The editor view component. This is where the user
 * can type their code that will be run.
 *
 * @author Aditya Srinivasan, Arjun Desai
 */
public class Editor extends ScrollPane implements IEditor {
    
    private TextArea userInput;
    
    private static final String PROMPT = "Enter your SLogo code here!";
    
    /**
     * Initializes the editor with a specified width and height,
     * and an initial prompt text.
     * @param paneWidth
     * @param paneHeight
     */
    public Editor(double paneWidth, double paneHeight){
        
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        this.setFitToWidth(true);
        
        userInput= new TextArea();
        userInput.setPromptText(PROMPT);
        this.setContent(userInput);
    }
    
    /**
     * Returns the text from the editor to whoever
     * wants it.
     */
    @Override
    public StringBuilder getEditorText () {
        StringBuilder userCommand = new StringBuilder(userInput.getText());
        
        return userCommand;
    }

    /**
     * Sets the editor text to a specified String
     * @param code
     */
	public void setEditorText(String code) {
		userInput.setText(code);
	}

}
