package slogo.tester;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
/**
 * @web http://java-buddy.blogspot.com/
 */
public class CanvasWriterTester extends Application {
     
    final static int CANVAS_WIDTH = 400;
    final static int CANVAS_HEIGHT = 400;
     
    ColorPicker colorPicker;
 
    @Override
    public void start(final Stage primaryStage) {
 
        final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);
         
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.stroke();
            }
        });
         
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.stroke();
            }
        });
 
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
 
            }
        });
 
        Group root = new Group();
         
        Button buttonSave = new Button("Save");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                 
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
               
                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);
                 
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(CanvasWriterTester.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
             
        });
         
        HBox hBox = new HBox();
        hBox.getChildren().addAll(colorPicker, buttonSave);
 
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, canvas);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 400, 425);
        primaryStage.setTitle("java-buddy.blogspot.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
     
    private void initDraw(GraphicsContext gc){
         
        colorPicker = new ColorPicker();
         
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
         
        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
 
        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
 
        gc.setFill(colorPicker.getValue());
        gc.setStroke(colorPicker.getValue());
        gc.setLineWidth(1);
    }
     
}