package com.example.cscb07projectcode;

public class ItemMetaData {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;
    //  private boolean available;

    public ItemMetaData() {
    }

    public ItemMetaData(String name, String description, double price, int quantity, String unit) {
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnit(unit);
        setPrice(price);

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription(){ return this.description;}

    public double getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getUnit() {
        return this.unit;
    }
}
