package com.bzh.data.repository;

import android.content.Context;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.NetWorkUtil;
import com.bzh.data.film.IFilmService;
import com.bzh.data.guokr.IGuoKrService;
import com.bzh.data.picture.IMeizhiService;
import com.bzh.data.picture.IPictureService;
import com.bzh.data.weixin.IWeiXinService;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 */
public class RetrofitManager {


    private final IFilmService filmService;

    private final IPictureService mIPictureService;

    private final IMeizhiService mIMeizhiService;

    private final IGuoKrService mIGuoKrService;

    private final IWeiXinService mIWeiXinService;

    private static RetrofitManager retrofitManager;


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            if (NetWorkUtil.isNetWorkAvailable(GlobalContext.getInstance())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    private RetrofitManager(Context context) {

        int cacheSize = 10 * 1024 * 1024;

        String cachePath;
        if (null == context) {
            cachePath = "cache.dytt";
        } else {
            cachePath = context.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";
        }
        Cache cache = new Cache(new File(cachePath), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
//                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ygdy8.net")
                //scalar也是一种语言
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();



        Retrofit meizhiRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        

        Retrofit pictureRetrofit = new Retrofit.Builder()
                .baseUrl("http://image.baidu.com/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        //---------------news的加拦截器的okhttp------------------

        OkHttpClient client = new OkHttpClient.Builder()
                //addNetworkInterceptor 让所有网络请求都附上你的拦截器
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

        Retrofit weiXinRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.huceo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();


        Retrofit guokrRetrofit = new Retrofit.Builder()
                .baseUrl("http://apis.guokr.com/minisite/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        filmService = retrofit.create(IFilmService.class);

        mIMeizhiService = meizhiRetrofit.create(IMeizhiService.class);

        mIPictureService = pictureRetrofit.create(IPictureService.class);

        //---------------------新闻模块的果壳、微信、It、知乎日报--------
        mIGuoKrService = guokrRetrofit.create(IGuoKrService.class);

        mIWeiXinService = weiXinRetrofit.create(IWeiXinService.class);
    }

    public static RetrofitManager getInstance() {

        RetrofitManager tmp = retrofitManager;
        if (tmp == null) {
            synchronized (RetrofitManager.class) {
                tmp = retrofitManager;
                if (tmp == null) {
                    if (GlobalContext.getInstance() != null) {
                        tmp = new RetrofitManager(GlobalContext.getInstance());
                    } else {
                        tmp = new RetrofitManager(null);
                    }
                    retrofitManager = tmp;
                }
            }
        }
        return tmp;
    }


    public IFilmService getFilmService() {

        return filmService;
    }


    public IMeizhiService getIMezhiService() {

        return mIMeizhiService;
    }

    public IPictureService getIPictureService() {

        return mIPictureService;
    }

    public IGuoKrService getIGuoKrService() {

        return mIGuoKrService;
    }

    public IWeiXinService getIWeiXinService() {

        return mIWeiXinService;
    }
}


