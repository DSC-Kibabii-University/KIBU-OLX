package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ifixhubke.kibu_olx.data.Favourites;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentDetailsBinding;
//import com.ifixhubke.kibu_olx.ui.fragments.favorites.FavoritesFragmentArgs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;
    public String imageOne;
    public String imageTwo;
    public String imageThree;
    Item data;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        assert getArguments() != null;

        data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetailsArgs();
        Timber.d(data.getItemName());

        binding.userName1.setText(data.getSellerName());
        binding.tvLastseen1.setText(data.getSellerLastSeen());
        binding.favPhoneNumber1.setText(data.getSellerPhoneNum());

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage2(), data.getItemName(), ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(data.getItemImage3(), data.getItemName(), ScaleTypes.CENTER_CROP));

        Timber.d("image 1 " + data.getItemImage() + " \n image 2 " + data.getItemImage2() + " \n image 3 " + data.getItemImage3());

        binding.imageSliderFav1.setImageList(imageList);

        binding.favoritesItemName1.setText(data.getItemName());
        binding.favItemPrice1.setText("Ksh. "+data.getItemPrice());
        binding.favDatePosted1.setText("Uploaded on " + data.getDatePosted());
        binding.favLocation1.setText(data.getLocation());
        binding.favDescription1.setText(data.getItemDescription());

        binding.imageSliderFav1.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {

                ///String image = imageList.get(position).getImageUrl();
                Item item = new Item(data.getItemImage(),data.getItemImage2(),data.getItemImage3());
               NavDirections navDirections = DetailsFragmentDirections.actionDetailsFragmentToPictureBrowserFragment(item);
               Navigation.findNavController(getView()).navigate(navDirections);
            }
        });

        return view;
    }
}