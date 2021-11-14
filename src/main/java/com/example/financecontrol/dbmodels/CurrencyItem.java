package com.example.financecontrol.dbmodels;

/**
 * CurrencyItem class which initializes all object for Currency table
 * @author Dana
 * @version 1.0
 */
public class CurrencyItem {
    /**
     * id - an int object which sets a special id for each currency
     */
    private final int id;
    /**
     * name - a string object which sets the name of currencies
     */
    private final String name;
    /**
     * apiID - an int object which shows the rate of change of currencies
     */
    private final int apiId;

    /**
     * CurrencyItem constructor which receives data and initializes it to class objects
     * @param id sets a special id for each category
     * @param name sets the name of currencies
     * @param apiId shows the rate of change of currencies
     */
    public CurrencyItem(int id, String name, int apiId) {
        this.id = id;
        this.name = name;
        this.apiId = apiId;
    }

    /**
     * getID method which returns the value of id object from class
     * @return {@link CurrencyItem#id}
     */
    public int getId() {
        return id;
    }

    /**
     * getName method which returns the value of name object from class
     * @return {@link CurrencyItem#name}
     */
    public String getName() {
        return name;
    }

    /**
     * getApiId method which returns the value of apiId object from class
     * @return {@link CurrencyItem#apiId}
     */
    public int getApiId() {
        return apiId;
    }
}
