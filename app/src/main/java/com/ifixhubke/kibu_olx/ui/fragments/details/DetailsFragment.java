package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentDetailsBinding;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import timber.log.Timber;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Item data = DetailsFragmentArgs.fromBundle(getArguments()).getItemDetails();
        Timber.d(data.getItemName());
        Toast.makeText(requireContext(), "Item: "+data.getItemName(), Toast.LENGTH_SHORT).show();

        binding.detailsItemName.setText(data.getItemName());
        binding.detailsItemPrice.setText(data.getItemPrice());

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(data.getItemImage(),data.getItemName(), ScaleTypes.CENTER_CROP));

        binding.imageSlider.setImageList(imageList);

        return view;
    }

}