package com.ifixhubke.kibu_olx.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;

import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {

    ItemClickListener itemClickListener;

    List<Item> itemArrayList;

    public SettingsAdapter(List<Item> itemArrayList, ItemClickListener itemClickListener) {
        this.itemArrayList = itemArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_row, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SettingsHolder holder, int position) {
        holder.name.setText(itemArrayList.get(position).getItemName());
        holder.price.setText("Kshs. " + itemArrayList.get(position).getItemPrice());
        holder.date.setText(itemArrayList.get(position).getDatePosted());

        Glide.with(holder.itemView)
                .load(itemArrayList.get(position).getItemImage())
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
                .into(holder.image);

        if (itemArrayList.get(position).getIsSoldOut()) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setEnabled(false);
        }else if (!itemArrayList.get(position).getIsSoldOut()){
            holder.checkBox.setChecked(false);
            holder.checkBox.setEnabled(true);

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Timber.d(holder.name.getText().toString());

                Item item = new Item(itemArrayList.get(position).getItemImage(),
                        itemArrayList.get(position).getItemName(),
                        itemArrayList.get(position).getItemPrice(),
                        itemArrayList.get(position).getDatePosted(),
                        itemArrayList.get(position).getIsSoldOut(),
                        itemArrayList.get(position).getItemUniqueId());

                itemClickListener.itemClick(item, itemArrayList.get(position).getId());

            });
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    static class SettingsHolder extends RecyclerView.ViewHolder {
        TextView name, price, date;
        ImageView image;
        CheckBox checkBox;
        ProgressBar progressBar;

        public SettingsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.p_item_name);
            price = itemView.findViewById(R.id.p_item_price);
            image = itemView.findViewById(R.id.posted_image);
            date = itemView.findViewById(R.id.posted_on);
            checkBox = itemView.findViewById(R.id.sold_out_checkbox);
            progressBar = itemView.findViewById(R.id.settingRowProgressBar);
        }
    }

}
