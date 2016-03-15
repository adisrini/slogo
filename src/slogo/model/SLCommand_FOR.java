package slogo.model;
import java.util.Map;

public class SLCommand_FOR extends Looper{
	private IFunction myStart;
	private String myVarName;
	private IFunction myEnd;
	private IFunction myIncrement;
	
	@Override
	public void createFunction(IParser p, IMemory m, Map<String,Double> scope) {
		super.createFunction(p, m, scope);
		
		p.parseListBegin();
		myVarName = p.parseVariableName();
		myStart = p.parseExpression(myScope);
		myEnd = p.parseExpression(myScope);
		myIncrement = p.parseExpression(myScope);
		p.parseListEnd();
		
		parseFunctionList(p, m, myScope);		
	}

	@Override
	protected double getStart(IMemory m) {
		return myStart.executeFunction(m, myScope);
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
		return myIncrement.executeFunction(m, myScope);
	}
}
