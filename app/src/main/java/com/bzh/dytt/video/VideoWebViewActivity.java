package com.bzh.dytt.video;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bzh.dytt.R;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class VideoWebViewActivity extends BaseActivityV {

    @BindView(R.id.wv_video)
    WebView wvVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_webview);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");

        WebSettings webSettings = wvVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//可任意比例缩放
        webSettings.setBuiltInZoomControls(true);//隐藏缩放按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        wvVideo.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlTo) {
                //处理自动跳转到浏览器的问题
                view.loadUrl(urlTo);
                return true;
            }
        });
        wvVideo.setWebChromeClient(new WebChromeClient() {

            //显示全屏按钮
            private View myView = null;

            private CustomViewCallback myCallback = null;

            //播放网络视频全屏会被调用的方法
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                //如果一个视图已经存在，那么立刻终止并新建一个
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                    return;
                }
                ViewGroup parent = (ViewGroup) wvVideo.getParent();
                parent.removeView(wvVideo);
                parent.addView(view);
                myView = view;
                myCallback = callback;
            }

            //视频播放退出全屏会被调用
            @Override
            public void onHideCustomView() {

                if (myView != null) {
                    if (myCallback != null) {
                        myCallback.onCustomViewHidden();
                        myCallback = null;
                    }
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);
                    parent.addView(wvVideo);
                    myView = null;
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });
        wvVideo.loadUrl(url);
    }

    /**
     * 继续播放
     */
    @Override
    protected void onResume() {

        super.onResume();
        try {
            wvVideo.getClass().getMethod("onResume").invoke(wvVideo, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停播放
     */
    @Override
    protected void onPause() {

        super.onPause();
        try {
            wvVideo.getClass().getMethod("onPause").invoke(wvVideo, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {

        if (wvVideo != null) {
            ((ViewGroup) wvVideo.getParent()).removeView(wvVideo);
            wvVideo.destroy();
            wvVideo = null;
        }
        super.onDestroy();
    }
}
