package com.example.cscb07projectcode;

import java.util.*;

public class Customer {
    private String username;
    private String fullName;
    private String password;
    private ArrayList<Order> orders;

    public Customer() {
    }

    public Customer(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.orders = new ArrayList<Order>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String newfullName) {
        this.fullName = newfullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public ArrayList<Order> getOrders(){
        return orders;
    }

    public void setOrders(ArrayList<Order> newOrders) {
        this.orders = newOrders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
