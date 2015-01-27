package com.github.alxwhtmr.herbs.logic;


/**
 * The {@code Substance} class that represents a
 * substance (like vitamins and minerals) for the {@code Goods}
 *
 * @since 25.12.2014
 */
public class Substance {
    private String name = "";
    private double amountPerServing = 0;
    private String measurement = "";
    private int dailyValue = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountPerServing() {
        return amountPerServing;
    }

    public void setAmountPerServing(double amountPerServing) {
        this.amountPerServing = amountPerServing;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public int getDailyValue() {
        return dailyValue;
    }

    public void setDailyValue(int dailyValue) {
        this.dailyValue = dailyValue;
    }
}
