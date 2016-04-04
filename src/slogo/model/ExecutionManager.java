package slogo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ExecutionManager.
 * 
 * Manages important 'behind the scenes' methods
 * that are relevant to the Executions in memory
 */
public class ExecutionManager {
    	
	/** Execution Manager's Parameters. */
	private DoublyLinkedList<IExecution> myExecutions;
    private static final Integer STARTER_ID = 1;
	
	/**
	 * Instantiates a new execution manager.
	 */
	public ExecutionManager(){
		myExecutions = new DoublyLinkedList<IExecution>();
	}
	
	/**
	 * Write an execution.
	 *
	 * @param exec the exec
	 */
	public void writeExecution (IExecution exec) {
        myExecutions.add(exec);
    }
    
    /**
     * Sets the starter execution.
     */
    public void setStarterExecution(){
   	 IExecution starter = new Execution();
    	List<IState> initialStates = new ArrayList<IState>();
    	initialStates.add(new TurtleState(STARTER_ID));
    	starter.addStateList(initialStates);
    	writeExecution(starter);
    }
    
    /**
     * Reads the current execution.
     *
     * @return the current execution
     */
    public IExecution readCurrExecution () {
        if (myExecutions.getCurr() == null) {
            return null;
        }
        return myExecutions.getCurr();
    }
    
    /**
     * Redo the previous execution.
     *
     * @return the previous execution
     */
    public IExecution redoExecution () {    	
    	if(!myExecutions.hasNext()){return null;}
    	IExecution nextExecution = myExecutions.redo();
    	while(nextExecution.getCurrStateList().size()==0){
    		if(!myExecutions.hasNext()){return null;}
    		nextExecution = myExecutions.redo();
    	}
    	return nextExecution;   	
    }
    
    /**
     * Undo an execution.
     *
     * @return the states within a previous execution
     */
    public List<IState> undoExecution () {
    	if(!myExecutions.hasPrev()){
    		myExecutions.undo();
    		return null;
    	}
    	IExecution prevExecution = myExecutions.undo();
    	while(prevExecution.getCurrStateList().size()==0){
    		if(!myExecutions.hasPrev()){ 
    			myExecutions.undo(); 
    			return null;
    		}
    		prevExecution = myExecutions.undo();
    	}
    	List<IState> prevStates = prevExecution.getCurrStateList();    	
    	return prevStates;
    }
    
    /**
     * Redo a previous list of States.
     *
     * @return a list of States
     */
    public List<IState> redoState(){
    	if (readCurrExecution().hasNextState()) {
    		List<IState> list = readCurrExecution().redoStateList();
    		return list;
    	}
    	return null;
    }

    /**
     * Undo the current list of States.
     *
     * @return the last list of States
     */
    public List<IState> undoState () {
        if (readCurrExecution().hasPrevState()) {
        	List<IState> list = readCurrExecution().undoStateList();
            return list;
        }
        return null;
    }
    
    /**
     * Checks for prev execution.
     *
     * @return true, if has a previous execution
     */
    public boolean hasPrevExecution () {
        return myExecutions.hasPrev();
    }
}
