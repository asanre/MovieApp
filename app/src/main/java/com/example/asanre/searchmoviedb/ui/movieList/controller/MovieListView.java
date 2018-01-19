package com.example.asanre.searchmoviedb.ui.movieList.controller;

import com.example.asanre.searchmoviedb.ui.base.BaseView;
import com.example.asanre.searchmoviedb.ui.model.IMovie;

import java.util.List;

public interface MovieListView extends BaseView {

    void setAdapterData(List<IMovie> movies);

    void notifyFinishLoading();

    void clear();
}
