package slogo.model;
import java.util.Map;

public class UserMethod extends InnerScopedFunction{
	private static final int LIMIT = 2;
	private String myMethodName;
	private String myMethodContent;
	private Parser myMethodParser;

	public void createFunction(IParser p, IMemory m,  Map<String,Double> scope) {
		super.createFunction(p, m, scope);

		myMethodName = p.getPreviousText();
		myMethodContent = m.getStorageMemory().readMethod(myMethodName);
		myMethodParser = new Parser(myMethodContent,m);
		
		myMethodParser.parseListBegin();
		while(!myMethodParser.reachedListEnd()){
			myScope.put(myMethodParser.parseVariableName(), p.parseExpression(myScope).executeFunction(m, scope));
		}
	}

	@Override
	public double executeFunction(IMemory m,Map<String, Double> scope) {
		double returnval = 0;
		
		super.parseFunctionList(myMethodParser, m, myScope);
		returnval = super.executeFunctionList(m, myScope);
		
		return returnval;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
