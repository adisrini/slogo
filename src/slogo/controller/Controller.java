package slogo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import generic.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.model.IState;
import slogo.model.Model;
import slogo.model.SlogoException;
import slogo.view.ErrorFactory;
import slogo.view.SplashScreen;
import slogo.view.TabPreference;
import slogo.view.View;
import slogo.xml.XMLReader;
import slogo.xml.XMLWriter;


// TODO: Add command converter in only english
public class Controller extends Observable implements Observer {
    private static final String DEFAULT_XML_PATH = "./src/resources/preferences/PreferencesXML.xml";
    private static final String LANGUAGE_RESOURCE_LOCATION = "resources/languages/";
    private static final String DEFAULT_LANGUAGE = "English";
    private static final String LANGUAGE_CODES = "LanguageCodes";
    private static final String GENERAL_RESOURCE_LOCATION = "resources/bundles/General";

    private static final String TITLE = "SLogo | A Simple Logo IDE";

    private static final int FRAMES_PER_SECOND = 30;
    public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private static final String ERROR = "ERROR: ";

    private Stage stage;

    private Boolean tabRequested;
    private String workspaceIndexToLoad;

    private View view;
    private Model model;

    private Map<String, String> commandMap;

    private ResourceBundle myLanguageResource;
    private ResourceBundle myGeneralResource;
    private ResourceBundle languageCodes;

    private Timeline animation;
    private Iterator<List<IState>> stateIterator;

    private String language;

    private int id;
    
    Map<String, EventHandler<ActionEvent>> eventHandlerMap =
            new HashMap<String, EventHandler<ActionEvent>>();

    public Controller (int id, Stage stage, boolean bypass, TabPreference tabPreference) {
        this.id=id;
        this.stage = stage;

        tabRequested = false;
        language = DEFAULT_LANGUAGE;

        myLanguageResource =
                ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + DEFAULT_LANGUAGE);

        myGeneralResource = ResourceBundle.getBundle(GENERAL_RESOURCE_LOCATION);

