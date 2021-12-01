package com.example.cscb07projectcode.Activities.CartStuff;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.databinding.StoreRowForOrderingBinding;

public class StoreListAdapter extends ListAdapter<Item,StoreListAdapter.StoreViewHolder> {

    protected StoreListAdapter() {
        super(Item.itemItemCallback);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StoreRowForOrderingBinding storeRowForOrderingBinding = StoreRowForOrderingBinding.inflate(layoutInflater,parent,false);
        return new StoreViewHolder(storeRowForOrderingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
    Item item = getItem(position);
    holder.storeRowForOrderingBinding.setItem(item);
    }

    class StoreViewHolder extends RecyclerView.ViewHolder {
        StoreRowForOrderingBinding storeRowForOrderingBinding;

        public StoreViewHolder(StoreRowForOrderingBinding view)
        {
            super(view.getRoot());
            this.storeRowForOrderingBinding =view;
        }
    }


    public interface storeInterace {
        void addItem(Item item);
        void onItemClick(Item item);

    }
}
