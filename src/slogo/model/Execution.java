package slogo.model;

import java.util.List;


class Execution implements IExecution {
    /**
     * Contains a linked list of States that can
     * be navigated through
     */
    private DoublyLinkedList<IState> myStates;

    public Execution () {
        myStates = new DoublyLinkedList<IState>();
    }

    @Override
    public IState getCurrState () {
        return myStates.getCurr();
    }

    public IState undoState () {
        return myStates.undo();
    }

    public IState redoState () {
        return myStates.redo();
    }

    @Override
    public IState getNextState () {
        return myStates.getNext();
    }

    @Override
    /**
     * Sets head to previous State Node
     * 
     * @return the state at that Node
     */
    public IState getPrevState () {
        return myStates.getPrev();
    }

    @Override
    /**
     * Add state to the end of the list
     */
    public void addState (IState s) {
        myStates.add(s);
    }

    @Override
    public void createFunctions (String input) {

    }

    @Override
    public void executeFunctions () {
        // TODO Auto-generated method stub

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
    public List<IState> getListOfStates () {
        return myStates.toList();
    }
}
