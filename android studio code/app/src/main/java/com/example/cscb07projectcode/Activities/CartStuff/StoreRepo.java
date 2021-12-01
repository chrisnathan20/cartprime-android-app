package com.example.cscb07projectcode.Activities.CartStuff;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class StoreRepo {
        String store;
        public StoreRepo(String s)
        {
            this.store = s;
        }
    // load data from firebase onto here
    // this is our repository

    public MutableLiveData<ArrayList<Item>> mutableItemList;
    public LiveData<ArrayList<Item>> getItems()
    {
        if(mutableItemList == null)
        {
            mutableItemList = new MutableLiveData<>();
            loadItems();
        }
        return mutableItemList;
    }





    private void  loadItems() // for encapsulation this is private to prevent unauthorised access to store data
    {
        ArrayList<Item> itemArrayList = new ArrayList<Item>();

        //SharedPreferences pref = getSharedPreferences("credentials_store_name", Context.MODE_PRIVATE);
        //String username = pref.getString("store_name", ""); // retrieving the store name
        // We will use the databse to populate this list now

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    Store store = child.getValue(Store.class);
                    if(store.equals(store.getName())){
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(store.getName()).child("products");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product and append it into the arraylist
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    Item item = newChild.getValue(Item.class);
                                    itemArrayList.add(item);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        newRef.addValueEventListener(newListener);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addValueEventListener(listener);





        mutableItemList.setValue(itemArrayList);
    }
}

