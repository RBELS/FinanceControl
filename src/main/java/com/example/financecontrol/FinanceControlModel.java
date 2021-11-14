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

/**
 * Class FinanceControlModel, that contains methods to connect with tables
 * @author Dana
 * @version 1.0
 */
public class FinanceControlModel {
    /**
     * Initializing and creating objects(expenses table, income table, config table, categories table, currencies table)
     */
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
                    "('Sport', '#8E05C2', 0), ('Gifts (from me)', '#FF8243', 0),\n" +
                    "('Cafe', '#FFB344', 0), ('Hobbies', '#CEE5D0', 0),\n" +
                    "('Other', '#391d5c', 0),\n" +
                    "('Salary', '#80ED99', 1), ('Gifts (for me)', '#FFB319', 1),\n" +
                    "('Scholarship', '#3B185F', 1), ('Other', '#D0C1E3', 1)",
            CREATE_CURRENCIES_TABLE = "CREATE TABLE IF NOT EXISTS currencies (\n" +
                    "id INTEGER NOT NULL UNIQUE,\n" +
                    "name TEXT NOT NULL UNIQUE,\n" +
                    "apiId INTEGER NOT NULL UNIQUE,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            INIT_CURRENCIES_TABLE = "INSERT INTO currencies (name, apiId) VALUES ('USD', 431), ('EUR', 451), ('RUB', 456), ('BYN', 0);";
    /**
     * a string object baseUrl which contains a link to the official page of the nbrb
     */
    private final String baseUrl = "https://www.nbrb.by/api/exrates/rates/";
    /**
     * A Logger object which is used to log messages about program operations(information about errors)
     */
    private final Logger log = Logger.getLogger(getClass().getName());
    /**
     * an object that will connect program with database
     */
    private Connection connection;
    /**
     * a statement instance for executing SQL queries.
     */
    private Statement stmt;

    /**
     * Constructor FinanceControlModel that catches errors connected with DataBase
     * @see FinanceControlModel#initDB()
     */
    public FinanceControlModel() {
        try {
            initDB();
        } catch (SQLException e) {
            log.info(e.toString());
        }
    }

    /**
     * addExpenses method which inserts the input values into the database(expenses table)
     * @param name a name of your expenses
     * @param price how much have you spent on it
     * @param category a type of your expenses
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * addIncome method which inserts the input values into the database(income table)
     * @param name a name of your income
     * @param price how much have you spent on it
     * @param category a type of your income
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getCategories a method of the list type that reads values from the table of categories(NAME, COLOR, ID) from the database and returns them in a separate arraylist
     * @param type the type of your category
     * @return returns an ArrayList CategoriesItem that contains information from CategoriesTable
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getOperations a method that accepts operation type, start date and end date of selection, reads values from start to end date from database(ID, DATE, PRICE, NAME, CATEGORY, COLOR) and return them in a separate arraylist
     * @param type the type of the operation (0 - expense, 1 - income, 2 - both for all time)
     * @param startDate the date from which method will read values
     * @param endDate the date til which method will read values
     * @return returns an arraylist OperationItem that contains information from OperationsTable
     * @throws SQLException when there is error connected with a database access
     */
    public ArrayList<OperationItem> getOperations(int type, String startDate, String endDate) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();
        String n = getTableName(type);
        ResultSet operationSet;
        ResultSet bufOperationSet;


        if(type == 2) {
            operationSet = stmt.executeQuery("SELECT expenses.id, expenses.name, -1*expenses.price AS price, expenses.date, expenses.category, categories.color FROM expenses JOIN categories ON categories.name = expenses.category;");
        } else {
            operationSet = stmt.executeQuery("SELECT " + n + ".id, " + n + ".name, " + n + ".price, " + n + ".date, " + n + ".category, categories.color " +
                    "FROM " + n + " JOIN categories ON " + n + ".category=categories.name AND categories.type=" + type + " " +
                    "WHERE " + n + ".date BETWEEN '" + startDate + "' and '" + endDate + "' " +
                    "ORDER BY " + n + ".date");
        }


