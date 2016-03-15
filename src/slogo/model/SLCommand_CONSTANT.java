package slogo.model;

import java.util.Map;

public class SLCommand_CONSTANT implements IFunction{
	private double myVal;
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		myVal = p.parseConstant();
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		return myVal;
	}

}
