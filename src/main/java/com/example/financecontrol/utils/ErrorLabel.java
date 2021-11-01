package com.example.financecontrol.utils;

import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class ErrorLabel {
    private Label label;

    public ErrorLabel(String text, int transX, int transY) {
        this.label = new Label(text);
        this.label.setVisible(false);
        this.label.getStyleClass().add("errorLabel");
        this.label.setTranslateX(transX);
        this.label.setTranslateY(transY);
        this.label.setStyle("-fx-opacity: 0");
    }

    public Label getLabel() {
        return label;
    }

    public void show() {
        this.label.setVisible(true);
        final Timer timer = new Timer();
        label.setStyle("-fx-opacity: 1");
        timer.scheduleAtFixedRate(new TimerTask() {
            double opacity = 1.0;
            @Override
            public void run() {
                label.setStyle("-fx-opacity: " + opacity);
                opacity-=0.1;

                if(opacity <= 0.001) {
                    label.setStyle("-fx-opacity: 0");
                    label.setVisible(false);
                    this.cancel();
                }
            }
        }, 2000, 100);
    }
}
