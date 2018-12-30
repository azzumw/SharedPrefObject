package com.example.macintosh.sharedprefobject;

public class Ingredients{
    private String name;
    private int  qty;
    private String unit;

    public Ingredients(String name, int qty, String unit) {
        this.name = name;
        this.qty = qty;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
