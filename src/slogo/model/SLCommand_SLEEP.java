package slogo.model;

import java.util.Map;

public class SLCommand_SLEEP extends GeneralFunction {
	private static final int LIMIT = 0;
	
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		parseBracketedExpressions(p, m, scope);
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		for(int i = 0 ; i<numberOfExpressions(); i++){
			m.getActiveMemory().activateTurtle((int) getExpression(0,m,scope));
		}
		return m.getActiveMemory().getCurrentlyActiveTurtleID();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}

}
