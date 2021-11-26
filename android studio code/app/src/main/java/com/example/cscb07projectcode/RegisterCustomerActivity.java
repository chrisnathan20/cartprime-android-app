package com.example.cscb07projectcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_store_owner);
    }

    // called when user clicks "Create new account" button
    public void CreateNewAccount(View view) {

        // initialize the empty message notification
        TextView notifyMessage = (TextView) findViewById(R.id.textView);
        notifyMessage.setText("");

        // reset field_status=true on each button click
        boolean field_status = true;

        // set navigation for user to login page when account is successfully created
        Intent intent = new Intent(this, LoginCustomerActivity.class);

        // store user input for first name
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String firstname_field = editText.getText().toString();

        // store user input for last name
        editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        String lastname_field = editText.getText().toString();

        // store user input for email (e.g. username)
        editText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String email_field = editText.getText().toString();

        // store user input for password
        editText = (EditText) findViewById(R.id.editTextTextPassword);
        String password_field = editText.getText().toString();

        // checks if any input fields (e.g. first name, last name, email, password) are empty
        if(firstname_field.isEmpty()){
            field_status=false;
        }
        if(lastname_field.isEmpty()){
            field_status=false;
        }
        if(email_field.isEmpty()){
            field_status=false;
        }
        if(password_field.isEmpty()){
            field_status=false;
        }

        // inform user that at least one input box is empty and needs to be filled
        if(!field_status){
            notifyMessage.setText("Please fill in all input fields.");
        }
        // proceed to next validation check: validates if email input is available
        else{
            // getReference(): selects data under key:"Customers" who has a child named (email_field)
            // convention for storing data: Customers -> (username) -> (firstname,lastname,username,password)
            // note: email and username are synonyms
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customers").child(email_field);
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // if username is already taken
                    if(snapshot.exists()){
                        notifyMessage.setText("An account is already registered with that email.");
                    }
                    // if username is not taken, then append new user into database
                    else {
                        // create a new user instance with user-specified data
                        Customer customer = new Customer(email_field, firstname_field, lastname_field, password_field);
                        // append new user under Customers as a child with key: email
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        ref.child("Customers").child(email_field).setValue(customer);
                        // re-directs the user to login page for Customers
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // TODO: add a error message here
                }
            };
            // call the event listener method with the database reference
            ref.addValueEventListener(listener);
        }
    }
}