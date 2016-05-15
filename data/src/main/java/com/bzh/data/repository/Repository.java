package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.film.FilmNetWorkDataStore;
import com.bzh.data.film.IFilmDataStore;
import com.bzh.data.film.IFilmService;
import com.bzh.data.image.ImageResponse;
import com.bzh.data.zhihu.ZhihuRequest;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class Repository implements IFilmDataStore {

    private static volatile Repository repository;

    private static volatile IFilmService filmService;


    public static Repository getInstance() {

        Repository tmp = repository;
        if (tmp == null) {
            synchronized (Repository.class) {
                tmp = repository;
                if (tmp == null) {
                    tmp = new Repository();
                    repository = tmp;
                    filmService = RetrofitManager.getInstance().getFilmService();
                }
            }
        }
        return tmp;
    }

    private Repository() {

    }

    private FilmNetWorkDataStore filmNetWorkDataStore;


    private IFilmDataStore getFilmDataStore() {

        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
        }
        return filmNetWorkDataStore;
    }


    @Override
    public Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {

        return getFilmDataStore().getDomestic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 131) int index) {

        return getFilmDataStore().getNewest(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {

        return getFilmDataStore().getEuropeAmerica(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {

        return getFilmDataStore().getJapanSouthKorea(index);
    }

    @Override
    public Observable<DetailEntity> getFilmDetail(String filmStr) {

        return getFilmDataStore().getFilmDetail(filmStr);
    }

    //得到图片
    public Observable<ImageResponse> getImage() {

        return ZhihuRequest.getZhihuApi().getImage();
    }
}
