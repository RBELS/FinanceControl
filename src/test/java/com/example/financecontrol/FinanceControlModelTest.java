package com.example.financecontrol;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FinanceControlModelTest {

    FinanceControlModel model;
    final int INIT_EXPENSES_CATEGORIES = 9;
    final int INIT_INCOME_CATEGORIES = 4;
    Connection conn;
    Statement stmt;


    @BeforeAll
    void setUp() {
        model = new FinanceControlModel();
    }

    @AfterAll
    void close() {
        try {
            dropDB();
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void addExpense() {
        try {
            model.addExpense("TestName", 0, "TestCategory");
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void addIncome() {
        try {
            model.addIncome("TestName", 0, "TestCategory");
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void getCategories() {
        try {
            assertEquals(model.getCategories(0).toArray().length, INIT_EXPENSES_CATEGORIES); //Check for number of initial expenses categories
            assertEquals(model.getCategories(1).toArray().length, INIT_INCOME_CATEGORIES); //Check for number of initial income categories
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void getExpenses() {
        try {
            model.addExpense("Burger", 5, "Food");
            model.addExpense("Bus", 10, "Transport");
            String today = new java.sql.Date(System.currentTimeMillis()).toString();
            assertEquals(model.getOperations(0,today, today).toArray().length, 2);
        } catch (SQLException e) {
            fail(e.toString());
        }
    }

    @Test
    void initDB() {
        try {
            conn = DBController.connector();
            assert conn != null;
            stmt = conn.createStatement();

            stmt.execute("SELECT * FROM expenses");
            stmt.execute("SELECT * FROM income");
            stmt.execute("SELECT * FROM categories");
            stmt.execute("SELECT * FROM config");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private void dropDB() throws SQLException {
        conn = DBController.connector();
        assert conn != null;
        stmt = conn.createStatement();
        stmt.execute("DROP TABLE expenses;");
        stmt.execute("DROP TABLE income;");
        stmt.execute("DROP TABLE categories");
        stmt.execute("DROP TABLE config");
        stmt.close();
        conn.close();
    }
}