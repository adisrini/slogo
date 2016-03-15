package slogo.model;

import java.util.Map;

class SLCommand_TURTLES extends ReturnValue{
	private static final int LIMIT = 0;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return m.getActiveMemory().readTurtleNumber();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}

}
