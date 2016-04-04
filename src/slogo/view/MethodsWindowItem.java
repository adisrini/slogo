package slogo.view;

public class MethodsWindowItem extends MenuHandler {
	private View view;
	private static final String WINDOW_NAME = "Methods";
	public MethodsWindowItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		view.toggleWindow(WINDOW_NAME);
	}	
}