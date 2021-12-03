package com.example.cscb07projectcode;

import androidx.recyclerview.widget.RecyclerView;

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

public class Cart_Item_Adapter extends RecyclerView.Adapter<Cart_Item_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Item> list;
    private Cart_Item_Adapter.onItemClickListener mListner;
    // INTERFACE FOR ONCLICK
    public interface onItemClickListener{
        void onItemClick(int position);

    }
    public void setOnItemClickListener(Cart_Item_Adapter.onItemClickListener listener)
    {
        mListner = listener;
    }

    // Constructor
    public Cart_Item_Adapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public Cart_Item_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item_indiv,parent, false);

        return new Cart_Item_Adapter.MyViewHolder(v,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Item_Adapter.MyViewHolder holder, int position) {
        Item item_ = list.get(position);
        holder.Name.setText(item_.getName());

        holder.Price.setText("$ "+ item_.getPrice()+  "per " + item_.getUnit());
        holder.Price.setTextColor(Color.DKGRAY);
        holder.Quantity.setText(String.valueOf(item_.getQuantity()) + " " +item_.getUnit());
        holder.Total.setText(String.valueOf("Total: $" + ((double) item_.getQuantity()* item_.getPrice())));



//       holder.Quantity.setText(item_.getQuantity() + " " + item_.getUnit());
  //     holder.Total.setText(" $ " +item_.getQuantity() * item_.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // STATIC CLASS FOR INDIVIDUAL ITEMS ON THE RECYLERVIEW IN THIS CASE EACH PRODUCT
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;

        TextView Price;
        TextView Quantity;
        TextView Total;


        // Constructors
        public MyViewHolder(@NonNull View itemView,Cart_Item_Adapter.onItemClickListener listener) {
            super(itemView);
            Name = itemView.findViewById(R.id.tv_name);
            Price = itemView.findViewById(R.id.tv_rate);
            Quantity = itemView.findViewById(R.id.tv_size);
            Total = itemView.findViewById(R.id.tv_total);




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







        }
    }

}
