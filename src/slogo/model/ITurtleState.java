package slogo.model;

public interface ITurtleState extends IState{
    /**
     * All methods here set various values
     * of the State.
     */
    public void setX (double X);

    public void setY (double Y);

    public void setHeading (double heading);

    public void setPenStatus (boolean status);

    public void setVisibility (boolean visibility);

    public int getId ();

    /**
     * All methods here return various values
     * of the State.
     * 
     * @return according Value
     */
    double getX ();

    double getY ();

    double getHeading ();

    boolean getPenStatus ();

    boolean getVisibility ();

    public String toString ();

    public boolean hasStamp ();

    public void setStamp (boolean stamp);

    public int getPenColorIndex ();

    public void setPenColorIndex (int myPenColorIndex);

    public double getPenSize ();

    public void setPenSize (double myPenSize);

    public int getShapeIndex ();

    public void setShapeIndex (int myShapeIndex);

	public int getPenStyle();
	
	public void setPenStyle(int penStyle);
}
