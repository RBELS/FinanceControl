package com.example.financecontrol;

import java.sql.*;
import java.util.Date;

public class FinanceControlModel {
    private final Connection connection;
    private Statement stmt;


    public FinanceControlModel() {
        connection = DBController.Connector();
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }


//        if (connection == null) {
//            System.exit(1);
//        }
    }

    public void addExpense(String name, double price, int categoryId) {
        try {
//            stmt = connection.createStatement();
            stmt.execute("INSERT INTO expenses (date, price, name, categoryId)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', "+categoryId+")");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addIncome(String name, double price, int categoryId) {
        try {
//            stmt = connection.createStatement();
            stmt.execute("INSERT INTO income (date, price, name, categoryId)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', "+categoryId+")");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int getNumberOfRowsExp() {
        try {
            ResultSet set = stmt.executeQuery("SELECT COUNT(*) AS num FROM expenses");
            int write = set.getInt("num");
            return  write;
        } catch (SQLException e) {
            System.out.println(e);
            return -1;
        }
    }

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
