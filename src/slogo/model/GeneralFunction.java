package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class GeneralFunction.
 */
public abstract class GeneralFunction implements IFunction{
	
	/** Whether or not the function has Expressions. */
	private boolean hasExpressions = true;
	
	/** The expressions. */
	private List<IFunction> myExpressions;
	
	/** The functions. */
	private List<IFunction> myFunctions;
	
	/** The Maximum number of expressions that will be parsed through. */
	private static final int MAX_EXPRESSIONS = 100;

	/**
	 * Creates the most general type of function, in which
	 * a list of expressions are given after the method title
	 */
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		parseExpressions(p,m,scope);
	}
	
	/**
	 * Parses the expressions.
	 *
	 * @param p the p
	 * @param m the m
	 * @param scope the scope
	 */
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
	
	/**
	 * Parses the bracketed expressions.
	 *
	 * @param p the p
	 * @param m the m
	 * @param scope the scope
	 */
	protected void parseBracketedExpressions(IParser p, IMemory m,  Map<String,Double> scope){
		myExpressions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myExpressions.add(p.parseExpression(scope));
		}
	}
	
	/**
	 * Parses the function list.
	 *
	 * @param p the p
	 * @param m the m
	 * @param scope the scope
	 */
	protected void parseFunctionList(IParser p, IMemory m,  Map<String,Double> scope){
		myFunctions = new ArrayList<IFunction>();
		p.parseListBegin();
		while(!p.reachedListEnd()){
			myFunctions.add(p.parseExpression(scope));
		}
	}
	
	/**
	 * Execute function list.
	 *
	 * @param m the m
	 * @param scope the scope
	 * @return the double
	 */
	protected double executeFunctionList(IMemory m,  Map<String,Double> scope){
		double val = 0;
		for(IFunction f : myFunctions){
			val = f.executeFunction(m, scope);
		}
		return val;
	}
	
	/**
	 * Gets the expression.
	 *
	 * @param i the Index
	 * @param m the Memory
	 * @param scope the method's Scope
	 * @return the evaluated Expression @ Index.
	 */
	protected double getExpression(int i, IMemory m,Map<String,Double> scope){
		return myExpressions.get(i).executeFunction(m, scope);
	}
	
	/**
	 * Checks to see if this method
	 * contains expressions that should
	 * be iterated through
	 *
	 * @return true, if expressions are had
	 */
	protected boolean hasExpressions(){
		return hasExpressions;
	}
	
	/**
	 * Returns the number of Expressions
	 * that have been parsed .
	 *
	 * @return the int
	 */
	protected int numberOfExpressions(){
		return myExpressions.size();
	}
	
	/**
	 * Methods must be able to return
	 * the minimum number
	 * of arguments that they need to 
	 * execute.
	 *
	 * @return the int
	 */
	protected abstract int argsNeeded();
	
}
