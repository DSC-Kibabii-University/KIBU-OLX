package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.User;
import com.ifixhubke.kibu_olx.databinding.FragmentRegisterBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import timber.log.Timber;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.signupTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment));

        binding.registerButton.setOnClickListener(v -> {

            String mail = binding.enterEmail.getEditText().getText().toString().trim();
            String pass = binding.enterPassword.getEditText().getText().toString().trim();
            String first_Name = binding.firstName.getEditText().getText().toString().trim();
            String last_Name = binding.lastName.getEditText().getText().toString().trim();
            String phone_Number = binding.phoneNumber.getEditText().getText().toString().trim();
            Boolean agree_with_rules = binding.materialCheckBox.callOnClick();

            if (TextUtils.isEmpty(binding.enterEmail.getEditText().getText().toString())) {
                binding.enterEmail.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.enterPassword.getEditText().getText().toString())) {
                binding.enterPassword.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.firstName.getEditText().getText().toString())) {
                binding.firstName.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.lastName.getEditText().getText().toString())) {
                binding.lastName.setError("Field can't be empty!");
                return;
            } else if (TextUtils.isEmpty(binding.phoneNumber.getEditText().getText().toString())) {
                binding.phoneNumber.setError("Field can't be empty!");
                return;
            } else if (binding.phoneNumber.getEditText().getText().length() < 10) {
                binding.phoneNumber.setError("Put correct Fields");
                return;
            } else if (binding.enterPassword.getEditText().getText().length() < 6) {
                binding.enterPassword.setError("Password should be 6 characters or more!");
            } else if (!isvalidemail(binding.enterEmail.getEditText().getText().toString())) {
                binding.enterEmail.setError("Invalid Email!");
            } else if (!binding.materialCheckBox.isChecked()) {
                binding.materialCheckBox.setError("please check here!");
                return;
            } else {
                binding.registerProgressBar.setVisibility(View.VISIBLE);
            }

            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    //sending verification email
                    FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(task.isSuccessful()){
                                Toast.makeText(requireContext(),"Check email for verification link",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(requireContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Timber.d("Not created");
                        }
                    });
                    //
                    userID = firebaseAuth.getUid();
                    saveUserDetails(mail, first_Name, last_Name, phone_Number);
                    Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    Timber.d("createUserWithEmailAndPassword: Success");
                    binding.registerProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (!(CheckInternet.isConnected(requireContext()))) {
                        Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        Timber.d("No Internet");
                        binding.registerProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        binding.registerProgressBar.setVisibility(View.INVISIBLE);
                        Timber.d("createUserWithEmailAndPassword: Failed");
                    }
                }
            });
        });

        return view;
    }

    private boolean isvalidemail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void saveUserDetails(String mail, String firstName, String lastName, String phoneNumber) {
        User user = new User(mail, firstName, lastName, phoneNumber);
        databaseReference.child(userID).setValue(user);
    }
}