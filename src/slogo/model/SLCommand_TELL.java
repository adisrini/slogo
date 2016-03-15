package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLCommand_TELL extends GeneralFunction{
	private static final int LIMIT = 0;

	@Override
	public void createFunction(IParser p, IMemory m,  Map<String,Double> scope) {
		parseBracketedExpressions(p,m,scope);
	}
	
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		List<Integer> turtleIDs = new ArrayList<Integer>();
		for(int i = 0 ; i<numberOfExpressions(); i++){
			turtleIDs.add((int) getExpression(i,m,scope));
		}
		m.getActiveMemory().updateActiveTurtles(turtleIDs);
		return turtleIDs.get(turtleIDs.size()-1);
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}

}
