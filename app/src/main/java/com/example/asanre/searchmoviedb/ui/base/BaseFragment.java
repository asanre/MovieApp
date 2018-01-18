package com.example.asanre.searchmoviedb.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(getFragmentLayout(), container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        prepareView(view);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getFragmentLayout();

    protected abstract void prepareView(View view);

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

        if (getView() != null) {
            Snackbar.make(getView(), errorMessage, LENGTH_LONG).show();
        }
    }

    @Override
    public boolean isViewAlive() {

        return isAdded() && getActivity() != null && !getActivity().isFinishing();
    }
}
