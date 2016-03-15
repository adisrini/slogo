package slogo.model;

import java.util.Iterator;
import java.util.List;

import generic.Pair;

interface IModel {
	/**
     * initializes an Execution and runs the commands that
     * both create and execute commands.
     * Writes the Execution to memory
     */
	public void initExecution(Pair<String, String> inputs);
	/**
     * Goes back one state
     * @return past State
     */
	public List<IState> redoState();
	/**
     * Goes forward one state
     * @return future state
     */
	public List<IState> undoState();
	/**
     * Goes back one execution
     * @return past Execution
     */
	public Iterator<List<IState>> redoExecution();
	/**
     * Goes forward one execution
     * @return future Execution
     */
	public List<IState> undoExecution();
	/**
     * Returns Iterator of states
     * @return State Iterator
     */
	public Iterator<List<IState>> getStateIterator();
}
