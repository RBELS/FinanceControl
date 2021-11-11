package com.example.financecontrol.dbmodels;

/**
 * CategoriesTable class which sets the names of the categories table columns(in database)
 * @author Dana
 * @version 1.0
 */
public class CategoriesTable {
    /**
     * an 'id' column
     */
    public static final String ID = "id";
    /**
     * a 'name' column
     */
    public static final String NAME = "name";
    /**
     * a 'color' column
     */
    public static final String COLOR = "color";
    /**
     * a 'type' column(0 - expenses, 1 - income)
     */
    public static final String TYPE = "type";

    /**
     * CategoriesTable constructor
     */
    private CategoriesTable() {}
}
