package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CurrencyItem;
import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.utils.NotificationLabel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SettingsController implements Initializable {
    @FXML private ChoiceBox<String> currencyBox;
    @FXML private AnchorPane containerPane;

    private List<CurrencyItem> currencies;
    private final FinanceControlModel model = new FinanceControlModel();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private String currencyState;

    @FXML protected void onXlsBtClick() throws IOException, SQLException {
        String folderPath = getFolderPath();
        if(!folderPath.equals("")) {
            String fileStr = folderPath + "/output.xls";
            File file = new File(fileStr);
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            csvWriter.writeNext(new String[] {"        id              date            price          name        category"});
            List<OperationItem> operationItemList = model.getOperations(2, "", "");
            for(int i = 0;i < operationItemList.size();i++) {
                csvWriter.writeNext(operationItemList.get(i).toStringArray(i));
            }
            csvWriter.close();
        }
    }

    @FXML protected void onPdfBtClick() throws IOException, DocumentException, SQLException {
        String folderPath = getFolderPath();
        if(!folderPath.equals("")) {
            String fileStr = folderPath + "/output.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileStr));//change
            document.open();
            document.addTitle("Expenses and Income");
            Paragraph paragraph;
            List<OperationItem> operationItemList = model.getOperations(2, "", "");
            for(int i = 0;i < operationItemList.size();i++) {
                OperationItem item = operationItemList.get(i);
                paragraph = new Paragraph(String.format("%d) %s      %s      %.2f      %s", i+1, item.getName(), item.getCategory(), item.getPrice(), item.getDate()));
                document.add(paragraph);
            }
            document.close();
        }
    }

    private String getFolderPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(containerPane.getScene().getWindow());

        return selectedDirectory == null ? "" : selectedDirectory.getAbsolutePath();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NotificationLabel errorLabel = new NotificationLabel("No Internet connection", false, 80,65);
        NotificationLabel successLabel = new NotificationLabel("Successfully changed", true, 80, 65);
        containerPane.getChildren().add(errorLabel.getLabel());

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
                        successLabel.show();
                    } else {
                        errorLabel.show();
                    }
                } catch (Exception e) {
                    logger.info(e.toString());
                }
            }
        });
    }


}
