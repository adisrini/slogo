package slogo.model;

import java.util.Map;

public class SLCommand_IF extends InnerScopedFunction{
	private IFunction myBooleanFunc;
	private static final int LIMIT = 1;

	@Override
	public void createFunction(IParser p, IMemory m,  Map<String,Double> scope) {
		super.createFunction(p, m, scope);
		myBooleanFunc = p.parseExpression(myScope);
		parseFunctionList(p, m, scope);
	}
	
	@Override
	public double executeFunction(IMemory m,Map<String,Double> scope) {
		double finalnum = 0;
		double myBoolean = myBooleanFunc.executeFunction(m, myScope);
		if(myBoolean!=0){
			finalnum = executeFunctionList(m,scope);
		}
		return finalnum;
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
	
}
