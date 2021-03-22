package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;


import android.renderscript.ScriptGroup;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

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
        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordResetFragment resetFragment = new PasswordResetFragment();
                resetFragment.show(getFragmentManager(),"dialog_password_reset");
            }
        });

        binding.dontHaveAccount.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment));

        binding.signinButton.setOnClickListener(v -> {
            String mail = binding.emailEditText.getEditText().getText().toString().trim();
            String password = binding.passwordEditText.getEditText().getText().toString().trim();

            if (TextUtils.isEmpty(binding.emailEditText.getEditText().getText().toString())) {
                binding.emailEditText.setError("Field can't be empty!!");
                return;
            } else if (TextUtils.isEmpty(binding.passwordEditText.getEditText().getText().toString())) {
                binding.passwordEditText.setError("Field can't be empty!!");
                return;
            } else if (!(CheckInternet.isConnected(requireContext()))) {
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                Timber.d("No Internet");
            } else {
                binding.loginProgressBar.setVisibility(View.VISIBLE);
                binding.emailEditText.setEnabled(false);
                binding.passwordEditText.setEnabled(false);
                binding.signinButton.setEnabled(false);
            }
            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment2);
                            Timber.d("signInWithEmailAndPassword: success");
                            binding.loginProgressBar.setVisibility(View.INVISIBLE);
                        } else {
                            binding.loginProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Verify you email first", Toast.LENGTH_SHORT).show();
                            binding.emailEditText.getEditText().setText("");
                            binding.passwordEditText.getEditText().setText("");
                        }
                    } else {
                        Toast.makeText(getContext(), "Something Went Wrong.\n please check your details and try again", Toast.LENGTH_SHORT).show();
                        Timber.d("signInWithEmailAndPassword: Failed");
                        binding.loginProgressBar.setVisibility(View.INVISIBLE);
                        binding.emailEditText.setEnabled(true);
                        binding.passwordEditText.setEnabled(true);
                        binding.signinButton.setEnabled(true);
                    }
                }
            });

    });

        return view;
}
}