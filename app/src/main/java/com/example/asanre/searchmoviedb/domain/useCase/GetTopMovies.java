package com.example.asanre.searchmoviedb.domain.useCase;

import android.support.annotation.NonNull;

import com.example.asanre.searchmoviedb.domain.Provider;
import com.example.asanre.searchmoviedb.domain.useCase.base.BaseUseCase;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import java.util.List;

import io.reactivex.Single;

public class GetTopMovies implements BaseUseCase<Single<List<IMovie>>, Integer> {

    @Override
    public Single<List<IMovie>> execute(@NonNull Integer page) {

        return Provider.getInstance().getTopMovies(page);
    }
}
