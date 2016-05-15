package com.bzh.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 */
public class SharePreferenceUtil {

    public static final String SHARED_PREFERENCE_NAME = "micro_reader";

    public static final String IMAGE_DESCRIPTION = "image_description";

    public static final String VIBRANT = "vibrant";

    public static final String MUTED = "muted";

    public static final String IMAGE_GET_TIME = "image_get_time";

    public static final String SAVED_CHANNEL = "saved_channel";

    public static boolean isRefreshOnlyWifi(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("refresh_data", false);
    }

    public static boolean isChangeThemeAuto(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("get_image", true);
    }

    public static boolean isImmersiveMode(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("pre_status_bar", true);
    }

    public static boolean isChangeNavColor(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("pre_nav_color", true);
    }

    public static boolean isUseLocalBrowser(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("pre_use_local", false);
    }
}
