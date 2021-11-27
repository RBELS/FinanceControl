package com.example.financecontrol.settingsview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CurrencyItem;
import com.example.financecontrol.dbmodels.OperationItem;
import com.example.financecontrol.utils.NotificationLabel;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import java.io.File;
import java.io.FileOutputStream;
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
     * versionText - displays used app version
     */
    @FXML private Text versionText;
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
     * successLabel - label shown if operation was successful
     */
    private NotificationLabel successLabel;


    /**
     * onXlsBtClick method which chooses and create new path to a new folder 'output.xls' with the help of {@link SettingsController#getFolderPath()} method, and puts all information of your income/expenses and price from database into this file of xls format
     * @throws IOException when the I/O operations were failed or interrupted
     */
    @FXML protected void onXlsBtClick() throws IOException {
        String folderPath = getFolderPath();
        if(!folderPath.equals("")) {
            String fileStr = folderPath + "/output.xls";
            File file = new File(fileStr);
            if(file.createNewFile()) {
                try (HSSFWorkbook workbook = new HSSFWorkbook()) {
                    HSSFSheet sheet = workbook.createSheet("Operations");
                    Cell cell;
                    Row row = sheet.createRow(0);
                    HSSFCellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);

                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue("id");
                    cell.setCellStyle(style);

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue("date");
                    cell.setCellStyle(style);

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue("price");
                    cell.setCellStyle(style);

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue("name");
                    cell.setCellStyle(style);

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue("category");
                    cell.setCellStyle(style);

                    List<OperationItem> operationItemList = model.getOperations(2, "", "");
                    for (int i = 1; i <= operationItemList.size(); i++) {
                        row = sheet.createRow(i);

                        cell = row.createCell(0, CellType.NUMERIC);
                        cell.setCellValue(i);

                        cell = row.createCell(1, CellType.STRING);
                        cell.setCellValue(operationItemList.get(i - 1).date());

                        cell = row.createCell(2, CellType.NUMERIC);
                        cell.setCellValue(operationItemList.get(i - 1).price());

                        cell = row.createCell(3, CellType.STRING);
                        cell.setCellValue(operationItemList.get(i - 1).name());

                        cell = row.createCell(4, CellType.STRING);
                        cell.setCellValue(operationItemList.get(i - 1).category());
                    }

                    FileOutputStream outFile = new FileOutputStream(file);
                    workbook.write(outFile);
                    workbook.close();
                    successLabel.show();
                } catch (Exception e) {
                    logger.info(e.toString());
                }
            }
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



            for (int i = 0; i < operationItemList.size(); i++) {
                OperationItem item = operationItemList.get(i);
                paragraph = new Paragraph((i+1) + ".\n", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12));
                paragraph.add(new Chunk(String.format("Name: %s%nCategory: %s%nPrice: %.2f%nDate: %s%n", item.name(), item.category(), item.price(), item.date())));
                document.add(paragraph);
            }
            document.close();
            successLabel.show();
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
        NotificationLabel errorLabel = new NotificationLabel("No Internet Connection", false, 80,65);
        successLabel = new NotificationLabel("Successfully operated", true, 80, 65);
        containerPane.getChildren().addAll(errorLabel.getLabel(), successLabel.getLabel());
        versionText.setText("v1.0.0");

        try {
            currencies = model.getCurrencies();
            currencyState = model.getCurrencyState();
        } catch (SQLException e) {
            logger.info(e.toString());
        }

        currencyBox.setValue(currencyState);

        for(CurrencyItem item : currencies) {
            currencyBox.getItems().add(item.name());
        }

        currencyBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            String newValue = currencyBox.getItems().get(observableValue.getValue().intValue());
            if(!newValue.equals(currencyState)) {
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
