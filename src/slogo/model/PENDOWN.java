package slogo.model;

class PENDOWN extends PENCHANGE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		super.myStatus = true;
	}
}
