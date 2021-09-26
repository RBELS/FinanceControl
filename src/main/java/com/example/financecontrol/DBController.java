package com.example.financecontrol;

import java.sql.*;

public class DBController {
    private static Boolean isInited;

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:FinanceControl.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}