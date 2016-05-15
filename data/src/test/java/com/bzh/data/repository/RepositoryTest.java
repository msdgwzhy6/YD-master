package com.bzh.data.repository;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.bzh.common.context.GlobalContext;
import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.DetailEntity;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
public class RepositoryTest extends ApplicationTestCase {

    private Repository instance;
    private Subscriber<ArrayList<BaseInfoEntity>> listSubscriber;
    private Gson gson;

    @Before
    public void setUp() throws Exception {
        instance = Repository.getInstance();
        gson = new Gson();
        listSubscriber = new Subscriber<ArrayList<BaseInfoEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("RepositoryTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("RepositoryTest.onError");
                System.out.println("e = [" + e + "]");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<BaseInfoEntity> baseInfoEntities) {
                System.out.println("RepositoryTest.onNext");
                System.out.println(gson.toJson(baseInfoEntities));
                assertNotNull(baseInfoEntities);
                assertTrue(baseInfoEntities.size() > 0);
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDomestic() throws Exception {
        instance.getDomestic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewest() throws Exception {
        instance.getNewest(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetEuropeAmerica() throws Exception {
        instance.getEuropeAmerica(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetJapanSouthKorea() throws Exception {
        instance.getJapanSouthKorea(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetFilmDetail() throws Exception {
        instance.getFilmDetail("/html/gndy/dyzz/20160324/50538.html").subscribe(new Subscriber<DetailEntity>() {
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
    public void testGetChineseDomesticTv() throws Exception {
        instance.getChineseDomesticTv(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHKTTv() throws Exception {
        instance.getHKTTv(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetChineseTv() throws Exception {
        instance.getChineseTv(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetJapanSouthKoreaTV() throws Exception {
        instance.getJapanSouthKoreaTV(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetEuropeAmericaTV() throws Exception {
        instance.getEuropeAmericaTV(1).subscribe(listSubscriber);
    }


    @Test
    public void testGet2013NewestChineseVariety() throws Exception {
        instance.get2013NewestChineseVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013ChineseVariety() throws Exception {
        instance.get2013ChineseVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013HKTVariety() throws Exception {
        instance.get2013HKTVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013OtherVariety() throws Exception {
        instance.get2013OtherVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2009ChineseVariety() throws Exception {
        instance.get2009ChineseVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetComic() throws Exception {
        instance.getComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewComic() throws Exception {
        instance.getNewComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetSSComic() throws Exception {
        instance.getSSComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetOtherComic() throws Exception {
        instance.getOtherComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetGCComic() throws Exception {
        instance.getGCComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHZWComic() throws Exception {
        instance.getHZWComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHYComic() throws Exception {
        instance.getHYComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetGame() throws Exception {
        instance.getGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHotGame() throws Exception {
        instance.getHotGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetClassicGame() throws Exception {
        instance.getClassicGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewestGame() throws Exception {
        instance.getNewComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testApp() {
        PackageManager manager = GlobalContext.getInstance().getPackageManager();
        List<ApplicationInfo> installedApplications = manager.getInstalledApplications(0);
        System.out.println(manager);
        System.out.println(installedApplications);
    }
}