package com.bzh.dytt.detail;

import com.bzh.data.film.DetailEntity;
import com.bzh.dytt.base.basic_pageswitch.IPageView;

public interface IDetailView extends IPageView {

    void initFab();

    void setFilmDetail(DetailEntity detailEntity);

    void initToolbar();
}
