package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class SLCommand_ASK.
 */
public class SLCommand_ASK extends GeneralFunction{
	
	private static final int LIMIT = 0;

	/**
	 * parses required arguments
	 */
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		parseBracketedExpressions(p,m,scope);
		parseFunctionList(p,m,scope);
	}

	/**
	 * tells the turtle ids specified to perform given commands
	 * resets the active turtle ids to those held previously
	 * 
	 * @return double
	 */
	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		List<Integer> prevTurtleIDs = new ArrayList<Integer>(m.getActiveMemory().getActiveTurtleIDs());
		List<Integer> currentTurtleIDs = new ArrayList<Integer>();
		
		for(int i = 0; i<numberOfExpressions(); i++){
			currentTurtleIDs.add((int) getExpression(i,m,scope));
		}
		
		m.getActiveMemory().updateActiveTurtles(currentTurtleIDs);
		double val = 0;
		val = executeFunctionList(m,scope);
		m.getActiveMemory().updateActiveTurtles(prevTurtleIDs);
		return val;
	}

	/**
	 * return args needed
	 */
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}

}
