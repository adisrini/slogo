package slogo.model;
import java.util.List;


class Execution implements IExecution {
    /**
     * Contains a linked list of States that can
     * be navigated through
     */
    private DoublyLinkedList<List<IState>> myStates;

    public Execution () {
        myStates = new DoublyLinkedList<List<IState>>();
    }
    
    @Override
    public List<IState> getCurrStateList() {
        return myStates.getCurr();
    }

    public List<IState> undoStateList () {
        return myStates.undo();
    }

    public List<IState> redoStateList () {
        return myStates.redo();
    }

    @Override
    public List<IState> getNextStateList() {
        return myStates.getNext();
    }

    @Override
    /**
     * Sets head to previous State Node
     * 
     * @return the state at that Node
     */
    public List<IState> getPrevStateList() {
        return myStates.getPrev();
    }

    @Override
    /**
     * Add state to the end of the list
     */
    public void addStateList (List<IState> list) {
        myStates.add(list);
    }

    public boolean hasNextState () {
        return myStates.hasNext();
    }

    public boolean hasCurrState () {
        return !myStates.isEmpty();
    }

    public boolean hasPrevState () {
        return myStates.hasPrev();
    }

    @Override
    /**
     * Returns list of all states
     */
    public List<List<IState>> getListOfStates () {
        return myStates.toList();
    }
}
