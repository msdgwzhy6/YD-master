package com.bzh.dytt.video.weiboVideo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YiuChoi on 2016/4/12 0012.
 */
public interface VideoRequstApi {

    @GET("http://m.weibo.cn/page/json?containerid=1005051914635823_-_WEIBO_SECOND_PROFILE_WEIBO&")
    Observable<WeiboVideoResponse> getWeiboVideo(@Query("page") int page);
}
