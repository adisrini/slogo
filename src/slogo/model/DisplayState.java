package slogo.model;

public class DisplayState implements IDisplayState{
	private boolean ClearStamps;
	private String myConsoleMessage;
	private int myBackgroundIndex;
	private static final String myStateType = "DisplayState";
	private String myEditorCode;
	
	
	public DisplayState(){
		ClearStamps = false;
		myConsoleMessage = "";
		myBackgroundIndex = 1;
		myEditorCode = "";
	}
	public DisplayState(DisplayState other){
		myBackgroundIndex = other.getBackgroundIndex();
	}
	public boolean getClearStamps() {
		return ClearStamps;
	}
	public void setClearStamps(boolean clearStamps) {
		ClearStamps = clearStamps;
	}
	public String getConsoleMessage() {
		return myConsoleMessage;
	}
	public void setConsoleMessage(String consoleMessage) {
		this.myConsoleMessage = consoleMessage;
	}
	public int getBackgroundIndex() {
		return myBackgroundIndex;
	}
	public void setBackgroundIndex(int myBackgroundIndex) {
		this.myBackgroundIndex = myBackgroundIndex;
	}
	@Override
	public String getStateType() {
		return myStateType;
	}
	public String toString(){
		return ("Console: "+myConsoleMessage);
	}
	@Override
	public void setEditorCode(String code) {
		// TODO Auto-generated method stub
		myEditorCode = code;
	}
	@Override
	public String getEditorCode() {
		// TODO Auto-generated method stub
		return myEditorCode;
	}
}
