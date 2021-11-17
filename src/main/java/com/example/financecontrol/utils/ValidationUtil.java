package com.example.financecontrol.utils;

import java.util.logging.Logger;

public class ValidationUtil {
    private static final Logger logger = Logger.getLogger(ValidationUtil.class.getName());

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

    public static boolean validateInputEI(String categoryValue, String nameTextFieldValue, String priceTextFieldValue) {
        return categoryValue != null
                && !nameTextFieldValue.equals("")
                && validatePrice(priceTextFieldValue);
    }

    private ValidationUtil() {}
}

