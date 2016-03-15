package slogo.view;

import generic.Pair;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Deprecated
 * Using Palette class instead now 
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */

public class ColorPickerStage extends CustomDialog {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;
    
    private Color colorChosen;

    private static final String TITLE = "Pick your color";

    private String context;
    
    private Button OK;

    private GraphicsWindow graphicsWindow;

    public ColorPickerStage (GraphicsWindow graphicsWindow) {
        super(graphicsWindow);

        this.graphicsWindow = graphicsWindow;
        
    }

    @Override
    public Pair<Scene, String> makeStage () {

        BorderPane root = new BorderPane();

        root.setCenter(putColorPicker());
        root.setBottom(putButtons());

        Scene myScene = new Scene(root, WIDTH, HEIGHT);

        return new Pair<Scene, String>(myScene, TITLE);
    }

    @Override
    protected void setContext (String context) {
        this.context = context;
    }

    private ColorPicker putColorPicker () {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(event -> {
        	colorChosen = colorPicker.getValue();
        });
        return colorPicker;
    }

    private HBox putButtons () {
        HBox row = new HBox();
        OK = new Button("OK");
        OK.setId("OK");
        OK.setOnAction(e -> {
            switch (context) {
            case "background":
                graphicsWindow.setBackgroundColor(colorChosen);
                break;
            case "pen":
                graphicsWindow.setPenColor(colorChosen);
            }
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
