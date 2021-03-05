package com.ifixhubke.kibu_olx.adapters;


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
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragment;
import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragmentDirections;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class FavouritesAdapter extends FirebaseRecyclerAdapter<Favourites, FavouritesAdapter.ViewHolder> {

    ItemClickListener itemClickListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FavouritesAdapter(@NonNull FirebaseRecyclerOptions<Favourites> options,ItemClickListener itemClickListener) {
        super(options);
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Favourites model) {
        holder.itemName.setText(model.getItemName());
        holder.itemPrice.setText(model.getItemPrice());
        Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.loadin)
                .into(holder.itemImage);

        holder.cardView.setOnClickListener(v -> {
            Favourites favourites = new Favourites(
                    model.getSellerName(),
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
            Timber.d(""+favourites.getItemImage2()+""+favourites.getItemImage3()+"\n description"+favourites.getItemDescription());
            NavDirections directions = FavoritesFragmentDirections.actionFavoritesFragment2ToFavouriteDetailsFragment(favourites);
            Navigation.findNavController(v).navigate(directions);
            Timber.d("favorite card clicked");
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row,parent,false));
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