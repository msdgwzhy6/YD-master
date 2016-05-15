package com.bzh.data.zhihu;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class ZhihuRequest {
    private static ZhihuApi zhihuApi = null;
    protected static final Object monitor = new Object();
    public static ZhihuApi getZhihuApi() {
        synchronized (monitor){
            if (zhihuApi == null) {
                zhihuApi = new Retrofit.Builder()
                        .baseUrl("http://news-at.zhihu.com")
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build().create(ZhihuApi.class);
            }
            return zhihuApi;
        }
    }
}
