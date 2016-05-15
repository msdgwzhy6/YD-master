package com.bzh.dytt.main;

import android.support.v4.view.PagerAdapter;

/**
 *MVP模式，Activity的UI逻辑抽象成View接口
 */
public interface MainIView {

    void setHeaderViewBackground(String url);

    void setHeadView(String url);

    void initToolbar(String title);

    void setTitle(String title);

    void initDrawerToggle();

    void initContainer(PagerAdapter pagerAdapter, int limit);

    void setCurrentItem(int item);

    void closeDrawer();

    void setNavigationItemSelectedListener(MainPresenter mainA);
}
