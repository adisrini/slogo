package slogo.view;

import java.util.List;

import javafx.scene.shape.Line;

/**
 * Enum class for the line style. Holds values specifying
 * solid, dashed, stagger_dash, or dotted.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public enum LineStyle {
	SOLID(),
	DASHED(20d, 10d),
	STAGGER_DASH(25d, 20d, 10d, 20d),
	DOTTED(1d, 14d);
	
	private Line myLine;
	
	/**
	 * Initializes a particular LineStyle enum
	 * with dash array doubles.
	 * @param doubles
	 */
	private LineStyle(Double... doubles) {
		myLine = new Line();
		myLine.getStrokeDashArray().addAll(doubles);
	}
	
	/**
	 * Returns the line associated with the enum.
	 * @return
	 */
	Line getLine() {
		return this.myLine;
	}
	
	/**
	 * Returns the dash array associated with the enum.
	 * @return
	 */
	List<Double> getDashArray() {
		return this.myLine.getStrokeDashArray();
	}

}
