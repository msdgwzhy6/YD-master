package com.bzh.dytt.video;

import com.bzh.dytt.video.weiboVideo.WeiboVideoBlog;

import java.util.ArrayList;


public interface IVideoFragment extends IBaseFragment{
    void updateList(ArrayList<WeiboVideoBlog> weiboVideoBlogs);
}
