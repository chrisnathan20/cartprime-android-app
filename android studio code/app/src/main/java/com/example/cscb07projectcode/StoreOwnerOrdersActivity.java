package com.example.cscb07projectcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cscb07projectcode.Activities.EditProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StoreOwnerOrdersActivity extends AppCompatActivity {

    public static String storename = null;
    public static ArrayList<OrderMetaData> ordersList;
    public static ArrayList<Item> itemsList;
    private RecyclerView recyclerView;
    private OrderRecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_orders);

        recyclerView = findViewById(R.id.recyclerOrderId);
        ordersList = new ArrayList<OrderMetaData>();
        itemsList = new ArrayList<Item>();

        setStoreInfo();
        // set a short delay to read from database
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("mytag", "caught exception");
        }
    }

    public void setStoreInfo() {
        // Get the Intent that started this activity and extract the username
//        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);

        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

        // find the store name based on store owner id
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    StoreOwner storeowner = child.getValue(StoreOwner.class);
                    if(username.equals(storeowner.getUsername())){
                        // extract store name of storeowner
                        storename = storeowner.getStorename();
                        // leverages store name to find their orders
                        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("orders").child("list_of_orders");
                        ValueEventListener newListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // loops through user (of type store owner) until it matches a username
                                for(DataSnapshot newChild:dataSnapshot.getChildren()) {
                                    OrderMetaData orderMetaData = newChild.getValue(OrderMetaData.class);
                                    if(storename.equals(orderMetaData.getStoreName())){
                                        OrderMetaData newOrder = new OrderMetaData(
                                            orderMetaData.getOrderId(),
                                            orderMetaData.getOrderStatus(),
                                            orderMetaData.getCustomerId(),
                                            orderMetaData.getStoreName());
                                        ordersList.add(newOrder);
                                        Log.i("random", String.valueOf(orderMetaData.getOrderId()));
                                    }
                                }
                                setAdapter();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
                            }
                        };
                        newRef.addListenerForSingleValueEvent(newListener);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
    }


    public void setAdapter(){
        setOnClickListener();
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(ordersList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setClickable(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    // called when clicking on any product under current products
    private void setOnClickListener() {
        listener = new OrderRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), StoreOwnerOrdersActivity.class);

                // use intent as a vehicle to transfer product details into next activity
//                intent.putExtra("productName", itemsList.get(position).getName());
//                intent.putExtra("productDesc", itemsList.get(position).getDescription());
//                intent.putExtra("productPrice", String.valueOf(itemsList.get(position).getPrice()));
//                intent.putExtra("productQty", String.valueOf(itemsList.get(position).getQuantity()));
//                intent.putExtra("productUnit", itemsList.get(position).getUnit());

//                Log.i("mytager", itemsList.get(position).getName());
                startActivity(intent);
            }
        };
    }
}