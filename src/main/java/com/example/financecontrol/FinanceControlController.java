package com.example.financecontrol;

import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.settingsview.SettingsView;
import com.example.financecontrol.unified.OperationView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
/**
 * FinanceControlController class that implements methods connected with interface( implements {@link Initializable} class)
 * @author Dana
 * @version 1.0
 */
public class FinanceControlController implements Initializable {

    /**
     * an object which assigns 86 400 000 to ONE_DAY (an amount of milliseconds in one day)
     */
    private final static long ONE_DAY = 86400000;

    /**
     * currentOperationType - an int object which shows current operation: expenses(0) or income(1)
     */
    private int currentOperationType;
    /**
     * currentChartType - an int object which shows current chart: day(0), week(1), month(2), year(3)
     */
    private int currentChartType;
    /**
     * expensesView - an {@link OperationView} object
     */
    private OperationView expensesView;
    /**
     * incomeView - an {@link OperationView} object
     */
    private OperationView incomeView;
    /**
     * settingsView - a {@link SettingsView} object
     */
    private SettingsView settingsView;

    /**
     * model - a {@link FinanceControlModel} object
     */
    private final FinanceControlModel model;
    /**
     * operationItemList - a list object of an {@link OperationItem} type
     */
    private List<OperationItem> operationItemList;
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private Logger logger;
    /**
     * caption - a {@link Label} object
     */
    private Label caption;
    /**
     * page - an int object which shows the number of page with graphics
     */
    private int page;
            /**
     * containerPane - an {@link AnchorPane} object
     */
        /**
     * mainPane - an {@link AnchorPane} object which contains main pane
     */
    @FXML public AnchorPane Main;
    /**
     * settingsBt - a {@link Button} object which contains settings button info
     */
    @FXML public Button settingsBt;
    /**
     * expensesBt - a {@link Button} object which contains expenses button info
     */
    @FXML public Button expensesBt;
    /**
     * incomeBt - a {@link Button} object which contains income button info
     */
    @FXML public Button incomeBt;
    /**
     * themeBt - a {@link Button} object which contains theme button info
     */
    @FXML public Button themeBt;
    /**
     * expensesChart - a {@link Button} object which contains expenses Chart button info
     */
    @FXML private Button expensesChart;
    /**
     * incomeChart - a {@link Button} object which contains income Chart button info
     */
    @FXML private Button incomeChart;
    /**
     * chartPane - a {@link StackPane} object
     */
    @FXML private StackPane chartPane;
    /**
     * balance - a {@link Text} object which contains 'Balance'
     */
    @FXML public Text balance;
    /**
     * operationListView - a listview object of an {@link AnchorPane} type
     */
    @FXML private ListView<AnchorPane> operationListView;
    /**
     * backBt - a {@link Button} object which contains back button
     */
    @FXML private Button backBt;
    /**
     * forwardBt - a {@link Button} object which contains forward button
     */
    @FXML private Button forwardBt;
    /**
     * pageText - a {@link Text} object which contains page text
     */
    @FXML private Text pageText;



    /**
     * onSettingsButtonClick method which shows the settings windows through class {@link SettingsView} with the help of method {@link SettingsView#show()}
     */
    @FXML
    protected void onSettingsButtonClick()  { // make fields private
        settingsView.show();
    }

    /**
     * onExpensesButtonClick method which shows the expenses windows through class {@link OperationView} with the help of method {@link OperationView#show()}
     */
    @FXML
    protected void onExpensesButtonClick() { // make fields private
        expensesView.show();
    }

    /**
     * onIncomeButtonClick method which shows the income windows through class {@link OperationView} with the help of method {@link OperationView#show()}
     */
    @FXML
    protected void onIncomeButtonClick() {
        incomeView.show();
    }

