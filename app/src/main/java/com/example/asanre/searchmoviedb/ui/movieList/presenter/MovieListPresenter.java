package com.example.asanre.searchmoviedb.ui.movieList.presenter;

import com.example.asanre.searchmoviedb.domain.useCase.GetTopMovies;
import com.example.asanre.searchmoviedb.ui.base.BasePresenter;
import com.example.asanre.searchmoviedb.ui.base.BaseView;
import com.example.asanre.searchmoviedb.ui.model.IMovie;
import com.example.asanre.searchmoviedb.ui.movieList.controller.MovieListView;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListPresenter extends BasePresenter {

    private final MovieListView view;
    private GetTopMovies getTopMovies;
    private int currentPage;
    private boolean isLoadingOnDemand;

    public MovieListPresenter(MovieListView view) {

        this.view = view;
        getTopMovies = new GetTopMovies();
        currentPage = 1;
        isLoadingOnDemand = false;
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

        isLoadingOnDemand = true;
        fetchMovies(++currentPage);
    }

    public void searchMovie(String query) {

    }

    private void fetchMovies(int page) {

        view.showLoading();
        getTopMovies.execute(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<IMovie>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<IMovie> iMovies) {

                        if (isViewAlive()) {
                            onGetTopMovieSuccess(iMovies);
                        }
                    }

                    @Override
                    public void onError(Throwable error) {

                        if (isViewAlive()) {
                            handlerError(error.getMessage());
                        }
                    }
                });

    }

    void onGetTopMovieSuccess(List<IMovie> response) {

        view.setAdapterData(response);
        view.hideLoading();

        if (isLoadingOnDemand) {
            view.notifyFinishLoading();
        }
    }

    void handlerError(String errorMessage) {

        view.hideLoading();
        view.showErrorMessage(errorMessage);
    }
}
