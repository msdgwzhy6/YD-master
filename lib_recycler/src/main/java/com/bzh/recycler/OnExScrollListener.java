package com.bzh.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　15-12-11 <br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：　<br>
 * ========================================================== <br>
 */
public class OnExScrollListener extends RecyclerView.OnScrollListener {

    private enum LayoutManagerType {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    protected LayoutManagerType yLayoutManagerType;

    private int[] yLastPositions;

    private int yLastVisibleItemPosition;

    private int yFirstVisibleItemPosition;

    private static final int HIDE_THRESHOLD = 20;

    private int yDistance = 0;

    private boolean yIsScrollDown = false;

    private int yScrolledYDistance = 0;

    private int yScrolledXDistance = 0;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        judgeLayoutManager(layoutManager);

        yFirstVisibleItemPosition = calculateFirstVisibleItemPos(layoutManager);

        calculateScrollUpOrDown(yFirstVisibleItemPosition, dy);

        yScrolledXDistance += dx;
        yScrolledYDistance += dy;

        yScrolledXDistance = (yScrolledXDistance < 0) ? 0 : yScrolledXDistance;
        yScrolledYDistance = (yScrolledYDistance < 0) ? 0 : yScrolledYDistance;

        onMoved(yScrolledXDistance, yScrolledYDistance);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();

        int totalItemCount = layoutManager.getItemCount();

        int index = 1;

        if (recyclerView instanceof ExRecyclerView) {
            if (((ExRecyclerView) recyclerView).getHeaderView() != null) {
                index = 2;
            }
        }

        if (yIsScrollDown
                && visibleItemCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && yLastVisibleItemPosition >= totalItemCount - index) {
            onBottom();
        } else if (visibleItemCount > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && recyclerView.getY() >= 0
                && recyclerView.getY() <= HIDE_THRESHOLD) {
            onTop();
        }

    }

    /**
     * 判断layout manageer的类型
     */
    private void judgeLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            yLayoutManagerType = LayoutManagerType.GRID;
        } else if (layoutManager instanceof LinearLayoutManager) {
            yLayoutManagerType = LayoutManagerType.LINEAR;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            yLayoutManagerType = LayoutManagerType.STAGGERED_GRID;
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are " + "LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
        }
    }

    /**
     * 根据类型来计算出第一个可见的item的位置，由此判断是否触发到底部的监听器
     */
    private int calculateFirstVisibleItemPos(RecyclerView.LayoutManager layoutManager) {

        int firstVisibleItemPosition = 0;
        switch (yLayoutManagerType) {
            case LINEAR:
                yLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case GRID:
                yLastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (yLastPositions == null) {
                    yLastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                yLastPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(yLastPositions);
                yLastVisibleItemPosition = findMax(yLastPositions);
                staggeredGridLayoutManager.findFirstVisibleItemPositions(yLastPositions);
                firstVisibleItemPosition = findMax(yLastPositions);
                break;

            default:
                break;
        }
        return firstVisibleItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (firstVisibleItemPosition == 0 && dy < -HIDE_THRESHOLD) {
            onScrollUp();
            yIsScrollDown = false;
        } else {
            if (yDistance > HIDE_THRESHOLD) {
                onScrollDown();
                yIsScrollDown = true;
                yDistance = 0;
            } else if (yDistance < -HIDE_THRESHOLD) {
                onScrollUp();
                yIsScrollDown = false;
                yDistance = 0;
            }
        }
        yDistance += dy;
    }


    /**
     * 滚动到顶部
     */
    public void onTop() {
    }

    /**
     * 向上滚动
     */
    public void onScrollUp() {
    }

    /**
     * 向下滚动
     */
    public void onScrollDown() {
    }

    /**
     * 滚动到底部
     */
    public void onBottom() {
    }

    /**
     * 正在滚动
     */
    public void onMoved(int distanceX, int distanceY) {
    }
}
