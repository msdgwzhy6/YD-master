package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.film.FilmNetWorkDataStore;
import com.bzh.data.film.IFilmDataStore;
import com.bzh.data.film.IFilmService;
import com.bzh.data.guokr.GuoKrNetWorkDataStore;
import com.bzh.data.guokr.IGuoKrDataStore;
import com.bzh.data.guokr.IGuoKrService;
import com.bzh.data.guokr.bean.GuokrArticle;
import com.bzh.data.guokr.bean.GuokrHotItem;
import com.bzh.data.image.ImageResponse;
import com.bzh.data.picture.IPictureDataStore;
import com.bzh.data.picture.IPictureService;
import com.bzh.data.picture.PictureNetWorkDataStore;
import com.bzh.data.picture.bean.MeiNvs;
import com.bzh.data.weixin.IWeiXinDataStore;
import com.bzh.data.weixin.IWeiXinService;
import com.bzh.data.weixin.WeiXinNetWorkDataStore;
import com.bzh.data.weixin.bean.WeixinNews;
import com.bzh.data.zhihu.ZhihuRequest;

import java.util.ArrayList;
import java.util.List;

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
public class Repository implements IFilmDataStore,IPictureDataStore,IGuoKrDataStore,
        IWeiXinDataStore{

    private static volatile Repository repository;

    private static volatile IFilmService filmService;
    
    private static volatile IPictureService mIPictureService;

    private static volatile IGuoKrService mIGuoKrService;

    private static volatile IWeiXinService mIWeiXinService;
    public static Repository getInstance() {

        Repository tmp = repository;
        if (tmp == null) {
            synchronized (Repository.class) {
                tmp = repository;
                if (tmp == null) {
                    tmp = new Repository();
                    repository = tmp;
                    filmService = RetrofitManager.getInstance().getFilmService();
                    mIPictureService = RetrofitManager.getInstance().getIPictureService();
                    
                    //--------------果壳的service--------
                    mIGuoKrService = RetrofitManager.getInstance().getIGuoKrService();

                    mIWeiXinService = RetrofitManager.getInstance().getIWeiXinService();
                }
            }
        }
        return tmp;
    }

    private Repository() {

    }
    
    //---------------------------电影------------

    private FilmNetWorkDataStore filmNetWorkDataStore;


    private IFilmDataStore getFilmDataStore() {

        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
        }
        return filmNetWorkDataStore;
    }
    
    //----------------------------图片-------------

    private IPictureDataStore mIPictureDataStore;

    private IPictureDataStore getIPictureDataStore() {

        if (mIPictureDataStore==null) {
            mIPictureDataStore = new PictureNetWorkDataStore(mIPictureService);
        }

        return mIPictureDataStore;
    }

    //----------------------------果壳--------------

    private IGuoKrDataStore mIGuoKrDataStore;

    private IGuoKrDataStore getIGuoKrDataStore() {

        if (mIGuoKrDataStore==null) {
            mIGuoKrDataStore = new GuoKrNetWorkDataStore(mIGuoKrService);
        }

        return mIGuoKrDataStore;
    }

    //----------------------------微信--------------

    private IWeiXinDataStore mIWeiXinDataStore;

    private IWeiXinDataStore getIWeiXinDataStore() {

        if (mIWeiXinDataStore==null) {
            mIWeiXinDataStore = new WeiXinNetWorkDataStore(mIWeiXinService);
        }

        return mIWeiXinDataStore;
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

    @Override
    public Observable<List<MeiNvs.ImgsEntity>> getMeiNv(String col, @IntRange(from = 1, to = 99) int pn
                                                        ) {
        return getIPictureDataStore().getMeiNv(col,pn);
    }
    
    //------------------------果壳热门的数据源------------

    @Override
    public Observable<List<GuokrHotItem>> getGuoKrHotItems(int offset) {

        return getIGuoKrDataStore().getGuoKrHotItems(offset);
    }

    @Override
    public Observable<GuokrArticle> getGuokrArticle(String id) {

        return getIGuoKrDataStore().getGuokrArticle(id);
    }

    @Override
    public Observable<List<WeixinNews>> getWeixin(int page) {

        return getIWeiXinDataStore().getWeixin(page);
    }
}
