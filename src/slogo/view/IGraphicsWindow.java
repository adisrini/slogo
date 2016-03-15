package slogo.view;

import javafx.scene.paint.Color;

/**
 * Graphics Window API
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public interface IGraphicsWindow {
    
    //External API
    
    
    
    //Internal API
    
    /**
     * The graphics of the turtle are updated based on the new state to draw. 
     * 
     * The turtle's current state is stored in the graphics (positionX, positionY, and Orientation)
     * This method will compute where to move the turtle to and add a new line based on that movement
     *  
     * If the message is only to turn (i.e. Change in orientation) the orientation of the turtle will change
     * @param newState: new state the controller passes to the view and passes to the updateGraphics method
     */
    //void updateGraphics(IState newState);
    
    /**
     * Sets the pen color for the line to be drawn
     * @param color: new pen color
     */
    void setPenColor(Color color);
    
    /**
     * Sets background color for background window
     * @param color: new background color
     */
    void setBackgroundColor(Color color);
    
    /**
     * Allows the user to set a new image for the turtle if desired
     * @param turtleImage: string that designates what resource file to load for the image
     */
    void setTurtleImage(String turtleImage);
    
    
    
    
    
}
