package com.example.cscb07projectcode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Item {

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String unit;
    private boolean available;

    public Item() {
    }

    public Item(String name, String description, double price, int quantity, String unit) {
        setName(name);
        setDescription(description);
        setQuantity(quantity);
        setUnit(unit);
        setPrice(price);
        setAvailable();
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
    public void setAvailable()
    {
        this.available = this.quantity >0;
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

    public boolean getAvailable()
    {
        return this.quantity >0;
    }

    public int hashCode() {
        int x = 0;
        int p = (int) this.price;
        x = this.quantity + p ;
        return x;
    }


    public static DiffUtil.ItemCallback<Item> itemItemCallback = new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getDescription().equals(newItem.getDescription());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.equals(newItem);
        }
    };

}
