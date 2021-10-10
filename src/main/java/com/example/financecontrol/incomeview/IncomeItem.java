package com.example.financecontrol.incomeview;

public class IncomeItem {
    private int id;
    private int date;
    private int price;
    private String name;
    private String category;


    public IncomeItem(int id, int date, int price, String name, String category) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }
    public int getDate() {
        return date;
    }
    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
}
