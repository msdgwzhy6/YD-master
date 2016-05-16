package com.bzh.dytt.base.refresh_recyclerview;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IPaging;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.io.Serializable;
import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
public abstract class RefreshRecyclerPresenter<Entity, Entities>
        extends PagePresenter
        implements
        SwipeRefreshLayout.OnRefreshListener,
        ExCommonAdapter.OnItemClickListener,
        ExRecyclerView.OnLoadMoreListener {

    private static final String TAG = "REPresenter";

    private RecyclerView.LayoutManager mLinearLayoutManager;

    /**
     * The definition of a page request data mode
     */
    @IntDef({REQUEST_MODE_DATA_FIRST, REQUEST_MODE_DATA_REFRESH, REQUEST_MODE_DATA_LOAD_MORE})
    public @interface MODE_REQUEST_DATA {

    }

    /**
     * The first request data
     */
    private static final int REQUEST_MODE_DATA_FIRST = 1;

    /**
     * The refresh current page to reset data
     */
    private static final int REQUEST_MODE_DATA_REFRESH = 2;

    /**
     * Loading more data
     */
    private static final int REQUEST_MODE_DATA_LOAD_MORE = 3;

    private Observable<Entities> listObservable;

    private DefaultTaskSubscriber subscriber;

    private RefreshConfig refreshConfig;

    /**
     * structure
     */
    private final RefreshRecyclerView iView;

    private ExCommonAdapter<Entity> exCommonAdapter;

    /**
     * Paging information
     */
    private IPaging paging;

    public RefreshRecyclerPresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {

        super(baseActivity, baseFragment, iView);
        this.iView = iView;
    }

    public RecyclerView.LayoutManager getLayoutManager
            () {
        return null;
    }

    @Override
    public void initFragmentConfig() {

        super.initFragmentConfig();
        paging = configPaging();
        refreshConfig = new RefreshConfig();
        exCommonAdapter = getExCommonAdapter();
        iView.getRecyclerView().setOnItemClickListener(this);
        iView.getRecyclerView().setOnLoadingMoreListener(this);
        
        mLinearLayoutManager = getLayoutManager()==null?new LinearLayoutManager(baseActivity):getLayoutManager();
        
        iView.initRecyclerView(mLinearLayoutManager, exCommonAdapter);
        iView.getSwipeRefreshLayout().setOnRefreshListener(this);
        iView.getSwipeRefreshLayout().setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        onRefreshConfigChanged(refreshConfig);
    }

    @NonNull
    public IPaging configPaging() {

        return new DefaultPaging();
    }

    @Override
    public void onUserInvisible() {

        if (subscriber != null && subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    @Override
    public void onRequestData() {

        super.onRequestData();
        onRequestData(REQUEST_MODE_DATA_FIRST);
    }

    public void onRequestData(@MODE_REQUEST_DATA int requestMode) {

        Log.d(TAG, "onRequestData() called with: " + "requestMode = [" + getRequestModeName(requestMode) + "]");

        if (REQUEST_MODE_DATA_FIRST == requestMode || REQUEST_MODE_DATA_REFRESH == requestMode) {
            paging = configPaging();
        }

        subscriber = new DefaultTaskSubscriber(requestMode);

        if (null != paging) {
            listObservable = getRequestListDataObservable(paging.getNextPage());
            listObservable
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(subscriber)
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    private String getRequestModeName(int requestMode) {

        switch (requestMode) {
            case REQUEST_MODE_DATA_FIRST:
                return "第一次请求数据";
            case REQUEST_MODE_DATA_REFRESH:
                return "刷新数据";
            case REQUEST_MODE_DATA_LOAD_MORE:
                return "加载更多数据";
        }
        return "未知状态";
    }

    @Override
    public void onRefresh() {

        onRequestData(REQUEST_MODE_DATA_REFRESH);
    }

    @Override
    public void onLoadingMore() {

        if (refreshConfig != null && refreshConfig.canLoadMore) {
            onRequestData(REQUEST_MODE_DATA_LOAD_MORE);
        }
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {

    }

    public RefreshRecyclerView getiView() {

        return iView;
    }

    public ExCommonAdapter<Entity> getCommonAdapter() {

        return exCommonAdapter;
    }

    public IPaging getPaging() {

        return paging;
    }

    /**
     * According to the task execution state to update the page display state.
     *
     * @param tag May result in a task execution process information
     */
    public void taskStateChanged(@TASK_STATE int taskState, Serializable tag) {

        super.taskStateChanged(taskState, tag);
        if (taskState == TASK_STATE_FAILED) {
            if (!isContentEmpty()) {
                refreshConfig.canLoadMore = false;
                iView.layLoadingVisibility(false);
                iView.btnLoadMoreVisibility(true);
                iView.setTextLoadingHint(loadingHintLabel());
                iView.setTextLoadMoreHint(tag.toString());
            }
        }
        if (taskState == TASK_STATE_FINISH) {
            if (isRefreshing()) {
                onRefreshViewComplete();
            }
        }
    }

    public void onRefreshViewComplete() {

        if (iView.isRefreshing()) {
            iView.hideSwipeRefreshing();
        }
        if (iView.isLoadingMore()) {
            iView.finishLoadMore();
        }
    }

    /**
     * Whether the page is refreshing or loading state.
     */
    public boolean isRefreshing() {

        return subscriber != null && !subscriber.isUnsubscribed();
    }

    /**
     * Update the page some state when the configuration changed.
     */
    public void onRefreshConfigChanged(RefreshConfig refreshConfig) {

        iView.footerVisibility(isRefreshing());
        iView.layLoadingVisibility(refreshConfig.canLoadMore);
        iView.btnLoadMoreVisibility(!refreshConfig.canLoadMore);
        iView.setTextLoadingHint(loadingHintLabel());
        iView.setTextLoadMoreHint(loadDisabledLabel());
    }

    public String loadingHintLabel() {

        return "加载中...";
    }

    public String loadDisabledLabel() {

        return "全部都加载完了";
    }

    ///////////////////////////////////////////////////////////////////////////
    // Inner class
    public static class RefreshConfig implements Serializable {

        public boolean canLoadMore = true; // initial value must be true;
    }

    public class DefaultTaskSubscriber extends AbstractTaskSubscriber<Entities> {

        private int requestMode;

        public DefaultTaskSubscriber(int requestMode) {

            this.requestMode = requestMode;
        }


        @Override
        public void onSuccess(Entities entities) {

            super.onSuccess(entities);

            if (entities == null || exCommonAdapter == null) {
                return;
            }

            ArrayList<Entity> resultList = (ArrayList<Entity>) entities;

            if (requestMode == REQUEST_MODE_DATA_FIRST || requestMode == REQUEST_MODE_DATA_REFRESH) {
                exCommonAdapter.setData(resultList);
            } else if (requestMode == REQUEST_MODE_DATA_LOAD_MORE) {
                exCommonAdapter.addData(resultList);
            }

            // process paging
            if (null != paging) {
                paging.processData();
            }

            // process canLoadMOre
            if (requestMode == REQUEST_MODE_DATA_FIRST) {
                refreshConfig.canLoadMore = true;
            }

            try {
                if (null != paging) {
                    if (isNotCanLoadMore(paging)) {
                        refreshConfig.canLoadMore = false;
                    } else {
                        refreshConfig.canLoadMore = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                refreshConfig.canLoadMore = false;
            }

            onRefreshConfigChanged(refreshConfig);
        }

        public boolean resultIsEmpty(Entities entities) {

            return entities == null;
        }
    }

    public boolean isNotCanLoadMore(IPaging paging) {

        return Integer.valueOf(paging.getNextPage()) > Integer.valueOf(paging.getMaxPage());
    }

    public class DefaultPaging implements IPaging {

        private int mIndex = 1;

        @Override
        public String getMaxPage() {

            return RefreshRecyclerPresenter.this.getMaxPage();
        }

        @Override
        public void processData() {

            mIndex++;
        }

        @Override
        public String getNextPage() {

            return mIndex + "";
        }
    }
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // Abstract method
    public abstract ExCommonAdapter<Entity> getExCommonAdapter();

    /**
     * Maximum value of the current page request index.
     */
    public abstract String getMaxPage();

    public abstract Observable<Entities> getRequestListDataObservable(String nextPage);
    ///////////////////////////////////////////////////////////////////////////
}
