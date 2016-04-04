package slogo.view;

public class HistoryWindowItem extends MenuHandler {
	private View view;
	private static final String WINDOW_NAME = "History";
	public HistoryWindowItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		view.toggleWindow(WINDOW_NAME);
	}	
}