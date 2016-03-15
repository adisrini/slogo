package slogo.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * Handles adding content and buttons to tab and positioning them on the screen
 * 
 * @author Arjun Desai, Aditya Srinivasan
 * 
 */
public class CustomTab extends Tab{
    private BorderPane root;
    private HBox buttonContainer;
    private VBox contentContainer;
    
    /**
     * Initializes the CustomTab object, and populates its
     * contents.
     */
    public CustomTab(){
        root = new BorderPane();
        
        this.setContent(root);
        initializeButtonContainer();
        initializeContentContainer();
        
        root.setCenter(contentContainer);
        root.setBottom(buttonContainer);
    }
    
    /**
     * Alternate constructor, to define with a name.
     * @param name
     */
    public CustomTab(String name){
        this();
        this.setText(name);
    }
    
    /**
     * Initializes the container holding the buttons
     * for control.
     */
    private void initializeButtonContainer(){
        buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(25,25,25,25));
        buttonContainer.setAlignment(Pos.CENTER);
    }
    
    /**
     * Initializes the container for the content.
     */
    private void initializeContentContainer(){
        contentContainer = new VBox();
        contentContainer.setPadding(new Insets(20, 50, 20, 50));
    }
    
    /**
     * Adds buttons to the button container.
     * @param buttons
     */
    void addButtons(Button... buttons){
        for (Button button :buttons){
            button.setAlignment(Pos.CENTER);
        }
        buttonContainer.getChildren().addAll(buttons);
    }
    
    /**
     * Adds content to the content container.
     * @param nodes
     */
    void addContent(Node... nodes){
        contentContainer.getChildren().addAll(nodes);
    }
    
    /**
     * Creates a label with a given title.
     * @param titleName
     * @return the label.
     */
    protected Label generateTitle(String titleName){
        Label title = new Label(titleName);
        title.setPadding(new Insets(10,10,10,10));
        return title;
    }
}
