package slogo.model;

public class MAKE implements IFunction{
	private String varName;
	private double varVal;
	@Override
	public void createFunction(IParser p, IMemory m) {
		varName = p.parseVariableName();
		varVal = p.parseExpression();
		executeFunction(m);
	}

	@Override
	public double executeFunction(IMemory m) {
		m.writeVariable(varName,varVal);
		return varVal;
	}
	public String toString(){
		return ""+ varName+ " "+varVal;
	}
}
