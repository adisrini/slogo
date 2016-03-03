package slogo.view;

import generic.Pair;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class CustomDialog extends Stage {
	
	protected Object context;
	
	public CustomDialog(Object context) {
		this.context = context;
	}
	
	protected abstract Pair<Scene, String> makeStage();
	
	public void setTheStage() {
		this.setScene(makeStage().getFirst());
		this.setTitle(makeStage().getLast());
		this.show();
	}

	protected abstract void setContext(String string);

}
