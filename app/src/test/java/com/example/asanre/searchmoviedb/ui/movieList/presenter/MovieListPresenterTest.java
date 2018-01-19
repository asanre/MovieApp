package com.example.asanre.searchmoviedb.ui.movieList.presenter;

import android.text.TextUtils;

import com.example.asanre.searchmoviedb.domain.Provider;
import com.example.asanre.searchmoviedb.domain.useCase.GetTopMovies;
import com.example.asanre.searchmoviedb.domain.useCase.SearchMovieUseCase;
import com.example.asanre.searchmoviedb.ui.model.IMovie;
import com.example.asanre.searchmoviedb.ui.movieList.controller.MovieListView;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MovieListView.class, SearchMovieUseCase.class, GetTopMovies.class, Provider.class, GetTopMovies.class, SearchMovieUseCase.class, MovieListPresenter.class, TextUtils.class})
public class MovieListPresenterTest {

    @Mock
    private MovieListView view;

    private MovieListPresenter spy;

    @BeforeClass
    public static void setUpClass() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Before
    public void setup() {

        spy = PowerMockito.spy(new MovieListPresenter(view));
    }

    @Test
    public void when_fetchMoreMoviesWithQuerySearch_then_loadSimilarMovies() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenReturn(false);
        mockSearchMovie();
        spy.fetchMoreMovies();
        PowerMockito.verifyPrivate(spy, times(1)).invoke("loadSimilarMovie", anyString(), anyInt());

    }

    @Test
    public void when_fetchMoreMoviesWithEmptySearch_then_fetchMoviesIsInvoke() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenReturn(true);

        GetTopMovies useCase = PowerMockito.mock(GetTopMovies.class);
        when(useCase.execute(any())).thenReturn(Single.just(new ArrayList<>()));
        Whitebox.setInternalState(spy, "getTopMovies", useCase);

        spy.fetchMoreMovies();
        PowerMockito.verifyPrivate(spy, times(1)).invoke("fetchMovies", anyInt());

    }

    @Test
    public void given_emptyQueryMovie_then_shouldNotSearchMovie() throws Exception {

        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenReturn(true);

        spy.searchMovie(anyString());
        verifyZeroInteractions(view);
        PowerMockito.verifyPrivate(spy, times(0)).invoke("loadSimilarMovie", anyString(), anyInt());
    }

    @Test
    public void given_SearchMovieQuery_then_clearViewDataAndSearchMovie() throws Exception {

        final String empty = "matrix";
        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(empty)).thenReturn(false);

        mockSearchMovie();

        spy.searchMovie(empty);
        verify(view).clear();
        PowerMockito.verifyPrivate(spy, times(1)).invoke("loadSimilarMovie", anyString(), anyInt());
    }

    @Test
    public void given_SuccessRequest_then_updateListData() throws Exception {

        List<IMovie> movies = new ArrayList<>();
        spy.successHandler(movies);
        verify(view).setAdapterData(movies);
        verify(view).notifyFinishLoading();
        verify(view).hideLoading();
    }

    @Test
    public void given_ErrorRequest_then_showErrorMessage() throws Exception {

        String error = "server error";
        spy.handlerError(error);

        verify(view).hideLoading();
        verify(view).showErrorMessage(error);
    }

    private void mockSearchMovie() {

        SearchMovieUseCase useCase = PowerMockito.mock(SearchMovieUseCase.class);
        when(useCase.execute(any())).thenReturn(Single.just(new ArrayList<>()));
        Whitebox.setInternalState(spy, "searchMovie", useCase);
    }

}