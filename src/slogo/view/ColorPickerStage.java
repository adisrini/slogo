package slogo.view;

import generic.Pair;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;


public class ColorPickerStage extends CustomDialog {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;

    private static final String TITLE = "Pick your color";

    private String context;

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
            switch (context) {
                case "background":
                    graphicsWindow.setBackgroundColor(colorPicker.getValue());
                    break;
                case "pen":
                    graphicsWindow.setPenColor(colorPicker.getValue());
            }
        });
        return colorPicker;
    }

    private HBox putButtons () {
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
