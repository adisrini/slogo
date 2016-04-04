package slogo.view;

public abstract class Toggler extends MenuHandler {

	private View view;
	
	public Toggler(View view) {
		super(view);
		this.view = view;
	}

	@Override
	public abstract void handle();
	
	protected void toggleWindow(String region) {
		view.toggleWindow(region);
	}
}
