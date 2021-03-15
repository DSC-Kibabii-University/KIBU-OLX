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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentSplashBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class SplashFragment extends Fragment {
    FragmentSplashBinding binding;
    FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mFirebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return binding.content;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (!(CheckInternet.isConnected(requireContext()))) {

                Snackbar snackbar = Snackbar.make(getView(), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onStart();
                    }
                });
                snackbar.show();
                Timber.d("No Internet");
            }   else {
                FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mFirebaseUser != null && onBoardingFinished()) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment2);
                } else if (onBoardingFinished()) {
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