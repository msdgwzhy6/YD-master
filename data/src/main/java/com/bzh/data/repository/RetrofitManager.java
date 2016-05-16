package com.bzh.data.repository;

import android.content.Context;

import com.bzh.common.context.GlobalContext;
import com.bzh.data.film.IFilmService;
import com.bzh.data.picture.IPictureService;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class RetrofitManager {


    private final IFilmService filmService;

    private final IPictureService mIPictureService;


    private static RetrofitManager retrofitManager;


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


        Retrofit meiZiRetrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(okHttpClient)
                .build();


        filmService = retrofit.create(IFilmService.class);

        mIPictureService = meiZiRetrofit.create(IPictureService.class);
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


    public IPictureService getIPictureService() {

        return mIPictureService;
    }

}


