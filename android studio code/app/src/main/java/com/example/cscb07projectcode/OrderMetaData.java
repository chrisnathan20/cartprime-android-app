package com.example.cscb07projectcode;
import java.util.ArrayList;

public class OrderMetaData {
    private int orderId;
    private String orderStatus;
    private String customerId;
    private String storeName;

    public OrderMetaData(){
    }

    public OrderMetaData(int orderId, String orderStatus, String customerId, String storeName) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.storeName = storeName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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