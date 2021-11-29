package com.example.cscb07projectcode;

public class Item {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;

    public Item() {
    }

    public Item(String name, String description, double price, int quantity, String unit) {
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

    @Override
    public String toString() {
        return "Item Name: " + this.name + " Quantity: " + this.quantity + " " + this.unit + "for $" + this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        Item obj = (Item) o;
        if (obj.getName() != this.name &&
                obj.getDescription() != this.description &&
                obj.getPrice() != this.price &&
                obj.getQuantity() != this.quantity &&
                this.unit != obj.getUnit())
            return false;
        return true;
    }

    public int hashCode() {
        int x = 0;
        int p = (int) this.price;
        x = this.quantity + p ;
        return x;
    }

}
