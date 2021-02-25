package com.ifixhubke.kibu_olx.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this,R.id.fragment);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        //bottom navigation setup
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.homeFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.favoritesFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }else if (destination.getId() == R.id.settingsFragment2) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
            else {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            }
        });
    }
}