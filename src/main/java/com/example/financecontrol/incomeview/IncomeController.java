package com.example.financecontrol.incomeview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbModels.CategroiesItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IncomeController implements Initializable {
    private FinanceControlModel model;
    private ArrayList<CategroiesItem> list;

    public IncomeController() {
        model = new FinanceControlModel();
    }

    @FXML
    private ChoiceBox categoryChoiceBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button addBt;

    @FXML
    protected void onAddBtClick() {
        if (validateInput()) {
            model.addIncome(
                    nameTextField.getText(),
                    Double.parseDouble(priceTextField.getText()),
                    categoryChoiceBox.getValue().toString()
            );
            nameTextField.setText("");
            priceTextField.setText("");
            categoryChoiceBox.setValue(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list = model.getCategroies(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (CategroiesItem item : list) {
            categoryChoiceBox.getItems().add(item.getName());
        }

    }



    private boolean validateInput() {
        if (
                categoryChoiceBox.getValue() != null
                        && !nameTextField.getText().equals("")
                        && isNumeric(priceTextField.getText())
        ) {
            return true;
        }
        return false;
    }

    private boolean isNumeric(String str) {
        if (str == null) return  false;
        try {
            Double d = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
