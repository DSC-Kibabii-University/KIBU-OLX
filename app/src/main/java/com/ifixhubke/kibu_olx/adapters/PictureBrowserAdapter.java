package com.ifixhubke.kibu_olx.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PictureBrowserAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrrayList;


    public PictureBrowserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<Fragment> fragmentArrrayList1) {
        super(fragmentManager, lifecycle);
        fragmentArrrayList = fragmentArrrayList1;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrrayList.size();
    }
}
