package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;

import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerMainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT ="com.example.application.cscbo7projectcode.extratest";
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> stores;
    ArrayList<String> stores_;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    Store store;
    Store store_;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("stores/list_of_stores");
        stores = new ArrayList<>();
        stores_ = new ArrayList<>();
        adapter =new ArrayAdapter<String>(this,R.layout.store_info_for_customer_main_activity,R.id.userInfo,stores);
        adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,stores_);
        store = new Store();
        store_ = new Store();
        spinner = (Spinner)findViewById(R.id.spinnerdata);
        Button button = (Button) findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          openNext();
                                      }
                                  }

        );



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren())
                {
                    store = ds.getValue(Store.class);
                    stores.add(store.getName().toString() +"\n\n Description: "+store.getDescription().toString());
                    store_ = ds.getValue(Store.class);
                    stores_.add(store_.getName().toString());



                }
                listView.setAdapter(adapter);
                spinner.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**public class MySpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

     public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
     String selected = parent.getItemAtPosition(pos).toString();
     }

     public void onNothingSelected(AdapterView parent) {
     // Do nothing.
     }
     } **/
    //final String USN = spinner.getSelectedItem().toString();
    public void openNext(){

        //String text_to_send = spinner.getOnItemSelectedListener().toString();

        Intent intent = new Intent(this, CustomerOrderActivity.class);
        String x = spinner.getSelectedItem().toString();
        intent.putExtra("getData",x);
        SharedPreferences pref = getSharedPreferences("credentials_store_name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("store_name", x); // saving the current store name to a local file
        editor.apply();
        startActivity(intent);


    }


}