package slogo.view;

public class SaveFileItem extends MenuHandler {
	private View view;
	public SaveFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		CustomDialog dialog = new ScriptSaver(view);
        dialog.setTheStage();
	}
}