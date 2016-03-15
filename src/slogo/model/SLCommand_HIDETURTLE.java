package slogo.model;

import java.util.Map;

public class SLCommand_HIDETURTLE extends VisibilityChange{
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		super.myStatus = false;
	}
}
