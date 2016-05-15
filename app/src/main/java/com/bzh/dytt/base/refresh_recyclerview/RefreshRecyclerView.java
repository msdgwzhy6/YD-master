package com.bzh.dytt.base.refresh_recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bzh.dytt.base.basic.BaseView;
import com.bzh.dytt.base.basic_pageswitch.IPageView;
import com.bzh.recycler.ExRecyclerView;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-22<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface RefreshRecyclerView extends IPageView {

    boolean isRefreshing();

    boolean isLoadingMore();

    void initRecyclerView(LinearLayoutManager linearLayoutManager, RecyclerView.Adapter adapter);

    void footerVisibility(boolean isVisible);

    void btnLoadMoreVisibility(boolean isVisible);

    void layLoadingVisibility(boolean isVisible);

    void setTextLoadingHint(String content);

    void setTextLoadMoreHint(String content);

    void showSwipeRefreshing();

    void hideSwipeRefreshing();

    ExRecyclerView getRecyclerView();

    SwipeRefreshLayout getSwipeRefreshLayout();

    void finishLoadMore();

}
