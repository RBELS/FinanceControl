package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CurrencyItem;
import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.utils.ErrorLabel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileOutputStream;
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

    private List<CurrencyItem> currencies;
    private final FinanceControlModel model = new FinanceControlModel();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String currencyState;

    @FXML protected void onXlsBtClick() throws IOException, SQLException {
        String system = System.getProperty("os.name");
        String fileStr;
        if(system.contains("Windows")) {
            fileStr = System.getenv("USERPROFILE") + "\\AppData\\Local\\FinancialControl\\output.xls";

        } else {
            fileStr = System.getenv("HOME") + "/Documents/output.xls";
        }

        File file = new File(fileStr);
        file.createNewFile();
        CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
        csvWriter.writeNext(new String[] {"id", "date", "price", "name", "category"});
        List<OperationItem> operationItemList = model.getOperations(2, "", "");
        for(int i = 0;i < operationItemList.size();i++) {
            csvWriter.writeNext(operationItemList.get(i).toStringArray(i));
        }
        csvWriter.close();
    }

    @FXML protected void onPdfBtClick() throws IOException, DocumentException, SQLException {
        String system = System.getProperty("os.name");
        String file;
        if(system.contains("Windows")) {
            file = System.getenv("USERPROFILE") + "\\AppData\\Local\\FinancialControl\\output.pdf";
        } else {
            file = System.getenv("HOME") + "/Documents/output.pdf";
        }


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));//change
        document.open();
        document.addTitle("Expenses and Income");
        Paragraph paragraph;
        List<OperationItem> operationItemList = model.getOperations(2, "", "");
        for(int i = 0;i < operationItemList.size();i++) {
            OperationItem item = operationItemList.get(i);
            paragraph = new Paragraph(String.format("%d) %s      %s      %.2f      %s", i, item.getName(), item.getCategory(), item.getPrice(), item.getDate()));
            document.add(paragraph);
        }
        document.close();
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
