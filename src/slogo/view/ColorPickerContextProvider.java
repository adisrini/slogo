// This entire file is part of my masterpiece.
// Aditya Srinivasan

/**
 * This class is an example of how MenuHandler can be extended. This class is used since the menu items frequently need
 * to create a ColorPickerStage, with differing contexts. The class merely extends MenuHandler and provides its subclasses
 * with the additional method of setting the context. Classes that extend MenuHandler will not have access to this.
 */

package slogo.view;

public abstract class ColorPickerContextProvider extends MenuHandler {
	private View view;
	public ColorPickerContextProvider(View view) {
		super(view);
		this.view = view;
	}

	@Override
	public abstract void handle();
	
	protected void setColorPickerWithContext(String context) {
		CustomDialog dialog = new ColorPickerStage(view.getGraphicsWindow());
        dialog.setContext(context);
        dialog.setTheStage();
	}
}
