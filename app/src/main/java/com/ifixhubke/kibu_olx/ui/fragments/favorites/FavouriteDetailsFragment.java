package com.ifixhubke.kibu_olx.ui.fragments.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.databinding.FragmentFavouriteDetailsBinding;

import java.util.ArrayList;
import java.util.UUID;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteDetailsFragment extends Fragment {

    FragmentFavouriteDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavouriteDetailsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Favourites favourites = FavouriteDetailsFragmentArgs.fromBundle(getArguments()).getFavoriteArgs();
        Timber.d(favourites.getItemName());

        ArrayList<SlideModel> imageList = new ArrayList<>();
       imageList.add(new SlideModel(favourites.getItemImage(), favourites.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(favourites.getItemImage2(), favourites.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(favourites.getItemImage3(), favourites.getItemName(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + favourites.getItemImage() + " \n image 2 " + favourites.getItemImage2() + " \n image 3 " + favourites.getItemImage3());

        binding.imageSliderFav.setImageList(imageList);

        binding.favoritesItemName.setText(favourites.getItemName());
        binding.favItemPrice.setText("Ksh. "+favourites.getItemPrice());
        binding.favDatePosted.setText("Uploaded on: "+favourites.getDatePosted());
        binding.favLocation.setText(favourites.getLocation());
        binding.favDescription.setText(favourites.getItemDescription());
        Timber.d("\n description "+favourites.getItemDescription());


        return view;
    }
}