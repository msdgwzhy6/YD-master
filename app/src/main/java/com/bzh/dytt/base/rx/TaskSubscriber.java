package com.bzh.dytt.base.rx;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-23<br>
 * <b>描述</b>：　　　General network task observer, the task execution status feedback to the user. <br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
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
