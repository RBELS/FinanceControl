package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CurrencyItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

;

public class SettingsController implements Initializable {
    @FXML private ChoiceBox<String> currencyBox;
    
    private List<CurrencyItem> currencies;
    private final FinanceControlModel model = new FinanceControlModel();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String currencyState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            currencies = model.getCurrencies();
            currencyState = model.getCurrencyState();
        } catch (SQLException e) {
            logger.info(e.toString());
        }

        currencyBox.setValue(currencyState);

        for(CurrencyItem item : currencies) {
            currencyBox.getItems().add(item.getName());
        }

        currencyBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            String newValue = currencyBox.getItems().get(observableValue.getValue().intValue());
            if(!newValue.equals(currencyState)) {
//                currencyState = newValue;
                try {
                    model.setCurrencyState(newValue);
                    model.updateOperations(currencyState, newValue);
                } catch (Exception e) {
                    logger.info(e.toString());
                }
                currencyState = newValue;
            }
        });
    }


}
