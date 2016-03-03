package slogo.model;

import java.util.ArrayList;

public class DOTIMES extends LOOPER{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myIncrement = 1; //DEFAULT FOR DOTIMES
		myStart = 1; //DEFAULT FOR DOTIMES
		p.parseListBegin();
		myVariableName = p.parseVariableName();
		System.out.println("my variable name : " +myVariableName);
		myEnd = p.parseExpression();
		p.parseListEnd();
		myFunctionsString = p.extractListAsString(); //get method text

//		p.parseListBegin();
//		myFunctions = new ArrayList<IFunction>();
//		while(!p.reachedListEnd()){
//			myFunctions.add(p.parseFunction());
//		}
	}
}
