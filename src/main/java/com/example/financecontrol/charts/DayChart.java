package com.example.financecontrol.charts;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DayChart extends AnchorPane {
    public DayChart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("day-chart.fxml"));//create fxml

//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);

        fxmlLoader.load();
    }
}
