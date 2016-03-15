package slogo.model;

import java.util.Map;

public class SLCommand_SHOWINGP extends ReturnValue{
	private static final int LIMIT = 0;
	/**
     * Returns number of visible turtles
     * @return double
     */	
	@Override
	public double getValue(IMemory m, Map<String,Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getVisibility() ? 1 : 0;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
