package slogo.model;

import java.util.Iterator;
import java.util.List;

public class TesterMain {
	public static void main (String[] args){
		
		//String tester = "TELL [ 1 2 3 ] ( FORWARD 100 200 300 ( SUM 50 50 50 50 ) ) FORWARD 100 TELL [ 1 2 ] FORWARD 200 TELL [ 3 ] ";
		/*String tester = "TO square [ ] [ FORWARD :size RT 90 ] ] TO triangle [ :size ] "
				+ "[ REPEAT 3 [ FD :size RT 120 ] ]"
				+ "TO house [ ] [ SET :size 100 square FD :size RIGHT 60 triangle 80 FD :size ]";*/
//		String tester = "TO square [ ] [ FORWARD :size ]";
//		tester += " TO triangle [ :size ] [";
//		tester+=" FORWARD :size ]";
//		tester+=" TO house [ ] [";
//		tester+=" MAKE :size 100 ";
//		tester+= "square ";
//		tester+="FORWARD :size ";
//		tester+="triangle 80 ";
//		tester+="FORWARD :size ] ";
//		tester+="TELL [ 1 2 3 4 ] house";
//				+ " TO triangle [ :size ] ["
//				+ " FORWARD :size ] "
//				+ "TO house [ ] [ "
//				+ "MAKE :size 100 "
//				+ "square "
//				+ "FORWARD :size "
//				+ "triangle :size ] "
//				+ "house";
		System.out.println("First Execution");
		//String tester = " TELL [ 1 2 3 ] FORWARD PRODUCT 10 10 ( SUM 1 1 1 1 ) ASK [ 1 ] [ FORWARD 20 ] FORWARD 10";
		String tester1 = "TO square [ :a ] [ forward :a ] square 100";
		Model model = new Model();
		model.updateCurrentTab(1);
		model.initExecution(tester1);
		Iterator<List<IState>> it = model.getStateIterator();
		while(it.hasNext()){
			for(IState state : it.next()){
				System.out.println(state);
			}
		}
		System.out.println("Second Execution");
		//String tester2 = "TELL [ 2 ] right 30 TELL [ 2 3 ] FORWARD 10";
		String tester2 = "ASKWITH [ EQUALP SHAPE 2 ] [ FORWARD 388 BACKWARD 50 ]";
		model.initExecution(tester2);
		Iterator<List<IState>> it2 = model.getStateIterator();
		while(it2.hasNext()){
			for(IState state : it2.next()){
				System.out.println(state);
			}
		}
		model.updateCurrentTab(2);
		System.out.println("First Execution");
		//String tester = " TELL [ 1 2 3 ] FORWARD PRODUCT 10 10 ( SUM 1 1 1 1 ) ASK [ 1 ] [ FORWARD 20 ] FORWARD 10";
		String tester3 = " TELL [ 1 2 ] FORWARD 100 ";
		model.initExecution(tester3);
		Iterator<List<IState>> it3 = model.getStateIterator();
		while(it3.hasNext()){
			for(IState state : it3.next()){
				System.out.println(state);
			}
		}
		System.out.println("Second Execution");
		//String tester2 = "TELL [ 2 ] right 30 TELL [ 2 3 ] FORWARD 10";
		String tester4 = "TELL [ 2 3 ] FORWARD 100 BACKWARD 50";
		model.initExecution(tester4);
		Iterator<List<IState>> it4 = model.getStateIterator();
		while(it4.hasNext()){
			for(IState state : it4.next()){
				System.out.println(state);
			}
		}
		System.out.println("Third Execution");

		String tester5 = "WAKE [ 1 ] HOME";
		model.initExecution(tester5);
		Iterator<List<IState>> it5 = model.getStateIterator();
		while(it5.hasNext()){
			for(IState state : it5.next()){
				System.out.println(state);
			}
		}
		
		System.out.println("Fourth Execution");

		String tester6 = "RESET";
		model.initExecution(tester6);
		Iterator<List<IState>> it6 = model.getStateIterator();
		while(it6.hasNext()){
			for(IState state : it6.next()){
				System.out.println(state);
			}
		}
		
	}
}
