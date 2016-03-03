package slogo.view;

import java.util.Stack;
import generic.Pair;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import slogo.model.IState;


public class GraphicsWindow extends StackPane implements IGraphicsWindow, Displayable, Updatable {

    private Canvas myCanvas;
    private GraphicsContext myGC;
    private Turtle myTurtle;

    private Color lineColor;

    Stack<Pair<IState,Node>> nodes;

    public GraphicsWindow (double paneWidth, double paneHeight) {
        // set dimensions for window
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        nodes = new Stack<Pair<IState,Node>> ();

        initializeCanvas(paneWidth, paneHeight);

        initializeTurtle();

        setBackgroundColor(Color.WHITE);

        lineColor = Color.BLACK;

        /*
         * Line line1 = new Line(0, 0, 0, -100);
         * line1.setTranslateX(0);
         * line1.setTranslateY(-50);
         * Line line2 = new Line(0, -100, -100, -100);
         * line2.setTranslateX(-50);
         * line2.setTranslateY(-100);
         * this.getChildren().addAll(line1, line2);
         */

    }

    private Line positionedLine (double startx, double starty, double endx, double endy) {

        Line line = new Line(startx, starty, endx, endy);
        line.setTranslateX(endx - (endx - startx) / 2);
        line.setTranslateY(endy - (endy - starty) / 2);

        line.setStroke((Paint) lineColor);
        line.setStrokeWidth(3);
        return line;
    }

    /**
     * Initializes the Turtle class and adds it to the
     * StackPane.
     */
    private void initializeTurtle () {
        myTurtle = new Turtle();
        myTurtle.setFitWidth(100);
        myTurtle.setFitHeight(100);
        StackPane.setAlignment(myTurtle, Pos.CENTER);
        display(myTurtle);
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

        // add the canvas to the root
        this.getChildren().add(myCanvas);
    }

    /**
     * Changes the color of the pen that the turtle draws in.
     */
    @Override
    public void setPenColor (Color color) {
        // TODO Auto-generated method stub
        lineColor = color;
    }

    /**
     * Changes the background color of the window in which the turtle
     * moves.
     */
    @Override
    public void setBackgroundColor (Color color) {
        // TODO Auto-generated method stub
        myGC.setFill(color);
        myGC.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
    }

    /**
     * Changes the image of the turtle to another image as given by a
     * string to indicate the image name.
     */
    @Override
    public void setTurtleImage (String turtleImage) {
        // TODO Auto-generated method stub
        Image newImage =
                new Image(this.getClass().getResourceAsStream("/resources/images/" + turtleImage));
        myTurtle.setImage(newImage);
    }

    @Override
    public void display (Object object) {
        // TODO Auto-generated method stub
        this.getChildren().add((Node) object);
    }

    private boolean sameQuantities (double a, double b) {
        System.out.println("A: " + a + " B: " + b);
        if (Math.abs(a - b) < Math.pow(10, -10)) {
            return true;
        }

        return false;
    }

    /**
     * Takes in a State object and transforms the position and rotation
     * of the turtle accordingly.
     */
    @Override
    public void update (Object object) {

        IState state = (IState) object;

        System.out.println(state);
        System.out.println(myTurtle);

        if (state.getHeading() > 360 || state.getHeading()<0) {
            System.out.println("RESET");
            reset();
            return;
        }

        if (sameQuantities(state.getX(), myTurtle.getTranslateX()) &&
            sameQuantities(state.getY(), myTurtle.getTranslateY()) &&
            sameQuantities(state.getHeading(), myTurtle.getRotate())) {
            System.out.println("SAME");
            return;
        }

        double startX = myTurtle.getTranslateX();
        double startY = myTurtle.getTranslateY();

        updateTurtle(state);

        double endX = state.getX();
        double endY = state.getY();
        
        Node line = positionedLine(startX,startY,endX,endY);
        nodes.add(new Pair(state,line));
        
        this.getChildren().add(line);
    }


    Canvas getCanvas () {
        return myCanvas;
    }

    public void updateTurtle (IState state) {

        /*
         * double distance = distance(new Pair<Double, Double>(myTurtle.getTranslateX(),
         * myTurtle.getTranslateY()),
         * new Pair<Double, Double>(state.getX(), state.getY()));
         * double duration = distance*myTurtle.getSpeed();
         * 
         * TranslateTransition tt = new TranslateTransition(Duration.millis(duration), myTurtle);
         * 
         * double currentX = myTurtle.getTranslateX();
         * double currentY = myTurtle.getTranslateY();
         * 
         * tt.setByX(currentX);
         * tt.setByY(currentY);
         * tt.setToX(state.getX());
         * tt.setToY(state.getY());
         * 
         * RotateTransition rt = new RotateTransition(Duration.millis(duration), myTurtle);
         * 
         * double currentHeading = myTurtle.getRotate();
         * 
         * rt.setByAngle(currentHeading);
         * rt.setToAngle(state.getHeading());
         * 
         * ParallelTransition pt = new ParallelTransition();
         * pt.getChildren().addAll(tt, rt);
         * 
         * pt.play();
         */

        myTurtle.setTranslateX(state.getX());
        myTurtle.setTranslateY(state.getY());
        myTurtle.setRotate(state.getHeading());
    }

    public double getFrameRate (IState state) {
        double distance =
                distance(new Pair<Double, Double>(myTurtle.getTranslateX(),
                                                  myTurtle.getTranslateY()),
                         new Pair<Double, Double>(state.getX(), state.getY()));

        double time = distance / myTurtle.getSpeed();

        if (distance == 0) {
            return 100;
        }

        return time;

    }

    private double distance (Pair point1, Pair point2) {
        return Math.sqrt(Math.pow((double) point1.getFirst() - (double) point2.getFirst(), 2) +
                         Math.pow((double) point1.getLast() - (double) point2.getLast(), 2));
    }

    void undoExecution (IState state) {
        if (state == null) {
            reset();
            return;
        }


        System.out.println("undo execution- graphicswindow");

        updateTurtle(state);
    }

    void undoState (IState state) {
        if (state == null) {
            reset();
            return;
        }
        // System.out.println("Execution Counter is: " + executionCounter);

        
        System.out.println("undo state- graphicswindow");

        updateTurtle(state);
    }

    /**
     * Move turtle to original position
     * Remove all lines drawn
     */
    void reset () {
        // Reset turtle's position
        myTurtle.setTranslateX(0);
        myTurtle.setTranslateY(0);
        myTurtle.setRotate(0);
        
        while (!nodes.isEmpty()){
            removeLines();
        }
    }
    
    
    public void setSpeed (double speed) {
        // TODO Auto-generated method stub
        myTurtle.setSpeed(speed);
    }
    
    void undo(IState state){
        while (!nodes.isEmpty() && nodes.peek().getFirst()!=state){
            removeLines();
        }
        
        updateTurtle(state);
    }
    
    private void removeLines(){
        Pair<IState,Node> pair = nodes.pop();
        this.getChildren().remove(pair.getLast());
    }

}
