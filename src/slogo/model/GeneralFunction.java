package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GeneralFunction implements IFunction{
	private boolean hasExpressions = true;
	private List<IFunction> myExpressions;
	private List<IFunction> myFunctions;
	private static final int MAX_EXPRESSIONS = 100;

	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		parseExpressions(p,m,scope);
	}
	
	protected void parseExpressions(IParser p, IMemory m,  Map<String,Double> scope){
		myExpressions = new ArrayList<IFunction>();
		int limit =  p.requestedUnlimitedExpressions() ? MAX_EXPRESSIONS : argsNeeded();
		if(limit==0){
			hasExpressions=false;
			if(p.requestedUnlimitedExpressions()){p.parseGroupEnd();}
		}
		int count = 0;
		while(!p.reachedGroupEnd()&&count<limit){
			myExpressions.add(p.parseExpression(scope));
			count++;
		}
	}
	
	protected void parseBracketedExpressions(IParser p, IMemory m,  Map<String,Double> scope){
		myExpressions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myExpressions.add(p.parseExpression(scope));
		}
	}
	
	protected void parseFunctionList(IParser p, IMemory m,  Map<String,Double> scope){
		myFunctions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myFunctions.add(p.parseExpression(scope));
		}
	}
	
	protected double executeFunctionList(IMemory m,  Map<String,Double> scope){
		double val = 0;
		for(IFunction f : myFunctions){
			val = f.executeFunction(m, scope);
		}
		return val;
	}
	
	protected double getExpression(int i, IMemory m,Map<String,Double> scope){
		return myExpressions.get(i).executeFunction(m, scope);
		//TODO: THROW ERROR IF OUT OF BOUNDS
		//NOT ENOUGH EXPRESSIONS PROVIDED
	}
	
	protected boolean hasExpressions(){
		return hasExpressions;
	}
	
	protected int numberOfExpressions(){
		return myExpressions.size();
	}
	
	protected abstract int argsNeeded();
	
}
