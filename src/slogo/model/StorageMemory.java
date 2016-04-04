package slogo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Class StorageMemory.
 * 
 * The history of Memory where
 * executions, variables, and methods are stored
 */
public class StorageMemory {
	
	/** The tab's execution manager. */
	private ExecutionManager myExecutionManager;
    
    /** The tab's variables. */
    private Map<String, Double> myVariables;
    
    /** The tab's methods. */
    private Map<String, String> myMethods;

    /**
     * Instantiates a new storage memory.
     */
    public StorageMemory(){
    	myExecutionManager = new ExecutionManager();
        myVariables = new HashMap<String, Double>();
        myMethods = new  HashMap<String, String>();
    }
    
    /**
     * Gets the execution manager.
     *
     * @return the execution manager
     */
    public ExecutionManager getExecutionManager(){
    	return myExecutionManager;
    }
    
    /**
     * Write states.
     *
     * @param list the list
     */
    public void writeStates(List<IState> list){
		myExecutionManager.readCurrExecution().addStateList(list);
    }
    
    /**
     * Write variable.
     *
     * @param varName the var name
     * @param varValue the var value
     */
    public void writeVariable (String varName, double varValue) {
        myVariables.put(varName, varValue);
    }

    /**
     * Read variable.
     *
     * @param varName the var name
     * @return the double
     */
    public Double readVariable (String varName) {
        return myVariables.get(varName);
    }

    /**
     * Write method.
     *
     * @param methodName the method name
     * @param methodContent the method content
     */
    public void writeMethod (String methodName, String methodContent) {
        myMethods.put(methodName, methodContent);
    }

    /**
     * Read method.
     *
     * @param methodName the method name
     * @return the string
     */
    public String readMethod (String methodName) {
        return myMethods.get(methodName);
    }

    /**
     * Gets the variable map.
     *
     * @return the variable map
     */
    public Map<String, Double> getVariableMap () {
        return Collections.unmodifiableMap(myVariables);
    }
    
    /**
     * Update variables.
     *
     * @param m the m
     */
    public void updateVariables(Map<String,Double> m){
		for(String varName : m.keySet()){
			myVariables.put(varName, m.get(varName));
		}
	}
    
    /**
     * Gets the state iterator.
     *
     * @return the state iterator
     */
    public Iterator<List<IState>> getStateIterator (){
    	return myExecutionManager.readCurrExecution().getListOfStates().iterator();
    }
}
