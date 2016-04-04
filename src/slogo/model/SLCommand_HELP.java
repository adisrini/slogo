package slogo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import generic.Pair;

public class SLCommand_HELP implements IFunction{
	String myFunctionName;
	private static final String METHODHELP_RESOURCE_LOCATION = "resources/general/MethodHelp";
    private static final String COMMAND_NOT_CONTAINED_IN_HELP_ERROR = "CommandNotContainedInHelpError";

	@Override
	public void createFunction(IParser p, IMemory m, Map<String, Double> scope) {
		myFunctionName = p.parseCommandName();
	}

	@Override
	public double executeFunction(IMemory m, Map<String, Double> scope) {
		ResourceBundle myCheatSheet = ResourceBundle.getBundle(METHODHELP_RESOURCE_LOCATION);

		DisplayState dispstate = new DisplayState(m.getActiveMemory().getStarterDisplay());
		try { dispstate.setConsoleMessage(myCheatSheet.getString(myFunctionName.toLowerCase()));}
		catch (RuntimeException e) { helpError(COMMAND_NOT_CONTAINED_IN_HELP_ERROR, myFunctionName);}
		List<IState> list = new ArrayList<IState>();
		list.add(dispstate);
		m.getStorageMemory().writeStates(list);
		m.getActiveMemory().updateStarterDisplay(dispstate);
		
		return 0;
	}
	private void helpError(String errorType, String specificMessage){
		throw new SlogoException(new Pair<String, String>(errorType,specificMessage));
	}
}
