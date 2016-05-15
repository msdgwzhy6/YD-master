package com.bzh.dytt.film;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DomesticFilmPresenterFilm extends BaseFilmInfoPresenter {

    public DomesticFilmPresenterFilm(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        super(baseActivity, baseFragment, iView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getDomestic(Integer.valueOf(nextPage));
    }

    @Override
    public ExCommonAdapter<BaseInfoEntity> getExCommonAdapter() {
        return new ExCommonAdapter<BaseInfoEntity>(getBaseActivity(), R.layout.item_film) {
            @Override
            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item) {
                viewHolder.setText(R.id.tv_film_name, item.getName());
                viewHolder.setText(R.id.tv_film_publish_time, getBaseActivity().getResources().getString(R.string.label_publish_time, item.getPublishTime()));
            }
        };
    }

    @Override
    public String getMaxPage() {
        return 87 + "";
    }
}
