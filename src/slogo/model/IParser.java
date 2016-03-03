package slogo.model;

import java.util.regex.Pattern;

interface IParser {
	/**
     * Uses reflection to create a new function
     * as read in by the Parser
     * @return Function
     */
	IFunction parseFunction();
	/**
     * Parses a variable name when needed by an argument
     * @return String
     */
	String parseVariableName();
	/**
     * Parses a constant value when needed by an argument
     * @return String
     */
	double parseConstant();
	/**
     * Parses an expression when needed by an argument
     * this is either a function value or a constant
     * @return String
     */
	Double parseExpression();
	/**
     * Checks to see if the parser
     * has anything left to parse
     * @return boolean
     */
	boolean hasNext();
	public String parseListBegin();
	public String parseListEnd();
	public String parseCommandName();
	public String getPreviousText();
	public String extractListAsString();
	boolean reachedListEnd();
	String readNext(Pattern p);
}
