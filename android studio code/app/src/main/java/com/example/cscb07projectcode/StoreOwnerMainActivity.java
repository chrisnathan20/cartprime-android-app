package com.example.cscb07projectcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class StoreOwnerMainActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner_main);
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