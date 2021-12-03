package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public void displayErrorMessage(String message) {
        TextView alert = (TextView) findViewById(R.id.TextViewAlert);
        alert.setText(message);
        alert.setTextColor(0xFFFF0000);
    }

    public void displaySuccessMessage(String message) {
        TextView alert = (TextView) findViewById(R.id.TextViewAlert);
        alert.setText(message);
        alert.setTextColor(0xFF00FF00);
    }


    public void onSuccess(String username) {
        Intent intent = new Intent(this, CustomerMainActivity.class);

        // pass data through intent into the next activity, which is the store owner's menu page
        intent.putExtra(username_key, username);
        // write username into a shared preference
        SharedPreferences pref = getSharedPreferences("credentialsCustomer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username", username);
        editor.apply();
        // navigate to the next activity
        startActivity(intent);
    }

    public void login_button (View view){

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