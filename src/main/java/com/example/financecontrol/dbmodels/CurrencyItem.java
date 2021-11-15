package com.example.financecontrol.dbmodels;

/**
 * CurrencyItem class which initializes all object for Currency table
 *
 * @author Dana
 * @version 1.0
 */
public record CurrencyItem(int id, String name, int apiId) {
    /**
     * CurrencyItem constructor which receives data and initializes it to class objects
     *
     * @param id    sets a special id for each category
     * @param name  sets the name of currencies
     * @param apiId shows the rate of change of currencies
     */
    public CurrencyItem {
    }

    /**
     * getID method which returns the value of id object from class
     *
     * @return {@link CurrencyItem#id}
     */
    public int getId() {
        return id;
    }

    /**
     * getName method which returns the value of name object from class
     *
     * @return {@link CurrencyItem#name}
     */
    public String getName() {
        return name;
    }

    /**
     * getApiId method which returns the value of apiId object from class
     *
     * @return {@link CurrencyItem#apiId}
     */
    public int getApiId() {
        return apiId;
    }
}
