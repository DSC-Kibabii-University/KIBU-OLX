package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentDetailsBinding;
import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragmentArgs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        assert getArguments() != null;
        //try
        Favourites favourites = FavoritesFragmentArgs.fromBundle(getArguments()).getFavoriteArgs();
        Item data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetailsArgs();
        Timber.d(data.getItemName());

        binding.userName.setText(data.getSellerName());
        binding.tvLastseenHours.setText(data.getSellerLastSeen());
        binding.sellerPhoneNum.setText(data.getSellerPhoneNum());

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage2(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage3(), data.getItemName(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + data.getItemImage() + " \n image 2 " + data.getItemImage2() + " \n image 3 " + data.getItemImage3());

        binding.imageSlider.setImageList(imageList);

        binding.detailsItemName.setText(data.getItemName());
        binding.detailsItemPrice.setText("Ksh. "+data.getItemPrice());
        binding.detailsDatePosted.setText("Uploaded on " + data.getDatePosted());
        binding.itemDetailsLocation.setText(data.getLocation());
        binding.itemDetailsDescription.setText(data.getItemDescription());

        return view;
    }
}