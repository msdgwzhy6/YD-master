package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;

public class FilmMainFragment extends TabLayoutFragment implements FilmMainIView {

    public static FilmMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FilmMainFragment fragment = new FilmMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected void initFragmentConfig() {
        tabLayoutPresenter = new FilmMainPresenter(getBaseActivity(), this, this);
        tabLayoutPresenter.initFragmentConfig();
    }
}
