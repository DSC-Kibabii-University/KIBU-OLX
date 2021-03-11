package com.ifixhubke.kibu_olx.ui.fragments.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.ifixhubke.kibu_olx.adapters.AllItemsAdapter;
import com.ifixhubke.kibu_olx.adapters.FavouritesAdapter;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentFavoritesBinding;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import java.util.ArrayList;
import java.util.UUID;

public class FavoritesFragment extends Fragment implements ItemClickListener {

    FragmentFavoritesBinding binding;
    private FavouritesAdapter adapter;
    private DatabaseReference databaseReference;
    ArrayList<Favourites> savedarrayList;
    private String userID = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference();
/*
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("all_times").child(userID).child("favoriteitems");
        savedarrayList = new ArrayList<>();*/
        initializeRecycler();


        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot i : snapshot.getChildren()){
                        Favourites favourites = i.getValue(Favourites.class);
                        savedarrayList.add(favourites);

                        initializeRecycler();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        return view;
    }

    private void initializeRecycler() {
        Query query = databaseReference.child("favoriteitems");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.favprogressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseRecyclerOptions<Favourites> options = new FirebaseRecyclerOptions.Builder<Favourites>()
                .setQuery(query,Favourites.class)
                .build();

        adapter = new FavouritesAdapter(options,this);
        binding.favoriteRecyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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

    @Override
    public void addItemToFavorites(Item item, int position) {

    }

    @Override
    public void clickCard(Favourites favourites, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("favoriteitems");
        //databaseReference.child(UUID.randomUUID()
        //        .toString())
        //        .setValue(favourites)
        //        .addOnSuccessListener(aVoid -> Toast.makeText(requireContext(), favourites.getItemName() + " to favorites successfully", Toast.LENGTH_SHORT).show());
        //databaseReference.child(String.valueOf(UUID.randomUUID())).setValue(null);
    }

}