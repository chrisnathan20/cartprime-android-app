package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreRecyclerAdapter;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity {

    private ArrayList<Store> storesList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        recyclerView = findViewById(R.id.recyclerViewId);

        storesList = new ArrayList<>();

        setStoreInfo();
        setAdapter();
    }

    public void setStoreInfo(){
        storesList.add(new Store("Walmart", "Retail"));
        storesList.add(new Store("Target", "Retail"));
        storesList.add(new Store("Lowes", "Retail"));
    }

    public void setAdapter(){
        StoreRecyclerAdapter adapter = new StoreRecyclerAdapter(storesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}