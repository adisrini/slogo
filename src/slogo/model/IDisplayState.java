package slogo.model;

public interface IDisplayState extends IState{
	
	/**
	 * Returns whether or not the 
	 * Display should clear stamps
	 * 
	 * @return boolean
	 */
	public boolean getClearStamps();
	
	/**
	 * sets ClearStamps
	 */
	public void setClearStamps(boolean clearStamps);
	
	/**
	 * Returns the console message
	 */
	public String getConsoleMessage();
	
	/**
	 * Sets the console message
	 */
	public void setConsoleMessage(String consoleMessage);
	
	/**
	 * Gets the background index
	 */
	public int getBackgroundIndex();
	
	/**
	 * Sets the background index
	 */
	public void setBackgroundIndex(int myBackgroundIndex);
	
	/**
	 * @return State Type "DisplayState"
	 */
	public String getStateType();
	
	/**
	 * Sets the editor code
	 */
	public void setEditorCode(String code);
	
	/**
	 * @return the editor code
	 */
	public String getEditorCode();
}
