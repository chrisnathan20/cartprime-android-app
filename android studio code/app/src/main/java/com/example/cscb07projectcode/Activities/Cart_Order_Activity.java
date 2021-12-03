package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07projectcode.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.cscb07projectcode.Item;
import java.util.ArrayList;

public class Cart_Order_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order);

        Intent intent = getIntent();
        String name = intent.getStringExtra("first");
        Log.i("Taggg",name );
    }
}