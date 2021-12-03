package com.example.cscb07projectcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07projectcode.Activities.EditProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreOwnerOrderFormActivity extends AppCompatActivity {

    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;
    private ProductRecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_order_form);

        // extract order id and set the value to corresponding order id textview
        SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
        String fromComplete = pref.getString("fromComplete","");
        if(fromComplete.equals("true")){
            // hides "complete" button for completing orders
            Button button = (Button) findViewById(R.id.button16);
            button.setVisibility(View.INVISIBLE);

            // change text to complete
            // change font colour to be green
            TextView statusView = (TextView) findViewById(R.id.textView23);
            statusView.setText("Complete");
            statusView.setTextColor(Color.parseColor("#28B463"));
        }
        else{
            TextView statusView = (TextView) findViewById(R.id.textView23);
            statusView.setText("Incomplete");
            statusView.setTextColor(Color.parseColor("#C0392B"));
        }

        // pull order/customer id from sharedpreference file
        String orderId = "Receipt Id: #" + pref.getString("orderIdKey", "");
        String CustomerId = pref.getString("CustomerIdKey", "");

        // set textview value via backend
        TextView order_id = (TextView) findViewById(R.id.textView5);
        TextView customer_id = (TextView) findViewById(R.id.textView20);
        order_id.setText(orderId);
        customer_id.setText(CustomerId);

        // initialize variables and call method for the recycler
        recyclerView = findViewById(R.id.recyclerOrderFormId);
        itemsList = new ArrayList<>();
        setStoreInfo();
    }

    public void setStoreInfo() {
        // extract order id and set the value to corresponding order id textview
        SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
        String orderId = pref.getString("orderIdKey", "");

        // leverages order id to find list of products
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("list_of_orders");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    OrderMetaData order = child.getValue(OrderMetaData.class);
                    Log.i("newtag", String.valueOf(order.getOrderId()));
                    Log.i("newtag", orderId);
                    if(orderId.equals(String.valueOf(order.getOrderId()))){
                        Log.i("newtag", "works");
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(orderId).child("itemsList");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // initialize total price variable
                                double totalPrice = 0;
                                // iterate through each product
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    // append it into the arraylist
                                    Item item = newChild.getValue(Item.class);
                                    itemsList.add(item);

                                    // also compute the total price
                                    double itemPrice = item.getPrice()*item.getQuantity();
                                    totalPrice = totalPrice + itemPrice;
                                }
                                // populates data in each container in the recycler view
                                setAdapter();
                                // update textview wtih total price value and change colour to green
                                TextView priceView = (TextView) findViewById(R.id.textView22);
                                priceView.setText("$" + String.valueOf(totalPrice));
                                priceView.setTextColor(Color.parseColor("#28B463"));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
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
//                Intent intent = new Intent(getApplicationContext(), StoreOwnerOrderFormActivity.class);

                // use intent as a vehicle to transfer product details into next activity
//                intent.putExtra("productUnit", itemsList.get(position).getUnit());
//                startActivity(intent);
                Toast.makeText(StoreOwnerOrderFormActivity.this, itemsList.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void completeOrder_button(View view){
        // extract order id and set the value to corresponding order id textview
        SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
        String orderId = pref.getString("orderIdKey", "");

        // leverages order id to find list of products
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("list_of_orders");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
//                    OrderMetaData order = child.getValue(OrderMetaData.class);
//                    order.setOrderStatus("Complete");
                    DatabaseReference newRef = ref.child(orderId);
//                    newRef.setValue(order);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("orderStatus", "Complete");
                    newRef.updateChildren(updates);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);

        Intent intent = new Intent(this, StoreOwnerOrdersActivity.class);
        startActivity(intent);
    }

    public void displayAlertStockShortage(String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(StoreOwnerOrderFormActivity.this);

        builder.setTitle("Order cannot be completed");
        builder.setMessage(product_name + " has insufficient stock");

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