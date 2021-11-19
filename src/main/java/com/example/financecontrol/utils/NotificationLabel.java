package com.example.financecontrol.utils;

import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

/**
 * NotificationLabel class which sets the style of succeed or failed label and shows it
 * @author Dana
 * @version 1.0
 */
public class NotificationLabel {
    /**
     * label - a {@link Label} type object
     */
    private final Label label;

    /**
     * NotificationLabel constructor which initializes text of the label, it's type(success or error), and position
     * @param text a string variable which sets a text inside the label
     * @param success a boolean variable which defines the type of label
     * @param transX an int variable which sets x coordinate of the label position
     * @param transY an int variable which sets y coordinate of the label position
     */
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

    /**
     * getLabel method which gets a {@link NotificationLabel#label} object
     * @return a label object
     */
    public Label getLabel() {
        return label;
    }

    /**
     * show method which sets style and shows the label for a certain period of time
     */
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