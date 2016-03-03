package slogo.view;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;
import generic.Pair;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import slogo.model.IState;


public class View extends Observable implements IView {

    private Scene myScene;

    private GraphicsWindow graphicsWindow;
    private History history;
    private Editor editor;
    private Console console;
    private Variables variables;
    private MenuPanel menuPanel;
    private ToolPanel toolPanel;
    private Methods methods;

    private Map<String, Pair<Region, Boolean>> windowsShowing;

    private static final String HELP_URL =
            "http://www.cs.duke.edu/courses/current/cps108/assign/03_slogo/commands.php";

    private static final double COL70 = 70;
    private static final double COL30 = 30;
    private static final double TWENTY_FOURTH = 100 / 24;

    private BorderPane root;
    private GridPane grid;

    private SplashScreen splashScreen;

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private StringProperty language;

    public View (EventHandler<ActionEvent> splashScreenComboBoxEvent,
                 Map<String, EventHandler<ActionEvent>> eventMap) {
        splashScreen = new SplashScreen(splashScreenComboBoxEvent);
        myScene = new Scene(splashScreen.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
        myScene.getStylesheets().add("/application/application.css");

        root = new BorderPane();

        graphicsWindow = new GraphicsWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        variables = new Variables(WINDOW_WIDTH, WINDOW_HEIGHT);
        history = new History(WINDOW_WIDTH, WINDOW_HEIGHT, splashScreenComboBoxEvent);
        editor = new Editor(WINDOW_WIDTH, WINDOW_HEIGHT);
        console = new Console(WINDOW_WIDTH, WINDOW_HEIGHT);

        windowsShowing = new HashMap<String, Pair<Region, Boolean>>();

        methods = new Methods(WINDOW_WIDTH, WINDOW_HEIGHT, splashScreenComboBoxEvent);

        // TODO: construct instances of MenuBar and ToolBar
        menuPanel = new MenuPanel( (ActionEvent e) -> {
            new MenuPanelHandler(e, this, graphicsWindow);
        });
        toolPanel = new ToolPanel(eventMap);

        language = new SimpleStringProperty();
    }

    void toggleWindow (String window) {
        clearRightPanel();

        for (String win : windowsShowing.keySet()) {
            if (win.contains(window)) {
                Pair<Region, Boolean> windowInfo = windowsShowing.get(win);
                windowsShowing.put(win, new Pair<Region, Boolean>(windowInfo.getFirst(),
                                                                  !windowInfo.getLast()));
            }
        }

        setRightPanels(windowsShowing);

    }

    void launchGetHelpPage () {
        try {
            Desktop.getDesktop().browse(new URI(HELP_URL));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            alert("ERROR: Desktop browsing not supported");
        }
    }

    public void changeToMainScene () {
        establishGridPane();

        root.setCenter(grid);
        root.setTop(makeTopPanels());

        myScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        myScene.getStylesheets().add("/application/application.css");

    }

    private VBox makeTopPanels () {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuPanel, toolPanel);
        return vbox;
    }

    /**
     * Creates the 5 different panels that are used to hold different windows of
     * the program
     */
    private void establishGridPane () {
        grid = new GridPane();
        ColumnConstraints leftColumn = new ColumnConstraints();
        leftColumn.setPercentWidth(COL70);
        ColumnConstraints rightColumn = new ColumnConstraints();
        rightColumn.setPercentWidth(COL30);
        grid.getColumnConstraints().addAll(leftColumn, rightColumn);

        for (int i = 0; i < 24; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(TWENTY_FOURTH);
            grid.getRowConstraints().add(row);
        }

        List<Region> windows = Arrays.asList(variables, methods, history, console);

        for (Region window : windows) {
            windowsShowing.put(window.getClass().toString(),
                               new Pair<Region, Boolean>(window, true));
        }

        setTopLeft(tabify(graphicsWindow, false));
        setBottomLeft(tabify(editor, false));
        setRightPanels(windowsShowing);

    }

    private TabPane tabify (Region panel, boolean closable) {

        TabPane tabPane = new TabPane();
        String[] split = panel.getClass().toString().split("\\.");
        String title = split[split.length - 1];
        Tab tab = new Tab(title);
        tab.setClosable(closable);
        tab.setContent(panel);

        if (closable) {
            tab.setOnClosed(e -> {
                toggleWindow(title);
            });

        }

        tabPane.getTabs().add(tab);

        return tabPane;
    }

