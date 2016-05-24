package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

public class JSKFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

    public static JSKFilmFragment newInstance() {
        Bundle args = new Bundle();
        JSKFilmFragment fragment = new JSKFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new JSKFilmPresenterFilm(getBaseActivity(), this, this);
    }
}
