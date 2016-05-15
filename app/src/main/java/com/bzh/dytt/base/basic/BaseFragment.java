package com.bzh.dytt.base.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.base.eventbus.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    private BaseActivity baseActivity;
    private FragmentConfig fragmentConfig;
    private boolean isPrepared;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isCallSetUserVisibleHint = false;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof BaseActivity) {
            baseActivity = (BaseActivity) getActivity();
        }
        fragmentConfig = new FragmentConfig();
        initUIConfig(fragmentConfig);
        if (fragmentConfig.isApplyButterKnife) {
            mUnbinder = ButterKnife.bind(this, view);
        }

        if (fragmentConfig.isApplyEventBus) {
            EventBus.getDefault().register(this);
        }

        initFragmentConfig();
    }

    /**
     * here we can do some initialized work
     */
    protected abstract void initFragmentConfig();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fragmentConfig.isApplyButterKnife) {
            mUnbinder.unbind();
        }

        if (fragmentConfig.isApplyEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initUIConfig(FragmentConfig fragmentConfig) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume && isCallSetUserVisibleHint) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCallSetUserVisibleHint = true;

        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    protected synchronized void initPrepare() {
        if (isPrepared || !isCallSetUserVisibleHint) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    protected void onEventMainThread(EventCenter eventCenter) {
        if (eventCenter != null) {
            onEventComing(eventCenter);
        }
    }
    
    protected void onEventComing(EventCenter eventCenter) {
    }

    protected abstract int getContentView();

    /**
     * when fragment is visible for the first time,  refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    protected void onFirstUserInvisible() {
        Log.d(TAG, "onFirstUserInvisible() called with: " + "");
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();

    protected final static class FragmentConfig extends UIConfig {

    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }
}
