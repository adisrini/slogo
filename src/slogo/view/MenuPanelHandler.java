package slogo.view;

import java.awt.Desktop;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

public class MenuPanelHandler {
	
	private ActionEvent e;
	
	private View view;
	private GraphicsWindow graphicsWindow;

	public MenuPanelHandler(ActionEvent e, View view, GraphicsWindow graphicsWindow) {
		this.e = e;
		this.view = view;
		this.graphicsWindow = graphicsWindow;
		
		handleEvent();
		
	}
	
	private void handleEvent() {
		MenuItem menuItem = (MenuItem) e.getSource();
		CustomDialog dialog;		
		if (menuItem.getId().equals("BGColorEditItem")) {
			dialog = new ColorPickerStage(graphicsWindow);
			dialog.setContext("background");
			dialog.setTheStage();
		} else if (menuItem.getId().equals("PenColorEditItem")) {
			dialog = new ColorPickerStage(graphicsWindow);
			dialog.setContext("pen");
			dialog.setTheStage();
		} else if (menuItem.getId().equals("TurtleImageEditItem")) {
			dialog = new TurtlePicker(graphicsWindow);
			dialog.setTheStage();
		} else if (menuItem.getId().equals("GetHelpHelpItem")) {
			if (Desktop.isDesktopSupported()) {
				view.launchGetHelpPage();
			}
		} else if (menuItem.getId().equals("VariablesWindowItem")) {
			view.toggleWindow("Variables");
		} else if (menuItem.getId().equals("ConsoleWindowItem")) {
			view.toggleWindow("Console");
		} else if (menuItem.getId().equals("HistoryWindowItem")) {
			view.toggleWindow("History");
		} else if (menuItem.getId().equals("MethodsWindowItem")) {
			view.toggleWindow("Methods");
		} else if (menuItem.getId().equals("SaveFileItem")) {
			dialog = new ScriptSaver(view);
			dialog.setTheStage();
		} else if (menuItem.getId().equals("OpenFileItem")) {
			dialog = new ScriptLoader(view);
			dialog.setTheStage();
		} else if (menuItem.getId().equals("PrefFileItem")) {
			dialog = new Preferences(view);
			dialog.setTheStage();
		}
	}
	
}
