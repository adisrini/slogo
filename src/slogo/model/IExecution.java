package slogo.model;

import java.util.List;

interface IExecution {

    public List<IState> getCurrStateList();

    public List<IState> undoStateList ();

    public List<IState> redoStateList ();

    List<IState> getNextStateList();

    /**
     * Sets head to previous State Node
     * 
     * @return the state at that Node
     */
    public List<IState> getPrevStateList();

    /**
     * Add state to the end of the list
     */
    public void addStateList (List<IState> list);
    
    public boolean hasNextState ();

    public boolean hasCurrState ();

    public boolean hasPrevState ();
    /**
     * Returns list of all states
     */
    public List<List<IState>> getListOfStates ();
}
