package slogo.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import generic.Pair;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import slogo.view.View;


public class Controller implements Observer {

    private static final String LANGUAGE_RESOURCE_LOCATION = "resources/languages/";
    private static final String DEFAULT_LANGUAGE = "English";
    private static final String LANGUAGE_CODES = "LanguageCodes";

    private static final String GENERAL_RESOURCE_LOCATION = "resources/bundles/General";

    private static final String TITLE = "SLogo | A Simple Logo IDE";

    private static final int FRAMES_PER_SECOND = 30;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    
    private static final String ERROR = "ERROR: ";

    private Stage stage;

    private View view;
    private Model model;

    private Map<String, String> commandMap;

    private ResourceBundle myLanguageResource;
    private ResourceBundle myGeneralResource;
    private ResourceBundle languageCodes;

    private Timeline animation;
    private Iterator<IState> stateIterator;

    private String language;

    Map<String, EventHandler<ActionEvent>> eventHandlerMap =
            new HashMap<String, EventHandler<ActionEvent>>();

    public Controller (Stage stage) {
        this.stage = stage;

        language = DEFAULT_LANGUAGE;

        myLanguageResource =
                ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + DEFAULT_LANGUAGE);

        myGeneralResource = ResourceBundle.getBundle(GENERAL_RESOURCE_LOCATION);

        languageCodes = ResourceBundle.getBundle(LANGUAGE_RESOURCE_LOCATION + LANGUAGE_CODES);

        // TODO: Figure out a way to make this cleaner
        eventHandlerMap.put(myGeneralResource.getString("Run"), e -> execute());
        eventHandlerMap.put(myGeneralResource.getString("PrevState"), e -> undoState());
        eventHandlerMap.put(myGeneralResource.getString("PrevExec"), e -> undoExecution());
        eventHandlerMap.put(myGeneralResource.getString("NextState"), e -> redoState());
        eventHandlerMap.put(myGeneralResource.getString("NextExec"), e -> redoExecution());
        eventHandlerMap.put(myGeneralResource.getString("Reset"), e -> resetExecution());

        view = new View(event -> {
            onComboBoxAction(event);
            onContinueAction(event);
            onMenuItemAction(event);
            onButtonAction(event);
            setLanguage();
            // execute();
        } , eventHandlerMap);

        view.addObserver(this);
        // view.getLanguage().bindBidirectional(language);

        commandMap = new HashMap<String, String>();
        model = new Model();

        setScene();
        this.stage.show();
        this.stage.setTitle(TITLE);

        initializeAnimation();
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
        callModelExecution(parsedText.toString().replaceAll("(?m)^#.*", ""));
    }

    private void callModelExecution (String code) {
        System.out.println(code);
        try {
            model.initExecution(code);
        }
        catch (SlogoException e) {
            handleParsingException(e);
        }

        stateIterator = model.getStateIterator();

        animation.play();
    }
    
    private void handleParsingException(SlogoException e) {
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
            if(comboBox.getId().equals("languageBox")) {
            	language = comboBox.getValue().toString();
            	view.setLanguage(language);
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
                    view.changeToMainScene();
                    setScene();
                    break;
            }
            if (element.getId().contains("Callback")) {
                String code = element.getId().split(" ", 2)[1];
                callModelExecution(code);
            }
        }
    }

    private StringBuilder parseEditorText (StringBuilder editorText) {
        StringBuilder parsedCommands = new StringBuilder();
        String[] editorTextArray = editorText.toString().split(" ");

        for (String command : editorTextArray) {
            System.out.println(command);
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

    private void initializeAnimation () {
        animation = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
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
            IState state = stateIterator.next();
            view.updateState(state);
        }
    }

    private void undoState () {
        IState state = model.undoState();

        System.out.println("New state: " + state);

        if (state == null){
            return;
        }
        
        view.undoState(state);
    }

    private void undoExecution () {
        IState state = model.undoExecution();
        
        if (state == null){
            return;
        }
        
        System.out.println("New execution: " + state);

        view.undoExecution(state);

    }

    private void redoState () {

        IState state = model.redoState();
        
        if (state == null){
            return;
        }
        
        System.out.println("Redo state: " + state);

        view.updateState(state);

    }

    private void redoExecution () {

        IState state = model.redoExecution();
        
        if (state == null){
            return;
        }
        System.out.println("Redo Execution: " + state);

        view.redoExecution(state);
    }

    private void resetExecution () {

        IState state = model.resetExecution();

        System.out.println("Reset Execution: " + state);

        // view.updateState(state);

        view.reset();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newMessage;
    }

    @Override
    public void update (Observable observed, Object object) {
        System.out.println("update called");

        if (observed instanceof View) {
            language = view.getLanguage().getValue();
            setLanguage();
        }
    }

}
