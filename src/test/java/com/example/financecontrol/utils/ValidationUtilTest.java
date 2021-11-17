package com.example.financecontrol.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationUtilTest {
    static final String TEST_STR = "TEST", TEST_STR_NUMBER = "1";

    @BeforeAll
    void setUp() {
    }

    @AfterAll
    void tearDown() {
    }

    @Test
    void validateInput() {
        assertFalse(ValidationUtil.validateInputEI(null, TEST_STR, TEST_STR_NUMBER));
        assertFalse(ValidationUtil.validateInputEI(TEST_STR, "", TEST_STR_NUMBER));
        assertFalse(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, TEST_STR));
        assertTrue(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, TEST_STR_NUMBER));
        assertFalse(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, null));
        assertFalse(ValidationUtil.validateInputEI(TEST_STR, TEST_STR, "-1"));
    }
}
