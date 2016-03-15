package slogo.model;

import java.util.Map;

public class SLCommand_REPEAT extends Looper {
	private double myIncrement = 1;
	private String myVarName = "repcount";
	private double myStart = 1;
	private IFunction myEnd;

	@Override
	public void createFunction(IParser p, IMemory m, Map<String,Double> scope) {
		super.createFunction(p, m, scope);
		myEnd = p.parseExpression(scope);
		parseFunctionList(p, m, scope);
	}

	@Override
	protected double getStart(IMemory m) {
		return myStart;
	}

	@Override
	protected double getEnd(IMemory m) {
		return myEnd.executeFunction(m, myScope);
	}

	@Override
	protected String getVarName(IMemory m) {
		return myVarName;
	}

	@Override
	protected double getIncrement(IMemory m) {
		return myIncrement;
	}
}
