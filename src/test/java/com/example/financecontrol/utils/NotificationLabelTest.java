package com.example.financecontrol.utils;

import javafx.scene.control.Label;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationLabelTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void genLabel() {
        try {
            NotificationLabel label = new NotificationLabel("Test", true, 50,50);
            label.show();
            Label label1 = label.getLabel();
        } catch (ExceptionInInitializerError e) {

        }
    }
}

