package slogo.view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.sun.prism.paint.Color;

import generic.Pair;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TurtlePicker extends CustomDialog {
	
	private GraphicsWindow graphicsWindow;
	
	private static final double hGap = 30;
	private static final double vGap = 30;
	
	private static final double PADDING = 25;
	
	private static final String DEFAULT_RESOURCE_LOCATION = "/resources/images/";
	private static final String EXTENSION = ".png";
	
	private static final String TITLE = "Pick your image";
	
	private static final int PREF_WIDTH = 400;
	
	public TurtlePicker(GraphicsWindow graphicsWindow) {
		super(graphicsWindow);
		this.graphicsWindow = graphicsWindow;
	}
	
	@Override
	protected Pair<Scene, String> makeStage() {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		root.setCenter(pickerOptions());
		
		Scene myScene = new Scene(root);
		return new Pair<Scene, String>(myScene, TITLE);
	}
	
	@Override
	protected void setContext(String context) {
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TilePane pickerOptions() {
		TilePane tile = new TilePane(hGap, vGap);
		tile.setPadding(new Insets(PADDING));
		
		List<String> images = (List<String>) loadImages();
		
		Pair dimensions = findBestDimensions(images.size());
		
		tile.setPrefRows((int) dimensions.getFirst());
		tile.setPrefColumns((int) dimensions.getLast());
		
		int width = PREF_WIDTH / (int) dimensions.getLast();
		
		for(int i = 0; i < images.size(); i++) {
			String imageName = images.get(i);
			Image image = new Image(this.getClass().getResourceAsStream(DEFAULT_RESOURCE_LOCATION + imageName + EXTENSION));
			ImageView iv = new ImageView(image);
			iv.setFitWidth(width);
			iv.setFitHeight(width);
			Button choice = new Button();
			choice.setGraphic(iv);
			choice.setOnAction(event -> {
				graphicsWindow.setTurtleImage(imageName + EXTENSION);
			});
			tile.getChildren().add(choice);
		}
		
		return tile;
		
	}
	
	private Collection<?> loadImages() {
		File directory = new File("./src/resources/images");	// load current directory
		
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return true;
			}
		};
		
		ResourceFilter filter = new ResourceFilter(directory, textFilter);
		
		List<String> languages = (List<String>) filter.getFilteredList();
		
		languages = languages.stream()
							 .map(m -> m.replace(".png", ""))
				 			 .collect(Collectors.toList());
		
		return languages;
	}
	

	private Pair findBestDimensions(int total) {
		if(isPrime(total)) { total++; }
		int start = (int) Math.floor(Math.sqrt((double)total));
		int dim1 = recursivelyFindDimension(start, total);
		int dim2 = total/dim1;
		Pair dimensions = new Pair(dim1, dim2);
		return dimensions;
	}
	
	private static boolean isPrime(int num) {
        if (num == 2 ) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
}
	
	private int recursivelyFindDimension(int start, int total) {
		if(((double)total/start) % 1 == 0) {
			return start;
		}
		int lower = start - 1;
		return recursivelyFindDimension(lower, total);
	}
	
}
