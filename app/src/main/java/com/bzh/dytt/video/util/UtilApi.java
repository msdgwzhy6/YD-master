package com.bzh.dytt.video.util;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 */
public interface UtilApi {

    @FormUrlEncoded
    @POST("http://www.weibovideo.com")
    Observable<ResponseBody> getVideoUrl(@Field("weibourl") String weibourl);
}
