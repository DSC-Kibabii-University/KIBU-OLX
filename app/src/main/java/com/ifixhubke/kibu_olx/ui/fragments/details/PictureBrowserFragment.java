package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.models.SlideModel;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentPictureBrowserBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;



import timber.log.Timber;

public class PictureBrowserFragment extends Fragment {
Item item;
FragmentPictureBrowserBinding binding;
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPictureBrowserBinding.inflate(inflater,container,false);
        View view = binding.getRoot();



        item = PictureBrowserFragmentArgs.fromBundle(getArguments()).getImageArgs();

        Timber.d(item.getItemImage());

        Picasso.get()
                .load(item.getItemImage())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(binding.fullScreenPicture);

        return view;
    }
}