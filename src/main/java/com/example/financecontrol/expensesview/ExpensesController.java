package com.example.financecontrol.expensesview;

import com.example.financecontrol.FinanceControlModel;
import com.example.financecontrol.dbmodels.CategoriesItem;
import com.example.financecontrol.utils.ErrorLabel;
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

public class ExpensesController implements Initializable {
    private final FinanceControlModel model;
    private List<CategoriesItem> list;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public ExpensesController() {
        model = new FinanceControlModel();
    }

    @FXML private ChoiceBox<String> categoryChoiceBox;
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private AnchorPane container;

    private ErrorLabel errorLabel;

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
        } else {
            errorLabel.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel = new ErrorLabel("Incorrect input", 200, 20);
        container.getChildren().add(errorLabel.getLabel());

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
