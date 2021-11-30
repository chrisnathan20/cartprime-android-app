package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cscb07projectcode.CustomerModel;
import com.example.cscb07projectcode.LoginContract;
import com.example.cscb07projectcode.LoginPresenter;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.StoreOwnerModel;

public class LoginCustomerActivity extends AppCompatActivity  implements LoginContract.View {

    public static final String username_key = "username_key";

    private LoginContract.Presenter presenter;

    public String getUsername(){
        EditText editText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        return editText.getText().toString();
    }

    public String getPassword(){
        EditText editText = (EditText)  findViewById(R.id.editTextTextPassword2);
        return editText.getText().toString();
    }

    public void displayMessage(String message) {
        TextView alert = (TextView) findViewById(R.id.TextViewAlert);
        alert.setText(message);
    }


    public void onSuccess(String username) {
        Intent intent = new Intent(this, CustomerMainActivity.class);

        // pass data through intent into the next activity, which is the store owner's menu page
        intent.putExtra(username_key, username);

        // navigate to the next activity
        startActivity(intent);
    }

    public void login_button (View view){
        //commented out, intent passing implemented in method onSuccess
        /*
        Intent intent = new Intent(this, StoreOwnerMainActivity.class);

        // pass data through intent into the next activity, which is the store owner's menu page
        EditText editText = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        String username = editText.getText().toString();
        intent.putExtra(username_key, username);

        // navigate to the next activity
        startActivity(intent);
         */

        presenter.checkLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);
        presenter = new LoginPresenter(new CustomerModel(), this);
    }

    public void register_button (View view){
        Intent intent = new Intent(this, RegisterCustomerActivity.class);
        startActivity(intent);
    }

    public void no_authorization_login (View view){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        startActivity(intent);
    }
}