package com.example.financecontrol.dbmodels;

public class CategroiesItem {
    private final String value;
    private final String color;
    private final int id;

    public CategroiesItem(String name, String color, int id) {
        this.value = name;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
