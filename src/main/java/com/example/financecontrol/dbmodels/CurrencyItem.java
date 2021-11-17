package com.example.financecontrol.dbmodels;

/**
 * CurrencyItem class which initializes all object for Currency table
 *
 * @author Dana
 * @version 1.0
 */
public record CurrencyItem(int id, String name, int apiId) {
    /**
     * CurrencyItem which receives data and initializes it to class objects
     *
     * @param id    sets a special id for each category
     * @param name  sets the name of currencies
     * @param apiId shows the rate of change of currencies
     */
}
