package com.ifixhubke.kibu_olx.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentSplashBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import timber.log.Timber;

public class SplashFragment extends Fragment {
    FragmentSplashBinding binding;
    FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (!(CheckInternet.isConnected(requireContext()))) {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            Timber.d("No Internet");
        }

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mFirebaseUser != null && onBoardingFinished()) {
                    Timber.d("current user exist");
                    Toast.makeText(requireContext(), "WELCOME TO HOMESCREEN", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment2);
                } else if (onBoardingFinished()) {
                    Timber.d("Log in please");
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_viewPagerFragment);
                }
            }
        }, 3000);
    }

    private boolean onBoardingFinished() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Finished", false);
    }
}