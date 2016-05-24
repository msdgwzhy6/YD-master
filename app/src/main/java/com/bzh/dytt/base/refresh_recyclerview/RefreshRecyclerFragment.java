package com.bzh.dytt.base.refresh_recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bzh.common.utils.UIUtils;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic_pageswitch.PageFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;
import com.bzh.recycler.ExRecyclerView;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;

public abstract class RefreshRecyclerFragment extends PageFragment implements RefreshRecyclerView {

    private static final String TAG = "PageFragment";

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    ExRecyclerView recyclerView;

    @Override
    protected void initFragmentConfig() {
        super.initFragmentConfig();
    }

    @Override
    protected PagePresenter initPresenter() {
        return initRefreshRecyclerPresenter();
    }

    protected abstract RefreshRecyclerPresenter initRefreshRecyclerPresenter();

    @Override
    protected int getContentView() {
        return R.layout.comm_lay_refresh_recyclerview;
    }

    @Override
    public void btnLoadMoreVisibility(boolean isVisible) {
        if (recyclerView != null && recyclerView.getFooterView() != null && recyclerView.getFooterView().findViewById(R.id.btnLoadMore) != null) {
            RxView.visibility(recyclerView.getFooterView().findViewById(R.id.btnLoadMore), View.GONE).call(isVisible);
        }
    }

    @Override
    public void layLoadingVisibility(boolean isVisible) {
        if (recyclerView != null && recyclerView.getFooterView() != null && recyclerView.getFooterView().findViewById(R.id.layLoading) != null) {
            RxView.visibility(recyclerView.getFooterView().findViewById(R.id.layLoading), View.GONE).call(isVisible);
        }
    }

    @Override
    public void setTextLoadingHint(String content) {
        if (recyclerView != null && recyclerView.getFooterView() != null && recyclerView.getFooterView().findViewById(R.id.txtLoadingHint) != null) {
            if (!TextUtils.isEmpty(content)) {
                ((TextView) recyclerView.getFooterView().findViewById(R.id.txtLoadingHint)).setText(content);
            }
        }
    }


    @Override
    public void footerVisibility(boolean isVisible) {
        if (recyclerView != null && recyclerView.getFooterView() != null) {
            RxView.visibility(recyclerView.getFooterView(), View.GONE).call(isVisible);
        }
    }

    @Override
    public void setTextLoadMoreHint(String content) {
        if (recyclerView != null && recyclerView.getFooterView().findViewById(R.id.btnLoadMore) != null) {
            if (!TextUtils.isEmpty(content)) {
                ((TextView) recyclerView.getFooterView().findViewById(R.id.btnLoadMore)).setText(content);
            }
        }
    }

    @Override
    public void showSwipeRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideSwipeRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean isRefreshing() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        }
        return false;
    }

    @Override
    public boolean isLoadingMore() {
        return recyclerView != null && recyclerView.isLoadingMore();
    }

    @Override
    public void initRecyclerView(RecyclerView.LayoutManager linearLayoutManager, RecyclerView.Adapter adapter) {
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new MyItemDecoration());
            recyclerView.setAdapter(adapter);
        }
    }

    public ExRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void finishLoadMore() {
        recyclerView.finishLoadingMore();
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, UIUtils.dip2px(4));
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }
    }

}
