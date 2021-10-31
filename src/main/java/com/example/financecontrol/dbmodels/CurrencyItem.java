package com.example.financecontrol.dbmodels;

public class CurrencyItem {
    private final int id;
    private final String name;
    private final int apiId;

    public CurrencyItem(int id, String name, int apiId) {
        this.id = id;
        this.name = name;
        this.apiId = apiId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getApiId() {
        return apiId;
    }
}
