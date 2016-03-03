package slogo.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


public class Editor extends ScrollPane implements IEditor {
    
    private TextArea userInput;
    
    public Editor(double paneWidth, double paneHeight){
        
        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);
        
        // set policy for scrolling
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        // Does not allow for horizontal scrolling
        this.setFitToWidth(true);
        
        // Initialize userInput
        
        userInput= new TextArea();
        userInput.setPromptText("Enter your SLogo code here!");
        this.setContent(userInput);
    }
    
    @Override
    public StringBuilder getEditorText () {
        StringBuilder userCommand = new StringBuilder(userInput.getText());
        
        return userCommand;
    }

	public void setEditorText(String code) {
		// TODO Auto-generated method stub
		userInput.setText(code);
	}

}
