package com.ifixhubke.kibu_olx.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.others.ItemClickListener;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(itemArrayList.get(position).getItemImage()).placeholder(R.drawable.ic_image_placeholder).into(holder.image);
        holder.price.setText("Kshs. " + itemArrayList.get(position).getItemPrice());
        holder.date.setText(itemArrayList.get(position).getDatePosted());

        if (itemArrayList.get(position).getIsSoldOut()) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setEnabled(false);
        } else {
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Timber.d(holder.name.getText().toString());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference.child("all_items").orderByChild("itemUniqueId").equalTo(itemArrayList.get(position)
                        .getItemUniqueId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot i : snapshot.getChildren()) {
                                Timber.d(Objects.requireNonNull(i.getValue()).toString());
                                i.getRef().removeValue();
                                Timber.d("%s removed from advertisements", i.getValue().toString());
                            }
                        }else{
                            Timber.d("Does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Timber.d(error.getMessage());
                    }
                });

                Item item = new Item(itemArrayList.get(position).getItemImage(),
                        itemArrayList.get(position).getItemName(),
                        itemArrayList.get(position).getItemPrice(),
                        itemArrayList.get(position).getDatePosted(),
                        itemArrayList.get(position).getIsSoldOut(),
                        itemArrayList.get(position).getItemUniqueId());

                itemClickListener.addItemToFavorites(item, itemArrayList.get(position).getId());

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

        public SettingsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.p_item_name);
            price = itemView.findViewById(R.id.p_item_price);
            image = itemView.findViewById(R.id.posted_image);
            date = itemView.findViewById(R.id.posted_on);
            checkBox = itemView.findViewById(R.id.sold_out_checkbox);
        }
    }

}
