package com.example.financecontrol.unified;

import com.example.financecontrol.FinanceControlApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OperationView {
    private final Button openBt;
    private final Stage stage;

    public OperationView(Button openBt, int type) throws IOException {
        this.openBt = openBt;

        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource((type == 0 ? "expenses" : "income") + "-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage = new Stage();
        stage.setTitle(type == 0 ? "Expenses" : "Income");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> openBt.setDisable(false));
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}
