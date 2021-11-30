package com.example.cscb07projectcode;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerModel implements LoginContract.Model {

    /*
    //to work around the void return of onDataChange
    public static boolean user_result = false;
    public static boolean pw_result = false;
     */

    public CustomerModel() {
    }

    public void userCheck(String username, String password, LoginContract.Presenter presenter) {

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user = root.child("users").child("customers").child(username);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    presenter.invalidLogin();
                }
                else{
                    passwordCheck(username, password, presenter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* For some reason, this does not work
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners").child(username);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // true if username exists, false if username does not exists
                StoreOwnerModel.user_result = snapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        */
    }

    public void passwordCheck(String username, String password, LoginContract.Presenter presenter) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("customers");

        ref.child(username).child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //true if password in database is equal to password in argument
                //false otherwise
                if(!password.equals(snapshot.getValue())){
                    presenter.invalidLogin();
                }
                else{
                    presenter.validLogin(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
            }
        });

    }
}
