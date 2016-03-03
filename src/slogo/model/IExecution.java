package slogo.model;

import java.util.Iterator;
import java.util.List;

interface IExecution {
	/**
	 * Returns whatever State the current pointer
	 * is pointing to
     * @return State
     */
	IState getCurrState();
	/**
     * Returns a State in the Future
     * @return next State
     */
	IState getNextState();
	/**
     * Goes back one State
     * @return past State
     */
	IState getPrevState();
	/**
     * Adds a specified State to the linked list
     * of States
     */
	void addState(IState s);
	/**
     * goes through and parses the String input,
     * creating all the Functions needed
     */
	void createFunctions(String input);
	/**
     * Executes all functions created in the list above
     * this generates a list of States 
     */
	void executeFunctions();
	/**
     * returns true if the state list 
     * has another state
     */
	boolean hasNextState();
	/**
     * returns true if the state list
     * has a previous state
     */
	
	boolean hasCurrState();
	boolean hasPrevState();
	public IState undoState();
	public IState redoState();
	/**
     * Returns list of States
     * @return List of States
     */
	List<IState> getListOfStates();
}
