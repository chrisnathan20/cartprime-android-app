package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07projectcode.AdapterForCustomerStoreView;
import com.example.cscb07projectcode.Cart_Item_Adapter;
import com.example.cscb07projectcode.Order;
import com.example.cscb07projectcode.OrderMetaData;
import com.example.cscb07projectcode.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cscb07projectcode.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart_Order_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Order order_place; // gotta init it and this is the order we will send via this form
   Cart_Item_Adapter myAdapter;
    ArrayList<Item> list;
Button SendOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_order);

        list = new ArrayList<>();
        Intent intent = getIntent();
        String[] x = getIntent().getStringArrayExtra("VALUESSSSS"); // RETRIVE AN ARRAY OF ITEMS AS STRINGS AND WE GOTTA MODIFY THAT INTO AN ARRAY LIST FOR RECYLER VIEW
        // ADAPTER
        // PROBABLY ADD A FUNCTION FOR It
        TextView order_placed = (TextView) findViewById(R.id.textView26);
       // order_placed.setEnabled(false);



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
        DatabaseReference ref ;


        recyclerView.setAdapter(myAdapter);

        double newTotal = Calculate_total(list);

        TextView total_ = (TextView) findViewById(R.id.tv_total);
        total_.setText("$ "+String.format("%.2f",newTotal)); // TOTAL OF THE ORDER IN 2 DECIMAL PLACES

        //ArrayList<Item> myList = getIntent().getParcelableExtra("Contact_list");
        //t.setText(myList.get(0).getName());


// RETRIEVING THE CUSTOMER USERNAME
        SharedPreferences pref = getSharedPreferences("credentialsCustomer", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        // RETRIEVING THE STORE NAME
        SharedPreferences pref2 = getSharedPreferences("credentials_store_name",Context.MODE_PRIVATE);
        String store_name = pref2.getString("store_name","");

        order_place = new Order();
        OrderMetaData Order_info = new OrderMetaData();
         ref = FirebaseDatabase.getInstance().getReference("orders");
        SendOrder = (Button)findViewById(R.id.btn_placeorder);
        //DatabaseReference orderRef = ref.child("list_of_orders");

        SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase  = FirebaseDatabase.getInstance().getReference();
                Map<String, OrderMetaData> new_order = new HashMap<>();
               order_place.generateOrderId();
                String id = String.valueOf(order_place.getOrderId());


                new_order.put(id,new OrderMetaData(order_place.getOrderId(), "Incomplete",username,store_name));
                DatabaseReference new_child = ref.child("list_of_orders");
                //mDatabase.child("orders").child("list_of_orders").setValue(new_order);
               // new_child.setValue(new_order);
                //DatabaseReference child2 = ref.child("list_of_orders").child(id).child("itemList");
                Map<String, Item> new_list = new HashMap<>();

                for(Item i:list)
                {
                    new_list.put(i.getName(),i);
                }

                mDatabase.child("orders").child("list_of_orders").child(id).setValue(new OrderMetaData(order_place.getOrderId(), "Incomplete",username,store_name));


                mDatabase.child("orders").child("list_of_orders").child(id).child("itemsList").setValue(new_list);
             //  child2.setValue(new_list);
                   order_placed.setText("Order Placed Successfully");









            }
        });



       // Intent intent = getIntent();
       // String name = intent.getStringExtra("first");
     // Log.i("Taggg",myList.get(0).getName());
    }

    public double Calculate_total(ArrayList<Item>ls) // CALCULATES THE TOTAL OF THE ORDER
    {
        double Total =0;
        for(Item i: ls)
        {
            Total += i.getPrice()*i.getQuantity();

        }
        return Total;
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
                //to_Add.setQuantity(1);
               if(x.contains(to_Add))
                {
                    x.get(x.indexOf(to_Add)).setQuantity(x.get(x.indexOf(to_Add)).getQuantity() + 1);
                }
                else {
                    to_Add.setQuantity(1);
                    x.add(to_Add);
                }

            }


    }
}