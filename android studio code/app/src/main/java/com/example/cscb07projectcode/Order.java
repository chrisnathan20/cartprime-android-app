package com.example.cscb07projectcode;
import android.util.Log;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> itemsList;
    private int orderId;
    private String orderStatus;
    private String customerId;
    private String storeName;

    public Order() {
    }

    public Order(ArrayList<Item> itemsList, int orderId, String orderStatus, String customerId, String storeName) {
        this.itemsList = itemsList;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.storeName = storeName;
    }

    public String computeTotalPrice(){
        double totalPrice = 0;
        Log.i("pricerino", String.valueOf(itemsList));
        if(!itemsList.isEmpty()) {
            for (Item item : itemsList) {
                double temp = item.getPrice() * item.getQuantity();
                totalPrice = totalPrice + temp;
                Log.i("pricerino", String.valueOf(item.getPrice()));
                Log.i("pricerino", String.valueOf(item.getQuantity()));
                Log.i("pricerino", String.valueOf(totalPrice));
            }
        }
        return String.valueOf(totalPrice);
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        int max= 999999;
        int min = 100000;
        int random = (int) Math.floor(Math.random()*(max-min+1)+min); //RANDOMIZING THE ORDER ID
        this.orderId = random;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}