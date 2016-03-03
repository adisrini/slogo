package slogo.model;
import java.util.ArrayList;

import java.util.List;

public class IF implements IFunction{
	private double myBoolean;
	private List<IFunction> myTrueFunctions;
	@Override
	public void createFunction(IParser p, IMemory m) {
		myBoolean = p.parseExpression();
		myTrueFunctions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myTrueFunctions.add(p.parseFunction());
		}
	}
	@Override
	public double executeFunction(IMemory m) {
		double finalnum = 0;
		if(myBoolean!=0){
			for(IFunction function : myTrueFunctions){
				finalnum = function.executeFunction(m);
			}
		}
		return finalnum;
	}
	
}
