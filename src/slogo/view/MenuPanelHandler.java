package slogo.view;

import java.awt.Desktop;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * This is the class that handles menu panel actions.
 * @author Aditya Srinivasan
 *
 */
public class MenuPanelHandler {

    private ActionEvent e;
    
    private static final String BG_COLOR = "BGColorEditItem";
    private static final String BG = "background";
    private static final String PEN_COLOR = "PenColorEditItem";
    private static final String PEN = "pen";
    private static final String TURTLE_IMAGE = "TurtleImageEditItem";
    private static final String HELP = "GetHelpHelpItem";
    private static final String VARIABLES_WINDOW = "VariablesWindowItem";
    private static final String VARIABLES = "Variables";
    private static final String CONSOLE_WINDOW = "ConsoleWindowItem";
    private static final String CONSOLE = "Console";
    private static final String HISTORY_WINDOW = "HistoryWindowItem";
    private static final String HISTORY = "History";
    private static final String METHODS_WINDOW = "MethodsWindowItem";
    private static final String METHODS = "Methods";
    private static final String SAVE_FILE = "SaveFileItem";
    private static final String OPEN_FILE = "OpenFileItem";
    private static final String PREF_FILE = "PrefFileItem";
    private static final String WS_FILE = "WorkspaceFileItem";
    private static final String NEW_TAB_FILE = "NewTabFileItem";
    private static final String LOAD_TAB_FILE = "LoadTabFileItem";
    private static final String SAVE_TAB_FILE = "SaveTabFileItem";

    private View view;
    private GraphicsWindow graphicsWindow;

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
        this.graphicsWindow = graphicsWindow;

        handleEvent();

    }

    /**
     * Handles events from the menu panel.
     */
    private void handleEvent () {
        MenuItem menuItem = (MenuItem) e.getSource();
        if (menuItem.getId().equals(BG_COLOR)) { openColor(BG); }
        else if (menuItem.getId().equals(PEN_COLOR)) { openColor(PEN); }
        else if (menuItem.getId().equals(TURTLE_IMAGE)) { openTurtle(); }
        else if (menuItem.getId().equals(HELP)) {
            if (Desktop.isDesktopSupported()) { view.launchGetHelpPage(); }}
        else if (menuItem.getId().equals(VARIABLES_WINDOW)) { view.toggleWindow(VARIABLES);}
        else if (menuItem.getId().equals(CONSOLE_WINDOW)) { view.toggleWindow(CONSOLE); }
        else if (menuItem.getId().equals(HISTORY_WINDOW)) { view.toggleWindow(HISTORY); }
        else if (menuItem.getId().equals(METHODS_WINDOW)) { view.toggleWindow(METHODS); }
        else if (menuItem.getId().equals(SAVE_FILE)) { openScriptSaver(); }
        else if (menuItem.getId().equals(OPEN_FILE)) { openScriptLoader(); }
        else if (menuItem.getId().equals(PREF_FILE)) { openPrefs(); }
        else if (menuItem.getId().equals(WS_FILE)) { openWorkspace(); }
        else if (menuItem.getId().equals(NEW_TAB_FILE)) { view.askForNewTab(); }
        else if (menuItem.getId().equals(LOAD_TAB_FILE)) { loadTab(); }
        else if (menuItem.getId().equals(SAVE_TAB_FILE)) { openWorkspaceSaver(); }
    }
    
    private void openColor(String context) {
    	CustomDialog dialog = new ColorPickerStage(graphicsWindow);
        dialog.setContext(context);
        dialog.setTheStage();
    }
    
    private void openTurtle() {
        CustomDialog dialog = new TurtlePicker(graphicsWindow);
        dialog.setTheStage();
    }

    private void openScriptSaver() {
    	CustomDialog dialog = new ScriptSaver(view);
        dialog.setTheStage();
    }
    
    private void openScriptLoader() {
    	CustomDialog dialog = new ScriptLoader(view);
        dialog.setTheStage();
    }
    
    private void openPrefs() {
    	CustomDialog dialog = new Preferences(view);
        dialog.setTheStage();
    }
    
    private void openWorkspace() {
    	CustomDialog dialog = new Workspace(view);
        dialog.setTheStage();
    }
    
    private void loadTab() {
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
    
    private void openWorkspaceSaver() {
        WorkspaceSaver workspaceSaver = new WorkspaceSaver(view);
        workspaceSaver.showAndWait();
    }
    
}
