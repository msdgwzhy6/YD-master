package com.bzh.dytt.film;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.ScreenUtil;
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

                runEnterAnimation(viewHolder.itemView, viewHolder.getAdapterPosition());
            }
        };
    }

    /**
     * item动画，可以抽象到RefreshFragment里面去
     * 
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {

        view.setTranslationY(ScreenUtil.getScreenHight(GlobalContext.getInstance()));
        view.animate()
                .translationY(0)
                .setStartDelay(100 * (position % 5))
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {

        super.onItemClick(viewHolder);

        
        BaseInfoEntity baseInfoEntity = getCommonAdapter().getData().get(viewHolder.getAdapterPosition());
        DetailFragment.launch(getBaseActivity(), baseInfoEntity.getUrl());
    }
}
