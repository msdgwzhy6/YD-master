package com.bzh.dytt.film;

import android.os.Bundle;

import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
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
