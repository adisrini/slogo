package slogo.model;

import java.util.Map;

public class SLCommand_PENCOLOR extends ReturnValue {
	private static final int LIMIT = 1;

	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getPenColorIndex();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
