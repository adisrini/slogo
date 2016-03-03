package slogo.model;

import java.util.ArrayList;
import java.util.List;

public abstract class LOOPER implements IFunction{
	protected double myStart;
	protected String myVariableName;
	protected double myEnd;
	protected double myIncrement;
	protected String myFunctionsString;
	protected List<IFunction> myFunctions;

	@Override
	public double executeFunction(IMemory m) {
		System.out.println("My Function String: "+myFunctionsString);
		
		
		myFunctions =  new ArrayList<IFunction>();
		
		double finalnum = 0;
		for(double n = myStart; n <= myEnd; n+=myIncrement){
			m.writeVariable(myVariableName, n);
			Parser methodParser = new Parser(myFunctionsString,m);
			methodParser.parseListBegin();
			while(!methodParser.reachedListEnd()){
				myFunctions.add(methodParser.parseFunction());
			}
			for(IFunction function : myFunctions){
				function.executeFunction(m);
			}
			myFunctions.clear();
		}
		return finalnum;
	}
}
