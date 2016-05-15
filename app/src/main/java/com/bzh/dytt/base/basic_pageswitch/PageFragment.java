package com.bzh.dytt.base.basic_pageswitch;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseFragment;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;

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
public abstract class PageFragment extends BaseFragment implements IPageView {

    private static final String TAG = "PageFragment";

    @BindView(R.id.layoutLoading)
    LinearLayout layoutLoading;

    @BindView(R.id.layoutLoadFailed)
    LinearLayout layoutLoadFailed;

    @BindView(R.id.txtLoadFailed)
    TextView txtLoadFailed;

    @BindView(R.id.layoutEmpty)
    LinearLayout layoutEmpty;

    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;

    private PagePresenter pagePresenter;

    @Override
    protected void initFragmentConfig() {
        pagePresenter = initPresenter();
        pagePresenter.initFragmentConfig();
    }

    protected abstract PagePresenter initPresenter();

    @Override
    protected void onFirstUserVisible() {
        pagePresenter.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        pagePresenter.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        pagePresenter.onUserInvisible();
    }

    @Override
    public void layoutLoadingVisibility(boolean isVisible) {
        if (layoutLoading != null) {
            RxView.visibility(layoutLoading, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutLoadFailedVisibility(boolean isVisible) {
        if (layoutLoadFailed != null) {
            RxView.visibility(layoutLoadFailed, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutEmptyVisibility(boolean isVisible) {
        if (layoutEmpty != null) {
            RxView.visibility(layoutEmpty, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutContentVisibility(boolean isVisible) {
        if (layoutContent != null) {
            RxView.visibility(layoutContent, View.GONE).call(isVisible);
        }
    }

    @Override
    public void setTextLoadFailed(String content) {
        if (txtLoadFailed != null) {
            if (!TextUtils.isEmpty(content)) {
                txtLoadFailed.setText(content);
            } else {
                Log.i(TAG, "setTextLoadFailed: content is empty");
            }
        }
    }

}
