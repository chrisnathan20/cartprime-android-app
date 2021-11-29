package com.example.cscb07projectcode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.MyViewHolder> {

    private ArrayList<Store> storesList;

    // each adapter requires an array list of stores on instantiation
    public StoreRecyclerAdapter(ArrayList<Store> storesList){
        this.storesList = storesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView storenameView;
        private TextView storedescriptionView;

        public MyViewHolder(final View view){
            // pass the view to superclass
            super(view);
            // locate and store the view for store name and description
            storenameView = view.findViewById(R.id.textView3);
            storedescriptionView = view.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public StoreRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the view with data
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_container, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRecyclerAdapter.MyViewHolder holder, int position) {
        // this method changes text in the views of the container

        String storeName = storesList.get(position).getName();
        String storeDescription = storesList.get(position).getDescription();

        holder.storenameView.setText(storeName);
        holder.storedescriptionView.setText(storeDescription);
    }

    @Override
    public int getItemCount() {
        return storesList.size();
    }
}
