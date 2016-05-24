package com.bzh.dytt.detail;

import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.ThunderHelper;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPresenter extends PagePresenter implements View.OnClickListener {


    private final IDetailView filmDetailView;
    private String url;
    private DetailEntity detailEntity;

    public DetailPresenter(BaseActivity baseActivity, BaseFragment baseFragment, IDetailView filmDetailView) {
        super(baseActivity, baseFragment, filmDetailView);
        this.filmDetailView = filmDetailView;
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == android.R.id.home) {
            getBaseActivity().finish();
        } else if (v.getId() == R.id.fab) {
            if (detailEntity != null && detailEntity.getDownloadUrls().size() > 0) {
                if (detailEntity.getDownloadUrls().size() == 1) {
                    ThunderHelper.getInstance(getBaseActivity()).onClickDownload(v, detailEntity.getDownloadUrls().get(0));
                } else {
                    new MaterialDialog.Builder(getBaseActivity())
                            .title("选择下载连接")
                            .items(detailEntity.getDownloadNames())
                            .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                    ThunderHelper.getInstance(getBaseActivity()).onClickDownload(v, detailEntity.getDownloadUrls().get(which));
                                    return true;
                                }
                            })
                            .positiveText("下载")
                            .show();
                }
            }
        }
    }

    @Override
    public void initFragmentConfig() {
        if (null != baseFragment.getArguments()) {
            url = baseFragment.getArguments().getString(DetailFragment.FILM_URL);
            if (!TextUtils.isEmpty(url)) {
                FilmDetailTaskSubscriber taskSubscriber = new FilmDetailTaskSubscriber();
                Repository.getInstance().getFilmDetail(url)
                        .doOnSubscribe(taskSubscriber)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(taskSubscriber);
            }
        }
        filmDetailView.initToolbar();
        filmDetailView.initFab();
    }

    private class FilmDetailTaskSubscriber extends AbstractTaskSubscriber<DetailEntity> {

        @Override
        public void onSuccess(DetailEntity detailEntity) {
            super.onSuccess(detailEntity);
            DetailPresenter.this.detailEntity = detailEntity;
            updateFileDetailStatus();
        }
    }

    private void updateFileDetailStatus() {
        filmDetailView.setFilmDetail(detailEntity);
    }
}
