package com.example.cscb07projectcode.Activities.CartStuff;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cscb07projectcode.Item;
import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.databinding.FragmentStore2Binding;

import java.util.ArrayList;


public class StoreFragment extends Fragment implements StoreListAdapter.storeInterace {

FragmentStore2Binding fragmentStore2Binding;

private  StoreListAdapter storeListAdapter;
private  StoreViewModel storeViewModel;

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

        storeViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);

        SharedPreferences pref = this.getActivity().getSharedPreferences("credentials_store_name", Context.MODE_PRIVATE);
        String username = pref.getString("store_name", ""); // retrieving the store name
        storeViewModel.setS(username);

        storeViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<Item>>()
        {
            @Override
            public void onChanged(ArrayList<Item> items) {
                storeListAdapter.submitList(items);
            }
        });

    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void onItemClick(Item item) {

    }
}