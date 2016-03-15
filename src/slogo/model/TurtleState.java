package slogo.model;

public class TurtleState implements ITurtleState {
    // ID
    private Integer myID;

    // Vector
    private double myX;
    private double myY;
    private double myHeading;

    // Stamp
    private boolean Stamp;

    // Pen
    private boolean myPenStatus;
    private int myPenColorIndex;
    private int myPenStyleIndex;
    private double myPenSize;

    // Turtle Display
    private int myShapeIndex;
    private boolean myVisibility;

    private static final String myStateType = "TurtleState";

    private String myEditorCode;

    /**
     * Initializes a default Turtle State
     */
    public TurtleState (Integer id) {
        myID = id.intValue();
        
        myX = 0;
        myY = 0;
        myHeading = 0;
        
        setStamp(false);
        
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
     * Makes copy of a previous State as constructor
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

    public void setX (double X) {
        myX = X;
    }

    public void setY (double Y) {
        myY = Y;
    }

    public void setHeading (double heading) {
        myHeading = heading;
    }

    public void setPenStatus (boolean status) {
        myPenStatus = status;
    }

    public void setVisibility (boolean visibility) {
        myVisibility = visibility;
    }

    public double getX () {
        return myX;
    }

    public double getY () {
        return myY;
    }

    public double getHeading () {
        return myHeading;
    }

    public boolean getPenStatus () {
        return myPenStatus;
    }

    public boolean getVisibility () {
        return myVisibility;
    }

    protected Object clone () throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString () {
        return ("Turtle " + myID + "\nX: " + myX + "\nY: " + myY + "\nHeading: " + myHeading+"\nStamp: "+Stamp);
    }

    public int getId () {
        return myID;
    }

    public boolean hasStamp () {
        return Stamp;
    }

    public void setStamp (boolean stamp) {
        Stamp = stamp;
    }

    public int getPenColorIndex () {
        return myPenColorIndex;
    }

    public void setPenColorIndex (int myPenColorIndex) {
        this.myPenColorIndex = myPenColorIndex;
    }

    public double getPenSize () {
        return myPenSize;
    }

    public void setPenSize (double myPenSize) {
        this.myPenSize = myPenSize;
    }

    public void setPenStyle (int penStyleIndex) {
        this.myPenStyleIndex = penStyleIndex;
    }
    
    public int getShapeIndex () {
        return myShapeIndex;
    }

    public void setShapeIndex (int myShapeIndex) {
        this.myShapeIndex = myShapeIndex;
    }

    @Override
    public String getStateType () {
        return myStateType;
    }

    @Override
    public void setEditorCode (String code) {
        myEditorCode = code;
    }

    @Override
    public String getEditorCode () {
        return myEditorCode;
    }

	public int getPenStyle() {
		return myPenStyleIndex;
	}
}
