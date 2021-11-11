package com.example.financecontrol.dbmodels;

/**
 * CategoriesItem class which initializes all object for Categories table
 * @author Dana
 * @version 1.0
 */
public class CategoriesItem {
    /**
     * Value - a string object which names the category
     */
    private final String value;
    /**
     * color - a string object which selects the display color of this category
     */
    private final String color;
    /**
     * id - an int object which sets a special id for each category
     */
    private final int id;

    /**
     * CategoriesItem method which takes values for the table of categories and passes them to class objects
     * @param name a name of a category
     * @param color display color of this category
     * @param id id of this category
     */
    public CategoriesItem(String name, String color, int id) {
        this.value = name;
        this.color = color;
        this.id = id;
    }

    /**
     * getName method which gets an object value from class CategoriesItem and returns it
     * @return a name of category
     */
    public String getName() {
        return value;
    }

    /**
     * getColor method which gets an object color from class CategoriesItem and returns it
     * @return display color of this category
     */
    public String getColor() {
        return color;
    }

    /**
     * getId method which gets an object id from class CategoriesItem and returns it
     * @return an id of this category
     */
    public int getId() {
        return id;
    }
}
