package com.coccoc.news;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

public class NewsApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public void changeMode(boolean isLightMode) {
        AppCompatDelegate.setDefaultNightMode(
                isLightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
    }
    public static Context getInstance() {
        return context;
    }
}
