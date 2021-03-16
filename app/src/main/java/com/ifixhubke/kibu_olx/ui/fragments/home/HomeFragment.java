package com.ifixhubke.kibu_olx.ui.fragments.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.adapters.AllItemsAdapter;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentHomeBinding;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import timber.log.Timber;

public class HomeFragment extends Fragment implements ItemClickListener, MaterialSearchBar.OnSearchActionListener,
        Toolbar.OnMenuItemClickListener, androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {

    FragmentHomeBinding binding;
    private AllItemsAdapter adapter;
    private DatabaseReference databaseReference;
    ArrayList<Item> itemsList = new ArrayList<>();
    FirebaseRecyclerOptions<Item> options;
    ItemClickListener itemClickListener;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.toolbar.setOnMenuItemClickListener(this);
        binding.searchBar.setOnSearchActionListener(this);
        binding.searchBar.inflateMenu(R.menu.main_menu);
        binding.searchBar.getMenu().setOnMenuItemClickListener(this);

        binding.floatingActionButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment2_to_sellFragmentOne));

        binding.searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                search(editable.toString());
            }
        });

        fetchItems();

        return view;
    }

    public void search(String itemName) {
        ArrayList<Item> filterItemsList = new ArrayList<>();

        for (Item item : itemsList) {
            if (item.getItemName().toLowerCase().contains(itemName.toLowerCase())) {
                filterItemsList.add(item);
            }
        }
        initializeRecycler(filterItemsList);
    }

    private void fetchItems() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.imageView2.setVisibility(View.INVISIBLE);
                    binding.textView.setVisibility(View.INVISIBLE);

                    for (DataSnapshot i : snapshot.getChildren()) {
                        Timber.d(i.toString());
                        Item item = i.getValue(Item.class);
                        itemsList.add(item);
                        //to reverse the list coz firebase sorts data in ascending order
                        Collections.reverse(itemsList);
                        binding.shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        binding.allItemsRecyclerview.setVisibility(View.VISIBLE);
                        initializeRecycler(itemsList);
                    }
                } else {
                    binding.imageView2.setVisibility(View.VISIBLE);
                    binding.textView.setVisibility(View.VISIBLE);
                    binding.shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    Timber.d("snapshot not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initializeRecycler(ArrayList<Item> itemsList) {
        adapter = new AllItemsAdapter(itemsList, this);
        binding.allItemsRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause");
        binding.shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume");
        binding.shimmerFrameLayout.startShimmer();
    }

    @Override
    public void addItemToFavorites(Item item, int position) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("favoriteitems");
        databaseReference.child(UUID.randomUUID().toString()).setValue(item).addOnSuccessListener(aVoid ->
                Toast.makeText(requireContext(), item.getItemName() + " to favorites successfully", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.filter_menu) {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment2_to_filterFragment);
            return true;
        } else if (item.getItemId() == R.id.shareMenu) {
            Toast.makeText(requireContext(), "Share Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.aboutUsMenu) {
            Toast.makeText(requireContext(), "About Us Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.inviteFriendMenu) {
            Toast.makeText(requireContext(), "Invite Friend Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.rateUsMenu) {
            Toast.makeText(requireContext(), "Rate Us Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.helpMenu) {
            Toast.makeText(requireContext(), "Help and Feedback Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.logoutMenu) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment2_to_loginFragment);
            return true;
        } else
            return false;
    }

    @Override
    public void clickCard(Favourites favourites, int position) {
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}