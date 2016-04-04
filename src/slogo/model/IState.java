package slogo.model;

/**
 * The Interface IState.
 */
public interface IState {
	
	/**
	 * Gets the state type.
	 *
	 * @return the state type
	 */
	String getStateType();
	
	/**
	 * Sets the editor code.
	 * Used for highlighting purposes
	 *
	 * @param code the new editor code
	 */
	void setEditorCode(String code);
	
	/**
	 * Gets the editor code.
	 * Used for highlighting purposes
	 * 
	 * @return the editor code
	 */
	String getEditorCode();
	
}
