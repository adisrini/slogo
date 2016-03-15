package slogo.model;

public interface IDisplayState extends IState{
	
	public boolean getClearStamps();
	
	public void setClearStamps(boolean clearStamps);
	
	public String getConsoleMessage();
	
	public void setConsoleMessage(String consoleMessage);
	
	public int getBackgroundIndex();
	
	public void setBackgroundIndex(int myBackgroundIndex);
	
}
