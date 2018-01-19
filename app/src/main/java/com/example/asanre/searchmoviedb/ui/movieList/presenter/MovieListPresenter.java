package com.example.asanre.searchmoviedb.ui.movieList.presenter;

import android.text.TextUtils;

import com.example.asanre.searchmoviedb.domain.useCase.GetTopMovies;
import com.example.asanre.searchmoviedb.domain.useCase.SearchMovieUseCase;
import com.example.asanre.searchmoviedb.domain.useCase.params.SearchMovieParams;
import com.example.asanre.searchmoviedb.ui.base.BasePresenter;
import com.example.asanre.searchmoviedb.ui.base.BaseView;
import com.example.asanre.searchmoviedb.ui.model.IMovie;
import com.example.asanre.searchmoviedb.ui.movieList.controller.MovieListView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends BasePresenter {

    private final MovieListView view;
    private GetTopMovies getTopMovies;
    private SearchMovieUseCase searchMovie;
    private int currentPage;
    private String movieQuery;

    public MovieListPresenter(MovieListView view) {

        this.view = view;
        getTopMovies = new GetTopMovies();
        searchMovie = new SearchMovieUseCase();
        currentPage = 1;
        movieQuery = "";
    }

    @Override
    public BaseView getView() {

        return view;
    }

    /**
     * fetch top movies page 1
     */
    public void loadMovies() {

        fetchMovies(currentPage);
    }

    /**
     * fetch more movies increasing page
     * number according to pagination
     */
    public void fetchMoreMovies() {

        int page = ++currentPage;

        if (TextUtils.isEmpty(movieQuery)) {
            fetchMovies(page);
        } else {
            loadSimilarMovie(movieQuery, page);
        }
    }

    public void searchMovie(String query) {

        movieQuery = query;
        if (!TextUtils.isEmpty(query)) {
            prepareSearch();
            loadSimilarMovie(query, currentPage);
        }
    }

    private void prepareSearch() {

        currentPage = 1;
        view.clear();
        clear();
    }

    private void loadSimilarMovie(String query, int page) {

        view.showLoading();
        addDisposable(searchMovie.execute(new SearchMovieParams(query, page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<IMovie>>() {

                    @Override
                    public void onSuccess(List<IMovie> iMovies) {

                        if (isViewAlive()) {
                            successHandler(iMovies);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {

                        if (isViewAlive()) {
                            handlerError(error.getMessage());
                        }
                    }
                }));
    }

    private void fetchMovies(int page) {

        view.showLoading();
        addDisposable(getTopMovies.execute(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<IMovie>>() {

                    @Override
                    public void onSuccess(List<IMovie> iMovies) {

                        if (isViewAlive()) {
                            successHandler(iMovies);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {

                        if (isViewAlive()) {
                            handlerError(error.getMessage());
                        }
                    }
                }));

    }

    void successHandler(List<IMovie> response) {

        view.setAdapterData(response);
        view.hideLoading();
        view.notifyFinishLoading();
    }

    void handlerError(String errorMessage) {

        view.hideLoading();
        view.showErrorMessage(errorMessage);
    }
}
