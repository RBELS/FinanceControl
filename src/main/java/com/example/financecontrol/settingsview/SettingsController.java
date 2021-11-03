package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CurrencyItem;
import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.utils.ErrorLabel;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

;

public class SettingsController implements Initializable {
    @FXML private ChoiceBox<String> currencyBox;
    @FXML private AnchorPane containerPane;
    @FXML private Button xlsbt;
    
    private List<CurrencyItem> currencies;
    private final FinanceControlModel model = new FinanceControlModel();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String currencyState;

    @FXML protected void onXlsBtClick() throws IOException, SQLException {
        File file = new File("C:/", "output.xls");
        file.createNewFile();
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write("");
        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        List<OperationItem> operationItemList = model.getOperations(2, "", "");
        for(OperationItem item : operationItemList) {
            csvWriter.writeNext(item.toStringArray());
        }
        csvWriter.close();
//        CSVWriter
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ErrorLabel label = new ErrorLabel("No Internet connection",80,65);
        containerPane.getChildren().add(label.getLabel());

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
                    boolean updated = model.updateOperations(currencyState, newValue);
                    if(updated) {
                        model.setCurrencyState(newValue);
                        currencyState = newValue;
                    } else {
                        label.show();
                    }
                    System.out.println(updated);

                } catch (Exception e) {
                    logger.info(e.toString());
                }
            }
        });
    }


}
