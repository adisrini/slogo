// This entire file is part of my masterpiece.
// Aditya Srinivasan

/**
 * This class is an example of one that handles the action for a menu item. In this case, the menu item BGColorEditItem
 * is given functionality. In this case, ColorPickerContextProvider is extended since this menu item establishes a
 * ColorPickerStage. In the handle method, the protected context-setting method from ColorPickerContextProvider is called
 * and the appropriate context is given. This is all that is needed to do to implement its functionality.
 */

package slogo.view;

public class BGColorEditItem extends ColorPickerContextProvider {
	private static final String BG = "background";
	public BGColorEditItem(View view) {
		super(view);
	}
	@Override
	public void handle() {
		setColorPickerWithContext(BG);
	}
}