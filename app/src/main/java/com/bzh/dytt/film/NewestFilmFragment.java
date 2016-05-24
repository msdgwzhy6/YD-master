package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

public class NewestFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

    public static NewestFilmFragment newInstance() {
        Bundle args = new Bundle();
        NewestFilmFragment fragment = new NewestFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new NewestFilmPresenterFilm(getBaseActivity(), this, this);
    }
}
