package slogo.model;

import java.util.Iterator;
import java.util.List;

interface IModel {
	/**
     * initializes an Execution and runs the commands that
     * both create and execute commands.
     * Writes the Execution to memory
     */
	public void initExecution(String input);
	/**
     * Goes back one state
     * @return past State
     */
	public IState redoState();
	/**
     * Goes forward one state
     * @return future state
     */
	public IState undoState();
	/**
     * Goes back one execution
     * @return past Execution
     */
	public IState redoExecution();
	/**
     * Goes forward one execution
     * @return future Execution
     */
	public IState undoExecution();
	/**
     * Returns Iterator of states
     * @return State Iterator
     */
	public Iterator<IState> getStateIterator();
}
