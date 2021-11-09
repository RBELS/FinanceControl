package com.example.financecontrol.utils;

import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationLabel {
    private Label label;

    public NotificationLabel(String text, boolean success, int transX, int transY) {
        this.label = new Label(text);
        this.label.setVisible(false);
        this.label.getStyleClass().add("notificationLabel");
        this.label.getStyleClass().add(success ? "ok" : "error");
        this.label.setStyle("-fx-border-color: red");
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
                opacity-=0.05;

                if(opacity <= 0.001) {
                    label.setStyle("-fx-opacity: 0");
                    label.setVisible(false);
                    this.cancel();
                }
            }
        }, 2000, 50);
    }
}
