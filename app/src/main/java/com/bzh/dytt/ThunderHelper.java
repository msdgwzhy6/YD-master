package com.bzh.dytt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.bzh.dytt.base.basic.BaseActivity;

import java.util.List;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-4-8<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class ThunderHelper {

    public static final String XUNLEI_PACKAGENAME = "com.xunlei.downloadprovider";
    private static ThunderHelper instance;
    private BaseActivity baseActivity;

    private ThunderHelper(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public static ThunderHelper getInstance(BaseActivity baseActivity) {
        if (instance == null) {
            instance = new ThunderHelper(baseActivity);
        }
        return instance;
    }

    public void onClickDownload(View view, String ftpUrl) {
        if (TextUtils.isEmpty(ftpUrl)) {
            Snackbar.make(view, baseActivity.getString(R.string.un_ftpurl_label), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        if (checkIsInstall(baseActivity, XUNLEI_PACKAGENAME)) {
            // 唤醒迅雷
            baseActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getThunderEncode(ftpUrl))));
        } else {
            Snackbar.make(view, baseActivity.getString(R.string.un_install_xunlei_label), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private boolean checkIsInstall(Context paramContext, String paramString) {
        if ((paramString == null) || ("".equals(paramString)))
            return false;
        try {
            paramContext.getPackageManager().getApplicationInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }

    private String getThunderEncode(String ftpUrl) {
        return "thunder://" + XunLeiBase64.base64encode(("AA" + ftpUrl + "ZZ").getBytes());
    }
}
