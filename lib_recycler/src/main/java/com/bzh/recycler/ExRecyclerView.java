package com.bzh.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.lib_recycler.R;

/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　15-12-11 <br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：
 * 1. 始终有默认的头和尾
 * 　<br>
 * ========================================================== <br>
 */
public class ExRecyclerView extends RecyclerView {

    private ExCommonAdapter.OnItemLongClickListener onItemLongClickListener;

    private ExCommonAdapter.OnItemClickListener onItemClickListener;

    private boolean isLoadingMore;

    private OnLoadMoreListener onLoadingMoreListener;

    private View headerView;

    private View footerView;

    public ExRecyclerView(Context context) {

        this(context, null);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        initDefaultFooterView(context);
        this.addOnScrollListener(new OnExScrollListener() {

            @Override
            public void onBottom() {

                super.onBottom();
                if (onLoadingMoreListener != null) {
                    onLoadMore(onLoadingMoreListener);
                }
            }
        });
    }

    @Override
    public void setLayoutManager(final LayoutManager layoutManager) {

        super.setLayoutManager(layoutManager);
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        } else if (layoutManager instanceof ExStaggeredGridLayoutManager) {
            ExStaggeredGridLayoutManager lm = (ExStaggeredGridLayoutManager) layoutManager;
            lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
        }
    }

    private void initDefaultFooterView(Context context) {

        footerView = LayoutInflater.from(context).inflate(R.layout.comm_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
    }


    public void addFooterView(View footerView) {

        this.footerView = footerView;
    }

    public void addHeaderView(View headerView) {

        this.headerView = headerView;
    }

    @Override
    public void setAdapter(Adapter adapter) {

        super.setAdapter(adapter);
        if (adapter instanceof ExCommonAdapter) {
            ((ExCommonAdapter) adapter).setOnItemClickListener(onItemClickListener);
            ((ExCommonAdapter) adapter).setOnItemLongClickListener(onItemLongClickListener);
            ((ExCommonAdapter) adapter).setHeaderView(headerView);
            ((ExCommonAdapter) adapter).setFooterView(footerView);
        }
    }

    private class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private int mSpanSize = 1;

        public GridSpanSizeLookup(int spanSize) {

            mSpanSize = spanSize;
        }

        @Override
        public int getSpanSize(int position) {

            ExCommonAdapter adapter = null;
            if (getAdapter() instanceof ExCommonAdapter) {
                adapter = (ExCommonAdapter) getAdapter();
            }

            if (adapter != null &&
                    adapter.getItemViewType(position) == ExCommonAdapter.VIEW_TYPES_HEADER ||
                    adapter.getItemViewType(position) == ExCommonAdapter.VIEW_TYPES_FOOTER ||
                    adapter.getItemViewType(position) == ExCommonAdapter.VIEW_TYPES_AD) {
                return mSpanSize;
            }

            return 1;
        }
    }

    /**
     * 执行加载更多的逻辑，需要传入加载更多时加载数据的监听
     */
    public void onLoadMore(final OnLoadMoreListener onLoadingMoreListener) {

        if (footerView != null && !isLoadingMore) {
            isLoadingMore = true;
            footerView.postDelayed(new Runnable() {

                @Override
                public void run() {

                    onLoadingMoreListener.onLoadingMore();
                }
            }, 300);
        }
    }

    public void finishLoadingMore() {

        if (footerView != null && isLoadingMore) {
            isLoadingMore = false;
        }
    }

    public boolean isLoadingMore() {

        return isLoadingMore;
    }

    public void setOnItemLongClickListener(ExCommonAdapter.OnItemLongClickListener mOnItemLongClickListener) {

        this.onItemLongClickListener = mOnItemLongClickListener;
    }

    public void setOnItemClickListener(ExCommonAdapter.OnItemClickListener mOnItemClickListener) {

        this.onItemClickListener = mOnItemClickListener;
    }

    public void setOnLoadingMoreListener(OnLoadMoreListener onLoadingMoreListener) {

        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    public View getFooterView() {

        return footerView;
    }

    public interface OnLoadMoreListener {

        void onLoadingMore();
    }

    public View getHeaderView() {

        return headerView;
    }

    @Override
    public boolean canScrollVertically(int direction) {

        if (direction < 1) {
            return !(!super.canScrollVertically(direction) || getChildAt(0) != null && getChildAt(0).getTop() >= 0);
        }
        return super.canScrollVertically(direction);
    }
}
