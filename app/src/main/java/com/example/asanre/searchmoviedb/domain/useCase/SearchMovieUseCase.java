package com.example.asanre.searchmoviedb.domain.useCase;

import com.example.asanre.searchmoviedb.domain.Provider;
import com.example.asanre.searchmoviedb.domain.useCase.base.BaseUseCase;
import com.example.asanre.searchmoviedb.domain.useCase.params.SearchMovieParams;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import java.util.List;

import io.reactivex.Single;

public class SearchMovieUseCase implements BaseUseCase<Single<List<IMovie>>, SearchMovieParams> {

    @Override
    public Single<List<IMovie>> execute(SearchMovieParams params) {

        return Provider.getInstance().searchMovie(params);
    }
}
