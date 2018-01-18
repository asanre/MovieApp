package com.example.asanre.searchmoviedb;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MovieApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        initStetho();
    }

    private void initStetho() {

        Stetho.initializeWithDefaults(this);
    }
}
