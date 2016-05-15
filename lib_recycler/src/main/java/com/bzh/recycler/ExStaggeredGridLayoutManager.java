package com.bzh.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 不规则排列（类似于瀑布流）的布局管理器
 */
public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

    GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    /**
     * 设置某个位置的item的跨列程度，这里和GridLayoutManager有点不一样，
     * 如果你设置某个位置的item的span>1了，那么这个item会占据所有列
     */
    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        // 只判断第一个item和最后一个item
        if (mSpanSizeLookup.getSpanSize(0) > 1) {
            try {
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                    lp.setFullSpan(true);
                }
            } catch (Exception ignored) {
            }
        }
        if (mSpanSizeLookup.getSpanSize(getItemCount() - 1) > 1) {
            try {
                View view = recycler.getViewForPosition(getItemCount() - 1);
                if (view != null) {
                    StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                    lp.setFullSpan(true);
                }
            } catch (Exception ignored) {
            }
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }
}
