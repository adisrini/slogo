package slogo.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;


public class SwitchButton extends Label {
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

    public SwitchButton () {
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(40);
        switchBtn.setPrefHeight(this.getHeight());
        switchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent t) {
                switchedOn.set(!switchedOn.get());
            }
        });

        setGraphic(switchBtn);

        switchedOn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed (ObservableValue<? extends Boolean> ov,
                                 Boolean t,
                                 Boolean t1) {
                if (t1) {
                    setText("ON");
                    setStyle("-fx-background-color: green;-fx-text-fill:white;");
                    setContentDisplay(ContentDisplay.RIGHT);
                }
                else {
                    setText("OFF");
                    setStyle("-fx-background-color: grey;-fx-text-fill:black;");
                    setContentDisplay(ContentDisplay.LEFT);
                }
            }
        });

        switchedOn.set(false);
    }

    boolean switchOnProperty () {
        return switchedOn.getValue();
    }
    
    void setOn(boolean on){
        switchedOn.setValue(on);
    }
}
