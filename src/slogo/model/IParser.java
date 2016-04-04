package slogo.model;

import java.util.Map;

/**
 * The Interface IParser.
 */
interface IParser {
	/**
	 * Uses reflection to create a new function
	 * as read in by the Parser.
	 *
	 * @param myScope the my scope
	 * @return Function
	 */
	IFunction parseFunction(Map<String,Double> myScope);
	
	/**
	 * Parses a variable name when needed by an argument.
	 *
	 * @return String
	 */
	String parseVariableName();
	
	/**
	 * Parses a constant value when needed by an argument.
	 *
	 * @return String
	 */
	double parseConstant();
	
	/**
	 * Parses an expression when needed by an argument
	 * this is either a function value or a constant.
	 *
	 * @param scope the scope
	 * @return String
	 */
	IFunction parseExpression(Map<String,Double> scope);
	
	/**
	 * Checks to see if the parser
	 * has anything left to parse.
	 *
	 * @return boolean
	 */
	boolean hasNext();
	
	/**
	 * Parses the list begin.
	 *
	 * @return the string
	 */
	public String parseListBegin();
	
	/**
	 * Parses the list end.
	 *
	 * @return the string
	 */
	public String parseListEnd();
	
	/**
	 * Parses the group start.
	 *
	 * @return the string
	 */
	public String parseGroupStart();
	
	/**
	 * Parses the group end.
	 *
	 * @return the string
	 */
	public String parseGroupEnd();
	
	/**
	 * Parses the command name.
	 *
	 * @return the string
	 */
	public String parseCommandName();
	
	/**
	 * Gets the previous text.
	 *
	 * @return the previous text
	 */
	public String getPreviousText();
	
	/**
	 * Extract list as string.
	 *
	 * @return the string
	 */
	public String extractListAsString();
	
	/**
	 * Reached list end.
	 *
	 * @return true, if has reached "]"
	 */
	boolean reachedListEnd();
	
	/**
	 * Read next.
	 *
	 * @param p the p
	 * @return the string
	 */
	String readNext(String p);
	
	/**
	 * Checks to see if the user has 
	 * requested unlimited expressions.
	 *
	 * @return true, if the statement is 
	 * surrounded by parenthesis
	 */
	boolean requestedUnlimitedExpressions();
	
	/**
	 * Reached group end.
	 *
	 * @return true, if ")" 
	 * has been reached
	 */
	boolean reachedGroupEnd();
	
 }
