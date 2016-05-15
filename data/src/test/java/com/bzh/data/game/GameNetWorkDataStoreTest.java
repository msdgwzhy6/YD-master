package com.bzh.data.game;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.RetrofitManager;
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
 * <b>创建日期</b>：　16-3-29<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class GameNetWorkDataStoreTest extends ApplicationTestCase{

    private Gson gson;
    private IGameService gameService;
    private GameNetWorkDataStore gameNetWorkDataStore;
    private Subscriber<ArrayList<BaseInfoEntity>> listSubscriber;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        gameService = RetrofitManager.getInstance().getGameService();
        gameNetWorkDataStore = new GameNetWorkDataStore(gameService);
        listSubscriber = new Subscriber<ArrayList<BaseInfoEntity>>() {
            @Override
            public void onCompleted() {
                System.out.println("GameNetWorkDataStoreTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("GameNetWorkDataStoreTest.onError");
                assertNull(e);
            }

            @Override
            public void onNext(ArrayList<BaseInfoEntity> baseInfoEntities) {
                System.out.println("GameNetWorkDataStoreTest.onNext");
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
    public void testGetGame() throws Exception {
        gameNetWorkDataStore.getGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetHotGame() throws Exception {
        gameNetWorkDataStore.getHotGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetClassicGame() throws Exception {
        gameNetWorkDataStore.getClassicGame(1).subscribe(listSubscriber);
    }

    @Test
    public void testGetNewestGame() throws Exception {
        gameNetWorkDataStore.getNewestGame(1).subscribe(listSubscriber);
    }
}