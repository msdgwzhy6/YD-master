package com.bzh.dytt.video;

/**
 * Created by 蔡小木 on 2016/4/23 0023.
 */
public interface IVideoPresenter extends BasePresenterV{
    void getVideo(int page);

    void getVideoFromCache(int page);
}
