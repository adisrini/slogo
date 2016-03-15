package slogo.model;

import java.util.Map;

public class SLCommand_ID extends ReturnValue{
	private static final int LIMIT = 0;
	
	public double getValue(IMemory m, Map<String, Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtleID();
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
