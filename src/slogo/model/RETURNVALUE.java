package slogo.model;

abstract class RETURNVALUE implements IFunction{
	protected double myValue;
	public double executeFunction(IMemory m) {
		return myValue;
	}
}
