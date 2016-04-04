package slogo.view;

public class WorkspaceFileItem extends MenuHandler {
	private View view;
	public WorkspaceFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		CustomDialog dialog = new Workspace(view);
        dialog.setTheStage();
	}	
}