package slogo.model;

import java.util.Map;

public interface IFunction {
	/**
     * Creates the functions by instantiating
     * all it's arguments by calling the parser
     * given
     */
	void createFunction(IParser p,IMemory m, Map<String,Double> scope);
	/**
     * Executes the Function and adds
     * a State to memory if needed
     */
	double executeFunction(IMemory m,  Map<String,Double> scope);
	
	String toString();
}
