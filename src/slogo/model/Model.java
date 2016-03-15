package slogo.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import generic.Pair;


public class Model implements IModel {
    private IMemory memory;
    
    public Model () {
        memory = Memory.getMemoryInstance();
    }

//TODO: DELETE SOON CAUSE FOR TESTING PURPOSES ONLY
   /* public void initExecution (String input) {
       IExecution exec = new Execution();
        
       if (!memory.hasPrevExecution()) {
        	IExecution starter = new Execution();
        	List<IState> initialStates = new ArrayList<IState>();
        	initialStates.add(new TurtleState(STARTER_ID));
        	starter.addStateList(initialStates);
//        	memory.updateStarterDisplay(new DisplayState());
        	memory.writeExecution(starter);
//            memory.activateTurtle(STARTER_ID);
//        	memory.updateStarterState(STARTER_ID, new TurtleState(STARTER_ID));
        	memory.resetStarterStates();
        }

       memory.writeExecution(exec);
       buildAndExecute(input, exec);
   }*/
   
    public void initExecution (Pair<String, String> inputs) {
        IExecution exec = new Execution();
        memory.getStorageMemory().getExecutionManager().writeExecution(exec);
        buildAndExecute(inputs.getFirst(), exec);
        System.out.println("UNALTERED " + inputs.getLast());
    }

	private void buildAndExecute(String input, IExecution exec) {
        Map<String,Double> myScope = new HashMap<String,Double>(memory.getStorageMemory().getVariableMap());
		Parser p = new Parser(input, memory);
		
        while (p.hasNext()) {
            try {p.parseFunction(myScope).executeFunction(memory,myScope);}
            catch (SlogoException e) {throw e;}
        }
        memory.getStorageMemory().updateVariables(myScope);
	}

    public List<IState> redoState(){
    	return memory.redoState();
    }

    public List<IState> undoState () {
        return memory.undoState();
    }

    public Iterator<List<IState>> redoExecution () {
    	List<List<IState>> list =  memory.redoExecution();
    	if(list == null){return null;}
    	return list.iterator();
    }

    public List<IState> undoExecution () {
    	return memory.undoExecution();
    }

    public void reset () {
    	memory.resetCurrentTab();
    }

    public Iterator<List<IState>> getStateIterator () {
        return memory.getStorageMemory().getStateIterator();
    }

	public void updateCurrentTab(int id) {
		memory.updateCurrentlyActiveTab(id);
	}
}
