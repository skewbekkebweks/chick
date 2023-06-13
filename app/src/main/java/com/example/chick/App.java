package com.example.chick;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private static final String API_KEY = "e947752b-09ad-4cde-b4ff-34baa4f2c79b";

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(API_KEY);
        MapKitFactory.initialize(this);
    }
}
