package slogo.model;

public class SLCommand_BACKWARD extends SLCommand_MOVE{
	@Override
	protected double setVal(double val){
		return -val;
	}
}
