package com.example.cscb07projectcode.Activities.CartStuff;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.Store;

import java.util.ArrayList;

public class StoreViewModel extends ViewModel{
        String s; // this is the store name

        public StoreViewModel(String s)
        {super();
            this.s = s;
        }
        public StoreViewModel()
        {

        }
        public void setS(String s)
        {this.s = s;}



    // here we extract the data from the database for the fragment to display and it displays it via the use of
    // the store list adapter
    StoreRepo storeRepo = new StoreRepo(this.s); // this is in reference to store repository class StoreRepo which helps us extract data into an array list
    // that array list contains data from our firebase realtime database


    public LiveData<ArrayList<Item>> getItems()
    {
        return storeRepo.getItems();
    }
}
