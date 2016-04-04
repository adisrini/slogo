package slogo.model;

/**
 * The Class DisplayState.
 * 
 * Controls GUI elements that are global to any given
 * tab.
 */
public class DisplayState implements IDisplayState{
	
	/** Display State Parameters. */
	private boolean ClearStamps;
	private String myConsoleMessage;
	private int myBackgroundIndex;
	private static final String myStateType = "DisplayState";
	private String myEditorCode;
	
	
	/**
	 * Instantiates a new display state.
	 */
	public DisplayState(){
		ClearStamps = false;
		myConsoleMessage = "";
		myBackgroundIndex = 1;
		myEditorCode = "";
	}
	
	/**
	 * Instantiates a new display state.
	 *
	 * @param other the other
	 */
	public DisplayState(DisplayState other){
		myBackgroundIndex = other.getBackgroundIndex();
	}
	
	/**
	 * Returns whether or not the 
	 * Display should clear stamps
	 * 
	 * @return boolean
	 */
	public boolean getClearStamps() {
		return ClearStamps;
	}
	
	/**
	 * sets ClearStamps
	 */
	public void setClearStamps(boolean clearStamps) {
		ClearStamps = clearStamps;
	}
	
	/**
	 * Returns the console message
	 */
	public String getConsoleMessage() {
		return myConsoleMessage;
	}
	
	/**
	 * Sets the console message
	 */
	public void setConsoleMessage(String consoleMessage) {
		this.myConsoleMessage = consoleMessage;
	}
	
	/**
	 * Gets the background index
	 */
	public int getBackgroundIndex() {
		return myBackgroundIndex;
	}
	
	/**
	 * Sets the background index
	 */
	public void setBackgroundIndex(int myBackgroundIndex) {
		this.myBackgroundIndex = myBackgroundIndex;
	}
	
	/**
	 * @return State Type "DisplayState"
	 */
	@Override
	public String getStateType() {
		return myStateType;
	}
	
	/**
	 * Sets the editor code
	 */
	@Override
	public void setEditorCode(String code) {
		myEditorCode = code;
	}
	
	/**
	 * @return the editor code
	 */
	@Override
	public String getEditorCode() {
		return myEditorCode;
	}
}
