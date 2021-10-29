package com.example.financecontrol.settings;

import com.example.financecontrol.FinanceControlApplication;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings{
        public static Scene scene;

    private final Stage stage1;
            public Settings() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(FinanceControlApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 170, 169);
        scene.getRoot().setStyle("-fx-font-family: 'serif';");
        stage1 = new Stage();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage1.setX((screenBounds.getWidth()) / 3.7);
        stage1.setY((screenBounds.getHeight()) / 5.3);
        stage1.setTitle("Settings");
        stage1.setScene(scene);
        stage1.setResizable(false);
            }

    public void show() {

        stage1.show();
    }



}









