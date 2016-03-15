package slogo.model;

import java.util.Map;

import generic.Pair;

public class SLCommand_VARIABLE implements IFunction{
	private String myName;
    private static final String UNKNOWN_VARIABLE_ERROR = "NoSuchVariableError";
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		myName = p.parseVariableName();	
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		if(!scope.containsKey(myName)){variableError(UNKNOWN_VARIABLE_ERROR,myName);}
		System.out.println(myName);
		return scope.get(myName);
	}
	
	private void variableError(String errorType, String specificMessage){
		throw new SlogoException(new Pair<String, String>(errorType,specificMessage));
	}
}
