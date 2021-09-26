package com.example.financecontrol.dbModels;

public class CategroiesItem {
    private String value;
    private String color;
    private int id;

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
