package com.bzh.dytt.film;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;
import com.bzh.dytt.base.tablayoutview.TabLayoutPresenter;

import java.util.ArrayList;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-21<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmMainPresenter extends TabLayoutPresenter implements IFragmentPresenter {

    public static final String NEWEST_FILM = "newest_film";
    public static final String DOMESTIC_FILM = "domestic_film";
    public static final String EUROPE_AMERICA_FILM = "europe_america_film";
    public static final String JAPAN_SOUTH_KOREA_FILM = "japan_south_korea_film";

    public FilmMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {
        super(baseActivity, baseFragment, filmMainIView);
    }

    @Override
    public BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case NEWEST_FILM:
                return NewestFilmFragment.newInstance();
            case DOMESTIC_FILM:
                return DomesticFilmFragment.newInstance();
            case EUROPE_AMERICA_FILM:
                return EAFilmFragment.newInstance();
            case JAPAN_SOUTH_KOREA_FILM:
                return JSKFilmFragment.newInstance();
        }
        return NewestFilmFragment.newInstance();
    }

    @Override
    public ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(NEWEST_FILM, "最新电影"));
        items.add(new StripTabItem(DOMESTIC_FILM, "国内电影"));
        items.add(new StripTabItem(EUROPE_AMERICA_FILM, "欧美电影"));
        items.add(new StripTabItem(JAPAN_SOUTH_KOREA_FILM, "日韩电影"));
        return items;
    }
}
