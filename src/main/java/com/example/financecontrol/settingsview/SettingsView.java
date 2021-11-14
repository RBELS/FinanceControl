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

public class SettingsView {
    public static Scene scene;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;

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
                controller.showChart(controller.currentOperationType, controller.currentChartType);
            } catch (SQLException | ParseException e) {
                logger.info(e.toString());
            }
        });
    }

    public void show() {
        stage.show();
    }


}









