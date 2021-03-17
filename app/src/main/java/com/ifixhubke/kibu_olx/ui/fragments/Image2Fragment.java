package com.ifixhubke.kibu_olx.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.FragmentImage1Binding;
import com.ifixhubke.kibu_olx.databinding.FragmentImage2Binding;
import com.squareup.picasso.Picasso;

public class Image2Fragment extends Fragment {
    String imageUrl;
    FragmentImage2Binding binding;

    public Image2Fragment(String imageUrl){
        this.imageUrl = imageUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImage2Binding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_image_placeholder).into(binding.imageViewImage2);

        return view;
    }
}