package com.ifixhubke.kibu_olx.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentFilterBinding;

public class FilterFragment extends BottomSheetDialogFragment {

    FragmentFilterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentFilterBinding.inflate(inflater,container,false);

       View view = binding.getRoot();
        return view;
    }
}