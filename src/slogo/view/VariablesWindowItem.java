package slogo.view;

public class VariablesWindowItem extends MenuHandler {
	private View view;
	private static final String WINDOW_NAME = "Variables";
	public VariablesWindowItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		view.toggleWindow(WINDOW_NAME);
	}	
}