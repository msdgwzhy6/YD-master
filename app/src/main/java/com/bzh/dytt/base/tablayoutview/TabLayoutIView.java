package com.bzh.dytt.base.tablayoutview;

import android.support.v4.view.PagerAdapter;
public interface TabLayoutIView {

    void initContainer(PagerAdapter pagerAdapter,int limit);

    void initTabLayout();
}
