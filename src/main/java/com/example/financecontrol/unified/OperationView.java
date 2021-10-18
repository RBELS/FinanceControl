package com.example.financecontrol.unified;

import com.example.financecontrol.FinanceControlApplication;
import com.example.financecontrol.FinanceControlModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class OperationView {
    private final Button openBt;
    private final Stage stage;
    private final FinanceControlModel model;
    private final Logger logger = Logger.getLogger(getClass().getName());


    public OperationView(Button openBt, Text balance, int type) throws IOException {
        this.openBt = openBt;
        model = new FinanceControlModel();
        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource((type == 0 ? "expenses" : "income") + "-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage = new Stage();
        stage.setTitle(type == 0 ? "Expenses" : "Income");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            openBt.setDisable(false);
            try {
                balance.setText("Balance: "+model.getBalance()+"$");
            } catch (SQLException e) {
                logger.info(e.toString());
            }
        });
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}
