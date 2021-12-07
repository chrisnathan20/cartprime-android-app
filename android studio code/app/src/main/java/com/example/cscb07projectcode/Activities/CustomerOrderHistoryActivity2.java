package com.example.cscb07projectcode.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07projectcode.CustomerOrderRecyclerAdapter;
import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.OrderMetaData;
import com.example.cscb07projectcode.OrderRecyclerAdapter;
import com.example.cscb07projectcode.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomerOrderHistoryActivity2 extends AppCompatActivity {
    public static String storename = null;
    public static ArrayList<OrderMetaData> ordersList;
    public static ArrayList<Item> itemsList;
    private RecyclerView recyclerView;
    private CustomerOrderRecyclerAdapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history2);
        recyclerView = findViewById(R.id.recyclerOrderHistoryId);
        ordersList = new ArrayList<OrderMetaData>();
        itemsList = new ArrayList<Item>();
        setHistoryInfo();
    }



    public void setHistoryInfo() {


        SharedPreferences pref = getSharedPreferences("credentialsCustomer", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");

        // find the store name based on store owner id
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("orders").child("list_of_orders");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot newChild:snapshot.getChildren()) {
                    OrderMetaData orderMetaData = newChild.getValue(OrderMetaData.class);
                    if(username.equals(orderMetaData.getCustomerId()) & orderMetaData.getOrderStatus().equals("Complete")){
                        OrderMetaData newOrder = new OrderMetaData(
                                orderMetaData.getOrderId(),
                                orderMetaData.getOrderStatus(),
                                orderMetaData.getCustomerId(),
                                orderMetaData.getStoreName());
                        ordersList.add(newOrder);
                    }
                    setAdapter();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(valueEventListener);

    }
    public void setAdapter(){
        setOnClickListener();
        CustomerOrderRecyclerAdapter adapter = new CustomerOrderRecyclerAdapter(ordersList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setClickable(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    private void setOnClickListener() {
        listener = new CustomerOrderRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), CustomerOrderFormActivity2.class);

                // write username into a shared preference
                SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("orderIdKey", String.valueOf(ordersList.get(position).getOrderId()));
                editor.putString("CustomerIdKey", String.valueOf(ordersList.get(position).getStoreName()));
                editor.putString("fromComplete", "true");
                editor.apply();

                startActivity(intent);
            }
        };
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
                            String logoutStatus = "false";
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // clicked yes, do action
                            String logoutStatus = "true";
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