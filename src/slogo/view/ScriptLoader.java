package slogo.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import generic.Pair;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import slogo.model.SlogoException;

public class ScriptLoader extends CustomDialog {
	
	private View view;
	private File script;
	
	private static final String TITLE = "Load new script...";
	
	public ScriptLoader(View view) {
		super(view);
		// TODO Auto-generated constructor stub
		
		this.view = view;
	}

	@Override
	protected Pair<Scene, String> makeStage() {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		root.setCenter(makeTextPrompt());
		
		Scene myScene = new Scene(root);
		
		return new Pair<Scene, String>(myScene, TITLE);
		
	}

	private VBox makeTextPrompt() {
		// TODO Auto-generated method stub
		VBox box = new VBox();
		
		Button browse = new Button("Browse");
		browse.setOnAction(e -> {
			
			script = fileOpener();
			
			if(script != null) {
				try {
					FileReader fileReader = new FileReader(script);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					StringBuffer stringBuffer = new StringBuffer();
					String line;
					while((line = bufferedReader.readLine()) != null) {
						stringBuffer.append(line);
						stringBuffer.append("\n");
					}
					fileReader.close();
					view.setEditorText(new Pair<String, String>(script.getName(), stringBuffer.toString()));
				} catch (Exception ee) {
					// TODO Auto-generated catch block
					throw new SlogoException();
				}
				this.close();
			}
			
		});
		
		box.getChildren().addAll(browse);
		
		return box;
		
	}
	
	private File fileOpener() {
		FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = 
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
       
        //Show save file dialog
        File file = fileChooser.showOpenDialog(this);
        
        return file;
	}

	@Override
	protected void setContext(String string) {
		// TODO Auto-generated method stub
	}

}
