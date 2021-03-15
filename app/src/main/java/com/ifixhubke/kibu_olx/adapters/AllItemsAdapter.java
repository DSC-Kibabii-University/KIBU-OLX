package com.ifixhubke.kibu_olx.adapters;

import android.service.autofill.ImageTransformation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.ifixhubke.kibu_olx.ui.fragments.home.HomeFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AllItemsAdapter extends FirebaseRecyclerAdapter<Item, AllItemsAdapter.ViewHolder> {

    ItemClickListener itemClickListener;
    private List<Item> items;

    public AllItemsAdapter(@NonNull FirebaseRecyclerOptions<Item> options, ItemClickListener itemClickListener) {
        super(options);
        this.itemClickListener = itemClickListener;
    }

    //emiliussDOTY

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        Timber.d("data loaded");
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        Timber.d(error.getMessage());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        if (model.getItemStarred()) {
            holder.starredItem.setVisibility(View.VISIBLE);
            holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
        } else if (!model.getItemStarred()) {
            holder.starredItem.setVisibility(View.INVISIBLE);
        }

        holder.item_name.setText(model.getItemName());
        holder.item_price.setText("Ksh. "+model.getItemPrice());
        Picasso.get()
                .load(model.getItemImage2())
                .fit().centerInside()
                .placeholder(R.drawable.loadin)
                .into(holder.item_image);


        holder.card.setOnClickListener(v -> {

            Item item = new Item(model.getSellerName(),
                    model.getSellerLastSeen(),
                    model.getSellerPhoneNum(),
                    model.getItemImage(),
                    model.getItemImage2(),
                    model.getItemImage3(),
                    model.getItemName(),
                    model.getItemPrice(),
                    model.getDatePosted(),
                    model.getLocation(),
                    model.getItemDescription());

            NavDirections action = HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
            Timber.d("card clicked");
        });

        holder.add_item_to_favorites.setOnClickListener(v -> {
            model.setItemStarred(true);
            Item item = new Item(model.getItemImage(), model.getItemName(), model.getItemPrice(), model.getItemStarred());
            itemClickListener.addItemToFavorites(item, position);
            Timber.d("clicked");
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row, parent, false));
    }

    /*public void filteredList(ArrayList<Item> filterItemsList) {
        items=filterItemsList;
        notifyDataSetChanged();
    }*/

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_name;
        TextView item_price;
        ImageView add_item_to_favorites;
        CardView card;
        ImageView starredItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.itemImageImg);
            item_name = itemView.findViewById(R.id.itemNameTxt);
            item_price = itemView.findViewById(R.id.itemPriceTxt);
            add_item_to_favorites = itemView.findViewById(R.id.favoriteItemImg);
            card = itemView.findViewById(R.id.item_card_layout);
            starredItem = itemView.findViewById(R.id.starredfavoriteItemImg);
        }
    }
}
