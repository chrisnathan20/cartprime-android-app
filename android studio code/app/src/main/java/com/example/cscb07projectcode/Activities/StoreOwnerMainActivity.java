package com.example.cscb07projectcode.Activities;

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
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.ProductRecyclerAdapter;
import com.example.cscb07projectcode.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StoreOwnerMainActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    public String logoutStatus;
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
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(store.getName()).child("products");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product and append it into the arraylist
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    Item item = newChild.getValue(Item.class);
                                    itemsList.add(item);

                                }
                                setAdapter();
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