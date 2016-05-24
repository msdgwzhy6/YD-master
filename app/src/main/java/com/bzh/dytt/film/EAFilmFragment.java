package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;

public class EAFilmFragment extends RefreshRecyclerFragment implements BaseFilmInfoIView {

    public static EAFilmFragment newInstance() {
        Bundle args = new Bundle();
        EAFilmFragment fragment = new EAFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new EAFilmPresenterFilm(getBaseActivity(), this, this);
    }
}
