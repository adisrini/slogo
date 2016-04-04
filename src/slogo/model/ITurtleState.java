package slogo.model;

public interface ITurtleState extends IState{
	/**
     * Sets the x.
     *
     * @param X the new x
     */
    public void setX (double X);

    /**
     * Sets the y.
     *
     * @param Y the new y
     */
    public void setY (double Y);

    /**
     * Sets the heading.
     *
     * @param heading the new heading
     */
    public void setHeading (double heading);

    /**
     * Sets the pen status.
     *
     * @param status the new pen status
     */
    public void setPenStatus (boolean status);

    /**
     * Sets the visibility.
     *
     * @param visibility the new visibility
     */
    public void setVisibility (boolean visibility);

    /**
     * Gets the x.
     *
     * @return the x
     */
    public double getX ();

    /**
     * Gets the y.
     *
     * @return the y
     */
    public double getY ();

    /**
     * Gets the heading.
     *
     * @return the heading
     */
    public double getHeading ();

    /**
     * Gets the pen status.
     *
     * @return the pen status
     */
    public boolean getPenStatus ();

    /**
     * Gets the visibility.
     *
     * @return the visibility
     */
    public boolean getVisibility ();

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId ();

    /**
     * Checks for stamp.
     *
     * @return true, if successful
     */
    public boolean hasStamp ();

    /**
     * Sets the stamp.
     *
     * @param stamp the new stamp
     */
    public void setStamp (boolean stamp);

    /**
     * Gets the pen color index.
     *
     * @return the pen color index
     */
    public int getPenColorIndex ();

    /**
     * Sets the pen color index.
     *
     * @param myPenColorIndex the new pen color index
     */
    public void setPenColorIndex (int myPenColorIndex);

    /**
     * Gets the pen size.
     *
     * @return the pen size
     */
    public double getPenSize ();

    /**
     * Sets the pen size.
     *
     * @param myPenSize the new pen size
     */
    public void setPenSize (double myPenSize);

    /**
     * Sets the pen style.
     *
     * @param penStyleIndex the new pen style
     */
    public void setPenStyle (int penStyleIndex);
    
    /**
     * Gets the shape index.
     *
     * @return the shape index
     */
    public int getShapeIndex ();

    /**
     * Sets the shape index.
     *
     * @param myShapeIndex the new shape index
     */
    public void setShapeIndex (int myShapeIndex);

    /**
     * Returns "TurtleState"
     * 
     * @return State Type 
     */
    @Override
    public String getStateType ();

    /**
     * Set the Editor Code to code
     */
    @Override
    public void setEditorCode (String code);

    /**
     * @return String Editor Code
     */
    @Override
    public String getEditorCode ();

	/**
	 * Gets the pen style index.
	 *
	 * @return the pen style index
	 */
	public int getPenStyle();
}
