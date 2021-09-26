package com.example.financecontrol;

import com.example.financecontrol.expensesview.ExpensesView;
import com.example.financecontrol.incomeview.IncomeView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinanceControlController implements Initializable {
    private FinanceControlModel model = new FinanceControlModel();
    private ExpensesView expensesView;
    private IncomeView incomeView;

    @FXML
    private Button expensesBt;

    @FXML
    private Button incomeBt;

    @FXML
    protected void onExpensesButtonClick() throws IOException { // make fields private
        expensesView.show();
    }

    @FXML
    protected void onIncomeButtonClick() {
        incomeView.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesBt.setFocusTraversable(false);
        incomeBt.setFocusTraversable(false);
        try {
            expensesView = new ExpensesView(expensesBt);
            incomeView = new IncomeView(incomeBt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}