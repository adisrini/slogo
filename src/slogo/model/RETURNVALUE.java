package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class ReturnValue extends GeneralFunction{
	private double myValue = 0;
	
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		myValue = getValue(m,scope);
		DisplayState dispstate = new DisplayState(m.getActiveMemory().getStarterDisplay());
		dispstate.setConsoleMessage(""+myValue);
		dispstate.setEditorCode("this is display code");
		List<IState> list = new ArrayList<IState>();
		list.add(dispstate);
		m.getStorageMemory().writeStates(list);
		m.getActiveMemory().updateStarterDisplay(dispstate);
		return myValue;
	}
	protected abstract double getValue(IMemory m, Map<String, Double> scope);
}
