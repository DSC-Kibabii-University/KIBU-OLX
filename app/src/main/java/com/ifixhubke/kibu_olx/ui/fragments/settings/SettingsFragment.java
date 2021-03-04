package com.ifixhubke.kibu_olx.ui.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.adapters.SettingsAdapter;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.data.Settings;
import com.ifixhubke.kibu_olx.databinding.FragmentSettingsBinding;
import com.ifixhubke.kibu_olx.viewmodels.MainViewModel;

import java.util.List;

import timber.log.Timber;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    SettingsAdapter adapter;
    private DatabaseReference databaseReference;
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(MainViewModel.class);

        Item item = new Item("asda","sdfaf","sdff",true);

        viewModel.insert(item);

        viewModel.allItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                Toast.makeText(getContext(),"Observed data"+item.getItemName(),Toast.LENGTH_SHORT).show();
            }
        });

        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //initializeRecycler();
        return view;
    }

    private void initializeRecycler() {
        Timber.d("initilize method call");
        Query query = databaseReference.child("posted_items_history");

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

   /* @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
}