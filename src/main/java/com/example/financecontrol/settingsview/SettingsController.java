package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CategoriesItem;
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

/**
 * SettingsController class that implements {@link Initializable} class which controls buttons in the settings window: can display income, expense and price information from database to xls file and pdf document, select location of xls and pdf files, display a message about successful or unsuccessful currency change
 * @author Dana
 * @version 1.0
 */
public class SettingsController implements Initializable {
    /**
     * currencyBox - a ChoiceBox object of a string type
     */
    @FXML private ChoiceBox<String> currencyBox;
    /**
     * containerPane - an AnchorPane object
     */
    @FXML private AnchorPane containerPane;

    /**
     * currencies - a list object of {@link CurrencyItem} type
     */
    private List<CurrencyItem> currencies;
    /**
     * model - an object of {@link FinanceControlModel} type
     */
    private final FinanceControlModel model = new FinanceControlModel();
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private final Logger logger = Logger.getLogger(getClass().getName());
    /**
     * currencyState - a string object which shows current currency
     */
    private String currencyState;

    /**
     * onXlsBtClick method which chooses and create new path to a new folder 'output.xls' with the help of {@link SettingsController#getFolderPath()} method, and puts all information of your income/expenses and price from database into this file of xls format
     * @throws IOException when the I/O operations were failed or interrupted
     * @throws SQLException when there is error connected with a database access
     */
    @FXML protected void onXlsBtClick() throws IOException, SQLException {
        String folderPath = getFolderPath();
        if(!folderPath.equals("")) {
            String fileStr = folderPath + "/output.xls";
            File file = new File(fileStr);
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            csvWriter.writeNext(new String[] {"id", "date", "price", "name", "category"}); //wtf??
            List<OperationItem> operationItemList = model.getOperations(2, "", "");
            for(int i = 0;i < operationItemList.size();i++) {
                csvWriter.writeNext(operationItemList.get(i).toStringArray(i));
            }
            csvWriter.close();
        }
    }

    /**
     * onPdfBtClick method which chooses and create new path to a new folder 'output.pdf' with the help of {@link SettingsController#getFolderPath()} method, and puts all information of your income/expenses and price from database into this document of pdf format
     * @throws IOException when the I/O operations were failed or interrupted
     * @throws DocumentException when there is a problem connected with creating or connecting to a document
     * @throws SQLException when there is error connected with a database access
     */
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

    /**
     * getFolderPath method which creates new file directory and shows a dialog window to choose a path for this file
     * @return returns chosen file directory
     */
    private String getFolderPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(containerPane.getScene().getWindow());

        return selectedDirectory == null ? "" : selectedDirectory.getAbsolutePath();
    }

    /**
     * initialize method which shows an error('No Internet connection') or successful('Successfully changed') label when currency changing operation is finished and catches the SQLException
     * @param url the url variable
     * @param resourceBundle the resourceBundle variable
     */
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
