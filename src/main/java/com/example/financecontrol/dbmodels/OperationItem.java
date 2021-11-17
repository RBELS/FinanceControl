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
     * OperationItem which receives data and initializes it to class objects
     *
     * @param id            {@link OperationItem#id}
     * @param date          {@link OperationItem#date}
     * @param price         {@link OperationItem#price}
     * @param name          {@link OperationItem#name}
     * @param category      {@link OperationItem#category}
     * @param categoryColor {@link OperationItem#categoryColor}
     */


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
