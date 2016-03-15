package slogo.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Memory implements IMemory {
    private static Memory memoryInstance = null;
    // TODO: Change so that the methods are package friendly instead

    //list of all active Tab elements
    private Map<Integer,ActiveMemory> allActiveMemory;
    private Map<Integer,StorageMemory> allStorageMemory;
    
    
    //list of pointers to these active Tab elements
    private ActiveMemory myActiveMemory;
    private StorageMemory myStorageMemory;
    
    private int currentlyActiveTab;
    
    private Memory () {
    	allActiveMemory = new TreeMap<Integer,ActiveMemory>();
    	allStorageMemory = new TreeMap<Integer,StorageMemory>();
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
    
    private void initTabMemory(){
    	
    	//initiate new elements for the new tab
    	ActiveMemory tabActiveMemory = new ActiveMemory();
    	StorageMemory tabStorageMemory = new StorageMemory();
        
        allActiveMemory.put(currentlyActiveTab, tabActiveMemory);
        allStorageMemory.put(currentlyActiveTab, tabStorageMemory);
        
        updatePointers();
        
        //set the starter states for these elements
        myStorageMemory.getExecutionManager().setStarterExecution(); //sets the starter execution to have one new turtle initially at 0,0
    	myActiveMemory.resetStarterStates(); //sets the active turtle to be 1 and    	
    }
    
	public void resetCurrentTab(){
		//remove everything so that it can be reinitilazed
		allActiveMemory.remove(currentlyActiveTab);
		allStorageMemory.remove(currentlyActiveTab);
		updateCurrentlyActiveTab(currentlyActiveTab);
	}
	
	public void updateCurrentlyActiveTurtleID(int id){
		myActiveMemory.updateCurrentlyActiveTurtleID(id);
	}
	public void updateCurrentlyActiveTab(int id) {
		this.currentlyActiveTab = id;
		if(allStorageMemory.get(id)==null){
			initTabMemory();
		}
		else{updatePointers();}

	}
	
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
	
	public List<IState> undoState(){
    	List<IState> list = myStorageMemory.getExecutionManager().undoState();
		if(list!=null){
			getActiveMemory().updateStartersWithStateList(list);
			return list;
		}
    	return undoExecution();
    }
	
    public List<IState> undoExecution () {
    	List<IState> prevStateList = myStorageMemory.getExecutionManager().undoExecution();
    	if(prevStateList==null){myActiveMemory.resetStarterStates();}
    	myActiveMemory.updateStartersWithStateList(prevStateList);
    	return prevStateList;
    	
    }
    public List<List<IState>> redoExecution() {
    	
    	IExecution nextExecution = myStorageMemory.getExecutionManager().redoExecution();
    	List<List<IState>> nextListOfStates = nextExecution.getListOfStates();
    	myActiveMemory.updateStartersWithStateList(nextExecution.getCurrStateList());
    	
    	return nextListOfStates; 
    }
	private void updatePointers(){
		myActiveMemory = allActiveMemory.get(currentlyActiveTab);
		myStorageMemory = allStorageMemory.get(currentlyActiveTab);
	}
	
	public StorageMemory getStorageMemory(){
		return myStorageMemory;
	}
	
	public ActiveMemory getActiveMemory(){
		return myActiveMemory;
	}
}
