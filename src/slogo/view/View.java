package slogo.view;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
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
import slogo.model.IDisplayState;
import slogo.model.IState;
import slogo.model.ITurtleState;
import slogo.xml.XMLReader;

/**
 * 
 * This is the central GUI class
 * 
 * This class controls the splash screen and manages the different panes that the user can see
 * The action events are also managed through the view
 * 
 *
 */
public class View extends Observable implements Observer, IView {

	private static final String DEFAULT_XML_PATH = "./src/resources/preferences/PreferencesXML.xml";

	private Scene myScene;

	private GraphicsWindow graphicsWindow;
	private History history;
	private Editor displayedEditor;
	private Console console;
	private Variables variables;
	private MenuPanel menuPanel;
	private MenuPanelHandler menuHandler;
	private ToolPanel toolPanel;
	private Methods methods;

	private Map<String, Pair<Region, Boolean>> windowsShowing;

	private static final String HELP_URL = "http://www.cs.duke.edu/courses/current/cps108/assign/03_slogo/commands.php";

	private static final double COL70 = 70;
	private static final double COL30 = 30;
	private static final double TWENTY_FOURTH = 100 / 24;

	private BorderPane root;
	private GridPane grid;

	private SplashScreen splashScreen;

	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;

	private StringProperty language;
	private StringBuilder internalCommand;
	private InternalEditor internalEditor;
	private Boolean tabRequested;
	private String workspaceToLoad;

	private double mySpeed;
	private int defaultNumTurtles;
	private int defaultBackgroundColorIndex;
	private int id;
	
	/**
	 * Constructor to initialize View 
	 * @param id: id correspond to the tab
	 * @param splashScreenComboBoxEvent: the splash screen event
	 * @param eventMap: map of events that is passed to menu handler and toolbar
	 * @param tabPreference: either load workspace or load default
	 */
	public View(int id, EventHandler<ActionEvent> splashScreenComboBoxEvent,
			Map<String, EventHandler<ActionEvent>> eventMap, TabPreference tabPreference) {
		this.id = id;

		splashScreen = new SplashScreen(splashScreenComboBoxEvent);
		myScene = new Scene(splashScreen.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
		myScene.getStylesheets().add("/application/application.css");

		language = new SimpleStringProperty();
		menuPanel = new MenuPanel((ActionEvent e) -> {
			initializeMenuHandler(e);
		});

		createTab(tabPreference.getIndex());

		root = new BorderPane();

		graphicsWindow = new GraphicsWindow(WINDOW_WIDTH, WINDOW_HEIGHT, defaultNumTurtles);
		graphicsWindow.setBackgroundColorIndex(defaultBackgroundColorIndex);
		variables = new Variables(WINDOW_WIDTH, WINDOW_HEIGHT);
		history = new History(WINDOW_WIDTH, WINDOW_HEIGHT, splashScreenComboBoxEvent);
		displayedEditor = new Editor(WINDOW_WIDTH, WINDOW_HEIGHT);

		console = new Console(WINDOW_WIDTH, WINDOW_HEIGHT);

		windowsShowing = new HashMap<String, Pair<Region, Boolean>>();

		methods = new Methods(WINDOW_WIDTH, WINDOW_HEIGHT, splashScreenComboBoxEvent);

		// TODO: construct instances of MenuBar and ToolBar

		toolPanel = new ToolPanel(eventMap, graphicsWindow);

		internalCommand = new StringBuilder();

		internalEditor = InternalEditor.getInstance();
		internalEditor.addObserver(this);

		establishGridPane();

		mySpeed = 500;
	}
	
	/**
	 * 
	 * @return GridPane
	 */
	public GridPane getGrid() {
		return grid;
	}
	
	/**
	 * Initialize Menu Handler
	 * @param e: Action Event read by MenuHandler
	 */
	private void initializeMenuHandler(ActionEvent e) {
		menuHandler = new MenuPanelHandler(e, this, graphicsWindow);
	}
	
	/**
	 * Allows user to toggle Variables, History, Console, and Methods windows
	 * @param window: window to toggle
	 */
	void toggleWindow(String window) {

		clearRightPanel();

		for (String win : windowsShowing.keySet()) {
			if (win.contains(window)) {
				Pair<Region, Boolean> windowInfo = windowsShowing.get(win);
				windowsShowing.put(win, new Pair<Region, Boolean>(windowInfo.getFirst(), !windowInfo.getLast()));
			}
		}

		setRightPanels(windowsShowing);

	}
	
	/**
	 * Launch the Help Page
	 */
	void launchGetHelpPage() {
		try {
			Desktop.getDesktop().browse(new URI(HELP_URL));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			alert("ERROR: Desktop browsing not supported");
		}
	}
	
	/**
	 * Change screen to Workspace GUI flow
	 */
	public void changeToMainScene() {

		root.setTop(makeTopPanels());

		myScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		myScene.getStylesheets().add("/application/application.css");

	}      
	
	/**
	 * Set Center node in the screen
	 * @param region: item to set in center of root
	 */
	public void setCenter(Region region) {
		root.setCenter(region);
	}
	
	/**
	 * 
	 * @return VBox of objects in topPanel 
	 */
	private VBox makeTopPanels() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(menuPanel);
		return vbox;
	}

