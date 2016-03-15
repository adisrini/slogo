package slogo.model;

import java.util.Map;

public class SLCommand_DOTIMES extends Looper{
	private double myStart = 1;
	private String myVarName;
	private IFunction myEnd;
	private double myIncrement = 1;
	
	@Override
	public void createFunction(IParser p, IMemory m,Map<String,Double> scope) {
		super.createFunction(p, m, scope);
		p.parseListBegin();
		myVarName = p.parseVariableName();
		myEnd = p.parseExpression(myScope);
		p.parseListEnd();
		super.parseFunctionList(p, m, myScope);		
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
