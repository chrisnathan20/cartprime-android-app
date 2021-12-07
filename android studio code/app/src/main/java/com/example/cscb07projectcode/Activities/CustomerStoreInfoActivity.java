package com.example.cscb07projectcode.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomerStoreInfoActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AdapterForCustomerStoreView myAdapter;
    ArrayList<Item> list;
    ArrayList<Item> cartList;

    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_view);
        recyclerView = findViewById(R.id.product_list_from_selected_store);
        list = new ArrayList<>();
        //quan = findViewById(R.id.quantity); // quantity to be ordered

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new AdapterForCustomerStoreView(this, list);
        cartList = new ArrayList<>();
        add = findViewById(R.id.Add_to_Cart);
        String[] strItemsList = new String[0];

        Intent intent = getIntent();
        strItemsList = intent.getStringArrayExtra("strItemsList");
        // if there is cart data being passed from the cart page via back button
        if (strItemsList.length != 0) {
            cartList = PopulateList(strItemsList);
        }

        // This for when items in the recyler view are clicked

// RETRIEVING THE CUSTOMER USERNAME
        SharedPreferences pref = getSharedPreferences("credentialsCustomer", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        // RETRIEVING THE CUSTOMER
        SharedPreferences pref2 = getSharedPreferences("credentials_store_name", Context.MODE_PRIVATE);
        String store_name = pref2.getString("store_name", "");

        TextView messages = (TextView) findViewById(R.id.storenameprod);

        messages.setText(store_name + " Products");


        // VISIT CART STUFF DOWN THERE
        Button cart_button = (Button) findViewById(R.id.visitCart); // what happens when you click the visit cart button
        cart_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               openCart();
                                           }
                                       }

        );


        // leverages store name to find the corresponding store's products
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stores").child("list_of_stores");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // loops through user (of type store owner) until it matches a username
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Store store = child.getValue(Store.class);
                    if (store_name.equals(store.getName())) {
                        // create a database reference to access list of products
                        DatabaseReference newRef = ref.child(store.getName()).child("products");
                        ValueEventListener newListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // iterate through each product and append it into the arraylist
                                for (DataSnapshot newChild : snapshot.getChildren()) {
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
            }

            @Override
            public void onAddtoCart(int position) {


                TextView how_many_in_Cart = findViewById(R.id.itemsInCart);
                list.get(position); // GET THE ITEM AT THIS POSITION DONE
                int how_many_can_be_added = list.get(position).getQuantity() - howManyProductsLeft(list.get(position), 0); // calcuates  how many in inventory - how many are already in the cart
                // VALIDATION FOR QUANTITY ENTERED
                if (how_many_can_be_added <= 0) {
                    findViewById(R.id.Add_to_Cart).setBackgroundColor(Color.DKGRAY);
                    displayAlertStock(Integer.toString(howManyProductsLeft(list.get(position), 0)), list.get(position).getName());
                }

                else {
                    cartList.add(list.get(position)); // if we have enough we will add them here
                    int occurences = occurences_of_item_in_list(cartList,list.get(position));
                    how_many_in_Cart.setText(occurences + " in cart");

                }
            }

            @Override
            public void onDelete(int position) {

                TextView how_many_in_Cart = findViewById(R.id.itemsInCart);
                int occurences = occurences_of_item_in_list(cartList,list.get(position));
                if(occurences <= 0 )
                {
                    displayAlertDelete(list.get(position).getName());


                }
                else
                {
                    remove_one_from_arrayList(cartList,list.get(position));
                    int occurences2 = occurences_of_item_in_list(cartList,list.get(position));
                    how_many_in_Cart.setText(occurences2 + " in cart");
                }

            }

            @Override
            public void onRefresh(int position) {
                TextView how_many_in_Cart = findViewById(R.id.itemsInCart);
                int occurences2 = occurences_of_item_in_list(cartList,list.get(position));
                how_many_in_Cart.setText(occurences2 + " in cart");

            }


        });



    }
    public void displayAlertDelete( String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerStoreInfoActivity.this);

        builder.setTitle(product_name + " not present in cart");
        builder.setMessage(product_name +  " is not in your cart, we cannot delete it ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void displayAlertStock(String product_left, String product_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerStoreInfoActivity.this);

        builder.setTitle("Insufficient " + product_name + " stock");
        builder.setMessage("We only have " + product_left + " " + product_name + "(s) in stock");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    // open cart button
    public void openCart() {
        for (Item i : cartList) {
        }
        if (cartList.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), Cart_Order_Activity.class);
            intent.putExtra("strItemsList", new String[0]);
            startActivity(intent);
        }
        Intent intent = new Intent(this, Cart_Order_Activity.class);
        String[] arr_ = new String[cartList.size()];
        int i = 0;
        for (Item item_ : cartList) {
            arr_[i] = item_.toString();
            i++;
        }
        intent.putExtra("strItemsList", arr_);
        startActivity(intent);
    }


    public int howManyProductsLeft(Item i, int init) {
        int count = init;
        for (Item x : cartList) {
            if (x.equals(i)) {
                count++;
            }
        }
        return count;
    }

    public void remove_one_from_arrayList(ArrayList<Item> itemlist, Item item) {
        int location = itemlist.lastIndexOf(item); // finds the last occurence of this item
        itemlist.remove(location);
    }

    public ArrayList<Item> PopulateList(String[] str) {
        ArrayList<Item> x = new ArrayList<Item>();
        for (String s : str) {
            String[] arr = s.split(";");
            int i = 0;
            Item to_Add = new Item(arr[0], arr[1], Double.parseDouble(arr[2]), Integer.parseInt(arr[3]), arr[4]);
            x.add(to_Add);
        }
        return x;
    }

    public int occurences_of_item_in_list(ArrayList<Item>itemList,Item item)
    {
        int count  = 0;
        for(Item i: itemList)
        {
            if(i.equals(item))
            {count++;}

        }
        return count;
    }
}