package com.ifixhubke.kibu_olx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder>{

    private final ArrayList<Item> items;

    public AllItemsAdapter(ArrayList<Item> itemList){
        items = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_recycler_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_name.setText(items.get(position).getItemName());
        holder.item_price.setText(items.get(position).getItemPrice());
        Picasso.get()
                .load(items.get(position).getItemImage())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_image;
        TextView item_name;
        TextView item_price;
        ImageView add_item_to_favorites;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.itemImageImg);
            item_name = itemView.findViewById(R.id.itemNameTxt);
            item_price = itemView.findViewById(R.id.itemPriceTxt);
            add_item_to_favorites = itemView.findViewById(R.id.favoriteItemImg);

        }
    }
}