	public ToolPanel getToolPanel() {
		return this.toolPanel;
	}

	/**
	 * Creates the 5 different panels that are used to hold different windows of
	 * the program
	 */
	private void establishGridPane() {
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
			windowsShowing.put(window.getClass().toString(), new Pair<Region, Boolean>(window, true));
		}

		setTopLeft(tabify(graphicsWindow, false));
		setBottomLeft(tabify(displayedEditor, false));
		setRightPanels(windowsShowing);

	}
	
	/**
	 * create a TabPane that allows the user to see different windows
	 * @param panel: window to display on the screen
	 * @param closable: set closable
	 * @return
	 */
	private TabPane tabify(Region panel, boolean closable) {

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
	
	/**
	 * Set panel at the top left of the screen
	 * @param panel
	 */
	private void setTopLeft(Region panel) {
		grid.add(panel, 0, 0);
		GridPane.setRowSpan(panel, 18);
	}
	
	/**
	 * Set Panel at Bottom Left of the Screen
	 * @param panel
	 */
	private void setBottomLeft(Region panel) {
		grid.add(panel, 0, 18);
		GridPane.setRowSpan(panel, 6);
	}
	
	/**
	 * Set windows on right side of screen
	 * @param windowsShowing
	 */
	private void setRightPanels(Map<String, Pair<Region, Boolean>> windowsShowing) {
		int numberOfPanels = 0;

		List<String> activeWindows = windowsShowing.keySet().stream().filter(s -> windowsShowing.get(s).getLast())
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
	
	/**
	 * clear Right panel area
	 */
	private void clearRightPanel() {
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
	
	/**
         * Method called by Controller to update the state of the turtle, whose id corresponds to that in states
         * The states are passed to the graphicWindow which updates the turtles that correspond to the state
         * based on the id
         * 
         * 
         * @param: states: list of states passed from controller, and created by the model
         * 
         */
	@Override
	public void updateState(List<IState> states) {
		// pass the states to the graphics window
		for (IState state : states) {
			if (state instanceof IDisplayState) {
				Text text = new Text(((IDisplayState) state).getConsoleMessage());
				text.setFill(Color.WHITE);
				console.printText(text);
			}
			graphicsWindow.update(state);
		}
	}
	
	/**
	 * Returns current scene of view
	 */
	@Override
	public Scene getScene() {
		return myScene;
	}
	
	/**
	 * Method is executed when controller RUN action is called
	 * 
	 * 1. Gets text that user inputted into Editor window
	 * 2. Updates the history with the text that the user executed
	 * 3. Prints white text to the console
	 * 4. Returns text from editor to the controller to change into understandable keys and send to model
	 */
	@Override
	public StringBuilder execute() {

		StringBuilder editorText = displayedEditor.getEditorText();
		history.update(new Pair<String, String>(editorText.toString()));

		Text text = new Text(editorText.toString());
		text.setFill(Color.WHITE);

		console.printText(text);

		return editorText;
	}
	
	/**
	 * Method executed when changes to program made graphically
	 * The classes that manage where the user can make changes to the program graphically have an
	 * instance of internalEditor, a singleton class, and update the text
	 * 
	 * @return text from InternalEditor to Controller
	 */
	public StringBuilder executeInternal() {
		return internalEditor.getEditorText();
	}
	
	/**
	 * Undo states of turtles until the state corresponds to the one in the list
	 */
	@Override
	public void undo(List<IState> states) {
		for (IState state : states) {
			if (state instanceof ITurtleState) {
				graphicsWindow.undo((ITurtleState) state);
			}
		}
	}

	@Override
	public void onSave(String methodName) {
		String editorText = displayedEditor.getEditorText().toString();

		// history.update(new Pair<String, String>(editorText));
		methods.addMethodToTable(methodName, editorText);
	}

	/**
	 * Produces buttons with given name and actions
	 * 
	 * @param name:
	 *            name
	 * @param actionToExecute:
	 *            Event to execute when button action is fired
	 * @return Button with name and action specified
	 */
	private Button buttonFactory(String name, EventHandler<ActionEvent> actionToExecute) {
		Button button = new Button(name);
		button.setOnAction(actionToExecute);

		return button;
	}

	/**
	 * 1. Update Variables
	 */
	public void onComplete() {
		variables.updateVariables();
	}
	
	/**
	 * Display error to the user on the Console
	 * @param message: error to display
	 */
	public void displayError(String message) {
		// TODO Auto-generated method stub
		Text error = new Text(message);
		error.setFill(Color.RED);
		console.printText(error);
	}
	
	/**
	 * Set the speed of he turtle in the graphics Window and notify the Controller of change to update
	 * animation
	 * @param speed: speed of turtle to set
	 */
	void setTurtleSpeed(double speed) {
		this.mySpeed = speed;
		graphicsWindow.setSpeed(speed);

		setChanged();
		notifyObservers(mySpeed);

	}
	
	/**
	 * Get the current frame rate for the animation from the graphics Window
	 * @param state: rate from state
	 * @return Frame Rate
	 */
	public double getFrameRate(ITurtleState state) {

		return graphicsWindow.getFrameRate(state);
	}
	
	/**
	 * Set the language of the view and notify the controller
	 * 
	 * @param language: sets the language to the value of language
	 */
	public void setLanguage(String language) {
		this.language.setValue(language);

		setChanged();
		notifyObservers(language);
		System.out.println("String Property Language Value-VIEW: " + this.language.getValue());
		menuPanel.setLanguage(language);
	}
	
	/**
	 * Sets the internalCommand field that the controller can retreive
	 * @param newCommand
	 */
	void setInternalCommand(StringBuilder newCommand) {
		internalCommand = new StringBuilder(newCommand);
		setChanged();
		notifyObservers(newCommand);
	}
	
	/**
	 * Allows user to get new tab
	 */
	public void askForNewTab() {
		tabRequested = true;

		setChanged();
		notifyObservers(tabRequested);

		tabRequested = false;
	}
	
	/**
	 * Load tab with the given index
	 * @param index
	 */
	public void loadTab(String index) {
		workspaceToLoad = index;
		setChanged();
		notifyObservers(workspaceToLoad);
	}
	
	/**
	 * Get the internalCommand- called by controller
	 * @return
	 */
	public StringBuilder getInternalCommand() {
		StringBuilder command = new StringBuilder(internalCommand.toString());
		internalCommand = new StringBuilder();
		return command;
	}
	
	/**
	 * Display alert message to the user
	 * @param message
	 */
	public void alert(String message) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	/**
	 * Reset the display
	 */
	public void reset() {
		graphicsWindow.reset();
	}
	
	/**
	 * 
	 * @return language as String
	 */
	public StringProperty getLanguage() {
		System.out.println("Language Property-VIEW: " + language);
		return language;
	}
	
	/**
	 * Sets the editorText when loading a file
	 * @param fileContents
	 */
	public void setEditorText(Pair<String, String> fileContents) {
		// TODO Auto-generated method stub
		displayedEditor.setEditorText(fileContents.getLast());

		// move string to resource file or constant
		Text scriptLoaded = new Text("The file " + fileContents.getFirst() + " was loaded.");
		scriptLoaded.setFill(Color.CADETBLUE);

		console.printText(scriptLoaded);
	}
	
	/**
	 * 
	 * @return graphicsWindow
	 */
	GraphicsWindow getGraphicsWindow() {
		return graphicsWindow;
	}
	
	/**
	 * 
	 * @return map of turtle ids to turtleviews
	 */
	Map<Integer, TurtleView> getTurtles() {
		return graphicsWindow.getTurtles();
	}
	
	/**
	 * If the text in the internalEditor ever changes, change the internalEditorText in View
	 * to notify controller to execute command
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof InternalEditor) {
			InternalEditor editor = (InternalEditor) o;
			if (arg instanceof StringBuilder && editor.getId() == this.id) {

				setInternalCommand(internalEditor.getEditorText());
			}
		}
	}
	
	/**
	 * Initialize a tab with the given index to load (0 is default)
	 * @param index
	 */
	private void createTab(String index) {
		File xml = null;
		xml = new File(DEFAULT_XML_PATH);

		XMLReader xmlReader = new XMLReader(xml);

		System.out.println("Language- VIEW: " + xmlReader.readAttributeContent("Language", "value", index));
		setLanguage(xmlReader.readAttributeContent("Language", "value", index));
		defaultNumTurtles = Integer.parseInt(xmlReader.readAttributeContent("NumTurtles", "value", index));
		defaultBackgroundColorIndex = Integer
				.parseInt(xmlReader.readAttributeContent("BackgroundColorIndex", "value", index));
	}
	
	/**
	 * 
	 * @return get the string of name of the workspace to load
	 */
	public String getWorkspaceToLoad() {
		return workspaceToLoad;
	}

}