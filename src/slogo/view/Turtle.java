package slogo.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Turtle extends ImageView {

    private static final double DEFAULT_SPEED = 50;
    private static int INSTANCE_COUNTER;

    private double mySpeed;
    private int id;
    private boolean active;

    public Turtle () {
        Image turtleImage =
                new Image(this.getClass()
                        .getResourceAsStream("/resources/images/turtle-default.png"));
        this.setImage(turtleImage);

        this.mySpeed = DEFAULT_SPEED;
        INSTANCE_COUNTER++;
        setId();
        active = true;
    }

    public void setSpeed (double speed) {

        this.mySpeed = speed;
    }

    public double getSpeed () {
        return mySpeed;
    }

    private void setId () {
        id = INSTANCE_COUNTER;
    }

    public int getTurtleId () {
        return id;
    }

    private void toggleActive () {
        active = (!active);
    }

    public boolean getActive () {
        return active;
    }

    public String toString () {
        return "X: " + getTranslateX() + " Y: " + getTranslateY() + " Heading: " + getRotate();
    }
}
