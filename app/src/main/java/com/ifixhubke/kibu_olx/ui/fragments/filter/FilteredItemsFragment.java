package com.ifixhubke.kibu_olx.ui.fragments.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.adapters.FilteredItemsAdapter;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentFilteredItemsBinding;
import com.ifixhubke.kibu_olx.others.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

import timber.log.Timber;

public class FilteredItemsFragment extends Fragment implements ItemClickListener {
    FragmentFilteredItemsBinding binding;
    Item data;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFilteredItemsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        assert getArguments() != null;
        data = FilteredItemsFragmentArgs.fromBundle(getArguments()).getArgsFromFilterDialog();
        Timber.d(data.getCategory() + " " + data.getCondition() + " " + data.getMinPrice() + " " + data.getMaxPrice());
        filterItems(data.getCategory(), data.getCondition(), data.getMinPrice(), data.getMaxPrice());

        binding.filteredToolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_filteredItemsFragment_to_homeFragment2));

        return view;
    }


    private void filterItems(String category, String condition, double min, double max) {
        Timber.d("Filter fun called");
        ArrayList<Item> filtered = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("all_items").orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot i : snapshot.getChildren()) {
                        Item item = i.getValue(Item.class);
                        assert item != null;
                        double price = Double.parseDouble(item.getItemPrice());
                        if ((item.getCondition().equals(condition))) {

                            Timber.d("Condtion: Items found");
                            if ((price >= min) && (price <= max)) {
                                Timber.d("Range: Items found");
                                filtered.add(item);
                                binding.progressBar2.setVisibility(View.INVISIBLE);
                                binding.filteredRecyclerView.setVisibility(View.VISIBLE);
                                initializeRecycler(filtered);
                            }/*else{
                                binding.imageViewNothingFound.setVisibility(View.INVISIBLE);
                                binding.textViewNothingFound.setVisibility(View.INVISIBLE);
                            }*/
                        } else {
                            Timber.d("Items Not Found");
                           /*binding.imageViewNothingFound.setVisibility(View.VISIBLE);
                            binding.textViewNothingFound.setVisibility(View.VISIBLE);*/
                        }
                    }
                    //initializeRecycler(filtered);
                } else {
                    binding.imageViewNothingFound.setVisibility(View.VISIBLE);
                    binding.textViewNothingFound.setVisibility(View.VISIBLE);
                    binding.progressBar2.setVisibility(View.INVISIBLE);
                    Timber.d("snapshot not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initializeRecycler(ArrayList<Item> itemsList) {
        FilteredItemsAdapter adapter = new FilteredItemsAdapter(itemsList, this);
        binding.filteredRecyclerView.setAdapter(adapter);
    }

    @Override
    public void itemClick(Item item, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("favoriteitems");
        databaseReference.child(UUID.randomUUID().toString()).setValue(item).addOnSuccessListener(aVoid ->
                Toast.makeText(requireContext(), item.getItemName() + " added to favorites successfully", Toast.LENGTH_SHORT).show());
    }
}