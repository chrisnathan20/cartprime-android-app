package com.example.cscb07projectcode;
import java.util.HashMap;
public class Order {
    private HashMap<String,Integer> orderContents;
    private int orderId;
    private boolean orderStatus;
    private Customer customer;
    private Store store;


    public Order()
    {

    }

    public Order(HashMap<String,Integer > orderContents,int orderId,boolean orderStatus,Customer customer,Store store)
    { setOrderContents(orderContents); setOrderId(orderId);setOrderStatus(orderStatus);
        setStore(store); setCustomer(customer);}

    public void setOrderContents(HashMap<String,Integer> OC)
    { this.orderContents.clear();this.orderContents = OC; }
    public void setOrderId(int orderId)
    {   int max= 999999;
        int min = 100000;
        int random = (int)Math.floor(Math.random()*(max-min+1)+min); //RANDOMIZING THE ORDER ID
        this.orderId = random ;}
    public void setOrderStatus(boolean orderStatus)
    {this.orderStatus = orderStatus;}
    public void setCustomer(Customer customer)
    {this.customer = customer;}
    public void setStore(Store store)
    {this.store = store;}

    public HashMap<String,Integer> getOrderContents()
    { return this.orderContents; }
    public int getOrderId()
    {return this.orderId; }
    public boolean getOrderStatus()
    {return this.orderStatus;}
    public Customer getCustomer()
    {return this.customer;}
    public Store getStore()
    {return this.store;}

    @Override
    public String toString()
    {
        return "Order id "+ String.valueOf(this.orderId) + "Customer Name" + customer.getFirstname() + " " +customer.getLastname() +" Store Name: " + store.getName() + " item summary: "+orderContents.keySet().toString();
    }
}