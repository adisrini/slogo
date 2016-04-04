package slogo.model;

/**
 * The Class TurtleState.
 * 
 * Contains all information needed to create a 
 * turtle in SLogo
 */
public class TurtleState implements ITurtleState {
    
    /** The turtle's ID. */
    private Integer myID;

    /** The turtle's coordinates. */
    private double myX;
    private double myY;
    private double myHeading;

    /** Whether or not the turtle should place a Stamp. */
    private boolean Stamp;

    /** The turtle's Pen. */
    private boolean myPenStatus;    
    private int myPenColorIndex;
    private int myPenStyleIndex;    
    private double myPenSize;

    /** The shape index. */
    private int myShapeIndex;
    
    /** The visibility. */
    private boolean myVisibility;

    /** The State Type. */
    private static final String myStateType = "TurtleState";

    /** The Editor Code. */
    private String myEditorCode;

    /**
     * Initializes a default Turtle State.
     *
     * @param id the id
     */
    public TurtleState (Integer id) {
        myID = id.intValue();
        myX = 0;
        myY = 0;
        myHeading = 0;
        Stamp = false;
        myPenStatus = true;
        myPenColorIndex = 0;
        myPenStyleIndex = 0;
        myPenSize = 0;
        myShapeIndex = 0;
        myVisibility = true;
        myEditorCode = "";
        myPenSize = 3;
    }

    /**
     * Makes copy of a previous State as constructor.
     *
     * @param oldState the previous state
     */
	public TurtleState(TurtleState oldState){
		myID = oldState.getId();
		setX(oldState.getX());
		setY(oldState.getY());
		setHeading(oldState.getHeading());
		
		setStamp(false);
		
		setPenStatus(oldState.getPenStatus());
		setPenColorIndex(oldState.getPenColorIndex());
		setPenSize(oldState.getPenSize());
		setPenStyle(oldState.getPenStyle());

		setShapeIndex(oldState.getShapeIndex());
		setVisibility(oldState.getVisibility());
	}

    /**
     * Sets the x.
     *
     * @param X the new x
     */
    public void setX (double X) {
        myX = X;
    }

    /**
     * Sets the y.
     *
     * @param Y the new y
     */
    public void setY (double Y) {
        myY = Y;
    }

    /**
     * Sets the heading.
     *
     * @param heading the new heading
     */
    public void setHeading (double heading) {
        myHeading = heading;
    }

    /**
     * Sets the pen status.
     *
     * @param status the new pen status
     */
    public void setPenStatus (boolean status) {
        myPenStatus = status;
    }

    /**
     * Sets the visibility.
     *
     * @param visibility the new visibility
     */
    public void setVisibility (boolean visibility) {
        myVisibility = visibility;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public double getX () {
        return myX;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public double getY () {
        return myY;
    }

    /**
     * Gets the heading.
     *
     * @return the heading
     */
    public double getHeading () {
        return myHeading;
    }

    /**
     * Gets the pen status.
     *
     * @return the pen status
     */
    public boolean getPenStatus () {
        return myPenStatus;
    }

    /**
     * Gets the visibility.
     *
     * @return the visibility
     */
    public boolean getVisibility () {
        return myVisibility;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId () {
        return myID;
    }

    /**
     * Checks for stamp.
     *
     * @return true, if successful
     */
    public boolean hasStamp () {
        return Stamp;
    }

    /**
     * Sets the stamp.
     *
     * @param stamp the new stamp
     */
    public void setStamp (boolean stamp) {
        Stamp = stamp;
    }

    /**
     * Gets the pen color index.
     *
     * @return the pen color index
     */
    public int getPenColorIndex () {
        return myPenColorIndex;
    }

    /**
     * Sets the pen color index.
     *
     * @param myPenColorIndex the new pen color index
     */
    public void setPenColorIndex (int myPenColorIndex) {
        this.myPenColorIndex = myPenColorIndex;
    }

    /**
     * Gets the pen size.
     *
     * @return the pen size
     */
    public double getPenSize () {
        return myPenSize;
    }

    /**
     * Sets the pen size.
     *
     * @param myPenSize the new pen size
     */
    public void setPenSize (double myPenSize) {
        this.myPenSize = myPenSize;
    }

    /**
     * Sets the pen style.
     *
     * @param penStyleIndex the new pen style
     */
    public void setPenStyle (int penStyleIndex) {
        this.myPenStyleIndex = penStyleIndex;
    }
    
    /**
     * Gets the shape index.
     *
     * @return the shape index
     */
    public int getShapeIndex () {
        return myShapeIndex;
    }

    /**
     * Sets the shape index.
     *
     * @param myShapeIndex the new shape index
     */
    public void setShapeIndex (int myShapeIndex) {
        this.myShapeIndex = myShapeIndex;
    }

    /**
     * Returns "TurtleState"
     * 
     * @return State Type 
     */
    @Override
    public String getStateType () {
        return myStateType;
    }

    /**
     * Set the Editor Code to code
     */
    @Override
    public void setEditorCode (String code) {
        myEditorCode = code;
    }

    /**
     * @return String Editor Code
     */
    @Override
    public String getEditorCode () {
        return myEditorCode;
    }

	/**
	 * Gets the pen style index.
	 *
	 * @return the pen style index
	 */
	public int getPenStyle() {
		return myPenStyleIndex;
	}
}
