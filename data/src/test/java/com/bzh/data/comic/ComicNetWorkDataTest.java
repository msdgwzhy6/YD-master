package com.bzh.data.comic;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.RetrofitManager;
import com.bzh.data.variety.IVarietyService;
import com.bzh.data.variety.VarietyNetWorkDataStore;
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
public class ComicNetWorkDataTest extends ApplicationTestCase {

    private IComicService iComicService;
    private ComicNetWorkData comicNetWorkData;
    private Gson gson;
    private Subscriber<ArrayList<BaseInfoEntity>> listSubscriber;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        iComicService = RetrofitManager.getInstance().getComicService();
        comicNetWorkData = new ComicNetWorkData(iComicService);
        listSubscriber = new Subscriber<ArrayList<BaseInfoEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("ComicNetWorkDataTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("ComicNetWorkDataTest.onError");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<BaseInfoEntity> baseInfoEntities) {
                System.out.println("ComicNetWorkDataTest.onNext");
                System.out.println(gson.toJson(baseInfoEntities));
                assertNotNull(baseInfoEntities);
                assertNotNull(baseInfoEntities.size() > 0);
                assertNotEquals("", baseInfoEntities.get(0).getName());
                assertNotEquals("", baseInfoEntities.get(0).getUrl());
                assertNotEquals("", baseInfoEntities.get(0).getPublishTime());
            }
        };
    }


    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetComic() throws Exception {
        comicNetWorkData.getComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewComic() throws Exception {
        comicNetWorkData.getComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetSSComic() throws Exception {
        comicNetWorkData.getSSComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetOtherComic() throws Exception {
        comicNetWorkData.getOtherComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetGCComic() throws Exception {
        comicNetWorkData.getGCComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHZWComic() throws Exception {
        comicNetWorkData.getHZWComic(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHYComic() throws Exception {
        comicNetWorkData.getHYComic(1).subscribe(listSubscriber);
    }
}