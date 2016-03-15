package slogo.model;

import java.util.Map;

class SLCommand_PENDOWN extends PenChange{

	@Override
	public void createFunction(IParser p, IMemory m,  Map<String,Double> scope) {
		super.createFunction(p, m, scope);
		super.setMyStatus(true);
	}
}
