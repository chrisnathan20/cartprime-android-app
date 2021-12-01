package com.example.cscb07projectcode.Activities.CartStuff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.databinding.FragmentStore2Binding;


public class StoreFragment extends Fragment implements StoreListAdapter.storeInterace {

FragmentStore2Binding fragmentStore2Binding;
private  StoreListAdapter storeListAdapter;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStore2Binding = FragmentStore2Binding.inflate(inflater,container,false);
        return fragmentStore2Binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        storeListAdapter = new StoreListAdapter();
        fragmentStore2Binding.storeRecyclerView.setAdapter(storeListAdapter);

    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void onItemClick(Item item) {

    }
}