package slogo.model;
import java.util.List;

/**
 * The Class Execution.
 * Contains a linked list of IStates that can 
 * be navigated through.
 */
class Execution implements IExecution {
   	
    private DoublyLinkedList<List<IState>> myStates;

    /**
     * Instantiates a new execution.
     */
    public Execution () {
        myStates = new DoublyLinkedList<List<IState>>();
    }
    
    /**
     * Returns the current list of 
     * states held by the execution
     * 
     * @return current List of States
     */
    @Override
    public List<IState> getCurrStateList() {
        return myStates.getCurr();
    }

    /**
     * Undoes the current state list
     * 
     * @return list of IStates
     */
    public List<IState> undoStateList () {
        return myStates.undo();
    }

    /**
     * Returns to the previously undone
     * State
     * 
     * @return list of IStates
     */
    public List<IState> redoStateList () {
        return myStates.redo();
    }

    /**
     * Add state to the end of the list
     */
    public void addStateList (List<IState> list) {
        myStates.add(list);
    }

    /**
     * Checks to see if the Execution
     * has a next State
     * 
     * @return true if a next State is present
     */
    public boolean hasNextState () {
        return myStates.hasNext();
    }

    /**
     * Checks to see if the Execution
     * has a current State
     */
    public boolean hasCurrState () {
        return !myStates.isEmpty();
    }

    /**
     * Checks to see if the Execution
     * has a previous State
     */
    public boolean hasPrevState () {
        return myStates.hasPrev();
    }

    /**
     * Returns list of all states
     */
    public List<List<IState>> getListOfStates () {
        return myStates.toList();
    }
}
