/*
 * @author Krista Opsahl-Ong
 */
package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ActiveMemory.
 * Serves to separate out the Storage component
 * from the Active component of total memory.
 */
public class ActiveMemory {
	
	/** The current starter states. */
	private Map<Integer,TurtleState> myStarterStates;
    
    /** The current starter display. */
    private DisplayState myStarterDisplay;
    
    /** The active turtle IDs. */
    private List<Integer> myActiveTurtleIDs;
    
    /** The currently active turtle id. */
    private Integer myCurrentlyActiveTurtleID;
    
    /** Static fields. */
    private static final Integer STARTER_ID = 1;
    private static final String DISPLAY_STATE = "DisplayState";
    private static final String TURTLE_STATE = "TurtleState";
    
    /**
     * Instantiates a new active memory.
     */
    public ActiveMemory(){
    	myStarterStates = new HashMap<Integer,TurtleState>();
        myStarterDisplay = new DisplayState();
        myActiveTurtleIDs = new ArrayList<Integer>();
        myCurrentlyActiveTurtleID = 1;
    }
    
	/**
	 * Read turtle number.
	 *
	 * @return the number of turtles
	 */
	public int readTurtleNumber(){
    	return myStarterStates.size();
    }
	
	/**
	 * Update Starter States with a state list.
	 *
	 * @param list the List of Starter States
	 */
	public void updateStartersWithStateList(List<IState> list){
    	for(IState state : list){
    		if(state.getStateType().equals(DISPLAY_STATE)){
    			updateStarterDisplay(new DisplayState((DisplayState) state));
    		}
    		if(state.getStateType().equals(TURTLE_STATE)){
    			TurtleState turtState = (TurtleState) state;
    			updateStarterState(turtState.getId(), turtState);
    		}
    	}
    }
	
	/**
	 * Update currently active turtle id.
	 *
	 * @param id the id
	 */
	public void updateCurrentlyActiveTurtleID(int id){
		this.myCurrentlyActiveTurtleID = id;
	}

	/**
	 * Reset starter states.
	 */
	public void resetStarterStates(){
    	updateStarterDisplay(new DisplayState());
    	myActiveTurtleIDs.clear();
    	myStarterStates.clear();
        activateTurtle(STARTER_ID);
    	updateStarterState(STARTER_ID, new TurtleState(STARTER_ID));
    }
	
	/**
	 * Read starter state.
	 *
	 * @param id the id
	 * @return the turtle state
	 */
	public TurtleState readStarterState(Integer id) {
		return myStarterStates.get(id);
	}
	
	/**
	 * Gets the active turtle IDs.
	 *
	 * @return the active turtle IDs
	 */
	public List<Integer> getActiveTurtleIDs(){
		return myActiveTurtleIDs;
	}
	
	/**
	 * Update starter state.
	 *
	 * @param id the id
	 * @param builderState the builder state
	 */
	public void updateStarterState(Integer id, TurtleState builderState) {
		myStarterStates.put(id, builderState);
	}
	
	/**
	 * Update active turtles.
	 *
	 * @param ids the IDs
	 */
	public void updateActiveTurtles(List<Integer> ids){
		myActiveTurtleIDs = new ArrayList<Integer>(ids);
		for(Integer id : myActiveTurtleIDs){
			if(!myStarterStates.containsKey(id)){
				myStarterStates.put(id, new TurtleState(id));
			}
		}
	}
	
	/**
	 * Activate turtle.
	 * Unlike the updateActiveTurtles method,
	 * it does not deactivate those that are not
	 * included in the inputed id
	 * 
	 * @param id the id
	 */
	public void activateTurtle(Integer id){
		myActiveTurtleIDs.add(id);
		if(!myStarterStates.containsKey(id)){
			myStarterStates.put(id, new TurtleState(id));
		}
	}
	
	/**
	 * Deactivate a single turtle.
	 * 
	 * @param id the id
	 */
	public void deactivateTurtle(Integer id){
		myActiveTurtleIDs.remove(id);
	}

	/**
	 * Gets the starter display.
	 *
	 * @return the starter display
	 */
	public DisplayState getStarterDisplay() {
		return myStarterDisplay;
	}

	/**
	 * Update starter display.
	 *
	 * @param myStarterDisplay the my starter display
	 */
	public void updateStarterDisplay(DisplayState myStarterDisplay) {
		this.myStarterDisplay = myStarterDisplay;
	}

	/**
	 * Gets the currently active turtle id.
	 *
	 * @return the currently active turtle id
	 */
	public int getCurrentlyActiveTurtleID() {
		return myCurrentlyActiveTurtleID;
	}
	
	/**
	 * Gets the currently active turtle.
	 *
	 * @return the currently active turtle
	 */
	public TurtleState getCurrentlyActiveTurtle(){
		return myStarterStates.get(myCurrentlyActiveTurtleID);
	}
}
