package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveMemory {
	private Map<Integer,TurtleState> myStarterStates;
    private DisplayState myStarterDisplay;
    private List<Integer> myActiveTurtleIDs;
    private Integer myCurrentlyActiveTurtleID;
    private static final Integer STARTER_ID = 1;

    public ActiveMemory(){
    	myStarterStates = new HashMap<Integer,TurtleState>();
        myStarterDisplay = new DisplayState();
        myActiveTurtleIDs = new ArrayList<Integer>();
        myCurrentlyActiveTurtleID = 1;
    }
    
	public int readTurtleNumber(){
    	return myStarterStates.size();
    }
	public void updateStartersWithStateList(List<IState> list){
    	for(IState state : list){
    		if(state.getStateType().equals("DisplayState")){
    			updateStarterDisplay(new DisplayState((DisplayState) state));
    		}
    		if(state.getStateType().equals("TurtleState")){
    			TurtleState turtState = (TurtleState) state;
    			updateStarterState(turtState.getId(), turtState);
    		}
    	}
    }
	public void updateCurrentlyActiveTurtleID(int id){
		this.myCurrentlyActiveTurtleID = id;
	}

	public void resetStarterStates(){
    	updateStarterDisplay(new DisplayState());
    	myActiveTurtleIDs.clear();
    	myStarterStates.clear();
        activateTurtle(STARTER_ID);
    	updateStarterState(STARTER_ID, new TurtleState(STARTER_ID));
    }
	public TurtleState readStarterState(Integer id) {
		return myStarterStates.get(id);
	}
	
	public List<Integer> getActiveTurtleIDs(){
		return myActiveTurtleIDs;
	}
	public void updateStarterState(Integer id, TurtleState builderState) {
		myStarterStates.put(id, builderState);
	}
	public void updateActiveTurtles(List<Integer> ids){
		myActiveTurtleIDs = new ArrayList<Integer>(ids);
		for(Integer id : myActiveTurtleIDs){
			if(!myStarterStates.containsKey(id)){
				myStarterStates.put(id, new TurtleState(id));
			}
		}
	}
	public void activateTurtle(Integer id){
		myActiveTurtleIDs.add(id);
		if(!myStarterStates.containsKey(id)){
			myStarterStates.put(id, new TurtleState(id));
		}
	}
	public void deactivateTurtle(Integer id){
		myActiveTurtleIDs.remove(id);
	}

	public DisplayState getStarterDisplay() {
		return myStarterDisplay;
	}

	public void updateStarterDisplay(DisplayState myStarterDisplay) {
		this.myStarterDisplay = myStarterDisplay;
	}

	public int getCurrentlyActiveTurtleID() {
		return myCurrentlyActiveTurtleID;
	}
	public TurtleState getCurrentlyActiveTurtle(){
		return myStarterStates.get(myCurrentlyActiveTurtleID);
	}
}
