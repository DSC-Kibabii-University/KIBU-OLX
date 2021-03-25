package com.ifixhubke.kibu_olx.ui.fragments.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.adapters.FavouritesAdapter;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentFavoritesBinding;

import java.util.Objects;

import timber.log.Timber;

public class FavoritesFragment extends Fragment {

    FragmentFavoritesBinding binding;
    private FavouritesAdapter adapter;
    private DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        initializeRecycler();

        return view;
    }

    private void initializeRecycler() {

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("favorite_items");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.favprogressBar.setVisibility(View.INVISIBLE);
                    binding.imageViewFavoritesNothingFound.setVisibility(View.INVISIBLE);
                    binding.textViewFavoritesNothingFound.setVisibility(View.INVISIBLE);
                } else {
                    binding.favprogressBar.setVisibility(View.INVISIBLE);
                    binding.imageViewFavoritesNothingFound.setVisibility(View.VISIBLE);
                    binding.textViewFavoritesNothingFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.favprogressBar.setVisibility(View.INVISIBLE);
                Timber.d(error.getMessage());
            }
        });

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new FavouritesAdapter(options);
        binding.favoriteRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.favoriteRecyclerView);

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
}