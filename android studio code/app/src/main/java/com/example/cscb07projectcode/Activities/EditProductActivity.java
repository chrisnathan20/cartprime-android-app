package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.Order;
import com.example.cscb07projectcode.OrderExtraction;
import com.example.cscb07projectcode.OrderMetaData;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // retrieve data from intent
        String productName = getIntent().getStringExtra("productName");
        String productDesc = getIntent().getStringExtra("productDesc");
        String productPrice = getIntent().getStringExtra("productPrice");
        String productQty = getIntent().getStringExtra("productQty");
        String productUnit = getIntent().getStringExtra("productUnit");

        // connect edittext views
        EditText productName_id = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText productDesc_id = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText productPrice_id= (EditText) findViewById(R.id.editTextTextPersonName5);
        EditText productQty_id = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText productUnit_id = (EditText) findViewById(R.id.editTextTextPersonName9);

        // convert view inputs to string
        String productName_field = productName_id.getText().toString();
        String productDesc_field = productDesc_id.getText().toString();
        String productPrice_field = productPrice_id.getText().toString();
        String productQty_field = productQty_id.getText().toString();
        String productUnit_field = productUnit_id.getText().toString();

        // set data inside input fields
        productName_id.setText(productName);
        productDesc_id.setText(productDesc);
        productPrice_id.setText(productPrice);
        productQty_id.setText(productQty);
        productUnit_id.setText(productUnit);
    }

    public void old_republish_button(View view){
        Intent intent = new Intent(this, StoreOwnerMainActivity.class);
        // connect edittext views
        EditText productName_id = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText productDesc_id = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText productPrice_id= (EditText) findViewById(R.id.editTextTextPersonName5);
        EditText productQty_id = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText productUnit_id = (EditText) findViewById(R.id.editTextTextPersonName9);

        // convert view inputs to string
        String productName_field = productName_id.getText().toString();
        String productDesc_field = productDesc_id.getText().toString();
        String productPrice_field = productPrice_id.getText().toString();
        String productQty_field = productQty_id.getText().toString();
        String productUnit_field = productUnit_id.getText().toString();

        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

        // leverages store owner's username to find their store name
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    StoreOwner storeowner = child.getValue(StoreOwner.class);
                    if(username.equals(storeowner.getUsername())){
                        // extract store name of storeowner
                        String storename = storeowner.getStorename();
                        // create a new product instance
                        Item item = new Item(
                                productName_field,
                                productDesc_field,
                                Double.parseDouble(productPrice_field),
                                Integer.parseInt(productQty_field),
                                productUnit_field);
                        // get database reference object
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        // overwrite with a new product under the storeowner's store as a child with key: product name
                        ref.child("stores").child("list_of_stores").child(storename).child("products").child(productName_field).setValue(item);
                        Log.i("mytag", storename);
                        break;
                    }
                }
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
    }

    public void deleteProduct_button(View view){
        Intent intent = new Intent(this, StoreOwnerMainActivity.class);

        // convert view inputs to string
        String productName_field = getIntent().getStringExtra("productName");

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
                        // extract store name of storeowner
                        String storename = store.getName();


                        Log.i("edit", username);

                        //check if the store still has any incomplete orders
                        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference user = root.child("orders").child("list_of_orders");

                        user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                boolean incomplete_and_contains = false;
                                Log.v("Store working with", storename);
                                // loops through to gather orders
                                for (DataSnapshot orderdata : dataSnapshot.getChildren()) {
                                    OrderMetaData order = orderdata.getValue(OrderMetaData.class);

                                    Log.v("Store Name", order.getStoreName());
                                    if (order.getOrderStatus().equals("Incomplete") & storename.equals(order.getStoreName())) {
                                        String Order_id = Integer.toString(order.getOrderId());
                                        Log.v("Work on", order.getStoreName());

                                        incomplete_and_contains = true;
                                    }
                                }

                                if (incomplete_and_contains) {
                                    displayAlertIncompleteOrder2(productName_field);
                                } else {
                                    // create a database reference to access list of products
                                    DatabaseReference newRef = ref.child(storename).child("products");
                                    ValueEventListener newListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            // iterate through each product until product name matches
                                            for (DataSnapshot newChild : snapshot.getChildren()) {
                                                Item item = newChild.getValue(Item.class);
                                                Log.i("edit", productName_field);
                                                Log.i("edit", item.getName());
                                                if (productName_field.equals(item.getName())) {
                                                    newChild.getRef().removeValue();
                                                    Log.i("edit", productName_field);
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    };
                                    newRef.addListenerForSingleValueEvent(newListener);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
    }

    public void displayAlertIncompleteOrder(String product_name){
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

            builder.setTitle(product_name + " edit denied");
            builder.setMessage("please complete all incomplete orders before editing");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }


    public void displayAlertIncompleteOrder2(String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

        builder.setTitle(product_name + " delete denied");
        builder.setMessage("please complete all incomplete orders before deleting");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void displayAlertTakenProduct(String product_name, String prev_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

        builder.setTitle(prev_name + " edit denied");
        builder.setMessage(product_name + " is taken, please choose another product name");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void flexible_republish_button(View view){

        /* algorithm to handle with key change and occurrences of said product in orders

           Identify which type of republish we are performing

           first type:
           Qty_change
           - Qty_change if only Qty gets edited
           - identifies as an update of stock
           - can be performed regardless having incomplete orders with said product in it

           second type:
           Non Qty_change
           - Only can be performed if product not in any incomplete orders

           General Republish Algorithm:
           - Delete previous product
           - Add new product

           Warning: this code is disgusting
        */

        // extract prev info of product before change
        String prev_productName = getIntent().getStringExtra("productName");
        String prev_productDesc = getIntent().getStringExtra("productDesc");
        String prev_productPrice = getIntent().getStringExtra("productPrice");
        String prev_productQty = getIntent().getStringExtra("productQty");
        String prev_productUnit = getIntent().getStringExtra("productUnit");

        // connect edittext views
        EditText productName_id = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText productDesc_id = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText productPrice_id= (EditText) findViewById(R.id.editTextTextPersonName5);
        EditText productQty_id = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText productUnit_id = (EditText) findViewById(R.id.editTextTextPersonName9);

        // convert view inputs to string
        String productName_field = productName_id.getText().toString();
        String productDesc_field = productDesc_id.getText().toString();
        String productPrice_field = productPrice_id.getText().toString();
        String productQty_field = productQty_id.getText().toString();
        String productUnit_field = productUnit_id.getText().toString();

        // declare booleans to be used for stock_update check
        Boolean name_change = prev_productName.equals(productName_field);
        Boolean desc_change = prev_productDesc.equals(productDesc_field);
        Boolean price_change = prev_productPrice.equals(productPrice_field);
        Boolean qty_change = prev_productQty.equals(productQty_field);
        Boolean unit_change = prev_productUnit.equals(productUnit_field);

        Boolean no_change = name_change&desc_change&price_change&qty_change&unit_change;
        Boolean qty_change_type = (!qty_change & (name_change&desc_change&price_change&unit_change));

        Intent intent = new Intent(this, StoreOwnerMainActivity.class);

        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

        if(no_change){
            startActivity(intent);
            Log.v("product change type", "no_change");
        }
        else if(qty_change_type){
            // same as previous republish button functionality
            Log.v("product change type", "qty_change_type");
            // leverages store owner's username to find their store name
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // loops through user (of type store owner) until it matches a username
                    for(DataSnapshot child:dataSnapshot.getChildren()) {
                        StoreOwner storeowner = child.getValue(StoreOwner.class);
                        if(username.equals(storeowner.getUsername())){
                            // extract store name of storeowner
                            String storename = storeowner.getStorename();
                            // create a new product instance
                            Item item = new Item(
                                    productName_field,
                                    productDesc_field,
                                    Double.parseDouble(productPrice_field),
                                    Integer.parseInt(productQty_field),
                                    productUnit_field);
                            // get database reference object
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            // overwrite with a new product under the storeowner's store as a child with key: product name
                            ref.child("stores").child("list_of_stores").child(storename).child("products").child(productName_field).setValue(item);
                            Log.i("mytag", storename);
                            break;
                        }
                    }
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("warning", "loadPost:onCancelled", databaseError.toException());
                }
            };
            ref.addListenerForSingleValueEvent(listener);
        }

        else{
            Log.v("product change type", "normal_change");
            // leverages store owner's username to find their store name
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // loops through user (of type store owner) until it matches a username
                    for(DataSnapshot child:dataSnapshot.getChildren()) {
                        StoreOwner storeowner = child.getValue(StoreOwner.class);
                        if(username.equals(storeowner.getUsername())){
                            // extract store name of storeowner
                            String storename = storeowner.getStorename();

                            DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference user = root.child("stores").child("list_of_stores").child(storename).child("products").child(productName_field);

                            user.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if(snapshot.exists() && !name_change){
                                        Log.v("Invalid status","name already taken");
                                        displayAlertTakenProduct(productName_field, prev_productName);
                                    }
                                    else {
                                        Log.v("Valid status", "continues with normal_change");

                                        //check if there are incomplete orders with the product in it

                                        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
                                        DatabaseReference user = root.child("orders").child("list_of_orders");

                                        user.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //ArrayList<Order> orders = new ArrayList<Order>();

                                                boolean incomplete_and_contains = false;
                                                Log.v("Store working with", storename);
                                                // loops through to gather orders
                                                for (DataSnapshot orderdata : dataSnapshot.getChildren()) {
                                                    OrderMetaData order = orderdata.getValue(OrderMetaData.class);

                                                    Log.v("Store Name", order.getStoreName());
                                                    if(order.getOrderStatus().equals("Incomplete") & storename.equals(order.getStoreName())){
                                                        String Order_id = Integer.toString(order.getOrderId());
                                                        Log.v("Work on", order.getStoreName());

                                                        //HashMap<String, Item> itemsList = order.getItemsList();
                                                        //I need some algorithm to check if the product exist in an incomplete order
                                                        //For now if there are incomplete data then cannot change

                                                        //if (itemsList.containsKey(prev_productName)){
                                                            //Log.v("Hashmap Status", prev_productName + " found in incomplete order");
                                                        //}
                                                        incomplete_and_contains = true;

                                                    }
                                                }

                                                Log.v("Loop Status", "incomplete is " + Boolean.toString(incomplete_and_contains));

                                                if(incomplete_and_contains){
                                                    Log.v("incomplete and contains", "unable to republish");
                                                    displayAlertIncompleteOrder(prev_productName);
                                                }
                                                else{
                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores");
                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            // loops through user (of type store owner) until it matches a username
                                                            for(DataSnapshot child:snapshot.getChildren()) {
                                                                Store store = child.getValue(Store.class);
                                                                if (username.equals(store.getStoreowner())) {
                                                                    Log.i("edit", username);
                                                                    // create a database reference to access list of products
                                                                    DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores").child(storename).child("products");
                                                                    newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                            // iterate through each product until product name matches
                                                                            for(DataSnapshot newChild:snapshot.getChildren()) {
                                                                                Item item = newChild.getValue(Item.class);
                                                                                Log.i("edit", prev_productName);
                                                                                Log.i("edit", item.getName());
                                                                                if(prev_productName.equals(item.getName())){
                                                                                    newChild.getRef().removeValue();
                                                                                    Log.i("edit", prev_productName);

                                                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
                                                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                            // loops through user (of type store owner) until it matches a username
                                                                                            for(DataSnapshot child:dataSnapshot.getChildren()) {
                                                                                                StoreOwner storeowner = child.getValue(StoreOwner.class);
                                                                                                if(username.equals(storeowner.getUsername())){
                                                                                                    // extract store name of storeowner
                                                                                                    String storename = storeowner.getStorename();
                                                                                                    // create a new product instance
                                                                                                    Item item = new Item(
                                                                                                            productName_field,
                                                                                                            productDesc_field,
                                                                                                            Double.parseDouble(productPrice_field),
                                                                                                            Integer.parseInt(productQty_field),
                                                                                                            productUnit_field);
                                                                                                    // get database reference object
                                                                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                                                                    // overwrite with a new product under the storeowner's store as a child with key: product name
                                                                                                    ref.child("stores").child("list_of_stores").child(storename).child("products").child(productName_field).setValue(item);
                                                                                                    Log.i("mytag", storename);
                                                                                                    break;
                                                                                                }
                                                                                            }
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(DatabaseError databaseError) {
                                                                                            Log.w("warning", "loadPost:onCancelled", databaseError.toException());
                                                                                        }
                                                                                    });
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                                        }
                                                                    });break;
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("warning", "loadPost:onCancelled", databaseError.toException());
                }
            };
            ref.addListenerForSingleValueEvent(listener);
        }
    }
}