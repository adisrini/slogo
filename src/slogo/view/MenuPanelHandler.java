// This entire file is part of my masterpiece.
// Aditya Srinivasan

/**
 * This file was modified to incorporate reflection as a technique for deciding what action to complete. Earlier,
 * a large if-else tree was used. The ID of the UI element clicked was the condition and the action was the method
 * to which it corresponded. This was not scalable, and as additional menu items were added, the design became infeasible.
 * I used reflection because it automated the process of deciding which method to complete in an efficient manner. 
 * Moreover, this refactoring allowed the class to be CLOSED to modification. Earlier, additional menu items were 
 * implemented by adding to the if-else tree. Now, a new class is created with the same ID as the menu item. It must 
 * extend MenuHandler and specify what to do in its handle() method. This helps solidify the Open-Closed principle.
 */

package slogo.view;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import slogo.model.SlogoException;

/**
 * This is the class that handles menu panel actions.
 * @author Aditya Srinivasan
 *
 */
public class MenuPanelHandler {

	private static final String ITEM_NOT_IMPLEMENTED_ERROR = "This item has not been implemented. Please do so.";
	private static final String PACKAGE_LOCATION = "slogo.view.";
	
    private ActionEvent e;
    private View view;

    /**
     * Instantiates the menu panel handler.
     * @param e
     * @param view
     * @param graphicsWindow
     */
    public MenuPanelHandler (ActionEvent e,
                             View view,
                             GraphicsWindow graphicsWindow) {
        this.e = e;
        this.view = view;
        
        handleEvent();
    }

    /**
     * Handles events from the menu panel.
     */
    private void handleEvent () {
        MenuItem menuItem = (MenuItem) e.getSource();
        MenuHandler menuHandler;
        Class<?> clazz;
		try {
			clazz = Class.forName(PACKAGE_LOCATION + menuItem.getId());
	        menuHandler = (MenuHandler) clazz.getConstructor(View.class).newInstance(this.view);
			menuHandler.getClass().getDeclaredMethod("handle").invoke(menuHandler);
		} catch (Exception ee) {
			throw new SlogoException(ITEM_NOT_IMPLEMENTED_ERROR);
		}
    }
}
