package com.example.financecontrol;

import java.sql.*;
import java.util.logging.Logger;

public class DBController {
    private static final Logger logger = Logger.getLogger(String.valueOf(DBController.class));
    private DBController() {

    }

    public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:FinanceControl.sqlite");
        } catch (Exception e) {
            logger.info(e.toString());
            return null;
        }
    }
}