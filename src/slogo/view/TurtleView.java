package slogo.view;

import java.util.List;
import java.util.ResourceBundle;
import generic.Pair;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.model.ITurtleState;

/**
 * The front-end representation of the turtle.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class TurtleView extends ImageView implements Updatable {

    private static final double DEFAULT_SPEED = 500;

    private static final String TURTLE_STATUS = "ID: ";
    private static final String TOGGLE_VISIBILITY_PROMPT = "Toggle visibility";
    private static final String CHANGE_TURTLE_PEN_COLOR = "Change this turtle's pen color";
    private static final String CHANGE_TURTLE_IMAGE = "Change this turtle's image";
    private static final String STAMP = "Stamp";
    private static final String CHANGE_SHAPE_PROMPT = "Change turtle image";

    private static final String TELL = "Tell";
    private static final String SET_COLOR = "SetPenColor";
    private static final String SET_SHAPE = "SetShape";
    
    private static final String IMAGE_EXTENSION = "/resources/images/";

    private double mySpeed;

    private Color penColor;

    private List<Double> myDashArray;
    
    private SimpleIntegerProperty penColorIndex;
    private SimpleIntegerProperty shapeIndex;

    private boolean penDown;
    private double penWidth;
    private boolean visible;
    
    private int penStyleIndex;

    private InternalEditor internalEditor;
    private int id;

    private Tooltip tooltip;

    private ResourceBundle idsToCommands;
    private ResourceBundle palette;
    private ResourceBundle shape;
    
    private static final int DEFAULT_STYLE = 1;
    private static final double DEFAULT_WIDTH = 3;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_COLOR_INDEX = 1;
    
    private static final String DEFAULT_STARTING_TURTLE = "/resources/images/turtle-realistic.gif";
    private static final String IDS_LOCATION = "resources/general/IdsToCommands";
    private static final String PALETTE_LOCATION = "resources/general/Palette";
    private static final String SHAPE_LOCATION = "resources/general/Shape";

    /**
     * Initializes the turtle with an ID and sets default values.
     * @param id
     */
    public TurtleView (int id) {
    	loadBundles();
        Image turtleImage = new Image(this.getClass().getResourceAsStream(DEFAULT_STARTING_TURTLE));
        this.setImage(turtleImage);
        setDefaults();
    	this.id = id;

        penColorIndex.addListener(penColorListener());
        penColorIndex.setValue(DEFAULT_COLOR_INDEX);

        shapeIndex = new SimpleIntegerProperty(1);
        penDown = true;

        this.setOnMouseClicked(e -> {
            establishContextMenu().show(this, e.getScreenX(), e.getScreenY());
        });

        internalEditor = InternalEditor.getInstance();
        tooltip = new Tooltip();
        visible = true;

        initializeStateVisualization();
    }
    
    private void loadBundles() {
        idsToCommands = ResourceBundle.getBundle(IDS_LOCATION);
        palette = ResourceBundle.getBundle(PALETTE_LOCATION);
        shape = ResourceBundle.getBundle(SHAPE_LOCATION);
    }
    
    private void setDefaults() {
    	this.mySpeed = DEFAULT_SPEED;
    	this.penStyleIndex = DEFAULT_STYLE;
    	this.penColor = DEFAULT_COLOR;
        this.penWidth = DEFAULT_WIDTH;
        this.penColorIndex = new SimpleIntegerProperty();
    }
    
    @SuppressWarnings("rawtypes")
	private ChangeListener penColorListener() {
    	return new ChangeListener() {
            @Override
            public void changed (ObservableValue observable, Object oldValue, Object newValue) {
                Integer value = (Integer) newValue;
                if (value == 0) {
                    penColor = Color.valueOf(palette.getString("1"));
                }
                else {
                    penColor = Color.valueOf(palette.getString(Integer.toString((value))));
                }

            }
        };
    }
    
    void setDashArray(List<Double> array) {
    	this.myDashArray = array;
    }
    
    List<Double> getDashArray() {
    	return this.myDashArray;
    }

    void setSpeed (double speed) {
        this.mySpeed = speed;
    }

    double getSpeed () {
        return mySpeed;
    }

    @Override
    public void update (Object object) {
        ITurtleState state = (ITurtleState) object;
        
        this.penStyleIndex = state.getPenStyle();
        TranslateTransition tt = new TranslateTransition(Duration.millis(mySpeed), this);

        double currentX = this.getTranslateX(); double currentY = this.getTranslateY();
        tt.setByX(currentX); tt.setByY(currentY); tt.setToX(state.getX()); tt.setToY(state.getY());

        RotateTransition rt = new RotateTransition(Duration.millis(mySpeed), this);

        double currentHeading = this.getRotate();
        rt.setByAngle(currentHeading); rt.setToAngle(state.getHeading());

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(tt, rt);

        pt.setOnFinished(e -> {
            updateTurtleState(state);
            System.out.println("myturtle: " + this.toString());
            tooltip.setText(this.toString());
        });

        pt.play();
    }

    private void updateTurtleState (ITurtleState state) {
        setTranslateX(state.getX());
        setTranslateY(state.getY());
        setRotate(state.getHeading());

        this.visible = state.getVisibility();
        setVisibility(visible);
        
        setImageByIndex(state.getShapeIndex());
        
        if (penColorIndex.getValue() != state.getPenColorIndex()) {
            penColorIndex.setValue(state.getPenColorIndex());
        }
        
        penWidth = state.getPenSize();
        penDown = state.getPenStatus();
                
    }

    private void setVisibility (boolean visible) {

        if (visible) {
            this.setOpacity(1);
        }
        else {
            this.setOpacity(0);
        }

    }
    
    int getPenStyleIndex() {
    	return this.penStyleIndex;
    }

    double getFrameRate (ITurtleState state) {
        double distance =
                distance(new Pair<Double, Double>(this.getTranslateX(),
                                                  this.getTranslateY()),
                         new Pair<Double, Double>(state.getX(), state.getY()));

        double time = distance / this.getSpeed();

        if (distance == 0) {
            return 100;
        }

        return time;

    }

    private double distance (Pair<Double, Double> point1, Pair<Double, Double> point2) {
        return Math.sqrt(Math.pow(point1.getFirst() - point2.getFirst(), 2) +
                         Math.pow(point1.getLast() - point2.getLast(), 2));
    }

    private void initializeStateVisualization () {
        tooltip.setText(this.toString());
        Tooltip.install(this, tooltip);
    }

    void setImage (String turtleImagePath) {
        Image newImage =
                new Image(this.getClass().getResourceAsStream(turtleImagePath));
        this.setImage(newImage);
    }

    private void setImageByIndex (int shapeKey) {
        if (shapeKey == 0) {
            return;
        }
        setImage(IMAGE_EXTENSION + shape.getString(Integer.toString(shapeKey)));
    }

    void setPenColor (Color color) {
        penColor = color;
    }

    Color getPenColor () {
        return penColor;
    }

    boolean isPenDown () {
        return penDown;
    }

    void reset () {
        setTranslateX(0);
        setTranslateY(0);
        setRotate(0);
        tooltip.setText(this.toString());
    }

    private double displayValue (double value) {
        if (Math.abs(value) < Math.pow(10, -10)) {
            return 0;
        }
        return value;
    }

    ImageView stamp () {
        ImageView stamp = new ImageView();
        stamp.setFitHeight(this.getFitHeight());
        stamp.setFitWidth(this.getFitWidth());
        stamp.setImage(this.getImage());
        stamp.setTranslateX(this.getTranslateX());
        stamp.setTranslateY(this.getTranslateY());
        
        stamp.setRotate(this.getRotate());
        return stamp;
    }

    public String toString () {
        String currentState = "ID: " + id + "\nX: " + displayValue(getTranslateX()) + "" +
                              "\nY: " + displayValue(getTranslateY()) + "\nHeading: " +
                              displayValue(getRotate()) + "\n";
        if (penDown) {
            currentState += "Pen Down";
        }
        else {
            currentState += "Pen Up";
        }

        return currentState;
    }

    private ContextMenu establishContextMenu () {
        ContextMenu context = new ContextMenu();
        MenuItem turtleStatus = new MenuItem(TURTLE_STATUS + id);
        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem visibility = new MenuItem(TOGGLE_VISIBILITY_PROMPT);
        visibility.setOnAction(e -> visibilityAction());

        MenuItem shape = new MenuItem(CHANGE_SHAPE_PROMPT);
        shape.setOnAction(e -> shapeAction());

        MenuItem color = new MenuItem(CHANGE_TURTLE_PEN_COLOR);
        color.setOnAction(e -> colorAction());

        MenuItem stamp = new MenuItem(STAMP);
        stamp.setOnAction(e -> writeToInternalEditor(new StringBuilder(idsToCommands.getString((STAMP)))));

        context.getItems().addAll(turtleStatus,
                                  separator,
                                  visibility,
                                  color,
                                  shape,
                                  stamp);

        return context;
    }
    
    private void colorAction() {
        StringBuilder command = new StringBuilder(idsToCommands.getString(SET_COLOR) + " ");
        Stage colorStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        Palette palette = new Palette();
        palette.setOnAction(ee -> {
            command.append(palette.getValue());
            colorStage.close();
            writeToInternalEditor(command);
        });
        root.getChildren().add(palette);
        colorStage.setScene(scene);
        colorStage.show();
    }
    
    private void shapeAction() {
        StringBuilder command = new StringBuilder(idsToCommands.getString(SET_SHAPE) + " ");
        TurtlePicker turtlePicker = new TurtlePicker(1);

        turtlePicker.makeStage();
        turtlePicker.showAndWait();

        if (turtlePicker.getValue() != null && Integer.parseInt(turtlePicker.getValue()) != 0) {
            command.append(turtlePicker.getValue());
            writeToInternalEditor(command);
        }
    }
    
    private void visibilityAction() {
        String key = null;
        visible = !visible;
        if (visible) {
            key = "ShowTurtle";
        }
        else {
            key = "HideTurtle";
        }
        StringBuilder command = new StringBuilder(idsToCommands.getString(key));
        writeToInternalEditor(command);
    }

    private void writeToInternalEditor (StringBuilder command) {
        StringBuilder executeCommand =
                new StringBuilder(idsToCommands.getString("Tell") + " " + id + " ] ");
        executeCommand.append(command);
        internalEditor.setEditorText(executeCommand);
    }

    int getTurtleId () {
        return id;
    }

    String getPenWidth () {
        return Double.toString(penWidth);
    }

    String getPenColorIndex () {
        return Integer.toString(penColorIndex.getValue());
    }

    int getShapeIndex () {
        return shapeIndex.getValue();
    }

	public void setPenWidth(double strokeWidth) {
		penWidth = strokeWidth;
	}

}
