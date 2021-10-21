package com.example.financecontrol.utils;

import java.util.logging.Logger;

class NegativeNumberException extends Exception
{
    public NegativeNumberException() {}

    public NegativeNumberException(String message)
    {
        super(message);
    }
}

public class ValidationUtil {
    private static final Logger logger = Logger.getLogger(ValidationUtil.class.getName());

    private static boolean isNumeric(String str) {
        if (str == null) return  false;
        try {
            Double.parseDouble(str);
        }
        catch (NumberFormatException e) {
            logger.info(e.toString());
            return false;
        }
        try
        {
            Double price = Double.parseDouble(str);
            if(price <= 0)
            {
                throw new NegativeNumberException();
            }
        }
        catch(NegativeNumberException e)
        {
            logger.info(e.toString());
            return false;
        }
        return true;
    }

    public static boolean validateInputEI(String categoryValue, String nameTextFieldValue, String priceTextFieldValue) {
        return categoryValue != null
                && !nameTextFieldValue.equals("")
                && isNumeric(priceTextFieldValue);
    }


}
