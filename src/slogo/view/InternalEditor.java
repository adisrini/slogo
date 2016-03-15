package slogo.view;

import java.util.Observable;

/**
 * The Singleton internal editor that is called when front-end controls are used
 * to change turtle appearance, pen color, background color, and others.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class InternalEditor extends Observable implements IEditor {
    private StringBuilder internalCommand;
    private static InternalEditor instance = null;
    
    private int id;
    
    /**
     * Initializes (privately) the internal editor.
     */
    private InternalEditor(){
        internalCommand = new StringBuilder();
    }
    
    /**
     * Gets the editor text from the internal editor
     */
    @Override
    public StringBuilder getEditorText () {
        return internalCommand;
    }
    
    /**
     * Sets the editor text to be a certain command, and then
     * notifies observers.
     * @param command
     */
    void setEditorText(StringBuilder command){
        internalCommand = new StringBuilder(command);
        setChanged();
        notifyObservers(internalCommand);
    }
    
    /**
     * The singleton caller returning an instance of the
     * internal editor
     * @return
     */
    public static InternalEditor getInstance(){
        if (instance == null){
            instance = new InternalEditor();
        }
        return instance;
    }
    
    /**
     * Sets the current ID of the internal memory being used
     * @param index
     */
    public void setId(int index){
        id=index;
    }
    
    /**
     * Gets the current ID of the internal memory being used
     * @return
     */
    int getId(){
        return id;
    }

}
