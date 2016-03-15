package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

/**
 * The language combobox used to select a language from the
 * list provided in a properties folder.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class LanguageComboBox extends ComboBox<String> {

    private static final String UNWANTED_EXTENSION = ".properties";
    private List<String> languages;
    private static final String DIRECTORY = "./src/resources/languages";
    private static final String UNWANTED_FILENAME = "Syntax";
    private static final String PROMPT = "Select A Langauge...";
    
    /**
     * Initializes the combobox with an event listener
     * @param eventOnAction
     */
    public LanguageComboBox (EventHandler<ActionEvent> eventOnAction) {
        languages = new ArrayList<String>();
        
        initialize();
        
        if (eventOnAction != null){
            this.setOnAction(eventOnAction);
        }
    }
    
    /**
     * Alternate initialization of the combo box (without a listener)
     */
    public LanguageComboBox(){
        initialize();
    }

    /**
     * Parses the list of property files from the resource bundle "languages"
     * 
     * @return the list of languages as Strings
     */
    private List<String> loadLanguages () {
        File directory = new File(DIRECTORY);

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept (File dir, String name) {
                if (!name.startsWith(UNWANTED_FILENAME)) {
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

    /**
     * Initializes the combobox with the list of languages.
     */
    private void initialize () {
        languages = loadLanguages();

        this.setPromptText(PROMPT);
        this.setPrefWidth(600);
        this.getItems().addAll(languages);
    }
    
}
