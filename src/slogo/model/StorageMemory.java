package slogo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageMemory {
	private ExecutionManager myExecutionManager;
    private Map<String, Double> myVariables;
    private Map<String, String> myMethods;

    public StorageMemory(){
    	myExecutionManager = new ExecutionManager();
        myVariables = new HashMap<String, Double>();
        myMethods = new  HashMap<String, String>();
    }
    
    public ExecutionManager getExecutionManager(){
    	return myExecutionManager;
    }
    public void writeStates(List<IState> list){
		myExecutionManager.readCurrExecution().addStateList(list);
    }
    public void writeVariable (String varName, double varValue) {
        myVariables.put(varName, varValue);
    }

    public Double readVariable (String varName) {
        return myVariables.get(varName);
    }

    public void writeMethod (String methodName, String methodContent) {
        myMethods.put(methodName, methodContent);
    }

    public String readMethod (String methodName) {
        return myMethods.get(methodName);
    }

    public Map<String, Double> getVariableMap () {
        return Collections.unmodifiableMap(myVariables);
    }
    
    public void updateVariables(Map<String,Double> m){
		for(String varName : m.keySet()){
			myVariables.put(varName, m.get(varName));
		}
	}
    public Iterator<List<IState>> getStateIterator (){
    	return myExecutionManager.readCurrExecution().getListOfStates().iterator();
    }
}
