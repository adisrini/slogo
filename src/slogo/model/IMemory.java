package slogo.model;

import java.util.List;

interface IMemory {
	/**
     * writes an Execution to memory
     * sets it as the current execution
     */
	//void writeExecution(IExecution exec);
	/**
     * Reads off the current Execution
     * @return current Execution
     */
	//IExecution readCurrExecution();
	/**
     * Reads a future Execution
     * @return next Execution
     */
	List<List<IState>> redoExecution();
	/**
     * Checks to see if there is a next
     * Execution
     * @return boolean
     */	
	//boolean hasNextExecution();
	/**
     * Goes back one Execution
     * @return past Execution
     */	
	List<IState> undoExecution();
	
	List<IState> redoState();

	
	List<IState> undoState();

	/**
     * Checks to see if has previous
     * Execution
     * @return boolean
     */	
    //IExecution readPrevExecution ();
    //IExecution readNextExecution ();
    
    //public void updateStartersWithStateList(List<IState> list);
    
    //public void resetStarterStates();

	//boolean hasPrevExecution();
	/**
     * Writes a variable to the map in Memory
     */
	//void writeVariable(String varName, double varValue);
	/**
     * Reads a variable from Memory
     */
	//Double readVariable(String var);
	/**
     * Writes a method to the map in Memory
     */
	//void writeMethod(String methodName, String methodContent);
	/**
     * Returns the string of method content to be
     * executed by method
     * @return String of methodContent
     */
	//String readMethod(String methodName);
	
	//public TurtleState readStarterState(Integer id);

	//public void updateStarterState(Integer id, TurtleState state);
	
	//Map<String, Double> getVariableMap ();
	//public void activateTurtle(Integer id);
	//public void deactivateTurtle(Integer id);
	//public List<Integer> getActiveTurtleIDs();
	//public void updateActiveTurtles(List<Integer> ids);
	//public DisplayState getStarterDisplay();
    //public int readTurtleNumber();
	//public void updateStarterDisplay(DisplayState myStarterDisplay);
	//public int getCurrentlyActiveTurtleID();
	//public void updateCurrentlyActiveTurtleID(int currentlyActiveID);
	void updateCurrentlyActiveTab(int currentlyActiveID);
	//public TurtleState getCurrentlyActiveTurtle();
	void resetCurrentTab();
	//public void updateVariables(Map<String,Double> m);
	StorageMemory getStorageMemory();
	ActiveMemory getActiveMemory();
}
