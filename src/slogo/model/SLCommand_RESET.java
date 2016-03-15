package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLCommand_RESET extends GeneralFunction {
	private static final int LIMIT = 0;
	
	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		m.resetCurrentTab(); // reset everything
		TurtleState turtState = new TurtleState(m.getActiveMemory().getCurrentlyActiveTurtle());
		turtState.setHeading(361);
		List<IState> list = new ArrayList<IState>();
		m.getStorageMemory().writeStates(list);
		list.add(turtState);
		return 1;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
