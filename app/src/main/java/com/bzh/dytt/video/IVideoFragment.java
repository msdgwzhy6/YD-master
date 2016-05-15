package com.bzh.dytt.video;

import com.bzh.dytt.video.weiboVideo.WeiboVideoBlog;

import java.util.ArrayList;


/**
 * Created by 蔡小木 on 2016/4/23 0023.
 */
public interface IVideoFragment extends IBaseFragment{
    void updateList(ArrayList<WeiboVideoBlog> weiboVideoBlogs);
}
