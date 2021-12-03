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
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void republish_button(View view){
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

        // connect edittext views
        EditText productName_id = (EditText) findViewById(R.id.editTextTextPersonName3);

        // convert view inputs to string
        String productName_field = productName_id.getText().toString();

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
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(storename).child("products");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product until product name matches
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    Item item = newChild.getValue(Item.class);
                                    Log.i("edit",productName_field);
                                    Log.i("edit", item.getName());
                                    if(productName_field.equals(item.getName())){
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

    public void displayAlertIncompleteOrder(String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

        builder.setTitle("Republish denied");
        builder.setMessage(product_name + " found in incomplete order");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void displayAlertTakenProduct(String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);

        builder.setTitle("Republish denied");
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
}