package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.ProductRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> stores;
    ArrayAdapter<String> adapter;
    Store store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("stores/list_of_stores");
        stores = new ArrayList<>();
        adapter =new ArrayAdapter<String>(this,R.layout.store_info_for_customer_main_activity,R.id.userInfo,stores);
        store = new Store();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    store = ds.getValue(Store.class);
                    stores.add(store.getName().toString() +"\n\n Description: "+store.getDescription().toString());




                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}