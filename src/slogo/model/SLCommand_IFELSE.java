package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLCommand_IFELSE implements IFunction{

	private IFunction myBooleanFunc;
	private List<IFunction> myTrueFunctions;
	private List<IFunction> myFalseFunctions;

	@Override
	public void createFunction(IParser p, IMemory m, Map<String,Double> scope) {
		myBooleanFunc = p.parseExpression(scope);
		p.parseListBegin();
		myTrueFunctions = new ArrayList<IFunction>();
		while(!p.reachedListEnd()){
			myTrueFunctions.add(p.parseFunction(scope));
		}
		myFalseFunctions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myFalseFunctions.add(p.parseFunction(scope));
		}
		if(p.requestedUnlimitedExpressions()){ p.parseGroupEnd(); }
	}

	@Override
	public double executeFunction(IMemory m,Map<String,Double> scope) {
		double finalnum = 0;
		double myBoolean = myBooleanFunc.executeFunction(m, scope);
		if(myBoolean == 0){
			for(IFunction function : myTrueFunctions){
				finalnum = function.executeFunction(m,scope);
			}
		}
		else{
			for(IFunction function : myFalseFunctions){
				finalnum = function.executeFunction(m,scope);
			}
		}
		return finalnum;
	}
}
