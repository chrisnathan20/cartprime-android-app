package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.ProductRecyclerAdapter;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreOwnerMainActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_main);

        recyclerView = findViewById(R.id.recyclerId);

        itemsList = new ArrayList<>();

        setStoreInfo();
        setAdapter();
    }

    public void setStoreInfo() {
        // Get the Intent that started this activity and extract the username
        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);

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
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(itemsList);
        Log.i("mytag", String.valueOf(adapter.getItemCount()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setClickable(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void addproduct_button(View view){
        // Get the Intent that started this activity and extract the username
        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);

        // assign the intent to open add a product form
        Intent intent = new Intent(this, AddProductActivity.class);

        // go into the next activity and pass the username
        intent.putExtra(username_key, username);

        // start the next activity
        startActivity(intent);
    }
}