package com.example.financecontrol.expensesview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbModels.CategroiesItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExpensesCotroller implements Initializable {
    private FinanceControlModel model = new FinanceControlModel();
    private ArrayList<CategroiesItem> list;

    @FXML
    private ChoiceBox categoryChoiceBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list = model.getCategroies(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (CategroiesItem item : list) {
//            System.out.println(item.getName());
            categoryChoiceBox.getItems().add(item.getName());
        }


//        categoryChoiceBox.setItems(FXCollections.observable);
    }
}
