package com.example.asanre.searchmoviedb.data.network;

import com.example.asanre.searchmoviedb.data.model.ServiceMoviesRepo;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("movie/top_rated")
    Single<ServiceMoviesRepo> getTopRateMovies(@QueryMap Map<String, String> options);

    @GET("search/movie")
    Single<ServiceMoviesRepo> searchMovie(@QueryMap Map<String, String> options);
}