    /**
     * onExpensesChartButtonClick method which shows different charts (depending on currentChartType) of your expenses (chooses OperationType with the help of class {@link ControllerFinals#EXPENSES}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onExpensesChartButtonClick() throws SQLException, ParseException {
        showChart(ControllerFinals.EXPENSES, currentChartType);
        currentOperationType = ControllerFinals.EXPENSES;
    }

    /**
     * onIncomeChartButtonClick method which shows different charts (depending on currentChartType) of your Income (chooses OperationType with the help of class {@link ControllerFinals#INCOME}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onIncomeChartButtonClick() throws SQLException, ParseException {
        showChart(ControllerFinals.INCOME, currentChartType);
        currentOperationType = ControllerFinals.INCOME;
    }

    /**
     * onBottomDayButtonClick method which shows a day chart of expenses/income (depending on currentOperationType) with the help of class {@link ControllerFinals#DAY_CHART}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onBottomDayButtonClick() throws SQLException, ParseException {
        showChart(currentOperationType, ControllerFinals.DAY_CHART);
        currentChartType = ControllerFinals.DAY_CHART;
    }
    /**
     * onBottomWeekButtonClick method which shows a Week chart of expenses/income (depending on currentOperationType) with the help of class {@link ControllerFinals#WEEK_CHART}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onBottomWeekButtonClick() throws SQLException, ParseException {
        showChart(currentOperationType, ControllerFinals.WEEK_CHART);
        currentChartType = ControllerFinals.WEEK_CHART;
    }
    /**
     * onBottomMonthButtonClick method which shows a Month chart of expenses/income (depending on currentOperationType) with the help of class {@link ControllerFinals#MONTH_CHART}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onBottomMonthButtonClick() throws SQLException, ParseException {
        showChart(currentOperationType, ControllerFinals.MONTH_CHART);
        currentChartType = ControllerFinals.MONTH_CHART;
    }
    /**
     * onBottomYearButtonClick method which shows a Year chart of expenses/income (depending on currentOperationType) with the help of class {@link ControllerFinals#YEAR_CHART}
     * @see FinanceControlController#showChart(int, int)
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML
    protected void onBottomYearButtonClick() throws SQLException, ParseException {
        showChart(currentOperationType, ControllerFinals.YEAR_CHART);
        currentChartType = ControllerFinals.YEAR_CHART;
    }

    /**
     * onBackBtClick method which changes page on previous and shows charts on previous page
     * @throws SQLException  when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML protected void onBackBtClick() throws SQLException, ParseException {
        page-=1;
        pageText.setText(String.valueOf(page));
        showChart(currentOperationType, currentChartType);
    }

    /**
     * onForwardBtClick method which changes page on forward and shows charts on forward page
     * @throws SQLException  when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    @FXML protected void onForwardBtClick() throws SQLException, ParseException {
        page+=1;
        pageText.setText(String.valueOf(page));
        showChart(currentOperationType, currentChartType);
    }

    /**
     * showChart method which chooses day/week/month/year chart to show depending on chartType of expenses/income depending on operationType
     * and shows charts with the help of {@link FinanceControlController#showDayChart(int,int)} method and {@link FinanceControlController#showWMYChart(int, int, int)}
     * @param operationType a type of your operation (0 if expenses, 1 if income)
     * @param chartType a type of your chart (0 if DAY_CHART, 1 if WEEK_CHART, 2 if MONTH_CHART, 3 if YEAR_CHART
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    public void showChart(int operationType, int chartType) throws SQLException, ParseException {
        if(chartType != this.currentChartType || operationType != this.currentOperationType) {
            page = 0;
            pageText.setText(String.valueOf(page));
        }

        if (chartType == ControllerFinals.DAY_CHART) {
            showDayChart(operationType, page);
        } else {
            showWMYChart(operationType, chartType, page);
        }
    }

    /**
     * FinanceControlController constructor which creates new model object with the help of {@link FinanceControlModel#FinanceControlModel()} constructor
     */
    public FinanceControlController() {
        model = new FinanceControlModel();
    }


