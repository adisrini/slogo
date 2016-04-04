package slogo.view;

public class SaveTabFileItem extends MenuHandler {
	private View view;
	public SaveTabFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		WorkspaceSaver workspaceSaver = new WorkspaceSaver(view);
        workspaceSaver.showAndWait();
	}
}