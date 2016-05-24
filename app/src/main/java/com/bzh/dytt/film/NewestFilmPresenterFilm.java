package com.bzh.dytt.film;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;

import java.util.ArrayList;

import rx.Observable;

public class NewestFilmPresenterFilm extends BaseFilmInfoPresenter {

    public NewestFilmPresenterFilm(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getNewest(Integer.valueOf(nextPage));
    }

    @Override
    public String getMaxPage() {
        return 131 + "";
    }
}
