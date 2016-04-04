package slogo.view;

import java.awt.Desktop;

public class GetHelpHelpItem extends MenuHandler {
	private View view;
	public GetHelpHelpItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		if(Desktop.isDesktopSupported()) {
			view.launchGetHelpPage();
		}
	}
}