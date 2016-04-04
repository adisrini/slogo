package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class TurtleStateCreator.
 */
abstract class TurtleStateCreator extends GeneralFunction{
		
	/** List of Values from executed Expressions. */
	private List<Double> myVals;
	
	/** List of IStates to add to current Execution. */
	private List<IState> myList;
	
	/**
	 * Executed a function that adds a Turtle State to the current
	 * Execution. 
	 * 
	 * Accounts for unlimited expressions, multiple turtles, setting a
	 * currently active turtle, and returning a value specified from the
	 * IFunction extending GeneralFunction
	 * 
	 * @return double SLogo method return value
	 */
	@Override
	public double executeFunction(IMemory m, Map<String,Double> scope) {
		double finalnum = 0;
		myVals = new ArrayList<Double>();
		int myLoopNum = !hasExpressions() ? 1 : numberOfExpressions();
		int increment = !hasExpressions() ? 1 : argsNeeded();
		
		for(int i = 0; i < myLoopNum; i+=increment){
			myList = new ArrayList<IState>();
			for(Integer id : m.getActiveMemory().getActiveTurtleIDs()){
				finalnum = updateTurtleAndState(m, scope, increment, i, id);
			}
			m.getStorageMemory().writeStates(myList);
		}

		return finalnum;
	}
	/**
	 * Updates the active Turtle in memory according to the current ID
	 * Re-executes expressions to attain a list of values
	 * Updates the current Turtle's state according to these values
	 * 
	 * @param m Memory
	 * @param scope scope
	 * @param increment increment
	 * @param i index
	 * @param id Turtle ID
	 * 
	 * @return Command's return Value
	 */
	private double updateTurtleAndState(IMemory m, Map<String, Double> scope, int increment, int i,Integer id) {
		double num;
		m.getActiveMemory().updateCurrentlyActiveTurtleID(id);
		
		if(hasExpressions()){
			for(int index = 0; index<increment;index++){
				myVals.add(getExpression(i+index,m,scope));
			}
		}
		
		TurtleState turtState = new TurtleState(m.getActiveMemory().readStarterState(id));
		num = setState(turtState);
		myList.add(turtState);
		m.getActiveMemory().updateStarterState(id, turtState);
		myVals.clear();
		return num;
	}
	
	/**
	 * Gives the Value to the extending method
	 * from GeneralFunction
	 *
	 * @param i the index
	 * @return the value
	 */
	protected double getVal(int i){
		return myVals.get(i);
	}
	
	/**
	 * Sets the state according to the extending
	 * method's preferences.
	 *
	 * @param myState the state
	 * @return the return value
	 */
	protected abstract double setState(TurtleState myState);
}
