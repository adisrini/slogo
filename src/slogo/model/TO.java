package slogo.model;

import java.util.ArrayList;
import java.util.List;

public class TO implements IFunction{
	private String myMethodName;
	private String myMethodText;
	private List<IFunction> methodContent; 
	@Override
	public void createFunction(IParser p, IMemory m) {
		myMethodName = p.parseCommandName();
		myMethodText = p.extractListAsString()+" "+p.extractListAsString();
		System.out.println(myMethodText);
		executeFunction(m);
	}

	@Override
	public double executeFunction(IMemory m) {
		m.writeMethod(myMethodName, myMethodText);
		return 1;
	}
}
