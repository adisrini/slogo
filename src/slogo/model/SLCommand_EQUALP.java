package slogo.model;

import java.util.Map;

public class SLCommand_EQUALP extends EqualityTester{
	public double getValue(IMemory m, Map<String, Double> scope) {
		return (super.determineEquality(m, scope));
	}
}
