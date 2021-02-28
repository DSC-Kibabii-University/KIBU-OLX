package com.ifixhubke.kibu_olx.ui.fragments.sell;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Sell1;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenOneBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentSellTwoBinding;

public class SellFragmentTwo extends Fragment {
    FragmentSellTwoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSellTwoBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Sell1 sell = SellFragmentTwoArgs.fromBundle(getArguments()).getArgsFromSell1();

        Toast.makeText(requireContext(),"Argument "+sell.getCategory(),Toast.LENGTH_SHORT).show();
        Toast.makeText(requireContext(),"Argument "+sell.getLocation(),Toast.LENGTH_SHORT).show();

        binding.previous2.setOnClickListener(v -> NavHostFragment.findNavController(SellFragmentTwo.this).navigate(R.id.sellFragmentOne));


        binding.postAdButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.productNameEditText.getText().toString())){
                binding.productNameEditText.setError("Please enter item name");
                return;
            }
            if (TextUtils.isEmpty(binding.conditionEditTtext.getText().toString())){
                binding.conditionEditTtext.setError("Please enter item condition");
                return;
            }
            if (TextUtils.isEmpty(binding.priceEditText.getText().toString())){
                binding.priceEditText.setError("Enter price");
                return;
            }
            if (TextUtils.isEmpty(binding.phoneNumberEditText.getText().toString())) {
                binding.phoneNumberEditText.setError("Enter phone number");
                return;
            }
            if (TextUtils.isEmpty(binding.itemDescription.getText().toString())) {
                binding.itemDescription.setError("Please enter your name");
                return;
            }
        });

        return view;

    }
}
