package com.example.asanre.searchmoviedb.data;

import android.content.Context;

import com.example.asanre.searchmoviedb.BuildConfig;
import com.example.asanre.searchmoviedb.data.db.MovieDB;
import com.example.asanre.searchmoviedb.data.db.MovieDao;
import com.example.asanre.searchmoviedb.data.model.MovieEntity;
import com.example.asanre.searchmoviedb.data.model.ServiceMoviesRepo;
import com.example.asanre.searchmoviedb.data.network.ApiService;
import com.example.asanre.searchmoviedb.data.network.RestClient;
import com.example.asanre.searchmoviedb.domain.DataSource;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class DataProvider implements DataSource {

    private final MovieDao movieDao;
    private final ApiService apiService;
    private static DataProvider sInstance;

    private DataProvider(Context context) {

        this.movieDao = MovieDB.getInstance(context).getMovieDao();
        this.apiService = RestClient.getService();
    }

    public static DataProvider getInstance(Context context) {

        if (sInstance == null) {
            synchronized (DataProvider.class) {
                if (sInstance == null) {
                    sInstance = new DataProvider(context);
                }
            }
        }
        return sInstance;
    }

    @Override
    public Single<List<MovieEntity>> getMovies(int page) {

        return Single.zip(retrieveMoviesAndSaveOnSuccess(page).onErrorResumeNext(throwable -> {

            if (throwable instanceof UnknownHostException) {
                return Single.just(new ArrayList<>());
            }
            return Single.error(throwable);
        }), movieDao.getMoviesByPage(page), (apiRepo, dbRepo) -> {

            if (apiRepo.isEmpty()) {
                return dbRepo;
            }
            return apiRepo;
        });
    }

    private Single<List<MovieEntity>> retrieveMoviesAndSaveOnSuccess(int page) {

        return fetchMovies(page).map(
                serviceMoviesRepo -> DataMapper.mapToEntity(serviceMoviesRepo.getMovies(),
                        serviceMoviesRepo.getPage())).doOnSuccess(this::saveOnDB);
    }

    private Single<ServiceMoviesRepo> fetchMovies(int page) {

        return apiService.getTopRateMovies(getQueryParams(page));
    }

    private void saveOnDB(List<MovieEntity> movieEntities) {

        MovieEntity[] movies = movieEntities.toArray(new MovieEntity[movieEntities.size()]);
        movieDao.save(movies);
    }

    private Map<String, String> getQueryParams(int pageNumber) {

        String page = String.valueOf(pageNumber);
        Map<String, String> data = new HashMap<>();
        data.put("page", page);
        data.put("language", "es");
        data.put("api_key", BuildConfig.API_KEY);

        return data;
    }
}
