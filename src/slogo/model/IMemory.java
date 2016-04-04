package slogo.model;

import java.util.List;

/**
 * The Interface IMemory.
 */
interface IMemory {
	/**
	 * Update currently active tab.
	 *
	 * @param currentlyActiveID the currently active id
	 */
	void updateCurrentlyActiveTab(int currentlyActiveID);
	
	/**
	 * Reset current tab.
	 */
	void resetCurrentTab();
	
	/**
	 * Gets the storage memory.
	 *
	 * @return the storage memory
	 */
	StorageMemory getStorageMemory();
	
	/**
	 * Gets the active memory.
	 *
	 * @return the active memory
	 */
	ActiveMemory getActiveMemory();
	/**
	 * Reads a future Execution.
	 *
	 * @return next Execution
	 */
	List<List<IState>> redoExecution();
	
	/**
	 * Goes back one Execution.
	 *
	 * @return past Execution
	 */	
	List<IState> undoExecution();
	
	/**
	 * Redo state.
	 *
	 * @return the list
	 */
	List<IState> redoState();
	
	/**
	 * Undo state.
	 *
	 * @return the list
	 */
	List<IState> undoState();
}
