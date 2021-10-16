package com.example.financecontrol;

import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.expensesview.ExpensesView;
import com.example.financecontrol.incomeview.IncomeView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FinanceControlController implements Initializable {
    private ExpensesView expensesView;
    private IncomeView incomeView;

    private final FinanceControlModel model;
    private List<OperationItem> expensesItemList;

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

    public FinanceControlController() {
        model = new FinanceControlModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesBt.setFocusTraversable(false);
        incomeBt.setFocusTraversable(false);
        try {
            expensesView = new ExpensesView(expensesBt);
            incomeView = new IncomeView(incomeBt);
            showDayChart();
        } catch (Exception e) {
            e.printStackTrace();//
        }
    }

    private void showDayChart() throws SQLException {
        PieChart dayChart = new PieChart();
        dayChart.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("charts/chart-style.css")).toExternalForm());
        dayChart.setTitle("Day Expenses");
        dayChart.setLayoutX(100);

        expensesItemList = model.getExpenses();
        List<PieChart.Data> dayChartItemList = groupExpenses(expensesItemList);

        dayChart.setData(FXCollections.observableArrayList(
                dayChartItemList
        ));

        dayChartItemList.forEach(item -> {
            String color = "#FFFFFF";
            for (OperationItem operationItem : expensesItemList) {
                if(operationItem.getCategory().equals(item.getName())) {
                    color = operationItem.getCategoryColor();
                    break;
                }
            }
            item.getNode().setStyle("-fx-background-color: " + color + ";");
        });

        chartPane.getChildren().add(dayChart);
    }

    private ArrayList<PieChart.Data> groupExpenses(List<OperationItem> list) { //Group ExpensesItem and IncomeItem
        ArrayList<PieChart.Data> returnList = new ArrayList<>();
        PieChart.Data data;
        int sum = 0;
        String categBuf = list.get(0).getCategory();
        for (OperationItem item : list) {
            if (!categBuf.equals(item.getCategory())) {
                data = new PieChart.Data(categBuf, sum);
                returnList.add(data);
                categBuf = item.getCategory();
                sum = 0;
            }
            sum = sum + item.getPrice();
        }
        data = new PieChart.Data(categBuf, sum);
        returnList.add(data);
        return returnList;
    }
}