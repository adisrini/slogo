package slogo.model;

public interface IState{
	/**
     * All methods here set various values
     * of the State.
     */
	void setX(double X);
	void setY(double Y);
	void setHeading(double heading);
	void setPenStatus(boolean status);
	void setVisibility(boolean visibility);
	
	/**
     * All methods here return various values
     * of the State.
     * @return according Value
     */
	double getX();
	double getY();
	double getHeading();
	boolean getPenStatus();
	boolean getVisibility();
	String toString();
}
