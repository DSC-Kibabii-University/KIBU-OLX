package com.ifixhubke.kibu_olx.ui.fragments.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.adapters.SettingsAdapter;
import com.ifixhubke.kibu_olx.data.Settings;
import com.ifixhubke.kibu_olx.databinding.FragmentSettingsBinding;

import timber.log.Timber;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    SettingsAdapter adapter;
    private DatabaseReference databaseReference;
    String userid;
    String F_Name;
    String L_Name;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        userid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        initializeRecycler();
        getUserDetails();

        binding.editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViews();

            }
        });

        binding.saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();
                binding.editUserName1.setVisibility(View.INVISIBLE);
                binding.editUserName2.setVisibility(View.INVISIBLE);
                binding.saveTextView.setVisibility(View.INVISIBLE);
                binding.editTextView.setVisibility(View.VISIBLE);
                binding.userName.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void initializeRecycler() {
        Timber.d("initilize method call");
        Query query = databaseReference.child("all_items");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Timber.d("data exist");
                    binding.progressBar.setVisibility(View.INVISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Timber.d("data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<Settings> options = new FirebaseRecyclerOptions.Builder<Settings>()
                .setQuery(query, Settings.class)
                .build();

        adapter = new SettingsAdapter(options);
        Timber.d("adapter");
        binding.yourPostRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void getUserDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                F_Name = snapshot.child("f_Name").getValue().toString();
                L_Name = snapshot.child("l_Name").getValue().toString();
                String email = snapshot.child("e_Mail").getValue().toString();
                binding.userName.setText(F_Name + " " + L_Name);
                binding.userEmail.setText(email);

                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userProfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USERNAME", F_Name + " " + L_Name);
                editor.apply();
                editor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateData() {
        String fName1 = binding.editUserName1.getText().toString();
        String fName2 = binding.editUserName2.getText().toString();
        if (TextUtils.isEmpty(fName1) && TextUtils.isEmpty(fName2)){
            Toast.makeText(requireContext(), "User name required!!! Please Enter to edit", Toast.LENGTH_LONG).show();
        }
         else if (TextUtils.isEmpty(fName1) || TextUtils.isEmpty(fName2)){

             databaseReference.child(userid).child("f_Name").setValue(fName1);
             binding.userName.setText(fName1+ " "+ L_Name);
             databaseReference.child(userid).child("l_Name").setValue(fName2);
             binding.userName.setText(F_Name+ " "+ fName2);
             binding.userName.setVisibility(View.VISIBLE);
        }
         else if ((!TextUtils.isEmpty(fName1)&&!fName1.equals(F_Name)) || (!TextUtils.isEmpty(fName2)&&!fName2.equals(L_Name))) {
            databaseReference.child(userid).child("f_Name").setValue(fName1);
             databaseReference.child(userid).child("l_Name").setValue(fName2);
           // binding.userName.setText(F_Name);
            binding.userName.setText(fName1 + " " + fName2);
            binding.userName.setVisibility(View.VISIBLE);
        }
         else {
             Toast.makeText(requireContext(), "Unable to edit name", Toast.LENGTH_SHORT).show();
         }
    }

    public void setViews() {
        binding.userName.setVisibility(View.INVISIBLE);
        binding.editUserName1.setVisibility(View.VISIBLE);
        binding.editUserName2.setVisibility(View.VISIBLE);
        binding.editTextView.setVisibility(View.INVISIBLE);
        binding.saveTextView.setVisibility(View.VISIBLE);
    }
}