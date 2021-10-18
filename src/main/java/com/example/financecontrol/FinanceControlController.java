package com.example.financecontrol;

import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.unified.OperationView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class FinanceControlController implements Initializable {
    private final int EXPENSES = 0, INCOME = 1;

    private OperationView expensesView;
    private OperationView incomeView;

    private final FinanceControlModel model;
    private List<OperationItem> operationItemList;

    @FXML
    private Button expensesBt;
    @FXML
    private Button incomeBt;
    @FXML
    private Button expensesChart;
    @FXML
    private Button incomeChart;
    @FXML
    private AnchorPane chartPane;
    @FXML
    private Text balance;

    @FXML
    protected void onExpensesButtonClick() { // make fields private
        expensesView.show();
    }

    @FXML
    protected void onIncomeButtonClick() {
        incomeView.show();
    }

    @FXML
    protected void onExpensesChartButtonClick() throws SQLException {
        chartPane.getChildren().clear();
        showDayChart(EXPENSES);
    }

    @FXML
    protected void onIncomeChartButtonClick() throws SQLException {
        chartPane.getChildren().clear();
        showDayChart(INCOME);
    }

    public FinanceControlController() {
        model = new FinanceControlModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expensesBt.setFocusTraversable(false);
        incomeBt.setFocusTraversable(false);
        expensesChart.setFocusTraversable(false);
        incomeChart.setFocusTraversable(false);

        try {
            expensesView = new OperationView(expensesBt, balance, EXPENSES);
            incomeView = new OperationView(incomeBt, balance, INCOME);
            showDayChart(EXPENSES);
            updateBalance();
        } catch (Exception e) {
            e.printStackTrace();//
        }
    }

    private void showDayChart(int type) throws SQLException {
        PieChart dayChart = new PieChart();
        dayChart.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("charts/chart-style.css")).toExternalForm());
        dayChart.setTitle("Day " + (type == EXPENSES ? "Expenses" : "Income"));
        dayChart.setLayoutX(100);
        dayChart.setMaxHeight(360);

        operationItemList = model.getOperations(type);
        List<PieChart.Data> dayChartItemList = operationItemList.size() == 0 ?
                new ArrayList<>() :
                groupExpenses(operationItemList);

        dayChart.setData(FXCollections.observableArrayList(
                dayChartItemList
        ));


        dayChartItemList.forEach(item -> {
            String color = "#FFFFFF";
            for (OperationItem operationItem : operationItemList) {
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

    public void updateBalance() throws SQLException {
        this.balance.setText("Balance: "+model.getBalance()+"$");
    }
}