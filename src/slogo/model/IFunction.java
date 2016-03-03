package slogo.model;
public interface IFunction {
	/**
     * Creates the functions by instantiating
     * all it's arguments by calling the parser
     * given
     */
	void createFunction(IParser p,IMemory m);
	/**
     * Executes the Function and adds
     * a State to memory if needed
     */
	double executeFunction(IMemory m);
	
	String toString();
}
