package slogo.view;

public class TurtleImageEditItem extends MenuHandler {
	private View view;
	public TurtleImageEditItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		CustomDialog dialog = new TurtlePicker(view.getGraphicsWindow());
        dialog.setTheStage();
	}	
}