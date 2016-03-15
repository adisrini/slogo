package slogo.model;

import java.util.Map;

public class SLCommand_MAKE extends GeneralFunction{
	public static final int LIMIT = 2;
	private String varName;
	private IFunction varExpr;
	
	@Override
	public void createFunction(IParser p, IMemory m, Map<String,Double> scope) {
		varName = p.parseVariableName();
		varExpr = p.parseExpression(scope);
		executeFunction(m,scope);
	}

	@Override
	public double executeFunction(IMemory m, Map<String,Double> scope) {
		double varVal = varExpr.executeFunction(m, scope);
		scope.put(varName,varVal);
		return varVal;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