        languageCodes = ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + LANGUAGE_CODES);

        // TODO: Figure out a way to make this cleaner
        initializeEventHandlerMap();

        model = new Model();
        // List<Integer> turtleIds = model.setNumTurtles(numTurtles);

        List<Integer> turtleIds = new ArrayList<Integer>();
        turtleIds.add(0);

        view = new View(id,event -> {
            onComboBoxAction(event);
            onContinueAction(event);
            onMenuItemAction(event);
            onButtonAction(event);
            setLanguage();
            // execute();
        } , eventHandlerMap, tabPreference);

        commandMap = new HashMap<String, String>();

        if (bypass) {
            view.changeToMainScene();
            language = DEFAULT_LANGUAGE;
            view.setLanguage(language);
            setLanguage();
        }

        view.addObserver(this);

        setScene();
        this.stage.show();
        this.stage.setTitle(TITLE);

        initializeAnimation(Duration.millis(500));
    }

    private void initializeEventHandlerMap () {
        eventHandlerMap.put(myGeneralResource.getString("Run"), e -> execute());
        eventHandlerMap.put(myGeneralResource.getString("RunInternal"), e -> executeInternal());
        eventHandlerMap.put(myGeneralResource.getString("PrevState"), e -> undoState());
        eventHandlerMap.put(myGeneralResource.getString("PrevExec"), e -> undoExecution());
        eventHandlerMap.put(myGeneralResource.getString("NextState"), e -> redoState());
        eventHandlerMap.put(myGeneralResource.getString("NextExec"), e -> redoExecution());
    }

    private void setScene () {
        stage.setScene(view.getScene());
        // stage.setFullScreen(true);
        // stage.setFullScreenExitHint("");
    }

    /**
     * onActionEvent that is passed to the Run buttons in menubar and toolbar
     * 
     * When run, the editorText is passed from the view to the controller
     * The controller passes this text to the model, which processes the data and returns a list of
     * States
     * 
     */
    private void execute () {

        StringBuilder editorText = view.execute();
        StringBuilder parsedText = parseEditorText(editorText);
        System.out.println("Altered Text: " + parsedText);
        callModelExecution(new Pair<String, String>(parsedText.toString()
                .replaceAll("(?m)^#.*", ""), editorText.toString()));

    }

    private void executeInternal () {
        StringBuilder command = view.executeInternal();
        System.out.println("Internal Editor Text- Controller: " + command);
        callModelExecution(new Pair<String, String>(parseEditorText(command).toString().replaceAll("(?m)^#.*", ""),
                                                    command.toString()));
    }

    private void callModelExecution (Pair<String, String> inputs) {
        try {
            model.initExecution(inputs);
        }
        catch (SlogoException e) {
            handleParsingException(e);
        }

        stateIterator = model.getStateIterator();

        animation.play();
    }

    private void handleParsingException (SlogoException e) {
        System.out.println("current language- controller: " + language);
        System.out.println("current language- view: " + view.getLanguage().getValue());

        Pair<String, String> errorVessel = e.getException();
        ErrorFactory error = ErrorFactory.valueOf(errorVessel.getFirst());

        String message = error.speak(errorVessel.getLast());

        if (!language.equals(DEFAULT_LANGUAGE)) {
            message = translate(message);
        }

        view.displayError(message);
        view.alert(message);
    }

    private void onComboBoxAction (ActionEvent event) {
        if (event.getSource() instanceof ComboBox) {
            ComboBox comboBox = (ComboBox) event.getSource();
            if (comboBox.getId().equals("languageBox")) {
                language = comboBox.getValue().toString();
                XMLWriter xmlWriter = new XMLWriter(new File(DEFAULT_XML_PATH));
                xmlWriter.modifyAttribute("Language", "value", "0", language);
            }

            System.out.println("LANGUAGE: " + language);
        }
    }

    private void onContinueAction (ActionEvent event) {
        System.out.println("hi");
    }

    private void onMenuItemAction (ActionEvent event) {
        if (event.getSource() instanceof MenuItem) {
            // execute();
        }
    }

    private void onButtonAction (ActionEvent event) {
        Node element = (Node) event.getSource();
        if (element.getId() != null) {
            switch (element.getId()) {
                case "Run":
                    execute();
                    break;
                case "Continue":
                    //view.loadMainScene();
                    view.changeToMainScene();
                    setScene();
                    break;
            }
            if (element.getId().contains("Callback")) {
                String code = element.getId().split(" ", 2)[1];
                callModelExecution(new Pair<String, String>(parseEditorText(new StringBuilder(code))
                        .toString(), code));
            }
        }
    }

    private StringBuilder parseEditorText (StringBuilder editorText) {
        StringBuilder parsedCommands = new StringBuilder();
        String[] editorTextArray = editorText.toString().split(" |(\n)");

        for (String command : editorTextArray) {
            if (command.equals("\n")) {
                System.out.println("fuck yeahh");
            }
            if (commandMap.containsKey(command)) {
                parsedCommands.append(commandMap.get(command) + " ");
            }
            else {
                parsedCommands.append(command + " ");
            }
        }

        return parsedCommands;
    }

    private void setLanguage () {

        myLanguageResource = ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + language);

        commandMap.clear();

        for (String key : myLanguageResource.keySet()) {
            String[] values = myLanguageResource.getString(key).split("\\|");

            for (String value : values) {
                commandMap.put(value.toLowerCase(), key.toLowerCase());
            }
        }
    }

    private void initializeAnimation (Duration timeScale) {
        animation = new Timeline();
        KeyFrame frame = new KeyFrame(timeScale,
                                      e -> step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step () {
        if (!stateIterator.hasNext()) {
            animation.stop();
            view.onComplete();
        }
        else {
            System.out.println("move");
            List<IState> states = stateIterator.next();
            System.out.println("STATES: ");
            int i = 1;
            for (IState state : states) {
                System.out.println(i + " " + state);
                i++;
            }
            System.out.println("DONE");
            view.updateState(states);
        }
    }

    private void undoState () {
        List<IState> states = model.undoState();

        System.out.println("New state: " + states);

        if (states == null) {
            return;
        }

        view.undo(states);
    }

    private void undoExecution () {
        List<IState> states = model.undoExecution();

        if (states == null) {
            return;
        }

        System.out.println("New execution: " + states);

        view.undo(states);

    }

    // TODO: UPDATE REDO
    private void redoState () {

        List<IState> states = model.redoState();

        if (states == null) {
            return;
        }

        System.out.println("Redo state: " + states);

        view.updateState(states);

    }

    private void redoExecution () {

        Iterator<List<IState>> states = model.redoExecution();
        if (states == null) {
            return;
        }
        stateIterator = states;
        System.out.println("Redo Execution: " + states);

        animation.play();
    }

    private String translate (String message) {

        System.out.println("Original Message: " + message);

        String newMessage = message;

        String languageCode = languageCodes.getString(language.toLowerCase());

        Language language = Language.fromString(languageCode);
        System.out.println(language);

        try {
            newMessage = Translate.execute(message, language);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return newMessage;
    }

    @Override
    public void update (Observable observed, Object object) {
        System.out.println("update called");

        if (observed instanceof View) {
            if (object instanceof StringProperty) {
                language = view.getLanguage().getValue();
                setLanguage();
            }

            if (object instanceof StringBuilder) {
                System.out.println("EXECUTE INTERNAL");
                executeInternal();
            }

            if (object instanceof Boolean) {
                tabRequested = true;

                setChanged();
                notifyObservers(tabRequested);
            }

            if (object instanceof String) {
                workspaceIndexToLoad = (String) object;
                setChanged();
                notifyObservers(workspaceIndexToLoad);

            }
            if (object instanceof Double) {
                initializeAnimation(Duration.millis((double) object));
            }

        }
    }

    public View getView () {
        return this.view;
    }

    public void updateCurrentTab (int id) {
        // TODO Auto-generated method stub
        model.updateCurrentTab(id);
    }

    public Model getModel () {
        return this.model;
    }

}
