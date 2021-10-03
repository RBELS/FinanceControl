package com.example.financecontrol;

import com.example.financecontrol.expensesview.ExpensesView;
import com.example.financecontrol.incomeview.IncomeView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FinanceControlController implements Initializable {
    private ExpensesView expensesView;
    private IncomeView incomeView;

    @FXML
    private Button expensesBt;
    @FXML
    private Button incomeBt;
    @FXML
    private AnchorPane chartPane;

    @FXML
    protected void onExpensesButtonClick() { // make fields private
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
            e.printStackTrace();//
        }
        showDayChart();
    }

    private void showDayChart() {
        PieChart dayChart = new PieChart();
        dayChart.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("charts/chart-style.css")).toExternalForm());
        dayChart.setTitle("Day Expenses");
        dayChart.setLayoutX(100);

        PieChart.Data data1 = new PieChart.Data("Transport", 20);
        PieChart.Data data2 = new PieChart.Data("Food", 20);
        PieChart.Data data3 = new PieChart.Data("Clothes", 20);

        dayChart.setData(FXCollections.observableArrayList(
                data1,
                data2,
                data3
        ));

        data2.getNode().setStyle("-fx-background-color: #00FF00;");
        data1.getNode().setStyle("-fx-background-color: #FF0000;");
        data3.getNode().setStyle("-fx-background-color: #0000FF;");

        chartPane.getChildren().add(dayChart);
    }
}