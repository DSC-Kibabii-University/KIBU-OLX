package com.ifixhubke.kibu_olx.ui.fragments.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.User;
import com.ifixhubke.kibu_olx.databinding.FragmentRegisterBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenOneBinding;

import timber.log.Timber;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        binding.signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        binding.registerlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = binding.enterEmail.getText().toString().trim();
                String pass = binding.enterPassword.getText().toString().trim();
                String first_Name = binding.firstName.getText().toString().trim();
                String last_Name  = binding.lastName.getText().toString().trim();
                String phone_Number = binding.phoneNumber.getText().toString().trim();

                if (TextUtils.isEmpty(binding.enterEmail.getText().toString())){
                    binding.enterEmail.setError("Field can't be empty!");
                    return;
                }
                else if (TextUtils.isEmpty(binding.enterPassword.getText().toString())){
                    binding.enterPassword.setError("Field can't be empty!");
                    return;
                }
                else if (TextUtils.isEmpty(binding.firstName.getText().toString())){
                    binding.firstName.setError("Field can't be empty!");
                    return;
                }
                else if (TextUtils.isEmpty(binding.lastName.getText().toString())){
                    binding.lastName.setError("Field can't be empty!");
                    return;
                }
                else if (TextUtils.isEmpty(binding.phoneNumber.getText().toString())){
                    binding.phoneNumber.setError("Field can't be empty!");
                    return;
                }
                else if (binding.enterPassword.length()<6){
                    binding.enterPassword.setError("Password should be 6 characters or more!");
                }
                else if (!isvalidemail(binding.enterEmail.getText().toString())){
                    binding.enterEmail.setError("Invalid Email!");
                }


                firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userID = firebaseAuth.getUid();
                            saveUserDetails(mail,first_Name,last_Name,phone_Number);
                            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                            Timber.d("createUserWithEmailAndPassword: Success");
                        }
                        else {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            Timber.d("createUserWithEmailAndPassword: Failed");
                        }
                    }
                })

            }
        });



        return view;
    }

    public void saveUserDetails(String mail,String firstName,String lastName,String phoneNumber){
        User user = new User(mail,firstName,lastName,phoneNumber);
        databaseReference.child(userID).setValue(user);
    }
}