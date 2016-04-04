// This entire file is part of my masterpiece.
// Aditya Srinivasan

/**
 * This class is an example of one that handles the action for a menu item. In this case, the menu item LoadTabFileItem
 * is given functionality. In this case, the general MenuHandler superclass is extended since this menu item requires 
 * functionality that is not common to other menu items. In the handle method, the functionality is implemented. It
 * can be noted that if another menu item were to do a very similar task, then a superclass could be created to extend
 * MenuHandler, and this class and the class of said menu item can both extend said superclass.
 */

package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoadTabFileItem extends MenuHandler {
	private View view;
	public LoadTabFileItem(View view) {
		super(view);
		this.view = view;
	}
	@Override
	public void handle() {
		Stage stage = new Stage();
        Group root = new Group();
        WorkspaceOptions workspaceOptions = new WorkspaceOptions();
        workspaceOptions.setOnAction(e -> {
            String index = workspaceOptions.getIndex();
            view.loadTab(index);
        });
        root.getChildren().add(workspaceOptions);
        stage.setScene(new Scene(root));
        stage.showAndWait();
	}	
}