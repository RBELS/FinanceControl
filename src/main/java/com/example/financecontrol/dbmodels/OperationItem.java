package com.example.financecontrol.dbmodels;

/**
 * OperationItem class which initializes all object for expenses and income tables
 *
 * @author Dana
 * @version 1.0
 */
public record OperationItem(int id, String date, double price, String name,
                            String category, String categoryColor) {
    /**
     * OperationItem constructor which receives data and initializes it to class objects
     *
     * @param id            {@link OperationItem#id}
     * @param date          {@link OperationItem#date}
     * @param price         {@link OperationItem#price}
     * @param name          {@link OperationItem#name}
     * @param category      {@link OperationItem#category}
     * @param categoryColor {@link OperationItem#categoryColor}
     */
    public OperationItem {
    }

    /**
     * getID method which returns the value of id object from class
     *
     * @return {@link OperationItem#id}
     */
    public int getId() {
        return id;
    }

    /**
     * getDate method which returns the value of date object from class
     *
     * @return {@link OperationItem#date}
     */
    public String getDate() {
        return date;
    }

    /**
     * getPrice method which returns the value of price object from class
     *
     * @return {@link OperationItem#price}
     */
    public double getPrice() {
        return price;
    }

    /**
     * getName method which returns the value of name object from class
     *
     * @return {@link OperationItem#name}
     */
    public String getName() {
        return name;
    }

    /**
     * getCategory method which returns the value of category object from class
     *
     * @return {@link OperationItem#category}
     */
    public String getCategory() {
        return category;
    }

    /**
     * getCategoryColor method which returns the value of CategoryColor object from class
     *
     * @return {@link OperationItem#categoryColor}
     */
    public String getCategoryColor() {
        return categoryColor;
    }

    /**
     * toStringArray method receives an index and creates new string array of information from table row
     *
     * @param index an id of expenses/income
     * @return a string array of special expense/income
     */
    public String[] toStringArray(int index) {
        return new String[]{String.valueOf(index), date, String.valueOf(price), name, category};
    }
}
