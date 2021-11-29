package com.example.cscb07projectcode;

import java.util.*;

public class Store {

    //private ArrayList<Order> orders;
    private String name;
    private String description;

    //private HashMap<Item, Integer> itemstock; //Key: item, Value: Stock Number

    //Empty Constructor
    public Store() {
    }

    //Normal Constructor
    public Store(String name, String description) {
        this.name = name;
        this.description = description;
        //this.orders = new ArrayList<Order>();
        //this.itemstock = new HashMap<Item, Integer>();
    }

    //setters and getters

    /*
    public ArrayList<Order> getOrders(){
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*
    public HashMap<Item, Integer> getItemstock() {
        return itemstock;
    }

    public void setItemstock(HashMap<Item, Integer> itemstock) {
        this.itemstock = itemstock;
    }

    public void removeItem(Item item) {
        itemstock.remove(item);
    }

    public void addnewItem(Item item, Integer stock) {
        itemstock.put(item, stock);
    }

    public void incrementStock(Item item, Integer inc) {
        if(itemstock.get(item) + inc < 0) {
            System.out.println("Invalid Increment Amount");
        }
        else {
            itemstock.put(key, itemstock.get(item) + inc);
        }
    }
    */

}
