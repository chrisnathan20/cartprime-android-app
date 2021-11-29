package com.example.cscb07projectcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginStoreOwnerActivity extends AppCompatActivity {

    public static final String username_key = "username_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store_owner);
    }

    public void register_button (View view){
        Intent intent = new Intent(this, RegisterStoreOwnerActivity.class);
        startActivity(intent);
    }

    public void login_button (View view){
        Intent intent = new Intent(this, StoreOwnerMainActivity.class);

        // pass data through intent into the next activity, which is the store owner's menu page
        EditText editText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String username = editText.getText().toString();
        intent.putExtra(username_key, username);

        // navigate to the next activity
        startActivity(intent);
    }
}