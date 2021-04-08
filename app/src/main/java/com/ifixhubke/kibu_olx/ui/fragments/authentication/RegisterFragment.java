package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.User;
import com.ifixhubke.kibu_olx.databinding.FragmentRegisterBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

import java.util.Objects;

import timber.log.Timber;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userID;
    String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String validPassword = "^" + //# start-of-string
            "(?=.*[0-9])" +      //# a digit must occur at least once
            "(?=.*[a-z])" +      //# a lower case letter must occur at least once
            "(?=.*[A-Z])" +      //# an upper case letter must occur at least once
            "(?=\\S+$)" +         //# no whitespace allowed in the entire string
            ".{8,}" +          //# anything, at least eight places though
            "$";                 //# end-of-string;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.signupTextView.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment));

        binding.termsCondition.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_termsConditionFragment);
        });

        binding.registerButton.setOnClickListener(v -> {
            String mail = Objects.requireNonNull(binding.enterEmail.getEditText()).getText().toString().trim();
            String pass = Objects.requireNonNull(binding.enterPassword.getEditText()).getText().toString().trim();
            String first_Name = Objects.requireNonNull(binding.firstName.getEditText()).getText().toString().trim();
            String last_Name = Objects.requireNonNull(binding.lastName.getEditText()).getText().toString().trim();
            Boolean agree_with_rules = binding.materialCheckBox.callOnClick();


            if (binding.enterEmail.getEditText().getText().toString().isEmpty()) {
                binding.enterEmail.setError("This field can't be empty!");
                return;
            } else if (!binding.enterEmail.getEditText().getText().toString().matches(validEmail)) {
                binding.enterEmail.setError("Invalid email!");
                return;
            } else if (binding.enterPassword.getEditText().getText().toString().isEmpty()) {
                binding.enterPassword.setError("This field can't be empty!");
                return;
            } else if (!(binding.enterPassword.getEditText().getText().toString()).matches(validPassword)) {
                binding.enterPassword.setError("Password is too weak.!"
                        + "\n Must contain" +
                        "\nat least 8 characters." + "\nAt least one digit"
                        + "\nAt least one lowercase letter and one uppercase letter");
                return;
            } else if (binding.firstName.getEditText().getText().toString().isEmpty()) {
                binding.firstName.setError("This field can't be empty!");
                return;
            } else if (binding.firstName.getEditText().getText().toString().length() >= 15) {
                binding.firstName.setError("Name is too long!");
                return;
            } else if (binding.lastName.getEditText().getText().toString().isEmpty()) {
                binding.lastName.setError("This field can't be empty!");
                return;
            } else if (binding.lastName.getEditText().getText().toString().length() >= 15) {
                binding.lastName.setError("Name is too long!");
                return;
            } else if (Objects.requireNonNull(binding.phoneNumber.getText()).toString().isEmpty()) {
                binding.phoneNumber.setError("This field can't be empty!");
                return;
            } else {
                binding.registerProgressBar.setVisibility(View.VISIBLE);
            }
            binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumber);
            String phone_Number = binding.countryCodePicker.getFullNumberWithPlus();
            Timber.d("%s", phone_Number);

            firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(requireActivity(), task -> {
                if (task.isSuccessful()) {
                    //sending verification email
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    firebaseUser.sendEmailVerification().addOnSuccessListener(aVoid -> {
                        if (task.isSuccessful()) {
                            userID = firebaseAuth.getUid();
                            saveUserDetails(mail, first_Name, last_Name, phone_Number);
                            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
                            Toast.makeText(requireContext(), "We have sent an email verification link to " + mail, Toast.LENGTH_LONG).show();
                            Timber.d("createUserWithEmailAndPassword: Success");
                            binding.registerProgressBar.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(e -> Timber.d("Not created"));

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

    public void saveUserDetails(String mail, String firstName, String lastName, String phoneNumber) {
        User user = new User(mail, firstName, lastName, phoneNumber);
        databaseReference.child(userID).setValue(user);
    }
}