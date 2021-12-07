package com.example.cscb07projectcode;

import android.content.Context;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.util.ArrayList;

public class AdapterForCustomerStoreView extends RecyclerView.Adapter<AdapterForCustomerStoreView.MyViewHolder> {
    Context context;
    ArrayList<Item> list;
    private onItemClickListener mListner;
    // interface for getting clicked item
    public interface onItemClickListener{
        void onItemClick(int position);
        void onAddtoCart(int position);
        void onDelete(int position);
        void onRefresh(int position);


    }
    // call this is our activity
    public void setOnItemClickListener(onItemClickListener listener)
    {
        mListner = listener;
    }

    // Constructor
    public AdapterForCustomerStoreView(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }
    // STATIC CLASS FOR INDIVIDUAL ITEMS ON THE RECYLERVIEW IN THIS CASE EACH PRODUCT
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

        TextView Description;
        TextView Unit;
        TextView Price;
        TextView Available;
        Button add_to_Cart;
        Button refresh;
        Button remove_from_cart;

       TextView howManyInCart;

        // Constructors
        public MyViewHolder(@NonNull View itemView,onItemClickListener listener) {
            super(itemView);
            Name = itemView.findViewById(R.id.productName);
            refresh = itemView.findViewById(R.id.refresh_btn);
            Description = itemView.findViewById(R.id.productDescription);
            Price = itemView.findViewById(R.id.productPrice);
            Available = itemView.findViewById(R.id.productAvailable);
            Unit = itemView.findViewById(R.id.productUnit);
            add_to_Cart = itemView.findViewById(R.id.Add_to_Cart);
            remove_from_cart = itemView.findViewById(R.id.deleteCart);
            howManyInCart = itemView.findViewById(R.id.itemsInCart);
           // add_product = itemView.findViewById(R.id.add1);
           // remove_product = itemView.findViewById(R.id.delete1);

           // quantity = itemView.findViewById(R.id.quantity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getBindingAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });



            add_to_Cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getBindingAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onAddtoCart(position);
                        }
                    }
                }
            });

            remove_from_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getBindingAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onDelete(position);
                        }
                    }
                }
            });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                {
                    int position = getBindingAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        listener.onRefresh(position);
                    }
                }
            }
        });


        }
    }


    @NonNull
    @Override
    public AdapterForCustomerStoreView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customer_products_recyler_indiv,parent, false);

        return new MyViewHolder(v,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForCustomerStoreView.MyViewHolder holder, int position) {
    Item item_ = list.get(position);
    holder.Name.setText(item_.getName());

    holder.Price.setText("$ "+ item_.getPrice());
        holder.Price.setTextColor(Color.DKGRAY);
        holder.add_to_Cart.setBackgroundColor(Color.parseColor("#32cd32"));
        holder.refresh.setBackgroundColor(Color.parseColor("#87cefa"));

        holder.remove_from_cart.setBackgroundColor(Color.RED);

    if(item_.getAvailable()){
        holder.Available.setText("Available");
    holder.Available.setTextColor(Color.parseColor("#32cd32"));

   // holder.howManyInCart.setText("0 in Cart");

        //holder.quantity.setTextSize;
    }
    else{holder.Available.setText("Out of Stock");
        holder.Available.setTextColor(Color.RED);
        //holder.add_to_Cart.setEnabled(false); // disables the button when no stock is there
        //holder.remove_from_cart.setEnabled(false);
        holder.add_to_Cart.setBackgroundColor(Color.parseColor("#cdc9c9"));
        holder.howManyInCart.setText("0 in cart");
        holder.remove_from_cart.setBackgroundColor(Color.parseColor("#cdc9c9"));

       // holder.quantity.setEnabled(false);
    }

    holder.Description.setText("Description:"+item_.getDescription());
    holder.Unit.setText("Price per " + item_.getUnit());


    }

    @Override
    public int getItemCount() {
        return list.size();
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
