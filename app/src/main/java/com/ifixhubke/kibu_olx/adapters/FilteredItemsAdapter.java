package com.ifixhubke.kibu_olx.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.ifixhubke.kibu_olx.ui.fragments.filter.FilteredItemsFragmentDirections;

import java.util.ArrayList;

import timber.log.Timber;

public class FilteredItemsAdapter extends RecyclerView.Adapter<FilteredItemsAdapter.ViewHolder> {

    private final ArrayList<Item> items;
    ItemClickListener itemClickListener;

    public FilteredItemsAdapter(ArrayList<Item> itemList, ItemClickListener itemClickListener) {
        items = itemList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (items.get(position).getItemStarred()) {
            holder.starredItem.setVisibility(View.VISIBLE);
            holder.add_item_to_favorites.setVisibility(View.INVISIBLE);
        } else if (!items.get(position).getItemStarred()) {
            holder.starredItem.setVisibility(View.INVISIBLE);
        }

        holder.item_name.setText(items.get(position).getItemName());
        holder.item_price.setText("Ksh. " + items.get(position).getItemPrice());

        Glide.with(holder.itemView)
                .load(items.get(position).getItemImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .fitCenter()
                .into(holder.item_image);

        holder.card.setOnClickListener(v -> {

            Item item = new Item(items.get(position).getSellerName(),
                    items.get(position).getSellerLastSeen(),
                    items.get(position).getSellerPhoneNum(),
                    items.get(position).getItemImage(),
                    items.get(position).getItemImage2(),
                    items.get(position).getItemImage3(),
                    items.get(position).getItemName(),
                    items.get(position).getItemPrice(),
                    items.get(position).getDatePosted(),
                    items.get(position).getLocation(),
                    items.get(position).getItemDescription(),
                    items.get(position).getCategory(),
                    items.get(position).getCondition(),
                    items.get(position).getItemUniqueId());

            NavDirections action = FilteredItemsFragmentDirections.actionFilteredItemsFragmentToDetailsFragment(item);
            Navigation.findNavController(v).navigate(action);
            Timber.d("card clicked");
        });

        holder.add_item_to_favorites.setOnClickListener(v -> {
            items.get(position).setItemStarred(true);
            Item item = new Item(items.get(position).getItemImage(), items.get(position).getItemName(), items.get(position).getItemPrice(), items.get(position).getItemStarred());
            itemClickListener.itemClick(item, position);
            Timber.d("clicked");
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_name;
        TextView item_price;
        ImageView add_item_to_favorites;
        CardView card;
        ImageView starredItem;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.itemImageImg);
            item_name = itemView.findViewById(R.id.itemNameTxt);
            item_price = itemView.findViewById(R.id.itemPriceTxt);
            add_item_to_favorites = itemView.findViewById(R.id.favoriteItemImg);
            card = itemView.findViewById(R.id.item_card_layout);
            starredItem = itemView.findViewById(R.id.starredfavoriteItemImg);
            progressBar = itemView.findViewById(R.id.allItemsRowProgressBar);
        }
    }
}