    /**
     * initialize method which sets initial values and state of buttons and charts, catches an exception in creating new windows of income/expenses/settings,
     * in showing charts {@link FinanceControlController#showChart(int, int)}, in updating balance {@link FinanceControlController#updateBalance()}
     * @param url a URL variable
     * @param resourceBundle a ResourceBundle variable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger = Logger.getLogger(getClass().getName());
        page = 0;
        pageText.setText(String.valueOf(page));
        pageText.setTextAlignment(TextAlignment.CENTER);
        caption = new Label("");
        caption.setStyle(
                "-fx-text-fill: #000000;" +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-padding: 2px;" +
                        "-fx-background-radius: 5px"
        );
        expensesBt.setFocusTraversable(false);
        incomeBt.setFocusTraversable(false);
        expensesChart.setFocusTraversable(false);
        incomeChart.setFocusTraversable(false);

        chartPane.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            caption.setTranslateX(mouseEvent.getX()-300);
            caption.setTranslateY(mouseEvent.getY()-230);
        });

        try {
            expensesView = new OperationView(this, ControllerFinals.EXPENSES);
            incomeView = new OperationView(this, ControllerFinals.INCOME);
            settingsView = new SettingsView(this);

            currentChartType = ControllerFinals.DAY_CHART;
            currentOperationType = ControllerFinals.EXPENSES;
            showChart(currentOperationType, currentChartType);
            updateBalance();
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    /**
     * showDayChart method which sets the style of your day chart and shows it and at the end updates list {@link FinanceControlController#updateList()}
     * @param type defines the type of your day chart (o is expenses, 1 is income)
     * @param pageCoeff pagination parameter, defines how many units we choose forward, 0 - that units, less 0 - previous units, more 0 - next units
     * @throws SQLException when there is error connected with a database access
     */
    private void showDayChart(int type, int pageCoeff) throws SQLException {
        chartPane.getChildren().clear();
        PieChart dayChart = new PieChart();
        caption.setVisible(false);

        chartPane.getChildren().addAll(dayChart, caption);

        dayChart.setLegendVisible(false);
        dayChart.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("charts/chart-style.css")).toExternalForm());
        dayChart.setTitle("Day " + (type == ControllerFinals.EXPENSES ? "Expenses" : "Income"));
        dayChart.setLayoutX(100);
        dayChart.setMaxHeight(360);

        String startDate = new java.sql.Date(System.currentTimeMillis() + pageCoeff * ONE_DAY).toString();
        String endDate = new java.sql.Date(System.currentTimeMillis() + pageCoeff * ONE_DAY).toString();

        pageText.setText(startDate);

        operationItemList = model.getOperations(type, startDate, endDate);
        List<PieChart.Data> dayChartItemList = operationItemList.size() == 0 ?
                new ArrayList<>() :
                groupItems(operationItemList);

        dayChart.setData(FXCollections.observableArrayList(
                dayChartItemList
        ));


        dayChartItemList.forEach(item -> {
            String defaultColor = "#FFFFFF";
            for (OperationItem operationItem : operationItemList) {
                if (operationItem.category().equals(item.getName())) {
                    defaultColor = operationItem.categoryColor();
                    break;
                }
            }
            item.getNode().setStyle("-fx-background-color: " + defaultColor + ";");
            item.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                try {
                    caption.setText(String.format("%.2f",item.getPieValue()) + " " + model.getCurrencyState());
                } catch (SQLException e) {
                    logger.info(e.toString());
                }
                caption.setVisible(true);
            });
            item.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> caption.setVisible(false));
            item.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
                caption.setTranslateX(mouseEvent.getX());
                caption.setTranslateY(mouseEvent.getY());
            });
        });

        updateList();
    }

    /**
     * showWMYChart method which sets the table with the information about date, category and price of your income/expenses,
     * sets the date depending on the type of chart and names this chart,
     * adds all the categories on chart, groups records into categories and places them on a chart with a specific date,
     * sets the style of your chart and update list {@link FinanceControlController#updateList()}
     * @param operationType defines the type of your chart (0 is expenses, 1 is income)
     * @param chartType defines the type of your chart (1 if WEEK_CHART, 2 if MONTH_CHART, 3 if YEAR_CHART)
     * @param pageCoeff pagination parameter, defines how many units we choose forward, 0 - that units, less 0 - previous units, more 0 - next units
     * @throws SQLException when there is error connected with a database access
     * @throws ParseException which shows signals that an error has been reached unexpectedly while parsing.
     */
    private void showWMYChart(int operationType, int chartType, int pageCoeff) throws SQLException, ParseException {
        chartPane.getChildren().clear();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price");
        final StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);
        chartPane.getChildren().addAll(sbc, caption);

        sbc.setPrefHeight(370);
        sbc.setMaxHeight(370);
        sbc.setLegendVisible(false);
        final HashMap<String, XYChart.Series<String, Number>> series = new HashMap<>();

        ArrayList<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        long currentTime = System.currentTimeMillis();
        long time;
        long startTime;


        //задание даты в зависимости от типа графика
        String chartName;
        if(chartType == ControllerFinals.WEEK_CHART) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            time = currentTime + pageCoeff * ONE_DAY * 7;
            chartName = "Week";
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY) {
                startTime = time - ONE_DAY * 6;
            } else {
                startTime = time - ONE_DAY * (dayOfWeek - 2);
            }
            for (int i = 0; i < calendar.getMaximum(Calendar.DAY_OF_WEEK); i++) {
                dates.add(new Date(startTime + ONE_DAY * i).toString());
            }
        } else if(chartType == ControllerFinals.MONTH_CHART) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            int startMonth = calendar.get(Calendar.MONTH) + 1;
            int startYear = calendar.get(Calendar.YEAR);

            int month = startMonth;
            int year = startYear;
            for(int j = 0;j < Math.abs(pageCoeff);j++) {
                if(month != 1 && month != 12) {
                    if(pageCoeff < 0) {
                        month-=1;
                    } else {
                        month+=1;
                    }
                } else {
                    if(pageCoeff < 0) {
                        month = 12;
                        year-=1;
                    } else {
                        month = 1;
                        year+=1;
                    }
                }
            }
            calendar.setTime(format.parse(year+"-"+month+"-01"));

            chartName = "Month";
            startTime = calendar.getTimeInMillis();
            String outputMonth = null;
            Date writeDate;
            for (int i = 0; i < calendar.getMaximum(Calendar.DAY_OF_MONTH); i++) {
                writeDate = new Date(startTime + ONE_DAY * i);
                if(outputMonth == null) {
                    outputMonth = writeDate.toString().substring(5,7);
                }
                if(outputMonth.equals(writeDate.toString().substring(5,7))) {
                    dates.add(writeDate.toString());
                }
            }
        } else if(chartType == ControllerFinals.YEAR_CHART) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            chartName = "Year";
            int year = calendar.get(Calendar.YEAR) + pageCoeff;
            for (int i = 1; i <= 11; i++) {
                String template = i < 10 ? "%d-0%d-%d" : "%d-%d-%d";
                dates.add(String.format(template, year, i, 1));
            }
            dates.add(String.format("%d-%d-%d", year, 12, 31));
        } else {
            throw new IllegalStateException("No such chart: " + chartType);
        }
        sbc.setTitle(chartName + " " + (operationType == 0 ? "Expenses" : "Income"));
        pageText.setText(dates.get(0) + " - " + dates.get(dates.size()-1));

        operationItemList = model.getOperations(operationType, dates.get(0), dates.get(dates.size()-1));

        ArrayList<String> categoryDates = new ArrayList<>();


        //добавление всех категорий на график
        for (OperationItem item : operationItemList) {
            if (!series.containsKey(item.category())) {
                series.put(item.category(), new XYChart.Series<>());
                series.get(item.category()).setName(item.category());
            }
        }
        //Добавление категорий на графки
        for (Map.Entry<String, XYChart.Series<String, Number>> entry : series.entrySet()) {
            sbc.getData().add(entry.getValue());
        }


        for(String date : dates) {
            String pushDate = chartType == ControllerFinals.YEAR_CHART ? date.substring(5,7) : date.substring(5);
            categoryDates.add(pushDate);


            //группировка записей по категориям и помещение на график с определенной датой *difficult*
            double sum;
            for (Map.Entry<String, XYChart.Series<String, Number>> entry : series.entrySet()) {
                sum = 0;
                for(OperationItem item : operationItemList) {
                    if(chartType == ControllerFinals.YEAR_CHART) {
                        if(item.category().equals(entry.getValue().getName()) && item.date().substring(5,7).equals(pushDate)) {
                            sum += item.price();
                        }
                    } else if(item.category().equals(entry.getValue().getName()) && item.date().equals(date)) {
                        sum += item.price();
                    }
                }
                XYChart.Data<String, Number> data = new XYChart.Data<>(pushDate, sum);
                series.get(entry.getValue().getName()).getData().add(data);
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                    try {
                        caption.setText(entry.getValue().getName() + " " + String.format("%.2f",data.getYValue().doubleValue()) + " " + model.getCurrencyState());
                    } catch (SQLException e) {
                        logger.info(e.toString());
                    }
                    caption.setVisible(true);
                });
                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> caption.setVisible(false));
            }


            /////////////////////////////////////////////////////////////////////////////////////////////
        }

        xAxis.setCategories(FXCollections.observableArrayList(categoryDates));

        String defaultColor;
        for (Map.Entry<String, XYChart.Series<String, Number>> entry : series.entrySet()) {
            defaultColor = "#FFFFFF";
            for (OperationItem operationItem : operationItemList) {
                if (operationItem.category().equals(entry.getKey())) {
                    defaultColor = operationItem.categoryColor();
                    break;
                }
            }

            for (XYChart.Data<String, Number> item : entry.getValue().getData()) {
                item.getNode().setStyle("-fx-background-color: " + defaultColor + ";");
            }
        }

        updateList();
    }

    /**
     * groupItems method which reads values from OperationItem and returns an arraylist of categories with their appropriate sums
     * @param list is the List OperationItem type object which contains the group ExpensesItem and IncomeItem
     * @return returnList of the ArrayList PieChart.Data type with an information from your expenses/income table
     */
    private ArrayList<PieChart.Data> groupItems(List<OperationItem> list) { //Group ExpensesItem and IncomeItem for dayChart
        ArrayList<PieChart.Data> returnList = new ArrayList<>();
        PieChart.Data data;
        double sum = 0;
        String categBuf = list.get(0).category();
        for (OperationItem item : list) {
            if (!categBuf.equals(item.category())) {
                data = new PieChart.Data(categBuf, sum);
                returnList.add(data);

                categBuf = item.category();
                sum = 0;
            }
            sum = sum + item.price();
        }
        data = new PieChart.Data(categBuf, sum);
        returnList.add(data);
        return returnList;
    }

    /**
     * updateBalance method which updates your currency balance with the help of methods {@link FinanceControlModel#getBalance()} and {@link FinanceControlModel#getCurrencyState()}
     * @throws SQLException when there is error connected with a database access
     */
    public void updateBalance() throws SQLException {
        this.balance.setText(String.format("%.2f", model.getBalance()) + " " + model.getCurrencyState());
    }

    /**
     * updateList method which clears the OperationList view and updates it to its initial state
     * @throws SQLException when there is error connected with a database access
     */
    private void updateList() throws SQLException {
        operationListView.getItems().clear();

        AnchorPane pane;
        Label innerText;
        for(OperationItem item : operationItemList) {
            pane = new AnchorPane();
            pane.setStyle("-fx-background-radius: 5px; -fx-background-color: " + item.categoryColor() + "; -fx-min-width: 166; -fx-pref-width: 166;");
            pane.setMinHeight(26);

            innerText = new Label(item.name() + ": " + String.format("%.2f",item.price()) + " " + model.getCurrencyState() + "\n" + item.date());
            innerText.setLayoutY(5);
            innerText.getStyleClass().add("listViewItem");
            pane.getChildren().add(innerText);
            operationListView.getItems().add(pane);
        }

    }

    /**
     * getCurrentChartType method which gets your current chart type : day(0), week(1), month(2), year(3)
     * @return {@link FinanceControlController#currentChartType}
     */
    public int getCurrentChartType() {
        return currentChartType;
    }

    /**
     * getCurrentOperationType method which gets your current operation: expenses(0) or income(1)
     * @return {@link FinanceControlController#currentOperationType}
     */
    public int getCurrentOperationType() {
        return currentOperationType;
    }

    //create list item
}