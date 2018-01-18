package com.example.asanre.searchmoviedb.domain;

import android.content.Context;

import com.example.asanre.searchmoviedb.data.DataProvider;
import com.example.asanre.searchmoviedb.domain.useCase.params.SearchMovieParams;
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

    public void init(Context context) {

        this.dataSource = DataProvider.getInstance(context);
    }

    public Single<List<IMovie>> getTopMovies(int page) {

        return dataSource.getMovies(page).map(DomainMapper::map);
    }

    public Single<List<IMovie>> searchMovie(SearchMovieParams params) {

        return dataSource.searchMovies(params.getMovie(), params.getPage()).map(DomainMapper::map);
    }
}
