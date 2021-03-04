package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentLoginBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import timber.log.Timber;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();

        binding.dontHaveAccount.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));

        binding.signinButton.setOnClickListener(v -> {
            String mail = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(binding.emailEditText.getText().toString())) {
                binding.emailEditText.setError("Field can't be empty!!");
                return;
            } else if (TextUtils.isEmpty(binding.passwordEditText.getText().toString())) {
                binding.passwordEditText.setError("Field can't be empty!!");
                return;
            } else if (!(CheckInternet.isConnected(requireContext()))) {
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                Timber.d("No Internet");
            } else {
                binding.loginProgressBar.setVisibility(View.VISIBLE);
            }

            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment2);
                    Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
                    Timber.d("signInWithEmailAndPassword: success");
                    binding.loginProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getContext(), "Something Went Wrong.\n please check your details and try again", Toast.LENGTH_SHORT).show();
                    Timber.d("signInWithEmailAndPassword: Failed");
                }
            });
        });

        return view;
    }
}