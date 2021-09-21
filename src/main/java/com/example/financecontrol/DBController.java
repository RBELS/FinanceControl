package com.example.financecontrol;

import java.sql.*;

public class DBController {
    private static final String
            createExpensesTableSQL = "CREATE TABLE IF NOT EXISTS expenses (\n" +
            "        id\tINTEGER NOT NULL UNIQUE,\n" +
            "        date\tDATE NOT NULL,\n" +
            "        price\tREAL NOT NULL,\n" +
            "        name\tTEXT NOT NULL,\n" +
            "        categoryId\tINTEGER NOT NULL,\n" +
            "        PRIMARY KEY(id AUTOINCREMENT)\n" +
            "        )",
            createIncomeTableSQL = "CREATE TABLE IF NOT EXISTS income (\n" +
            "        id\tINTEGER NOT NULL UNIQUE,\n" +
            "        date\tDATE NOT NULL,\n" +
            "        price\tREAL NOT NULL,\n" +
            "        name\tTEXT NOT NULL,\n" +
            "        categoryId\tINTEGER NOT NULL,\n" +
            "        PRIMARY KEY(id AUTOINCREMENT)\n" +
            "        )";

    private static Statement stmt;

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:FinanceControl.sqlite");
            stmt = conn.createStatement();
            stmt.execute(createExpensesTableSQL);
            stmt.execute(createIncomeTableSQL);
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}