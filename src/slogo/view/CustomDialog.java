package slogo.view;

import generic.Pair;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is used to establish popups easily.
 * It is provided with a context that it can access.
 *
 * @author Aditya Srinivasan, Arjun Desai
 */
public abstract class CustomDialog extends Stage {
	
	protected Object context;
	
	/**
	 * Initializes the dialog with a context that
	 * it can access.
	 * @param context
	 */
	public CustomDialog(Object context) {
		this.context = context;
	}
	
	/**
	 * Lets the subclass CustomDialog define what to
	 * populate the stage with.
	 * @return
	 */
	protected abstract Pair<Scene, String> makeStage();
	
	/**
	 * Called after making the stage to show the dialog
	 */
	void setTheStage() {
		this.setScene(makeStage().getFirst());
		this.setTitle(makeStage().getLast());
		this.show();
	}

	/**
	 * Allows the context to be set
	 * @param string
	 */
	protected abstract void setContext(String string);

}
