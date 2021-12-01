package com.example.cscb07projectcode.Activities.CartStuff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.cscb07projectcode.Item;

public class StoreListAdapter extends ListAdapter<Item,StoreListAdapter.StoreViewHolder> {

    protected StoreListAdapter(@NonNull DiffUtil.ItemCallback<Item> diffCallback) {
        super(Item.itemItemCallback);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {

    }

    class StoreViewHolder extends RecyclerView.ViewHolder {

        public StoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
