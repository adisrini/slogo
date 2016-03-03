package slogo.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class STOREDMETHOD implements IFunction{

	String myMethodName;
	String myMethodContent;
	Map<String, Double> myVariables;
	List<IFunction> myFunctions;
	public void createFunction(IParser p, IMemory m) {
		myMethodName = p.getPreviousText(); //GETS NAME TOKEN THAT WAS PREVIOUSLY READ IN
		myMethodContent = m.readMethod(myMethodName);
		myMethodContent = myMethodContent.substring(0,myMethodContent.lastIndexOf(']'));
		//create a parser out of the method string
		Parser methodparser = new Parser(myMethodContent,m);
		//parse away the list start
		methodparser.parseListBegin();
		while(!methodparser.reachedListEnd()){
			//parse through methodparser variable names
			//and add values from our parser
			m.writeVariable(methodparser.parseVariableName(),p.parseExpression());
		}
		methodparser.parseListBegin();
		myFunctions =  new ArrayList<IFunction>();
		while(methodparser.hasNext()){
			myFunctions.add(methodparser.parseFunction());
		}
		
	}

	@Override
	public double executeFunction(IMemory m) {
		double returnval = 0;
		for(IFunction function: myFunctions){
			returnval = function.executeFunction(m);
		}
		return returnval;
	}
}
