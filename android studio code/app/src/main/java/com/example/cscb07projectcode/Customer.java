package com.example.cscb07projectcode;

import java.util.*;

public class Customer {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    //private ArrayList<Order> orders;

    public Customer() {
    }

    public Customer(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        //this.orders = new ArrayList<Order>();
    }

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String newFirstname) {
        this.firstname = newFirstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String newLastname) {
        this.firstname = newLastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /*public ArrayList<Order> getOrders(){
        return orders;
    }

    public void setOrders(ArrayList<Order> newOrders) {
        this.orders = newOrders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }*/
}
