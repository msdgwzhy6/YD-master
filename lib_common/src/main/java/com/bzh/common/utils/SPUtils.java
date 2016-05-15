package com.bzh.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bzh.common.context.GlobalContext;

import java.util.HashSet;
import java.util.Set;

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
public class SPUtils {

    public static final String KEY = "com.bzh.android.sp_key";

    /**
     * 获取string，默认值为""
     */
    public static String getShareData(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 获取string
     */
    public static String getShareData(String key, String defValue) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int
     */
    public static int getIntShareData(String key, int defValue) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static int getIntShareData(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static boolean getBooleanShareData(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static boolean getBooleanShareData(String key, boolean defValue) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putShareData(String key, String value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        Editor et = sp.edit();
        et.putString(key, value);
        et.commit();
    }

    public static void putIntShareData(String key, int value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

    public static void putBooleanShareData(String key, boolean value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        Editor et = sp.edit();
        et.putBoolean(key, value);
        et.commit();
    }

    public static void putSetShareData(String key, Set<String> value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        Editor et = sp.edit();
        et.putStringSet(key, value);
        et.commit();
    }

    public static Set<String> getSetShareData(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getStringSet(key, new HashSet<String>());
    }

}
