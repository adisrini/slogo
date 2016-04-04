package slogo.model;
import java.util.Map;

/**
 * The Class MathOperation.
 * 
 * Used by SLogo Math commands
 */
public abstract class MathOperation extends ReturnValue{
	
	/**
	 * Operate on values using Operator op.
	 *
	 * @param op the Operator
	 * @param scope the scope
	 * @param m the m
	 * @return the double
	 */
	protected double operateOnValues(ArithmeticOperator op, Map<String,Double> scope,IMemory m){
		double myValue = getExpression(0,m,scope);
		for(int i = 1; i<numberOfExpressions(); i++){
			myValue = op.apply(myValue, getExpression(i,m,scope));
		}
		return myValue;
	}
}
