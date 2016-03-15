package slogo.tabmanager;

import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.view.TabPreference;

public class TabPackage {
	
	private int id;
	private Controller myController;

	public TabPackage(Controller c, int id) {
		this.myController = c;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}	
	
	public Controller getController() {
		return this.myController;
	}

}
