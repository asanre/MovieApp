package com.example.asanre.searchmoviedb.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter {

    public CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public abstract BaseView getView();

    public boolean isViewAlive() {

        return getView() != null && getView().isViewAlive();
    }

    public void addDisposable(Disposable disposable) {

        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public void clear() {

        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void dispose() {

        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}