package com.ifixhubke.kibu_olx;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

public class KibuOlxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        SharedPreferences sharedPreferences = getSharedPreferences("ui_mode", Context.MODE_PRIVATE);
        boolean itemUIMode = sharedPreferences.getBoolean("ISCHECKED", false);
        Timber.d("UI Theme: " + itemUIMode);
        uiMode(itemUIMode);
    }

    private void uiMode(boolean isDarkModeOn) {
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
