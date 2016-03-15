package slogo.model;

import java.util.ArrayList;
import java.util.List;

public class ExecutionManager {
    private static final Integer STARTER_ID = 1;
	private DoublyLinkedList<IExecution> myExecutions;
	
	public ExecutionManager(){
		myExecutions = new DoublyLinkedList<IExecution>();
	}
	
	public void writeExecution (IExecution exec) {
        myExecutions.add(exec);
    }
    
    public void setStarterExecution(){
   	 IExecution starter = new Execution();
    	List<IState> initialStates = new ArrayList<IState>();
    	initialStates.add(new TurtleState(STARTER_ID));
    	starter.addStateList(initialStates);
    	writeExecution(starter);
    }
    
    public IExecution readCurrExecution () {
        if (myExecutions.getCurr() == null) {
            return null;
        }
        return myExecutions.getCurr();
    }
    
    public IExecution readPrevExecution () {
        return myExecutions.getPrev();
    }
    
    public IExecution readNextExecution () {
        return myExecutions.getNext();
    }
    
    public IExecution redoExecution () {    	
    	if(!myExecutions.hasNext()){return null;}
    	IExecution nextExecution = myExecutions.redo();
    	while(nextExecution.getCurrStateList().size()==0){
    		if(!myExecutions.hasNext()){return null;}
    		nextExecution = myExecutions.redo();
    	}
    	return nextExecution;   	
    }
    
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
    public List<IState> redoState(){
    	if (readCurrExecution().hasNextState()) {
    		List<IState> list = readCurrExecution().redoStateList();
    		return list;
    	}
    	return null;
    }

    public List<IState> undoState () {
        if (readCurrExecution().hasPrevState()) {
        	List<IState> list = readCurrExecution().undoStateList();
            return list;
        }
        return null;
    }
    public boolean hasPrevExecution () {
        return myExecutions.hasPrev();
    }
}
