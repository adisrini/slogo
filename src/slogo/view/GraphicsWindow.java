package slogo.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import generic.Pair;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import slogo.model.IDisplayState;
import slogo.model.ITurtleState;

/**
 * The graphics window view component.
 * This is the bulk of the front-end, as it displays what
 * the turtle is instructed to do.
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
public class GraphicsWindow extends StackPane implements IGraphicsWindow, Displayable, Updatable {

    private Canvas myCanvas;
    private GraphicsContext myGC;
    private SimpleIntegerProperty backgroundColorIndex;
    private Map<Integer, Stack<Pair<ITurtleState, Node>>> nodes;
    private List<Node> stamps;
    private Map<Integer, TurtleView> turtleMap;
    private InternalEditor internalEditor;
    private ResourceBundle palette;
    private static final String PALETTE_PROPERTIES = "resources/general/Palette";
    private static final String PEN_STYLE_PROPERTIES = "resources/general/PenStyle";
    private static final Color DEFAULT_BG_COLOR = Color.WHITE;
    private static final String DEFAULT_STYLE = "SOLID";

    /**
     * Instantiates the graphics window with a given width and height, and
     * the number of turtles to place.
     * 
     * @param paneWidth
     * @param paneHeight
     * @param numTurtles
     */
    public GraphicsWindow (double paneWidth, double paneHeight, int numTurtles) {
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        palette = ResourceBundle.getBundle(PALETTE_PROPERTIES);
        
        initializeBackgroundColorIndex();

        nodes = new HashMap<Integer, Stack<Pair<ITurtleState, Node>>>();
        stamps = new ArrayList<Node>();
        turtleMap = new HashMap<Integer, TurtleView>();

        initializeCanvas(paneWidth, paneHeight);

        setBackgroundColor(DEFAULT_BG_COLOR);
        internalEditor = InternalEditor.getInstance();

        initialTurtleFactory(numTurtles);
    }

    /**
     * Initializes the background color index.
     */
    @SuppressWarnings({ "rawtypes", "unused" })
	private void initializeBackgroundColorIndex () {
        backgroundColorIndex = new SimpleIntegerProperty();
        final ChangeListener changeListener = new ChangeListener() {

            @Override
            public void changed (ObservableValue observable, Object oldValue, Object newValue) {
                Integer value = (Integer) newValue;
                if (value == 0) {
                    setBackgroundColor(DEFAULT_BG_COLOR);
                }
                else {
                    setBackgroundColor(Color.valueOf(palette.getString(Integer.toString((value)))));
                }
            }
        };
    }

    /**
     * Positions and styles the pen according to the information
     * from the turtle.
     * 
     * @param startx - starting X position
     * @param starty - starting Y position
     * @param endx - ending X position
     * @param endy - ending Y position
     * @param penColor - color of the stroke
     * @param penWidth - width of the stroke
     * @param penStyleIndex - style index from properties file
     * @return the positioned and styled line
     */
    private Line positionedLine (double startx,
                                 double starty,
                                 double endx,
                                 double endy,
                                 Color penColor,
                                 String penWidth,
                                 int penStyleIndex) {

        Line line = new Line(startx, starty, endx, endy);
        line.setTranslateX(endx - (endx - startx) / 2);
        line.setTranslateY(endy - (endy - starty) / 2);
        line.setStroke(penColor);
        line.setStrokeWidth(Double.parseDouble(penWidth));
        line.getStrokeDashArray().clear();
        
        for(Double d : assignDashArray(penStyleIndex)) {
        	line.getStrokeDashArray().add(d);
        }
        
        return line;
    }
    
    /**
     * Returns the dash array corresponding to the pen style
     * index specified.
     * @param penStyleIndex
     * @return
     */
    private List<Double> assignDashArray(int penStyleIndex) {
    	ResourceBundle styles = ResourceBundle.getBundle(PEN_STYLE_PROPERTIES);
        String styleName = "";
        
        for(String key : styles.keySet()) {
        	if(key.equals(Integer.toString(penStyleIndex))) {
        		styleName = styles.getString(key);
        	}
        }
        
        if(styleName.equals("")) {
        	styleName = DEFAULT_STYLE;
        }
        
        LineStyle lineStyle = LineStyle.valueOf(styleName);
        
        return lineStyle.getDashArray();
    }

    /**
     * Initializes the canvas and sets a default background color of
     * white, then adds to the parent StackPane
     * 
     * @param paneWidth
     * @param paneHeight
     */
    private void initializeCanvas (double paneWidth, double paneHeight) {
        myCanvas = new Canvas(paneWidth, paneHeight);
        myGC = myCanvas.getGraphicsContext2D();

        myGC.setFill(Color.BLACK);
        myGC.fillRect(0, 0, paneWidth, paneHeight);

        this.getChildren().add(myCanvas);
    }

    /**
     * Changes the background color of the window in which the turtle
     * moves.
     */
    @Override
    public void setBackgroundColor (Color color) {
        myGC.setFill(color);
        myGC.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    }

    /**
     * Changes the image of the turtle to another image as given by a
     * string to indicate the image name.
     */
    @Override
    public void setTurtleImage (String turtleImage) {

    }

    /**
     * Displays the Object given to the graphics window
     */
    @Override
    public void display (Object object) {
        this.getChildren().add((Node) object);
    }

    /**
     * Updates the turtle's positions and adds a line/stamp to
     * the window if needed.
     * @param state
     */
    private void updateTurtle (ITurtleState state) {
    	initializeTurtle(state);
        TurtleView turtle = turtleMap.get(state.getId());

        if (state.getHeading() > 360) {
            undo(state); turtle.reset(); return;
        }

        double startX = turtle.getTranslateX(); double startY = turtle.getTranslateY();
        double endX = state.getX(); double endY = state.getY();

        turtle.update(state);
        Node line = null;
        if(turtle.isPenDown()) {
            line = positionedLine(startX, startY, endX, endY, turtle.getPenColor(), turtle.getPenWidth(), turtle.getPenStyleIndex());
        }

        addNode(state, line);
        if (state.hasStamp()){
            addNode(state, turtle.stamp());
            stamps.add(turtle.stamp());
        }
    }
    
    /**
     * Initializes the turtle if it is not already initialized.
     * @param state
     */
    private void initializeTurtle(ITurtleState state) {
    	if (!turtleMap.containsKey(state.getId())) {
            turtleMap.put(state.getId(), initializeTurtle(state.getId()));
        }
    }
    
    /**
     * Clears all stamps from the graphics window.
     */
    private void clearStamps(){
        for(Node node : stamps){
            this.getChildren().remove(node);
        }
        stamps.clear();
    }

    /**
     * Updates the display if the state is a DisplayState
     * @param state
     */
    private void updateDisplay (IDisplayState state) {
        backgroundColorIndex.setValue(state.getBackgroundIndex());
        if (state.getClearStamps()){
            clearStamps();
        }
    }

    /**
     * Takes in a State object and transforms the position and rotation
     * of the turtle accordingly.
     */
    @Override
    public void update (Object object) {

        if (object instanceof ITurtleState) {
            updateTurtle((ITurtleState) object);
        }
        else if (object instanceof IDisplayState) {
            updateDisplay((IDisplayState) object);
        }

    }

    /**
     * Undoes the last state and reverts to the previous one
     * @param state
     */
    void undo (ITurtleState state) {
        Stack<Pair<ITurtleState, Node>> nodeStack = nodes.get(state.getId());

        while (!nodeStack.isEmpty() && nodeStack.peek().getFirst() != state) {

            removeNode(nodeStack);
        }

        turtleMap.get(state.getId()).update(state);

    }

    /**
     * Move turtle to original position
     * Remove all lines drawn
     */
    void reset () {
        for (Integer key : nodes.keySet()) {
            Stack<Pair<ITurtleState, Node>> nodeStack = nodes.get(key);

            while (!nodeStack.isEmpty()) {
                removeNode(nodeStack);
            }

        }
    }

    /**
     * Removes the top node from the stack of nodes.
     * @param nodeStack
     */
    private void removeNode (Stack<Pair<ITurtleState, Node>> nodeStack) {
        Pair<ITurtleState, Node> pair = nodeStack.pop();
        TurtleView turtle = turtleMap.get(pair.getFirst().getId());
        turtle.update(pair.getFirst());
        this.getChildren().remove(pair.getLast());
    }

    /**
     * Adds a node to the top of the stack of nodes
     * @param state
     * @param nodeToAdd
     */
    private void addNode (ITurtleState state, Node nodeToAdd) {
        if (!nodes.containsKey(state.getId())) {
            nodes.put(state.getId(), new Stack<Pair<ITurtleState, Node>>());
        }
        Stack<Pair<ITurtleState, Node>> nodeStack = nodes.get(state.getId());
        nodeStack.push(new Pair<ITurtleState, Node>(state, nodeToAdd));
        nodes.put(state.getId(), nodeStack);
        this.getChildren().add(nodeToAdd);
    }

    /**
     * Initializes the factory for which to keep track of active turtles.
     * @param numTurtles
     */
    void initialTurtleFactory (int numTurtles) {
        StringBuilder initializedTurtles = new StringBuilder();
        initializedTurtles.append("TELL [ ");

        for (int id = 1; id <= numTurtles; id++) {
            turtleMap.put(id, initializeTurtle(id));
            initializedTurtles.append(id + " ");
        }
        initializedTurtles.append(']');
        internalEditor.setEditorText(initializedTurtles);
    }

    /**
     * Initializes the Turtle class based on a passed id
     * StackPane.
     */
    private TurtleView initializeTurtle (int id) {
        TurtleView turtle = new TurtleView(id);
        turtle.setFitWidth(100);
        turtle.setFitHeight(100);
        StackPane.setAlignment(turtle, Pos.CENTER);
        display(turtle);
        return turtle;
    }

    /**
     * Sets speed for all turtles
     * 
     * @param speed
     */
    void setSpeed (double speed) {
        for (Integer key : turtleMap.keySet()) {
            turtleMap.get(key).setSpeed(speed);
        }
    }

    /**
     * Sets pen color for all turtles
     * 
     */
    @Override
    public void setPenColor (Color color) {
        for (Integer key : turtleMap.keySet()) {
            turtleMap.get(key).setPenColor(color);
        }
    }

    /**
     * Gets the frame rate of the turtle state
     * @param state
     * @return
     */
    double getFrameRate (ITurtleState state) {
        return turtleMap.get(state.getId()).getFrameRate(state);
    }

    /**
     * Returns an unmodifiable map of active turtles
     * @return
     */
    Map<Integer, TurtleView> getTurtles () {
        return Collections.unmodifiableMap(turtleMap);
    }

    /**
     * Gets the turtle corresponding to a particular ID.
     * @param id
     * @return
     */
    TurtleView getTurtle (int id) {
        return turtleMap.get(id);
    }
    
    /**
     * Gets the total number of active turtles.
     * @return
     */
    int getNumTurtles(){
        return turtleMap.keySet().size();
    }
    
    /**
     * Gets the current background color index.
     * @return
     */
    int getBackgroundColorIndex(){
        return this.backgroundColorIndex.getValue();
    }
    
    /**
     * Sets the current background color index.
     * @param index
     */
    void setBackgroundColorIndex(int index){
        backgroundColorIndex.setValue(index);
    }
}
