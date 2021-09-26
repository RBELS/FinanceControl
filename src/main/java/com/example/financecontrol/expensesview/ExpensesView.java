package com.example.financecontrol.expensesview;

import com.example.financecontrol.FinanceControlApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ExpensesView {
    private Button openBt;
    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Stage stage;

    private final String TITLE = "Expenses";

    public ExpensesView(Button openBt) throws IOException {
        this.openBt = openBt;

        fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource("expenses-view.fxml")));
        scene = new Scene(fxmlLoader.load(), 300, 200);
        stage = new Stage();
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                openBt.setDisable(false);
            }
        });
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}