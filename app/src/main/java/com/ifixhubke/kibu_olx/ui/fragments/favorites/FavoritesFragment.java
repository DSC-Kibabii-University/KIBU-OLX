package com.ifixhubke.kibu_olx.ui.fragments.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.adapters.FavouritesAdapter;
import com.ifixhubke.kibu_olx.data.FavouritesModel;
import com.ifixhubke.kibu_olx.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {
    FragmentFavoritesBinding binding;
    private FavouritesAdapter adapter;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentFavoritesBinding.inflate(inflater, container, false);
        View view1 = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        initializeRecycler();
        return view1;
    }

    private void initializeRecycler() {
        Query query = databaseReference.child("favoriteitems");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.favprogressBar.setVisibility(View.INVISIBLE);
                }else {
                    binding.favprogressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        FirebaseRecyclerOptions<FavouritesModel> options = new FirebaseRecyclerOptions
                .Builder<FavouritesModel>()
                .setQuery(query, FavouritesModel.class).build();

        adapter = new FavouritesAdapter(options);
        binding.favoriteRecyclerView.setAdapter(adapter);
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