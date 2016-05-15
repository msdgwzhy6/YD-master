package com.bzh.data.detail;

import android.support.annotation.NonNull;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.film.DetailEntity;
import com.bzh.data.film.FilmNetWorkDataStore;
import com.bzh.data.film.IFilmService;
import com.bzh.data.repository.RetrofitManager;
import com.bzh.data.tv.ITvService;
import com.bzh.data.tv.TvNetWorkDataStore;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import rx.Subscriber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-26<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class DetailTest extends ApplicationTestCase {

    private Gson gson;
    private IFilmService filmService;
    private FilmNetWorkDataStore filmNetWorkDataStore;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        filmService = RetrofitManager.getInstance().getFilmService();
        filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
    }

    @Test
    public void testGetFilmDetail() throws Exception {
        filmNetWorkDataStore.getFilmDetail("/html/gndy/dyzz/20160324/50538.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                assertNotNull(detailEntity);
                System.out.println(gson.toJson(detailEntity));
            }
        });
    }

    @Test
    public void testGetTvDetail() throws Exception {
        filmNetWorkDataStore.getFilmDetail("/html/tv/hytv/20160226/50311.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {
                System.out.println("");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println("detailEntity = [" + detailEntity + "]");
            }
        });
    }

    @Test
    public void testGetRIHanTvDetail() throws Exception {
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160329/50567.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160329/50566.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160329/50565.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160328/50558.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160320/50506.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20160123/50028.html").subscribe(getRiHanSubscriber());
        filmNetWorkDataStore.getFilmDetail("/html/tv/rihantv/20150519/48105.html").subscribe(getRiHanSubscriber());
    }

    @NonNull
    private Subscriber<DetailEntity> getRiHanSubscriber() {
        return new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {
                System.out.println("");
            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println(gson.toJson(detailEntity));
                assertNotNull(detailEntity);
                assertNotNull(detailEntity.getName());
                assertNotNull(detailEntity.getSource());
                assertNotNull(detailEntity.getCategory());
                assertNotNull(detailEntity.getPlaytime());
                assertNotNull(detailEntity.getScreenWriters().size() > 0);
                assertNotNull(detailEntity.getDirectors());
                assertNotNull(detailEntity.getLeadingPlayers());
                assertNotNull(detailEntity.getEpisodeNumber());
                assertNotNull(detailEntity.getDescription());
            }
        };
    }

    @Test
    public void testGet欧美TvDetail() throws Exception {
        filmNetWorkDataStore.getFilmDetail("/html/tv/oumeitv/20160404/50634.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println(gson.toJson(detailEntity));
                assertNotNull(detailEntity);
                assertNotNull(detailEntity.getTranslationName());
                assertNotNull(detailEntity.getName());
                assertNotNull(detailEntity.getYears());
                assertNotNull(detailEntity.getCountry());
                assertNotNull(detailEntity.getCategory());
                assertNotNull(detailEntity.getLanguage());
                assertNotNull(detailEntity.getPlaytime());
                assertNotNull(detailEntity.getSource());
                assertNotNull(detailEntity.getEpisodeNumber());
                assertNotNull(detailEntity.getShowTime());
                assertNotNull(detailEntity.getDirectors());
                assertNotNull(detailEntity.getScreenWriters());
                assertNotNull(detailEntity.getLeadingPlayers());
                assertNotNull(detailEntity.getDescription());
                assertTrue(detailEntity.getDirectors().size() > 0);
                assertTrue(detailEntity.getScreenWriters().size() > 0);
                assertTrue(detailEntity.getLeadingPlayers().size() > 0);
            }
        });
        filmNetWorkDataStore.getFilmDetail("/html/tv/oumeitv/20160305/50383.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println(gson.toJson(detailEntity));
                assertNotNull(detailEntity);
                assertNotNull(detailEntity.getTranslationName());
                assertNotNull(detailEntity.getName());
                assertNotNull(detailEntity.getYears());
                assertNotNull(detailEntity.getCountry());
                assertNotNull(detailEntity.getCategory());
                assertNotNull(detailEntity.getLanguage());
                assertNotNull(detailEntity.getPlaytime());
                assertNotNull(detailEntity.getSource());
                assertNotNull(detailEntity.getEpisodeNumber());
                assertNotNull(detailEntity.getShowTime());
                assertNotNull(detailEntity.getImdb());
                assertNotNull(detailEntity.getDirectors());
                assertNotNull(detailEntity.getScreenWriters());
                assertNotNull(detailEntity.getLeadingPlayers());
                assertNotNull(detailEntity.getDescription());
                assertTrue(detailEntity.getDirectors().size() > 0);
                assertTrue(detailEntity.getScreenWriters().size() > 0);
                assertTrue(detailEntity.getLeadingPlayers().size() > 0);
            }
        });
        filmNetWorkDataStore.getFilmDetail("/html/tv/oumeitv/20151113/49502.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println(gson.toJson(detailEntity));
                assertNotNull(detailEntity);
                assertNotNull(detailEntity.getTranslationName());
                assertNotNull(detailEntity.getName());
                assertNotNull(detailEntity.getYears());
                assertNotNull(detailEntity.getCountry());
                assertNotNull(detailEntity.getCategory());
                assertNotNull(detailEntity.getLanguage());
                assertNotNull(detailEntity.getPlaytime());
                assertNotNull(detailEntity.getEpisodeNumber());
                assertNotNull(detailEntity.getShowTime());
                assertNotNull(detailEntity.getImdb());
                assertNotNull(detailEntity.getDirectors());
                assertNotNull(detailEntity.getScreenWriters());
                assertNotNull(detailEntity.getLeadingPlayers());
                assertNotNull(detailEntity.getDescription());
                assertTrue(detailEntity.getDirectors().size() > 0);
                assertTrue(detailEntity.getScreenWriters().size() > 0);
                assertTrue(detailEntity.getLeadingPlayers().size() > 0);
            }
        });
    }


    @Test
    public void testGame() {
        filmNetWorkDataStore.getFilmDetail("/html/game/jingdianyouxifabu/20160422/50770.html").subscribe(new Subscriber<DetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(DetailEntity detailEntity) {
                System.out.println(gson.toJson(detailEntity));
            }
        });
    }
}