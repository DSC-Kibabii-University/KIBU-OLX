package com.ifixhubke.kibu_olx;

import android.app.Application;

import timber.log.Timber;

public class KibuOlxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