    private void setTopLeft (Region panel) {
        grid.add(panel, 0, 0);
        GridPane.setRowSpan(panel, 18);
    }

    private void setBottomLeft (Region panel) {
        grid.add(panel, 0, 18);
        GridPane.setRowSpan(panel, 6);
    }

    private void setRightPanels (Map<String, Pair<Region, Boolean>> windowsShowing) {
        int numberOfPanels = 0;

        List<String> activeWindows = windowsShowing.keySet().stream()
                .filter(s -> windowsShowing.get(s).getLast())
                .collect(Collectors.toList());

        numberOfPanels = activeWindows.size();
        System.out.println(numberOfPanels);

        for (String window : windowsShowing.keySet()) {
            if (windowsShowing.get(window).getLast()) {
                TabPane tp = tabify(windowsShowing.get(window).getFirst(), true);
                grid.addColumn(1, tp);
                GridPane.setRowSpan(tp, 24 / numberOfPanels);
            }
        }
    }

    private void clearRightPanel () {
        List<Node> nodesToRemove = new ArrayList<Node>();
        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (GridPane.getColumnIndex(grid.getChildren().get(i)) == 1) {
                nodesToRemove.add(grid.getChildren().get(i));
            }
        }

        for (int i = 0; i < nodesToRemove.size(); i++) {
            grid.getChildren().remove(nodesToRemove.get(i));
        }

    }

    @Override
    public void updateState (IState state) {
        // pass the states to the graphics window
        graphicsWindow.update(state);
    }

    @Override
    public Scene getScene () {
        return myScene;
    }

    @Override
    public StringBuilder execute () {

        StringBuilder editorText = editor.getEditorText();

        // Create new execution number (increase graphicsWindow execution
        // counter) every time this method
        // is executed

        history.update(new Pair<String, String>(editorText.toString()));

        Text text = new Text(editorText.toString());
        text.setFill(Color.WHITE);

        console.printText(text);

        return editorText;
    }

    @Override
    public void undoState (IState state) {
        graphicsWindow.undo(state);
    }

    @Override
    public void undoExecution (IState state) {
        graphicsWindow.undo(state);
    }

    public void redoExecution (IState state) {
        updateState(state);
    }

    @Override
    public void onSave (String methodName) {
        String editorText = editor.getEditorText().toString();

        // history.update(new Pair<String, String>(editorText));
        methods.addMethodToTable(methodName, editorText);
    }

    /**
     * Produces buttons with given name and actions
     * 
     * @param name:
     *        name
     * @param actionToExecute:
     *        Event to execute when button action is fired
     * @return Button with name and action specified
     */
    private Button buttonFactory (String name, EventHandler<ActionEvent> actionToExecute) {
        Button button = new Button(name);
        button.setOnAction(actionToExecute);

        return button;
    }

    /**
     * 1. Update Variables
     */
    public void onComplete () {
        variables.updateVariables();
    }

    public void displayError (String message) {
        // TODO Auto-generated method stub
        Text error = new Text(message);
        error.setFill(Color.RED);
        console.printText(error);
    }

    void setTurtleSpeed (double speed) {
        graphicsWindow.setSpeed(speed);
    }

    public double getFrameRate (IState state) {
        return graphicsWindow.getFrameRate(state);
    }

    public void setLanguage (String language) {
        this.language.set(language);

        setChanged();

        System.out.println("Has changed: " + hasChanged());

        notifyObservers(language);

        menuPanel.setLanguage(language);
    }

    public void alert (String message) {
        // TODO Auto-generated method stub
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void reset () {
        graphicsWindow.reset();
    }

    public StringProperty getLanguage () {
        return language;
    }

    public void setEditorText (Pair<String, String> fileContents) {
        // TODO Auto-generated method stub
        editor.setEditorText(fileContents.getLast());

        // move string to resource file or constant
        Text scriptLoaded = new Text("The file " + fileContents.getFirst() + " was loaded.");
        scriptLoaded.setFill(Color.CADETBLUE);

        console.printText(scriptLoaded);
    }

}
