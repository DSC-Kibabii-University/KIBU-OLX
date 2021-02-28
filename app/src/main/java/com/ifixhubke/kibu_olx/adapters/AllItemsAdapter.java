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
import com.google.firebase.database.DatabaseError;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.ifixhubke.kibu_olx.ui.fragments.home.HomeFragmentDirections;
import com.squareup.picasso.Picasso;
import timber.log.Timber;

public class AllItemsAdapter extends FirebaseRecyclerAdapter<Item,AllItemsAdapter.ViewHolder> {

    ItemClickListener itemClickListener;

    public AllItemsAdapter(@NonNull FirebaseRecyclerOptions<Item> options,  ItemClickListener itemClickListener) {
        super(options);
        this.itemClickListener = itemClickListener;
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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model) {
        holder.item_name.setText(model.getItemName());
        holder.item_price.setText(model.getItemPrice());
        Picasso.get()
                .load(model.getItemImage())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.item_image);


        holder.card.setOnClickListener(v -> {

            Item item = new Item(model.getItemImage(),model.getItemName(),model.getItemPrice());
            NavDirections action =  HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
            Timber.d("card clicked");
        });

        holder.add_item_to_favorites.setOnClickListener(v -> {
            Item item = new Item(model.getItemImage(),model.getItemName(),model.getItemPrice());
            itemClickListener.addItemToFavorites(item,position);
            Timber.d("clicked");
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row,parent,false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_image;
        TextView item_name;
        TextView item_price;
        ImageView add_item_to_favorites;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.itemImageImg);
            item_name = itemView.findViewById(R.id.itemNameTxt);
            item_price = itemView.findViewById(R.id.itemPriceTxt);
            add_item_to_favorites = itemView.findViewById(R.id.favoriteItemImg);
            card = itemView.findViewById(R.id.item_card_layout);
        }
    }
}
