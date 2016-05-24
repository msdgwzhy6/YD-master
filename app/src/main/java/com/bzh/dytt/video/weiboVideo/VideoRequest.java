package com.bzh.dytt.video.weiboVideo;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoRequest {

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(GlobalContext.getInstance())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    static File httpCacheDirectory = new File(GlobalContext.getInstance().getCacheDir(), "videoCache");

    static int cacheSize = 10 * 1024 * 1024; // 10 MiB

    static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    private static VideoRequstApi sVideoRequstApi = null;

    protected static final Object monitor = new Object();

    public static VideoRequstApi getVideoRequstApi() {

        synchronized (monitor) {
            if (sVideoRequstApi == null) {
                sVideoRequstApi = new Retrofit.Builder()
                        //加下面这句话从VideoRequstApi的getWeiboVideo获得Observe对象了
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .baseUrl("http://www.baidu.com")
                        .build().create(VideoRequstApi.class);
            }
            return sVideoRequstApi;
        }
    }
}
