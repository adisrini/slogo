package slogo.model;

public class SLCommand_LEFT extends Turn{	
	@Override
	protected double setVal(double val){
		return -val;
	}
}
