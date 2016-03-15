package slogo.view;

/**
 * Enumeration of error messages that are used for displaying
 * errors.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public enum ErrorFactory {	
	CommentMisplacementError("Comment was placed incorrectly."),
	IncorrectFormatError("Incorrect format. Expected % but received %."),
	NoSuchFunctionError("The function % does not exist."),
	NoSuchVariableError("The variable % does not exist in scope."),
	VariableSyntaxError("Forgot to place a colon (:) before the variable declaration."),
	UnrecognizableSyntaxError("Unrecognizable syntax in \'%\'");
	
	private String template;
	
	private static final String PLACEHOLDER = "%";
	
	/**
	 * Constructs the enum with a given error message template
	 * @param template
	 */
	private ErrorFactory(String template) {
		this.template = template;
	}
	
	/**
	 * Regurgitates the error message, with the specific mistakes replacing
	 * the placeholders
	 * @param errorFeed
	 * @return
	 */
	public String speak(String errorFeed) {
		String[] split = errorFeed.split("/");
		
		String error = this.template;
		
		for(String s : split) {
			error = error.replaceFirst(PLACEHOLDER, s);
		}
		
		return error;
	}
	
}
