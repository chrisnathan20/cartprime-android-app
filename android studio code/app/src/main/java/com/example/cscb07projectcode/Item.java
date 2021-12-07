package com.example.cscb07projectcode;


import java.util.Objects;

public class Item  {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;
  //  private boolean available;

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
        String x = this.name + ";" + this.description + ";" + this.price + ";" + this.quantity + ";" + this.unit;
        // ADDED A DELIMETER TO MAKE SURE I CAN EXTRACT STUFF WHILE DOING THE CART
        return x;
    }

    public boolean getAvailable()
    {
        return this.quantity >0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 && name.equals(item.name) && description.equals(item.description) && unit.equals(item.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, unit);
    }
}
