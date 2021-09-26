package com.example.financecontrol;

import com.example.financecontrol.dbModels.CategoriesTable;
import com.example.financecontrol.dbModels.CategroiesItem;

import java.sql.*;
import java.util.ArrayList;
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
            stmt.execute("INSERT INTO expenses (date, price, name, categoryId)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', "+categoryId+")");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addIncome(String name, double price, int categoryId) {
        try {
            stmt.execute("INSERT INTO income (date, price, name, categoryId)VALUES " +
                    "('"+new java.sql.Date(System.currentTimeMillis())+"', "+price+", '"+name+"', "+categoryId+")");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<CategroiesItem> getCategroies(int type) throws SQLException {
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
        return list;
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
