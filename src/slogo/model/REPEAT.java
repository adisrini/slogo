package slogo.model;

import java.util.ArrayList;

public class REPEAT extends LOOPER {
	@Override
	public void createFunction(IParser p, IMemory m) {
		//TODO: ASSIGN THE VARIABLE TO MEMORY
		myIncrement = 1; //DEFAULT FOR DOTIMES
		myStart = 1; //DEFAULT FOR DOTIMES
		myVariableName = "repcount";
		myEnd = p.parseExpression();
		myFunctionsString = p.extractListAsString(); //get method text
	}

}
