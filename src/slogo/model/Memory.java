package slogo.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * The Class Memory.
 * 
 * Contains both Active memory and Storage memory
 */
public class Memory implements IMemory {
    
    /** The memory instance. */
    private static Memory memoryInstance = null;

    /** List of all active Tab elements. */
    private Map<Integer,ActiveMemory> allActiveMemory;
    private Map<Integer,StorageMemory> allStorageMemory;
    
    
    /** List of pointers to these active Tab elements. */
    private ActiveMemory myActiveMemory;
    private StorageMemory myStorageMemory;
    
    /** The currently active tab. */
    private int currentlyActiveTab;
    
    /**
     * Instantiates a new memory.
     */
    private Memory () {
    	allActiveMemory = new TreeMap<Integer,ActiveMemory>();
    	allStorageMemory = new TreeMap<Integer,StorageMemory>();
    }

    /**
     * Singleton Design Pattern Implementation
     * Only one instance of memory can exist in the project.
     *
     * @return private instance of memory
     */
    public static Memory getMemoryInstance () {
        if (memoryInstance == null) {
            memoryInstance = new Memory();
        }
        return memoryInstance;
    }
    
    /**
     * Initializes a tab memory.
     */
    private void initTabMemory(){
    	
    	//initiate new elements for the new tab
    	ActiveMemory tabActiveMemory = new ActiveMemory();
    	StorageMemory tabStorageMemory = new StorageMemory();
        
        allActiveMemory.put(currentlyActiveTab, tabActiveMemory);
        allStorageMemory.put(currentlyActiveTab, tabStorageMemory);
        
        updatePointers();
        
        //set the starter states for these elements
        myStorageMemory.getExecutionManager().setStarterExecution();
    	myActiveMemory.resetStarterStates(); 
    }
    
	/**
	 * Resets the current Tab to initial settings
	 */
	public void resetCurrentTab(){
		//remove everything so that it can be reinitilazed
		allActiveMemory.remove(currentlyActiveTab);
		allStorageMemory.remove(currentlyActiveTab);
		updateCurrentlyActiveTab(currentlyActiveTab);
	}
	
	/**
	 * Update currently active turtle id.
	 *
	 * @param id the id
	 */
	public void updateCurrentlyActiveTurtleID(int id){
		myActiveMemory.updateCurrentlyActiveTurtleID(id);
	}
	
	/**
	 * updates the currently Active Tab id
	 */
	public void updateCurrentlyActiveTab(int id) {
		this.currentlyActiveTab = id;
		if(allStorageMemory.get(id)==null){
			initTabMemory();
		}
		else{updatePointers();}

	}
	
	/**
	 * re-do the previous State
	 * 
	 * @return List<IState>
	 */
	public List<IState> redoState(){
    	List<IState> list = myStorageMemory.getExecutionManager().redoState();
		if(list!=null){
			getActiveMemory().updateStartersWithStateList(list);
			return list;
		}
    	List<List<IState>> listoflists = redoExecution();
    	if(listoflists==null){return null;}
    	return listoflists.get(listoflists.size()-1);
    }
	
	/**
	 * un-do current STate
	 * 
	 * @return List<IState>
	 */
	public List<IState> undoState(){
    	List<IState> list = myStorageMemory.getExecutionManager().undoState();
		if(list!=null){
			getActiveMemory().updateStartersWithStateList(list);
			return list;
		}
    	return undoExecution();
    }
	
    /**
     * un-do current execution
     * 
     * @return List<IState>
     */
    public List<IState> undoExecution () {
    	List<IState> prevStateList = myStorageMemory.getExecutionManager().undoExecution();
    	if(prevStateList==null){myActiveMemory.resetStarterStates();}
    	myActiveMemory.updateStartersWithStateList(prevStateList);
    	return prevStateList;
    	
    }
    
    /**
     * re-do previous execution
     * 
     * @return List<List<IState>>
     */
    public List<List<IState>> redoExecution() {
    	
    	IExecution nextExecution = myStorageMemory.getExecutionManager().redoExecution();
    	List<List<IState>> nextListOfStates = nextExecution.getListOfStates();
    	myActiveMemory.updateStartersWithStateList(nextExecution.getCurrStateList());
    	
    	return nextListOfStates; 
    }
	
	/**
	 * Update tab specific pointers.
	 */
	private void updatePointers(){
		myActiveMemory = allActiveMemory.get(currentlyActiveTab);
		myStorageMemory = allStorageMemory.get(currentlyActiveTab);
	}
	
	/**
	 * get memory storage
	 * 
	 * @return StorageMemory
	 */
	public StorageMemory getStorageMemory(){
		return myStorageMemory;
	}
	
	/**
	 * get active memory
	 * 
	 * @return ActiveMemory
	 */
	public ActiveMemory getActiveMemory(){
		return myActiveMemory;
	}
}
