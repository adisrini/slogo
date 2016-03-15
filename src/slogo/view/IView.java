package slogo.view;

import java.util.List;
import javafx.scene.Scene;
import slogo.model.IState;

/**
 * View API
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public interface IView {
    
    // External APIS
    
    /**
     * Runs a list of executions that are related to the graphic display of updating a state
     * 
     * 
     * @param state: State passed in from the Controller
     */
    public void updateState(List<IState> states);
    
    /**
     * 
     * @return current scene to the Controller to set the stage's scene
     */
    public Scene getScene();
    
    /**
     * Runs on execution (MenuBar --> RUN, ToolBar--> RUN)
     * @return StringBuilder of the editor.getEditorText() to Controller
     */
    public StringBuilder execute();
    
    /**
     * Go to the previous state
     */
    public void undo (List<IState> states);
    
    // Internal APIS
    
    /**
     * This method is executed when the save buttons are clicked on
     * The method creates a popup window for the user to name methods
     * The name is saved as a string
     * 
     * The name and editorText are passed to the Methods class, in the updateMethods() method. The method
     * updates the memory to store the current StringBuilder editorText and the name into the Map of Methods in 
     * the Memory
     * 
     * @return save current text on console into method
     */
    void onSave(String methodName);
    
}
