package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddProductActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    // called when publish button is pressed and writes new product data to database
    public void publish_button(View view) {
        // Get the Intent that started this activity and extract the username
//        String username = getIntent().getStringExtra(LoginStoreOwnerActivity.username_key);
        //        Log.i("mytag", username);
        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

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
        String strprice_field = price_id.getText().toString();

        // store user input for product quantity
        EditText quantity_id = (EditText) findViewById(R.id.editTextTextPersonName6);
        String strquantity_field = quantity_id.getText().toString();

        // store user input for product measurement unit
        EditText unit_id = (EditText) findViewById(R.id.editTextTextPersonName9);
        String unit_field = unit_id.getText().toString();

        // store notification message
        TextView notifyMessage_id = (TextView) findViewById(R.id.textView26);

        // reset field_status=true on each button click
        boolean field_status = true;

        if (productname_field.isEmpty()) {
            field_status = false;
            productname_id.setError("Please fill in the product's name.");
        }

        if (description_field.isEmpty()) {
            field_status = false;
            description_id.setError("Please fill in the product's description.");
        }

        if (String.valueOf(strprice_field).isEmpty()) {
            field_status = false;
            price_id.setError("Please fill in the price of the product");
        }

        if (String.valueOf(strquantity_field).isEmpty()) {
            field_status = false;
            quantity_id.setError("Please fill in the product's quantity.");
        }

        if (unit_field.isEmpty()) {
            field_status = false;
            unit_id.setError("Please specify in the product's unit type.");
            Log.i("empty", "unit is empty");
        }

        // if any input fields are empty
        if (field_status == false) {
            notifyMessage_id.setText("Please fill in all input fields");
            Log.i("empty", "field status is empty");
        }
        // if all input fields are filled
        else {
            // restore the data types of the variables
            int quantity_field = Integer.parseInt(strquantity_field);
            double price_field = Double.parseDouble(strprice_field);
            // leverages store owner's username to find their store name
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // loops through user (of type store owner) until it matches a username
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        StoreOwner storeowner = child.getValue(StoreOwner.class);
                        Log.i("mytag", username);
                        Log.i("mytag", storeowner.getUsername());
                        if (username.equals(storeowner.getUsername())) {
                            Log.i("mytag", "works");
                            // extract store name of storeowner
                            String storename = storeowner.getStorename();
                            // create a new product instance
                            Item item = new Item(productname_field, description_field, price_field, quantity_field, unit_field);

                            // validate if product name already exists in the database
                            DatabaseReference newRef = FirebaseDatabase.getInstance().getReference().child("stores").child("list_of_stores").child(storename).child("products").child(productname_field);
                            ValueEventListener newListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot newSnapshot) {
                                    Log.i("exists", newRef.getKey());
                                    // if product already exists
                                    if (newSnapshot.exists()) {
                                        productname_id.setError("This product already exists in the store.");

                                        // store input for message notification;
                                        notifyMessage_id.setText("This product already exists in the store.");
                                    }
                                    // if product does not exist, append and write it to the database
                                    else {
                                        // get database reference built on first database reference object
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        // append new product under the storeowner's store as a child with key: product name
                                        ref.child("stores").child("list_of_stores").child(storename).child("products").child(productname_field).setValue(item);
                                        Log.i("mytag", storename);
                                        // pass data through intent into the next activity, which is the store owner's menu page
                                        newIntent.putExtra(username_key, username);
                                        startActivity(newIntent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            };
                            newRef.addListenerForSingleValueEvent(newListener);
                            break;
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
    }
}