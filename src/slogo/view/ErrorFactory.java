package slogo.view;

public enum ErrorFactory {	
	CommentMisplacementError("Comment was placed incorrectly."),
	IncorrectFormatError("Incorrect format. Expected % but received %."),
	NoSuchFunctionError("The function % does not exist."),
	VariableSyntaxError("Forgot to place a colon (:) before the variable declaration."),
	UnrecognizableSyntaxError("Unrecognizable syntax in \'%\'");
	
	private String template;
	
	private static final String PLACEHOLDER = "%";
	
	private ErrorFactory(String template) {
		this.template = template;
	}
	
	public String speak(String errorFeed) {
		String[] split = errorFeed.split("/");
		
		String error = this.template;
		
		for(String s : split) {
			error = error.replaceFirst(PLACEHOLDER, s);
		}
		
		return error;
	}
	
	public static void main(String[] args) {
		
		ErrorFactory e = ErrorFactory.valueOf("IncorrectFormatError");
		System.out.println(e.speak("[ rt/[rt"));
	}
}
