/*
 * @author Krista Opsahl-Ong
 */
package slogo.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import generic.Pair;


/**
 * The Class Model.
 */
public class Model implements IModel {
    
    /** The memory. */
    private IMemory memory;
    
    /**
     * Instantiates a new model.
     */
    public Model () {
        memory = Memory.getMemoryInstance();
    }

    /**
     * Initializes the Execution, adds it to memory
     * and then makes the call to build and execute the command tree
     */
    public void initExecution (Pair<String, String> inputs) {
        IExecution exec = new Execution();
        memory.getStorageMemory().getExecutionManager().writeExecution(exec);
        buildAndExecute(inputs.getFirst(), exec);
    }

	/**
	 * Builds and executes the Executions Command Tree
	 *
	 * @param input the cleaned text from the console
	 * @param exec the current Execution
	 */
	private void buildAndExecute(String input, IExecution exec) {
        Map<String,Double> myScope = new HashMap<String,Double>(memory.getStorageMemory().getVariableMap());
		Parser p = new Parser(input, memory);
		
        while (p.hasNext()) {
            try {p.parseFunction(myScope).executeFunction(memory,myScope);}
            catch (SlogoException e) {throw e;}
        }
        memory.getStorageMemory().updateVariables(myScope);
	}

    /**
     * Returns the State before the last
     * had been undone
     * 
     * @return List<IState>
     */
    public List<IState> redoState(){
    	return memory.redoState();
    }

    /**
     * Returns the State before the current
     * 
     * @return List<IState>
     */
    public List<IState> undoState () {
        return memory.undoState();
    }

    /**
     * Re-does the Execution that had previously
     * been undone. Returns an Iterator of Lists
     * of States that allows for the GUI to update
     * the States sequentially to restore the previous
     * Execution
     * 
     * @return Iterator<List<IState>>
     */
    public Iterator<List<IState>> redoExecution () {
    	List<List<IState>> list =  memory.redoExecution();
    	if(list == null){return null;}
    	return list.iterator();
    }

    /**
     * Returns the Model a previous State
     * from before the last Execution had been
     * made
     * 
     * @return List<IState>
     */
    public List<IState> undoExecution () {
    	return memory.undoExecution();
    }

    /**
     * Returns an Iterator of lists of States
     * to allow the View to update the GUI
     * 
     * @return Iterator<List<IState>> 
     */
    public Iterator<List<IState>> getStateIterator () {
        return memory.getStorageMemory().getStateIterator();
    }

	/**
	 * Update current tab.
	 *
	 * @param id the id
	 */
	public void updateCurrentTab(int id) {
		memory.updateCurrentlyActiveTab(id);
	}
}
