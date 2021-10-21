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
import java.util.logging.Logger;

public class FinanceControlController implements Initializable {
    public int currentOperationType;
    public int currentChartType;

    private OperationView expensesView;
    private OperationView incomeView;

    private final FinanceControlModel model;
    private List<OperationItem> operationItemList;
    private final Logger logger = Logger.getLogger(getClass().getName());

    @FXML public Button expensesBt;
    @FXML public Button incomeBt;
    @FXML private Button expensesChart;
    @FXML private Button incomeChart;
    @FXML private AnchorPane chartPane;
    @FXML public Text balance;

    @FXML protected void onExpensesButtonClick() { // make fields private
        expensesView.show();
    }
    @FXML protected void onIncomeButtonClick() {
        incomeView.show();
    }
    @FXML protected void onExpensesChartButtonClick() throws SQLException {
        currentOperationType = ControllerFinals.EXPENSES;
        if(currentChartType == ControllerFinals.DAY_CHART) {
            showDayChart(currentOperationType);

        } else {
            showWMYChart(currentOperationType, currentChartType);
        }
    }
    @FXML protected void onIncomeChartButtonClick() throws SQLException {
        currentOperationType = ControllerFinals.INCOME;
        if(currentChartType == ControllerFinals.DAY_CHART) {
            showDayChart(currentOperationType);

        } else {
            showWMYChart(currentOperationType, currentChartType);
        }
    }
    @FXML protected void onBottomDayButtonClick() throws SQLException {
        currentChartType = ControllerFinals.DAY_CHART;
        showDayChart(currentOperationType);
    }
    @FXML protected void onBottomWeekButtonClick() {
        currentChartType = ControllerFinals.WEEK_CHART;
        showWMYChart(currentOperationType, currentChartType);
    }
    @FXML protected void onBottomMonthButtonClick() {
        currentChartType = ControllerFinals.MONTH_CHART;
        showWMYChart(currentOperationType, currentChartType);
    }
    @FXML protected void onBottomYearButtonClick() {
        currentChartType = ControllerFinals.YEAR_CHART;
        showWMYChart(currentOperationType, currentChartType);
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
            expensesView = new OperationView(this, ControllerFinals.EXPENSES);
            incomeView = new OperationView(this, ControllerFinals.INCOME);
            currentChartType = ControllerFinals.DAY_CHART;
            currentOperationType = ControllerFinals.EXPENSES;
            showDayChart(currentOperationType);
            updateBalance();
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    public void showDayChart(int type) throws SQLException {
        chartPane.getChildren().clear();
        PieChart dayChart = new PieChart();
        dayChart.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("charts/chart-style.css")).toExternalForm());
        dayChart.setTitle("Day " + (type == ControllerFinals.EXPENSES ? "Expenses" : "Income"));
        dayChart.setLayoutX(100);
        dayChart.setMaxHeight(360);

        operationItemList = model.getOperations(type, ControllerFinals.DAY_CHART);
        List<PieChart.Data> dayChartItemList = operationItemList.size() == 0 ?
                new ArrayList<>() :
                groupExpenses(operationItemList);

        dayChart.setData(FXCollections.observableArrayList(
                dayChartItemList
        ));


        dayChartItemList.forEach(item -> {
            String defaultColor = "#FFFFFF";
            for (OperationItem operationItem : operationItemList) {
                if(operationItem.getCategory().equals(item.getName())) {
                    defaultColor = operationItem.getCategoryColor();
                    break;
                }
            }
            item.getNode().setStyle("-fx-background-color: " + defaultColor + ";");
        });

        chartPane.getChildren().add(dayChart);
    }

    public void showWMYChart(int operationType, int chartType) {
        chartPane.getChildren().clear();
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
        this.balance.setText(model.getBalance()+"$");
    }
}