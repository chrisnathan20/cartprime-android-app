package com.example.cscb07projectcode;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreOwnerModel implements LoginContract.Model {

    //to work around the void return of onDataChange
    public static boolean user_result = false;
    public static boolean pw_result = false;

    public StoreOwnerModel() {
    }

    public boolean userExists(String username) {

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user = root.child("users").child("storeowners").child(username);
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                user_result = snapshot.exists();
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

        //uses static to work around void return def of onDataChange
        return StoreOwnerModel.user_result;
    }

    public boolean correctPassword(String username, String password) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child("storeowners");

        ref.child(username).child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //true if password in database is equal to password in argument
                //false otherwise
                if(!(snapshot.getValue().equals(null))) {
                    StoreOwnerModel.pw_result = (snapshot.getValue().equals(password));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
            }
        });

        //uses static to work around void return def of onDataChange
        return StoreOwnerModel.pw_result;

    }
}