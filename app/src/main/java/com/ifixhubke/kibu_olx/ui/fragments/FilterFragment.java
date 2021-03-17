package com.ifixhubke.kibu_olx.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentFilterBinding;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

import timber.log.Timber;

public class FilterFragment extends BottomSheetDialogFragment {

    FragmentFilterBinding binding;
    String category, condition;
    double minPrice=0, maxPrice=10000;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Timber.d("Check on Slider default values: "+minPrice+" to: "+maxPrice);

        binding.categoryChipGroup.setOnCheckedChangeListener((group, checkedId) -> category = getSelectedChipText(group, checkedId));

        binding.conditionChipGroup.setOnCheckedChangeListener((group, checkedId) -> condition = getSelectedChipText(group, checkedId));

        binding.textViewDone.setOnClickListener(v -> {
            Item item = new Item(category, condition, minPrice, maxPrice);
            NavDirections action = FilterFragmentDirections.actionFilterFragmentToFilteredItemsFragment(item);
            NavHostFragment.findNavController(this).navigate(action);
        });

        binding.priceSlider.setLabelFormatter(value -> {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);
            format.setCurrency(Currency.getInstance("KSH"));
            return format.format(Double.parseDouble(Float.toString(value)));
        });


        binding.priceSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                Timber.d("OnChangeListener");
                minPrice = Double.parseDouble(Float.toString(slider.getValues().get(0)));
                maxPrice = Double.parseDouble(Float.toString(slider.getValues().get(1)));
                Timber.d("OnChangeListener From"+ minPrice);
                Timber.d("OnChangeListener T0"+ maxPrice);
            }
        });

        return view;
    }

    private String getSelectedChipText(ChipGroup chipGroup, int id) {
        Chip mySelection = chipGroup.findViewById(id);
        return mySelection.getText().toString();
    }
}