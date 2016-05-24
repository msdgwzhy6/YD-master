package com.bzh.dytt.base.refresh_recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.bzh.dytt.base.basic_pageswitch.IPageView;
import com.bzh.recycler.ExRecyclerView;

public interface RefreshRecyclerView extends IPageView {

    boolean isRefreshing();

    boolean isLoadingMore();

    void initRecyclerView(RecyclerView.LayoutManager linearLayoutManager, RecyclerView.Adapter adapter);

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
