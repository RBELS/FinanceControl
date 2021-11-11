package com.example.financecontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FinanceControlApplication class which extends an Application class and from which the main view of the program launches
 * @author Dana
 * @version 1.0
 */
public class FinanceControlApplication extends Application {
    /**
     * start method which sets and shows the main view of the program
     * @param stage the main Stage object is passed to the start () method, when the application starts, this object defines the main window or screen of the program
     * @throws IOException when the I/O operations were failed or interrupted
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinanceControlApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage.setTitle("Financial Control");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * main () method from which Java program execution starts
     * @param args command line arguments as an array of String objects
     */
    public static void main(String[] args) {
        launch();
    }
}