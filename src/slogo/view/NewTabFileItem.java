package slogo.view;

public class NewTabFileItem extends MenuHandler {
	private View view;
	public NewTabFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		view.askForNewTab();
	}	
}