package slogo.model;

import java.util.List;

interface IMemory {
	/**
     * writes an Execution to memory
     * sets it as the current execution
     */
	void writeExecution(IExecution exec);
	/**
     * Reads off the current Execution
     * @return current Execution
     */
	IExecution readCurrExecution();
	/**
     * Reads a future Execution
     * @return next Execution
     */
	IState redoExecution();
	/**
     * Checks to see if there is a next
     * Execution
     * @return boolean
     */	
	boolean hasNextExecution();
	/**
     * Goes back one Execution
     * @return past Execution
     */	
	IState undoExecution();
	/**
     * Checks to see if has previous
     * Execution
     * @return boolean
     */	
    IExecution readPrevExecution ();
    IExecution readNextExecution ();

	boolean hasPrevExecution();
	/**
     * Writes a variable to the map in Memory
     */
	void writeVariable(String varName, double varValue);
	/**
     * Reads a variable from Memory
     */
	Double readVariable(String var);
	/**
     * Writes a method to the map in Memory
     */
	void writeMethod(String methodName, String methodContent);
	/**
     * Returns the string of method content to be
     * executed by method
     * @return String of methodContent
     */
	String readMethod(String methodName);
	
	public IState readBuilderState();

	public void setBuilderState(IState builderState);
}
