package com.bzh.data.variety;

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
public class VarietyNetWorkDataStoreTest extends ApplicationTestCase {

    private IVarietyService varietyService;
    private VarietyNetWorkDataStore varietyNetWorkDataStore;
    private Gson gson;
    private Subscriber<ArrayList<BaseInfoEntity>> listSubscriber;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        varietyService = RetrofitManager.getInstance().getVarietyService();
        varietyNetWorkDataStore = new VarietyNetWorkDataStore(varietyService);
        listSubscriber = new Subscriber<ArrayList<BaseInfoEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("VarietyNetWorkDataStoreTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("VarietyNetWorkDataStoreTest.onError");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<BaseInfoEntity> baseInfoEntities) {
                System.out.println("VarietyNetWorkDataStoreTest.onNext");
                System.out.println(gson.toJson(baseInfoEntities));
                assertNotNull(baseInfoEntities);
                assertNotNull(baseInfoEntities.size() > 0);
                assertNotEquals("", baseInfoEntities.get(0).getName());
                assertNotEquals("", baseInfoEntities.get(0).getUrl());
                assertNotEquals("", baseInfoEntities.get(0).getPublishTime());
            }
        };
    }

    @Test
    public void testGet2013NewestChineseVariety() throws Exception {
        varietyNetWorkDataStore.get2013NewestChineseVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013ChineseVariety() throws Exception {
        varietyNetWorkDataStore.get2013ChineseVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013HKTVariety() throws Exception {
        varietyNetWorkDataStore.get2013HKTVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2013OtherVariety() throws Exception {
        varietyNetWorkDataStore.get2013OtherVariety(1).subscribe(listSubscriber);
    }

    @Test
    public void testGet2009ChineseVariety() throws Exception {
        varietyNetWorkDataStore.get2009ChineseVariety(1).subscribe(listSubscriber);
    }
}