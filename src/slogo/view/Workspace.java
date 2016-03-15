package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import generic.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import slogo.xml.XMLReader;
import slogo.xml.XMLWriter;


public class Workspace extends CustomDialog {
    private static final String PREFERENCES_XML = "./src/resources/preferences/Preferences.xml";
    private static final String PREFERENCES_PROPERTIES =
            "./src/resources/preferences/XMLterms.properties";

    private static final String BACKGROUND_COLOR = "BackgroundColor";
    private static final String NUM_TURTLES = "NumTurtles";
    private static final String LANGUAGE = "Language";
    private static final String STARTING_FILE = "StartingFile";

    private static final String UNWANTED_EXTENSION = ".properties";

    private ResourceBundle resourceBundle;

    Map<String, String> preferenceMap;

    private XMLReader reader;
    private XMLWriter writer;
    
    private Scene myScene;
    private VBox root;
    private View view;
    
    private String tabIndex = "0";
    public Workspace (View view) {
        super(view);
        
        this.view = view;
        File xmlFile = new File(PREFERENCES_XML);
        
        reader = new XMLReader(xmlFile);
        writer = new XMLWriter(xmlFile);
        resourceBundle = ResourceBundle.getBundle(PREFERENCES_PROPERTIES);

        initializePreferenceMap();
        initializeScene();
    }

    private void initializePreferenceMap () {
        preferenceMap.put(BACKGROUND_COLOR,
                          reader.readElementContent(resourceBundle.getString(BACKGROUND_COLOR),tabIndex));
        preferenceMap.put(NUM_TURTLES,
                          reader.readElementContent(resourceBundle.getString(NUM_TURTLES),tabIndex));
        preferenceMap.put(LANGUAGE, reader.readElementContent(resourceBundle.getString(LANGUAGE),tabIndex));
        preferenceMap.put(STARTING_FILE,
                          reader.readElementContent(resourceBundle.getString(STARTING_FILE),tabIndex));

    }

    private void initializeScene () {
        root = new VBox();

        root.getChildren().add(createComboBox(LANGUAGE, loadLanguages()));
        root.getChildren().add(createColorPicker(BACKGROUND_COLOR));

        HBox textHBox = new HBox();
        textHBox.getChildren().addAll(new Text(NUM_TURTLES), createTextField(NUM_TURTLES));
        root.getChildren().add(textHBox);
        
        myScene = new Scene(root);
    }

    private void save () {
        for (String preference : preferenceMap.keySet()){
            writer.modifyElementContent(resourceBundle.getString(preference), preferenceMap.get(preference),tabIndex);
        }
    }
    
    private void apply(){
        
    }
    
    private ComboBox createComboBox (String id, List<String> list) {
        ComboBox comboBox = new ComboBox();

        comboBox.setId(id);
        comboBox.getItems().addAll(list);
        comboBox.setOnAction(e -> updateValue(resourceBundle.getString(id),
                                              comboBox.getValue().toString()));

        return comboBox;
    }

    private ColorPicker createColorPicker (String id) {
        ColorPicker colorPicker = new ColorPicker();

        colorPicker.setId(id);
        colorPicker.setOnAction(e -> updateValue(resourceBundle.getString(id),
                                                 colorPicker.getValue().toString()));

        return colorPicker;
    }

    private TextField createTextField (String id) {
        TextField textField = new TextField();

        textField.setText(preferenceMap.get(id));
        textField.setOnMouseExited(e -> updateValue(resourceBundle.getString(id),
                                                    textField.getText()));

        return textField;
    }

    private void updateValue (String elementName, String value) {
        writer.modifyElementContent(elementName, value,tabIndex);
    }

    private HBox generateGenericButtons () {
        HBox buttons = new HBox();

        buttons.getChildren().add(buttonFactory("Save", e -> save()));
        buttons.getChildren().add(buttonFactory("Cancel", e -> close()));

        return buttons;
    }

    private Button buttonFactory (String name, EventHandler<ActionEvent> event) {
        Button button = new Button();

        button.setText(name);
        button.setOnAction(event);

        return button;
    }

    /**
     * Parses the list of property files from the resource bundle "languages"
     * 
     * @return the list of languages as Strings
     */
    private List<String> loadLanguages () {
        File directory = new File("./src/resources/languages"); // load current directory

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                if (!name.startsWith("Syntax") && !name.startsWith("LanguageCodes")) {
                    return true;
                }
                return false;
            }
        };

        ResourceFilter filter = new ResourceFilter(directory, textFilter);

        List<String> languages = (List<String>) filter.getFilteredList();

        languages = languages.stream()
                .map(m -> m.replace(UNWANTED_EXTENSION, ""))
                .collect(Collectors.toList());

        return languages;
    }

    @Override
    protected Pair<Scene, String> makeStage () {
        return new Pair<Scene,String> (myScene,"Workspace Preferences");
    }

    @Override
    protected void setContext (String string) {
      
    }

}
