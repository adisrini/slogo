package slogo.model;

public class State implements IState{
	private double myX;
	private double myY;
	private double myHeading;
	private boolean myPenStatus;
	private boolean myVisibility;
	
	//Initializes a default State
	public State(){
		myX = 0;
		myY = 0;
		myHeading = 0;
		myPenStatus = true;
		myVisibility = true;
	}
	//makes a copy of an oldState in it's constructor
	public State(IState oldState){
		setX(oldState.getX());
		setY(oldState.getY());
		setHeading(oldState.getHeading());
		setPenStatus(oldState.getPenStatus());
		setVisibility(oldState.getVisibility());
	}
	@Override
	public void setX(double X) {
		myX = X;
	}

	@Override
	public void setY(double Y) {
		myY = Y;
	}

	@Override
	public void setHeading(double heading) {
		myHeading = heading;
	}

	@Override
	public void setPenStatus(boolean status) {
		myPenStatus = status;
	}

	@Override
	public void setVisibility(boolean visibility) {
		myVisibility = visibility;
	}

	@Override
	public double getX() {
		return myX;
	}

	@Override
	public double getY() {
		return myY;
	}

	@Override
	public double getHeading() {
		return myHeading;
	}

	@Override
	public boolean getPenStatus() {
		return myPenStatus;
	}

	@Override
	public boolean getVisibility() {
		return myVisibility;
	}
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	public String toString(){
		return ("X: "+myX+" Y: "+myY + " Heading: "+myHeading);
	}
	@Override
	public boolean equals (Object obj){
		if(obj==null){return false;}
		if(obj.getClass()!=getClass()){return false;}
		IState s = (IState) obj;
		if(s.getPenStatus()!=getPenStatus()){return false;}
		if(s.getVisibility()!=getVisibility()){return false;}
		if(s.getHeading()!=getHeading()){return false;}
		if(s.getX()!=getX()){return false;}
		if(s.getY()!=getY()){return false;}
		return true;
	}
}
