package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cscb07projectcode.R;

public class LoginCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
    }

    public void register_button (View view){
        Intent intent = new Intent(this, RegisterCustomerActivity.class);
        startActivity(intent);
    }

    public void login_button (View view){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        startActivity(intent);
    }
}