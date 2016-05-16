package com.bzh.dytt;

import com.bzh.common.context.GlobalContext;
import com.bzh.dytt.base.basic.BaseAppManager;
import com.bzh.log.MyLog;
import com.umeng.analytics.MobclickAgent;

/**
 * ==========================================================<br>
 * ==========================================================<br>
 */
public class MyApplicatioin extends GlobalContext {

    @Override
    public void onCreate() {
        super.onCreate();

        MyLog.init(BuildConfig.DEBUG);
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        MobclickAgent.onKillProcess(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
