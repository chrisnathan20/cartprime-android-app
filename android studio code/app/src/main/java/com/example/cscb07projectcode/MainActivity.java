package com.example.cscb07projectcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // re-directs user to Login page for Store Owners
    public void storeOwner_button (View view){
        Intent intent = new Intent(this, LoginStoreOwnerActivity.class);
        startActivity(intent);
    }

    // re-directs user to Login page for Customers
    public void customer_button (View view){
        Intent intent = new Intent(this, LoginCustomerActivity.class);
        startActivity(intent);
    }


}