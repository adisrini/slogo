package slogo.view;

public class PenColorEditItem extends ColorPickerContextProvider {
	private static final String PEN = "pen";
	public PenColorEditItem(View view) {
		super(view);
	}
	@Override
	public void handle() {
		setColorPickerWithContext(PEN);
	}
}