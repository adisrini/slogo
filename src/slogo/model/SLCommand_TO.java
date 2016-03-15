package slogo.model;

import java.util.Map;

public class SLCommand_TO implements IFunction{
	private String myMethodName;
	private String myMethodText;

	@Override
	public void createFunction(IParser p, IMemory m,  Map<String,Double> scope) {
		myMethodName = p.parseCommandName();
		myMethodText = p.extractListAsString()+" "+p.extractListAsString();
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		m.getStorageMemory().writeMethod(myMethodName, myMethodText);
		return 1;
	}
}
