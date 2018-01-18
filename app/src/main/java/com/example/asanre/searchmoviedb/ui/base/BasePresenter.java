package com.example.asanre.searchmoviedb.ui.base;

public abstract class BasePresenter {

    public abstract BaseView getView();

    public boolean isViewAlive() {

        return getView() != null && getView().isViewAlive();
    }
}