package com.bzh.dytt.base.rx;

public interface TaskSubscriber<T> {

    /**
     * Run in the main thread, you can do some preparation work for this task.
     */
    void onPrepare();

    /**
     * Run in the main thread, you can do some finished work for this task.
     */
    void onFinish();

    /**
     * Run in the main thread, call when the error occurs, the failure of the tak
     */
    void onFailure(Throwable e);

    /**
     * Run in the main thread, when the task is successful call.
     */
    void onSuccess(T t);
}
