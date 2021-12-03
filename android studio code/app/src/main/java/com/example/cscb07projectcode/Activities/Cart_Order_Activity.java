package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cscb07projectcode.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cscb07projectcode.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cart_Order_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order);
        Intent intent = getIntent();
        String[] x = getIntent().getStringArrayExtra("VALUESSSSS"); // RETRIVE AN ARRAY OF ITEMS AS STRINGS AND WE GOTTA MODIFY THAT INTO AN ARRAY LIST FOR RECYLER VIEW
        // ADAPTER
        // PROBABLY ADD A FUNCTION FOR It

        TextView  t= (TextView)findViewById(R.id.textView26);
      t.setText(x[2].toString());

        //ArrayList<Item> myList = getIntent().getParcelableExtra("Contact_list");
        //t.setText(myList.get(0).getName());



       // Intent intent = getIntent();
       // String name = intent.getStringExtra("first");
      // Log.i("Taggg",myList.get(0).getName());
    }
}