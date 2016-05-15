package com.bzh.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.IntDef;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bzh.common.context.GlobalContext;

import java.io.File;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-17<br>
 * <b>描述</b>：　　　网络/系统等信息<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
@SuppressWarnings("unused")
public class SystemUtils {

    @IntDef({NETWORK_TYPE_NONE, NETWORK_TYPE_MOBILE, NETWORK_TYPE_WIFI})
    public @interface NetWorkType {
    }

    public static final int NETWORK_TYPE_NONE = 1;
    public static final int NETWORK_TYPE_MOBILE = NETWORK_TYPE_NONE << 1;
    public static final int NETWORK_TYPE_WIFI = NETWORK_TYPE_MOBILE << 1;

    public static int getNetworkType() {

        ConnectivityManager connMgr = (ConnectivityManager) GlobalContext.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    return NETWORK_TYPE_MOBILE;
                case ConnectivityManager.TYPE_WIFI:
                    return NETWORK_TYPE_WIFI;
            }
        }
        return NETWORK_TYPE_NONE;
    }

    /**
     * mac地址
     */
    public static String getMacAddress() {
        WifiManager wifiManager = (WifiManager) GlobalContext.getInstance().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getMacAddress() != null)
            return wifiInfo.getMacAddress().replace(":", "");
        else
            return "0022f420d03f";// 00117f29d23a
    }

    public static String getUDPIP() {
        Context context = GlobalContext.getInstance();

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi.getDhcpInfo();
        int IpAddress = dhcpInfo.ipAddress;
        int subMask = dhcpInfo.netmask;
        return transformIp((~subMask) | IpAddress);
    }

    private static String transformIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    public static String getIP() {
        Context context = GlobalContext.getInstance();

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return transformIp(wifi.getConnectionInfo().getIpAddress());
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception ignored) {
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception ignored) {
        }
        return 0;
    }

    public static String getPackage(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception ignored) {
        }
        return "";
    }

    public static void scanPhoto(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        GlobalContext.getInstance().sendBroadcast(intent);
    }

    public static void hideSoftInput(View paramEditText) {
        ((InputMethodManager) GlobalContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    public static void showKeyBoard(final View paramEditText) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) GlobalContext.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
            }
        });
    }

    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getStatusBarHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;

    }

    public static int getActionBarHeight(Activity paramActivity) {
        // if (true) return UIUtils.dip2px(56);

        // test on samsung 9300 android 4.1.2, this value is 96px
        // but on galaxy nexus android 4.2, this value is 146px
        // statusbar height is 50px
        // I guess 4.1 Window.ID_ANDROID_CONTENT contain statusbar
        // int contentViewTop = paramActivity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

        //  return contentViewTop - getStatusBarHeight(paramActivity);

        int[] attrs = new int[]{android.R.attr.actionBarSize};
        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
        int dimensionPixelSize = ta.getDimensionPixelSize(0, UIUtils.dip2px(56));
        ta.recycle();
        return dimensionPixelSize;
    }



    // below status bar,include actionbar, above softkeyboard
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    // below actionbar, above softkeyboard
    public static int getAppContentHeight(Activity paramActivity) {
        return SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getActionBarHeight(paramActivity) - SystemUtils.getKeyboardHeight(paramActivity);
    }

    public static int getKeyboardHeight(Activity paramActivity) {

        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        if (height == 0) {
            height = SPUtils.getIntShareData("KeyboardHeight", 400);
        } else {
            SPUtils.putIntShareData("KeyboardHeight", height);
        }


        return height;
    }

    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        return height != 0;
    }
}
