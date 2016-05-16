package com.bzh.dytt.base.tablayoutview;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.SharePreferenceUtil;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseFragment;

import butterknife.BindView;

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
public abstract class TabLayoutFragment extends BaseFragment implements TabLayoutIView {

    protected TabLayoutPresenter tabLayoutPresenter;

    @BindView(R.id.tabLayout)
    protected
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    protected
    ViewPager container;

    @Override
    protected int getContentView() {

        return R.layout.comm_tab_layout;
    }

//    @Override
//    protected void initFragmentConfig() {
//        tabLayoutPresenter.initFragmentConfig();
//    }

    @Override
    protected void onFirstUserVisible() {

        tabLayoutPresenter.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {

        tabLayoutPresenter.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {

        tabLayoutPresenter.onUserInvisible();
    }

    @Override
    public void initContainer(PagerAdapter pagerAdapter) {

        container.setOffscreenPageLimit(0);
        container.setAdapter(pagerAdapter);
    }

    @Override
    public void initTabLayout() {

        //scroll适合很多tabs的情况
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(Color.parseColor("#b3ffffff"), Color.WHITE);
        tabLayout.setupWithViewPager(container);
        
        setTabColor(tabLayout);
    }

    public void setTabColor(TabLayout tabLayout) {

        int vibrantColor = GlobalContext.getInstance().
                getSharedPreferences(SharePreferenceUtil
                        .SHARED_PREFERENCE_NAME, Context
                        .MODE_PRIVATE)
                .getInt(SharePreferenceUtil.VIBRANT, 0);

        if (tabLayout != null) {
            tabLayout.setBackgroundColor(vibrantColor);
        }
    }
}
