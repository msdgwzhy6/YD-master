package com.bzh.dytt.base.tablayoutview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public abstract class TabLayoutPresenter implements IFragmentPresenter {

    private final BaseActivity baseActivity;

    private final BaseFragment baseFragment;

    private final TabLayoutIView tabLayoutIView;

    private ArrayList<StripTabItem> mItems;

    private Map<String, BaseFragment> fragments;

    private MyViewPagerAdapter myViewPagerAdapter;

    public TabLayoutPresenter(BaseActivity baseActivity, BaseFragment baseFragment, TabLayoutFragment filmMainIView) {

        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.tabLayoutIView = filmMainIView;
        mItems = generateTabs();
        fragments = new HashMap<>();
    }

    @Override
    public void initFragmentConfig() {

        myViewPagerAdapter = new MyViewPagerAdapter(baseFragment.getChildFragmentManager());
        tabLayoutIView.initContainer(myViewPagerAdapter);
        tabLayoutIView.initTabLayout();
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    public abstract BaseFragment newFragment(StripTabItem stripTabItem);

    public abstract ArrayList<StripTabItem> generateTabs();

    public String makeFragmentName(int position) {

        return mItems.get(position).getTitle();
    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            BaseFragment fragment = fragments.get(makeFragmentName(position));
            if (fragment == null) {
                fragment = newFragment(mItems.get(position));
                fragments.put(makeFragmentName(position), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {

            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mItems.get(position).getTitle();
        }
    }

    public static class StripTabItem {

        private String type;

        private String title;

        private Serializable tag;

        public StripTabItem() {

        }

        public StripTabItem(String type, String title) {

            this.type = type;
            this.title = title;
        }

        public String getType() {

            return type;
        }

        public void setType(String type) {

            this.type = type;
        }

        public String getTitle() {

            return title;
        }

        public void setTitle(String title) {

            this.title = title;
        }

        public Serializable getTag() {

            return tag;
        }

        public void setTag(Serializable tag) {

            this.tag = tag;
        }
    }
}
