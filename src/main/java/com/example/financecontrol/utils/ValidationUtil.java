package com.example.financecontrol.utils;

import java.util.logging.Logger;

/**
 * ValidationUtil class which checks input data for correctness (price must be entered and be greater than zero, expenses/income category name must be entered)
 * @author Dana
 * @version 1.0
 */
public class ValidationUtil {
    /**
     * logger - an object of {@link java.lang.System.Logger} type which contains a string with an information about the runtime class and its name
     */
    private static final Logger logger = Logger.getLogger(ValidationUtil.class.getName());

    /**
     * validatePrice method which check the input price for correctness(greater than zero) and transform it from string to double
     * @param str an input price of your chosen category
     * @return true if the input data matches the conditions, false if there is an error in input data or it's not entered
     */
    private static boolean validatePrice(String str) {
        if (str == null) return  false;
        try {
            double price = Double.parseDouble(str);
            if(price <= 0) {
                return false;
            }
        }
        catch (NumberFormatException e) {
            logger.info(e.toString());
            return false;
        }
        return true;
    }

    /**
     * validateInputEI method which checks the input category name and price(with the help of method {@link ValidationUtil#validatePrice(String)}) for correctness(it needs to be entered)
     * @param categoryValue the chosen category of expenses/income
     * @param nameTextFieldValue the name of chosen category
     * @param priceTextFieldValue the price that was spent during the execution of the category
     * @return true if all data was entered correct, false if there was a typing error
     */
    public static boolean validateInputEI(String categoryValue, String nameTextFieldValue, String priceTextFieldValue) {
        return categoryValue != null
                && !nameTextFieldValue.equals("")
                && validatePrice(priceTextFieldValue);
    }

    /**
     * ValidationUtil constructor
     */
    private ValidationUtil() {}
}

