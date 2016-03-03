package slogo.tester;

import java.util.HashMap;
import slogo.model.State;

public class StateMapTester {
    public static void main (String[] args){
        State a = new State();
        a.setX(100);
        a.setY(100);
        a.setHeading(100);
        
        State b = new State();
        b.setX(100);
        b.setY(100);
        b.setHeading(100);
        
        System.out.println("==: "+ (a==b));
        System.out.println(".equals: "+(a.equals(b)));
        
        HashMap<State,Integer> map = new HashMap<State,Integer>();
        
        map.put(a, 1);
        map.put(b, 1);
        
        System.out.println("HashMap keys: "+ map.keySet());
    }
}