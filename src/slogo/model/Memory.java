package slogo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Memory implements IMemory {
    private static Memory memoryInstance = null;
    // TODO: Change so that the methods are package friendly instead

    private DoublyLinkedList<IExecution> myExecutions;
    private Map<String, Double> myVariables;
    private Map<String, String> myMethods;
    private IState builderState;
    
    private Memory () {
        myExecutions = new DoublyLinkedList<IExecution>();
        myVariables = new HashMap<String, Double>();
        myMethods = new HashMap<String, String>();
    }

    /**
     * Singleton Design Pattern Implementation
     * 
     * Only one instance of memory can exist in the project
     * 
     * @return private instance of memory
     */
    public static Memory getMemoryInstance () {
        if (memoryInstance == null) {
            memoryInstance = new Memory();
        }
        return memoryInstance;
    }

    @Override
    public void writeExecution (IExecution exec) {
        myExecutions.add(exec);
    }

    @Override
    public IExecution readCurrExecution () {
        if (myExecutions.getCurr()==null){
            return null;
        }
        
        return myExecutions.getCurr();
    }

    @Override
    public IExecution readPrevExecution () {
        return myExecutions.getPrev();
    }

    @Override
    public IExecution readNextExecution () {
        return myExecutions.getNext();
    }

    @Override
    public IState redoExecution () {
    	IState curr = myExecutions.getCurr().getCurrState();
    	if(curr==null){curr=builderState;}
    	IState redone = curr;
    	while(curr.equals(redone)&&redone!=null&&hasNextExecution()){
    		myExecutions.redo();
    		redone = myExecutions.getCurr().getCurrState();
    	}
    	setBuilderState(myExecutions.getCurr().getCurrState());
    	if(curr.equals(redone)){return null;} //if there was no new state just return null
    	return myExecutions.getCurr().getCurrState();
    }

    @Override
    public IState undoExecution () {
    	IState curr = myExecutions.getCurr().getCurrState();
    	if(curr==null){curr=builderState;}
    	IState undone = curr;
    	while(curr.equals(undone)&&undone!=null){
    		if(myExecutions.undo()==null){
    			setBuilderState(new State());
    			return null;}
    		undone = myExecutions.getCurr().getCurrState();
    	}
		setBuilderState(myExecutions.getCurr().getCurrState());
    	return myExecutions.getCurr().getCurrState();
    }

    @Override
    public void writeVariable (String varName, double varValue) {
        myVariables.put(varName, varValue);
    }

    @Override
    public Double readVariable (String varName) {
        return myVariables.get(varName);
    }

    @Override
    public void writeMethod (String methodName, String methodContent) {
        myMethods.put(methodName, methodContent);
    }

    @Override
    public String readMethod (String methodName) {
        return myMethods.get(methodName);
    }

    public Map<String, Double> getVariableMap () {
        return Collections.unmodifiableMap(myVariables);
    }

    @Override
    public boolean hasNextExecution () {
        return myExecutions.hasNext();
    }

    @Override
    public boolean hasPrevExecution () {
        return myExecutions.hasPrev();
    }

	public IState readBuilderState() {
		return builderState;
	}

	public void setBuilderState(IState builderState) {
		this.builderState = builderState;
	}
}
