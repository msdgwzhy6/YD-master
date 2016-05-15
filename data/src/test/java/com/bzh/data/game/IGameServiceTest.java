package com.bzh.data.game;

import com.bzh.data.repository.RetrofitManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
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
public class IGameServiceTest {

    private IGameService gameService;
    private Subscriber<ResponseBody> subscriber;

    @Before
    public void setUp() throws Exception {
        gameService = RetrofitManager.getInstance().getGameService();
        subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                System.out.println("IGameServiceTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("IGameServiceTest.onError");
                System.out.println("e = [" + e + "]");
                assertNull(e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                System.out.println("IGameServiceTest.onNext");
                System.out.println("responseBody = [" + responseBody + "]");
                assertNotNull(responseBody);
            }
        };
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetGame() throws Exception {
        gameService.getGame(1).subscribe(subscriber);
    }

    @Test
    public void testGetHotGame() throws Exception {
        gameService.getHotGame(1).subscribe(subscriber);
    }

    @Test
    public void testGetClassicGame() throws Exception {
        gameService.getClassicGame(1).subscribe(subscriber);
    }

    @Test
    public void testGetNewestGame() throws Exception {
        gameService.getNewestGame(1).subscribe(subscriber);
    }
}