package com.bzh.data.exception;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-14<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class TaskException extends RuntimeException {

    public static final String LABEL_UNKNOWN = "未知错误";
    public static final String LABEL_NONE_NETWORK = "没有网络连接";
    public static final String LABEL_TIMEOUT = "网络不给力";
    public static final String LABEL_RESULT_ILLEGAL = "数据解析出错";
    public static final String LABEL_HTML_PARSE = "Html网页解析错误";

    @StringDef({ERROR_NONE_NETWORK, ERROR_TIMEOUT, ERROR_RESULT_ILLEGAL, ERROR_HTML_PARSE, ERROR_IO, ERROR_UNKNOWN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DATA_LAYER_ERROR {
    }

    public static final int ERROR_STATUS_DEFAULT = 1;
    public static final String ERROR_NONE_NETWORK = (ERROR_STATUS_DEFAULT << 1) + "";
    public static final String ERROR_TIMEOUT = (ERROR_STATUS_DEFAULT << 2) + "";
    public static final String ERROR_RESULT_ILLEGAL = (ERROR_STATUS_DEFAULT << 3) + "";
    public static final String ERROR_HTML_PARSE = (ERROR_STATUS_DEFAULT << 4) + "";
    public static final String ERROR_IO = (ERROR_STATUS_DEFAULT << 5) + "";
    public static final String ERROR_UNKNOWN = (ERROR_STATUS_DEFAULT << 6) + "";

    private String code;

    private String msg;

    private static IExceptionDeclare exceptionDeclare;

    public TaskException(@DATA_LAYER_ERROR String code) {
        this.code = code;
    }

    public TaskException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {

        if (!TextUtils.isEmpty(msg)) {
            return msg;
        }

        if (!TextUtils.isEmpty(code) && exceptionDeclare != null) {
            String msg = exceptionDeclare.declareMessage(code);
            if (!TextUtils.isEmpty(msg)) {
                return msg;
            }
        }

        switch (code) {
            case ERROR_NONE_NETWORK:
                return LABEL_NONE_NETWORK;
            case ERROR_TIMEOUT:
                return LABEL_TIMEOUT;
            case ERROR_RESULT_ILLEGAL:
                return LABEL_RESULT_ILLEGAL;
            case ERROR_IO:
            case ERROR_HTML_PARSE:
                return LABEL_HTML_PARSE;
        }

        return super.getMessage();
    }

    public static void config(IExceptionDeclare declare) {
        TaskException.exceptionDeclare = declare;
    }
}
