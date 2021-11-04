package com.example.financecontrol.dbmodels;

public class OperationItem {
    private final int id;
    private final double price;
    private final String date;
    private final String name;
    private final String category;
    private final String categoryColor;


    public OperationItem(int id, String date, double price, String name, String category, String categoryColor) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.name = name;
        this.category = category;
        this.categoryColor = categoryColor;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public String[] toStringArray(int index) {
        return new String[] {String.valueOf(index), date, String.valueOf(price), name, category};
    }
}
