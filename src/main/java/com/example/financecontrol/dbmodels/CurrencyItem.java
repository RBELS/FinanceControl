package com.example.financecontrol.dbmodels;

/**
 * CurrencyItem record which receives id, name, data and initializes it to class objects
 * @param id    an int object which contains special id for each currency
 * @param name  a string object which contains the name of currencies
 * @param apiId an int object which contains the rate of change of currencies
 * @author Dana
 * @version 1.0
 */
public record CurrencyItem(int id, String name, int apiId) {

}
