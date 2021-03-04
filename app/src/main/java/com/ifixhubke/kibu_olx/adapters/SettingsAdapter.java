package com.ifixhubke.kibu_olx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Settings;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class SettingsAdapter extends FirebaseRecyclerAdapter<Settings, SettingsAdapter.SettingsHolder> {

    public SettingsAdapter(@NonNull FirebaseRecyclerOptions<Settings> options) {
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
    protected void onBindViewHolder(@NonNull SettingsHolder holder, int position, @NonNull Settings model) {
        Timber.d(model.toString());
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        Picasso.get().load(model.getImage())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.image);

    }

    @NonNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_row, parent, false));
    }

    class SettingsHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        CheckBox checkBox;

        public SettingsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.p_item_name);
            price = itemView.findViewById(R.id.p_item_price);
            image = itemView.findViewById(R.id.posted_image);
            checkBox = itemView.findViewById(R.id.sold_out_checkbox);
        }
    }

}
