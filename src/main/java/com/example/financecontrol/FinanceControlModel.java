package com.example.financecontrol;

import com.example.financecontrol.dbmodels.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.google.gson.*;

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
                    "('theme', 0), ('currencyId', 1), ('balance', 0)",
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
                    "('Scholarship', '#3B185F', 1), ('Other', '#F3F1F5', 1)",
            CREATE_CURRENCIES_TABLE = "CREATE TABLE IF NOT EXISTS currencies (\n" +
                    "id INTEGER NOT NULL UNIQUE,\n" +
                    "name TEXT NOT NULL UNIQUE,\n" +
                    "apiId INTEGER NOT NULL UNIQUE,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            INIT_CURRENCIES_TABLE = "INSERT INTO currencies (name, apiId) VALUES ('USD', 431), ('EUR', 451), ('RUB', 456), ('BYN', 0);";
    private final String baseUrl = "https://www.nbrb.by/api/exrates/rates/";

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

    public ArrayList<OperationItem> getOperations(int type, String startDate, String endDate) throws SQLException {

        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();
        String n = getTableName(type);

        ResultSet expensesSet = stmt.executeQuery("SELECT " + n + ".id, " + n + ".name, " + n + ".price, " + n + ".date, " + n + ".category, categories.color " +
                "FROM " + n + " JOIN categories ON " + n + ".category=categories.name AND categories.type=" + type + " " +
                "WHERE " + n + ".date BETWEEN '" + startDate + "' and '" + endDate + "' " +
                "ORDER BY " + n + ".date");
        ArrayList<OperationItem> list = new ArrayList<>();
        while (expensesSet.next()) {
            list.add(new OperationItem(
                    expensesSet.getInt(OperationsTable.ID),
                    expensesSet.getString(OperationsTable.DATE),
                    expensesSet.getDouble(OperationsTable.PRICE),
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

    public List<CurrencyItem> getCurrencies() throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        ResultSet currenciesSet = stmt.executeQuery("SELECT * FROM currencies");
        List<CurrencyItem> list = new ArrayList<>();
        while (currenciesSet.next()) {
            list.add(new CurrencyItem(
                    currenciesSet.getInt(CurrenciesTable.ID),
                    currenciesSet.getString(CurrenciesTable.NAME),
                    currenciesSet.getInt(CurrenciesTable.API_ID)
            ));
        }

        stmt.close();
        connection.close();
        return list;
    }

    public String getCurrencyState() throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        String currencyStr = stmt.executeQuery("SELECT name FROM currencies JOIN config ON config.value=currencies.id WHERE config.field='currencyId'").getString(CurrenciesTable.NAME);

        stmt.close();
        connection.close();
        return currencyStr;
    }

    public void setCurrencyState(String str) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        stmt.execute("UPDATE config SET value=(SELECT id FROM currencies WHERE name = '"+str+"') WHERE field='currencyId';");

        stmt.close();
        connection.close();
    }

    private ResponseItem getResponse(String curr) throws IOException, SQLException {
        Gson gson = new Gson();
        int apiId = stmt.executeQuery("SELECT apiId FROM currencies WHERE name='"+curr+"'").getInt(CurrenciesTable.API_ID);
        URL url = new URL(baseUrl+apiId);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();
        if (status != 200) return null;
        String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return gson.fromJson(response, ResponseItem.class);
    }

    public boolean updateOperations(String prev, String new_) throws SQLException, IOException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        double rate = 1.0;
        ResponseItem respObj;


        if(!prev.equals("BYN")) {
            respObj = getResponse(prev);
            if (respObj == null) return false;
            rate = respObj.Cur_OfficialRate / respObj.Cur_Scale;
        }

        if(!new_.equals("BYN")) {
            respObj = getResponse(new_);
            if (respObj == null) return false;
            rate = rate / respObj.Cur_OfficialRate * respObj.Cur_Scale;
        }

        stmt.execute("UPDATE expenses SET price = price * " +  rate);
        stmt.execute("UPDATE income SET price = price * " + rate);
        stmt.execute("UPDATE config SET value = value * "+rate+" WHERE field = 'balance'");

        stmt.close();
        connection.close();
        return true;
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

        stmt.execute(CREATE_CURRENCIES_TABLE);
        isInited = stmt.executeQuery("SELECT * FROM currencies").next();
        if (!isInited) stmt.execute(INIT_CURRENCIES_TABLE);

        stmt.close();
        connection.close();
    }

    private String getTableName(int type) {
        return type == 0 ? "expenses" : "income";
    }
}
