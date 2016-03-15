package slogo.view;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is a horizontal panel with a rectangular
 * color and a number index for use with the Palette
 * class.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class HColorAlias extends HBox {

    private Color myColor;
    private String myIndex;
    
    private static final double WIDTH = 45;
    private static final double HEIGHT = 20;

    /**
     * Initializes the HBox with a given color and index
     * @param color
     * @param index
     */
    public HColorAlias (Color color, String index) {
        myColor = color;
        myIndex = index;

        establishHorizontalPanel();
    }
    
    /**
     * Returns the color of this instance.
     * @return
     */
    public Color getColor() {
    	return this.myColor;
    }

    /**
     * Establishes the horizontal panel with the specified
     * color and text.
     */
    private void establishHorizontalPanel () {
        Rectangle color = new Rectangle(WIDTH, HEIGHT);
        color.setFill(myColor);

        Label index = new Label(myIndex);
        Tooltip tip = new Tooltip(myIndex);

        this.getChildren().addAll(color, index);
        Tooltip.install(this, tip);
    }

    /**
     * Returns the index of this instance.
     */
    public String toString () {
        return myIndex;
    }
}
