package slogo.view;

public class ConsoleWindowItem extends Toggler {
	private static final String WINDOW_NAME = "Console";
	public ConsoleWindowItem(View view) {
		super(view);
	}
	@Override
	public void handle() {
		toggleWindow(WINDOW_NAME);
	}	
}