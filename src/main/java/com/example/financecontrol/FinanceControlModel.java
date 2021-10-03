package com.example.financecontrol;

import com.example.financecontrol.dbModels.CategoriesTable;
import com.example.financecontrol.dbModels.CategroiesItem;
import com.example.financecontrol.incomeview.IncomeItem;
import com.example.financecontrol.incomeview.IncomeTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FinanceControlModel {
    private static final String
            createExpensesTableSQL = "CREATE TABLE IF NOT EXISTS expenses (\n" +
            "id\tINTEGER NOT NULL UNIQUE,\n" +
            "date\tDATE NOT NULL,\n" +
            "price\tREAL NOT NULL,\n" +
            "name\tTEXT NOT NULL,\n" +
            "category\tTEXT NOT NULL,\n" +
            "PRIMARY KEY(id AUTOINCREMENT)\n" +
            ")",
            createIncomeTableSQL = "CREATE TABLE IF NOT EXISTS income (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "date\tDATE NOT NULL,\n" +
                    "price\tREAL NOT NULL,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "category\tTEXT NOT NULL,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            createConfigTableSQL = "CREATE TABLE IF NOT EXISTS config (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "field\tTEXT NOT NULL,\n" +
                    "value\tREAL NOT NULL,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            initConfigTable = "INSERT INTO config (field, value) VALUES" +
                    "('theme', 0), ('currencyId', 0), ('balance', 0)", // 0-dark, 1-light; 0-USD
            createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "color\tTEXT NOT NULL,\n" +
                    "type\tINTEGER NOT NULL,\n" + // 0-expense, 1-income
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            initCategoriesTable = "INSERT INTO categories (name, color, type) VALUES ('Food', '#FF5C58', 0), ('Transport', '#1DB9C3', 0),\n" +
                    "('Clothes', '#316B83', 0), ('Technology', '#6F69AC', 0),\n" +
                    "('Sport', '#FF5C58', 0), ('Gifts', '#FF5C58', 0),\n" +
                    "('Cafe', '#FFB344', 0), ('Hobbies', '#CEE5D0', 0),\n" +
                    "('Other', '#F3F1F5', 0),\n" +
                    "('Salary', '#80ED99', 1), ('Gifts', '#FFB319', 1),\n" +
                    "('Scholarship', '#3B185F', 1), ('Other', '#F3F1F5', 1)";

    private Connection connection;
    private Statement stmt;


    public FinanceControlModel() {
        try {
            initDB();
        } catch (SQLException e) {
            System.out.println(e);
        }


//        if (connection == null) {
//            System.exit(1);
//        }
    }

    public void addExpense(String name, double price, String category) {
        try {
            connection = DBController.Connector();
            stmt = connection.createStatement();

            stmt.execute("INSERT INTO expenses (date, price, name, category)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', '"+category+"')");

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addIncome(String name, double price, String category) {
        try {
            connection = DBController.Connector();
            stmt = connection.createStatement();

            stmt.execute("INSERT INTO income (date, price, name, category)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', '"+category+"')");
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<CategroiesItem> getCategroies(int type) throws SQLException {
        connection = DBController.Connector();
        stmt = connection.createStatement();
        ResultSet categoriesSet = stmt.executeQuery("SELECT id, name, color FROM categories WHERE type="+type);
        ArrayList<CategroiesItem> list = new ArrayList<CategroiesItem>();
        CategroiesItem item;
        while (categoriesSet.next()) {
            list.add(new CategroiesItem(
                    categoriesSet.getString(CategoriesTable.name),
                    categoriesSet.getString(CategoriesTable.color),
                    categoriesSet.getInt(CategoriesTable.id)
            ));
        }
        stmt.close();
        connection.close();
        return list;
    }

    public ArrayList<IncomeItem> getIncome(int type) throws SQLException {
        connection = DBController.Connector();
        stmt = connection.createStatement();
        ResultSet incomeSet = stmt.executeQuery("SELECT id, date, price, name, category FROM income WHERE type="+type);
        ArrayList<IncomeItem> list = new ArrayList<IncomeItem>();
        while (incomeSet.next()) {
            list.add(new IncomeItem(
                    incomeSet.getInt(IncomeTable.id),
                    incomeSet.getInt(IncomeTable.date),
                    incomeSet.getInt(IncomeTable.price),
                    incomeSet.getString(IncomeTable.name),
                    incomeSet.getString(IncomeTable.category)
            ));
        }
        stmt.close();
        connection.close();
        return list;
    }

    public void initDB() throws SQLException {
        connection = DBController.Connector();
        stmt = connection.createStatement();

        stmt = connection.createStatement();
        stmt.execute(createExpensesTableSQL);
        stmt.execute(createIncomeTableSQL);
        stmt.execute(createConfigTableSQL);
        Boolean isInited = stmt.executeQuery("SELECT * FROM config").next();
        if (!isInited) stmt.execute(initConfigTable);

        stmt.execute(createCategoriesTable);
        isInited = stmt.executeQuery("SELECT * FROM categories").next();
        if (!isInited) stmt.execute(initCategoriesTable);

        stmt.close();
        connection.close();
    }
}
