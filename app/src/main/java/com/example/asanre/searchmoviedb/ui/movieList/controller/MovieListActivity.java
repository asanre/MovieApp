package com.example.asanre.searchmoviedb.ui.movieList.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.asanre.searchmoviedb.R;
import com.example.asanre.searchmoviedb.domain.Provider;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Provider.getInstance().init(this);
        setContentView(R.layout.activity_movie_list);
    }
}
