package com.example.financecontrol.dbmodels;

/**
 * OperationItem record which receives id, data, price, name, category and category color and initializes it to class objects
 * @param id            an int object which contains id of each operation
 * @param date          a string object which contains the date of this operation
 * @param price         a double object which contains price that was spent during this operation
 * @param name          a string object which contains the name of this operation
 * @param category      a string object which contains the category of this operation
 * @param categoryColor a string object which contains a color of this category
 * @author Dana
 * @version 1.0
 */
public record OperationItem(int id, String date, double price, String name,
                            String category, String categoryColor) {

    /**
     * toStringArray method receives an index and creates new string array of information from table row
     * @param index an id of expenses/income
     * @return a string array of special expenses/income
     */
    public String[] toStringArray(int index) {
        return new String[]{String.valueOf(index), date, String.valueOf(price), name, category};
    }
}
