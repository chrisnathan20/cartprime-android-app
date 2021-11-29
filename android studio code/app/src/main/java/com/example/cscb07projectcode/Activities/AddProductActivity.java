package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    // called when publish button is pressed and writes new product data to database
    public void publish_button(View view){
        // Get the Intent that started this activity and extract the username
        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);
        //        Log.i("mytag", username);

        // set navigation for store owner to their home page when item is created
        Intent newIntent = new Intent(this, StoreOwnerMainActivity.class);

        // store user input for product name
        EditText productname_id = (EditText) findViewById(R.id.editTextTextPersonName3);
        String productname_field = productname_id.getText().toString();

        // store user input for product description
        EditText description_id = (EditText) findViewById(R.id.editTextTextPersonName4);
        String description_field = description_id.getText().toString();

        // store user input for product price
        EditText price_id = (EditText) findViewById(R.id.editTextTextPersonName5);
        double price_field = Double.parseDouble(price_id.getText().toString());

        // store user input for product quantity
        EditText quantity_id = (EditText) findViewById(R.id.editTextTextPersonName6);
        int quantity_field = Integer.parseInt(quantity_id.getText().toString());

        // leverages store owner's username to find their store name
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    StoreOwner storeowner = child.getValue(StoreOwner.class);
                    Log.i("mytag", username);
                    Log.i("mytag", storeowner.getUsername());
                    if(username.equals(storeowner.getUsername())){
                        Log.i("mytag", "works");
                        // extract store name of storeowner
                        String storename = storeowner.getStorename();
                        // create a new product instance
                        Item item = new Item(productname_field, description_field, price_field, quantity_field, "kg");
                        // get database reference object
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        // append new product under the storeowner's store as a child with key: product name
                        ref.child("stores").child("list_of_stores").child(storename).child("products").child(productname_field).setValue(item);
                        Log.i("mytag", storename);
                        break;
                    }
                }
                startActivity(newIntent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);
    }
}