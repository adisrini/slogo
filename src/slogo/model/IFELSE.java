package slogo.model;

import java.util.ArrayList;
import java.util.List;

public class IFELSE implements IFunction{

	private double myBoolean;
	private List<IFunction> myTrueFunctions;
	private List<IFunction> myFalseFunctions;

	@Override
	public void createFunction(IParser p, IMemory m) {
		myBoolean = p.parseExpression();
		p.parseListBegin();
		myTrueFunctions = new ArrayList<IFunction>();
		while(!p.reachedListEnd()){
			myTrueFunctions.add(p.parseFunction());
		}
		myFalseFunctions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myFalseFunctions.add(p.parseFunction());
		}
	}

	@Override
	public double executeFunction(IMemory m) {
		double finalnum = 0;
		if(myBoolean == 0){
			for(IFunction function : myTrueFunctions){
				finalnum = function.executeFunction(m);
			}
		}
		else{
			for(IFunction function : myFalseFunctions){
				finalnum = function.executeFunction(m);
			}
		}
		return finalnum;
	}
	@Override
	public String toString(){
		return "IFELSE "+myBoolean;
	}
}
