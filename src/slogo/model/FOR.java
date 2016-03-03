package slogo.model;

import java.util.List;

public class FOR extends LOOPER{

	@Override
	public void createFunction(IParser p, IMemory m) {
		p.parseListBegin();
		myVariableName = p.parseVariableName();
		myStart = p.parseExpression();
		myEnd = p.parseExpression();
		myIncrement = p.parseExpression();
		p.parseListEnd();
		myFunctionsString = p.extractListAsString(); //get method text
	}
}
