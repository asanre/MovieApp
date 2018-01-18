package com.example.asanre.searchmoviedb.domain;

import com.example.asanre.searchmoviedb.data.model.MovieEntity;

import java.util.List;

import io.reactivex.Single;

public interface DataSource {

    /**
     * @param page int
     * @return list of movies
     */
    Single<List<MovieEntity>> getMovies(int page);

    Single<List<MovieEntity>> searchMovies(String movie, int page);
}
