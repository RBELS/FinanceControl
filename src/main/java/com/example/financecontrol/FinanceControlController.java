package com.example.financecontrol;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FinanceControlController implements Initializable {
    public FinanceControlModel model = new FinanceControlModel();

    @FXML
    private Button expensesBt;

    @FXML
    private Button incomeBt;

    @FXML
    protected void onExpensesButtonClick() {
        model.addExpense("IPhone", 799.99, 2);
    }

    @FXML
    protected void onIncomeButtonClick() {
        model.addIncome("Salary", 1000, 1);
    }

    @FXML
    protected void onTest3ButtonClick() {
//        viewText.setText(Integer.toString(model.getNumberOfRowsExp()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesBt.setFocusTraversable(false);
        incomeBt.setFocusTraversable(false);
//        if(model.isDBConnected()) {
//            statusText.setText("Connected");
//        } else {
//            statusText.setText("Not connected");
//        }
//        viewText.setText(Integer.toString(model.getNumberOfRowsExp()));
    }
}