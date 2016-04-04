package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class ReturnValue.
 */
abstract class ReturnValue extends GeneralFunction{
	
	private double myValue = 0;
	
	/**
	 * Retrieves a return value from the extending function
	 * and adds it to the display state console message
	 * 
	 * @return return value
	 */
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		myValue = getValue(m,scope);
		DisplayState dispstate = new DisplayState(m.getActiveMemory().getStarterDisplay());
		dispstate.setConsoleMessage(""+myValue);
		List<IState> list = new ArrayList<IState>();
		list.add(dispstate);
		m.getStorageMemory().writeStates(list);
		m.getActiveMemory().updateStarterDisplay(dispstate);
		return myValue;
	}
	
	/**
	 * Gets the return value.
	 *
	 * @param m the m
	 * @param scope the scope
	 * @return the value
	 */
	protected abstract double getValue(IMemory m, Map<String, Double> scope);
}
