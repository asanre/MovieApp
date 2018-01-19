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
import java.util.Locale;
import java.util.Map;

import io.reactivex.Single;

import static com.example.asanre.searchmoviedb.domain.Constant.API_KEY;
import static com.example.asanre.searchmoviedb.domain.Constant.LANGUAGE;
import static com.example.asanre.searchmoviedb.domain.Constant.PAGE;
import static com.example.asanre.searchmoviedb.domain.Constant.QUERY;

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

        Single<ServiceMoviesRepo> response = apiService.getTopRateMovies(
                getDefaultQueryParams(page));

        return handleDataSource(page, response);
    }

    @Override
    public Single<List<MovieEntity>> searchMovies(String movie, int page) {

        Map<String, String> params = getDefaultQueryParams(page);
        params.put(QUERY, movie);
        Single<ServiceMoviesRepo> response = apiService.searchMovie(params);

        return handleDataSource(page, response);
    }

    private Single<List<MovieEntity>> handleDataSource(int page,
                                                       Single<ServiceMoviesRepo> response) {

        return Single.zip(retrieveMoviesAndSaveOnSuccess(response).onErrorResumeNext(throwable -> {

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

    private Single<List<MovieEntity>> retrieveMoviesAndSaveOnSuccess(
            Single<ServiceMoviesRepo> response) {

        return response.map(
                serviceMoviesRepo -> DataMapper.mapToEntity(serviceMoviesRepo.getMovies(),
                        serviceMoviesRepo.getPage())).doOnSuccess(this::saveOnDB);
    }

    private void saveOnDB(List<MovieEntity> movieEntities) {

        MovieEntity[] movies = movieEntities.toArray(new MovieEntity[movieEntities.size()]);
        movieDao.save(movies);
    }

    private Map<String, String> getDefaultQueryParams(int pageNumber) {

        String locale = Locale.getDefault() != null ? Locale.getDefault().toString() : "es";

        String page = String.valueOf(pageNumber);
        Map<String, String> data = new HashMap<>();
        data.put(PAGE, page);
        data.put(LANGUAGE, locale);
        data.put(API_KEY, BuildConfig.API_KEY);

        return data;
    }
}
