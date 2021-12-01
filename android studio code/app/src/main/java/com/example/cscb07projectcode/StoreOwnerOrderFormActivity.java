package com.example.cscb07projectcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class StoreOwnerOrderFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_order_form);

        SharedPreferences pref = getSharedPreferences("ordersData", Context.MODE_PRIVATE);
        String orderId = pref.getString("orderId", "");

        TextView order_id = (TextView) findViewById(R.id.textView20);
        order_id.setText("money");

        Log.i("yessirski", String.valueOf(orderId));
    }
}