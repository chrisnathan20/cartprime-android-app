package com.example.cscb07projectcode.Activities.CartStuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cscb07projectcode.R;
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        String value= getIntent().getStringExtra("getData");
        SharedPreferences pref = getSharedPreferences("credentials_store_name", Context.MODE_PRIVATE);
        String username = pref.getString("store_name", ""); // retrieving the store name
    }
}