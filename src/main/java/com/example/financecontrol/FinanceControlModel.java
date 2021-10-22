package com.example.financecontrol;

import com.example.financecontrol.dbmodels.*;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class FinanceControlModel {
    private static final String
            CREATE_EXPENSES_TABLE_SQL = "CREATE TABLE IF NOT EXISTS expenses (\n" +
            "id\tINTEGER NOT NULL UNIQUE,\n" +
            "date\tDATE NOT NULL,\n" +
            "price\tREAL NOT NULL,\n" +
            "name\tTEXT NOT NULL,\n" +
            "category\tTEXT NOT NULL,\n" +
            "PRIMARY KEY(id AUTOINCREMENT)\n" +
            ")",
            CREATE_INCOME_TABLE_SQL = "CREATE TABLE IF NOT EXISTS income (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "date\tDATE NOT NULL,\n" +
                    "price\tREAL NOT NULL,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "category\tTEXT NOT NULL,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            CREATE_CONFIG_TABLE_SQL = "CREATE TABLE IF NOT EXISTS config (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "field\tTEXT NOT NULL,\n" +
                    "value\tREAL NOT NULL,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            INIT_CONFIG_TABLE = "INSERT INTO config (field, value) VALUES" +
                    "('theme', 0), ('currencyId', 0), ('balance', 0)", // 0-dark, 1-light; 0-USD
            CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS categories (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "color\tTEXT NOT NULL,\n" +
                    "type\tINTEGER NOT NULL,\n" + // 0-expense, 1-income
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            INIT_CATEGORIES_TABLE = "INSERT INTO categories (name, color, type) VALUES ('Food', '#FF5C58', 0), ('Transport', '#1DB9C3', 0),\n" +
                    "('Clothes', '#316B83', 0), ('Technology', '#6F69AC', 0),\n" +
                    "('Sport', '#8E05C2', 0), ('Gifts', '#FF8243', 0),\n" +
                    "('Cafe', '#FFB344', 0), ('Hobbies', '#CEE5D0', 0),\n" +
                    "('Other', '#F3F1F5', 0),\n" +
                    "('Salary', '#80ED99', 1), ('Gifts', '#FFB319', 1),\n" +
                    "('Scholarship', '#3B185F', 1), ('Other', '#F3F1F5', 1)";

    private final long oneDay = 1000*60*60*24;
    private final Logger log = Logger.getLogger(getClass().getName());

    private Connection connection;
    private Statement stmt;


    public FinanceControlModel() {
        try {
            initDB();
        } catch (SQLException e) {
            log.info(e.toString());
        }
    }

    public void addExpense(String name, double price, String category) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        stmt.execute("INSERT INTO expenses (date, price, name, category)VALUES " +
                "('" + new java.sql.Date(System.currentTimeMillis()) + "', " + price + ", '" + name + "', '" + category + "')");
        stmt.execute("UPDATE config SET value = value - " + price + " WHERE field = 'balance'");

        stmt.close();
        connection.close();
    }

    public void addIncome(String name, double price, String category) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        stmt.execute("INSERT INTO income (date, price, name, category)VALUES " +
                "('" + new java.sql.Date(System.currentTimeMillis()) + "', " + price + ", '" + name + "', '" + category + "')");
        stmt.execute("UPDATE config SET value = value + " + price + " WHERE field = 'balance'");

        stmt.close();
        connection.close();
    }

    public List<CategoriesItem> getCategories(int type) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();
        ResultSet categoriesSet = stmt.executeQuery("SELECT id, name, color FROM categories WHERE type=" + type);
        ArrayList<CategoriesItem> list = new ArrayList<>();
        while (categoriesSet.next()) {
            list.add(new CategoriesItem(
                    categoriesSet.getString(CategoriesTable.NAME),
                    categoriesSet.getString(CategoriesTable.COLOR),
                    categoriesSet.getInt(CategoriesTable.ID)
            ));
        }
        stmt.close();
        connection.close();
        return list;
    }

    public ArrayList<OperationItem> getOperations(int type, int chartType) throws SQLException {
        java.sql.Date startDate, endDate;

        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();
        String n = getTableName(type);

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        long time = System.currentTimeMillis();
        long startTime, endTime;

        switch (chartType) {
            case ControllerFinals.DAY_CHART:
                startDate = new java.sql.Date(time);
                endDate = startDate;
                break;
            case ControllerFinals.WEEK_CHART:
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek == Calendar.SUNDAY) {
                    endTime = time;
                    startDate = new java.sql.Date(time - oneDay * 6);
                    endDate = new java.sql.Date(endTime);
                } else {
                    startTime = time - oneDay*(dayOfWeek-2);
                    startDate = new java.sql.Date(startTime);
                    endDate = new java.sql.Date(startTime + oneDay * 6);
                }
                break;
            case ControllerFinals.MONTH_CHART:
                startTime = time - oneDay * (calendar.get(Calendar.DAY_OF_MONTH) - 1);
                startDate = new java.sql.Date(startTime);
                endDate = new java.sql.Date(startTime + oneDay * (calendar.getMaximum(Calendar.DAY_OF_MONTH)-1));
                break;
            case ControllerFinals.YEAR_CHART:
                startTime = time - oneDay * (calendar.get(Calendar.DAY_OF_YEAR) - 1);
                startDate = new java.sql.Date(startTime);
                endDate = new java.sql.Date(startTime + oneDay * (calendar.getMaximum(Calendar.DAY_OF_YEAR)-1));
                break;
            default:
                startDate = null;
                endDate = null;
        }

        ResultSet expensesSet = stmt.executeQuery("SELECT " + n + ".id, " + n + ".name, " + n + ".price, " + n + ".date, " + n + ".category, categories.color " +
                "FROM " + n + " JOIN categories ON " + n + ".category=categories.name AND categories.type="+type+" " +
                "WHERE " + n + ".date BETWEEN '" + startDate + "' and '" + endDate + "' " +
                "ORDER BY " + n + ".date");
        ArrayList<OperationItem> list = new ArrayList<>();
        while (expensesSet.next()) {
            list.add(new OperationItem(
                    expensesSet.getInt(OperationsTable.ID),
                    expensesSet.getString(OperationsTable.DATE),
                    expensesSet.getInt(OperationsTable.PRICE),
                    expensesSet.getString(OperationsTable.NAME),
                    expensesSet.getString(OperationsTable.CATEGORY),
                    expensesSet.getString(CategoriesTable.COLOR)
            ));
        }
        stmt.close();
        connection.close();
        return list;
    }

    public double getBalance() throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        ResultSet balanceSet = stmt.executeQuery("SELECT value FROM config WHERE field = 'balance'");
        balanceSet.next();
        double balance = balanceSet.getDouble(ConfigTable.VALUE);

        stmt.close();
        connection.close();
        return balance;
    }

    public void initDB() throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        stmt = connection.createStatement();
        stmt.execute(CREATE_EXPENSES_TABLE_SQL);
        stmt.execute(CREATE_INCOME_TABLE_SQL);
        stmt.execute(CREATE_CONFIG_TABLE_SQL);
        boolean isInited = stmt.executeQuery("SELECT * FROM config").next();
        if (!isInited) stmt.execute(INIT_CONFIG_TABLE);

        stmt.execute(CREATE_CATEGORIES_TABLE);
        isInited = stmt.executeQuery("SELECT * FROM categories").next();
        if (!isInited) stmt.execute(INIT_CATEGORIES_TABLE);

        stmt.close();
        connection.close();
    }

    private String getTableName(int type) {
        return type == 0 ? "expenses" : "income";
    }
}
