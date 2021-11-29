package com.example.cscb07projectcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder> {

    private ArrayList<Item> itemsList;

    // each adapter requires an array list of stores on instantiation
    public ProductRecyclerAdapter(ArrayList<Item> itemsList){
        this.itemsList = itemsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView itemnameView;
        private TextView itemdescriptionView;

        public MyViewHolder(final View view){
            // pass the view to superclass
            super(view);
            // locate and store the view for store name and description
            itemnameView = view.findViewById(R.id.textView3);
            itemdescriptionView = view.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public ProductRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the view with data
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_container, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerAdapter.MyViewHolder holder, int position) {
        // this method changes text in the views of the container

        String itemName = itemsList.get(position).getName();
        String itemDescription = itemsList.get(position).getDescription();

        holder.itemnameView.setText(itemName);
        holder.itemdescriptionView.setText(itemDescription);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
