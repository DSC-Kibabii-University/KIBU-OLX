package com.ifixhubke.kibu_olx.adapters;


import android.annotation.SuppressLint;
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
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragmentDirections;
import com.squareup.picasso.Picasso;

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Item, FavouritesAdapter.ViewHolder> {

    public FavouritesAdapter(@NonNull FirebaseRecyclerOptions<Item> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getItemName());
        holder.itemPrice.setText("Ksh." + model.getItemPrice());
        holder.phoneNum.setText(model.getSellerPhoneNum());
        holder.location.setText(model.getLocation());
        holder.description.setText("Posted by " + model.getSellerName() + " on " + model.getDatePosted());
        Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.loadin)
                .into(holder.itemImage);

        holder.cardView.setOnClickListener(v -> {
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
                    model.getItemDescription(),
                    model.getCategory(),
                    model.getCondition(),
                    model.getItemUniqueId());

            NavDirections action = FavoritesFragmentDirections.actionFavoritesFragment2ToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false));
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice, description, location, phoneNum;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemImage = itemView.findViewById(R.id.item_image);
            this.itemName = itemView.findViewById(R.id.item_name);
            this.itemPrice = itemView.findViewById(R.id.item_price);
            this.cardView = itemView.findViewById(R.id.favourites_cardView);
            this.description = itemView.findViewById(R.id.textViewSomeDescription);
            this.location = itemView.findViewById(R.id.textViewLocation);
            this.phoneNum = itemView.findViewById(R.id.textViewPhoneNum);
        }
    }
}