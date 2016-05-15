package com.bzh.dytt.film;

import android.support.v4.widget.SwipeRefreshLayout;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerView;
import com.bzh.dytt.detail.DetailFragment;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

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
public abstract class BaseFilmInfoPresenter extends RefreshRecyclerPresenter<BaseInfoEntity, 
        ArrayList<BaseInfoEntity>> implements SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter
        .OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    public BaseFilmInfoPresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {

        super(baseActivity, baseFragment, iView);
    }

    @Override
    public ExCommonAdapter<BaseInfoEntity> getExCommonAdapter() {

        return new ExCommonAdapter<BaseInfoEntity>(getBaseActivity(), R.layout.item_film) {

            @Override
            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item) {

                viewHolder.setText(R.id.tv_film_name, item.getName());
                viewHolder.setText(R.id.tv_film_publish_time, getBaseActivity().getResources().getString(R.string
                        .label_publish_time, item.getPublishTime()));
            }
        };
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {

        super.onItemClick(viewHolder);
        BaseInfoEntity baseInfoEntity = getCommonAdapter().getData().get(viewHolder.getAdapterPosition());
        DetailFragment.launch(getBaseActivity(), baseInfoEntity.getUrl());
    }
}
