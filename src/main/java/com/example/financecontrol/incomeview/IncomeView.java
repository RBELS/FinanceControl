package com.example.financecontrol.incomeview;

import com.example.financecontrol.FinanceControlApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class IncomeView {
    private final Button openBt;
    private final Stage stage;

    public IncomeView(Button openBt) throws IOException {
        this.openBt = openBt;

        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource("income-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage = new Stage();
        stage.setTitle("Income");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> openBt.setDisable(false));
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}
