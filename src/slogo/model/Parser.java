package slogo.model;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import generic.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class Parser.
 */
public class Parser implements IParser{
	
	private Scanner s;
	private IMemory m;
	private FunctionFactory f;
	private String lastString;
	private boolean unlimitedExpressions;
	private ResourceBundle mySyntax;
	private String constant;
	private String variable;
	private String command;
	private String listStart;
	private String listEnd;
	private String groupStart;
	private String groupEnd;
	
	private static final String SYNTAX_RESOURCE_LOCATION = "resources/languages/Syntax";
	private static final String CONSTANT_CLASS = "CONSTANT";
	private static final String VARIABLE_CLASS = "VARIABLE";
    private static final String COMMENT_MISPLACEMENT_ERROR = "CommentMisplacementError";
    private static final String INCORRECT_FORMAT_ERROR = "IncorrectFormatError";    
    private static final String VARIABLE_SYNTAX_ERROR = "VariableSyntaxError";    
    private static final String UNRECOGNIZABLE_SYNTAX_ERROR = "UnrecognizableSyntaxError";

	/**
	 * Instantiates a new parser.
	 *
	 * @param input the input
	 * @param memory the memory
	 */
	public Parser(String input,IMemory memory){
		s = new Scanner(input);
		m = memory;
		f = new FunctionFactory();
		lastString = "";
        mySyntax = ResourceBundle.getBundle(SYNTAX_RESOURCE_LOCATION);
        constant = mySyntax.getString("Constant");
        variable = mySyntax.getString("Variable");
        command = mySyntax.getString("Command");
        listStart = mySyntax.getString("ListStart");
        listEnd = mySyntax.getString("ListEnd");
        groupStart = mySyntax.getString("GroupStart");
        groupEnd = mySyntax.getString("GroupEnd");
	}
	
	/**
	 * Reads the next token given by Pattern p
	 * 
	 * @returns the token's string
	 */
	public String readNext(String p){
		boolean correctPattern = s.hasNext(p);
		lastString = s.next();

		if(!correctPattern) {
			if(lastString.contains("#")){parserError(COMMENT_MISPLACEMENT_ERROR, "");}
			parserError(INCORRECT_FORMAT_ERROR, p.toString() + "/" + lastString);
		}
		
		return lastString;
	}
	
	/**
	 * Parses a Function and instantiates it by 
	 * calling the FunctionFactoy's getFunction
	 * command
	 * 
	 * @return IFunction
	 */
	public IFunction parseFunction(Map<String,Double> scope) {
		unlimitedExpressions = s.hasNext(groupStart) ? true : false;
		if(unlimitedExpressions){parseGroupStart();}
		IFunction func = f.getFunction(readNext(command), m);
		func.createFunction(this, m, scope);
		unlimitedExpressions = false;
		return func;
	}
	
	/**
	 * Extracts the string within a list
	 * 
	 * @return String
	 */
	public String extractListAsString(){
		parseListBegin();
		String listString = " [";
		int leftBracketCount = 0;
		int rightBracketCount = 0;

		if(s.hasNext(listEnd)){rightBracketCount = 100;}
		
		while(leftBracketCount>=rightBracketCount){
			listString += " " + s.next();
			if(s.hasNext(listStart)){leftBracketCount++;}
			if(s.hasNext(listEnd)){rightBracketCount++;}
		}
		
		listString += " " + s.next();
		return listString;
	}
	
	/**
	 * Parses '['
	 */
	public String parseListBegin(){
		return readNext(listStart);
	}
	
	/**
	 * Parses ']'
	 */
	public String parseListEnd(){
		return readNext(listEnd);
	}
	
	/**
	 * Parses '('
	 */
	public String parseGroupStart(){
		return readNext(groupStart);
	}
	
	/**
	 * Parses ')'
	 */
	public String parseGroupEnd(){
		return readNext(groupEnd);
	}
	
	/**
	 * If the next token is a group end,
	 * parses it and returns true
	 * 
	 * @return has next group end
	 */
	public boolean reachedGroupEnd(){
		if(s.hasNext(groupEnd)){readNext(groupEnd); return true;}
		return false;
	}
	
	/**
	 * If the next token is a list end,
	 * parses it and returns true
	 * 
	 * @return has next list end
	 */
	public boolean reachedListEnd(){
		if(s.hasNext(listEnd)){readNext(listEnd); return true;}
		return false;
	}
	
	/**
	 * Parse variable name 
	 * 
	 * @return variable name
	 */
	public String parseVariableName() {
		String varName = readNext(variable).substring(1);
		if(m.getStorageMemory().readVariable(lastString)!=null){
			parserError(VARIABLE_SYNTAX_ERROR,"");
		}
		return varName;
	}

	/**
	 * Parse command name
	 * 
	 * @return command name
	 */
	public String parseCommandName() {
		return readNext(command);
	}

	/**
	 * Parse constant
	 * 
	 * @return constant value
	 */
	public double parseConstant() {
		return Double.parseDouble(readNext(constant));
	}
	
	/**
	 * Parse an Expression
	 * Can be defined as a constant, a command, or a variable
	 * Returns one of these types as an IFunction
	 * 
	 * @return IFunction expr
	 */
	public IFunction parseExpression(Map<String,Double> scope){
		if(s.hasNext(constant)){
			IFunction func = f.getFunction(CONSTANT_CLASS, m);
			func.createFunction(this, m, scope);
			return func;
		}
		else if(s.hasNext(command)||s.hasNext(groupStart)){
			return parseFunction(scope);
		}
		else if(s.hasNext(variable)){
			IFunction func = f.getFunction(VARIABLE_CLASS, m);
			func.createFunction(this, m, scope);
			return func;
		}
		
		String incorrectString = s.next();
		if(incorrectString.contains("#")) { parserError(COMMENT_MISPLACEMENT_ERROR,""); }
		parserError(UNRECOGNIZABLE_SYNTAX_ERROR,incorrectString);
		
		return null;
	}
	
	/**
	 * Returns true if the parser
	 * has a next token
	 * 
	 * @return boolean
	 */
	public boolean hasNext() {
		return s.hasNext();
	}
	
	/**
	 * Returns the last parsed text
	 * 
	 * @return String
	 */
	public String getPreviousText(){
		return lastString;
	}
	
	/**
	 * Returns true if the user has
	 * requested unlimited expressions
	 * using the group signal
	 * 
	 * @return boolean
	 */
	public boolean requestedUnlimitedExpressions(){
		return unlimitedExpressions;
	}
	
	/**
	 * Parser error.
	 *
	 * @param errorType the error type
	 * @param specificMessage the specific message
	 */
	public void parserError(String errorType, String specificMessage){
		throw new SlogoException(new Pair<String, String>(errorType,specificMessage));
	}
	
}
