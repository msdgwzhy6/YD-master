package com.bzh.data.film;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.RetrofitManager;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import rx.Subscriber;

import static org.junit.Assert.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-27<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmNetWorkDataStoreTest extends ApplicationTestCase {

    private Subscriber<ArrayList<BaseInfoEntity>> listSubscriber;
    private FilmNetWorkDataStore filmNetWorkDataStore;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        IFilmService filmService = RetrofitManager.getInstance().getFilmService();
        filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
        listSubscriber = new Subscriber<ArrayList<BaseInfoEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("FilmNetWorkDataStoreTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<BaseInfoEntity> baseInfoEntities) {
                assertNotNull(baseInfoEntities);
                assertTrue(baseInfoEntities.size() > 0);
                System.out.println(gson.toJson(baseInfoEntities));
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDomestic() throws Exception {
        filmNetWorkDataStore.getDomestic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewest() throws Exception {
        filmNetWorkDataStore.getNewest(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetEuropeAmerica() throws Exception {
        filmNetWorkDataStore.getEuropeAmerica(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetJapanSouthKorea() throws Exception {
        filmNetWorkDataStore.getJapanSouthKorea(1).subscribe(listSubscriber);
    }


}