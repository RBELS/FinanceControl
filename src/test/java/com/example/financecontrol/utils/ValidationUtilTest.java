package com.example.financecontrol.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationUtilTest {
    static final String TEST_STR = "TEST", TEST_STR_NUMBER = "0";

    @BeforeAll
    void setUp() {
    }

    @AfterAll
    void tearDown() {
    }

    @Test
    void validateInputWrongCategoryValue() {
        assertEquals(ValidationUtil.validateInputEI(null, TEST_STR, TEST_STR_NUMBER), false);
    }

    @Test
    void validateInputWrongName() {
        assertEquals(ValidationUtil.validateInputEI(TEST_STR, "", TEST_STR_NUMBER), false);
    }

    @Test
    void validateInputWrongPrice() {
        assertEquals(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, TEST_STR), false);
    }

    @Test
    void validateInputCorrect() {
        assertEquals(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, TEST_STR_NUMBER), true);
    }
}