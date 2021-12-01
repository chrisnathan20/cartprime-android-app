package com.example.cscb07projectcode.Activities.CartStuff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cscb07projectcode.R;
import com.example.cscb07projectcode.databinding.FragmentStore2Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreFragment extends Fragment {

FragmentStore2Binding fragmentStore2Binding;

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
}