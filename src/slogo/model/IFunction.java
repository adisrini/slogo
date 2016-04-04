package slogo.model;

import java.util.Map;

/**
 * The Interface IFunction. Used to implement all
 * SLogo Commands
 */
public interface IFunction {
	
	/**
	 * Instantiates all arguments necessary
	 * for the function.
	 *
	 * @param p the Parser
	 * @param m the Memory
	 * @param scope the scope
	 */
	void createFunction(IParser p,IMemory m, Map<String,Double> scope);
	
	/**
	 * Executes the Function and adds
	 * a State to memory if needed.
	 *
	 * @param m the Memory
	 * @param scope the scope
	 * @return function's value
	 */
	double executeFunction(IMemory m,  Map<String,Double> scope);
	
}
