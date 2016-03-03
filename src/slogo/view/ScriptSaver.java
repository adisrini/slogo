package slogo.view;

import generic.Pair;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ScriptSaver extends CustomDialog {
	
	private View view;
	
	private static final String TITLE = "Save new script...";
	
	public ScriptSaver(View view) {
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
		
		TextField entry = new TextField();
		entry.setPromptText("Enter script name...");
		
		Button save = new Button("Save");
		save.setOnAction(e -> {
			String methodName = entry.getText();
			view.onSave(methodName);
			this.close();
		});
		
		box.getChildren().addAll(entry, save);
		
		return box;
		
	}

	@Override
	protected void setContext(String string) {
		// TODO Auto-generated method stub
	}

}
