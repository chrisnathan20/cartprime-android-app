package com.example.cscb07projectcode.Activities;

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

import java.util.ArrayList;

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
        itemsList.add(new Item("Walmart", "Retail"));
        itemsList.add(new Item("Walmart", "Retail"));
        itemsList.add(new Item("Walmart", "Retail"));
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