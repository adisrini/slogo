package slogo.model;

import java.util.Iterator;

public class TesterMain {
	public static void main (String[] args){
		
		String tester = "RIGHT 360";
		Model model = new Model();
		model.initExecution(tester);
		Iterator<IState> it = model.getStateIterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}




	}
}
