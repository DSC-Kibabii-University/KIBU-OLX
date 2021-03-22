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
    String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String validPassword = "^" + //# start-of-string
            "(?=.*[0-9])" +      //# a digit must occur at least once
            "(?=.*[a-z])" +      //# a lower case letter must occur at least once
            "(?=.*[A-Z])" +      //# an upper case letter must occur at least once
            "(?=.*[@#$%^&+=])" + //# a special character must occur at least once
            "(?=\\S+$)" +         //# no whitespace allowed in the entire string
            ".{8,}"   +          //# anything, at least eight places though
            "$";                 //# end-of-string;

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


            if (binding.enterEmail.getEditText().getText().toString().isEmpty()){
                binding.enterEmail.setError("This field can't be empty!");
                return;
            }
            else if (!binding.enterEmail.getEditText().getText().toString().matches(validEmail)){
                binding.enterEmail.setError("Invalid email!");
                return;
            }

            else if (binding.enterPassword.getEditText().getText().toString().isEmpty()){
                binding.enterPassword.setError("This field can't be empty!");
                return;
            }
            else if (!(binding.enterPassword.getEditText().getText().toString()).matches(validPassword)){
                binding.enterPassword.setError("Password is too weak.!"
                        +"\n Must contain"+
                        "\nat least 8 characters."+"\nAt least one digit"
                        +"\nAt least one lowercase letter and one uppercase letter"
                        +"\nA special character...@,#,$,%,^,&,+ or = and no spaces");
                return;
            }

           else if (binding.firstName.getEditText().getText().toString().isEmpty()){
                binding.firstName.setError("This field can't be empty!");
                return;
            }
            else if (binding.firstName.getEditText().getText().toString().length() >= 15){
                binding.firstName.setError("Name is too long!");
                return;
            }

            else if (binding.lastName.getEditText().getText().toString().isEmpty()){
                binding.lastName.setError("This field can't be empty!");
                return;
            }
            else if (binding.lastName.getEditText().getText().toString().length() >= 15){
                binding.lastName.setError("Name is too long!");
                return;
            }


            else if (binding.phoneNumber.getEditText().getText().toString().isEmpty()){
                binding.phoneNumber.setError("This field can't be empty!");
                return;
            }

            else {
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

    public void saveUserDetails(String mail, String firstName, String lastName, String phoneNumber) {
        User user = new User(mail, firstName, lastName, phoneNumber);
        databaseReference.child(userID).setValue(user);
    }
}