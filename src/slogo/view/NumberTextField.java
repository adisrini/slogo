package slogo.view;

import javafx.scene.control.TextField;

/**
 * A custom text field that only accepts numbers.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class NumberTextField extends TextField {

	/**
	 * Replaces unwanted text with a desired text.
	 */
    @Override
    public void replaceText(int start, int end, String text){
        if (validate(text)){
            super.replaceText(start, end, text);
        }
    }

    /**
     * Replaces the selection with specified text.
     */
    @Override
    public void replaceSelection(String text){
        if (validate(text)){
            super.replaceSelection(text);
        }
    }

    /**
     * Checks if the text is valid.
     * @param text
     * @return
     */
    private boolean validate(String text){
        return text.matches("[0-9]*");
    }
    
}
