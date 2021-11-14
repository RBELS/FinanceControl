package com.example.financecontrol.expensesview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CategoriesItem;
import com.example.financecontrol.utils.NotificationLabel;
import com.example.financecontrol.utils.ValidationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * ExpensesController class that implements {@link Initializable} class which controls mouth click connection with the program and database and adds the input data to the database, after which it updates empty text fields and displays a message about the successful or failed addition
 * @author Dana
 * @version 1.0
 */
public class ExpensesController implements Initializable {
    /**
     * model - an object of {@link FinanceControlModel} type
     */
    private final FinanceControlModel model;
    /**
     * list - a list object of {@link CategoriesItem} type
     */
    private List<CategoriesItem> list;
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * ExpensesController constructor which initializes a model object as a new {@link FinanceControlModel} class
     */
    public ExpensesController() {
        model = new FinanceControlModel();
    }

    /**
     * categoryChoiceBox - a ChoiceBox object of a string type
     */
    @FXML private ChoiceBox<String> categoryChoiceBox;
    /**
     * nameTextField - a TextField object
     */
    @FXML private TextField nameTextField;
    /**
     * priceTextField - a TextField object
     */
    @FXML private TextField priceTextField;
    /**
     * container - an AnchorPane object
     */
    @FXML private AnchorPane container;
    /**
     * errorLabel - a {@link NotificationLabel} object which can show information about failed operation
     */
    private NotificationLabel errorLabel;
    /**
     * successLabel - a {@link NotificationLabel} object which can show information about succeed operation
     */
    private NotificationLabel successLabel;

    /**
     * onAddBtClick method which adds (with the help of {@link FinanceControlModel#addExpense(String, double, String)} method) the input data into expenses table when you click by mouth on an AddButton
     * @throws SQLException when there is error connected with a database access
     */
    @FXML
    protected void onAddBtClick() throws SQLException {
        if (ValidationUtil.validateInputEI(
                categoryChoiceBox.getValue(),nameTextField.getText(),priceTextField.getText()
        )) {
            model.addExpense(
                    nameTextField.getText(),
                    Double.parseDouble(priceTextField.getText()),
                    categoryChoiceBox.getValue()
            );
            nameTextField.setText("");
            priceTextField.setText("");
            categoryChoiceBox.setValue(null);
            successLabel.show();
        } else {
            errorLabel.show();
        }
    }

    /**
     * initialize method which shows an error('Incorrect input') or successful('Successfully added') label when adding operation is finished and catches the SQLException
     * @param url the url variable
     * @param resourceBundle the resourceBundle variable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel = new NotificationLabel("Incorrect input", false, 200, 20);
        successLabel = new NotificationLabel("Successfully added", true, 200, 20);

        container.getChildren().addAll(errorLabel.getLabel(), successLabel.getLabel());

        try {
            list = model.getCategories(0);
        } catch (SQLException e) {
            logger.info(e.toString());
        }

        for (CategoriesItem item : list) {
            categoryChoiceBox.getItems().add(item.getName());
        }

    }


}
