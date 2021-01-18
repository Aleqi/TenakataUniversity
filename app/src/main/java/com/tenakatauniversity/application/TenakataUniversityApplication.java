package com.tenakatauniversity.application;

import android.app.Application;

import com.tenakatauniversity.BuildConfig;

import timber.log.Timber;

public class TenakataUniversityApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
