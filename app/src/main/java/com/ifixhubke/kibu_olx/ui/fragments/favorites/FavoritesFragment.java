package com.ifixhubke.kibu_olx.ui.fragments.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.adapters.FavouritesAdapter;
import com.ifixhubke.kibu_olx.data.FavouritesModel;
import com.ifixhubke.kibu_olx.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;

import static android.content.ContentValues.TAG;

public class FavoritesFragment extends Fragment {
    FragmentFavoritesBinding binding;
    private DatabaseReference databaseReference;
   // RecyclerView recyclerView;
    private ArrayList<FavouritesModel> models;
    private FavouritesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentFavoritesBinding.inflate(inflater, container, false);
        View view1 = binding.getRoot();
        models=new ArrayList<>();
        fetchFavoritePosts();
        return view1;


    }


    private void fetchFavoritePosts() {
        binding.progressBar4.setVisibility(View.VISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference("favoriteitems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(snapshot.exists()){
                   for (DataSnapshot i: snapshot.getChildren()){
                       Timber.d(i.toString());
                       FavouritesModel model=i.getValue(FavouritesModel.class);
                       models.add(model);

                       binding.progressBar4.setVisibility(View.INVISIBLE);
                       Collections.reverse(models);
                       initializeRecycler();
                   }
               }
               else {
                   Timber.d("Snapshot not found");
               }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Timber.tag(TAG).w(error.toException(), "loadPost:onCancelled");
            }
        });


    }

    private void initializeRecycler() {
        binding.favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new FavouritesAdapter(models);
        binding.favoriteRecyclerView.setAdapter(adapter);
    }


}