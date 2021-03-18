package com.ifixhubke.kibu_olx;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import timber.log.Timber;

public class KibuOlxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Timber.plant(new Timber.DebugTree());
    }
}
