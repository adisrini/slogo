package slogo.model;

import generic.Pair;

/**
 * A factory for creating Function objects.
 */
public class FunctionFactory {
	
	private static final String METHOD_LOCATION = "slogo.model.";
	private final static String METHOD_MAKER_CLASS = "STOREDMETHOD";
    private static final String VARIABLE_SYNTAX_ERROR = "VariableSyntaxError";
    private static final String NO_SUCH_FUNCTION_ERROR = "NoSuchFunctionError";
    private static final String METHOD_PREFIX = "SLCommand_";
	
	/**
	 * Creates and returns the function specified by
	 * functionType using reflection.
	 *
	 * @param functionType the function type
	 * @param m the m
	 * @return the function
	 */
	public IFunction getFunction(String functionType, IMemory m){
		IFunction myInstance = null;
		String functionTypeUpper = functionType.toUpperCase();
		
		try {
			if(m.getStorageMemory().readMethod(functionType)!=null){functionTypeUpper = METHOD_MAKER_CLASS;}
			myInstance = (IFunction) Class.forName(METHOD_LOCATION+METHOD_PREFIX+functionTypeUpper).newInstance();
			return myInstance;
		} 
		catch (InstantiationException e) {
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			e.printStackTrace(); 
		} 
		catch (ClassNotFoundException e) {
			if(m.getStorageMemory().readVariable(functionType)!=null){functionError(VARIABLE_SYNTAX_ERROR,"");}
			functionError(NO_SUCH_FUNCTION_ERROR, functionType);
		}
		
		return myInstance;
	}
	
	/**
	 * Function error.
	 *
	 * @param errorType the error type
	 * @param specificMessage the specific message
	 */
	private void functionError(String errorType, String specificMessage){
		throw new SlogoException(new Pair<String, String>(errorType,specificMessage));
	}
}
