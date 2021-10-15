package com.example.financecontrol.expensesview;

import com.example.financecontrol.FinanceControlApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpensesView {
    private final Button openBt;
    private final Stage stage;

    public ExpensesView(Button openBt) throws IOException {
        this.openBt = openBt;

        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource("expenses-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage = new Stage();
        stage.setTitle("Expenses");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> openBt.setDisable(false));
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}