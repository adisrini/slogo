package slogo.view;

public class PrefFileItem extends MenuHandler {
	private View view;
	public PrefFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		CustomDialog dialog = new Preferences(view);
        dialog.setTheStage();
	}	
}