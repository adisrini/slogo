package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class TurtleStateCreator extends GeneralFunction{
	private List<IState> list;
	private List<Double> myVals;
	@Override
	public double executeFunction(IMemory m, Map<String,Double> scope) {
		double finalnum = 0;
		myVals = new ArrayList<Double>();
		int myLoopNum = !hasExpressions() ? 1 : numberOfExpressions();
		int increment = !hasExpressions() ? 1 : argsNeeded();
		for(int i = 0; i < myLoopNum; i+=increment){
			list = new ArrayList<IState>();
			for(Integer id : m.getActiveMemory().getActiveTurtleIDs()){
				m.getActiveMemory().updateCurrentlyActiveTurtleID(id);
				
				if(hasExpressions()){
					for(int index = 0; index<increment;index++){
						myVals.add(getExpression(i+index,m,scope));
					}
				}
				
				TurtleState turtState = new TurtleState(m.getActiveMemory().readStarterState(id));
				
				finalnum = setState(turtState);
				
				list.add(turtState);
				m.getActiveMemory().updateStarterState(id, turtState);
				
				myVals.clear();
			}
			
			m.getStorageMemory().writeStates(list);
		}

		return finalnum;
	}
	protected double getVal(int i){
		return myVals.get(i);
	}
	protected abstract double setState(TurtleState myState);
}