        ArrayList<OperationItem> list = new ArrayList<>();
        while (operationSet.next()) {
            list.add(new OperationItem(
                    operationSet.getInt(OperationsTable.ID),
                    operationSet.getString(OperationsTable.DATE),
                    operationSet.getDouble(OperationsTable.PRICE),
                    operationSet.getString(OperationsTable.NAME),
                    operationSet.getString(OperationsTable.CATEGORY),
                    operationSet.getString(CategoriesTable.COLOR)
            ));
        }
        if(type == 2) {
            operationSet = stmt.executeQuery("SELECT income.id, income.name, income.price, income.date, income.category, categories.color FROM income JOIN categories ON categories.name = income.category;");
            while (operationSet.next()) {
                list.add(new OperationItem(
                        operationSet.getInt(OperationsTable.ID),
                        operationSet.getString(OperationsTable.DATE),
                        operationSet.getDouble(OperationsTable.PRICE),
                        operationSet.getString(OperationsTable.NAME),
                        operationSet.getString(OperationsTable.CATEGORY),
                        operationSet.getString(CategoriesTable.COLOR)
                ));
            }
        }

        stmt.close();
        connection.close();
        return list;
    }

    /**
     * getBalance method that reads your current balance(the amount of money in the account) from database and returns it
     * @return a double variable that contains your balance
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getCurrencies method that reads values from the currency table(ID, NAME, API_ID) from database and returns them in a separate ArrayList
     * @return returns an ArrayList that contains information from CurrenciesTable
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getCurrencyState method that checks and return your current currency state
     * @return returns a string variable that contains NAME from CurrenciesTable
     * @throws SQLException when there is error connected with a database access
     */
    public String getCurrencyState() throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        String currencyStr = stmt.executeQuery("SELECT name FROM currencies JOIN config ON config.value=currencies.id WHERE config.field='currencyId'").getString(CurrenciesTable.NAME);

        stmt.close();
        connection.close();
        return currencyStr;
    }

    /**
     * setCurrencyState method that accepts new currency(string variable) and sets it into a database
     * @param str a string variable that contains currency's name
     * @throws SQLException when there is error connected with a database access
     */
    public void setCurrencyState(String str) throws SQLException {
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        stmt.execute("UPDATE config SET value=(SELECT id FROM currencies WHERE name = '"+str+"') WHERE field='currencyId';");

        stmt.close();
        connection.close();
    }

    /**
     * getResponse method that accept chosen currency, sends request to nbrb site for information of transforming old currency value into a new currency and returns information from the site into a ResponseItem class
     * @param curr a name of chosen currency
     * @return an string line into a ResponseItem class from nbrb site
     * @throws IOException when the I/O operations were failed or interrupted
     * @throws SQLException when there is error connected with a database access
     */
    private ResponseItem getResponse(String curr) throws IOException, SQLException {
        Gson gson = new Gson();
        int apiId = stmt.executeQuery("SELECT apiId FROM currencies WHERE name='"+curr+"'").getInt(CurrenciesTable.API_ID);
        URL url = new URL(baseUrl+apiId);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(1000);
        con.setReadTimeout(1000);
        int status = con.getResponseCode();
        if (status != 200) return null;
        String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return gson.fromJson(response, ResponseItem.class);
    }

    /**
     * updateOperations method that compares previous and new currencies to translate one into another and update information in database after translating
     * @param prev the name of previous currency
     * @param new_ the name of new currency
     * @return FALSE if there is IOException or ResponseItem object is null and TRUE in other cases
     * @throws SQLException when there is error connected with a database access
     */
    public boolean updateOperations(String prev, String new_) throws SQLException{
        connection = DBController.connector();
        assert connection != null;
        stmt = connection.createStatement();

        double rate = 1.0;
        ResponseItem respObj;

        try {
            if(!prev.equals("BYN")) {
                respObj = getResponse(prev);
                if (respObj == null) {
                    stmt.close();
                    connection.close();
                    return false;
                }
                rate = respObj.Cur_OfficialRate / respObj.Cur_Scale;
            }

            if(!new_.equals("BYN")) {
                respObj = getResponse(new_);
                if (respObj == null) {
                    stmt.close();
                    connection.close();
                    return false;
                }
                rate = rate / respObj.Cur_OfficialRate * respObj.Cur_Scale;
            }
        } catch (IOException e) {
            stmt.close();
            connection.close();
            return false;
        }

        stmt.execute("UPDATE expenses SET price = price * " +  rate);
        stmt.execute("UPDATE income SET price = price * " + rate);
        stmt.execute("UPDATE config SET value = value * "+rate+" WHERE field = 'balance'");

        stmt.close();
        connection.close();
        return true;
    }

    /**
     * initDB method that checks if objects(expenses, income and config tables in database) are initialized and creates them or initializes
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getTableName method that accept type and names table depending on this type(0 - expense, 1 - income)
     * @param type 0/1 - variable defining the type of the table
     * @return the final name of the table
     */
    private String getTableName(int type) {
        return type == 0 ? "expenses" : "income";
    }
}
