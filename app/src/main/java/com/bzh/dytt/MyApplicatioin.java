package com.bzh.dytt;

import com.bzh.common.*;
import com.bzh.common.context.GlobalContext;
import com.bzh.log.MyLog;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-17<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class MyApplicatioin extends GlobalContext {

    @Override
    public void onCreate() {
        super.onCreate();

        MyLog.init(BuildConfig.DEBUG);
    }
}
