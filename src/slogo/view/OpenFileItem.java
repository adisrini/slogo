package slogo.view;

public class OpenFileItem extends MenuHandler {
	private View view;
	public OpenFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		CustomDialog dialog = new ScriptLoader(view);
        dialog.setTheStage();
	}	
}