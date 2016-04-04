// This entire file is part of my masterpiece.
// Aditya Srinivasan

/**
 * This class is accessed by reflection from MenuPanelHandler. It is an abstract class with one method called
 * handle(). It is designed to be inherited by other classes, namely the classes mentioned in the description
 * of MenuPanelHandler which are used to implement menu item functionality. This class is closed to modification
 * and open to extension. The source code is not intended to be modified, but other abstract classes can
 * extend this class if additional functionality is desired. This can be seen in the classes Toggler and 
 * ColorPickerContextProvider.
 */

package slogo.view;

public abstract class MenuHandler {	
	@SuppressWarnings("unused")
	private View view;
	
	public MenuHandler(View v) {
		this.view = v;
	}
	
	public abstract void handle();
}
