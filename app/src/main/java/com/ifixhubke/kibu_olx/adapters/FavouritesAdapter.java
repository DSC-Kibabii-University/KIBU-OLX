package com.ifixhubke.kibu_olx.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.FavouritesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.myViewHolder>  {
    private ArrayList<FavouritesModel> list;
    public FavouritesAdapter(ArrayList<FavouritesModel> models){
        list=models;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getItemImage()).into(holder.favImage);
        holder.favItemName.setText(list.get(position).getItemName());
        holder.favItemPrice.setText(list.get(position).getItemPrice());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView favImage;
        TextView favItemName, favItemPrice;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.favImage=itemView.findViewById(R.id.item_image);
            this.favItemName=itemView.findViewById(R.id.item_name);
            this.favItemPrice=itemView.findViewById(R.id.item_price);

        }
    }


}