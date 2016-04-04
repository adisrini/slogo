package slogo.model;

import java.util.List;

/**
 * The Interface IExecution.
 */
interface IExecution {

    /**
     * Gets the curr state list.
     *
     * @return the curr state list
     */
    public List<IState> getCurrStateList();

    /**
     * Undo state list.
     *
     * @return the list
     */
    public List<IState> undoStateList ();

    /**
     * Redo state list.
     *
     * @return the list
     */
    public List<IState> redoStateList ();

    /**
     * Add state to the end of the list.
     *
     * @param list the list
     */
    public void addStateList (List<IState> list);
    
    /**
     * Checks for next state.
     *
     * @return true, if successful
     */
    public boolean hasNextState ();

    /**
     * Checks for curr state.
     *
     * @return true, if successful
     */
    public boolean hasCurrState ();

    /**
     * Checks for prev state.
     *
     * @return true, if successful
     */
    public boolean hasPrevState ();
    
    /**
     * Returns list of all states.
     *
     * @return the list of states
     */
    public List<List<IState>> getListOfStates ();
}
