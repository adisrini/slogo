package slogo.model;

import java.util.Map;

public class SLCommand_NOTEQUAL extends EqualityTester{
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return super.determineEquality(m, scope)==0 ? 1 : 0;
	}
}
