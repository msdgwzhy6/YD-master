package com.bzh.dytt.base.basic_pageswitch;

import com.bzh.dytt.base.basic.BaseView;

public interface IPageView extends BaseView {
    

    void layoutLoadingVisibility(boolean isVisible);

    void layoutLoadFailedVisibility(boolean isVisible);

    void layoutEmptyVisibility(boolean isVisible);

    void layoutContentVisibility(boolean isVisible);

    void setTextLoadFailed(String content);
}
