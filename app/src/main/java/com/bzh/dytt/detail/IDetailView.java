package com.bzh.dytt.detail;

import com.bzh.data.film.DetailEntity;
import com.bzh.dytt.base.basic_pageswitch.IPageView;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-4-3<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IDetailView extends IPageView {

    void initFab();

    void setFilmDetail(DetailEntity detailEntity);

    void initToolbar();
}
