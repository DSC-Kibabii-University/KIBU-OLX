package com.ifixhubke.kibu_olx.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.adapters.AllItemsAdapter;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentHomeBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentRegisterBinding;
import com.ifixhubke.kibu_olx.databinding.FragmentScreenOneBinding;

import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    DatabaseReference databaseReference;
    private AllItemsAdapter adapter;

    ArrayList<Item> itemsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.searchForItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        itemsList = new ArrayList<>();
        fetchItems();

        return view;
    }

    public void filter(String itemName){
        ArrayList<Item> itemsList = new ArrayList<>();

        for(Item item : itemsList){
            if(item.getItemName().toLowerCase().contains(itemName.toLowerCase())){
                itemsList.add(item);
            }
        }
        adapter.filterList(itemsList);
    }

    private void fetchItems() {
        binding.allItemsProgressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("all_items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Timber.d(i.toString());
                        Item item = i.getValue(Item.class);
                        itemsList.add(item);

                        //to reverse the list coz firebase sorts data in ascending order
                        Collections.reverse(itemsList);
                        binding.allItemsProgressBar.setVisibility(View.INVISIBLE);
                        initializeRecycler();
                    }
                }
                else{
                    binding.allItemsProgressBar.setVisibility(View.INVISIBLE);
                    Timber.d("snapshot not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeRecycler() {
        adapter = new AllItemsAdapter(itemsList);
        binding.allItemsRecyclerview.setAdapter(adapter);
    }
}