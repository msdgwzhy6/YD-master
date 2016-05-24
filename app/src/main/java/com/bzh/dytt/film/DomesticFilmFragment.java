package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

public class DomesticFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

    public static DomesticFilmFragment newInstance() {
        Bundle args = new Bundle();
        DomesticFilmFragment fragment = new DomesticFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new DomesticFilmPresenterFilm(getBaseActivity(), this, this);
    }
}
