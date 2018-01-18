package com.example.asanre.searchmoviedb.domain;

import com.example.asanre.searchmoviedb.data.DataProvider;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import java.util.List;

import io.reactivex.Single;

public class Provider {

    private DataSource dataSource;
    private static Provider sInstance;

    public static Provider getInstance() {

        if (sInstance == null) {
            synchronized (DataProvider.class) {
                if (sInstance == null) {
                    sInstance = new Provider();
                }
            }
        }
        return sInstance;
    }

    public Single<List<IMovie>> getTopMovies(int page) {

        return dataSource.getMovies(page).map(DomainMapper::map);
    }
}
