package slogo.view;

import generic.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Preferences extends CustomDialog {
    private static final String TITLE = "Preferences";

    private View view;
    private GraphicsWindow graphicsWindow;
    
    private CustomTab currentPreferences;
    private DefaultPreferenceTab defaultPreferences;
    private TurtlePreferenceTab turtlePreferences;
    
    public Preferences (View view) {
        super(view);
        
        this.view = view;
        graphicsWindow = view.getGraphicsWindow();

        initializeDefaultPreferences();
        initializeTurtlePreferences();
        initializeCurrentPreferences();
    }
    
    private void initializeDefaultPreferences(){
        defaultPreferences = new DefaultPreferenceTab("Default");
        defaultPreferences.setClosable(false);
        Button apply = buttonFactory("Apply",e->{
            defaultPreferences.writeToXML();
            this.close();
        });
        Button cancel = buttonFactory("Cancel",e->this.close());
        defaultPreferences.addButtons(apply,cancel);
    }
    
    private void initializeTurtlePreferences(){
        turtlePreferences = new TurtlePreferenceTab(graphicsWindow,view.getTurtles());
        Button apply = buttonFactory("Apply",e->{
            turtlePreferences.buildInternalCommand();
            this.close();
        });
        Button cancel = buttonFactory("Cancel",e->this.close());
        turtlePreferences.addButtons(apply,cancel);
    }
    
    private void initializeCurrentPreferences(){
        currentPreferences = new CustomTab("Current");
        currentPreferences.setClosable(false);
        
        currentPreferences.addContent(languageChoices(),turtleSpeedSlider());
        
       // currentPreferences.root.getChildren().add(new Button ("bod"));
        
        Button apply = buttonFactory("Apply",e ->{this.close();});
        Button cancel = buttonFactory("Cancel",e->{this.close();});
        currentPreferences.addButtons(apply,cancel);
    }

    @Override
    protected Pair<Scene, String> makeStage () {

        TabPane root = new TabPane();
        root.getTabs().addAll(currentPreferences,turtlePreferences,defaultPreferences);

        Scene myScene = new Scene(root);
        return new Pair<Scene, String>(myScene, TITLE);
    }

    @Override
    protected void setContext (String context) {

    }

    private VBox turtleSpeedSlider () {
        VBox container = new VBox();

        HBox row = new HBox();

        final Slider slider = new Slider(1, 1000, 50);

        slider.setPrefWidth(400);

        Label text = new Label(Integer.toString((int) slider.getValue()));

        slider.valueProperty().addListener( (obs, old, newValue) -> {
            long speed = Math.round((double) newValue);
            text.setText(Long.toString(speed));
            
            view.setTurtleSpeed(25000/((double) newValue));

        });

        row.getChildren().addAll(slider, text);

        container.getChildren().addAll(new Label("Select turtle speed"),
                                       row);

        return container;
    }

    /**
     * Creates a combo box listing the language options
     * 
     * @param splashScreenComboBoxEvent is the event listener in Controller that responds to actions
     * @return
     */
    private VBox languageChoices () {
        VBox container = new VBox();
        
        LanguageComboBox languageComboBox = new LanguageComboBox();
        languageComboBox.setOnAction(event -> view.setLanguage(languageComboBox.getValue()));

        container.getChildren().addAll(new Label("Select your language"),
                                       languageComboBox);

        return container;
    }

    private Button buttonFactory(String name, EventHandler<ActionEvent> eventOnAction){
        Button button = new Button(name);
        button.setOnAction(eventOnAction);
        
        return button;
    }
}
