package com.example.asanre.searchmoviedb.ui.base;

public interface BaseView {

    void showLoading();

    void hideLoading();

    boolean isViewAlive();

    void showErrorMessage(String errorMessage);
}
