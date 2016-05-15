package com.bzh.dytt.base.basic_pageswitch;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.rx.TaskSubscriber;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import rx.Subscriber;
import rx.functions.Action0;

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
public abstract class PagePresenter implements
        IFragmentPresenter {

    /**
     * task state
     */
    @IntDef({TASK_STATE_PREPARE, TASK_STATE_SUCCESS, TASK_STATE_FINISH, TASK_STATE_FAILED})
    public @interface TASK_STATE {
    }

    /**
     * Task preparation stage, you can update the UI.
     */
    protected static final int TASK_STATE_PREPARE = 1;

    /**
     * Task success stage, you can update the UI.
     */
    protected static final int TASK_STATE_SUCCESS = 2;

    /**
     * Task finish stage, you can update the UI.
     */
    protected static final int TASK_STATE_FINISH = 3;

    /**
     * Task fail stage, you can update the UI.
     */
    protected static final int TASK_STATE_FAILED = 4;

    protected boolean contentEmpty = true;

    /**
     * structure
     */
    protected final BaseActivity baseActivity;
    protected final BaseFragment baseFragment;
    protected final IPageView iView;

    protected Handler handlerWR = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public PagePresenter(BaseActivity baseActivity, BaseFragment baseFragment, IPageView iView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.iView = iView;
    }

    @Override
    public void initFragmentConfig() {
    }

    @Override
    public void onUserVisible() {
    }

    @Override
    public void onUserInvisible() {
    }

    @Override
    public void onFirstUserVisible() {
        onRequestData();
    }

    public void onRequestData() {
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public BaseFragment getBaseFragment() {
        return baseFragment;
    }

    public IPageView getiView() {
        return iView;
    }

    /**
     * According to the task execution state to update the page display state.
     *
     * @param tag May result in a task execution process information
     */
    public void taskStateChanged(@TASK_STATE int taskState, Serializable tag) {
        switch (taskState) {
            case TASK_STATE_PREPARE: {
                iView.layoutLoadingVisibility(isContentEmpty());
                iView.layoutContentVisibility(!isContentEmpty());
                iView.layoutEmptyVisibility(false);
                iView.layoutLoadFailedVisibility(false);
            }
            break;
            case TASK_STATE_SUCCESS: {
                iView.layoutLoadingVisibility(false);
                if (isContentEmpty()) {
                    iView.layoutEmptyVisibility(true);
                } else {
                    iView.layoutContentVisibility(true);
                }
            }
            break;
            case TASK_STATE_FAILED: {
                if (isContentEmpty()) {
                    iView.layoutEmptyVisibility(false);
                    iView.layoutLoadingVisibility(false);
                    iView.layoutLoadFailedVisibility(true);
                    if (tag != null) {
                        iView.setTextLoadFailed(tag.toString());
                    }
                }
            }
            break;
            case TASK_STATE_FINISH:
                break;
        }
    }

    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }

    public boolean isContentEmpty() {
        return contentEmpty;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Inner class
    public class AbstractTaskSubscriber<Entity> extends Subscriber<Entity> implements TaskSubscriber<Entity>, Action0 {

        @Override
        final public void onStart() {
            // no something to do
        }

        @Override
        final public void onCompleted() {
            this.onFinish();
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        @Override
        final public void onError(Throwable e) {
            this.onFailure(e);
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        @Override
        public void onNext(Entity entity) {
            this.onSuccess(entity);
        }

        @Override
        final public void call() {
            this.onPrepare();
        }

        @Override
        public void onPrepare() {
            taskStateChanged(TASK_STATE_PREPARE, null);
        }

        @Override
        public void onFinish() {
            taskStateChanged(TASK_STATE_FINISH, null);
        }

        @Override
        public void onFailure(Throwable e) {
            taskStateChanged(TASK_STATE_FAILED, e.getMessage());
        }

        @Override
        public void onSuccess(Entity entity) {
            setContentEmpty(resultIsEmpty(entity));
            taskStateChanged(TASK_STATE_SUCCESS, null);
        }

        public boolean resultIsEmpty(Entity entity) {
            return entity == null;
        }
    }
}
