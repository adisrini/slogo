package slogo.model;

class PENUP extends PENCHANGE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		super.myStatus = false;
	}
}
