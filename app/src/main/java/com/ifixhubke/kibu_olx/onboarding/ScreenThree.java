package com.ifixhubke.kibu_olx.onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenThreeBinding;

import org.jetbrains.annotations.NotNull;

public class ScreenThree extends Fragment {
    FragmentScreenThreeBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScreenThreeBinding.inflate(inflater, container, false);


        View view = binding.getRoot();

        binding.start.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_viewPagerFragment_to_registerFragment);
            onBoardingDone();
        });

        return view;
    }

    //To save the state of onboarding if it is done
    private void onBoardingDone() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Finished", true);
        editor.apply();
    }
}