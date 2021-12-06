package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07projectcode.AdapterForCustomerStoreView;
import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Customer_Store_View extends AppCompatActivity {

    RecyclerView recyclerView;

    AdapterForCustomerStoreView myAdapter;
    ArrayList<Item> list;
    ArrayList<Item> cartList;
    EditText quan; // for the quantity of a selected item to be added
    TextView isQuanAvliable; // tells whether the quanity added is available
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_view);
        recyclerView = findViewById(R.id.product_list_from_selected_store);
        list = new ArrayList<>();
        quan = findViewById(R.id.quantity); // quantity to be ordered
        isQuanAvliable = findViewById(R.id.MessageForQuantity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter =  new AdapterForCustomerStoreView(this,list);
        cartList = new ArrayList<>();


        // This for when items in the recyler view are clicked

// RETRIEVING THE CUSTOMER USERNAME
        SharedPreferences pref = getSharedPreferences("credentialsCustomer", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        // RETRIEVING THE CUSTOMER
        SharedPreferences pref2 = getSharedPreferences("credentials_store_name",Context.MODE_PRIVATE);
        String store_name = pref2.getString("store_name","");





            // VISIT CART STUFF DOWN THERE
        Button cart_button = (Button) findViewById(R.id.visitCart); // what happens when you click the visit cart button
        cart_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               openCart();
                                           }
                                       }

        );





        Intent intent = getIntent();
        String value= intent.getStringExtra("getData");// the store name here



        // leverages store name to find the corresponding store's products
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    Store store = child.getValue(Store.class);
                    if(value.equals(store.getName())){
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(store.getName()).child("products");
                        ValueEventListener newListener = new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product and append it into the arraylist
                                for(DataSnapshot newChild:snapshot.getChildren()) {
                                    Item item = newChild.getValue(Item.class);
                                    list.add(item);
                                }
                                recyclerView.setAdapter(myAdapter);
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
        myAdapter.setOnItemClickListener(new AdapterForCustomerStoreView.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                list.get(position); // GET THE ITEM AT THIS POSITION DONE

                Log.i("AAAAAA", list.get(position).getName());
            }

            @Override
            public void onAddtoCart(int position) {
                list.get(position); // GET THE ITEM AT THIS POSITION DONE

                // VALIDATION FOR QUANTITY ENTERED
                Log.i(" CAN I GET TO STRING",list.get(position).toString());
                EditText quantity = findViewById(R.id.quantity);
                TextView MessageForQuantity = findViewById(R.id.MessageForQuantity);

                if(quantity.getText() == null || Integer.parseInt(quantity.getText().toString()) < 0  )
                {
                    MessageForQuantity.setText("Please a valid quantity " );
                }

                else {
                    String quantity_ordewred_for_item = quantity.getText().toString();
                    int quant = Integer.parseInt(quantity_ordewred_for_item);
                    Log.i("IS THE QUANTITY READ", quantity_ordewred_for_item);

                    if (quant > list.get(position).getQuantity()) {
                        MessageForQuantity.setText("We only have " + list.get(position).getQuantity());


                    } else {
                        MessageForQuantity.setText(" ");
                        //Intent intent = new Intent(getApplicationContext(),Cart_Order_Activity.class);
                        Log.i("DOES IT ADD STUFFBEFORE", cartList.size()  + "  Prev");

                        cartList.add(list.get(position));
                        Item iii = list.get(position);
                        int tt= iii.getQuantity() -quant;
                        list.get(position).setQuantity(tt);
                        //list.get(position).setQuantity((list.get(position).getQuantity() - quant));

                        cartList.get(cartList.indexOf(list.get(position))).setQuantity(quant);
                        Item x = cartList.get(cartList.indexOf(list.get(position)));
                        Log.i("What is the quantity", " " + x.getQuantity() );
                        Log.i("How many left", " "+ list.get(position).getQuantity());
                        Log.i("How many ", " "+ cartList.get(position).getQuantity());
                        Log.i("DOES IT ADD STUFF ", cartList.size() + "  ");
                        //intent.putExtra("extra", list.get(position).toString());
                        //startActivity(intent);
                    }
                }

            }
        });


    }

public void openCart()
{
    for(Item i: cartList)
    {
        Log.i("TESt",i.getName()+ "\n\n"+i.getDescription());
    }
    if(cartList.isEmpty())
    {   Intent intent = new Intent(getApplicationContext(), Cart_Order_Activity.class);
        startActivity(intent);
    intent.putExtra("first","Empty");
    }

    Intent intent = new Intent(this,Cart_Order_Activity.class);
    String [] arr_ = new String[cartList.size()];
    int  i = 0;
    for(Item item_: cartList)
    {
                arr_[i] = item_.toString();
                i++;
    }

    intent.putExtra("VALUESSSSS",arr_);



    startActivity(intent);



    startActivity(intent);
}

}