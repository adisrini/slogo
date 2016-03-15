package slogo.model;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import generic.Pair;

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
	
	public String readNext(String p){
		boolean correctPattern = s.hasNext(p);
		lastString = s.next();

		if(!correctPattern) {
			if(lastString.contains("#")){parserError(COMMENT_MISPLACEMENT_ERROR, "");}
			parserError(INCORRECT_FORMAT_ERROR, p.toString() + "/" + lastString);
		}
		
		return lastString;
	}
	
	public IFunction parseFunction(Map<String,Double> scope) {
		unlimitedExpressions = s.hasNext(groupStart) ? true : false;
		if(unlimitedExpressions){parseGroupStart();}
		IFunction func = f.getFunction(readNext(command), m);
		func.createFunction(this, m, scope);
		unlimitedExpressions = false;
		return func;
	}
	
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
	
	public String parseListBegin(){
		return readNext(listStart);
	}
	
	public String parseListEnd(){
		return readNext(listEnd);
	}
	
	public String parseGroupStart(){
		return readNext(groupStart);
	}
	
	public String parseGroupEnd(){
		return readNext(groupEnd);
	}
	
	public boolean reachedGroupEnd(){
		if(s.hasNext(groupEnd)){readNext(groupEnd); return true;}
		return false;
	}
	
	public boolean reachedListEnd(){
		if(s.hasNext(listEnd)){readNext(listEnd); return true;}
		return false;
	}
	
	public String parseVariableName() {
		String varName = readNext(variable).substring(1);
		if(m.getStorageMemory().readVariable(lastString)!=null){parserError(VARIABLE_SYNTAX_ERROR,"");}
		return varName;
	}

	public String parseCommandName() {
		return readNext(command);
	}
	
	public double parseConstant() {
		return Double.parseDouble(readNext(constant));
	}

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
			//if(!scope.containsKey(variable)){parserError(UNKNOWN_VARIABLE_ERROR,variable);}
			IFunction func = f.getFunction(VARIABLE_CLASS, m);
			func.createFunction(this, m, scope);
			return func;
		}
		
		String incorrectString = s.next();
		if(incorrectString.contains("#")) { parserError(COMMENT_MISPLACEMENT_ERROR,""); }
		parserError(UNRECOGNIZABLE_SYNTAX_ERROR,incorrectString);
		
		return null;
	}
	
	public boolean hasNext() {
		return s.hasNext();
	}
	
	public String getPreviousText(){
		return lastString;
	}
	
	public boolean requestedUnlimitedExpressions(){
		return unlimitedExpressions;
	}
	
	public void parserError(String errorType, String specificMessage){
		throw new SlogoException(new Pair<String, String>(errorType,specificMessage));
	}
}
