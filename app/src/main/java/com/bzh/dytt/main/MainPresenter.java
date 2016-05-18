package com.bzh.dytt.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.IActivityPresenter;
import com.bzh.dytt.film.FilmMainFragment;
import com.bzh.dytt.news.NewsMainFragment;
import com.bzh.dytt.picture.PictureMainFragment;
import com.bzh.dytt.setting_about.SettingsActivity;
import com.bzh.dytt.video.VideoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * MVP模式把业务逻辑抽象成Presenter接口，相比较Model类还是原来的Model
 */
public class MainPresenter implements IActivityPresenter, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPresenter";

    public static final String FILM = "film";

    public static final String NEWS = "news";

    public static final String VIDEO = "video";


    public static final String PICTURE = "picture";


    private final BaseActivity baseActivity;

    private final MainIView iMainView;

    private InnerPageAdapter innerPageAdapter;

    private ArrayList<String> items;

    private Map<String, Fragment> fragments;

    public MainPresenter(BaseActivity baseActivity, MainIView iMainView) {

        this.baseActivity = baseActivity;
        this.iMainView = iMainView;
        fragments = new HashMap<>();
        items = new ArrayList<>();
        items.add(NEWS);        // 新闻
        items.add(FILM);      // 电影
        items.add(VIDEO);   // 视频
        items.add(PICTURE);     // 图片
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        iMainView.initToolbar("一点");
        iMainView.initDrawerToggle();
        iMainView.setNavigationItemSelectedListener(this);
        innerPageAdapter = new InnerPageAdapter(baseActivity.getSupportFragmentManager());
        iMainView.initContainer(innerPageAdapter, items.size());
//        iMainView.setHeadView("https://raw.githubusercontent.com/biezhihua/MyResource/master/biezhihua.png");
//        loadMeiZi();
    }

    private void loadMeiZi() {
/*
        Repository.getInstance().getMeiZi(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<MeiZiEntity>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<MeiZiEntity> meiZiEntities) {

                        if (meiZiEntities != null && meiZiEntities.size() > 0) {
                            iMainView.setHeaderViewBackground(meiZiEntities.get(0).getUrl());
                        }
                    }
                });*/
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_news: {
                iMainView.setCurrentItem(0);
                iMainView.setTitle("");
            }
            break;
            case R.id.nav_film: {
                iMainView.setCurrentItem(1);
                iMainView.setTitle("最热电影");
            }
            break;
            case R.id.nav_video: {
                iMainView.setCurrentItem(2);
                iMainView.setTitle("视频爽看");
            }
            break;
            case R.id.nav_picture: {
                iMainView.setCurrentItem(3);
                iMainView.setTitle("图片悦看");
            }
            break;
           /* case R.id.nav_game: {
                iMainView.setCurrentItem(4);
                iMainView.setTitle("游戏");
            }
            break;
            case R.id.nav_meizi: {
                iMainView.setCurrentItem(5);
                iMainView.setTitle("妹子");
            }
            break;*/
            case R.id.nav_setting: {
                baseActivity.startActivity(new Intent(baseActivity, SettingsActivity.class));
            }
            break;
        }
        iMainView.closeDrawer();
        return true;
    }

    private class InnerPageAdapter extends FragmentPagerAdapter {

        public InnerPageAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = fragments.get(items.get(position));
            if (fragment == null) {
                fragment = newFragment(items.get(position));
                fragments.put(items.get(position), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {

            return items.size();
        }
    }

    private Fragment newFragment(String item) {

        switch (item) {
            case FILM:
                return FilmMainFragment.newInstance();
            case VIDEO:
                return new VideoFragment();
            case PICTURE:
                return PictureMainFragment.newInstance();
            case NEWS:
                return NewsMainFragment.newInstance();
        }
        throw new RuntimeException("没有指定类型的Fragment");
    }
}
