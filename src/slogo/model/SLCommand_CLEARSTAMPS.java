package slogo.model;

import java.util.Map;

public class SLCommand_CLEARSTAMPS extends GeneralFunction {
	public static final int LIMIT = 0;
	
	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		DisplayState ds = m.getActiveMemory().getStarterDisplay();
		ds.setClearStamps(true);
		m.getActiveMemory().updateStarterDisplay(ds);
		return 1;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
