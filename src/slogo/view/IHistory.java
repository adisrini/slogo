package slogo.view;

public interface IHistory {
    
   //External APIs
    
    
    //Internal APIs
    
    /**
     * update the history display to display the most recently executed editorText
     * 
     * @param editorText: most recently displayed string of commands for execution that the user
     *        enters into the editor
     */
    void updateHistory(String editorText);
}
