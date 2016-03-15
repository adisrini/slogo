package slogo.model;
import java.util.Map;

public abstract class MathOperation extends ReturnValue{
	
	protected double operateOnValues(ArithmeticOperator op, Map<String,Double> scope,IMemory m){
		double myValue = getExpression(0,m,scope);
		for(int i = 1; i<numberOfExpressions(); i++){
			myValue = op.apply(myValue, getExpression(i,m,scope));
		}
		return myValue;
	}
}
