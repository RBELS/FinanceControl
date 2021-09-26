package com.example.financecontrol;

import java.sql.*;

public class DBController {
    private static final String
            createExpensesTableSQL = "CREATE TABLE IF NOT EXISTS expenses (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "date\tDATE NOT NULL,\n" +
                    "price\tREAL NOT NULL,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "categoryId\tINTEGER NOT NULL,\n" +
                    "PRIMARY KEY(id AUTOINCREMENT)\n" +
                    ")",
            createIncomeTableSQL = "CREATE TABLE IF NOT EXISTS income (\n" +
                    "id\tINTEGER NOT NULL UNIQUE,\n" +
                    "date\tDATE NOT NULL,\n" +
                    "price\tREAL NOT NULL,\n" +
                    "name\tTEXT NOT NULL,\n" +
                    "categoryId\tINTEGER NOT NULL,\n" +
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


    private static Statement stmt;
    private static Boolean isInited;

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:FinanceControl.sqlite");
            stmt = conn.createStatement();
            stmt.execute(createExpensesTableSQL);
            stmt.execute(createIncomeTableSQL);
            stmt.execute(createConfigTableSQL);
            isInited = stmt.executeQuery("SELECT * FROM config").next();
            if (!isInited) stmt.execute(initConfigTable);

            stmt.execute(createCategoriesTable);
            isInited = stmt.executeQuery("SELECT * FROM categories").next();
            if (!isInited) stmt.execute(initCategoriesTable);


            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}