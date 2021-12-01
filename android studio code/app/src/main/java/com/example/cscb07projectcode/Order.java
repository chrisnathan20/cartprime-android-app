package com.example.cscb07projectcode;
import java.util.ArrayList;

public class Order {
    private ArrayList<ReservedItem> reservedItemsList;
    private int orderId;
    private String orderStatus;
    private String customerId;
    private String storeName;

    public Order() {
    }

    public Order(ArrayList<ReservedItem> reservedItemsList, int orderId, String orderStatus, String customerId, String storeName){
        setReservedItems(reservedItemsList);
        setOrderId(orderId);
        setOrderStatus(orderStatus);
        setStore(storeName);
        setCustomer(customerId);
    }

    public void setReservedItems(ArrayList<ReservedItem> reservedItemsList) {
        this.reservedItemsList = reservedItemsList;
    }

    public void setOrderId(int orderId){
        int max= 999999;
        int min = 100000;
        int random = (int) Math.floor(Math.random()*(max-min+1)+min); //RANDOMIZING THE ORDER ID
        this.orderId = random;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCustomer(String customerId) {
        this.customerId = customerId;
    }

    public void setStore(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<ReservedItem> getReservedItems() {
        return this.reservedItemsList;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public String getCustomer() {
        return this.customerId;
    }

    public String getStore() {
        return this.storeName;
    }
}