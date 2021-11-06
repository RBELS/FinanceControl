package com.example.financecontrol.dbmodels;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoriesItemTest {
    final String TEST_NAME = "TestName";
    final String TEST_COLOR = "TestColor";
    final int TEST_ID = 0;
    CategoriesItem testItem;

    @BeforeAll
    void setUp() {
        testItem = new CategoriesItem(TEST_NAME, TEST_COLOR, TEST_ID);
    }

    @Test
    void getName() {
        assertEquals(testItem.getName(), TEST_NAME);
    }

    @Test
    void getColor() {
        assertEquals(testItem.getColor(), TEST_COLOR);
    }

    @Test
    void getId() {
        assertEquals(testItem.getId(), TEST_ID);
    }
}