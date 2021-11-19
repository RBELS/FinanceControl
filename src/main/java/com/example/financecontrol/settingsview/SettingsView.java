package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlApplication;
import com.example.financecontrol.FinanceControlController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;

/**
 * SettingsView class which sets the settings window view
 * @author Dana
 * @version 1.0
 */
public class SettingsView {
    /**
     * scene - a {@link Scene} type object
     */
    public static Scene scene;
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private final Logger logger = Logger.getLogger(getClass().getName());
    /**
     * stage - a {@link Stage} type object
     */
    private final Stage stage;

    /**
     * SettingsView constructor which creates and sets the view of the settings window
     * @param controller a variable of a {@link FinanceControlController} class type
     * @throws IOException when the I/O operations were failed or interrupted
     */
    public SettingsView(FinanceControlController controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinanceControlApplication.class.getResource("settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 170);
        scene.getRoot().setStyle("-fx-font-family: 'serif';");
        stage = new Stage();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth()) / 3.7);
        stage.setY((screenBounds.getHeight()) / 5.3);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            try {
                controller.updateBalance();
                controller.updateBalance();
                controller.showChart(controller.getCurrentOperationType(), controller.getCurrentChartType());
            } catch (SQLException | ParseException e) {
                logger.info(e.toString());
            }
        });
    }

    /**
     * show method which uses method {@link Stage#show()} to show the window
     */
    public void show() {
        stage.show();
    }


}









