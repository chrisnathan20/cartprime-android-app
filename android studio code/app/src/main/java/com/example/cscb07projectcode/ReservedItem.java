package com.example.cscb07projectcode;

public class ReservedItem {

    private String name;
    private double price;
    private int quantity;

    public ReservedItem() {
    }

    public ReservedItem(String name, double price, int quantity) {
        setName(name);
        setQuantity(quantity);;
        setPrice(price);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
