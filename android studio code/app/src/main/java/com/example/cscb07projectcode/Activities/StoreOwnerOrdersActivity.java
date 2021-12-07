package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.OrderMetaData;
import com.example.cscb07projectcode.OrderRecyclerAdapter;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreOwnerOrdersActivity extends AppCompatActivity {

    public static String storename = null;
    public String logoutStatus;
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
    }

    public void setStoreInfo() {

        SharedPreferences pref = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

        // find the store name based on store owner id
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");
        ValueEventListener valueListener = new ValueEventListener() {
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
                                    if(storename.equals(orderMetaData.getStoreName()) && orderMetaData.getOrderStatus().equals("Incomplete")){
                                        OrderMetaData newOrder = new OrderMetaData(
                                            orderMetaData.getOrderId(),
                                            orderMetaData.getOrderStatus(),
                                            orderMetaData.getCustomerId(),
                                            orderMetaData.getStoreName());
                                        ordersList.add(newOrder);
                                    }
                                }
                                if(ordersList.isEmpty()){
                                    TextView orderMsg = (TextView) findViewById(R.id.textView30);
                                    orderMsg.setText("There are no pending orders.");
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
        ref.addValueEventListener(valueListener);
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
                Intent intent = new Intent(getApplicationContext(), StoreOwnerOrderFormActivity.class);

                // write orderId into a shared preference
                SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("orderIdKey", String.valueOf(ordersList.get(position).getOrderId()));
                editor.putString("CustomerIdKey", String.valueOf(ordersList.get(position).getCustomerId()));
                editor.putString("fromComplete", "false");
                editor.apply();

                startActivity(intent);
            }
        };
    }

    public void orderHistory_button(View view){
        Intent intent = new Intent(this, StoreOwnerOrderHistoryActivity.class);
        startActivity(intent);
    }

    // adds button to toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_button, menu);
        return true;
    }
    // adds function to onclick button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {

            // initialize alert
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setIcon(R.drawable.question_mark)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // clicked no, do action
                            logoutStatus = "false";
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // clicked yes, do action
                            logoutStatus = "true";
                            returnMainActivity(logoutStatus);
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void returnMainActivity(String logoutStatus) {
        if (logoutStatus.equals("true")) {
            // start new activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}