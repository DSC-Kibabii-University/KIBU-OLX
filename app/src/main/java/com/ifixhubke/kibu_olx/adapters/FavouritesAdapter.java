package com.ifixhubke.kibu_olx.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.squareup.picasso.Picasso;

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Favourites, FavouritesAdapter.ViewHolder> {

    public FavouritesAdapter(@NonNull FirebaseRecyclerOptions<Favourites> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Favourites model) {
        holder.itemName.setText(model.getItemName());
        holder.itemPrice.setText(model.getItemPrice());
        Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.itemImage);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemPrice;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemImage = itemView.findViewById(R.id.item_image);
            this.itemName = itemView.findViewById(R.id.item_name);
            this.itemPrice = itemView.findViewById(R.id.item_price);
            this.cardView = itemView.findViewById(R.id.favourites_cardView);
        }
    }
}