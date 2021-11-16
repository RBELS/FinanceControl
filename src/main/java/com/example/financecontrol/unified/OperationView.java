package com.example.financecontrol.unified;

import com.example.financecontrol.ControllerFinals;
import com.example.financecontrol.FinanceControlApplication;
import com.example.financecontrol.FinanceControlController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;

public class OperationView {
    private final Button openBt;
    private final Stage stage;
    private final Logger logger = Logger.getLogger(getClass().getName());


    public OperationView(FinanceControlController controller, int type) throws IOException {
        this.openBt = type == ControllerFinals.EXPENSES ? controller.expensesBt : controller.incomeBt;
        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource((type == 0 ? "expenses" : "income") + "-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage = new Stage();
        stage.setTitle(type == ControllerFinals.EXPENSES ? "Expenses" : "Income");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            openBt.setDisable(false);
            try {
                controller.updateBalance();
                controller.showChart(controller.getCurrentOperationType(), controller.getCurrentChartType());
            } catch (SQLException | ParseException e) {
                logger.info(e.toString());
            }
        });
    }

    public void show() {
        openBt.setDisable(true);
        stage.show();
    }
}
