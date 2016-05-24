package com.bzh.dytt.video;

public interface IVideoPresenter extends BasePresenterV{
    void getVideo(int page);

    void getVideoFromCache(int page);
}
