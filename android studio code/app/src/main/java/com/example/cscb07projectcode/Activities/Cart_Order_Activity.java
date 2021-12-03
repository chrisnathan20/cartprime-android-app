package com.example.cscb07projectcode.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07projectcode.AdapterForCustomerStoreView;
import com.example.cscb07projectcode.Cart_Item_Adapter;
import com.example.cscb07projectcode.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cscb07projectcode.Item;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cart_Order_Activity extends AppCompatActivity {
    RecyclerView recyclerView;

   Cart_Item_Adapter myAdapter;
    ArrayList<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order);
        list = new ArrayList<>();
        Intent intent = getIntent();
        String[] x = getIntent().getStringArrayExtra("VALUESSSSS"); // RETRIVE AN ARRAY OF ITEMS AS STRINGS AND WE GOTTA MODIFY THAT INTO AN ARRAY LIST FOR RECYLER VIEW
        // ADAPTER
        // PROBABLY ADD A FUNCTION FOR It

        TextView  t= (TextView)findViewById(R.id.textView26);
      t.setText(x[2].toString());

     PopulateList(x);
      Log.i("IS THE ARRAYLIST EMPTY"," " + list.size());
   /**   for(Item i: list)
      {
          Log.i("ITEM", "   " + i.toString());

      } **/

   // SUCCESSFULLY ABLE TO POPULATE THE ARRAY LIST WITH PROPER QUANTITY
        recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter =  new Cart_Item_Adapter(this,list);


        recyclerView.setAdapter(myAdapter);

        //ArrayList<Item> myList = getIntent().getParcelableExtra("Contact_list");
        //t.setText(myList.get(0).getName());



       // Intent intent = getIntent();
       // String name = intent.getStringExtra("first");
     // Log.i("Taggg",myList.get(0).getName());
    }

    public void PopulateList(String[] str)
    {
            ArrayList<Item> x = list;
            for(String s: str)
            {
                String[]arr = s.split(";");
                int i =0;
            /**   for(String yyy:arr)
               {
                   Log.i("MMM", yyy + i );
                   i++;
               } **/
               Item to_Add  = new Item(arr[0],arr[1],Double.parseDouble(arr[2]), Integer.parseInt(arr[3]),arr[4]);
                to_Add.setQuantity(1);
               if(x.contains(to_Add))
                {
                    x.get(x.indexOf(to_Add)).setQuantity(x.get(x.indexOf(to_Add)).getQuantity() + 1);
                }
                else {
                    x.add(to_Add);
                }

            }


    }
}