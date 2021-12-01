package com.example.cscb07projectcode.Activities;

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

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.ProductRecyclerAdapter;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreOwnerOrdersActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StoreOwnerMainActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;
    private ProductRecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_main);

        recyclerView = findViewById(R.id.recyclerId);

        itemsList = new ArrayList<>();

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

        // leverages store owner's username to find their store name
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    Store store = child.getValue(Store.class);
                    if(username.equals(store.getStoreowner())){
                        Log.i("mytag", "passed into storeowner");
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(store.getName()).child("products");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product and append it into the arraylist
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    Item item = newChild.getValue(Item.class);
                                    itemsList.add(item);
                                    Log.i("mytag", item.getName());
                                    Log.i("mytag", item.getDescription());

                                }
                                setAdapter();
//                                Log.i("mytag", itemsList.toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
//                        // store hashmap into a variable
//                        Object products = child.getValue();
//                        HashMap other = (HashMap) products;
////                        Log.i("mtag",child.getValue().get("products").getClass().toString());
//                        Log.i("mtag",child.getValue().toString());
//                        Log.i("mtag",other.get("products").toString());
                        newRef.addValueEventListener(newListener);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
//        itemsList.add(new Item("test", "test", 25.44, 34, "test"));
    }


    public void setAdapter(){
        setOnClickListener();
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(itemsList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setClickable(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.i("mytag", "passed thru adapter");
    }

    // called when clicking on any product under current products
    private void setOnClickListener() {
        listener = new ProductRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), EditProductActivity.class);

                // use intent as a vehicle to transfer product details into next activity
                intent.putExtra("productName", itemsList.get(position).getName());
                intent.putExtra("productDesc", itemsList.get(position).getDescription());
                intent.putExtra("productPrice", String.valueOf(itemsList.get(position).getPrice()));
                intent.putExtra("productQty", String.valueOf(itemsList.get(position).getQuantity()));
                intent.putExtra("productUnit", itemsList.get(position).getUnit());

//                Log.i("mytager", itemsList.get(position).getName());
                startActivity(intent);
            }
        };
    }

    public void addproduct_button(View view){
        // Get the Intent that started this activity and extract the username
//        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);

        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        // assign the intent to open add a product form
        Intent intent = new Intent(this, AddProductActivity.class);

        // go into the next activity and pass the username
//        intent.putExtra(username_key, username);

        // start the next activity
        startActivity(intent);
    }

    public void ordersIcon_button(View view){
        Intent intent = new Intent(this, StoreOwnerOrdersActivity.class);
        startActivity(intent);
    }

}