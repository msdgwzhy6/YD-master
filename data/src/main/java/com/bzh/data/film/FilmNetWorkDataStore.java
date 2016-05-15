package com.bzh.data.film;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　电影<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmNetWorkDataStore implements IFilmDataStore {

    private final IFilmService iFilmService;

    public FilmNetWorkDataStore(IFilmService iFilmService) {

        this.iFilmService = iFilmService;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {

        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getDomestic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 131) final int index) {

        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getNewest(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {

        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getEuropeAmerica(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {

        return DataStoreController.getInstance().getNewWorkObservable(iFilmService
                .getJapanSouthKorea(index));
    }

    @Override
    public Observable<DetailEntity> getFilmDetail(final String filmStr) {

        return DataStoreController.getInstance().getNewWorkDetailObservable(iFilmService.getFilmDetail(filmStr));
    }
}
