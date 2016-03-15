package slogo.view;

import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This is the combobox used for displaying the line style
 * options in the toolbar.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class LineStyleOptions extends ComboBox<Line> {
	
	private static final double WIDTH = 150;
	
	private Color penColor;
	private double penWidth;
	
	/**
	 * Initializes the combobox with a color and width.
	 * @param color
	 * @param width
	 */
	public LineStyleOptions(Color color, double width) {
		
		this.setWidth(WIDTH);
		
		this.penWidth = width;
		this.penColor = color;
		
		populateLineStyleOptions(this.penWidth, this.penColor);
		
	}
	
	/**
	 * Populates the combobox with linestyle options given a width
	 * and color.
	 * @param penWidth
	 * @param penColor
	 */
	private void populateLineStyleOptions(double penWidth, Color penColor) {
		
		this.getItems().clear();
		
		LineStyle[] styles = LineStyle.values();
		
		for(LineStyle style : styles) {
			Line line = style.getLine();
			line.setStartX(-(WIDTH)/2);
			line.setEndX(WIDTH/2);
			line.setStrokeWidth(penWidth);
			line.setStroke(penColor);
			this.getItems().add(line);
		}
		
	}

	/**
	 * Sets the width of the lines displayed in the combobox.
	 * @param chosenWidth
	 */
	void setChosenWidth(double chosenWidth) {
		this.penWidth = chosenWidth;
		populateLineStyleOptions(chosenWidth, penColor);
	}

	/**
	 * Sets the color of the lines displayed in the combobox.
	 * @param chosenColor
	 */
	void setChosenColor(Color chosenColor) {
		this.penColor = chosenColor;
		populateLineStyleOptions(penWidth, chosenColor);
	}

}
