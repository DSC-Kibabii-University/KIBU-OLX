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
import com.google.firebase.database.DatabaseError;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.FavouritesModel;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class FavouritesAdapter extends FirebaseRecyclerAdapter<FavouritesModel,FavouritesAdapter.ViewHolder>{

    public FavouritesAdapter(@NonNull FirebaseRecyclerOptions<FavouritesModel> options) {
        super(options);
    }

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FavouritesModel model) {
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
            this.itemImage=itemView.findViewById(R.id.item_image);
            this.itemName=itemView.findViewById(R.id.item_name);
            this.itemPrice=itemView.findViewById(R.id.item_price);
            this.cardView=itemView.findViewById(R.id.favourites_cardView);
        }
    }
}