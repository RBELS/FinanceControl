package com.example.financecontrol.unified;

import com.example.financecontrol.ControllerFinals;
import com.example.financecontrol.FinanceControlApplication;
import com.example.financecontrol.FinanceControlController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;

/**
 * OperationView class which sets the expenses/income windows view
 * @author Dana
 * @version 1.0
 */
public class OperationView {
    /**
     * openBt - a {@link Button} type object
     */
    private final Button openBt;
    /**
     * stage - a {@link Stage} type object
     */
    private final Stage stage;
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * OperationView constructor which creates and sets the view of the expenses/income windows
     * @param controller a variable of a {@link FinanceControlController} class type
     * @param type an int variable which chooses the operation(0 - expenses, 1 - income)
     * @throws IOException when the I/O operations were failed or interrupted
     */
    public OperationView(FinanceControlController controller, int type) throws IOException {
        this.openBt = type == ControllerFinals.EXPENSES ? controller.expensesBt : controller.incomeBt;
        FXMLLoader fxmlLoader = new FXMLLoader((FinanceControlApplication
                .class.getResource((type == 0 ? "expenses" : "income") + "-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage = new Stage();
        stage.setTitle(type == ControllerFinals.EXPENSES ? "Expenses" : "Income");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {

            try {
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
