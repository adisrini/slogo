package application;

import com.memetix.mst.translate.Translate;
import javafx.application.Application;
import javafx.stage.Stage;
import slogo.controller.Controller;


/**
 * The Main Application class that starts the program.
 * 
 * @author Arjun Desai, Aditya Srinivasan, Krista Opsahl-Ong, Tavo Loaiza
 *
 */
public class Main extends Application {
	
    @Override
    public void start (Stage primaryStage) {
        try {
            Translate.setClientId("slogo");
            Translate.setClientSecret("AOybC0uSE9HzZxdFuZh6Sgw7KX+qzd1Ypih/aLRLAVE=");
            
            new Controller(primaryStage);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
