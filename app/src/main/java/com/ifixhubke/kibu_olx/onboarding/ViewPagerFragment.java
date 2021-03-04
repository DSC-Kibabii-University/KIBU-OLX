package com.ifixhubke.kibu_olx.onboarding;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new ScreenOne());
        fragmentArrayList.add(new ScreenTwo());
        fragmentArrayList.add(new ScreenThree());


        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager(),
                getLifecycle(), fragmentArrayList);

        viewPager2.setAdapter(adapter);

        return view;
    }
}