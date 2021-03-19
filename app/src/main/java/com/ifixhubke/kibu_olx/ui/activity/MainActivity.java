package com.ifixhubke.kibu_olx.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ifixhubke.kibu_olx.R;
import com.ifixhubke.kibu_olx.databinding.ActivityMainBinding;
import com.ifixhubke.kibu_olx.others.CheckInternet;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    CheckInternet checkInternet;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottom_nav);

        //try
        firebaseAuth = FirebaseAuth.getInstance();

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
                bottomNavigationView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            CheckInternet.updateUserStatus("online");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth.getCurrentUser() != null){
            CheckInternet.updateUserStatus("offline");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firebaseAuth.getCurrentUser() != null){
            CheckInternet.updateUserStatus("offline");
        }
    }
}