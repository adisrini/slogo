package slogo.model;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

import generic.Pair;

public class Parser implements IParser{
	private Scanner s;
	private IMemory m;
	private final static String METHOD_MAKER_CLASS = "STOREDMETHOD";
	//private final static String VARIABLE_MAKER_CLASS = "MAKE";
	private String lastString = "";
	private Pattern constantPattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
	private Pattern variablePattern = Pattern.compile(":[a-zA-Z_]+");
	private Pattern commandPattern = Pattern.compile("[a-zA-Z_]+(\\?)?");
	private Pattern comP = Pattern.compile("[#;].*");
	//private Pattern comP = Pattern.compile("#.*");
	private Pattern listBeginPattern =  Pattern.compile("\\[");
	private Pattern listEndPattern =  Pattern.compile("\\]");
    private ResourceBundle lang;
    private static final String LANGUAGE_RESOURCE_LOCATION = "resources/languages/";
    private static final String DEFAULT_LANGUAGE = "English";
    private static final String COMMENT_MISPLACEMENT_ERROR = "CommentMisplacementError";
    private static final String INCORRECT_FORMAT_ERROR = "IncorrectFormatError";
    private static final String NO_SUCH_FUNCTION_ERROR = "NoSuchFunctionError";
    private static final String VARIABLE_SYNTAX_ERROR = "VariableSyntaxError";
    private static final String UNRECOGNIZABLE_SYNTAX_ERROR = "UnrecognizableSyntaxError";

    //TODO: Add a bunch of exceptions to this list
	
	public Parser(String input,IMemory memory){
		s = new Scanner(input);
		m = memory;
		lang =
                ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + DEFAULT_LANGUAGE);
	}
	@Override
	public String readNext(Pattern p){
		
		//TODO: THROW ERROR HERE OF THE SPECIFIC TYPE. MAKE THIS TYPE IN RESOURCES
		boolean correctPattern = s.hasNext(p);
		lastString = s.next();
		//if(!correctPattern){;}
		if(!correctPattern&&lastString.contains("#")) { 
			throw new SlogoException(new Pair<String, String>(COMMENT_MISPLACEMENT_ERROR, ""));
		}
		if(!correctPattern) { 
			throw new SlogoException(new Pair<String, String>(INCORRECT_FORMAT_ERROR, p.toString() + "/" + lastString));
		}
		return lastString;
	}
	public IFunction parseFunction() {
		IFunction myInstance;
		String functionString = readNext(commandPattern);
		String functionStringUpper = functionString.toUpperCase();
		try {
			if(m.readMethod(functionString)!=null){
				functionStringUpper = METHOD_MAKER_CLASS;
			}
			myInstance = (IFunction) Class.forName("slogo.model."+functionStringUpper).newInstance();
			myInstance.createFunction(this,m);
			return myInstance;
		} 
		catch (InstantiationException e) {e.printStackTrace();} 
		catch (IllegalAccessException e) {e.printStackTrace();} 
		catch (ClassNotFoundException e) {
			if(m.readVariable(functionString)!=null){
				throw new SlogoException(new Pair<String, String>(VARIABLE_SYNTAX_ERROR,""));
			}
			throw new SlogoException(new Pair<String, String>(NO_SUCH_FUNCTION_ERROR, functionString));
		}
		return null;
	}
	public String extractListAsString(){
		parseListBegin();
		String listString = " [";
		int leftBracketCount = 0;
		int rightBracketCount = 0;

		while(leftBracketCount>=rightBracketCount){
			listString += " " + s.next(); //reads in full command text
			if(s.hasNext(listBeginPattern)){leftBracketCount++;}
			if(s.hasNext(listEndPattern)){rightBracketCount++;}
		}
		listString += " " + s.next();
		return listString;
	}
	public String parseListBegin(){
		return readNext(listBeginPattern);
	}
	public String parseListEnd(){
		return readNext(listEndPattern);
	}
	public boolean reachedListEnd(){
		if(s.hasNext(listEndPattern)){readNext(listEndPattern); return true;}
		return false;
	}
	@Override
	public String parseVariableName() {
		//String commandCalled = lastString.toUpperCase();
		String varName = readNext(variablePattern).substring(1);
		//if(m.readVariable(varName)==null&&!commandCalled.equals(VARIABLE_MAKER_CLASS))
		//{throw new SlogoException("Variable name "+varName+ " does not exist");}
		if(m.readVariable(lastString)!=null){
			System.out.println("here!!!!!!!");
			throw new SlogoException(new Pair<String, String>(VARIABLE_SYNTAX_ERROR,""));
		}
		return varName;
	}
	//WHAT IS PARSE COMMAND NAME FOR AGAIN?
	public String parseCommandName() {
		return readNext(commandPattern);
	}
	@Override
	public double parseConstant() {
		return Double.parseDouble(readNext(constantPattern));
	}
	//EXPRESSION CAN BE EITHER A COMMAND, CONSTANT, OR VARIABLE THAT WAS ALREADY CREATED
	public Double parseExpression(){
		if(s.hasNext(constantPattern)){return parseConstant();}
		if(s.hasNext(commandPattern)){return parseFunction().executeFunction(m);}
		if(s.hasNext(variablePattern)){return m.readVariable(parseVariableName());}
		
		else{
			String incorrectString = s.next();
			if(incorrectString.contains("#")) { 
				throw new SlogoException(new Pair<String, String>(COMMENT_MISPLACEMENT_ERROR,""));
			}
			throw new SlogoException(new Pair<String, String>(UNRECOGNIZABLE_SYNTAX_ERROR,incorrectString));
		}
	}
	@Override
	public boolean hasNext() {
		return s.hasNext();
	}
	public String getPreviousText(){
		return lastString;
	}
}
