package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cscb07projectcode.Activities.LoginStoreOwnerActivity;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.example.cscb07projectcode.StoreOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterStoreOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store_owner);
    }

    // called when user clicks "Create new account" button
    public void CreateNewAccount(View view) {
//        dismissKeyboard(this);

        // initialize the empty message notification
        TextView notifyMessage = (TextView) findViewById(R.id.textView);
        notifyMessage.setText("");

        // reset field_status=true on each button click
        boolean field_status = true;

        // set navigation for user to login page when account is successfully created
        Intent intent = new Intent(this, LoginStoreOwnerActivity.class);

        // store user input for first name
        EditText firstname_id = (EditText) findViewById(R.id.editTextTextPersonName);
        String firstname_field = firstname_id.getText().toString();

        // store user input for last name
        EditText lastname_id = (EditText) findViewById(R.id.editTextTextPersonName2);
        String lastname_field = lastname_id.getText().toString();

        // store user input for email (e.g. username)
        EditText email_id = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String email_field = email_id.getText().toString();

        // store user input for password
        EditText password_id = (EditText) findViewById(R.id.editTextTextPassword);
        String password_field = password_id.getText().toString();

        // store user input for retype_password
        EditText retype_password_id = (EditText) findViewById(R.id.editTextTextPassword3);
        String retype_password_field = retype_password_id.getText().toString();

        // store user input for storename
        EditText storename_id = (EditText) findViewById(R.id.editTextTextPersonName7);
        String storename_field = storename_id.getText().toString();

        // store user input for description
        EditText description_id = (EditText) findViewById(R.id.editTextTextPersonName8);
        String description_field = description_id.getText().toString();

        // checks if any input fields (e.g. first name, last name, email, password) are empty
        if(firstname_field.isEmpty()){
            field_status=false;
            firstname_id.setError("Please fill in your first name.");
        }
        if(lastname_field.isEmpty()){
            field_status=false;
            lastname_id.setError("Please fill in your last name.");
        }
        if(email_field.isEmpty()){
            field_status=false;
            email_id.setError("Please fill in your email.");
        }
        if(password_field.isEmpty()){
            field_status=false;
            password_id.setError("Please fill in your password.");
        }
        if(storename_field.isEmpty()){
            field_status=false;
            storename_id.setError("Please fill in your Store's name.");
        }
        if(description_field.isEmpty()){
            field_status=false;
            description_id.setError("Please fill in your Store's description.");
        }

        // checks if email input is valid
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(email_field);
        boolean valid_username = true;
        if (!matcher.matches()){
            notifyMessage.setText("Please input valid username.");
            valid_username = false;
        }

        // inform user that at least one input box is empty and needs to be filled
        if(!field_status){
            notifyMessage.setText("Please fill in all input fields.");
        }
        // proceed to next validation check: validates if email input is available
//        else if (valid_email){
        else {
            // check if password are matching
            if (!password_field.equals(retype_password_field)) {
                password_id.setError("Passwords do not match");
                retype_password_id.setError("Passwords do not match");
                notifyMessage.setText("Passwords do not match");
                dismissKeyboard(this);
            }
            else {
                // getReference(): selects data under key:"StoreOwners" who has a child named (email_field)
                // convention for storing data: StoreOwners -> (username) -> (firstname,lastname,username,password)
                // note: email and username are synonyms
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("taken_usernames").child(email_field);
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // if username is already taken
                        if (snapshot.exists()) {
                            // the reqason for if condition is because listener will instanteously trigger when new account is added
                            // this means it will show an error for a split second before re-directing you to login screen
                            // if statement prevents that
                            notifyMessage.setText("An account is already registered with that email.");
                            email_id.setError("Someone already has this email.");
                        }
                        // if username is not taken, then append new user into database
                        else {
                            DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("stores").child("taken_storeNames").child(storename_field);
                            ValueEventListener newListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot newsnapshot) {
                                    if (newsnapshot.exists()) {
                                        notifyMessage.setText("An account is already registered with that store name.");
                                        storename_id.setError("Someone already has this store name.");
                                    } else {
                                        // get database refernce
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                        // create a new user instance with user-specified data
                                        StoreOwner storeowner = new StoreOwner(firstname_field, lastname_field, email_field, password_field, storename_field);
                                        // append new user under StoreOwners as a child with key: email
                                        ref.child("users").child("storeowners").child(email_field).setValue(storeowner);
                                        // append username into list of pre-existing usernames
                                        ref.child("users").child("taken_usernames").child(email_field).setValue(email_field);

                                        // create a new store instance
                                        Store store = new Store(storename_field, description_field, storeowner.getUsername());
                                        // append new store data under stores with child key: storename
                                        ref.child("stores").child("list_of_stores").child(storename_field).setValue(store);
                                        // append store name into list of pre-existing store names
                                        ref.child("stores").child("taken_storeNames").child(storename_field).setValue(storename_field);

                                        notifyMessage.setTextColor(Color.parseColor("#00FF00"));
                                        notifyMessage.setText("Account was successfully registered!");

                                        // set a short delay to read from database
                                        try {
                                            TimeUnit.SECONDS.sleep(1);
                                        } catch (InterruptedException e) {
                                            //                                        e.printStackTrace();
                                        }

                                        // re-directs the user to login page for StoreOwners
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            };
                            newRef.addListenerForSingleValueEvent(newListener);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // TODO: add a error message here
                    }
                };
                // call the event listener method with the database reference
                ref.addListenerForSingleValueEvent(listener);
            }
            dismissKeyboard(this);
        }
    }

    // hides the keyboard on call
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}