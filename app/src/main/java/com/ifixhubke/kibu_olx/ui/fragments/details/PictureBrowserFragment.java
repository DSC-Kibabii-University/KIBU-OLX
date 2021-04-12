package com.ifixhubke.kibu_olx.ui.fragments.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.adapters.ViewPagerAdapter;
import com.ifixhubke.kibu_olx.data.Item;
import com.ifixhubke.kibu_olx.databinding.FragmentPictureBrowserBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class PictureBrowserFragment extends Fragment {
    Item item;
    FragmentPictureBrowserBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPictureBrowserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        assert getArguments() != null;
        item = PictureBrowserFragmentArgs.fromBundle(getArguments()).getImageArgs();

        Timber.d(item.getItemImage() + "" + item.getItemImage2() + "" + item.getItemImage3());


        binding.pictureBrowsingtoolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        ViewPager2 viewPager2 = view.findViewById(R.id.pictureBrowsingViewPager);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new Image1Fragment(item.getItemImage()));
        fragmentArrayList.add(new Image2Fragment(item.getItemImage2()));
        fragmentArrayList.add(new Image3Fragment(item.getItemImage3()));


        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager(),
                getLifecycle(), fragmentArrayList);

        viewPager2.setAdapter(adapter);

        return view;
    }
}