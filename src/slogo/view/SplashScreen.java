package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SplashScreen {
	
	private static final String CONTINUE = "Continue";
	private static final String UI_RESOURCE_LOCATION = "/resources/ui/";
	private static final String EXTENSION = ".png";
	
	private static final String UNWANTED_EXTENSION = ".properties";
	
	private BorderPane root;
	
	private Button continueButton;
	
	/**
	 * Initializes the splash screen and adds the nodes to the view
	 * @param splashScreenComboBoxEvent
	 */
	public SplashScreen(EventHandler<ActionEvent> splashScreenComboBoxEvent) {
		root = new BorderPane();
		root.setCenter(makeLanguageSelectionView(splashScreenComboBoxEvent));
	}

	protected BorderPane getRoot() {
		return root;
	}
	
	/**
	 * Makes the ComboBox, Button, and Text that comprise the SplashScreen and add them to
	 * a VerticalBox which can be added to the root
	 * @param splashScreenComboBoxEvent is the event listener in Controller that responds to actions
	 * @return
	 */
	private Pane makeLanguageSelectionView(EventHandler<ActionEvent> splashScreenComboBoxEvent) {
		VBox pane = new VBox();
		
		pane.setSpacing(10);
		
		Image image = new Image(this.getClass().getResourceAsStream(UI_RESOURCE_LOCATION + "logo" + EXTENSION));
		ImageView iv = new ImageView(image);
		
		pane.getChildren().addAll(iv, languageChoices(splashScreenComboBoxEvent), continueButton(splashScreenComboBoxEvent));
		
		pane.setAlignment(Pos.CENTER);
		
		return pane;
	}
	
	/**
	 * Creates a combo box listing the language options
	 * @param splashScreenComboBoxEvent is the event listener in Controller that responds to actions
	 * @return
	 */
	private ComboBox<String> languageChoices(EventHandler<ActionEvent> splashScreenComboBoxEvent) {
		ComboBox<String> comboBox = new ComboBox<String>();
		List<String> languages = loadLanguages();
		comboBox.setPrefWidth(200);
		comboBox.getItems().addAll(languages);
		comboBox.setOnAction(splashScreenComboBoxEvent);
		comboBox.setId("languageBox");
		
		comboBox.valueProperty().addListener(e -> {
			if(comboBox.getValue() == "") {
				continueButton.setDisable(true);
			} else {
				continueButton.setDisable(false);
			}
		});
		return comboBox;
	}
	
	/**
	 * Parses the list of property files from the resource bundle "languages"
	 * @return the list of languages as Strings
	 */
	private List<String> loadLanguages() {
		File directory = new File("./src/resources/languages");	// load current directory
		
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if(!name.startsWith("Syntax") && !name.startsWith("LanguageCodes")) {
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
	 * Constructs the button that is used to continue from the splash screen to the main
	 * window.
	 * @param splashScreenComboBoxEvent
	 * @return
	 */
	private Button continueButton(EventHandler<ActionEvent> splashScreenComboBoxEvent) {
		continueButton = new Button();
		continueButton.setText(CONTINUE);
		continueButton.setPrefWidth(200);
		continueButton.setOnAction(splashScreenComboBoxEvent);
		continueButton.setId("Continue");
		
		continueButton.setDisable(true);
		
		return continueButton;
	}

}
