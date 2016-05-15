package com.bzh.dytt.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bzh.common.utils.NetWorkUtil;
import com.bzh.common.utils.SharePreferenceUtil;
import com.bzh.dytt.R;
import com.bzh.dytt.video.weiboVideo.WeiboVideoBlog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VideoFragment extends BaseFragmentV implements SwipeRefreshLayout.OnRefreshListener, IVideoFragment {

    @BindView(R.id.video_progressBar)
    ProgressBar progressBar;

    @BindView(R.id.video_swipe_target)
    RecyclerView swipeTarget;

    @BindView(R.id.video_swipeToLoadLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager mLinearLayoutManager;

    private boolean loading = false;

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private Unbinder mUnbinder;

    private ArrayList<WeiboVideoBlog> mWeiboVideoBlogs = new ArrayList<>();

    private int currentPage = 1;

    private IVideoPresenter mIVideoPresenter;//多态

    private VideoAdapter videoAdapter;

    private StaggeredGridLayoutManager mLayoutManager;

    public VideoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {

        mIVideoPresenter = new VideoPresenterImpl(this, getActivity());
    }

    private void initView() {

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshLayoutColor(swipeRefreshLayout);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        mLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        
        swipeTarget.setLayoutManager(mLayoutManager);
        swipeTarget.setHasFixedSize(true);
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) //向下滚动
                {
                    visibleItemCount = mLayoutManager.getChildCount();//目前可触碰的view个数
                    totalItemCount = mLayoutManager.getItemCount();
                    
                    //这个样式适配器牵扯到列数，因为现在定义的是两列，所以参数为new int【2】。后面的0指的是左边第一列
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPositions(new int[2])[0];

                    if (!loading) {
                        //当目前可见的view个数+可见View个数的第一个大于等于总的Item个数是，刷新
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            onLoadMore();
                        }
                    }
                }
            }
        });
        
//        swipeTarget.addOnScrollListener(getOnBottomListener(layoutManager));
        
        
        videoAdapter = new VideoAdapter(getActivity(), mWeiboVideoBlogs);
        swipeTarget.setAdapter(videoAdapter);
        mIVideoPresenter.getVideoFromCache(1);
        if (SharePreferenceUtil.isRefreshOnlyWifi(getActivity())) {
            if (NetWorkUtil.isWifiConnected(getActivity())) {
                onRefresh();
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_wifi_refresh_data), Toast.LENGTH_SHORT).show();
            }
        } else {
            onRefresh();
        }
        
        //item动画
        swipeTarget.setItemAnimator(new DefaultItemAnimator());
    }
    
    

    /*private static final int PRELOAD_SIZE = 6;

    private boolean mIsFirstTimeTouchBottom = true;

    private int mPage = 1;

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager1) {

        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {

                boolean isBottom =
                        layoutManager1.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                videoAdapter.getItemCount() - PRELOAD_SIZE;
                if (!swipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        swipeRefreshLayout.setRefreshing(true);
                        mPage += 1;
                        onLoadMore();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }*/

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        mUnbinder.unbind();
        mIVideoPresenter.unsubcrible();
    }

    @Override
    public void onRefresh() {

        currentPage = 1;
        mWeiboVideoBlogs.clear();
        videoAdapter.notifyDataSetChanged();
        //这里进行数据封装
        mIVideoPresenter.getVideo(currentPage);
    }

    public void onLoadMore() {

        mIVideoPresenter.getVideo(currentPage);
    }

    @Override
    public void showProgressDialog() {

        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidProgressDialog() {

        if (swipeRefreshLayout != null) {//不加可能会崩溃
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
        }
        if (progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {

        if (swipeTarget != null) {
            mIVideoPresenter.getVideoFromCache(currentPage);
            Snackbar.make(swipeTarget, getString(R.string.common_loading_error) + error, Snackbar.LENGTH_SHORT)
                    .setAction("重试", new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    mIVideoPresenter.getVideo(currentPage);
                }
            }).show();
        }
    }

    @Override
    public void updateList(ArrayList<WeiboVideoBlog> weiboVideoBlogs) {

        currentPage++;
        mWeiboVideoBlogs.addAll(weiboVideoBlogs);
        videoAdapter.notifyDataSetChanged();
    }
}
