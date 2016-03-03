package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.stream.Collectors;

import generic.Pair;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Preferences extends CustomDialog {
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 100;
	
	private static final String TITLE = "Preferences";
	
	private static final String UNWANTED_EXTENSION = ".properties";
	
	private View view;
	
	public Preferences(View view) {
		super(view);
		
		this.view = view;
		
	}
	
	@Override
	protected Pair<Scene, String> makeStage() {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		
		root.setCenter(makePrefOptions());
		root.setBottom(putButtons());
		
		Scene myScene = new Scene(root);
		
		return new Pair<Scene, String>(myScene, TITLE);
	}
	
	@Override
	protected void setContext(String context) {
		
	}
	
	private VBox makePrefOptions() {
		VBox box = new VBox();
		
		box.getChildren().addAll(languageChoices(),
								 turtleSpeedSlider());
		
		box.setPadding(new Insets(100, 100, 100, 100));
		
		return box;
		
	}
	
	private VBox turtleSpeedSlider() {
		VBox container = new VBox();
		
		HBox row = new HBox();
		
		final Slider slider = new Slider(0, 100, 50);
		
		slider.setPrefWidth(400);
		
		Label text = new Label(Integer.toString((int) slider.getValue()));
		
		slider.valueProperty().addListener((obs, old, newValue) -> {
			long speed = Math.round((double) newValue);
			text.setText(Long.toString(speed));
			
			view.setTurtleSpeed((double) newValue);
			
		});
	    
	    row.getChildren().addAll(slider, text);
	    
	    container.getChildren().addAll(new Label("Select turtle speed"),
	    							   row);
	    
	    return container;
	}

	/**
	 * Creates a combo box listing the language options
	 * @param splashScreenComboBoxEvent is the event listener in Controller that responds to actions
	 * @return
	 */
	private VBox languageChoices() {
		VBox container = new VBox();
		
		ComboBox<String> comboBox = new ComboBox<String>();
		List<String> languages = loadLanguages();
		
		comboBox.setPromptText("Click for more choices...");
		comboBox.setPrefWidth(600);
		comboBox.getItems().addAll(languages);
		comboBox.setOnAction(e -> {
			view.setLanguage(comboBox.getValue());
		});
		
		container.getChildren().addAll(new Label("Select your language"), 
									   comboBox);
		
		return container;
	}
	
	/**
	 * Parses the list of property files from the resource bundle "languages"
	 * @return the list of languages as Strings
	 */
	private List<String> loadLanguages() {
		File directory = new File("./src/resources/languages");	// load current directory
		
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if(!name.startsWith("Syntax")) {
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
	
	private HBox putButtons() {
		HBox row = new HBox();
		Button OK = new Button("OK");
		OK.setId("OK");
		OK.setOnAction(e -> {
			this.close();
		});
		Button close = new Button("Cancel");
		close.setOnAction(e -> {
			this.close();
		});
		row.getChildren().addAll(OK, close);
		
		row.setAlignment(Pos.BASELINE_RIGHT);
		
		return row;
	}
}
