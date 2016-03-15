package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SLCommand_HELP implements IFunction{
	String myFunctionName;
	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		myFunctionName = p.parseCommandName();
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		DisplayState dispstate = new DisplayState(m.getActiveMemory().getStarterDisplay());
		dispstate.setConsoleMessage();
		List<IState> list = new ArrayList<IState>();
		list.add(dispstate);
		m.getStorageMemory().writeStates(list);
		m.getActiveMemory().updateStarterDisplay(dispstate);
	}

}
