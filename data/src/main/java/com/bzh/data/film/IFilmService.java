package com.bzh.data.film;

import android.support.annotation.IntRange;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　电影<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IFilmService {

    @Headers("Cache-Control:public, max-age=30, max-stale=10")
    @GET("/html/gndy/dyzz/list_23_{index}.html")
    Observable<Response<ResponseBody>> getTest(@Path("index") @IntRange(from = 1, to = 131) int index);


    // 获取最新电影
    @GET("/html/gndy/dyzz/list_23_{index}.html")
    Observable<ResponseBody> getNewest(@Path("index") @IntRange(from = 1, to = 131) int index);

    // 国内电影
    @GET("/html/gndy/china/list_4_{index}.html")
    Observable<ResponseBody> getDomestic(@Path("index") @IntRange(from = 1, to = 87) int index);

    // 欧美电影
    @GET("/html/gndy/oumei/list_7_{index}.html")
    Observable<ResponseBody> getEuropeAmerica(@Path("index") @IntRange(from = 1, to = 147) int index);

    // 日韩电影
    @GET("/html/gndy/rihan/list_6_{index}.html")
    Observable<ResponseBody> getJapanSouthKorea(@Path("index") @IntRange(from = 1, to = 25) int index);

    // 获取电影详情
    @GET("{filmDetailUrl}")
    Observable<ResponseBody> getFilmDetail(@Path("filmDetailUrl") String filmDetailUrl);
}
