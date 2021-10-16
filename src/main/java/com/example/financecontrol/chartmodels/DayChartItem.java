package com.example.financecontrol.chartmodels;

public class DayChartItem {
    private final String name;
    private int sum;

    public DayChartItem(String name) {
        this.name = name;
        this.sum = 0;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public void addSum(int val) {
        this.sum+=val;
    }
}
