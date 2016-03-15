package slogo.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeSet;
import generic.Pair;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class TurtlePicker extends CustomDialog {

    private Properties shapeProperties;
    private ResourceBundle shapeBundle;

    private static final double hGap = 30;
    private static final double vGap = 30;

    private static final double PADDING = 25;

    private static final String DEFAULT_RESOURCE_LOCATION = "/resources/images/";

    private static final String TITLE = "Pick your image";

    private static final int PREF_WIDTH = 400;

    private SimpleStringProperty index;
    private ImageView imageFromIndex;

    private EventHandler<ActionEvent> event;

    public TurtlePicker (Object o) {
        super(o);

        shapeProperties = new OrderedProperties();
        shapeBundle = ResourceBundle.getBundle("resources/general/Shape");

        initializeImage(o.toString());
        makeStage();
    }

    private void initializeImage (String initialIndex) {
        index = new SimpleStringProperty(initialIndex);
        imageFromIndex = new ImageView();

        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed (ObservableValue observable, Object oldValue, Object newValue) {
                String value = (String) newValue;
                String imageURL = shapeBundle.getString(value);
                imageFromIndex.setImage(new Image(this.getClass()
                        .getResourceAsStream("/resources/images/" + imageURL)));
                System.out.println("Changing the turtle picker index");
            }
        };
        
        index.addListener(changeListener);
    }

    @Override
    protected Pair<Scene, String> makeStage () {
        BorderPane bp = new BorderPane();
        bp.setCenter(initializePickerOptions());
        
        Scene myScene = new Scene(bp);
        this.setScene(myScene);
        return new Pair<Scene, String>(myScene, TITLE);
    }

    @Override
    protected void setContext (String context) {
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private TilePane initializePickerOptions () {
        TilePane root = new TilePane(hGap,vGap);
        root.setPadding(new Insets(PADDING));

        loadImages();

        int numImages = shapeProperties.keySet().size();
        Pair dimensions = findBestDimensions(numImages);

        System.out.println("Rows: " + dimensions.getFirst());
        System.out.println("Colm: " + dimensions.getLast());

        root.setPrefRows((int) dimensions.getFirst());
        root.setPrefColumns((int) dimensions.getLast());

        int width = PREF_WIDTH / (int) dimensions.getLast();
        
        Comparator sortStringByIntegerValue = new Comparator(){
            @Override
            public int compare (Object o1, Object o2) {
                return Integer.parseInt(o1.toString())-Integer.parseInt(o2.toString());
            }
        };
        
        List<String> indices = new ArrayList(shapeProperties.keySet());
        
        Collections.sort(indices,sortStringByIntegerValue);
        
        System.out.println("Indices: "+indices);
        
        for (String index : indices) {
            String imageName = shapeBundle.getString(index);
            System.out.println(imageName);
            Image image =
                    new Image(this.getClass().getResourceAsStream(DEFAULT_RESOURCE_LOCATION +
                                                                  imageName));
            System.out.println(image);
            ImageView iv = new ImageView(image);
            iv.setFitWidth(width);
            iv.setFitHeight(width);
            Button choice = new Button(index);
            choice.setGraphic(iv);

            choice.setOnAction(e -> {
                this.index.setValue(index);
                //this.imageFromIndex = loadImage(this.index.getValue());
                this.close();
            });

            root.getChildren().add(choice);
        }
        
        return root;

    }

    private void loadImages () {
        try {
            shapeProperties.load(this.getClass()
                    .getResourceAsStream("/resources/general/Shape.properties"));
            //System.out.println(shapeProperties.keySet());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pair findBestDimensions (int total) {
        if (isPrime(total)) {
            total++;
        }
        int start = (int) Math.floor(Math.sqrt((double) total));
        int dim1 = recursivelyFindDimension(start, total);
        int dim2 = total / dim1;
        Pair dimensions = new Pair(dim1, dim2);
        return dimensions;
    }

    private static boolean isPrime (int num) {
        if (num == 2)
            return true;
        if (num % 2 == 0)
            return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0)
                return false;
        return true;
    }

    private int recursivelyFindDimension (int start, int total) {
        if (((double) total / start) % 1 == 0) {
            return start;
        }
        int lower = start - 1;
        return recursivelyFindDimension(lower, total);
    }
    
    String getValue () {
        return index.getValue();
    }

    ImageView getImage () {
        System.out.println("Index: "+index);
        return imageFromIndex;
    }
    
    ImageView loadImage(String index){
        String imageLocation = shapeBundle.getString(index);
        System.out.println(imageLocation);
        ImageView turtleImageView = new ImageView(new Image (DEFAULT_RESOURCE_LOCATION+imageLocation));
        return turtleImageView;
    }

}
