package com.ifixhubke.kibu_olx.ui.fragments.terms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentTermsConditionBinding;

public class TermsConditionFragment extends Fragment {

    private FragmentTermsConditionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTermsConditionBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.finished.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_termsConditionFragment_to_registerFragment);
        });

        return view;
    }
}