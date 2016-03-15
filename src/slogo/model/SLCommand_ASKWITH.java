package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLCommand_ASKWITH extends GeneralFunction{
	private static final int LIMIT = 0;

	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		//make function scoped
		super.createFunction(p, m, scope);
		
		//extract our condition
		parseBracketedExpressions(p,m,scope);

		//extract our methods
		parseFunctionList(p, m, scope);
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		//initialize objects
		List<Integer> prevTurtleIDs = new ArrayList<Integer>(m.getActiveMemory().getActiveTurtleIDs());
		List<Integer> currentlyActiveTurtle = new ArrayList<Integer>(); 
		double finalnum = 0;

		//gets number of all turtles
		int turtlenum = m.getActiveMemory().readTurtleNumber(); 
		
		//loop through each turtle id
		for(int i = 1; i<=turtlenum; i++){ 
			//activate them one by one
			currentlyActiveTurtle.add(i);
			
			//make all other turtles inactive for now
			m.getActiveMemory().updateActiveTurtles(currentlyActiveTurtle);
			
			//set the currently active turtle to be turtle i
			m.getActiveMemory().updateCurrentlyActiveTurtleID(i); 
			
			//if the condition is true for this turtle, execute the functions
			if(getExpression(0,m,scope)!=0){
				finalnum = executeFunctionList(m, scope);
			}
			currentlyActiveTurtle.clear();
		}
		
		//now reset back to the original turtle list
		m.getActiveMemory().updateActiveTurtles(prevTurtleIDs);
		return finalnum;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
