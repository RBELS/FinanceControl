package com.example.financecontrol;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

/**
 * DBController class which connects to sqlite database and controls this connection
 */
public class DBController {
    /**
     * a new logger object which contains a string with an information about the class DBController
     */
    private static final Logger logger = Logger.getLogger(String.valueOf(DBController.class));

    /**
     * a DBController constructor
     */
    private DBController() {
    }

    /**
     * connector() method which dynamically loads the driver class into memory, automatically registers it, then gets connection with database and catches the error.
     * @return returns a created connection with database of it exists, else it returns null and shows an error info
     */
    public static Connection connector() {
        try {
            String system = System.getProperty("os.name");
            if(system.contains("Windows")) {
                File f = new File(System.getenv("USERPROFILE") + "\\AppData\\Local\\FinancelControl");
                boolean mkdirFlag = f.mkdirs();
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:"+f.getPath()+"/db.sqlite");
            } else {
                File f = new File(System.getenv("HOME")+"/Documents/FinanceControl");
                boolean mkDirFlag = f.mkdirs();
                Class.forName("org.sqlite.JDBC");
                return DriverManager.getConnection("jdbc:sqlite:"+f.getPath()+"/db.sqlite");
            }

        } catch (Exception e) {
            logger.info(e.toString());
            return null;
        }
    }
}