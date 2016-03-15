package slogo.model;

import java.util.Map;

public class SLCommand_PI extends ReturnValue {
	private static final int LIMIT = 0;
	@Override
	public double getValue(IMemory m, Map<String,Double> scope) {
		return Math.PI;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}